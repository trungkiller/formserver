package com.example.FormServer.selenium;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertPostCode {

	public static String checking(String postCode) {

		String[] splitArray = postCode.split(",");
		String regex = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";

		Pattern pattern = Pattern.compile(regex);

		for (int index = splitArray.length - 1; index >= 0; index--) {
			String[] i = splitArray[index].split(" ", 0);
			String postcode = Arrays.asList(i).get(i.length - 2) + " " + Arrays.asList(i).get(i.length - 1);
			Matcher matcher = pattern.matcher(postcode);
//			System.out.println(matcher.matches());
			if (matcher.matches()) {
				return postcode;
			}
		}
		return postCode;
	}
}