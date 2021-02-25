package com.example.loanManagement.api.controllers;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.notFound;
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

import com.example.loanManagement.business.abstracts.IAuthorizedService;
import com.example.loanManagement.entities.concretes.Loan;

@RestController
@RequestMapping("/api/v1")
public class AuhorizedController {
	
	@Autowired private IAuthorizedService authorizedService;
	
	@GetMapping("/authorized")
	public ResponseEntity<List<Loan>> getAllPendingApproval(){
		return ok()
				.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
				.body(authorizedService.getAllPendingApproval());
	}
	
	@GetMapping("/authorized/{id}")
	public ResponseEntity<Loan> getLoanById(@PathVariable(value="id") Long contractId ){
		return Optional.ofNullable(authorizedService.getLoanById(contractId))
				.map(loan -> ok()
						.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
						.body(loan))
				.orElse(notFound().build());
	}
	
	@GetMapping("/customer/loan/{id}")
	public ResponseEntity<Map<String, Loan>> getLoan(@PathVariable(value="id") Long contractId){
		return ok()
				.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
				.body(authorizedService.getLoanForCustomer(contractId));
	}
	
	@PutMapping("/authorized/{id}")
	public ResponseEntity<Map<String, Loan>> updateCreditLoan(@PathVariable(value="id") Long contractId,  
			@RequestBody Loan loan){
		return ok()
				.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
				.body(authorizedService.updateLoan(contractId, loan));
	}
	
	@GetMapping("/authorized/{customerId}/{amount}")
	public Map<String, Boolean>  getCreditStatus
			(@PathVariable(value="customerId") Long contractId, 
			@PathVariable(value="amount") Double amountOfCredit ){
		return authorizedService.checkCreditStatus(contractId, amountOfCredit);
	}
	
	

}
