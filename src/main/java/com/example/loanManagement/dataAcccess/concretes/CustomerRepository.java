package com.example.loanManagement.dataAcccess.concretes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.loanManagement.entities.concretes.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	@Query(value="SELECT * FROM customers WHERE (identity_number = :identityNumber)", nativeQuery = true)
	Customer findCustomerByIDNumber(@Param("identityNumber") String identityNumber);
	
	@Query(value="SELECT * FROM customers WHERE (legal_status = 'Olumsuz') AND (customer_id = :customerId)", nativeQuery = true)
	Customer findLegalStatus(@Param("customerId") Long customerId);
}
