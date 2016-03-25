package com.apexsoft.core.hadoop.util;

import java.util.Random;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=0;i<500000;i++){
			int number = new Random().nextInt(10000000) +1;
			System.out.println(number);
		}
	}

}
