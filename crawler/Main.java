package crawler;

import java.util.ArrayList;

public class Main 
{

	public static void main(String[] args) 
	{
		/*	In the arguments we will have the URL that we are going to crawl on and it can have or not https://
		 *  We have a class call Bean which is the basic structure of an item in the JSON result and it contains URL and assets
		 *  In the extractor is happening all the URL gathering part and add them to an ArrayList of Beans 
		 *  After a URL is finishing to crawling, I am writing in a file using a mapping Jackson library over the Bean object  
		 *  	  but it doesn't really as a JSON object and the printing is not very clean, it needs a better implementation
		 */
		if (args.length > 0) 
		{				
			String lArgument = args[0].trim();
			
			
			if (lArgument != null ) 
			{
				if((!lArgument.contains("http")))// && (!lArgument.contains("www")
					lArgument = "https://" + lArgument;
				
				Extractor lExtractor = new Extractor();
				System.out.println("Crawling " + lArgument);

				
				ArrayList<Bean> lResults = new ArrayList<Bean>();
				Bean lBean = new Bean(lArgument);
				
				
				Utilities.initialiseFileForWrite();
				lResults.add(lBean);
				lExtractor.Crawl(lResults);
			} 
		}
	}

}
