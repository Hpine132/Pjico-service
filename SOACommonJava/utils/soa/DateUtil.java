package utils.soa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static int iCount = 9;
	
	public static String getNextDate(String curDate) {

		String nextDate = "";
		try {
			Calendar today = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(curDate);
			today.setTime(date);
			today.add(Calendar.DAY_OF_YEAR, 1);
			nextDate = format.format(today.getTime());
		} catch (Exception e) {
			return nextDate;
		}
		return nextDate;
	}

	public static String getYDOY(String dtCurr, String Trace) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = format.parse(dtCurr);
		} catch (ParseException e) {
			date = new Date();
		}
		Calendar ca1 = Calendar.getInstance();
		ca1.setTime(date);
		int iDOY = ca1.get(Calendar.DAY_OF_YEAR);
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(iDOY);
		strBuff.append("0");
		if (iDOY < 10)
			strBuff.append("00");
		else if (iDOY < 100 && iDOY >= 10)
			strBuff.append("0");
		int SEQ = getNextInt();
		strBuff.append(SEQ);
		strBuff.append(Trace);
		return strBuff.toString();

	}
	
	public static String subDateTime ( String strDateStart, String strDateEnd, String formatDate)
	{
		if((strDateStart == null) || (strDateEnd == null) || (formatDate == null))
			return "-1";
		String time = "0";
		SimpleDateFormat formatter = new SimpleDateFormat(formatDate);
		try {
			time  = String.valueOf(formatter.parse(strDateEnd).getTime() - formatter.parse(strDateStart).getTime());
		} catch (Exception e) {
			time = "-1";
		}
		return time;
	}
	
	public synchronized static int getNextInt() {
		if (iCount == 99)
			iCount = 9;
		iCount++;
		return iCount;
	}
}
