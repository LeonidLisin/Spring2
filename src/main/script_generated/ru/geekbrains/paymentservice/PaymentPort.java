package ru.geekbrains.paymentservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.2.1
 * 2020-04-12T15:16:56.284+03:00
 * Generated source version: 3.2.1
 * 
 */
@WebService(targetNamespace = "http://www.geekbrains.ru/PaymentService", name = "PaymentPort")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface PaymentPort {

    @WebMethod
    @WebResult(name = "getPaymentResponse", targetNamespace = "http://www.geekbrains.ru/PaymentService", partName = "getPaymentResponse")
    public GetPaymentResponse getPayment(
        @WebParam(partName = "getPaymentRequest", name = "getPaymentRequest", targetNamespace = "http://www.geekbrains.ru/PaymentService")
        GetPaymentRequest getPaymentRequest
    );
}
