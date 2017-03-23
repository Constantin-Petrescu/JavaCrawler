# JavaCrawler
It gets static urls from a website

In the arguments we will have the URL that we are going to crawl on and it can have or not https:// or http://.
We have a class call Bean which is the basic structure of an item in the JSON result and it contains URL and assets.
In the extractor is happening all the URL gathering part and add them to an ArrayList of Beans .
After a URL is finishing to crawling, I am writing in a file using a mapping Jackson library over the Bean object but it doesn't really as a JSON object and the printing is not very clean, it needs a better implementation.

To run the project there are to chances:
1) Import the classes and Jackson library from crawler folder into an eclipse an run it;
2) Run the crawler.jar from folder runningJar by writing in a bash terminal the next command:
java -jar crawler.jar URL   
example: java -jar crawler.jar google.com

Possible improvements:
1) Better implementation of printing the json file
2) At the extract class, handling better the url so it accepts more formats or url (sometimes it might not work)

