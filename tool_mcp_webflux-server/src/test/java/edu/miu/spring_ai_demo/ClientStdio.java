/*
* Copyright 2024 - 2024 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package edu.miu.spring_ai_demo;

import java.io.IOException;
import java.util.Map;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.spec.McpSchema.CallToolRequest;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.ListToolsResult;

/**
 * With stdio transport, the MCP server is automatically started by the client. But you
 * have to build the server jar first:
 *
 * <pre>
 * ./mvnw clean install -DskipTests
 * </pre>
 */
public class ClientStdio {

	public static void main(String[] args) {

		// setup if we don't want to log traffic (also comment out try/catch below)
		// var stdioParams = ServerParameters.builder("java")
		// 	.args("-jar", "target/demo.springai.tool.mcp.server-0.0.1-SNAPSHOT.jar")
		// 	.build();

		// call the MCP server through Socket Cat, so that we can log traffic
		var stdioParams = ServerParameters.builder("socat")
			.args("-v","-","EXEC:java -jar target/demo.springai.tool.mcp.server-0.0.1-SNAPSHOT.jar")
			.build();

		var transport = new StdioClientTransport(stdioParams);

		// comment out the following try/catch if you're not logging traffic
		try {
			// stderr receives socket cat output, hook up consumer to receive it
			var stderrLogger = new FileLoggingConsumer("server-traffic.log");
			transport.setStdErrorHandler(stderrLogger);
		} catch (IOException e) {
			throw new RuntimeException("Failed to set up log file", e);
		}

		var client = McpClient.sync(transport).build();

		client.initialize();

		// List and demonstrate tools
		ListToolsResult toolsList = client.listTools();
		System.out.println("Available Tools = " + toolsList);

		CallToolResult currentDateTime = client.callTool(new CallToolRequest("getCurrentDateTime", Map.of()));
		System.out.println("Current date time: " + currentDateTime.content());

		CallToolResult added = client.callTool(new CallToolRequest("add", Map.of("a", 5, "b", 3)));
		System.out.println("Added: " + added.content());

		client.closeGracefully();
	}

}
