package com.ddos.jobMS.job_ms.job.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal minSalary;

    private BigDecimal maxSalary;

    private Long companyId;
}
