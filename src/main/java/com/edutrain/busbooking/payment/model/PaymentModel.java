package com.edutrain.busbooking.payment.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("PaymentDtls")
public class PaymentModel {
	
	private String paymentNo;
	private String bookingNo;
	private Date dateOfPayment;
	private String seatsBooked;
	private String paymentAmount;
		
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public String getBookingNo() {
		return bookingNo;
	}
	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}
	public Date getDateOfPayment() {
		return dateOfPayment;
	}
	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}
	public String getSeatsBooked() {
		return seatsBooked;
	}
	public void setSeatsBooked(String seatsBooked) {
		this.seatsBooked = seatsBooked;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	

}
