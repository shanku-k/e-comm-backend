package dev.shanku.paymentservice.services;

import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.stereotype.Service;

@Service("stripe")
public class StripePaymentGateway implements PaymentService{
    @Override
    public String generatePaymentLink(Long orderId) throws RazorpayException, StripeException {
        Stripe.apiKey = "sk_test_51QU4R8LTG1Eg931sh53DOj9CpxVEqSj5LyVXo7ksmAjokvNmEL1i1WwYNp8lWzCyv0Xb5ckc8gHURSu53aOqCJL200C5AUQ2P1";

        Stripe.apiKey = "sk_test_51QU4R8LTG1Eg931sh53DOj9CpxVEqSj5LyVXo7ksmAjokvNmEL1i1WwYNp8lWzCyv0Xb5ckc8gHURSu53aOqCJL200C5AUQ2P1";

        PriceCreateParams priceCreateParams =
                PriceCreateParams.builder()
                        .setCurrency("INR")
                        .setUnitAmount(10000L) //100 rs.
                        .setRecurring(
                                PriceCreateParams.Recurring.builder()
                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                        .build()
                        )
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                        )
                        .build();

        Price price = Price.create(priceCreateParams);

        PaymentLinkCreateParams stripeParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .build();

        PaymentLink paymentLink = PaymentLink.create(stripeParams);
        return paymentLink.toString();
    }
}
