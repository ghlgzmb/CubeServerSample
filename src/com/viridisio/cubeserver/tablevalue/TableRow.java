package com.viridisio.cubeserver.tablevalue;

public abstract class TableRow {

	public String rowID;
	
	// ���ַ��أ�double   �ַ������أ�string�� �������ͷ��أ�boolean��  �����������ͷŻأ� IConstant ��  cube�������ͷ��أ� Cube
	abstract public Object getCell(TableColumn col);
}
