package ltu;

import static ltu.CalendarFactory.getCalendar;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class PaymentTest
{   
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
    	assertTrue(is_47 == 2816);
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
}
