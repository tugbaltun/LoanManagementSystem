package com.example.loanManagement.business.concretes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.loanManagement.business.abstracts.ICustomerService;
import com.example.loanManagement.dataAcccess.concretes.CustomerRepository;
import com.example.loanManagement.dataAcccess.concretes.LoanRepository;
import com.example.loanManagement.dataAcccess.concretes.PaymentBillRepository;
import com.example.loanManagement.dataAcccess.concretes.PaymentLoanRepository;
import com.example.loanManagement.dataAcccess.concretes.PhoneBillRepository;
import com.example.loanManagement.entities.concretes.Customer;
import com.example.loanManagement.entities.concretes.Loan;
import com.example.loanManagement.entities.concretes.PaymentBill;
import com.example.loanManagement.entities.concretes.PaymentLoan;
import com.example.loanManagement.entities.concretes.PhoneBill;
import com.example.loanManagement.exception.ApiErrorException;

@Service
public class CustomerManager implements ICustomerService{

	@Autowired private CustomerRepository customerRepository;
	@Autowired private PhoneBillRepository phoneBillRepository;
	@Autowired private PaymentBillRepository paymentBillRepository;
	@Autowired private LoanRepository loanRepository;
	@Autowired private PaymentLoanRepository paymentLoanRepository;
	
	@Override
	public Customer getCustomerByIDNumber(String identityNumber) {
		if(customerRepository.findCustomerByIDNumber(identityNumber) == null) {
			throw new ApiErrorException("No customer with identity number:" + identityNumber);
		}
		return customerRepository.findCustomerByIDNumber(identityNumber);
	}

	@Override
	public Customer getCustomerById(Long customerId) {
		return customerRepository
				.findById(customerId)
				.orElseThrow(() -> new ApiErrorException("No customer with id:" + customerId)) ;
	}

	@Override
	public PhoneBill getPhoneBill(Long contractId) {
		return phoneBillRepository
				.findById(contractId)
				.orElseThrow(() -> new ApiErrorException("No contract with id:" + contractId)) ;
	}

	@Override
	public List<PaymentBill> getPaymentBill(Long contractId) {
		List<PaymentBill> paymentBillList = paymentBillRepository.findAllByContractId(contractId);
		if(paymentBillList.isEmpty()) {
			throw new ApiErrorException("Payment Bill cannot found in the cache");
		}
		return paymentBillList;
	}



	@Override
	public List<PaymentLoan> getPaymentLoan(Long contractId) {
		List<PaymentLoan> paymentBillList = paymentLoanRepository.findAllByContractId(contractId);
		if(paymentBillList.isEmpty()) {
			throw new ApiErrorException("Payment Loan cannot found in the cache");
		}
		return paymentBillList;
	}
	
	@Override
	public Map<String, Loan> updateLoan(Long contractId, Loan loan) {
		Loan loanToUpdate = loanRepository.findById(contractId)
				.orElseThrow(()-> new ApiErrorException("No loan with id: "+ contractId));
		loanToUpdate.setCustomerStatus(loan.getCustomerStatus());
		Map<String, Loan> result = new HashMap<String, Loan>();
		result.put("Kredi başvuru durumu güncellendi ve yetkili onayına gönderildi.", loanToUpdate);
		return result;
	}



}
