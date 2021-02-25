package com.example.loanManagement.business.concretes;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
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
import com.example.loanManagement.TestBase;
import com.example.loanManagement.business.concretes.CustomerManager;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
public class CustomerManagerTest extends TestBase{

	@InjectMocks private  CustomerManager customerManager;
	@MockBean private  CustomerRepository customerRepository;
	@MockBean private PhoneBillRepository phoneBillRepository;
	@MockBean private PaymentBillRepository paymentBillRepository;
	@MockBean private LoanRepository loanRepository;
	@MockBean private PaymentLoanRepository paymentLoanRepository;
	
	public List<Customer> customers;

	@Test
	void testGetCustomerByIDNumber() {
		Customer customer2 = new Customer(1L,"10210210225","FirstName2","LastName2","5052565658","Address2",
				java.sql.Date.valueOf("1995-02-25") , "Postpaid", "Path2", "Olumsuz", java.sql.Date.valueOf("2020-02-25"));
		Mockito.when(customerRepository.findCustomerByIDNumber("10210210225")).thenReturn(customer2);
		String expectedJson = asJsonString(customer2);
		String actualInJson = asJsonString(customerManager.getCustomerByIDNumber("10210210225"));
		assertEquals(expectedJson, actualInJson);
	}

	@Test
	void testGetCustomerById() {
		Customer customer2 = new Customer(1L,"10210210225","FirstName2","LastName2","5052565658","Address2",
				java.sql.Date.valueOf("1995-02-25") , "Postpaid", "Path2", "Olumsuz", java.sql.Date.valueOf("2020-02-25"));
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer2));
		String expectedJson = asJsonString(customer2);
		String actualInJson = asJsonString(customerManager.getCustomerById(1L));
		assertEquals(expectedJson, actualInJson);
	}

	@Test
	void testGetPhoneBill() {
		PhoneBill phoneBill = new PhoneBill(1L,1L,java.sql.Date.valueOf("1995-02-25"), java.sql.Date.valueOf("1995-02-25"), 52.55   );
		Mockito.when(phoneBillRepository.findById(1L)).thenReturn(Optional.ofNullable(phoneBill));
		String expectedJson = asJsonString(phoneBill);
		String actualInJson = asJsonString(customerManager.getPhoneBill(1L));
		assertEquals(expectedJson, actualInJson);
	}

	@Test
	void testGetPaymentBill() {
		List<PaymentBill> paymentBills = new ArrayList<PaymentBill>();
		PaymentBill paymentBill1 = new PaymentBill(1L, 1L, java.sql.Date.valueOf("1995-02-25"), java.sql.Date.valueOf("1995-02-25"), 25.55, "Ödendi" );
		PaymentBill paymentBill2 = new PaymentBill(2L, 1L, java.sql.Date.valueOf("1995-02-25"), java.sql.Date.valueOf("1995-02-25"), 25.55, "Ödendi" );
		paymentBills.add(paymentBill1);
		paymentBills.add(paymentBill2);
		Mockito.when(paymentBillRepository.findAllByContractId(1L)).thenReturn(paymentBills);
		String expectedJson = asJsonString(paymentBills);
		String actualInJson = asJsonString(customerManager.getPaymentBill(1L));
		assertEquals(expectedJson, actualInJson);
	}

	@Test
	void testGetPaymentLoan() {
		List<PaymentLoan> paymentLoans = new ArrayList<PaymentLoan>();
		PaymentLoan paymentLoan1 = new PaymentLoan(1L, 1L, java.sql.Date.valueOf("1995-02-25"), java.sql.Date.valueOf("1995-02-25"), 25.55, "Ödendi" );
		PaymentLoan paymentLoan2 = new PaymentLoan(2L, 1L, java.sql.Date.valueOf("1995-02-25"), java.sql.Date.valueOf("1995-02-25"), 25.55, "Ödendi" );
		paymentLoans.add(paymentLoan1);
		paymentLoans.add(paymentLoan2);
		Mockito.when(paymentLoanRepository.findAllByContractId(1L)).thenReturn(paymentLoans);
		String expectedJson = asJsonString(paymentLoans);
		String actualInJson = asJsonString(customerManager.getPaymentLoan(1L));
		assertEquals(expectedJson, actualInJson);
	}

	@Test
	void testUpdateLoan() {
		Loan loanToUpdate = new Loan(1L, java.sql.Date.valueOf("1995-02-25"), java.sql.Date.valueOf("1995-02-25"), 10, 250,"document.com","Onaylanması Bekleniyor", "Onaylandı" );
		Loan loanToSave = new Loan(1L, 1L, java.sql.Date.valueOf("1995-02-25"), java.sql.Date.valueOf("1995-02-25"), 10, 250,"document.com","Onaylandı", "Onaylandı" );
		Mockito.when(loanRepository.findById(1L)).thenReturn(Optional.ofNullable(loanToSave));
		Mockito.when(loanRepository.save(Mockito.any(Loan.class))).thenReturn(loanToSave);

		
		Map<String, Loan> response = new HashMap<>();
        response.put("Kredi başvuru durumu güncellendi ve yetkili onayına gönderildi.", loanToSave);
		String expectedJson = asJsonString(response);
		String actualInJson = asJsonString(customerManager.updateLoan(1L, loanToUpdate));
		assertEquals(expectedJson, actualInJson);	}

}
