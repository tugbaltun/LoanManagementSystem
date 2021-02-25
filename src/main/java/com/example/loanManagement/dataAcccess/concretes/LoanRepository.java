package com.example.loanManagement.dataAcccess.concretes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.loanManagement.entities.concretes.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>{
	
	@Query(value="SELECT * FROM loan WHERE (customer_id = :customerId)", nativeQuery = true)
	List<Loan> findAllByCustomerId(@Param("customerId") Long customerId);
	
	@Query(value="SELECT * FROM loan WHERE (authorized_status = 'Onaylanması Bekleniyor')", nativeQuery = true)
	List<Loan> findAllByStatusPending();
	
	@Query(value="SELECT * FROM loan WHERE (authorized_status = 'Onaylandı') AND (customer_id=:customerId)", nativeQuery = true)
	List<Loan> findAllByStatusApproved(@Param("customerId") Long customerd);

}
