package com.ddos.jobMS.job_ms.job.dto;

import com.ddos.jobMS.job_ms.job.dto.response.CompanyResponse;
import com.ddos.jobMS.job_ms.job.dto.response.JobResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobWithCompanyResponse {
    JobResponse jobResponse;

    CompanyResponse companyResponse;

}
