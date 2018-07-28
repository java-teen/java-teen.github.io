package examples4;


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
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JavaFXNotepad extends Application {
	@FXML private Label		stateString; 
	@FXML private TextArea	content;	
	private File			loadedFile = null;
	private File			currentDir = new File("./");
	private volatile Stage	parentStage;

	private class SceneAndDialog extends Scene {	// <1>
		final Stage		stage;
		
		private SceneAndDialog(final Stage stage, final Parent parent) {
			super(parent);
			this.stage = stage;
		}
	}
	
	@Override
	public void start(final Stage primaryStage) throws Exception {	// <2>
		final Parent root = FXMLLoader.load(getClass().getResource("./main.fxml"));
	    final  Scene scene = new Scene(root);
    
        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        parentStage = primaryStage;
        primaryStage.show();
	}

	@FXML private boolean newFile() {	// <3>
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

	@FXML private boolean openFile() {
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
	
	@FXML private boolean saveFile() {
		if (loadedFile != null) {
			saveContent(loadedFile);
			return true;
		}
		else {
			return saveAsFile();
		}
	}

	@FXML private boolean saveAsFile() {
		final FileChooser 	fileChooser = new FileChooser();
		final File			f;
		
		fileChooser.setTitle("Save File as...");
		fileChooser.setInitialDirectory(currentDir);
		
		if ((f = fileChooser.showSaveDialog(parentStage)) != null) {
			currentDir = fileChooser.getInitialDirectory();
			loadedFile = f;
			
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
	
	private boolean loadContent() {	// <4>
		final FileChooser 	fileChooser = new FileChooser();
		final File			f;
		
		fileChooser.setTitle("Open File");
		fileChooser.setInitialDirectory(currentDir);
		
		if ((f = fileChooser.showOpenDialog(parentStage)) != null) {		
			currentDir = fileChooser.getInitialDirectory();
			loadedFile = f;

			try{content.setText(new String(Files.readAllBytes(loadedFile.toPath())));
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
			
			wr.write(content.getText());
			message("File %1$s stored successfully", loadedFile.getAbsolutePath());
			return true;
		} catch (IOException e) {
			message("File %1$s: error storing (%1$s)", loadedFile.getAbsolutePath(), e.getMessage());
			return false;
		}
	}

	@FXML private boolean quit() {
		System.exit(0);
		return false;
	}
	
	@FXML private boolean about() {	// <5>
		try{final Parent 			root = FXMLLoader.load(getClass().getResource("./about.fxml"));
	    	final Stage				dialog = new Stage();
		    final SceneAndDialog 	scene = new SceneAndDialog(dialog,root);
		    
	        dialog.setTitle("About program");
	        dialog.setScene(scene);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(parentStage);
			System.err.println(this);
	        dialog.showAndWait();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	@FXML private void closeAbout(ActionEvent ae) {	// <6>

		((SceneAndDialog)((Node) ae.getSource()).getScene()).stage.close();
	}
	
	private void message(final String format, final Object... parameters) {
		if (parameters != null && parameters.length > 0) {
			stateString.setText(String.format(format,parameters));
		}
		else {
			stateString.setText(format);
		}
	}
	
	
	public static void main(String[] args) {	// <7>
		launch(args);
	}
}
