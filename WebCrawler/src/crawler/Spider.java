package crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Spider
{ 
  private int pag = 0;
  private int MAX_PAGES_TO_SEARCH ;
  private Set<String> pagesVisited = new HashSet<String>();
  private List<String> pagesToVisit = new LinkedList<String>();
  private String url;
  private Boolean download;
  private String conteudo;
  private Document doc;
  private List<String> urlsJaAdicionadas;
  private String total;
  public Spider(int maxPages, String urlInicial)
  {
	  urlsJaAdicionadas = new LinkedList<String>();
	  total = new String();
	  this.url = urlInicial;
	  this.download = false;
	  MAX_PAGES_TO_SEARCH = maxPages;
	  try {
	     conteudo = getText();
	} catch (IOException e) {
		
	}

  	try {
    	File logFile=new File("D:\\UrlPrincipalConteudo.txt");
  	    BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
  	    writer.newLine();
  	    writer.write("Site : " + url);
	  	    total = total + "                                                              ";
  	  	    total = total + "                                                              ";
  	    writer.newLine();
  	    writer.write(conteudo);
  	    total = "Site : " + url;
  	    total = total + "\n\n\n";
  	    total = total + conteudo;
  	    total = total + "\n\n\n";
  	    urlsJaAdicionadas.add(url);
  	    writer.close();
  	} catch(Exception e) {
  	}
	  search(url);
	  
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
        	  		  url = leg.getLinks().get(i);
        	  		  if(!urlsJaAdicionadas.contains(url))
        	  		  {
        	  			 try {
							conteudo = getText();
						} catch (IOException e) {
							
						}
        	  			try {
        	  				
        	  	    	File logFile=new File("D:\\UrlProxPags_"+pag+".txt");
        	  	    	pag++;
        	  	  	    BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
        	  	  	    total = total+ "\n"+ "Site : " + url;
        	  	  	    total = total + "                                                              ";
        	  	  	    total = total + "                                                              ";
        	  	  	    total = total + conteudo;
        	  	  	    total = total + "                                                              ";
        	  	  	    total = total + "                                                              ";
        	  	  	    writer.newLine();
        	  	  	    writer.write("Site : " + url);
        	  	  	    writer.newLine();
        	  	  	    writer.newLine();
        	  	  	    writer.write(conteudo);
        	  	  	    urlsJaAdicionadas.add(url);
        	  	  	    writer.close();
        	  			} catch(Exception e) {
        	  									}
        	  		  }
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
     //  for(int i= 0 ; i< pagesToVisit.size();i++)
     //  {
     //	  System.out.println(pagesToVisit.get(i));
     // }
    	try {
  	    	logFile=new File("D:\\ConteudoTotal.txt");
  	  	    BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
  	  	    writer.write(total);
  	  	    writer.close();
  			} catch(Exception e) {
  			}
    	}
    
  

	private void downloadURL() throws IOException {
		
		doc = Jsoup.connect(this.url).get();
		this.download = true;
		
	}
  
  
	//Pegando o texto da URL
	
	public String getText() throws IOException {
		
		if( !this.download ) {
			downloadURL();
		} 
		
		String htmlString = doc.toString();
		Document docHTML = Jsoup.parse(htmlString);
		String text = docHTML.body().text();
		
//		print("\nConteudo: ");
//		System.out.println(text);
//		print("\n");
		return text.trim();
		
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
