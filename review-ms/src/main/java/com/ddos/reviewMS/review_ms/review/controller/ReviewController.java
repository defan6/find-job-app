package com.ddos.reviewMS.review_ms.review.controller;


import com.ddos.reviewMS.review_ms.review.dto.ListReviewResponsesWithCompanyResponse;
import com.ddos.reviewMS.review_ms.review.dto.ReviewResponseWithCompanyResponse;
import com.ddos.reviewMS.review_ms.review.dto.request.ReviewRequest;
import com.ddos.reviewMS.review_ms.review.dto.response.ReviewResponse;
import com.ddos.reviewMS.review_ms.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;



    @GetMapping
    public ResponseEntity<ListReviewResponsesWithCompanyResponse> getAllReviewsByCompany(@RequestParam("companyId") Long id){
        return ResponseEntity.ok(reviewService.getAllReviewsByCompany(id));
    }


    @PostMapping
    public ResponseEntity<ReviewResponse> addReviewForCompany(@RequestBody ReviewRequest request){
        ReviewResponse response = reviewService.addReviewForCompany(request);
        return ResponseEntity.created(URI.create("/reviews" + response.getId())).body(response);
    }


    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseWithCompanyResponse> getReviewAboutCompanyById(@RequestParam("companyId") Long companyId,
                                                                                       @PathVariable("reviewId") Long reviewId){
        return ResponseEntity.ok(reviewService.getReviewByIdAndCompanyId(reviewId, companyId));
    }


    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseWithCompanyResponse> updateReview(@PathVariable("reviewId") Long reviewId,
                                                       @RequestBody ReviewRequest request){
        return ResponseEntity.ok(reviewService.updateReview(reviewId, request));
    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@RequestParam("companyId") Long companyId,
                                             @PathVariable("reviewId") Long reviewId){
        boolean isDeleted = isDeleted = reviewService.deleteReview(companyId, reviewId);
        return isDeleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
