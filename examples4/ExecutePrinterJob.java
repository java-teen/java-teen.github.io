package examples4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class ExecutePrinterJob implements Printable {	// <1>
	private static final Font	MY_FONT = new Font("Courier new", Font.PLAIN, 10);
	private static final String	MY_TEXT = "Hello, world!";
	
	@Override
	public int print(final Graphics graphics, final PageFormat pageFormat, final int pageIndex) throws PrinterException {
		if (pageIndex > 0) {		// <2>
			return NO_SUCH_PAGE;
		}
		else {
			final Graphics2D g2d = (Graphics2D)graphics;	// <3>
			final double width = pageFormat.getWidth(), height = pageFormat.getHeight();
			
            g2d.setColor(Color.black);						// <4>					
            g2d.setFont(MY_FONT);	            
            g2d.drawRect(5 , 5, (int)(width - 5), (int)(height - 5 - 5));
            g2d.drawString(MY_TEXT, (int)((width / 2) - (g2d.getFontMetrics().stringWidth(MY_TEXT) / 2)), (int)(height / 2));
            
			return PAGE_EXISTS;
		}
	}
	
	public static void main(String[] args) {
		final ExecutePrinterJob	epj = new ExecutePrinterJob();		// <5>
		final PrinterJob 		job = PrinterJob.getPrinterJob();
		
        if (job.printDialog()) {		// <6>
            job.setPrintable(epj);
            try{job.print();
            } catch (PrinterException ex) {
            	ex.printStackTrace();
            }
        }		
	}
}
