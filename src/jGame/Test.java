package jGame;

import JTuples.Tuple;

public class Test {

	public static void main(String[] args) {
		
		Object[] tmp1 = {"Hi", Integer.valueOf(42), new Object()};
		
		Tuple tup = Tuple.from(tmp1);
		
		System.out.println(tup.toString());
		
		Object[] tmp2 = {"Hello World!", true, 45.65f, 1000L};
		
		Tuple tup2 = tup.add(tmp2);
		
		System.out.println(tup2.toString());
		
		System.out.println(tup == tup2);
		
	}
}
