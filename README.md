# 2023-24 CST3130 Advanced Web Development with Big Data
## Coursework 1: Price Comparison Website
### Requirements:

You build a price comparison website that displays products and their prices from several different 
websites.
• The user can search for products on the website.
• Shopping functionality and customer registration/login are not required. 
• Your website provides links that enable users to navigate to the product on the original website.
• You can propose a different type of project based on scraped data from multiple websites - for example a 
property search website.
• Website scraping must be done using Java.
• You must use a SQL database to store your data. MySQL or MariaDB is recommended.
• Several threads should be used to download data.
• There are marks for using Spring, Hibernate and Maven.
• The back end of the website must be implemented in JavaScript running on Node.js. No marks are 
available for functionality that is implemented in other programming languages.
• Your application includes a REST API. This can be integrated into your front end (via AJAX). Or it can be a 
separate web service that enables third parties to access your data.
• The front end must be written in HTML, CSS and JavaScript. You cannot write an app.
• There are marks available for the quality and quantity of data that you have scraped.
• Marks are available for code quality, unit testing and the front end of the website.
• The final submission of your project must include a video demonstration that shows all the functionality


### Proposed website:

‘Price Pursuit’ is a website idea designed in the framework of the CST3130 Advanced Web Development with Big Data coursework. Price Pursuit is imagined as an online based price comparison platform with its main theme being eyewear.
The website enables the user to search for any kind of eyewear such as sunglasses, reading glasses, or prescription glasses. If no specific brand is searched for, the page will display the product searched for manufactured by different brands. If a brand is specified it will display all products corresponding to the search as well as additional eyewear from the same brand. The products can be sorted by price, year, or brand on the products page.
Using web scraping the website displays all available products. When clicking on a specific product, the user can access a description of the product and different information on its properties such as brand, sizes and frame colors available as well as a few pictures. On each product page, there is a list of websites, with its name, the product name, and the price as well as a link to redirect the user to that website to visit the product on that page. This enables the user to compare the prices on the different websites and find the best deals on their chosen product.
If a user does not know how to use the website, a how to use section is included in the homepage just below the search box. Moreover, the homepage also features an about us segment.
Finally, when showing all the results found after a search, due to the big number of products found, the user will have to change pages when reaching the end of the page, by pressing on the next button or directly on the page number. This will load new data to be displayed and won’t open a new page or refresh the actual page.
The website will be developed using HTML, CSS for styling and java programming language will be used for web scraping to extract data from different websites selling eyewear. The data will be stored in an SQL database using MySQL. Threads will be used to download the data, and the Hibernate framework will be used to map the domain model to the relational database. The backend of the website will be implemented in JavaScript running on Node.js. The frontend will consist of a REST API using AJAX to enable third-party websites to access the data.
