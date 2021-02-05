package jGame;

public class Test {

	public static void main(String[] args) {

		System.out.println(accum("HbideVbxncC"));

	}

	public static String accum(String s) {
		char[] chars = s.toCharArray();

		String result = "";

		for (int i = 0; i < chars.length; i++) {

			char c = chars[i];

			boolean firstLetter = true;

			for (int j = 0; j < i + 1; j++) {

				if (firstLetter) {
					result += String.valueOf(c).toUpperCase().toCharArray()[0];
					firstLetter = false;
				} else {
					result += String.valueOf(c).toLowerCase().toCharArray()[0];
				}
			}
			result += "-";
		}

		return result.substring(0, result.length() - 1);
	}
}
