package ltu;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import static ltu.CalendarFactory.getCalendar;
import ltu.PaymentImpl;

public class PaymentTest
{
		private final int FULL_LOAN = 7088;
		private final int FULL_SUBSIDY = 2816;

    @Test
    public void testSilly()
    {
        assertEquals(1, 1);
    }

		// The student must be studying at least half time to receive any subsidiary.
		@Test
		public void minimumStudyPace201() throws IOException{
			PaymentImpl paymentImpl = new PaymentImpl(getCalendar());
			assert(
				paymentImpl.getMonthlyAmount("19931212-1337", 0, 100, 100) -
				paymentImpl.getMonthlyAmount("19931212-1337", 0, 0, 100) == FULL_SUBSIDY
			);
		}

	  // A student studying less than full time is entitled to 50% subsidiary.
		@Test
		public void less_than_full_time_half_subsidiary202() throws IOException{
			PaymentImpl paymentImpl = new PaymentImpl(getCalendar());
			assert(
				paymentImpl.getMonthlyAmount("19931212-1337", 0, 100, 100) -
				paymentImpl.getMonthlyAmount("19931212-1337", 0, 51, 100) == FULL_SUBSIDY * 0.5
			);
		}

	  //  A student studying full time is entitled to 100% subsidiary.
		@Test
		public void full_time_full_subsidiary203() throws IOException{
			PaymentImpl paymentImpl = new PaymentImpl(getCalendar());
			assertEquals(
				paymentImpl.getMonthlyAmount("19931212-1337", 0, 100, 100), FULL_SUBSIDY + FULL_LOAN
			);
		}
}
