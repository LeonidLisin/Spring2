package ru.geekbrains.supershop.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.geekbrains.supershop.exceptions.InternalServerErrorException;
import ru.geekbrains.supershop.exceptions.ProductNotFoundException;

@Slf4j
@ControllerAdvice
public class ShopExceptionHandler {

    // TODO ДЗ - оформить страницу ошибки 404

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFoundException(Model model, final ProductNotFoundException ex) {
        //log.error("Product not found thrown", ex);
        model.addAttribute("exception", "Product not found");
        return "error";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException.class)
    public String handleInternalServerEror(Model model, final InternalServerErrorException ex) {
        //log.error("Internal server error", ex);
        model.addAttribute("exception", "Internal server error");
        return "error";
    }

}