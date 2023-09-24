package com.edutrain.busbooking.payment.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("PaymentDtls")
public class PaymentModelWrapper {
	
	@Id
	private String paymentNo;
	private PaymentModel paymentModel;
	
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public PaymentModel getPaymentModel() {
		return paymentModel;
	}
	public void setPaymentModel(PaymentModel paymentModel) {
		this.paymentModel = paymentModel;
	}
	
	

}
