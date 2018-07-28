package examples4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.JobAttributes;
import java.awt.JobAttributes.DefaultSelectionType;
import java.awt.JobAttributes.DestinationType;
import java.awt.JobAttributes.DialogType;
import java.awt.JobAttributes.SidesType;
import java.awt.PageAttributes;
import java.awt.PageAttributes.OrientationRequestedType;
import java.awt.PageAttributes.OriginType;
import java.awt.PrintJob;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class ExecutePrintJob {
	private static int			PRINT_DENCITY = 600;
	private static final String	TEXT = "Hello, world!"; 

	public static void main(String[] args) {
	    final JobAttributes jobAttributes = new JobAttributes();	// <1>
	    final PageAttributes pageAttributes = new PageAttributes();
	    final Font font = new Font("Arial", Font.PLAIN, 12);
		final PrintJob job;

	    jobAttributes.setDialog(DialogType.NONE);	// <2>
	    jobAttributes.setSides(SidesType.ONE_SIDED);
	    jobAttributes.setCopies(1);
	    jobAttributes.setDefaultSelection(DefaultSelectionType.ALL);
	    jobAttributes.setDestination(DestinationType.PRINTER);
	    jobAttributes.setFromPage(1);
	    jobAttributes.setToPage(2);
	    
	    pageAttributes.setOrigin(OriginType.PRINTABLE);	// <3>
	    pageAttributes.setOrientationRequested(OrientationRequestedType.PORTRAIT);
	    pageAttributes.setPrinterResolution(PRINT_DENCITY);

		if ((job = Toolkit.getDefaultToolkit().getPrintJob(new JFrame(), "MyPrintJob", jobAttributes, pageAttributes)) != null) {	// <4>
	        final Dimension	dim = job.getPageDimension();
	        
	        for (int pageNo = jobAttributes.getFromPage(); pageNo < jobAttributes.getToPage(); pageNo++) {	// <5>
	            final Graphics g = job.getGraphics();		// <6>
	            
	            g.setColor(Color.black);					// <7>					
	            g.setFont(font);	            
	            g.drawRect(5 , 5, dim.width - 5, dim.height - 5 - 5);
	            g.drawString(TEXT, (dim.width / 2) - (g.getFontMetrics().stringWidth(TEXT) / 2), dim.height / 2);
	            
	            g.dispose();	// <8>
	        }
	        job.end();	// <9>
	    }
		else {
			System.err.println("Can't create print job");
		}
	}
}
