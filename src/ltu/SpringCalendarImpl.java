package ltu;

import java.util.Calendar;
import java.util.Date;

public class SpringCalendarImpl implements ICalendar
{
	@Override
	public Date getDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 0, 1);
		return cal.getTime();
	}

}
