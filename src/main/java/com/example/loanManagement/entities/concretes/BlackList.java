package com.example.loanManagement.entities.concretes;

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
@Table(name="black_list")
public class BlackList implements IEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="blacklist_id")
	private Long blackListId;
	@Column(name="customer_id")
	private Long customerId;
	
	public BlackList(Long blackListId, Long customerId) {
		this.blackListId = blackListId;
		this.customerId = customerId;
	}

	public BlackList() {}

}
