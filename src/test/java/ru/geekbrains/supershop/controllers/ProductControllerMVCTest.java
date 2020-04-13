package ru.geekbrains.supershop.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.supershop.persistence.pojo.ReviewPojo;
import ru.geekbrains.supershop.services.ImageService;
import ru.geekbrains.supershop.services.ProductService;
import ru.geekbrains.supershop.services.ReviewService;
import ru.geekbrains.supershop.services.ShopuserService;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerMVCTest {

    String jsonReviewPojo;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageServiceMock;

    @MockBean
    private ProductService productServiceMock;

    @MockBean
    private ReviewService reviewServiceMock;

    @MockBean
    private ShopuserService shopuserServiceMock;

    @Before
    public void setUp() throws JsonProcessingException {

        ReviewPojo reviewPojoMock = new ReviewPojo();
        reviewPojoMock.setProductId(new UUID(40,40));
        reviewPojoMock.setCaptchaCode("unuin");
        reviewPojoMock.setCommentary("hello");

        ObjectMapper mapper = new ObjectMapper();
        this.jsonReviewPojo = mapper.writeValueAsString(reviewPojoMock);
    }

    @Test
    public void mustReturnRewiew() throws Exception {

        mockMvc
                .perform(post("/reviews").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReviewPojo))
                .andExpect(status().isAccepted());
    }
}
