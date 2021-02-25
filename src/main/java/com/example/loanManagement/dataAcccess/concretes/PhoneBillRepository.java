package com.example.loanManagement.dataAcccess.concretes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.loanManagement.entities.concretes.PhoneBill;

public interface PhoneBillRepository extends JpaRepository<PhoneBill, Long> {

	@Query(value="SELECT * FROM phone_bill WHERE (customer_id= :customerId)", nativeQuery = true)
	PhoneBill findByCustomerId(@Param("customerId") Long customerId);
}
