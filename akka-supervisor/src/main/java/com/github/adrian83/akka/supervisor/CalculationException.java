package com.github.adrian83.akka.supervisor;

public class CalculationException extends RuntimeException {

	private static final long serialVersionUID = 5535119858972039567L;
	
	private Integer value;
	
	CalculationException(Integer value) {
		super();
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
	
	

}
