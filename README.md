# FindEmail

Please make sure to have firefox installed before running

run 'mvn install' to create the exectuable jar

The jar will be located in the targets folder and named FindEmails-1-jar-with-dependancies.jar

run with 'java -jar FindEmails-1-jar-with-dependancies.jar http://jana.com'

Overview:

This solution uses a library called crawljax to perform the web crawling. This library supports dynamic content by using Selenium to open a web browser and follow links.  There are several rules you can set up, this is a simple set up that just follows all anchor tags. By doing this we know we'll be clicking only actual links, which prevent things like css and js files from being loaded. Also, by default crawljax does not leave the domain you start on, so no filters were added for that.

Once crawljax begins crawling we recieve callbacks that new pages have been loaded. This callback performs a regex search on the contents of the page and prints out each email that was found.
