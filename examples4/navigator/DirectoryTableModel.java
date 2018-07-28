package examples4.navigator;

import java.io.File;
import java.io.FileFilter;

import javax.swing.table.AbstractTableModel;

class DirectoryTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -8678771016671465922L;

	private final File	container;
	private File[]		content;
	
	DirectoryTableModel(final File dir) {
		this.container = dir;
		buildContent(dir);
	}
	
	@Override
	public int getRowCount() {
		return content == null ? 0 : content.length;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0	: return "Name";
			case 1	: return "Size (bytes)";
			default : return "";
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0	: return content[rowIndex].getName();
			case 1	: return content[rowIndex].length();
			default : return "";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 0;
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		content[rowIndex].renameTo(new File(container,value.toString()));
		buildContent(container);
		fireTableCellUpdated(rowIndex,columnIndex);
	}
	
	public void delete(int rowIndex) {
		content[rowIndex].delete();
		buildContent(container);
		fireTableRowsDeleted(rowIndex,rowIndex);
	}
	
	private void buildContent(final File dir) {
		this.content = dir.listFiles(new FileFilter(){
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		});
	}
}
