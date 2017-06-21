package com.viridisio.cubeserver.localtablecube;

import java.util.ArrayList;
import java.util.Date;

import com.viridisio.cubeserver.tablevalue.TableColumn;
import com.viridisio.cubeserver.tablevalue.TableRow;
import com.viridisio.cubeserver.tablevalue.TableValue;

public class LocalTable extends TableValue {

	public LocalTableCube cube;
	
	public Date lastModifiedDate;
	
	public LocalTable() {
		super();
		this.lastModifiedDate = new Date();
	}

	@Override
	public Date getLastModifiedDate() {
		
		return this.lastModifiedDate;
	}

	@Override
	public ArrayList<TableColumn> getTableColumns() {
		
		if (this.cube == null)
			return null;
		
		ArrayList<TableColumn> cols = new ArrayList<TableColumn>();
		TableColumn col;
		
		for (LocalTableColumn lcol: this.cube.columns)
		{
			col = new TableColumn();
			col.name = lcol.name;
			col.isCube = lcol.isCube;
			col.valueType = lcol.valueType;
			if (lcol.isFomular == true)
			{
				col.isCube = false;
			}
			
			cols.add(col);
		}
		
		return cols;

	}

	@Override
	public ArrayList<TableRow> getTableRows() {
		
		if (this.cube == null)
			return null;
		
		ArrayList<TableRow> rows = new ArrayList<TableRow>();
		for (LocalTableRow row:this.cube.rows)
		{
			rows.add(row);
		}
		return rows;
	}

}
