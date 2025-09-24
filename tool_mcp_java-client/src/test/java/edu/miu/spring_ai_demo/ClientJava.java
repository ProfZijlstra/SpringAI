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

import java.util.Map;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.spec.McpSchema.CallToolRequest;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.ListToolsResult;

public class ClientJava {
	public static void main(String[] args) {
		var stdioParams = ServerParameters.builder("java")
				.args("-jar", "target/demo.springai.tool.mcp.server-0.0.1-SNAPSHOT.jar")
				.build();
		var transportStdio = new StdioClientTransport(stdioParams);
		// var transportStreamable = HttpClientStreamableHttpTransport
		// .builder("http://localhost:8080").build();
		// var transportSse = HttpClientSseClientTransport
		// .builder("http://localhost:8080").build();
		var client = McpClient.sync(transportStdio).build();
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

