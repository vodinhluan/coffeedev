package com.coffeedev.review;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeedev.common.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
