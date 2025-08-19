package com.ddos.reviewMS.review_ms.review.repository;

import com.ddos.reviewMS.review_ms.review.dto.response.ReviewResponse;
import com.ddos.reviewMS.review_ms.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByIdAndCompanyId(Long reviewId, Long companyId);

    List<Review> findAllByCompanyId(Long id);
}
