package com.ddos.reviewMS.review_ms.review.service;

import com.ddos.reviewMS.review_ms.review.dto.ListReviewResponsesWithCompanyResponse;
import com.ddos.reviewMS.review_ms.review.dto.ReviewResponseWithCompanyResponse;
import com.ddos.reviewMS.review_ms.review.dto.request.ReviewRequest;
import com.ddos.reviewMS.review_ms.review.dto.response.ReviewResponse;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    ListReviewResponsesWithCompanyResponse getAllReviewsByCompany(Long companyId);

    ReviewResponse addReviewForCompany(ReviewRequest request);

    ReviewResponseWithCompanyResponse getReviewByIdAndCompanyId(Long companyId, Long reviewId);

    ReviewResponseWithCompanyResponse updateReview(Long reviewId, ReviewRequest request);

    boolean deleteReview(Long companyId, Long reviewId);
}
