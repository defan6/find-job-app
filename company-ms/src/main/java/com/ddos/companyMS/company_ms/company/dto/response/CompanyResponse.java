package com.ddos.companyMS.company_ms.company.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponse {
    private Long id;
    private String name;
    private String description;
//    private List<JobResponse> jobs;
////    private List<ReviewResponse> reviews;
}
