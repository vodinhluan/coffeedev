package com.coffeedev.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.coffeedev.common.entity.Review;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/submitReview")
    public String submitReview(@ModelAttribute Review review) {
        reviewService.saveReview(review);
        return "redirect:/"; // Chuyển hướng về trang chủ hoặc trang xác nhận
    }
}
