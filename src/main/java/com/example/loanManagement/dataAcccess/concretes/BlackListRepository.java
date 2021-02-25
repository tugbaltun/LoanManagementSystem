package com.example.loanManagement.dataAcccess.concretes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.loanManagement.entities.concretes.BlackList;

public interface BlackListRepository extends JpaRepository<BlackList, Long>{

	@Query(value="SELECT * FROM black_list WHERE (customer_id = :customerId)", nativeQuery = true)
	BlackList findByCustomerId(@Param("customerId") Long customerId);
}
