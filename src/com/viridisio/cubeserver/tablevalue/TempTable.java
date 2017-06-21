package com.viridisio.cubeserver.tablevalue;

import java.util.ArrayList;
import java.util.Date;

public class TempTable extends TableValue {

	public Date lastModifiedDate;
	
	public ArrayList<TableRow> rows = new ArrayList<TableRow >();
	
	public ArrayList<TableColumn> columns = new ArrayList<TableColumn>();
	
	public TempTable() {
		super();
		this.lastModifiedDate = new Date();
	}

	@Override
	public Date getLastModifiedDate() {
		
		return this.lastModifiedDate;
	}

	@Override
	public ArrayList<TableColumn> getTableColumns() {
		
		return this.columns;
	}

	@Override
	public ArrayList<TableRow> getTableRows() {
		
		return this.rows;
	}

}
