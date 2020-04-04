package ru.geekbrains.paymentservice.endpoint;

import ru.geekbrains.paymentservice.repository.PaymentRepository;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ru.geekbrains.soap.payment.GetPaymentRequest;
import ru.geekbrains.soap.payment.GetPaymentResponse;

@Endpoint
public class PaymentServiceEndpoint {

	private final String NAMESPACE = "http://www.geekbrains.ru/PaymentService";

	private final PaymentRepository paymentRepository;

	public PaymentServiceEndpoint(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	@PayloadRoot(namespace = NAMESPACE, localPart = "getPaymentRequest")
	@ResponsePayload
	public GetPaymentResponse getUser(@RequestPayload final GetPaymentRequest request) {
		GetPaymentResponse response = new GetPaymentResponse();
		response.getPayments().addAll(paymentRepository.getPayments(request.getCountry(), request.getPrice()));
		return response;
	}

}