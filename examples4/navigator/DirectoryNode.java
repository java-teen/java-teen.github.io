package examples4.navigator;

import java.io.File;
import java.io.FileFilter;

import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class DirectoryNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = -2793070381318205128L;

	private File[]	children = null;
	
	public DirectoryNode(final File node) {
		super(node,true);
	}

	@Override
    public TreeNode getChildAt(int index) {
		return new DirectoryNode(new File(children[index].getAbsolutePath()){public String toString(){return getName();}}); 
    }

	@Override
    public int getChildCount() {
		children = ((File)getUserObject()).listFiles(new FileFilter(){
						@Override
						public boolean accept(File pathname) {
							return pathname.isDirectory();
						}
					});
		return children == null ? 0 : children.length;
    }

	public TableModel getTableModel() {
		return new DirectoryTableModel((File)getUserObject());
	}
}
