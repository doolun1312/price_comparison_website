package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * A web scraper for Alensa website to extract eyewear information.
 * This class extends Thread to allow concurrent scraping.
 */
public class AlensaScraper extends Thread {

    /** The crawl delay in seconds. */
    private int crawlDelay = 1;

    /** Indicates whether the thread should continue running. */
    volatile private boolean runThread = false;

    /** List of eyewear models to search on Alensa website. */
    private List<String> glassestoSearch = new ArrayList<>();

    /**
     * Initializes the AlensaScraper with a list of eyewear models to search.
     */
    public AlensaScraper() {
        glassestoSearch.add("RX5228");
        glassestoSearch.add("VO5286");
        glassestoSearch.add("VE3186");
        glassestoSearch.add("GU7554");
        glassestoSearch.add("CK20510");
        glassestoSearch.add("4128");
        glassestoSearch.add("RB4101");
        glassestoSearch.add("GG1010S");
        glassestoSearch.add("VE4378");
        glassestoSearch.add("OO9238");
        glassestoSearch.add("1557");
    }

    /**
     * The main logic for scraping eyewear information from the Alensa website.
     * Overrides the run method of the Thread class.
     */
    @Override
    public void run() {
        runThread = true;
        int page = 3; // Start with the first page

        // While loop will keep running until runThread is set to false;
        while (runThread) {
            System.out.println("Scraper 5 thread is scraping data from page " + page);

            // WEB SCRAPING CODE GOES HERE
            try {
                // Initialize Hibernate session
                Hibernate_Session hibernate = new Hibernate_Session();
                hibernate.init();

                for (String glasstoSearch : glassestoSearch) {
                    // Download HTML document from website
                    Document doc = Jsoup.connect("https://www.alensa.co.uk/search?phrase=" + glasstoSearch).get();
                    String website = "Alensa";

                    // Get all of the products on the page
                    Elements prods = doc.select(".product");

                    // Work through the products
                    // Get the product description
                    Elements name = prods.get(0).select(".product-name");

                    // Get the product price
                    Elements price = prods.get(0).select(".product-price-actual");

                    // Get the product link
                    String link = prods.get(0).attr("href");

                    // Get the product image
                    Elements image = prods.get(0).select(".product-image img");
                    String productImage = image.attr("src");

                    Document doc1 = Jsoup.connect(link).get();

                    Elements brandContain = doc1.select("#properties > table > tbody > tr:nth-child(2) > td > a");
                    String brand = brandContain.text();

                    String modelText = name.text();
                    String model = modelText.substring(modelText.indexOf(brand) + brand.length()).trim();

                    // Get all of the info on the page
                    Element description = doc1.select(".product-generic-info").first();
                    Elements size = doc1.select(".form-pair");

                    if (name.isEmpty() && brand.isEmpty() && productImage.isEmpty()) {
                        // Handle empty data if needed
                    } else {
                        // Output the data that we have downloaded
                        System.out.println("WEBSITE: " + website +
                                " NAME: " + name.text() +
                                " MODEL: " + model +
                                " DESCRIPTION: " + description.text() +
                                " BRAND: " + brand +
                                " PRICE: " + price.text() +
                                " LINK: " + link +
                                " IMAGE: " + productImage +
                                " SIZE: " + size.text()
                        );
                    }
                    // Check if the product already exists in the database
                    if (!hibernate.searchEyewear(name.text())) {
                        // Add data to the database
                        hibernate.addEyewear(website, name.text(), model, description.text(), productImage, brand, size.text(), link, price.text());
                    } else {
                        // Product already exists, you may want to log or handle this case
                        System.out.println("Product already exists in the database: " + name.text());
                    }
                }

                // Download HTML document from website
                Document doc = Jsoup.connect("https://www.alensa.co.uk/designer-glasses-and-frames/ray-ban--oakley--calvin-klein--guess--nike--versace--tommy-hilfiger--vogue--hugo-by-hugo-boss--boss-by-hugo-boss?page=" + page).get();

                String website = "Alensa";

                // Get all of the products on the page
                Elements prods = doc.select(".product");

                // Work through the products
                for (int i = 0; i < prods.size(); ++i) {
                    // Get the product description
                    Elements name = prods.get(i).select(".product-name");

                    // Get the product price
                    Elements price = prods.get(i).select(".product-price-actual");

                    // Get the product link
                    String link = prods.get(i).attr("href");

                    // Get the product image
                    Elements image = prods.get(i).select(".product-image img");
                    String productImage = image.attr("src");

                    Document doc1 = Jsoup.connect(link).get();

                    Elements brandContain = doc1.select("#properties > table > tbody > tr:nth-child(2) > td > a");
                    String brand = brandContain.text();

                    String modelText = name.text();
                    String model = modelText.substring(modelText.indexOf(brand) + brand.length()).trim();

                    // Get all of the info on the page
                    Element description = doc1.select(".product-generic-info").first();
                    Elements size = doc1.select(".form-pair");

                    if (name.isEmpty() && brand.isEmpty() && productImage.isEmpty()) {
                        // Handle empty data if needed
                    } else {
                        // Output the data that we have downloaded
                        System.out.println("WEBSITE: " + website +
                                " NAME: " + name.text() +
                                " MODEL: " + model +
                                " DESCRIPTION: " + description.text() +
                                " BRAND: " + brand +
                                " PRICE: " + price.text() +
                                " LINK: " + link +
                                " IMAGE: " + productImage +
                                " SIZE: " + size.text()
                        );
                    }
                    // Check if the product already exists in the database
                    if (!hibernate.searchEyewear(name.text())) {
                        // Add data to the database
                        hibernate.addEyewear(website, name.text(), model, description.text(), productImage, brand, size.text(), link, price.text());
                    } else {
                        // Product already exists, you may want to log or handle this case
                        System.out.println("Product already exists in the database: " + name.text());
                    }
                }

                // Shut down Hibernate
                hibernate.shutDown();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Sleep for the crawl delay, which is in seconds
            try {
                sleep(1000 * crawlDelay); // Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    /**
     * Stops the thread when called by other classes.
     */
    public void stopThread() {
        runThread = false;
    }
}
