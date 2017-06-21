package com.viridisio.cubeserver.tablevalue;

import java.util.Map;

public class TempTableRow extends TableRow {

	public TempTable table;
	
	public Map<TableColumn,Object> cells;
	
	@Override
	public Object getCell(TableColumn col) {
		
		if (this.cells != null && col != null)
			return this.cells.get(col);
		
		return null;
	}

}
