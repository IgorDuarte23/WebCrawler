package crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Spider
{
  private int MAX_PAGES_TO_SEARCH ;
  private Set<String> pagesVisited = new HashSet<String>();
  private List<String> pagesToVisit = new LinkedList<String>();


  public Spider(int maxPages, String urlInicial)
  {
	  MAX_PAGES_TO_SEARCH = maxPages;
	  search(urlInicial);
	  
  }
  private void search(String url)
  {
      while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
      {
          String currentUrl;
          SpiderLeg leg = new SpiderLeg();
          if(this.pagesToVisit.isEmpty())
          {
              currentUrl = url;
              this.pagesVisited.add(url);
          }
          else
          {
              currentUrl = this.nextUrl();
          }
        	  leg.crawl(currentUrl); 
          
          for(int i = 0 ; i < leg.getLinks().size(); i++)
		          {	
		        	  pagesToVisit.add(leg.getLinks().get(i));
		        	  if(pagesToVisit.size()>= MAX_PAGES_TO_SEARCH)
		        		  break;
		          
          }
          if(pagesToVisit.size()>= MAX_PAGES_TO_SEARCH)
    		  break;
      }
	    File logFile=new File("D:\\Urls.txt");

    	try {
    	    
    	    BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
    	    for(int i= 0 ; i< pagesToVisit.size();i++)
    	      {
    	    	 writer.write(pagesToVisit.get(i));
    	    	 writer.newLine();
    	      }

    	    //Close writer
    	    writer.close();
    	} catch(Exception e) {
    	}
      for(int i= 0 ; i< pagesToVisit.size();i++)
      {
    	  System.out.println(pagesToVisit.get(i));
      }
  }


  /**
   * Returns the next URL to visit (in the order that they were found). We also do a check to make
   * sure this method doesn't return a URL that has already been visited.
   * 
   * @return
   */
  private String nextUrl()
  {
      String nextUrl;
      do
      {
          nextUrl = this.pagesToVisit.remove(0);
      } while(this.pagesVisited.contains(nextUrl));
      this.pagesVisited.add(nextUrl);
      return nextUrl;
  }
}
