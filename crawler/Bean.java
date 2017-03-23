package crawler;

import java.util.ArrayList;

public class Bean 
{
	private String url;
	private ArrayList<String> assets;
	
	public Bean() 
	{
		this.url ="" ;
		this.assets = new ArrayList<String>();
	}
	
	public Bean(String lStartURL) 
	{
		this.url = lStartURL ;
		this.assets = new ArrayList<String>();
	}
	
	public Bean(String mStartURL, ArrayList<String> mAssets) 
	{
		this.url = mStartURL;
		this.assets = mAssets;
	}
	
	public void addAsset(String lAsset)
	{
		
		try 
		{
			this.assets.add(lAsset);
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
	}
	
	public String getmStartURL() 
	{
		return url;
	}
	
	
	public void setmStartURL(String mStartURL) 
	{
		this.url = mStartURL;
	}
	
	public ArrayList<String> getmAssets() 
	{
		return assets;
	}
	
	public void setmStartURL(ArrayList<String> mAssets) 
	{
		this.assets = mAssets;
	}
	
	
}
