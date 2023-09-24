package com.edutrain.busbooking.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.edutrain.busbooking.payment.model.PaymentModelWrapper;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentModelWrapper,String> {

}
