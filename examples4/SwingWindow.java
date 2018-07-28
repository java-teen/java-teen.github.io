package examples4;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;

public class SwingWindow extends JFrame {
	private static final long 		serialVersionUID = -4276643083028577172L;

	private static final Object[][]	TABLE_CONTENT = new Object[][]{
											new Object[]{"cell 1.1","cell 1.2","cell 1.3"},
											new Object[]{"cell 2.1","cell 2.2","cell 2.3"},
										};
	private static final Object[]	TABLE_COLUMNS = new Object[]{"Column 1","Column 2","Column 3",};
	
	private final JLabel			stateString = new JLabel("Window state string");
	
	public SwingWindow() {
		super("Swing Window");
		
		final JMenuItem	item1 = new JMenuItem("Item 1");
		final JMenuItem	item2 = new JMenuItem("Item 2");
		final JMenu		submenu = new JMenu("Submenu");
		final JMenuBar	menuBar = new JMenuBar();
		
		submenu.add(item1);
		submenu.addSeparator();
		submenu.add(item2);
		menuBar.add(submenu);
		getContentPane().add(menuBar,BorderLayout.NORTH);
		
		final JPanel	statePanel = new JPanel();
		
		statePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		statePanel.add(stateString);
		getContentPane().add(statePanel,BorderLayout.SOUTH);
		
		final JTree			leftTree = new JTree();
		final JTable		rightTable = new JTable(TABLE_CONTENT,TABLE_COLUMNS);
		final JScrollPane	rightScrollInTab1 = new JScrollPane(rightTable);
		final JLabel		rightLabelInTab2 = new JLabel("I'm not visible yet");
		final JTabbedPane	rightPane = new JTabbedPane();
		final JSplitPane	splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		rightPane.addTab("Tab 1",rightScrollInTab1);
		rightPane.addTab("Tab 2",rightLabelInTab2);
		splitter.setLeftComponent(leftTree);
		splitter.setRightComponent(rightPane);
		getContentPane().add(splitter,BorderLayout.CENTER);
		
		pack();
		setPreferredSize(new Dimension(300,200));
	}
	
	public static void main(final String[] args) {
		new SwingWindow().setVisible(true); 
	}
}
