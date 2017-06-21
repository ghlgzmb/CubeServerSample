package com.viridisio.cubeserver.simplecube;

import java.util.Date;

import com.viridisio.cubeserver.core.IConstant;

public class TempNumber extends NumberValue implements IConstant {

	public double number;
	
	public Date lastModifiedDate;
	
	public TempNumber() {
		super();
		this.lastModifiedDate = new Date();
	}

	@Override
	public Date getLastModifiedDate() {

		return this.lastModifiedDate;
	}

	@Override
	public double getNumber() {

		return this.number;
	}

	@Override
	public String saveToString() {

		return Double.toString(this.number);
	}

	@Override
	public void filledFromString(String content) {
		
		this.number = Double.valueOf(content);
		
	}

}
