package ru.geekbrains.supershop.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import ru.geekbrains.paymentservice.Payment;
import ru.geekbrains.supershop.beans.Cart;
import ru.geekbrains.supershop.persistence.entities.Shopuser;
import ru.geekbrains.supershop.services.ProductService;
import ru.geekbrains.supershop.services.ReviewService;
import ru.geekbrains.supershop.services.ShopuserService;
import ru.geekbrains.supershop.utils.CaptchaGenerator;
import ru.geekbrains.supershop.utils.Validators;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final Cart cart;
    private final CaptchaGenerator captchaGenerator;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final ShopuserService shopuserService;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ApiOperation(value = "Отображение главной страницы магазина")
    public String index(Model model, @RequestParam(required = false) Integer category) {
        model.addAttribute("cart", cart.getCartRecords());
        model.addAttribute("products", productService.findAll(category));
        return "index";
    }

    @GetMapping("/admin")
    @ApiOperation(value = "Возвращает админскую страницу")
    public String adminPage(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/";
        }

        model.addAttribute("products", productService.findAll(null));

        return "admin";
    }

    @GetMapping("/profile")
    @ApiOperation(value = "Отображение страницы профиля пользователя")
    public String profilePage(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/";
        }

        Shopuser shopuser = shopuserService.findByPhone(principal.getName());

        model.addAttribute("reviews", reviewService.getReviewsByShopuser(shopuser).orElse(new ArrayList<>()));
        model.addAttribute("shopuser", shopuser);

        return "profile";
    }

    @GetMapping(value = "/captcha", produces = MediaType.IMAGE_PNG_VALUE)
    @ApiOperation(value = "Возвращает картинку капчи")
    public @ResponseBody byte[] captcha(HttpSession session) {
        try {
            BufferedImage img = captchaGenerator.getCaptchaImage();
            session.setAttribute("captchaCode", captchaGenerator.getCaptchaString());
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ImageIO.write(img, "png", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/checkout")
    @ApiOperation(value = "Возвращает страницу оплаты")
    public String proceedToCheckout(String paymentId, Model model){

        Payment payment = cart.getPayments()
                .stream()
                .filter(s -> s.getId() == Integer.valueOf(paymentId))
                .collect(Validators.toSingleton());
        cart.setPayment(payment);
        model.addAttribute("cart", cart);
        return "checkout";
    }

}