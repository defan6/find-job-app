package com.ddos.jobMS.job_ms.job.service;


import com.ddos.jobMS.job_ms.job.dto.JobWithCompanyResponse;
import com.ddos.jobMS.job_ms.job.dto.request.JobRequest;
import com.ddos.jobMS.job_ms.job.dto.response.JobResponse;

import java.util.List;
import java.util.Optional;

public interface JobService {
    List<JobWithCompanyResponse> findAll();

    JobResponse createJob(JobRequest jobRequest);

    JobWithCompanyResponse findById(Long jobId, Long companyId);

    JobWithCompanyResponse updateJob(Long id, JobRequest jobRequest);

    boolean deleteJob(Long jobId, Long companyId);
}
