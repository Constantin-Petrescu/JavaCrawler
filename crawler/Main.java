package crawler;

import java.util.ArrayList;

public class Main 
{

	public static void main(String[] args) 
	{
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
