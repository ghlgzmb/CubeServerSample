package com.viridisio.cubeserver.localtablecube;

import java.util.HashMap;
import java.util.Map;
import com.viridisio.cubeserver.core.FomularCube;
import com.viridisio.cubeserver.tablevalue.TableColumn;
import com.viridisio.cubeserver.tablevalue.TableRow;

public class LocalTableRow extends TableRow {
	
	public LocalTableCube cube;
	
	public Map<TableColumn,Object> cells = new HashMap<TableColumn,Object>();
		
	// 公式类型的列需要在 getcell的时候进行计算， 返回计算结果而不是返回公式
	@Override
	public	Object getCell(TableColumn col) {

		if (this.cells != null && col != null)
		{
			LocalTableColumn localCol = null;
			for (LocalTableColumn lcol:this.cube.columns)
			{
				if (col.name.endsWith(lcol.name))
					localCol = lcol;
			}
			if (localCol.isFomular == true)
			{
				this.cube.currentRowId = this.rowID;
				FomularCube fomular = 	(FomularCube)this.cells.get(localCol);
				fomular.exp.calculate();
				this.cube.currentRowId = null;
				return fomular.exp.getResultValue();
			}
			else return this.cells.get(localCol);
		}

	

		return null;
	}

}
