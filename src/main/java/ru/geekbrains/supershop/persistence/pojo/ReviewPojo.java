package ru.geekbrains.supershop.persistence.pojo;

import lombok.Data;
import ru.geekbrains.supershop.persistence.entities.Image;

import java.util.UUID;

@Data
public class ReviewPojo {
    private UUID id;
    private String captchaCode;
    private String commentary;
    private UUID productId;
}