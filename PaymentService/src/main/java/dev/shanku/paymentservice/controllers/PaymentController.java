package dev.shanku.paymentservice.controllers;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import dev.shanku.paymentservice.dtos.GeneratePaymentLinkRequestDto;
import dev.shanku.paymentservice.services.PaymentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private PaymentService paymentService;
    public PaymentController(@Qualifier("stripe") PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public String generatePaymentLink(@RequestBody GeneratePaymentLinkRequestDto generatePaymentLinkRequestDto) throws RazorpayException, StripeException {

        return paymentService.generatePaymentLink(generatePaymentLinkRequestDto.orderId);
    }

    @PostMapping("/webhook")//PG will call this api on change of payment status
    public void handleWebHook(@RequestBody Object object){
        //here is where you decide what you want to do
        //you can go to razorpay dashboard and set these webhooks
    }
}
