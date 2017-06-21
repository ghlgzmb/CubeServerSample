package com.viridisio.cubeserver.simplecube;

import java.util.Date;

import com.viridisio.cubeserver.core.IConstant;

public class TempString extends StringValue implements IConstant{

	public String str;
	
	public Date lastModifiedDate;
	
	public TempString() {
		super();
		this.lastModifiedDate = new Date();
	}

	@Override
	public Date getLastModifiedDate() {

		return this.lastModifiedDate;
	}

	@Override
	public String getString() {

		return this.str;
	}

	@Override
	public String saveToString() {

		return this.str;
	}

	@Override
	public void filledFromString(String content) {
		
		this.str = content;
		
	}

}
