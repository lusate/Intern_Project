package com.omnilab.templatekotlin.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    private Long deliveryId;
    private Long orderId;
    private DeliveryStatus status;
}
