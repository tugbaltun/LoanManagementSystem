package com.example.loanManagement.business.concretes;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.loanManagement.business.abstracts.IBankerService;
import com.example.loanManagement.dataAcccess.concretes.CustomerRepository;
import com.example.loanManagement.dataAcccess.concretes.LoanRepository;
import com.example.loanManagement.dataAcccess.concretes.PaymentBillRepository;
import com.example.loanManagement.dataAcccess.concretes.PhoneBillRepository;
import com.example.loanManagement.entities.concretes.Customer;
import com.example.loanManagement.entities.concretes.Loan;
import com.example.loanManagement.entities.concretes.PaymentBill;
import com.example.loanManagement.entities.concretes.PhoneBill;
import com.example.loanManagement.exception.ApiErrorException;

@Service
public class BankerManager implements IBankerService {

	@Autowired private LoanRepository loanRepository;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private PhoneBillRepository phoneBillRepository;
	@Autowired private PaymentBillRepository paymentBillRepository;
	
	@Override
	public Loan getLoanById(Long contractId) {
		return loanRepository
				.findById(contractId)
				.orElseThrow(() -> new ApiErrorException("No contract with id:" + contractId)) ;
	}

	@Override
	public Map<String, Loan> addCreditToLoan(Loan loan) {	
		loanRepository.save(loan);
		Map<String, Loan> result = new HashMap<>();
		result.put("Kredi başvurunuz alındı ve yetkili onayına gönderildi!", loan);
		return result;
	}

	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> customerList = customerRepository.findAll();
		if(customerList.isEmpty()) {
			throw new ApiErrorException("Customer cannot found in the cache");
		}
		return customerList;
	}

	@Override
	public Customer getCustomerById(Long customerId) {
		return customerRepository
				.findById(customerId)
				.orElseThrow( ()-> new ApiErrorException("No customer with id:"+customerId));
	}
	
	@Override
	public Customer getCustomerByIdentityNumber(String identityNumber) {
		 if(customerRepository.findCustomerByIDNumber(identityNumber)==null) {
			 throw new ApiErrorException("No customer with id:"+identityNumber);
		 }	
		return customerRepository.findCustomerByIDNumber(identityNumber);
	}

	@Override
	public Map<String, Customer> addCustomer(Customer customer) {
		customerRepository.save(customer);
		Map<String, Customer> result = new HashMap<>();
		result.put("Kaydınız yapıldı!", customer);
		return result;
	}

	@Override
	public Map<String, Customer> updateCustomer(Long customerId, Customer customer) {
		Customer customerToUpdate = customerRepository
				.findById(customerId)
				.orElseThrow(()-> new ApiErrorException("No customer with id:"+customerId));
		customerToUpdate.setAddress(customer.getAddress());
		customerToUpdate.setBirthDate(customer.getBirthDate());
		customerToUpdate.setCustomerType(customer.getCustomerType());
		customerToUpdate.setFirstName(customer.getFirstName());
		customerToUpdate.setIdentificationPath(customer.getIdentificationPath());
		customerToUpdate.setIdentityNumber(customer.getIdentityNumber());
		customerToUpdate.setLastName(customer.getLastName());
		customerToUpdate.setLegalStatus(customer.getLegalStatus());
		customerToUpdate.setPhoneNumber(customer.getPhoneNumber());
		customerToUpdate.setSubscriptionDate(customer.getSubscriptionDate());
		
		customerRepository.save(customerToUpdate);
		Map<String, Customer> result = new HashMap<>();
		result.put("Müşteri bilgileri güncellendi!", customer);
		return result;
	}

	@Override
	public Map<String, Boolean> deleteCustomer(Long customerId) {
		return customerRepository
				.findById(customerId)
				.map(
						customer->{
							customerRepository.delete(customer);
							Map<String, Boolean> response = new HashMap<>();
							response.put("The given customer is deleted", Boolean.TRUE);
							return response;
						})
				.orElseThrow(()-> new ApiErrorException("No customer with id:"+customerId));
	}

	@Override
	public PhoneBill getPhoneBill(Long customerId) {
		if(phoneBillRepository.findByCustomerId(customerId)==null) {
			throw new ApiErrorException("No contract with id:"+customerId);
		}
		return phoneBillRepository.findByCustomerId(customerId);
	}

	@Override
	public Map<String, PhoneBill> addPhoneBill(PhoneBill phoneBill) {
		ZoneId zonedId = ZoneId.of( "Europe/Istanbul" );
		LocalDate today = LocalDate.now( zonedId ).plusMonths(1) ;
		phoneBillRepository.save(phoneBill);
		PaymentBill paymentBill = new PaymentBill();
		paymentBill.setContractId(phoneBill.getContractId());
		paymentBill.setDateOfPayment(null);
		paymentBill.setDueDate(java.sql.Date.valueOf(today));
		paymentBill.setAmountOfPayment(phoneBill.getMonthlyBill());
		paymentBill.setStatus("Ödenmedi");
		paymentBillRepository.save(paymentBill);
		Map<String, PhoneBill> result = new HashMap<>();
		result.put("Fatura bilgileriniz kaydedildi!", phoneBill);
		return result;
	}



}
