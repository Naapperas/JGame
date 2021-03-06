package jGame;

import java.util.LinkedList;

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
	
	public static String[] inArray(String[] array1, String[] array2) {
		
	    LinkedList<String> ll = new LinkedList<>();
	    
	    outer: for (String s1 : array1){
	      for (String s2 : array2){
	        if(s2.contains(s2) && !ll.contains(s1)){
	          ll.add(s1);
	          continue outer;
	        }
	      } 
	    }
	    return (String[]) ll.toArray();
	  }
}
