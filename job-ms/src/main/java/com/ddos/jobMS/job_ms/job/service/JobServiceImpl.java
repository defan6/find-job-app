package com.ddos.jobMS.job_ms.job.service;

import com.ddos.jobMS.job_ms.job.clients.CompanyClient;
import com.ddos.jobMS.job_ms.job.dto.response.ErrorResponse;
import com.ddos.jobMS.job_ms.job.exception.CompanyNotFoundException;
import com.ddos.jobMS.job_ms.job.dto.JobWithCompanyResponse;
import com.ddos.jobMS.job_ms.job.dto.request.JobRequest;
import com.ddos.jobMS.job_ms.job.dto.response.CompanyResponse;
import com.ddos.jobMS.job_ms.job.dto.response.JobResponse;
import com.ddos.jobMS.job_ms.job.entity.Job;
import com.ddos.jobMS.job_ms.job.exception.JobNotFoundException;
import com.ddos.jobMS.job_ms.job.repository.JobRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;


    private final CompanyClient companyClient;


    @Transactional(readOnly = true)
    @Override
    public List<JobWithCompanyResponse> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<CompanyResponse> responses = companyClient.getAllCompanies();

        Map<Long, CompanyResponse> companyMap = responses.stream().collect(Collectors.toMap(CompanyResponse::getId, c -> c));
        List<JobWithCompanyResponse> DTOResponses = jobs.stream().map(job -> new JobWithCompanyResponse(mapToResponse(job), companyMap.get(job.getCompanyId()))).collect(Collectors.toList());
        return DTOResponses;
    }

    @Transactional
    @Override
    public JobResponse createJob(JobRequest jobRequest) {
        CompanyResponse companyResponse = getCompanyById(jobRequest.getCompanyId());
        Job job = mapToEntity(jobRequest);
        Job savedJob = jobRepository.save(job);
        return mapToResponse(savedJob);
    }

    @Transactional(readOnly = true)
    @Override
    public JobWithCompanyResponse findById(Long jobId, Long companyId) {
        CompanyResponse company = getCompanyById(companyId);
        Optional<JobResponse> jobResponseOptional = jobRepository.findById(jobId).map(this::mapToResponse);
        if (jobResponseOptional.isEmpty() || !jobResponseOptional.get().getCompanyId().equals(companyId)) {
            throw new JobNotFoundException("Job with id " + jobId + " not found for company with id" + companyId);
        }
        return new JobWithCompanyResponse(jobResponseOptional.get(), company);
    }

    @Transactional
    @Override
    public JobWithCompanyResponse updateJob(Long id, JobRequest jobRequest) {
        CompanyResponse company = getCompanyById(jobRequest.getCompanyId());
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isEmpty() || !jobOptional.get().getCompanyId().equals(company.getId())) {
            throw new JobNotFoundException("Job with id " + id + " not found for company with id" + jobRequest.getCompanyId());
        }
        Job job = jobOptional.get();
        updateFromRequest(jobRequest, job);


        Job savedJob = jobRepository.save(job);
        return new JobWithCompanyResponse(mapToResponse(savedJob), company);
    }

    @Transactional
    @Override
    public boolean deleteJob(Long jobId, Long companyId) {
        CompanyResponse company = getCompanyById(companyId);
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if (jobOptional.isEmpty() || !companyId.equals(jobOptional.get().getCompanyId())) {
            throw new JobNotFoundException("Job with id " + jobId + " not found for company with id" + companyId);
        }
        jobRepository.deleteById(jobId);
        return true;
    }


    private JobResponse mapToResponse(Job job) {
        return JobResponse.builder()
                .name(job.getName())
                .companyId(job.getCompanyId())
                .description(job.getDescription())
                .minSalary(job.getMinSalary())
                .maxSalary(job.getMaxSalary())
                .id(job.getId())
                .build();
    }


    private Job mapToEntity(JobRequest request) {
        return Job.builder()
                .name(request.getName())
                .companyId(request.getCompanyId())
                .description(request.getDescription())
                .minSalary(request.getMinSalary())
                .maxSalary(request.getMaxSalary())
                .build();
    }


    private Job updateFromRequest(JobRequest request, Job job) {
        job.setCompanyId(request.getCompanyId());
        job.setName(request.getName());
        job.setDescription(request.getDescription());
        job.setMinSalary(request.getMinSalary());
        job.setMaxSalary(request.getMaxSalary());


        return job;
    }


    private ErrorResponse parseError(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, ErrorResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось разобрать ошибку от company-service: " + json);
        }
    }


    private CompanyResponse getCompanyById(Long companyId) {
        try {
            CompanyResponse companyResponse = companyClient.getCompanyById(companyId);
            return companyResponse;
        } catch (FeignException e) {
            String errorJson = e.contentUTF8();
            ErrorResponse errorResponse = parseError(errorJson);
            if (errorResponse.getErrorCode().equals("CompanyNotFoundException")) {
                throw new CompanyNotFoundException("Company with id " + companyId + " not found");
            }
            throw new RuntimeException("Error from company-service: " + errorResponse.getMessage());
        }
    }
}
