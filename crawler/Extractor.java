package crawler;

import java.util.ArrayList;

public class Extractor 
{
	ArrayList<Bean> lBeansList;

	
	/*
	 * Crawl functions which iterates through BeanList which starts with one item and it repeat until
	 *		the last element of BeanList has the assets list not null
	 * During an iteration the crawl Extract from an URL all new possible URL to crawls and the assets 
	 */
	public void Crawl(ArrayList<Bean> lResults) 
	{
		lBeansList = lResults;
		String lPage;
		int lIt =0;
		
		while(lBeansList.get(lBeansList.size()-1).getmAssets().isEmpty())
		{
			Bean lBean = lBeansList.get(lIt);
			lPage = Utilities.getHTMLPage(lBean.getmStartURL());
			if(lPage == null)
			{
				lBean.getmStartURL().replace("https", "http");
				lPage = Utilities.getHTMLPage(lBean.getmStartURL());
			}
			lIt++;
			
			if(lPage != null )
			{
				Extract(lBean, lPage);
			}
			
			
			if(!lBean.getmAssets().isEmpty())
			{
				Utilities.writeResult(lBean);
			}
		}
		

			
			for(int lItList = 0; lItList < lBeansList.size(); lItList++)
			{
				System.out.println("PAGE:" + lBeansList.get(lItList).getmStartURL());
				for (int lItAsset = 0; lItAsset < lBeansList.get(lItList).getmAssets().size(); lItAsset++)
				{
					System.out.println(lBeansList.get(lItList).getmAssets().get(lItAsset));
				}
			}
	}

	
	// Extract Method which calls both  
	public void Extract(Bean lBean, String lPage)
	{
		String lSrcFormatWSpace = "src =";
		String lSrcFormatNSpace = "src=";
		String lHrefFormatWSpace = "href =";
		String lHrefFormatNSpace = "href=";
		
		ExtractTag(lBean, lPage, lSrcFormatWSpace, lSrcFormatNSpace);
		ExtractTag(lBean, lPage, lHrefFormatWSpace, lHrefFormatNSpace);
	}
	
	// Extract method which gets all URL based on tag received and puts them either as new crawling URL or assets
	public Bean ExtractTag(Bean lBean, String lPage, String lTagWSpace, String  lTagNSpace)
	{
		boolean lContinue = true;
		String lBlock = lPage;

		String lUrl; 
		int lIndexWSpace = -1;
		int lIndexNSpace = -1;
		
		// Iterate over the HTML page until it doesn't find any more tags related to our URL 
		while (lContinue == true)
		{
			lIndexNSpace = lBlock.indexOf(lTagNSpace,lIndexNSpace);
			lIndexWSpace = lBlock.indexOf(lTagWSpace,lIndexWSpace);			
			// If there aren't anymore URLs change variable to stop the loop
			if(lIndexNSpace == -1 && lIndexWSpace == -1)
			{
				lContinue = false;
			}
			else
			{
				
				if(lIndexNSpace != -1)
				{
					//Extract new URLs, verify their authenticity, and add domain if it is missing 
					lIndexNSpace = lIndexNSpace + lTagNSpace.length();
					lUrl = Utilities.ExtractURL(lBlock, lIndexNSpace);
					try 
					{
						lUrl = Utilities.verifyAsset(lBeansList, lBean,lUrl);
					}
					catch (Exception e) 
					{
						System.out.println(e.toString());
					}

					//if URL is null means is not a good or already exists and if not add it to Beans
					lIndexNSpace ++;
					if( lUrl != null)
					{
						String lTempUrl = lUrl.replace(lBean.getmStartURL(), "");
						if (lTempUrl.contains("."))
						{
							lBean.addAsset(lUrl);
						}
						else
						{
							if( Utilities.verifyUrlExists(lBeansList, lUrl) == true )
							{
								lBeansList.add(new Bean(lUrl));
							}
						}
					}

				}
				
				/*
				 * Same extraction as the method above but for different tag, I know it should have be done a method so it won't repeat the code
				 * but there is always place for further improvements for any written code 
				 */
				if(lIndexWSpace != -1)
				{
					lIndexWSpace = lIndexWSpace + lTagWSpace.length();
					lUrl = Utilities.ExtractURL(lBlock, lIndexWSpace);
					try 
					{
						lUrl = Utilities.verifyAsset(lBeansList, lBean,lUrl);
					}
					catch (Exception e) 
					{
						System.out.println(e.toString());
					}

					lIndexWSpace ++;
					if( lUrl != null)
					{
						String lTempUrl = lUrl.replace(lBean.getmStartURL(), "");
						if (lTempUrl.contains("."))
						{
							lBean.addAsset(lUrl);
						}
						else
						{
							if( Utilities.verifyUrlExists(lBeansList, lUrl) == true )
							{
								lBeansList.add(new Bean(lUrl));
							}
						}
					}
				}
			}
		}
		
		return lBean;
	}
	
}
