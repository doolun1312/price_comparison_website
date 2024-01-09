package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class EyeonsScraper extends Thread{

    private int crawlDelay = 1;

    //Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    private List<String> glassestoSearch = new ArrayList<>();

    public EyeonsScraper() {
        glassestoSearch.add("VE3186");
        glassestoSearch.add("RX5228");
        glassestoSearch.add("VO5286");
        glassestoSearch.add("VO5407");
        glassestoSearch.add("GU7554");
        glassestoSearch.add("CK20510");
        glassestoSearch.add("HG 1088");
        glassestoSearch.add("4128");
        glassestoSearch.add("RB4101");
        glassestoSearch.add("GG1010S");
        glassestoSearch.add("VE4378");
        glassestoSearch.add("OO9238");
        glassestoSearch.add("1557");

    }

    @Override
    public void run(){
        runThread = true;
        int page = 1; // Start with the first page

        // While loop will keep running until runThread is set to false;
        while(runThread){
            System.out.println("Scraper 1 thread is scraping data from page " + page);

            try {

                Hibernate_Session hibernate = new Hibernate_Session();

                //Set up the SessionFactory
                hibernate.init();

                for (String glasstoSearch : glassestoSearch) {
                    // Download HTML document from website
                    Document doc = Jsoup.connect("https://www.eyeons.com/index.php?route=product/search&search=" + glasstoSearch).get();

                    String website = "Eyeons";

                    // Get all of the products on the page
                    Elements prods = doc.select(".item");

                    // Work through the products
                        // Get the product description
                        Elements name = prods.get(1).select(".product-name");

                        // Get the product price
                        Elements price = prods.get(1).select(".price");

                        // Get the product link
                        Elements link = prods.get(1).select(".product-name a");
                        String productLink = link.attr("href");

                        // Get the product image
                        Elements image = prods.get(1).select(".image img");
                        String productImage = image.attr("src");


                        Document doc1 = Jsoup.connect(productLink).get();

                        // Get all of the info on the page
                        Elements brandContain = doc1.select(".tab-tableItem:contains(Manufacturer)");
                        String brand = brandContain.text().replace("Manufacturer:", "").trim();
                        Elements description = doc1.select(".overview-box");
                        Elements size = doc1.select(".hcol-attribute");
                        Elements modelContain = doc1.select(".tab-tableItem:contains(Model)");
                        String model = modelContain.text().replace("Model:", "").trim();



                        if (name.isEmpty() && brand.isEmpty() && size.isEmpty() && description.isEmpty() && productImage.isEmpty()) {

                        } else {
                            // Output the data that we have downloaded
                            System.out.println("WEBSITE: " + website +
                                    " NAME: " + name.text() +
                                    " MODEL: " + model +
                                    " DESCRIPTION: " + description.text() +
                                    " BRAND: " + brand +
                                    " PRICE: " + price.text() +
                                    " LINK: " + productLink +
                                    " IMAGE: " + productImage +
                                    " SIZE: " + size.text());

                            // Check if the product already exists in the database
                            if (!hibernate.searchEyewear(name.text())) {
                                // Product doesn't exist, add it to the database
                                hibernate.addEyewear(name.text(), model, description.text(), productImage, brand, size.text(), productLink, price.text());
                            } else {
                                // Product already exists, you may want to log or handle this case
                                System.out.println("Product already exists in the database: " + name.text());
                            }
                        }
                }

                // Download HTML document from website
                Document doc = Jsoup.connect("https://www.eyeons.com/eyeglasses/?page=" + page).get();

                String website = "Eyeons";

                // Get all of the products on the page
                Elements prods = doc.select(".item");

                // Work through the products
                for(int i=0; i<prods.size(); ++i){
                    // Get the product description
                    Elements name = prods.get(i).select(".product-name");

                    // Get the product price
                    Elements price = prods.get(i).select(".price");

                    // Get the product link
                    Elements link = prods.get(i).select(".product-name a");
                    String productLink = link.attr("href");

                    // Get the product image
                    Elements image = prods.get(i).select(".image img");
                    String productImage = image.attr("data-src");


                    Document doc1 = Jsoup.connect(productLink).get();

                    // Get all of the info on the page
                    Elements brandContain = doc1.select(".tab-tableItem:contains(Manufacturer)");
                    String brand = brandContain.text().replace("Manufacturer:", "").trim();
                    Elements description = doc1.select(".overview-box");
                    Elements size = doc1.select(".hcol-attribute");
                    Elements modelContain = doc1.select(".tab-tableItem:contains(Model)");
                    String model = modelContain.text().replace("Model:", "").trim();



                    if (name.isEmpty() && brand.isEmpty() && size.isEmpty() && description.isEmpty() && productImage.isEmpty()) {

                    } else {
                        // Output the data that we have downloaded
                        System.out.println("WEBSITE: " + website +
                                " NAME: " + name.text() +
                                " MODEL: " + model +
                                " DESCRIPTION: " + description.text() +
                                " BRAND: " + brand +
                                " PRICE: " + price.text() +
                                " LINK: " + productLink +
                                " IMAGE: " + productImage +
                                " SIZE: " + size.text());

                        // Check if the product already exists in the database
                        if (!hibernate.searchEyewear(name.text())) {
                            // Product doesn't exist, add it to the database
                            hibernate.addEyewear(name.text(), model, description.text(), productImage, brand, size.text(), productLink, price.text());
                        } else {
                            // Product already exists, you may want to log or handle this case
                            System.out.println("Product already exists in the database: " + name.text());
                        }
                    }
                }

                //Shut down Hibernate
                hibernate.shutDown();

                if (page>10) {
                    // No next page, stop scraping
                    stopThread();
                } else {
                    // Move to the next page
                    page++;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Sleep for the crawl delay, which is in seconds
            try{
                sleep(1000 * crawlDelay); // Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
            } catch(InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    //Other classes can use this method to terminate the thread.
    public void stopThread(){
        runThread = false;
    }
}
