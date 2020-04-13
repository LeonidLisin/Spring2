package ru.geekbrains.supershop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.geekbrains.supershop.persistence.entities.Country;
import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.persistence.entities.Review;
import ru.geekbrains.supershop.persistence.entities.Shopuser;
import ru.geekbrains.supershop.persistence.entities.enums.ProductCategory;
import ru.geekbrains.supershop.persistence.entities.enums.Role;
import ru.geekbrains.supershop.persistence.repositories.CountryRepository;
import ru.geekbrains.supershop.persistence.repositories.ProductRepository;
import ru.geekbrains.supershop.persistence.repositories.ShopuserRepository;
import ru.geekbrains.supershop.services.ImageService;
import ru.geekbrains.supershop.services.ProductService;
import ru.geekbrains.supershop.services.ReviewService;
import ru.geekbrains.supershop.services.ShopuserService;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReviewServiceTest {

    Product productMock;
    Shopuser shopuserMock;
    Review reviewMock;

    @Autowired
    ReviewService reviewService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShopuserRepository shopuserRepository;

    @Autowired
    CountryRepository countryRepository;

    @MockBean
    ImageService imageService;

    @Before
    public void setUp() {

        Country countryMock = new Country();
        countryMock.setId(1);
        countryMock.setCountryTitle("Mongolia");

        countryRepository.save(countryMock);

        this.productMock = Product.builder()
                .title("test")
                .added(new Date())
                .description("test")
                .price(10.0)
                .available(true)
                .image(null)
                .category(ProductCategory.DRINK)
                .madein(countryMock)
                .build();

        this.shopuserMock = Shopuser.builder()
                .phone("000")
                .password("1")
                .firstName("Aa")
                .lastName("Bb")
                .email("00@00.com")
                .role(Role.ROLE_ADMIN)
                .build();

        this.reviewMock = Review.builder()
                .product(productMock)
                .shopuser(shopuserMock)
                .commentary("hello")
                .approved(true)
                .build();

        productRepository.save(productMock);
        shopuserRepository.save(shopuserMock);
        reviewService.save(reviewMock);
    }

    @Test
    public void testReviewService(){
        assertEquals(Arrays.asList(reviewMock), reviewService.getReviewsByProduct(productMock).orElse(null));
    }

}
