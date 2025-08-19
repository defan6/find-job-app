package com.ddos.jobMS.job_ms.job.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequest {
    private String name;

    private String description;


    private BigDecimal minSalary;

    private BigDecimal maxSalary;


    private Long companyId;
}
