package edu.miu.spring_ai_demo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
class DateTimeTool {
    private Logger logger = LoggerFactory.getLogger(DateTimeTool.class);

    @Tool(description = "Get the current date and time in the user's timezone")
    public String getCurrentDateTime() {
        logger.info("Tool called: getCurrentDateTime");
        return LocalDateTime.now()
                .atZone(LocaleContextHolder.getTimeZone().toZoneId())
                .toString();
    }

    @Tool(description = "Get the current unix timestamp")
    public long getUnixTimestamp() {
        logger.info("Tool called: getUnixTimestamp");
        return Instant.now().getEpochSecond();
    }
}

