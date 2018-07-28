package examples2;

import java.util.Calendar;
import java.util.Date;

public class DateAndCalendar {
	public static void main(String[] args) {
		final Date		now = new Date(System.currentTimeMillis());	// <1>
		final Calendar	cal = Calendar.getInstance();				// <2>
		
		cal.setTime(now);											// <3>
		cal.add(Calendar.DAY_OF_MONTH,-1);
		
		final Date		yesterday = cal.getTime();					// <4>
		System.err.println("Yesterday: "+yesterday+" "+cal.getTimeInMillis()+" "+yesterday.getTime());	// <5>
	}
}
