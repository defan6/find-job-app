package com.ddos.companyMS.company_ms.company.service;

import com.ddos.companyMS.company_ms.company.dto.request.CompanyRequest;
import com.ddos.companyMS.company_ms.company.dto.response.CompanyResponse;
import com.ddos.companyMS.company_ms.company.entity.Company;
import com.ddos.companyMS.company_ms.company.exception.CompanyNotFoundException;
import com.ddos.companyMS.company_ms.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;



    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> findAll() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(this::mapToResponse).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public CompanyResponse createCompany(CompanyRequest companyRequest) {
        Company company = mapToEntity(companyRequest);
        Company savedCompany = companyRepository.save(company);
        return mapToResponse(savedCompany);
    }


    @Override
    @Transactional(readOnly = true)
    public CompanyResponse findCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company with id " + id + " not found"));
        return mapToResponse(company);
    }


    @Override
    @Transactional
    public CompanyResponse updateCompany(Long id, CompanyRequest companyRequest) {
        Company company = companyRepository.findById(id).orElseThrow(()-> new CompanyNotFoundException("Company with id " + id + "not found"));
        updateFromRequest(companyRequest, company);
        Company savedCompany = companyRepository.save(company);
        return mapToResponse(company);

    }


    @Override
    @Transactional
    public boolean deleteCompany(Long id) {
        return companyRepository.findById(id)
                .map(company -> {
                    companyRepository.delete(company);
                    return true;
                })
                .orElse(false);
    }


    private Company mapToEntity(CompanyRequest request){
        Company company = new Company();
        company.setName(request.getName());
        company.setDescription(request.getDescription());
        return company;
    }


    private CompanyResponse mapToResponse(Company company){
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .build();
    }


    private Company updateFromRequest(CompanyRequest request, Company company){
        company.setDescription(request.getDescription());
        company.setName(request.getName());
        return company;
    }



}
