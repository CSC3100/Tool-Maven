package javiergs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ExampleJSoup {
	
	public static void main(String[] args) {
		String url = "http://javiergs.info";
		try {
			Document document = Jsoup.connect(url).get();
			// Print the title of the document
			String title = document.title();
			System.out.println("Title: " + title);
			// Get all links in the document
			Elements links = document.select("a[href]");
			for (Element link : links)
				System.out.println("Link: " + link.attr("href") + " Text: " + link.text());
			// Get all paragraphs in the document
			Elements paragraphs = document.select("p");
			for (Element paragraph : paragraphs) {
				System.out.println("Paragraph: " + paragraph.text());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
