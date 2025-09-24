// package edu.miu.spring_ai_demo;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.apache.pdfbox.Loader;
// import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
// import org.apache.pdfbox.pdmodel.PDDocument;
// import org.apache.pdfbox.text.PDFTextStripper;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.ai.document.Document;

// public class PdfReader {
//     private static final Logger logger = LoggerFactory.getLogger(PdfReader.class);

//     private String filename;

//     public PdfReader(String file) {
//         this.filename = file;
//     }

//     public List<Document> read() {
//         logger.info("Attempting to open: {}", filename);
//         try (PDDocument document = Loader.loadPDF(new RandomAccessReadBufferedFile("target/classes/" + filename))) {
//             logger.info("Reading from: {}", filename);
//             List<Document> docs = new ArrayList<>();

//             // Create PDFTextStripper to extract text
//             PDFTextStripper pdfStripper = new PDFTextStripper();

//             // setup metadata
//             Map<String, Object> metadata = new HashMap<>();
//             metadata.put("file", filename);
//             metadata.put("page", 0);
//             metadata.put("timestamp", System.currentTimeMillis());

//             // Extract text from the PDF page by page
//             int pages = document.getNumberOfPages();
//             for (int i = 1; i <= pages; i++) {
//                 logger.trace("Reading page {}", i);
//                 pdfStripper.setStartPage(i);
//                 pdfStripper.setEndPage(i);
//                 String text = pdfStripper.getText(document);

//                 // skip pages that are mostly empty (only page number)
//                 if (text.length() < 3) {
//                     logger.trace("Skipping page: {}", i);
//                     continue;
//                 }

//                 // Store pages as Spring AI documents
//                 metadata.put("page", i);
//                 Document doc = new Document(text, metadata);
//                 docs.add(doc);
//             }
//             return docs;
//         } catch (IOException ex) {
//             String cwd = System.getProperty("user.dir");
//             logger.warn("Unable to read: {} from {}", filename, cwd);
//             return null;
//         }
//     }

//     public static void main(String[] args) {
//         PdfReader reader = new PdfReader("W1D2.pdf");
//         List<Document> docs = reader.read();
//         int page = 1;
//         for (Document doc : docs) {
//             System.out.printf("------- page: %3d ----------\n", page++);
//             System.out.println(doc.getFormattedContent());
//         }
//     }
// }
