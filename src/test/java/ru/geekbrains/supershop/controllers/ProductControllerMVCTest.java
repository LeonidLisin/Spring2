package ru.geekbrains.supershop.controllers;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerMVCTest {

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
    public void setUp(){
    }

    @Test
    public void mustReturnRewiew() throws Exception {

        ReviewPojo reviewPojoMock = new ReviewPojo();
        reviewPojoMock.setProductId(null);
        reviewPojoMock.setCaptchaCode("unuin");
        reviewPojoMock.setCommentary("hello");

        ObjectMapper mapper = new ObjectMapper();
        String jsonReviewPojo = mapper.writeValueAsString(reviewPojoMock);


        mockMvc
                .perform(post("/reviews").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReviewPojo))
                .andExpect(status().isAccepted());
    }
}
