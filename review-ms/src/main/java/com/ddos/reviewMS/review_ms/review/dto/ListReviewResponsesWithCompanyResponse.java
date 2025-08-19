package com.ddos.reviewMS.review_ms.review.dto;

import com.ddos.reviewMS.review_ms.review.dto.response.CompanyResponse;
import com.ddos.reviewMS.review_ms.review.dto.response.ReviewResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListReviewResponsesWithCompanyResponse {
    private CompanyResponse companyResponse;

    private List<ReviewResponse> reviewResponses;
}
