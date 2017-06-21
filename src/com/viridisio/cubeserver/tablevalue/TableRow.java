package com.viridisio.cubeserver.tablevalue;

public abstract class TableRow {

	public String rowID;
	
	// 数字返回：double   字符串返回：string， 布尔类型返回：boolean，  其他常数类型放回： IConstant ，  cube引用类型返回： Cube
	abstract public Object getCell(TableColumn col);
}
