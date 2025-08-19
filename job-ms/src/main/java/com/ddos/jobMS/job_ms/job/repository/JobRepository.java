package com.ddos.jobMS.job_ms.job.repository;

import com.ddos.jobMS.job_ms.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

}
