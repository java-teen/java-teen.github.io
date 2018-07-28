package examples4.navigator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;


public class Application extends JFrame {
	private static final long 	serialVersionUID = 1985625426685562190L;

	public Application(final String title, final File root) {
		super(title);	// <1>
		final JTree			tree = new JTree(new DirectoryNode(root));
		final JTable		table = new JTable();
		final JSplitPane	pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new JScrollPane(tree),new JScrollPane(table));
		final Dimension		scr = Toolkit.getDefaultToolkit().getScreenSize();
		
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(final TreeSelectionEvent e) {
				final DirectoryNode	node = ((DirectoryNode)tree.getLastSelectedPathComponent());
				
				table.setModel(node != null ? node.getTableModel() : new DefaultTableModel());
			}
		});
		tree.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,0),"insertchild");	// <2>
		tree.getActionMap().put("insertchild",new AbstractAction(){private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				final DirectoryNode	node = ((DirectoryNode)tree.getLastSelectedPathComponent());
				
				if (node != null) {
					final File	newDir = new File(((File)node.getUserObject()),"newDir");
					
					newDir.mkdir();
					((DefaultTreeModel)tree.getModel()).insertNodeInto(new DirectoryNode(newDir),node,0);
				}
			}
		});
		tree.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0),"delete");		// <3>
		tree.getActionMap().put("delete",new AbstractAction(){private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				final DirectoryNode	node = ((DirectoryNode)tree.getLastSelectedPathComponent());
				
				if (node != null && JOptionPane.showConfirmDialog(Application.this,"Do you wish to delete "+((File)node.getUserObject()).getAbsolutePath()+" and all it's children?") == JOptionPane.YES_OPTION) {
					if (node.getParent() != null) {
						((DefaultTreeModel)tree.getModel()).removeNodeFromParent(node);
					}
					delete(((File)node.getUserObject()));
				}
			}
		});
		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	// <4>
		table.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0),"delete");
		table.getActionMap().put("delete",new AbstractAction(){private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				final TableModel	model = table.getModel();
				final int			selected = table.getSelectedRow();
				
				if ((model instanceof DirectoryTableModel) && (selected >= 0) && JOptionPane.showConfirmDialog(Application.this,"Do you wish to delete selected item(s)?") == JOptionPane.YES_OPTION) {
					((DirectoryTableModel)model).delete(selected);
				}
			}
		});
		
		getContentPane().add(pane,BorderLayout.CENTER);	// <5>
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(scr.width/4,scr.height/4));
		setPreferredSize(new Dimension(scr.width/2,scr.height/2));
		setLocation(new Point(scr.width/4,scr.height/4));
		pack();
	}
	
	public static void main(String[] args) {
		new Application("Test",new File("c:/tmp/assa")).setVisible(true);
	}

	private static void delete(final File file) {
		if (file != null) {
			if (file.isDirectory()) {
				file.listFiles(new FileFilter(){
					@Override
					public boolean accept(File pathname) {
						delete(pathname);
						return false;
					}
				});
			}
			file.delete();
		}
	}
}
