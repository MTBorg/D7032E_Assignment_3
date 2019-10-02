package ltu;

import java.util.Calendar;
import java.util.Date;


public class SaturdayCalendarImpl implements ICalendar {
	@Override
	public Date getDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 3, 30);
		return cal.getTime();
	}
}
