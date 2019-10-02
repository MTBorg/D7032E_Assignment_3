package ltu;

import static ltu.CalendarFactory.getCalendar;
import static org.junit.Assert.*;

import java.io.IOException;

import static ltu.CalendarFactory.getCalendar;

import org.junit.Test;
import ltu.SpringCalendarImpl;
import ltu.PaymentImpl;

public class PaymentTest{
		private final int FULL_LOAN = 7088;
		private final int FULL_SUBSIDY = 2816;
		private final int LESS_THAN_FULL_TIME_LOAN = 3564;
		private final int LESS_THAN_FULL_TIME_SUBSIDY = 1396;

    @Test
    // [ID: 101] The student must be at least 20 years old to receive subsidiary and student loans.
    public void age_101() throws IOException {
    	PaymentImpl payment = new PaymentImpl(getCalendar());
    	
    	// Student under 20
    	int LT_20 = payment.getMonthlyAmount("20000101-0000", 0, 100, 100);
    	assertTrue(LT_20 == 0);
    	
    	// Student over 20
    	int over_20 = payment.getMonthlyAmount("19970101-0000", 0, 100, 100);
    	assertTrue(over_20 != 0);
    }
    
    @Test
    // [ID: 102] The student may receive subsidiary until the year they turn 56.
    public void age_102() throws IOException {
    	PaymentImpl payment = new PaymentImpl(getCalendar());
    	
    	int is_56 = payment.getMonthlyAmount("19590101-0000", 0, 100, 100);
    	assertTrue(is_56 == 0);
    }
    
    @Test
    // [ID: 103] The student may not receive any student loans from the year they turn 47.
    public void age_103() throws IOException {
    	PaymentImpl payment = new PaymentImpl(getCalendar());
    	
    	int is_47 = payment.getMonthlyAmount("19720101-0000", 0, 100, 100);
    	assertTrue(is_47 == FULL_SUBSIDY);
    }
    

		// The student must be studying at least half time to receive any subsidiary.
		@Test
		public void minimumStudyPace201() throws IOException{
			PaymentImpl paymentImpl = new PaymentImpl(new SpringCalendarImpl());
			assert(
				paymentImpl.getMonthlyAmount("19931212-1337", 0, 100, 100) -
				paymentImpl.getMonthlyAmount("19931212-1337", 0, 0, 100) == FULL_SUBSIDY
			);
		}

	  // A student studying less than full time is entitled to 50% subsidiary.
		@Test
		public void less_than_full_time_half_subsidiary202() throws IOException{
			PaymentImpl paymentImpl = new PaymentImpl(new SpringCalendarImpl());
			assert(
				paymentImpl.getMonthlyAmount("19931212-1337", 0, 100, 100) -
				paymentImpl.getMonthlyAmount("19931212-1337", 0, 51, 100) == FULL_SUBSIDY * 0.5
			);
		}

	  //  A student studying full time is entitled to 100% subsidiary.
		@Test
		public void full_time_full_subsidiary203() throws IOException{
			PaymentImpl paymentImpl = new PaymentImpl(new SpringCalendarImpl());
			assertEquals(
				paymentImpl.getMonthlyAmount("19931212-1337", 0, 100, 100), FULL_SUBSIDY + FULL_LOAN
			);
		}

		@Test
    public void max_income_301() throws IOException {
    	
    	PaymentImpl payment = new PaymentImpl(getCalendar());
    	
    	/*
    	 * [ID: 301] A student who is studying full time or more 
    	 * is permitted to earn a maximum of 85 813SEK per year 
    	 * in order to receive any subsidiary or student loans.
    	 * */
    	assertEquals(payment.getMonthlyAmount("19940902-0000", 85814, 100, 100), 0);
    	assertEquals(payment.getMonthlyAmount("19940902-0000", 85814, 150, 100), 0);
    	assertEquals(payment.getMonthlyAmount("19940902-0000", 85813, 100, 100), 7088 + 2816);
    	assertEquals(payment.getMonthlyAmount("19940902-0000", 85813, 150, 100), 7088 + 2816);

    }

    @Test
    public void max_income_302() throws IOException {
    	
    	PaymentImpl payment = new PaymentImpl(getCalendar());
    	
    	/*
    	 * [ID: 302] A student who is studying less than full time
    	 * is allowed to earn a maximum of 128 722SEK per year 
    	 * in order to receive any subsidiary or student loans.
    	 * */
    	assertEquals(payment.getMonthlyAmount("19940902-0000", 128723, 99, 100), 0);
    	assertEquals(payment.getMonthlyAmount("19940902-0000", 128722, 99, 100), 3564 + 1396);

    }

    @Test
    // [ID: 401] A student must have completed at least 50% of previous studies in order to receive any subsidiary or student loans.
    public void compl_ratio401() throws IOException {
    	PaymentImpl payment = new PaymentImpl(getCalendar());
    	
    	int ratio_50 = payment.getMonthlyAmount("19970101-0000", 0, 100, 50);
    	assertTrue(ratio_50 != 0);
    	
    	int ratio_LT_50 = payment.getMonthlyAmount("19970101-0000", 0, 100, 40);
    	assertTrue(ratio_LT_50 == 0);
    }
    

    @Test
    // Full time students
    // [ID: 501] Student loan: 7088 SEK / month
    // [ID: 502] Subsidiary: 2816 SEK / month
    public void full_time_loan501_502() throws IOException {
    	PaymentImpl payment = new PaymentImpl(getCalendar());
    	
    	int full_time = payment.getMonthlyAmount("19970101-0000", 0, 100, 100);
    	assertTrue(full_time == FULL_LOAN + FULL_SUBSIDY);
    }
    

	  //  A student studying full time is entitled to 100% subsidiary.
		@Test
		public void less_than_full_time_students_loan503() throws IOException{
			PaymentImpl paymentImpl = new PaymentImpl(new SpringCalendarImpl());
			assertEquals(
				paymentImpl.getMonthlyAmount("19931212-1337", 0, 50, 100) - LESS_THAN_FULL_TIME_SUBSIDY,
				LESS_THAN_FULL_TIME_LOAN
			);
		}
    
	  //  A student studying full time is entitled to 100% subsidiary.
		@Test
		public void less_than_full_time_students_subsidiary504() throws IOException{
			PaymentImpl paymentImpl = new PaymentImpl(new SpringCalendarImpl());
			assertEquals(
				paymentImpl.getMonthlyAmount("19931212-1337", 0, 50, 100) - LESS_THAN_FULL_TIME_LOAN, 
				LESS_THAN_FULL_TIME_SUBSIDY
			);
		}

    @Test
    // [ID: 505] A person who is entitled to receive a student loan will always receive the full amount.
    public void full_amount505() throws IOException {
    	PaymentImpl payment = new PaymentImpl(getCalendar());
    	
    	int full_amount_full_time = payment.getMonthlyAmount("19970101-0000", 0, 100, 100);
    	assertTrue(full_amount_full_time == FULL_LOAN + FULL_SUBSIDY);
    	
    	int full_amount_half_time = payment.getMonthlyAmount("19970101-0000", 0, 50, 100);
    	assertTrue(full_amount_half_time == LESS_THAN_FULL_TIME_LOAN + LESS_THAN_FULL_TIME_SUBSIDY);
    }

    @Test
    public void paid_last_weekday_month_506() throws IOException {
    	
    	PaymentImpl payment = new PaymentImpl(getCalendar());
    	
    	String paymentDate = payment.getNextPaymentDay();
    	/**
    	 * [ID: 506] Student loans and subsidiary is paid 
    	 * on the last weekday (Monday to Friday) every month.
    	 * */
    	//assertEquals(payment.getNextPaymentDay(), );
    	//System.out.println(paymentDate);
    	assertEquals(paymentDate, "20191031");
    }
}
