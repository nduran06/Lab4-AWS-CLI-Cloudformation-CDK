package com.myorg;

import software.constructs.Construct;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.iam.Role;
// Import Lambda function
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.FunctionUrl;
import software.amazon.awscdk.services.lambda.FunctionUrlAuthType;
import software.amazon.awscdk.services.lambda.FunctionUrlOptions;
import software.amazon.awscdk.services.lambda.Runtime;

public class HelloCdkStack extends Stack {
	public HelloCdkStack(final Construct scope, final String id) {
		this(scope, id, null);
	}

	public HelloCdkStack(final Construct scope, final String id, final StackProps props) {
		super(scope, id, props);

		// Define the Lambda function resource
		Function myFunction = Function.Builder.create(this, "HelloWorldFunction").runtime(Runtime.NODEJS_20_X) // Provide any supported Node.js runtime
				/*.handler("index.handler").code(Code.fromInline("exports.handler = async function(event) {"
																+ " return {"
																		+ " statusCode: 200," 
																		+ " body: JSON.stringify('Hello World!')"
																		+ " };" 
																+ "};"))*/
				.handler("index.handler")
				.code(Code.fromInline(
				"exports.handler = async function(event) {\n" +
				"  const queryParams = event.queryStringParameters || {};\n" +
				"  const { param1, param2, param3 } = queryParams;\n" +
				"\n" +
				"  const response = {\n" +
				"    message: '✨✨✨ Fantasy Response ✨✨✨',\n" +
				"    story: '🦄🧝🌟 In the enchanted woods of ' + (param1 || 'Oaken Glade') + ', near the borders of the mortal lands. ' +\n" +
				"           'Dwelt the last of the ' + (param2 || 'unicorns') + ', its coat shimmering like fresh snow in moonlight. ' +\n" +
				"           'The ancient ' + (param3 || 'Starweaver') + ' elves spoke of how this majestic being was born in the First Age. ' +\n" +
				"           'When magic still flowed freely through the world like morning dew. ' +\n" +
				"           'No hunter had ever caught her, no dragon had ever tasted her blood. ' +\n" +
				"           'And no spell nor enchantment had ever touched her. ' +\n" +
				"           'For she was the last of the True Immortals who remembered the worlds morning. ' +\n" +
				"           'The very trees bowed their branches as she passed. ' +\n" +
				"           'And even the most ancient of the woodland creatures whispered her true name in reverence. 🦄🧝🌟',\n" +
				"    attribution: 'Inspired by \"The Last Unicorn\" by Peter S. Beagle and \"The Silmarillion\" by J.R.R. Tolkien',\n" +
				"    footer: '✨🌈✨ Made with magic ✨🌈✨'\n" +
				"  };\n" +
				"\n" +
				"  return {\n" +
				"    statusCode: 200,\n" +
				"    headers: {\n" +
				"      'Content-Type': 'application/json',\n" +
				"      'Access-Control-Allow-Origin': '*'\n" +
				"    },\n" +
				"    body: JSON.stringify(response)\n" +
				"  };\n" +
				"};"
				))
				.role(Role.fromRoleArn(this, "LabRole", "arn:aws:iam::645349541441:role/LabRole"))
				.build();
		
		// Define the Lambda function URL resource
		FunctionUrl myFunctionUrl = myFunction
				.addFunctionUrl(FunctionUrlOptions.builder().authType(FunctionUrlAuthType.NONE).build());

		// Define a CloudFormation output for your URL
		CfnOutput.Builder.create(this, "myFunctionUrlOutput").value(myFunctionUrl.getUrl()).build();
	}
}