package edu.miu.spring_ai_demo;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

record AdditionRequest(long a, long b) { };
record AdditionResponse(long result) {};

public class AdditionTool implements Function<AdditionRequest, AdditionResponse>{
    private Logger logger = LoggerFactory.getLogger(AdditionTool.class);
    

    public AdditionResponse apply(AdditionRequest request) {
        logger.info("adding " + request.a() + " " + request.b());
        return new AdditionResponse(request.a() + request.b());
    }   
}
