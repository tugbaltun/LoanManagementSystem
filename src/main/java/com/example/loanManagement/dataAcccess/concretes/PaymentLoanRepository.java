package com.example.loanManagement.dataAcccess.concretes;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.loanManagement.entities.concretes.PaymentLoan;

public interface PaymentLoanRepository extends JpaRepository<PaymentLoan, Long> {

	@Query(value="SELECT * FROM payment_loan WHERE (contract_id = :contractId)", nativeQuery = true)
	List<PaymentLoan> findAllByContractId(@Param("contractId") Long contractId);
	
}
