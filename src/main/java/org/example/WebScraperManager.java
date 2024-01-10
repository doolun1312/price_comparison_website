package org.example;

import java.util.List;

/**
 * The type Web scraper manager.
 */
public class WebScraperManager {
    private static List<Thread> scraperList;

    /**
     * Instantiates a new Web scraper manager.
     */
//Empty constructor
    WebScraperManager() {
    }

    /**
     * Gets scraper list.
     *
     * @return the scraper list
     */
//Getter
    public static List<Thread> getScraperList() {
        return scraperList;
    }

    /**
     * Sets scraper list.
     *
     * @param sList the s list
     */
//Setter
    public static void setScraperList(List<Thread> sList) {
        scraperList = sList;
    }

    /**
     * Start threads.
     */
//Start TVScrapers
    public void startThreads() {
        for (Thread TVScraper : scraperList) {
            TVScraper.start();
        }
    }

    /**
     * Join threads.
     */
//Join TVScrapers
    public void joinThreads() {
        for (Thread TVScraper : scraperList) {

            try {
                TVScraper.join();
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}