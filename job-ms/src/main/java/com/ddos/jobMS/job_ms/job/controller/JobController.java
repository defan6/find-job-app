package com.ddos.jobMS.job_ms.job.controller;

import com.ddos.jobMS.job_ms.job.dto.JobWithCompanyResponse;
import com.ddos.jobMS.job_ms.job.dto.request.JobRequest;
import com.ddos.jobMS.job_ms.job.dto.response.JobResponse;
import com.ddos.jobMS.job_ms.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobWithCompanyResponse>> getAllJobs(){
        return ResponseEntity.ok(jobService.findAll());
    }


    @PostMapping
    public ResponseEntity<JobResponse> createJob(@RequestBody JobRequest jobRequest){
        JobResponse response = jobService.createJob(jobRequest);
        return ResponseEntity.created(URI.create("/jobs/" + response.getId())).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<JobWithCompanyResponse> getJobById(@PathVariable("id") Long id, @RequestParam("companyId") Long companyId){
        return ResponseEntity.ok(jobService.findById(id, companyId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<JobWithCompanyResponse> updateJob(@PathVariable("id") Long id, @RequestBody JobRequest jobRequest){
        return ResponseEntity.ok(jobService.updateJob(id, jobRequest));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") Long id, @RequestParam("companyId") Long companyId){
        boolean isDeleted = jobService.deleteJob(id, companyId
        );
        return isDeleted ?
                ResponseEntity.noContent().build()
                :
                ResponseEntity.notFound().build();
    }
}
