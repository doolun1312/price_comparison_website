package org.example;

import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.select.Elements;

public class EyebuydirectSraper extends Thread{

    private int crawlDelay = 1;

    //Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    @Override
    public void run(){
        runThread = true;
        int page = 1; // Start with the first page

        //While loop will keep running until runThread is set to false;
        while(runThread){
            System.out.println("Scraper 3 thread is scraping data from page " + page);

            //WEB SCRAPING CODE GOES HERE
            try{

                Hibernate_Session hibernate = new Hibernate_Session();

                //Set up the SessionFactory
                hibernate.init();

                //Name of item that we want to scrape
                String itemName = "glasses";

                //Download HTML document from website
                Document doc = Jsoup.connect("https://www.eyebuydirect.com/eyeglasses/s?name=" + itemName + "&page=" + page).get();

                String website = "Eyebuydirect";
                //Get all of the products on the page
                Elements prods = doc.select(".item");

                //Work through the products
                for(int i=0; i<prods.size(); ++i){

                    //Get the product description
                    Elements name = prods.get(i).select(".item-name");

                    //Get the product price
                    Elements price = prods.get(i).select(".normal-price");

                    // Get the product link
                    Elements link = prods.get(i).select(".event-list");
                    String productLink = "https://www.eyebuydirect.com" + link.attr("href");

                    // Get the product image
                    Elements image = prods.get(i).select(".item-image img");
                    String productImage = image.attr("data-src");

                    Document doc1 = Jsoup.connect(productLink).get();

                    // Get all of the info on the page
//                    Elements brandContain = doc1.select(".product-tag a img");
//                    String brand = brandContain.attr("alt");


                    Elements description = doc1.select(".desc-text");
                    Elements size = doc1.select(".size");

                    // Assuming you have already obtained the brand information from the HTML element with the class "item-name"
                    Elements brandElement = prods.get(i).select(".item-name");
                    String brand = brandElement.text().trim();

// Split the brand into words
                    String[] brandWords = brand.split("\\s+");
                    String model = "";

// Check if the brand contains "ray-ban"
                    if (brand.toLowerCase().contains("ray-ban")) {
                        // Set "ray-ban" as the brand and the rest as the model
                        model = brand.replace("Ray-Ban", "").trim();
                        brand = "Ray-Ban";
                        System.out.println("Brand: " + brand);
                        System.out.println("Model: " + model);
                    } else if (brand.toLowerCase().contains("oakley")) {
                        // Set "Oakley" as the brand and the rest as the model
                        model = brand.replace("Oakley", "").trim();
                        brand = "Oakley";
                        System.out.println("Brand: " + brand);
                        System.out.println("Model: " + model);
                    } else if (brand.toLowerCase().contains("vogue eyewear")) {
                        // Set "Vogue eyewear" as the brand and the rest as the model
                        model = brand.replace("Vogue Eyewear", "").trim();
                        brand = "Vogue Eyewear";
                        System.out.println("Brand: " + brand);
                        System.out.println("Model: " + model);
                    } else {
                        // If none of the specific cases match, you can use your original logic
                        brandWords = brand.split("\\s+");
                        if (brandWords.length > 1) {
                            model = brandWords[brandWords.length - 1];
                            brand = brand.substring(0, brand.lastIndexOf(model)).trim();
                        } else {
                            model = brand;
                        }
                    }


                    if (name.isEmpty() && brand.isEmpty() && size.isEmpty()  && description.isEmpty()  && productImage.isEmpty()) {

                    } else {

                        // Output the data that we have downloaded
                        System.out.println("WEBSITE: " + website +
                                " NAME: " + name.text() +
                                " MODEL: " + model +
                            " DESCRIPTION: " + description.text() +
                                " BRAND: " + brand+
                            " PRICE: " + price.text() +
                                " LINK: " + productLink +
                            " IMAGE: " + productImage +
                                " SIZE: " + size.text());

                        // Check if the product already exists in the database
                        if (!hibernate.searchEyewear(name.text())) {
                            //add data to database
                            hibernate.addEyewear(name.text(), model, description.text(), productImage, brand, size.text(), productLink, price.text());//Add data
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

            }
            catch(Exception ex){
                ex.printStackTrace();
            }


            //Sleep for the crawl delay, which is in seconds
            try{
                sleep(1000 * crawlDelay);//Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
            }
            catch(InterruptedException ex){
                System.err.println(ex.getMessage());
            }
        }
    }

    //Other classes can use this method to terminate the thread.
    public void stopThread(){
        runThread = false;
    }
}


