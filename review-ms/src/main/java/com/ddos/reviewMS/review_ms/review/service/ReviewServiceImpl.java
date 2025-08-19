package com.ddos.reviewMS.review_ms.review.service;

import com.ddos.reviewMS.review_ms.review.clients.CompanyClient;
import com.ddos.reviewMS.review_ms.review.dto.ListReviewResponsesWithCompanyResponse;
import com.ddos.reviewMS.review_ms.review.dto.ReviewResponseWithCompanyResponse;
import com.ddos.reviewMS.review_ms.review.dto.response.ErrorResponse;
import com.ddos.reviewMS.review_ms.review.exception.CompanyNotFoundException;
import com.ddos.reviewMS.review_ms.review.exception.ReviewNotFoundException;
import com.ddos.reviewMS.review_ms.review.dto.request.ReviewRequest;
import com.ddos.reviewMS.review_ms.review.dto.response.CompanyResponse;
import com.ddos.reviewMS.review_ms.review.dto.response.ReviewResponse;
import com.ddos.reviewMS.review_ms.review.entity.Review;
import com.ddos.reviewMS.review_ms.review.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final CompanyClient companyClient;


    @Override
    @Transactional(readOnly = true)
    public ListReviewResponsesWithCompanyResponse getAllReviewsByCompany(Long companyId) {
            CompanyResponse companyResponse = getCompanyById(companyId);
            //Handler Condition About Existing Company handle in Company Service
            List<ReviewResponse> reviewResponses = reviewRepository.findAllByCompanyId(companyId).stream().map(this::mapToResponse).toList();
            return new ListReviewResponsesWithCompanyResponse(companyResponse, reviewResponses);
    }


    @Override
    @Transactional
    public ReviewResponse addReviewForCompany(ReviewRequest request) {
        CompanyResponse companyResponse = getCompanyById(request.getCompanyId());
        //Handler Condition About Existing Company handle in Company Service
        Review review = mapToEntity(request);
        Review savedReview = reviewRepository.save(review);
        return mapToResponse(savedReview);
    }


    @Override
    @Transactional(readOnly = true)
    public ReviewResponseWithCompanyResponse getReviewByIdAndCompanyId(Long reviewId, Long companyId) {
        CompanyResponse companyResponse = getCompanyById(companyId);
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if(reviewOptional.isEmpty() || !companyResponse.getId().equals(reviewOptional.get().getCompanyId())){
            throw new ReviewNotFoundException("Review with id" + reviewId + " not found for company with id" + companyId);
        }
        return new ReviewResponseWithCompanyResponse(companyResponse, mapToResponse(reviewOptional.get()));
    }


    @Override
    @Transactional
    public ReviewResponseWithCompanyResponse updateReview(Long reviewId, ReviewRequest request) {
        CompanyResponse companyResponse = getCompanyById(request.getCompanyId());
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if(reviewOptional.isEmpty() || !companyResponse.getId().equals(reviewOptional.get().getCompanyId())){
            throw new ReviewNotFoundException("Review with id" + reviewId + " not found for company with id" + request.getCompanyId());
        }
        Review review = reviewOptional.get();
        updateFromRequest(request, review);
        Review updatedReview = reviewRepository.save(review);
        return new ReviewResponseWithCompanyResponse(companyResponse, mapToResponse(updatedReview));
    }


    @Override
    @Transactional
    public boolean deleteReview(Long companyId, Long reviewId) {
        CompanyResponse companyResponse = getCompanyById(companyId);
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if(reviewOptional.isEmpty() || !companyResponse.getId().equals(reviewOptional.get().getCompanyId())){
            throw new ReviewNotFoundException("Review with id" + reviewId + " not found for company with id" + companyId);
        }
        reviewRepository.delete(reviewOptional.get());
        return true;
    }


    private Review mapToEntity(ReviewRequest request) {
        return Review.builder()
                .description(request.getDescription())
                .rating(request.getRating())
                .companyId(request.getCompanyId())
                .build();
    }


    private ReviewResponse mapToResponse(Review review) {
        return ReviewResponse.builder()
                .companyId(review.getCompanyId())
                .description(review.getDescription())
                .rating(review.getRating())
                .id(review.getId())
                .build();
    }


    private Review updateFromRequest(ReviewRequest request, Review review) {
        review.setCompanyId(request.getCompanyId());
        review.setRating(request.getRating());
        review.setDescription(request.getDescription());


        return review;
    }


    private CompanyResponse getCompanyById(Long companyId) {
        try {
            CompanyResponse companyResponse = companyClient.getCompanyById(companyId);
            return companyResponse;
        } catch (FeignException e) {
            String errorJson = e.contentUTF8();
            ErrorResponse errorResponse = parseError(errorJson);
            if (errorResponse.getErrorCode().equals("CompanyNotFoundException")) {
                throw new CompanyNotFoundException("Company with id " + companyId + " not found");
            }
            throw new RuntimeException("Error from company-service: " + errorResponse.getMessage());
        }
    }


    private ErrorResponse parseError(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, ErrorResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось разобрать ошибку от company-service: " + json);
        }
    }
}
