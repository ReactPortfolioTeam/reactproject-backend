package com.reactproject.minishop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateFormat {

	@Test
	public void 포맷() {
		Date target = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String newone = simpleDateFormat.format(target);
		System.out.println(newone);
	}
}
