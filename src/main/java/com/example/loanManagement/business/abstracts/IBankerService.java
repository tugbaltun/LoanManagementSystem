package com.example.loanManagement.business.abstracts;

import java.util.List;
import java.util.Map;

import com.example.loanManagement.entities.concretes.Customer;
import com.example.loanManagement.entities.concretes.Loan;
import com.example.loanManagement.entities.concretes.PhoneBill;

public interface IBankerService {

	Loan getLoanById(Long contractId); //Kredi tablosunu getir
	Map<String, Loan> addCreditToLoan(Loan loan); //Müşterinin kredi başvurusu
	List<Customer> getAllCustomer(); //Tüm müşterileri getir
	Customer getCustomerById(Long customerId);//Beliştilen müşteriyi getir
	Customer getCustomerByIdentityNumber(String identityNumber);//Beliştilen müşteriyi getir
	Map<String, Customer> addCustomer(Customer customer); //Müşteri oluştur
	Map<String, Customer> updateCustomer(Long customerId, Customer customer);//Müşteri bilgilerini güncelle
	Map<String, Boolean> deleteCustomer(Long customerId);//Müşteriyi sil
	PhoneBill getPhoneBill(Long customerId); //Müşterinin fatura bilgilerini getir
	Map<String, PhoneBill> addPhoneBill(PhoneBill phoneBill);//Müşteriye hat tanımla
}
