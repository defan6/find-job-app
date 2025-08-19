package com.ddos.companyMS.company_ms.company.repository;

import com.ddos.companyMS.company_ms.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
