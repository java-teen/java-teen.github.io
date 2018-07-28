package examples4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;

public class ErzatsNotepad extends JFrame {	// <1>
	private static final long 	serialVersionUID = 7553877057619648347L;
	private static final String	ABOUT_CONTENT = "<html><head></head><body><h3>Test program</h3><p>This is a test program</p></body></html>";
	
	@FunctionalInterface
	private interface CallInterface {	// <2>
		boolean call();
	}
	
	private File		currentDir = new File("./");
	private File		loadedFile = null;
	private JTextArea	content = new JTextArea();
	private JLabel		stateString = new JLabel(" ");

	public ErzatsNotepad(final String caption) {	// <3>
		super(caption);
		getContentPane().add(prepareMenu(),BorderLayout.NORTH);
		getContentPane().add(prepareContent(),BorderLayout.CENTER);
		getContentPane().add(prepareStateString(),BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final Dimension	screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		setPreferredSize(new Dimension(screen.width/2,screen.height/2));
		pack();
		setVisible(true);
	}

	private JMenuBar prepareMenu() {	// <4>
		final JMenu		file = new JMenu("File");
		final JMenu		help = new JMenu("Help");
		final JMenuBar	result = new JMenuBar();
		
		file.add(createMenuItem("New file",this::newFile));
		file.add(createMenuItem("Open file",this::openFile));
		file.addSeparator();
		file.add(createMenuItem("Save file",this::saveFile));
		file.add(createMenuItem("Save as...",this::saveAsFile));
		file.addSeparator();
		file.add(createMenuItem("Quit",this::quit));
		
		help.add(createMenuItem("About",this::about));
		
		result.add(file);
		result.add(help);
		return result;
	}
	
	private JMenuItem createMenuItem(final String text, final CallInterface callMethod) { // <5>
		final JMenuItem		result = new JMenuItem(text);
		
		result.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				callMethod.call();
			}
		});
		
		return result;
	}

	private JScrollPane prepareContent() {	// <6>
		final JScrollPane	result = new JScrollPane(content,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		result.setBorder(BorderFactory.createEmptyBorder());
		return result;
	}

	
	private JPanel	prepareStateString() {	// <7>
		final JPanel	result = new JPanel();

		result.setLayout(new FlowLayout(FlowLayout.LEFT));
		result.add(stateString);
		result.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		return result;
	}

	private boolean newFile() {	// <8>
		if (loadedFile != null) {
			if (!saveAsFile()) {
				return false;
			}
			else {
				return newContent();
			}
		}
		else {
			return newContent();
		}
	}

	private boolean openFile() {
		if (loadedFile != null) {
			if (!saveAsFile()) {
				return false;
			}
			else {
				return loadContent();
			}
		}
		else {
			return loadContent();
		}
	}
	
	private boolean saveFile() {
		if (loadedFile != null) {
			saveContent(loadedFile);
			return true;
		}
		else {
			return saveAsFile();
		}
	}

	private boolean saveAsFile() {
		final JFileChooser	save = new JFileChooser(currentDir);
		
		if (save.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			currentDir = save.getCurrentDirectory();
			loadedFile = save.getSelectedFile();
			
			return saveContent(loadedFile);
		}
		else {
			return false;
		}
	}

	private boolean newContent() {
		loadedFile = null;
		content.setText("");
		message("Ready");
		return true;
	}
	
	private boolean loadContent() {	// <9>
		final JFileChooser	open = new JFileChooser(currentDir);
		
		if (open.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			currentDir = open.getCurrentDirectory();
			loadedFile = open.getSelectedFile();

			try(final InputStream	is = new FileInputStream(loadedFile);
				final Reader		rdr = new InputStreamReader(is);) {
				
				content.read(rdr,null);
				message("File %1$s loaded successfully", loadedFile.getAbsolutePath());
			} catch (IOException e) {
				message("File %1$s: error loading (%1$s)", loadedFile.getAbsolutePath(), e.getMessage());
				return newContent();
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean saveContent(final File contentFile) {
		try(final OutputStream	is = new FileOutputStream(loadedFile);
			final Writer		wr = new OutputStreamWriter(is);) {
			
			content.write(wr);
			message("File %1$s stored successfully", loadedFile.getAbsolutePath());
			return true;
		} catch (IOException e) {
			message("File %1$s: error storing (%1$s)", loadedFile.getAbsolutePath(), e.getMessage());
			return false;
		}
	}

	private boolean quit() {
		this.setVisible(false);
		System.exit(0);
		return false;
	}
	
	private boolean about() {	// <10>
		final JTextPane	pane = new JTextPane();
		
		pane.setContentType("text/html");
		pane.setText(ABOUT_CONTENT);	
		pane.setEditable(false);
		pane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		JOptionPane.showMessageDialog(this,pane,"About program",JOptionPane.INFORMATION_MESSAGE);
		return true;
	}
	
	private void message(final String format, final Object... parameters) { // <11>
		if (parameters != null && parameters.length > 0) {
			stateString.setText(String.format(format,parameters));
		}
		else {
			stateString.setText(format);
		}
	}
	
	public static void main(String[] args) {	// <12>
		new ErzatsNotepad("Test editor");
	}
}
