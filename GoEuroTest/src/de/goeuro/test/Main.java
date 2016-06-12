package de.goeuro.test;

import de.goeuro.test.services.TransformationService;

public class Main {
	public static void main(String[] args) {
		if(args.length == 0) {
			System.err.println("Please enter city name!");
			return;
		}
		String cityName = args[0];
		TransformationService transformationService = new TransformationService();
		transformationService.transform(cityName);
	}
}