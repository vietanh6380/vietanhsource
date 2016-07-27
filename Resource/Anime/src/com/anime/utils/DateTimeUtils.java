package com.anime.utils;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

	@SuppressWarnings("deprecation")
	public static Date convertDateTime(String date) {
		Calendar cal = Calendar.getInstance();

		if (date.equalsIgnoreCase(AnimeContants.TODAY) || date.contains("ago")) {
			return cal.getTime();
		} else if (date.equalsIgnoreCase(AnimeContants.YESTERDAY)) {
			cal.add(Calendar.DATE, -1);
			return cal.getTime();
		} else {
			return new Date(date);
		}
	}

}
