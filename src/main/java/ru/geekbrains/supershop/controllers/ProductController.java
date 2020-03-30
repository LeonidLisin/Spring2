package ru.geekbrains.supershop.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.geekbrains.supershop.exceptions.ProductNotFoundException;
import ru.geekbrains.supershop.persistence.entities.Image;
import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.persistence.entities.Review;
import ru.geekbrains.supershop.persistence.entities.Shopuser;
import ru.geekbrains.supershop.persistence.pojo.ProductPojo;
import ru.geekbrains.supershop.persistence.pojo.ReviewPojo;
import ru.geekbrains.supershop.services.ImageService;
import ru.geekbrains.supershop.services.ProductService;
import ru.geekbrains.supershop.services.ReviewService;
import ru.geekbrains.supershop.services.ShopuserService;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ImageService imageService;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final ShopuserService shopuserService;

    @GetMapping("/{id}")
    public String getOneProduct(Model model, @PathVariable String id) throws ProductNotFoundException {

        Product product = productService.findOneById(UUID.fromString(id));
        List<Review> reviews = reviewService.getReviewsByProduct(product).orElse(new ArrayList<>());
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        return "product";
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable String id, @RequestParam String type) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = imageService.loadFileAsResource(id, type);
        if (bufferedImage != null) {
            ImageIO.write(bufferedImage,"png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } else {
            return new byte[0];
        }
    }

    @PostMapping(path = "/addproduct")
    public String addProduct(@RequestParam("image") MultipartFile image, ProductPojo productPojo, HttpSession session) throws IOException {
        Image img = imageService.uploadImage(image, productPojo.getTitle());
        return productService.save(productPojo, img);
    }

    @PostMapping(path = "/reviews")
    public String addReview(@RequestParam("image") MultipartFile image, ReviewPojo reviewPojo, HttpSession session, Principal principal) throws ProductNotFoundException, IOException {

        Product product = productService.findOneById(reviewPojo.getProductId());
        Shopuser shopuser = shopuserService.findByPhone(principal.getName());
        Image img = imageService.uploadImage(image, image.getOriginalFilename().split("\\.")[0]);
        Review review = Review.builder()
            .commentary(reviewPojo.getCommentary())
            .product(product)
            .shopuser(shopuser)
            .moderated(false)
            .image(img)
        .build();

        reviewService.save(review);

        return "redirect:/products/" + product.getId();
    }

    @GetMapping(path = "/reviewmod/{id}")
    public String moderateReview(@PathVariable UUID id, @RequestParam String isModerated){
        return "redirect:/products/" + reviewService.moderate(id, isModerated);
    }

}