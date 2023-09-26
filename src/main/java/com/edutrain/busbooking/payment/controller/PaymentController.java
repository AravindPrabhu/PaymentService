package com.edutrain.busbooking.payment.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutrain.busbooking.payment.model.BookPayment;
import com.edutrain.busbooking.payment.model.PaymentModel;
import com.edutrain.busbooking.payment.model.PaymentModelWrapper;
import com.edutrain.busbooking.payment.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	PaymentModel paymentModel;

	@Autowired
	PaymentModelWrapper paymentModelWrapper;

	@Autowired
	BookPayment bookPayment;

	@Autowired
	private final PaymentRepository paymentRepository;

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	public PaymentController(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	@GetMapping("/getallpayments")
	public List<String> getAllPayments() {

		List<PaymentModel> PaymentModelList = new ArrayList<PaymentModel>();
		List<String> stringRouteList = new ArrayList<String>();

		paymentRepository.findAll().forEach((paymentModelWrapper) -> {
			PaymentModelList.add(paymentModelWrapper.getPaymentModel());
		});

		PaymentModelList.forEach((paymentModel) -> {
			stringRouteList.add("PaymentNo: " + paymentModel.getPaymentNo() + ",BookingNo: "
					+ paymentModel.getDateOfPayment() + ",DateOfPayment: " + paymentModel.getDateOfPayment());
		});

		return stringRouteList;

	}

	@JmsListener(destination = "BookingToPayment")
	public String ReceiveBookingAndProcessPayment(Object obj) {

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Message Received" + obj);
		bookPayment = (BookPayment) obj;

		// Add business logic
		paymentModel.setPaymentAmount(String
				.valueOf(Integer.parseInt(bookPayment.getNoOfSeats()) * Integer.parseInt(bookPayment.getPrice())));
		paymentModel.setBookingNo(bookPayment.getBookingNo());
		paymentModel.setDateOfPayment(new Date("2023-09-23"));
		paymentModel.setSeatsBooked(bookPayment.getNoOfSeats());
		addPayment(paymentModel);
		
		// Send message to inventory Service
		SendMessageToInventoryService(bookPayment);

		return null;

	}

	public String SendMessageToInventoryService(BookPayment bookPayment) {
		jmsMessagingTemplate.convertAndSend("PaymentToInventory", bookPayment);

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonString;
		try {
			
			jsonString = ow.writeValueAsString(paymentModel);
			return jsonString;
		} catch (JsonProcessingException e) {

			e.printStackTrace();
			return "Exception occured";
		}

	}

	@PostMapping("/addpayment")
	public String addPayment(@RequestBody PaymentModel paymentModel) {

		String paymentNumber = paymentModel.getPaymentNo();
		System.out.println("paymentNumber in addPayment is " + paymentNumber);

		// PaymentModelWrapper paymentModelWrapper= new PaymentModelWrapper();
		paymentModelWrapper.setPaymentNo(paymentNumber);
		paymentModelWrapper.setPaymentModel(paymentModel);

		try {
			PaymentModelWrapper retValue = paymentRepository.save(paymentModelWrapper);

			if (retValue != null) {
				return "Payment Added successfully";
			} else {
				return "There is an error in adding Payment";
			}
		} catch (Exception e) {

			return "There is an error in adding Payment";
		}

	}

	@GetMapping("/getpayments/{paymentNo}")
	public String getPaymentModel(@PathVariable String paymentNo) {

		Optional<PaymentModelWrapper> paymentModelWrapperRetValue = paymentRepository.findById(paymentNo);

		if (paymentModelWrapperRetValue.isPresent()) {

			paymentModelWrapper = paymentModelWrapperRetValue.get();
			paymentModel = paymentModelWrapper.getPaymentModel();
			String PaymentModelStr = "PaymentNo: " + paymentModel.getPaymentNo() + ",BookingNo: "
					+ paymentModel.getBookingNo() + ",DateOfPayment: " + paymentModel.getDateOfPayment();

			return PaymentModelStr;

		} else {

			return "Route Not found";
		}

	}

	@DeleteMapping("/deletepayment/{paymentNo}")
	public String deletePaymentModel(@PathVariable String paymentNo) {
		try {
			paymentRepository.deleteById(paymentNo);
			return "Payment Deleted successfully";
		} catch (Exception e) {
			return "Error while deletion of Payment";
		}

	}

	@PutMapping("/updatepayment")
	public String updatePayment(@RequestBody PaymentModel paymentModel) {

		String paymentNo = paymentModel.getPaymentNo();
		System.out.println("paymentNo in updatePayment is " + paymentNo);
		String RetValue = getPaymentModel(paymentNo);

		if (RetValue.equalsIgnoreCase("Payment Not found")) {
			return "Payment Not found,Please enter valid paymentNo";
		} else {

			// PaymentModelWrapper paymentModelWrapper= new PaymentModelWrapper();
			paymentModelWrapper.setPaymentNo(paymentNo);
			paymentModelWrapper.setPaymentModel(paymentModel);

			try {
				PaymentModelWrapper retValue = paymentRepository.save(paymentModelWrapper);

				if (retValue != null) {
					return "PaymentModel Updated successfully";
				} else {
					return "There is an error in updating PaymentModel";
				}
			} catch (Exception e) {

				return "There is an error in updating  PaymentModel";
			}
		}

	}

}
