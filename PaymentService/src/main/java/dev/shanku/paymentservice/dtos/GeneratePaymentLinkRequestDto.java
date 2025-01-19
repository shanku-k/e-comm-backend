package dev.shanku.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratePaymentLinkRequestDto {
    public Long orderId;
}
