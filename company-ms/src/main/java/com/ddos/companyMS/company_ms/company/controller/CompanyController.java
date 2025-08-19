package com.ddos.companyMS.company_ms.company.controller;

import com.ddos.companyMS.company_ms.company.dto.request.CompanyRequest;
import com.ddos.companyMS.company_ms.company.dto.response.CompanyResponse;
import com.ddos.companyMS.company_ms.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;


    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody CompanyRequest companyRequest) {
        CompanyResponse response = companyService.createCompany(companyRequest);
        return ResponseEntity
                .created(URI.create("/companies/" + response.getId())).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.findCompanyById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable("id") Long id, @RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.ok(companyService.updateCompany(id, companyRequest));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable("id") Long id) {
        return companyService.deleteCompany(id) ?
                ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
