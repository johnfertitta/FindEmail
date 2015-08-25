package me.fertitta;

import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;

public class FindEmails {
	
	public static void main(String[] args) {
		//First, check that we got a webpage to crawl
		if (args.length == 0)
		{
			System.out.println("Please specify a URL to scrape");
			return;
		}

		String url = args[0];
		
		//Some simple input validation
		if (!url.startsWith("http"))
		{
			url = "http://" + url;
		}
		
		/*
		 * Now we can start setting up the crawler. Crawljax is a crawler for java that works with dyncamic webpage
		 * it will actually open a browswer (in this configuration) and perform the commands we want
		 */
		CrawljaxConfigurationBuilder builder  = CrawljaxConfiguration.builderFor(url);
		
		//In this case, we want it to click every link (by default crawljax won't leave the starting domain
		builder.crawlRules().click("a");
		
		//Add our custom plugin
		builder.addPlugin(new EmailExtractorPlugin());
		
		CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
		crawljax.call();	
	}

}
