package com.example.loanManagement.entities.concretes;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.example.loanManagement.entities.abstracts.IEntity;
import lombok.Data;

@Data
@Entity
@Table(name="payment_loan")
public class PaymentLoan implements IEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="payment_id")
	private Long paymentId;
	@Column(name="contract_id")
	private Long contractId;
	@Column(name="date_of_payment")
	private Date dateOfPayment;
	@Column(name="due_date")
	private Date dueDate;
	@Column(name="amount_of_payment")
	private double amountOfPayment;
	@Column(name="status")
	private String status;
	
	public PaymentLoan(Long paymentId, Long contractId, Date dateOfPayment, Date dueDate, double amountOfPayment,
			String status) {
		super();
		this.paymentId = paymentId;
		this.contractId = contractId;
		this.dateOfPayment = dateOfPayment;
		this.dueDate = dueDate;
		this.amountOfPayment = amountOfPayment;
		this.status = status;
	}

	public PaymentLoan() {}

}
