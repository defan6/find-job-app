package com.ddos.companyMS.company_ms.company.handler;


import com.ddos.companyMS.company_ms.company.dto.response.ErrorResponse;
import com.ddos.companyMS.company_ms.company.exception.CompanyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCompanyNotFound(CompanyNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("CompanyNotFoundException", ex.getMessage()));
    }
}
