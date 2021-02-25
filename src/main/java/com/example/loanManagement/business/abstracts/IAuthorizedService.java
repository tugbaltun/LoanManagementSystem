package com.example.loanManagement.business.abstracts;

import java.util.List;
import java.util.Map;

import com.example.loanManagement.entities.concretes.Loan;

public interface IAuthorizedService {

	List<Loan> getAllPendingApproval();//Status'ü onay bekliyor olanları getir
	Map<String, Loan> getLoanForCustomer(Long contractId);//Müşterinin kredi bilgilerini getir
	Loan getLoanById(Long contractId);//Müşterinin kredi bilgilerini getir
	Map<String, Boolean>  checkCreditStatus(Long customerId, Double amountOfCredit);//Kredi alabilme durumunu getir
	Map<String, Loan> updateLoan(Long contractId, Loan loan); //Kredi bilgilerini güncelle
}
