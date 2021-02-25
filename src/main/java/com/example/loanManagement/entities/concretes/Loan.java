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
@Table(name="loan")
public class Loan implements IEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="contract_id")
	private Long contractId;
	@Column(name="customer_id")
	private Long customerId;
	@Column(name="date_contract_start")
	private Date dateContractStart;
	@Column(name="date_contract_ends")
	private Date dateContractEnds;
	@Column(name="interest_rate")
	private double interestRate;
	@Column(name="credit_limit")
	private double creditLimit;
	@Column(name = "loan_document")
	private String loanDocument;
	@Column(name="authorized_status")
	private String authorizedStatus;
	@Column(name="customer_status")
	private String customerStatus;

	public Loan(Long contractId, Long customerId, Date dateContractStart, Date dateContractEnds, double interestRate,
			double creditLimit, String loanDocument, String authorizedStatus, String customerStatus ) {
		this.contractId = contractId;
		this.customerId = customerId;
		this.dateContractStart = dateContractStart;
		this.dateContractEnds = dateContractEnds;
		this.interestRate = interestRate;
		this.creditLimit = creditLimit;
		this.loanDocument = loanDocument;
		this.authorizedStatus = authorizedStatus;
		this.customerStatus = customerStatus;
	}
	
	public Loan(Long customerId, Date dateContractStart, Date dateContractEnds, double interestRate,
			double creditLimit, String loanDocument, String authorizedStatus, String customerStatus ) {
		this.customerId = customerId;
		this.dateContractStart = dateContractStart;
		this.dateContractEnds = dateContractEnds;
		this.interestRate = interestRate;
		this.creditLimit = creditLimit;
		this.loanDocument = loanDocument;
		this.authorizedStatus = authorizedStatus;
		this.customerStatus = customerStatus;
	}
	
	public Loan() {}


}
