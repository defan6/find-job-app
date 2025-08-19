package com.ddos.companyMS.company_ms.company.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequest {
    private String name;
    private String description;
//    private List<JobRequest> jobs;
}
