package crawler;

import java.util.ArrayList;

public class Extractor 
{
	ArrayList<Bean> lBeansList;

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

	public void Extract(Bean lBean, String lPage)
	{
		String lSrcFormatWSpace = "src =";
		String lSrcFormatNSpace = "src=";
		String lHrefFormatWSpace = "href =";
		String lHrefFormatNSpace = "href=";
		
		ExtractTag(lBean, lPage, lSrcFormatWSpace, lSrcFormatNSpace, true);
		ExtractTag(lBean, lPage, lHrefFormatWSpace, lHrefFormatNSpace, false);
		System.out.println(lBean.getmAssets());
	}
	
	public Bean ExtractTag(Bean lBean, String lPage, String lTagWSpace, String  lTagNSpace, boolean IsAsset)
	{
		boolean lContinue = true;
		String lBlock = lPage;

		String lUrl; 
		int lIndexWSpace = -1;
		int lIndexNSpace = -1;
		
		while (lContinue == true)
		{
			lIndexNSpace = lBlock.indexOf(lTagNSpace,lIndexNSpace);
			lIndexWSpace = lBlock.indexOf(lTagWSpace,lIndexWSpace);			
			if(lIndexNSpace == -1 && lIndexWSpace == -1)
			{
				lContinue = false;
			}
			else
			{
				
				if(lIndexNSpace != -1)
				{
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
