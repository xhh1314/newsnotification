package newsnotification.dao;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import cn.haiwai.newsnotification.manage.util.TimeTransfer;

public class InstantTest {

	public static void main(String[] args) {
		Instant time = Instant.now();
		DateTimeFormatter formate = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);

		//System.out.println(time.);
		LocalDate date=LocalDate.now();
		System.out.println(date.toString());
		LocalDate date1=LocalDate.parse("2017-01-02",formate);
		//date1.
		System.out.println(TimeTransfer.stringToDate("2017-01-02").toString());

	}

}
