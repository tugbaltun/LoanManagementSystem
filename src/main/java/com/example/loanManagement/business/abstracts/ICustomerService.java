package com.example.loanManagement.business.abstracts;

import java.util.List;
import java.util.Map;
import com.example.loanManagement.entities.concretes.Customer;
import com.example.loanManagement.entities.concretes.Loan;
import com.example.loanManagement.entities.concretes.PaymentBill;
import com.example.loanManagement.entities.concretes.PaymentLoan;
import com.example.loanManagement.entities.concretes.PhoneBill;

public interface ICustomerService {
	
	Customer getCustomerByIDNumber(String identityNumber);//TC'ye göre müşteri bilgilerini getir
	Customer getCustomerById(Long customerId);//ID'ye göre müşteri bilgilerini getir
	PhoneBill getPhoneBill(Long contractId); //Fatura bilgilerini getir
	List<PaymentBill> getPaymentBill(Long contractId);//Fatura ödeme bilgilerini getir
	List<PaymentLoan> getPaymentLoan(Long contractId);//Kredi ödeme bilgilerini getir
	Map<String, Loan> updateLoan(Long contractId, Loan loan );
}
