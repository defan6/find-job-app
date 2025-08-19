package com.ddos.reviewMS.review_ms.review.dto;


import com.ddos.reviewMS.review_ms.review.dto.response.CompanyResponse;
import com.ddos.reviewMS.review_ms.review.dto.response.ReviewResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseWithCompanyResponse {

    private CompanyResponse companyResponse;


    private ReviewResponse reviewResponse;
}
