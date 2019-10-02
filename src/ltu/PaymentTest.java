package ltu;

import static org.junit.Assert.*;

import java.io.IOException;

import static ltu.CalendarFactory.getCalendar;

import org.junit.Test;

public class PaymentTest
{
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
