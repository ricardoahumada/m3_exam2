package com.ncm.util;

public class StringUtils {

	public final static int[] toIntArray(String list) {
		if (list != null) {
			String[] items = list.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

			int[] results = new int[items.length];

			for (int i = 0; i < items.length; i++) {
				try {
					results[i] = Integer.parseInt(items[i]);
				} catch (NumberFormatException nfe) {
				}
			}
			
			return results;
			
		} else return null;
	}

}
