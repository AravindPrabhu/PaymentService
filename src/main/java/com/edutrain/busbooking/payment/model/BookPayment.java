package com.edutrain.busbooking.payment.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class BookPayment  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5445968967517701748L;
	
	
	private String bookingNo;
	private String busNo;
	private String passengerId;
	private String passengerName;
	private String price;
	private String noOfSeats;

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public String getBusNo() {
		return busNo;
	}

	public void setBusNo(String busNo) {
		this.busNo = busNo;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNoOfSeats() {
		return noOfSeats;
	}

	public void setNoOfSeats(String noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	@Override
	public String toString() {
		return "BookPayment [bookingNo=" + bookingNo + ", busNo=" + busNo + ", passengerId=" + passengerId
				+ ", passengerName=" + passengerName + ", price=" + price + ", noOfSeats=" + noOfSeats + "]";
	}

	
}
