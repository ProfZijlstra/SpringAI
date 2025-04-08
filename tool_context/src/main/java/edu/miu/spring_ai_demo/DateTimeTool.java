package edu.miu.spring_ai_demo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.i18n.LocaleContextHolder;

class DateTimeTools {
    private Logger logger = LoggerFactory.getLogger(DateTimeTools.class);

    @Tool(description = "Get the current date and time in the user's timezone")
    public String getCurrentDateTime(ToolContext context) {
        ZoneId zone = ZoneId.of((String)context.getContext().get("zone"));
        long hour = (Long)context.getContext().get("hour");

        ZonedDateTime dt = LocalDateTime.now().plusHours(hour).atZone(zone);
        logger.info("Tool: getCurrentDateTime in zone " + zone + " " + dt);
        
        return dt.toString();
    }
}

//     @Tool(description = "Get the current unix timestamp")
//     public long getUnixTimestamp() {
//         logger.info("Tool called: getUnixTimestamp");
//         return Instant.now().getEpochSecond();
//     }

//     @Tool(description = "Set a user alarm for the given time")
//     public String setAlarm(@ToolParam(description = "Date and Time to set the alarm as unix timestamp") String time) {
//         logger.info("Tool called: setAlarm(" + time + ")");

//         // Convert Unix timestamp (seconds) to LocalDateTime
//         LocalDateTime dateTime = LocalDateTime.ofInstant(
//                 Instant.ofEpochSecond(Integer.parseInt(time)),
//                 ZoneId.systemDefault());

//         // Define the desired output format
//         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//         String output = dateTime.format(formatter);
//         logger.info("Alarm set for: " + output);

//         // Format the date time to string
//         return "Alarm set for: " + output;
//     }
// }