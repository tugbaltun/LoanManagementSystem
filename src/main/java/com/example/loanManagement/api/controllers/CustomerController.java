package com.example.loanManagement.api.controllers;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.loanManagement.business.abstracts.ICustomerService;
import com.example.loanManagement.entities.concretes.Customer;
import com.example.loanManagement.entities.concretes.Loan;
import com.example.loanManagement.entities.concretes.PaymentBill;
import com.example.loanManagement.entities.concretes.PaymentLoan;
import com.example.loanManagement.entities.concretes.PhoneBill;


@RestController
@RequestMapping("api/v1/")
public class CustomerController {
	
	private ICustomerService customerService;

	@Autowired
	public CustomerController(ICustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping("/customer/IDNumber/{id}")
	public ResponseEntity<Customer> getByIDNumber(@PathVariable(value="id") String identityNumber){
		return Optional.ofNullable(customerService.getCustomerByIDNumber(identityNumber))
				.map(customer -> ok()
						.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
						.body(customer))
				.orElse(notFound().build());
	}
	
	@GetMapping("/customer/id/{id}")
	public ResponseEntity<Customer> getById(@PathVariable(value="id") Long customerId){
		return Optional.ofNullable(customerService.getCustomerById(customerId))
				.map(customer -> ok()
									.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
									.body(customer))
				.orElse(notFound().build());
	}
	
	@GetMapping("/customer/phonebill/{id}")
	public ResponseEntity<PhoneBill> getPhoneBill(@PathVariable(value="id") Long contractId){
		return Optional.ofNullable(customerService.getPhoneBill(contractId))
				.map(phoneBill -> ok()
									.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
									.body(phoneBill))
				.orElse(notFound().build());
	}
	
	@GetMapping("/customer/paymentbill/{id}")
	public ResponseEntity<List<PaymentBill>> getPaymentBill(@PathVariable(value="id") Long customerId){
		return Optional.ofNullable(customerService.getPaymentBill(customerId))
				.map(paymentBill -> ok()
									.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
									.body(paymentBill))
				.orElse(notFound().build());
	}
	
	
	@GetMapping("/customer/paymentloan/{id}")
	public ResponseEntity<List<PaymentLoan>> getPaymentLoan(@PathVariable(value="id") Long customerId){
		return Optional.ofNullable(customerService.getPaymentLoan(customerId))
				.map(paymentLoan -> ok()
									.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
									.body(paymentLoan))
				.orElse(notFound().build());
	}

	@PutMapping("/customer/loan/{id}")
	public ResponseEntity<Map<String, Loan>> updateCreditLoan(@PathVariable(value="id") Long contractId,  
			@RequestBody Loan loan){
		return ok()
				.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
				.body(customerService.updateLoan(contractId, loan));
	}
	

	

	

}
