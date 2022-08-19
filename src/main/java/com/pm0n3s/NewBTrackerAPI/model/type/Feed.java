package com.pm0n3s.NewBTrackerAPI.model.type;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.pm0n3s.NewBTrackerAPI.model.EventType;

@JsonTypeName("feed")
@Entity
@DiscriminatorValue("feed")
public class Feed extends EventType{

	@Column(name="amount")
	private BigDecimal amount;
	
	public Feed() {};

	public Feed(BigDecimal amount) {
		super();
		this.amount = amount;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
