package javiergs.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This class is a JPanel that allows the user to enter a webpage and parse it using Jsoup.
 * The parsed information is displayed in a JTextArea.
 *
 * @author javiergs
 * @version 1.0
 */
public class JsoupPanel extends JPanel implements ActionListener {
	
	private JTextField textField;
	private JTextArea textArea;
	
	public JsoupPanel() {
		// widgets
		textField = new JTextField("http://javiergs.info");
		JButton button = new JButton("Parse");
		textArea = new JTextArea();
		textArea.setBackground(new Color(172, 248, 199));
		JScrollPane scrollPane = new JScrollPane(textArea);
		// layout
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		inputPanel.add(textField, BorderLayout.CENTER);
		inputPanel.add(button, BorderLayout.EAST);
		setLayout(new BorderLayout());
		add(inputPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		// behavior
		button.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		textArea.setText("");
		try {
			parseHtmlFile(textField.getText());
		} catch (Exception ex) {
			textArea.setText("Error: " + ex.getMessage());
		}
		textArea.setCaretPosition(0);
	}
	
	private void parseHtmlFile(String webpage) throws IOException {
		Document document = Jsoup.connect(webpage).get();
		String title = document.title();
		textArea.append("Title:\n" + title + "\n\n");
		// Get all links in the document
		textArea.append("Links:\n");
		Elements links = document.select("a[href]");
		for (Element link : links)
			textArea.append("* " + link.attr("href") + " :: " + link.text() + "\n");
		// Get all paragraphs in the document
		textArea.append("\nParagraphs:\n");
		Elements paragraphs = document.select("p");
		for (Element paragraph : paragraphs) {
			textArea.append("* " + paragraph.text() + "\n");
		}
	}
	
}