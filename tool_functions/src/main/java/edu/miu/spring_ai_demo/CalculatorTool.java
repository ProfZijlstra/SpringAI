package edu.miu.spring_ai_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class CalculatorTool {
    private Logger logger = LoggerFactory.getLogger(CalculatorTool.class);

    @Tool(description = "Adds two numbers together")
    public long add(
        @ToolParam(description = "First number") long a, 
        @ToolParam(description = "Second number") long b) {
            logger.info("adding " + a + " " + b);
            return a + b;
    }

    @Tool(description = "Subtracts number b from a")
    public long subtract(
        @ToolParam(description = "Number a") long a, 
        @ToolParam(description = "Number b") long b) {
            logger.info("subtracting " + a + " " + b);
            return a - b;
    }
}

//     @Tool(description = "Multiply number a by b")
//     public long multiply(
//         @ToolParam(description = "Number a") long a, 
//         @ToolParam(description = "Number b") long b) {
//             logger.info("multiplying " + a + " " + b);
//             return a * b;
//     }

//     @Tool(description = "Divide number a by b")
//     public long divide(
//         @ToolParam(description = "Number a") long a, 
//         @ToolParam(description = "Number b") long b) {
//             logger.info("dividing " + a + " " + b);
//             return a / b;
//     }
// }
