package ru.geekbrains.paymentservice.repository;

import org.springframework.stereotype.Component;

import ru.geekbrains.soap.payment.Payment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentRepository {

    public List<Payment> getPayments(String country, double price) {

        Payment payment1 = new Payment();
        payment1.setId(1);
        payment1.setName("PayPal");
        payment1.setCountry("USA");
        payment1.setPrice(100);

        Payment payment2 = new Payment();
        payment2.setId(2);
        payment2.setName("GooglePay");
        payment2.setCountry("USA");
        payment2.setPrice(100);

        Payment payment3 = new Payment();
        payment3.setId(3);
        payment3.setName("WebMoney");
        payment3.setCountry("Russia");
        payment3.setPrice(100);

        Payment payment4 = new Payment();
        payment4.setId(4);
        payment4.setName("YandexMoney");
        payment4.setCountry("Russia");
        payment4.setPrice(100);

        return new ArrayList<Payment>() {{
            add(payment1);
            add(payment2);
            add(payment3);
            add(payment4);
        }}.stream().filter(
            payment -> payment.getCountry().contains(country)
        ).collect(Collectors.toList())
                .stream().filter(
                        payment -> payment.getPrice()==price
                ).collect(Collectors.toList());
    }

}