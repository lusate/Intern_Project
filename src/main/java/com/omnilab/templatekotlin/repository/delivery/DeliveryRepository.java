package com.omnilab.templatekotlin.repository.delivery;

import com.omnilab.templatekotlin.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
