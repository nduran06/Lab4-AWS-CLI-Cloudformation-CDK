package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Arrays;

public class HelloCdkApp {
	public static void main(final String[] args) {
		App app = new App();

		new HelloCdkStack(app, "HelloCdkStack",
				StackProps.builder().env(Environment.builder().account("645349541441").region("us-east-1").build())

						.build());

		app.synth();
	}
}
