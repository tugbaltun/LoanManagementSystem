package com.example.loanManagement.api.controllers;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.loanManagement.business.abstracts.IBankerService;
import com.example.loanManagement.business.abstracts.ICustomerService;
import com.example.loanManagement.entities.concretes.Customer;
import com.example.loanManagement.entities.concretes.Loan;
import com.example.loanManagement.entities.concretes.PhoneBill;

@RestController
@RequestMapping("/api/v1")
public class BankerController {
	
	@Autowired private ICustomerService customerService;
	@Autowired private IBankerService bankerService;
	
	@GetMapping("/banker/loan/{id}")
	public ResponseEntity<Loan> getLoanById(@PathVariable(value="id") Long contractId ){
		return Optional.ofNullable(bankerService.getLoanById(contractId))
				.map(loan -> ok()
						.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
						.body(loan))
				.orElse(notFound().build());
	}
	
	@PostMapping("/banker/loan")
	public ResponseEntity<Map<String, Loan>> addCreditToLoan(@RequestBody Loan loan){
		return created(fromCurrentRequest().build().toUri())
				.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
				.body(bankerService.addCreditToLoan(loan));
	}
	
	@GetMapping("/banker/customer/id/{id}")
	public ResponseEntity<Customer> getById(@PathVariable(value="id") Long customerId){
		return Optional.ofNullable(customerService.getCustomerById(customerId))
				.map(customer -> ok()
									.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
									.body(customer))
				.orElse(notFound().build());
	}
	
	@GetMapping("/banker/customer/TC/{id}")
	public ResponseEntity<Customer> getById(@PathVariable(value="id") String identityNumber){
		return Optional.ofNullable(customerService.getCustomerByIDNumber(identityNumber))
				.map(customer -> ok()
									.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
									.body(customer))
				.orElse(notFound().build());
	}
	
	@GetMapping("/banker/customer")
	public ResponseEntity<List<Customer>> getAll(){
		return ok()
				.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
				.body(bankerService.getAllCustomer());
	}

	@PostMapping("/banker/customer")
	public ResponseEntity<Map<String, Customer>> addCustomer(@RequestBody Customer customer){
		return created(fromCurrentRequest().build().toUri())
				.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
				.body(bankerService.addCustomer(customer));
	}
	
	@PutMapping("/banker/customer/{id}")
	public ResponseEntity<Map<String, Customer>> updateCustomer(
			@PathVariable(value="id") Long customerId, @RequestBody Customer customer){
		return ok().header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
				.body(bankerService.updateCustomer(customerId, customer));
	}
	
	@DeleteMapping("/banker/customer/{id}")
	public ResponseEntity<Map<String, Boolean>> delete(
			@PathVariable(value="id") Long customerId, Customer customer){
		return ok().body(bankerService.deleteCustomer(customerId));
	}
	
	@GetMapping("/banker/phonebill/{id}")
	public ResponseEntity<PhoneBill> getPhoneBill(@PathVariable(value="id") Long customerId ){
		return Optional.ofNullable(bankerService.getPhoneBill(customerId))
				.map(phonebill -> ok()
						.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
						.body(phonebill))
				.orElse(notFound().build());
	}
	
	@PostMapping("/banker/phonebill")
	public ResponseEntity<Map<String, PhoneBill>> addPhoneBill(@RequestBody PhoneBill phoneBill){
		return created(fromCurrentRequest().build().toUri())
				.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
				.body(bankerService.addPhoneBill(phoneBill));
	}
}
