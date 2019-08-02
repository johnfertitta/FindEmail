package me.fertitta;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.crawljax.core.CrawlerContext;
import com.crawljax.core.plugin.OnNewStatePlugin;
import com.crawljax.core.state.StateVertex;

/*
 * Custom plugin that gets notified when a new page is loaded and then scrapes it for emails
 */
public class EmailExtractorPlugin implements OnNewStatePlugin {

	private final String EMAIL_REGEX = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,4}";
	private final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	
    @Override
    public void onNewState(CrawlerContext context, StateVertex newState) {
    
    	//Now that the page is loaded we can get it's content and perform a regex to find an emails
    	String content = context.getBrowser().getStrippedDom();
    	
    	Matcher matcher = EMAIL_PATTERN.matcher(content);
    	
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
}
