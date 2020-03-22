package ru.geekbrains.supershop.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.services.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShopController {

    @Autowired
    private ProductService productService;
//это не JSON
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index(Model model, @RequestParam(required = false) Integer category,
                        @RequestParam(required = false) Boolean availability) {

        //TODO сделать фильтр, который будет выводить фильтровать продукты по доступности. Выводить все продукты, но при этом указывать какие из них в наличие, а какие нет.

        List<Product> products = productService.findProducts(category, availability);
        model.addAttribute("products", products);

        return "index";
    }

}