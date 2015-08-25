package me.fertitta;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.crawljax.core.CrawlerContext;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.core.plugin.OnNewStatePlugin;
import com.crawljax.core.state.StateVertex;

public class FindEmails {

	public static void main(String[] args) {
		//First, check that we got a webpage to crawl
		if (args.length == 0)
		{
			System.out.println("Please specify a URL to scrape");
			return;
		}

		String url = args[0];
		
		/*
		 * Now we can start setting up the crawler. Crawljax is a crawler for java that works with dyncamic webpage
		 * it will actually open a browswer (in this configuration) and perform the commands we want
		 */
		CrawljaxConfigurationBuilder builder  = CrawljaxConfiguration.builderFor(url);
		
		//In this case, we want it to click every link (by default crawljax won't leave the starting domain
		builder.crawlRules().click("a");
		
		//Add a plugin that tells us everytime a new state occurs (page loads)
		builder.addPlugin(new OnNewStatePlugin() {
		
		    @Override
		    public void onNewState(CrawlerContext context, StateVertex newState) {
		    
		    	//Now that the page is loaded we can get it's content and perform a regex to find an emails
		    	String content = context.getBrowser().getStrippedDom();
		    	
		    	Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
		    	Matcher matcher = p.matcher(content);
		    	
		    	//We'll put the results of the regex into a set, that way the list is de-duped
		    	Set<String> emails = new HashSet<String>();
		    	while (matcher.find()) {
		    	   emails.add(matcher.group());
		    	}
		    	
		    	//and now we can just print out the set
		    	for (String email : emails)
		    	{
		    		System.out.println("Email: " + email);
		    	}
		    	
		    }
		
		});
		
		CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
		crawljax.call();	
	}

}
