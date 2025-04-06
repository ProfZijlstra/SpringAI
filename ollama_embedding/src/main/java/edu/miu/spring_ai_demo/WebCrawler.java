package edu.miu.spring_ai_demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebCrawler {
    Logger logger = LoggerFactory.getLogger(WebCrawler.class);

    // Track visited URLs to avoid cycles
    private Set<String> visitedUrls = new HashSet<>();
    // Store collected pages
    private List<org.springframework.ai.document.Document> pages = new ArrayList<>();
    // Base domain to restrict crawling
    private String baseDomain;
    // To restrict crawling to a specific directory structure
    private List<String> urlContains = new ArrayList<>();
    private List<String> urlNotContains = new ArrayList<>();
    // Maximum pages to crawl (safety limit)
    private int maxPages = 1000;
    // Politeness delay between requests (in milliseconds)
    private int delayMs = 1000;

    public WebCrawler(String baseDomain) {
        Assert.hasText(baseDomain, "Base domain must not be empty");
        this.baseDomain = baseDomain.toLowerCase();
    }

    public void setUrlContains(List<String> urlContains) {
        Assert.notNull(urlContains, "URL contains must not be null");
        this.urlContains = urlContains;
    }

    public void setUrlNotContains(List<String> urlNotContains) {
        Assert.notNull(urlNotContains, "URL not contains must not be null");
        this.urlNotContains = urlNotContains;
    }

    // Optional: Set maximum pages to crawl
    public void setMaxPages(int maxPages) {
        Assert.isTrue(maxPages > 0, "Max pages must be positive");
        this.maxPages = maxPages;
    }

    // Optional: Set delay between requests
    public void setDelayMs(int delayMs) {
        Assert.isTrue(delayMs >= 0, "Delay must be non-negative");
        this.delayMs = delayMs;
    }

    // to start crawling
    public List<org.springframework.ai.document.Document> crawl(String startUrl) {
        Assert.hasText(startUrl, "Start URL must not be empty");
        crawlPage(startUrl);
        return new ArrayList<>(pages);
    }

    private void crawlPage(String url) {
        // Check limits and if already visited
        if (pages.size() >= maxPages || visitedUrls.contains(url)) {
            return;
        }

        try {
            // Mark as visited
            visitedUrls.add(url);

            // Fetch page
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (compatible; WebsiteCrawler/1.0)")
                    .timeout(10000)
                    .get();

            // Extract content
            String textContent = doc.body().text();
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("site", baseDomain);
            metadata.put("title", doc.title());
            metadata.put("url", url);
            metadata.put("timestamp", System.currentTimeMillis());
            var page = new org.springframework.ai.document.Document(textContent, metadata);
            pages.add(page);

            logger.info("Crawled: " + url + " (" + pages.size() + " pages)");

            // Find all links
            // Elements links = doc.select("a[href]");
            // link:
            // for (Element link : links) {
            //     String nextUrl = link.absUrl("href");

            //     for (String contains : urlContains) {
            //         if (!nextUrl.contains(contains)) {
            //             logger.debug("not crawling: " + nextUrl);
            //             continue link;
            //         }
            //     }

            //     for (String notContains : urlNotContains) {
            //         if (nextUrl.contains(notContains)) {
            //             logger.debug("not crawling: " + nextUrl);
            //             continue link;
            //         }
            //     }

            //     // Only follow links within the same domain that we haven't visited yet
            //     if (isSameDomain(nextUrl) && !visitedUrls.contains(nextUrl)) {
            //         // Be polite - wait between requests
            //         Thread.sleep(delayMs);
            //         crawlPage(nextUrl);
            //     }
            // }

        } catch (IOException e) {
            logger.error("Error crawling " + url + ": " + e.getMessage());
        } 
        // catch (InterruptedException e) {
        //     Thread.currentThread().interrupt();
        //     logger.error("Crawling interrupted at " + url);
        // }
    }

    private boolean isSameDomain(String url) {
        try {
            String host = new java.net.URL(url).getHost().toLowerCase();
            return host.equals(baseDomain) || host.endsWith("." + baseDomain);
        } catch (Exception e) {
            return false;
        }
    }

    // Main method for demonstration
    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler("docs.spring.io");
        crawler.setUrlContains(List.of("spring-ai/reference"));
        crawler.setUrlNotContains(List.of("#", "/1.0/"));
        crawler.setMaxPages(500); // Limit to 500 pages
        crawler.setDelayMs(200); // 0.2 seconds between requests

        String startUrl = "https://docs.spring.io/spring-ai/reference/";
        List<org.springframework.ai.document.Document> pages = crawler.crawl(startUrl);

        // Print results
        System.out.println("\nCrawling completed. Found " + pages.size() + " pages:");
        for (org.springframework.ai.document.Document page : pages) {
            System.out.println("URL: " + page.getMetadata().get("url"));
            System.out.println("Title: " + page.getMetadata().get("title"));
            System.out.println("Content length: " + page.getText().length() + " chars");
            System.out.println("---");
        }
    }
}