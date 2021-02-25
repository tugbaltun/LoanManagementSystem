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
@Table(name="customers")
public class Customer implements IEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="customer_id")
	private Long customerId;
	@Column(name="identity_number")
	private String identityNumber;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name= "phone_number")
	private String phoneNumber;
	@Column(name="address")
	private String address;
	@Column(name="birth_date")
	private Date birthDate;
	@Column(name="customer_type")
	private String customerType;
	@Column(name="identification_path")
	private String identificationPath;
	@Column(name="legal_status")
	private String legalStatus;
	@Column(name="subscription_date")
	private Date subscriptionDate;

	public Customer(Long customerId, String identityNumber, String firstName, String lastName, String phoneNumber,
			String address, Date birthDate, String customerType, String identificationPath, String legalStatus, Date subscriptionDate) {
		super();
		this.customerId = customerId;
		this.identityNumber = identityNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.birthDate = birthDate;
		this.customerType = customerType;
		this.identificationPath = identificationPath;
		this.legalStatus = legalStatus;
		this.subscriptionDate = subscriptionDate;
	}
	
	public Customer() {}

}
