package com.ddos.companyMS.company_ms.company.service;


import com.ddos.companyMS.company_ms.company.dto.request.CompanyRequest;
import com.ddos.companyMS.company_ms.company.dto.response.CompanyResponse;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    List<CompanyResponse> findAll();

    CompanyResponse createCompany(CompanyRequest companyRequest);

   CompanyResponse findCompanyById(Long id);

    CompanyResponse updateCompany(Long id, CompanyRequest companyRequest);

    boolean deleteCompany(Long id);
}
