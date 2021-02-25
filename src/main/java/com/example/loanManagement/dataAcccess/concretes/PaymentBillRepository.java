package com.example.loanManagement.dataAcccess.concretes;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.loanManagement.entities.concretes.PaymentBill;

public interface PaymentBillRepository extends JpaRepository<PaymentBill, Long>{

	@Query(value="SELECT * FROM payment_bill WHERE (contract_id = :contractId)", nativeQuery = true)
	List<PaymentBill> findAllByContractId(@Param("contractId") Long contractId);
	

}
