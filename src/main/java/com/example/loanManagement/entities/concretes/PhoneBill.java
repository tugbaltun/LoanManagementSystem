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
@Table(name="phone_bill")
public class PhoneBill implements IEntity{

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
	@Column(name="monthly_bill")
	private double monthlyBill;
	
	public PhoneBill(Long contractId, Long customerId, Date dateContractStart, Date dateContractEnds,
			double monthlyBill) {
		super();
		this.contractId = contractId;
		this.customerId = customerId;
		this.dateContractStart = dateContractStart;
		this.dateContractEnds = dateContractEnds;
		this.monthlyBill = monthlyBill;
	}

	public PhoneBill() {}
}
