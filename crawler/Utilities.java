package crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Utilities 
{
	
	public static String getHTMLPage(String url) {
		try {
			return getHTMLPage(new URL(url)).toString();
		} catch (Exception ex) {
			System.out.println("URL Exception: " + url + " " + ex.toString());
		}
		return new StringBuffer().toString();
	}
	
	public static StringBuffer getHTMLPage(URL url) 
	{
		try 
		{
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			// conn.setRequestProperty("User-Agent",
			// "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");

			conn.connect();
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader rd = new BufferedReader(isr);
			String line;
			StringBuffer lOutput = new StringBuffer();

			while ((line = rd.readLine()) != null) {
				lOutput.append(line);
			}

			return lOutput;
		} 
		catch (Exception e) 
		{
			System.err.println("Connection Failure - " + e.toString());
			return new StringBuffer();
		}
	}

	public static String ExtractURL(String lPage, int lIndex)
	{
		int lIndexEnd;
		
		if(lPage.charAt(lIndex) == '\"')
		{			
			lIndexEnd = lPage.indexOf("\"",lIndex + 1);
			
			if (lIndexEnd != -1 && lIndexEnd > lIndex)
			{
				return lPage.substring(lIndex +1, lIndexEnd);
			}
		}
		return null;
	}

	public static String verifyAsset(ArrayList<Bean> lBeansList, Bean lBean, String lUrl) 
	{
		String lPageUrl = lBean.getmStartURL();
		String lUrlTemp = lUrl;
		
		if(lUrl.contains("http") || lUrl.contains("www"))
		{
			lPageUrl = lPageUrl.replace("https://", "").replace("http://", "");
			if ( lPageUrl.contains("/") )
			{
				lPageUrl = lPageUrl.substring( 0, lPageUrl.indexOf("/"));
				
				if (lUrlTemp.contains("/"))
				{
					if ( lUrlTemp.substring( 0, lUrlTemp.indexOf("/")) != lPageUrl )
					{
						return null;
					}
				}
			}
			else
			{
				if (lUrlTemp.contains("/"))
				{
					if ( lUrlTemp.substring( 0, lUrlTemp.indexOf("/")) != lPageUrl )
					{
						return null;
					}
				}
			}
		}
		else
		{
			if(lPageUrl.substring(lPageUrl.length()-1) == "/" && lUrl.substring(lUrl.length()-1) == "/")
			{
				lUrl = lBeansList.get(0).getmStartURL() + lUrl.substring(1);
			}
			else
			{
				lUrl = lBeansList.get(0).getmStartURL() + lUrl;
			}
		}
		
		lUrl = lUrl.replaceAll("//", "/");
		lUrl = lUrl.substring(0, lUrl.indexOf("/")+1) + "/" + lUrl.substring(lUrl.indexOf("/")+1);
		
		return lUrl;
	}

	public static boolean verifyUrlExists(ArrayList<Bean> lBeansList, String lUrl) 
	{
		
		for (int lIt = 0; lIt < lBeansList.size(); lIt++)
		{		
			System.out.println(lUrl + " " + lUrl.substring(0,lUrl.length()-1) + " " + lBeansList.get(lIt).getmStartURL());
			if (lUrl.equals(lBeansList.get(lIt).getmStartURL()))
			{
				return false;
			}
			
			if (lUrl.substring(0,lUrl.length()-1).equals(lBeansList.get(lIt).getmStartURL()))
			{
				return false;
			}
			
			if (lUrl.equals(lBeansList.get(lIt).getmStartURL().substring(0,lBeansList.get(lIt).getmStartURL().length()-1)))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static void initialiseFileForWrite()
	{
		try 
		{
			URL resultFileLocation = Main.class.getProtectionDomain().getCodeSource().getLocation();
			File resultFile = new File(resultFileLocation.getFile() + "crawler/result.json");
			if(resultFile.exists())
			{
				resultFile.delete();
			}
			else
			{
				System.out.println("Couldn't find the file on Path " + resultFileLocation.getFile() + "crawler/result.json");
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
			
	}
	
	public static void writeResult(Bean lBean)
	{
		if(lBean.getmAssets().isEmpty() == false)
		{
		    URL resultFileLocation = Main.class.getProtectionDomain().getCodeSource().getLocation();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        
	        try 
	        {
	        	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(resultFileLocation.getFile() + "crawler/result.json", true)));
	        	 
	        	mapper.writerWithDefaultPrettyPrinter().writeValue(out, lBean );
			} 
	        catch (JsonGenerationException e) 
	        {
				e.printStackTrace();
			} catch (JsonMappingException e) 
	        {
				e.printStackTrace();
			} catch (IOException e) 
	        {
				e.printStackTrace();
			}
		}
	}
}
