package ru.geekbrains.supershop.services;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.persistence.entities.Review;
import ru.geekbrains.supershop.persistence.entities.Shopuser;
import ru.geekbrains.supershop.persistence.repositories.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Optional<List<Review>> getReviewsByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }

    public Optional<List<Review>> getReviewsByShopuser(Shopuser shopuser) {
        return reviewRepository.findByShopuser(shopuser);
    }

    public Optional<Review> getReviewById(UUID id){
        return reviewRepository.findById(id);
    }

    public UUID moderate(UUID id, String mod){
        boolean isModerated = mod.equals("true");
        Review review = reviewRepository.findById(id).orElse(new Review());
        review.setModerated(isModerated);
        reviewRepository.save(review);
        return review.getProduct().getId();
    }

    @Transactional
    public void save(Review review) {
        reviewRepository.save(review);
    }

}