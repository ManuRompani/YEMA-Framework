package main;

import java.util.Scanner;

import exceptions.ValidatorException;
import validators.DoubleValidator;
import validators.IntValidator;

public class Main {

	public static void main(String[] args) {

		 
		 Scanner sc = new Scanner(System.in);
		 System.out.println("ingrese un num");
		 double num = sc.nextDouble();
		 
		 try {
			 
			DoubleValidator.from(num)
			 	.between(2, 5);
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());;
		}
	}
}