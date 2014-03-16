package edu.metu.se560.model;

public class Utils {

	public static double sumArray(double[] ds, int size) {
		double result = 0.;
		for (int i = 0; i < size; i++) {
			result += ds[i];
		}
		return result;
	}

	public static Object parseTill(String str, String till) {
		int ndx = str.indexOf(till);
		if (ndx>-1) {
			return str.substring(0, ndx);
		} else {
			return str;
		}
	}

	public static String parseBetween(String str, String from, String to) {
		int index = str.indexOf(from);
		if (index>-1) {
			int start = index+from.length();
			int toNdx = str.substring(start).indexOf(to);
			if (toNdx>-1) {
				return str.substring(start, start+toNdx);
			}
		}
		return "";
	}
	
	public static void main(String[] args) {
		System.out.println(parseBetween("Deneme ali", "e","i"));
	}

}
