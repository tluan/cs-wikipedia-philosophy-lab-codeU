
package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;
import org.omg.CORBA.Current;


public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher();
	public static void main(String[] args) throws IOException {
		
        // some example code to get you started
		String link_basic = "https://en.wikipedia.org";
		String url_wiki = "/wiki/Metaphysics";
		String combined = link_basic + url_wiki;
		
		
		Element Paragraph;
		Elements link;
		ArrayList <String> done = new ArrayList<String>();
		while(!(combined.equals("https://en.wikipedia.org/wiki/Philosophy"))){
				int index = 0;
				Elements Paragraph_local = wf.fetchWikipedia(combined);
				done.add(combined);
            do{
            	Paragraph = Paragraph_local.get(index);
				index = index + 1;
				link = Paragraph.select("a");
				
            }while(link.isEmpty() && index < Paragraph_local.size());

            if(index >= Paragraph_local.size()) {
            	System.out.println("no link in the page" + combined);
            	System.out.println(done.toString());
            	return;
            }

            boolean testParenth = false;

            Iterable<Node> iter = new WikiNodeIterable(Paragraph);
			boolean found_link = false;
            
			for (Node node: iter)
			{
				if (node instanceof TextNode)
				{
					if(node.toString().contains("(")||node.toString().contains(")"))
						testParenth = true;	
					
				}
				if(testParenth == false  && link.contains(node)){
					url_wiki = node.toString();
					int place = url_wiki.indexOf("\"", 10);
					url_wiki = url_wiki.substring(9, place);
					combined = link_basic.concat(url_wiki);
					found_link = true;
				
					if(done.contains(combined))
					{
						System.out.println("We have already visited page: " + combined);
						System.out.println("\n" + done.toString());
						return;
					}
				}
				
				if(found_link == true){
					break;
				}
			}
				
		}

		done.add("https://en.wikipedia.org/wiki/Philosophy");
		System.out.println("Found!\n" + done.toString());
		
		
	
	
        
	}
}