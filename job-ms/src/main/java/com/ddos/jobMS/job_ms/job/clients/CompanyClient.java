package com.ddos.jobMS.job_ms.job.clients;


import com.ddos.jobMS.job_ms.job.dto.response.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "company-ms")
public interface CompanyClient {

    @GetMapping("/companies/{companyId}")
    CompanyResponse getCompanyById(@PathVariable("companyId") Long companyId);


    @GetMapping("/companies")
    List<CompanyResponse> getAllCompanies();
}
