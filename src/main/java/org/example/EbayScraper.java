package org.example;

import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.select.Elements;

public class EbayScraper extends Thread{

    private int crawlDelay = 1;

    //Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    @Override
    public void run(){
        runThread = true;
        int page = 1; // Start with the first page

        //While loop will keep running until runThread is set to false;
        while(runThread){
            System.out.println("Scraper 2 thread is scraping data from page " + page);

            //WEB SCRAPING CODE GOES HERE
            try{

                Hibernate_Session hibernate = new Hibernate_Session();

                //Set up the SessionFactory
                hibernate.init();

                //Name of item that we want to scrape
                String itemName = "glasses";

                //Download HTML document from website
                Document doc = Jsoup.connect("https://www.ebay.com/sch/i.html?_from=R40&_trksid=p2380057.m570.l1313&_nkw=" + itemName + "&_sacat=0&_pgn=" + page).get();

                String website = "Ebay";

                //Get all of the products on the page
                Elements prods = doc.select(".s-item");

                //Work through the products
                for(int i=1; i<prods.size(); ++i){

                    //Get the product description
                    Elements name = prods.get(i).select(".s-item__title");

                    //Get the product price
                    Elements price1 = prods.get(i).select(".s-item__price");

                    // Get the product link
                    Elements link = prods.get(i).select(".s-item__link");
                    String productLink = link.attr("href");

                    // Get the product image
                    Elements image = prods.get(i).select(".s-item__image-wrapper img");
                    String productImage = image.attr("src");



                    Document doc1 = Jsoup.connect(productLink).get();

                    // Get all of the info on the page
//                    Elements brandContain = doc1.select(".ux-layout-section-evo__col:contains(Brand)");
//                    String brand = brandContain.text().replace("Brand", "").trim();
                    // Get all of the info on the page
                    Elements brandContain = doc1.select(".ux-layout-section-evo__col:contains(Brand)");
                    String brandText = brandContain.text();
                    String brand = brandText.substring(brandText.indexOf("Brand") + "Brand".length()).trim();

                    // Get all of the info on the page


                    Elements desclink = doc1.select("#desc_ifr");
                    String desc = desclink.attr("src");

                    Document description = Jsoup.connect(desc).get();

                    Elements sizeContain = doc1.select(".ux-layout-section-evo__col:contains(Temple)");
                    String size = sizeContain.text();

                    Elements sizeContain1 = doc1.select(".ux-layout-section-evo__col:contains(Width)");
                    String size1 = sizeContain1.text();


                    if (name.isEmpty() && brand.isEmpty() && size.isEmpty()  && desclink.isEmpty()  && productImage.isEmpty()) {

                    } else {

                        // Output the data that we have downloaded
                        System.out.println("WEBSITE: " + website +
                                " NAME: " + name.text() +
                                " DESCRIPTION: " + description.text() +
                                " BRAND: " + brand +
                                " PRICE: " + price1.text() +
                                " LINK: " + productLink +
                                " IMAGE: " + productImage +
                                " SIZE: " + size + size1);

                        // Check if the product already exists in the database
                        if (!hibernate.searchEyewear(name.text())) {
                            //add data to database
                            hibernate.addEyewear(name.text(), description.text(), productImage, brand, (size+size1), productLink, price1.text());//Add data
                        } else {
                            // Product already exists, you may want to log or handle this case
                            System.out.println("Product already exists in the database: " + name.text());
                        }
                    }
                }

                //Shut down Hibernate
                hibernate.shutDown();

                if (page > 10) {
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


