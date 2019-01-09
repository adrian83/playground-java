package com.github.adrian83.akka.supervisor;

import java.math.BigInteger;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CalculationResult {

	private Integer number;
	private BigInteger factorial;

	CalculationResult(Integer number, BigInteger factorial) {
		this.number = number;
		this.factorial = factorial;
	}

	public Integer getNumber() {
		return number;
	}

	public BigInteger getFactorial() {
		return factorial;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
