package edu.miu.spring_ai_demo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

public class FileLoggingConsumer implements Consumer<String> {

    private final BufferedWriter writer;

    public FileLoggingConsumer(String filePath) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filePath, true)); // append mode
    }

    @Override
    public void accept(String line) {
        try {
            writer.write(line);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace(); // fallback logging
        }
    }

    public void close() throws IOException {
        writer.close();
    }
}
