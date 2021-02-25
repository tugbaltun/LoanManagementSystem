package com.example.loanManagement.business.concretes;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.loanManagement.business.abstracts.IAuthorizedService;
import com.example.loanManagement.dataAcccess.concretes.BlackListRepository;
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
public class AuthorizedManager implements IAuthorizedService {

	@Autowired private LoanRepository loanRepository;
	@Autowired private PhoneBillRepository phoneBillRepository;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private BlackListRepository blackListRepository;
	@Autowired private PaymentBillRepository paymentBillRepository;
	@Autowired private PaymentLoanRepository paymentLoanRepository;

	@Override
	public List<Loan> getAllPendingApproval() {
		List<Loan> loanList = loanRepository.findAllByStatusPending();
		if(loanList.isEmpty()) {
			throw new ApiErrorException("Loan cannot found in the cache");
		}
		return loanList;
	}
	
	@Override
	public Map<String, Loan> getLoanForCustomer(Long contractId) {
		Loan loan = loanRepository
				.findById(contractId)
				.orElseThrow(() -> new ApiErrorException("No contract with id:" + contractId)) ;
		Map<String, Boolean> resultList = checkCreditStatus(loan.getCustomerId(), loan.getCreditLimit());
		List<String> listOfKey = new ArrayList<String>();
		
		if(loan.getAuthorizedStatus().contains("Reddedildi")) {
			for (Map.Entry<String,Boolean> entry: resultList.entrySet()){
	            String key = entry.getKey();
	            listOfKey.add(key);
	        }
		}
		else {
			listOfKey.add("");
		}
		
		Map<String, Loan> result = new HashMap<>();
		result.put(listOfKey.toString(), loan);
		return result;
	}
	
	@Override
	public Loan getLoanById(Long contractId) {
		return loanRepository
				.findById(contractId)
				.orElseThrow(() -> new ApiErrorException("No contract with id:" + contractId)) ;
	}

	@Override
	public Map<String, Loan> updateLoan(Long contractId, Loan loan) {
		Loan loanToUpdate = loanRepository.findById(contractId)
				.orElseThrow(()-> new ApiErrorException("No loan with id: "+ contractId));
		if(this.checkCreditStatus(loan.getCustomerId(), loan.getCreditLimit()).containsValue(false)) {
			loanToUpdate.setAuthorizedStatus("Reddedildi");
			loanRepository.save(loanToUpdate);
		}
		else {
		loanToUpdate.setCreditLimit(loan.getCreditLimit());
		loanToUpdate.setDateContractEnds(loan.getDateContractEnds());
		loanToUpdate.setDateContractStart(loan.getDateContractStart());
		loanToUpdate.setInterestRate(loan.getInterestRate());
		loanToUpdate.setLoanDocument(loan.getLoanDocument());
		loanToUpdate.setAuthorizedStatus(loan.getAuthorizedStatus());
		loanToUpdate.setCustomerStatus(loan.getCustomerStatus());
		loanRepository.save(loanToUpdate);
		}
		if(loan.getAuthorizedStatus().contains("Onaylandı") && loan.getCustomerStatus().contains("Onaylandı")) {
			ZoneId zonedId = ZoneId.of( "Europe/Istanbul" );
			LocalDate today = LocalDate.now( zonedId ).plusMonths(1);
			PaymentLoan paymentLoan = new PaymentLoan();
			paymentLoan.setContractId(contractId);
			paymentLoan.setDateOfPayment(null);
			paymentLoan.setDueDate(java.sql.Date.valueOf(today));
			paymentLoan.setAmountOfPayment(loan.getCreditLimit()*(loan.getInterestRate()+100)/100/3);
			paymentLoan.setStatus("Ödenmedi");
			paymentLoanRepository.save(paymentLoan);
		}
		
		Map<String, Loan> result = new HashMap<>();
		result.put("Kredi başvuru durumu güncellendi ve müşteri bilgilendirildi.", loan);
		return result;
	}
	
	
	@Override  
	public Map<String, Boolean> checkCreditStatus(Long customerId, Double amountOfCredit) {
		List<Loan> loanList = loanRepository.findAllByCustomerId(customerId);
		Map<String, Boolean> result = new HashMap<>();
		String message = null ;
		Boolean validation = null;
		
		if(loanList.isEmpty()) {
			if(		this.checkBlackList(customerId) == true && 
					this.checkLegalStatus(customerId) == true) {
				message ="Kredi alabilirsiniz!";
				validation = true;
				result.put(message, validation);
			}
			else {
				validation = false;
				if(this.checkBlackList(customerId)==false) {
					message = "-Kara listedesiniz";
					result.put(message, validation);
				}
				if(this.checkLegalStatus(customerId)==false) {
					message = "-Daha önce yasal takibe maruz kalmışsınız";
					result.put(message, validation);
				}
				if(this.countMaxCreditperMonth(customerId)<amountOfCredit) {
					message = "-Çekebileceğiniz aylık kredi limitinden fazla talepte bulundunuz";
					result.put(message, validation);
				}
			}
		}
		else {
			if(		this.checkBlackList(customerId) == true && 
					this.checkLegalStatus(customerId) == true &&
					this.checkMaxCreditPerMonth(customerId)== true &&
					this.checkMaxCreditPerYear(customerId, amountOfCredit) == true) {
				message ="Müşterinin durumu kredi alımı için uygundur! Kredi limiti:"
					+countMaxCreditperMonth(customerId)+" TL'dir.";
				validation = true;
				result.put(message, validation);
			}
			else {
				validation = false;
				if(this.checkBlackList(customerId)==false) {
					message = "-Kara listedesiniz";
					result.put(message, validation);
				}
				if(this.checkLegalStatus(customerId)==false) {
					message = "-Daha önce yasal takibe maruz kalmışsınız";
					result.put(message, validation);
				}
				if(this.checkMaxCreditPerMonth(customerId)==false) {
					message = "-Bu ay içinde kredi başvurusunda bulundunuz";
					result.put(message, validation);
				}
				if(this.checkMaxCreditPerYear(customerId, amountOfCredit)==false) {
					message = "-Yıllık çekebileceğiniz kredi miktarını aştınız";
					result.put(message, validation);
				}
				if(this.countMaxCreditperMonth(customerId)<amountOfCredit) {
					message = "-Çekebileceğiniz aylık kredi limitinden fazla talepte bulundunuz";
					result.put(message, validation);
				}
			}
		}
		return result;
	}
	
	private double countMaxCreditperMonth(Long customerId) {
		
		int delayCounter = 0;
		double delayRate = 0.0;
		double subscriptionYear;
		ZoneId zonedId = ZoneId.of( "Europe/Istanbul" );
		LocalDate today = LocalDate.now( zonedId );
		PhoneBill phoneBill = phoneBillRepository.findByCustomerId(customerId);
		if(phoneBillRepository.findByCustomerId(customerId)==null) {
			throw new ApiErrorException("No contract with id:"+customerId);
		}
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()-> new ApiErrorException("No customer with id:"+customerId));
		List<PaymentBill> paymentBill = paymentBillRepository.findAllByContractId(phoneBill.getContractId());
		
		for(PaymentBill list: paymentBill) {		
			delayCounter =  ((list.getDateOfPayment()!= null) && //Ödeme yapıldı ve tarihi veritabanına eklendi ama
							((list.getDueDate().getTime()-list.getDateOfPayment().getTime())/(3600*24*1000)<0) //Ödeme gecikti
							|| (list.getDateOfPayment()== null))  //Ödeme yapılmadı
							? delayCounter + 1 
							: delayCounter;
		}
		
		delayRate = (paymentBill.size()!=0) ? delayCounter/(paymentBill.size()) : 0.0;
		subscriptionYear = (java.sql.Date.valueOf(today).getTime()-customer.getSubscriptionDate().getTime())/(3600*24*1000)/365;
		
		double creditLimit 
		= (subscriptionYear>5 && delayRate == 0) ?  2000
		: (subscriptionYear>5 && delayRate>0 && delayRate<0.5) ? 1500
		: (subscriptionYear>=1 && subscriptionYear<=5 && delayRate==0) ? creditLimit = 1000
		: (subscriptionYear>=1 && subscriptionYear<=5 && delayRate>0 && delayRate<0.5) ? creditLimit = 500
		: 250.0 ;

		return creditLimit;
	}
	
	private double countMaxCreditperYear(Long customerId) {
		List<Loan> loanList = loanRepository.findAllByCustomerId(customerId);
		double totalCredit = 0;
		for(Loan list: loanList) {
			totalCredit += list.getCreditLimit();
		}
		return totalCredit;
	}

	private boolean checkMaxCreditPerYear(Long customerId, Double amounOfCredit) {
		Boolean validation = null;
		double maxCreditAmount= this.countMaxCreditperMonth(customerId);
		double totalCredit = maxCreditAmount*12+ amounOfCredit;
		
		validation = (totalCredit-this.countMaxCreditperYear(customerId)>=0) 
				? true
				: false;
		return validation;
	}
	
	
	private boolean checkMaxCreditPerMonth(Long customerId){
		List<Loan> loanList = loanRepository.findAllByCustomerId(customerId);
		if(loanList.isEmpty()) {
			throw new ApiErrorException("Customer cannot found in the cache");
		}
		double daysOfCredit = 0.0;
		Boolean result = true ;
		ZoneId zonedId = ZoneId.of( "Europe/Istanbul" );
		LocalDate today = LocalDate.now( zonedId );
		for(Loan loan: loanList) {
			daysOfCredit = (java.sql.Date.valueOf(today).getTime()-loan.getDateContractStart().getTime())/(3600*24*1000);
			result = (loanRepository.findAllByStatusApproved(customerId).isEmpty() || daysOfCredit>30)
					? true
					:false;

			if(result == false) {
				break;
			}
		}
		
		return result;
	}
	
	private boolean checkBlackList(Long customerId) {
		boolean result = blackListRepository.findByCustomerId(customerId)== null 
				? true :  false;
		 return result;
	}
	
	private Boolean checkLegalStatus(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()-> new ApiErrorException("No customer wih id:"+customerId));
		boolean result = customerRepository.findLegalStatus(customerId) == null
				? true : false;
		return result;
	}



}
