package javiergs.weka;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Delegate JPanel draws the cities in the clusters.
 *
 * @author javiergs
 * @version 1.0
 */
public class DelegatePanel extends JPanel {
	
	private ArrayList<City> cities;
	
	public DelegatePanel(ArrayList<City> cities) {
		setBackground(new Color(200, 200, 200));
		this.cities = cities;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// calculate min and max
		int min_x = Integer.MAX_VALUE;
		int max_x = Integer.MIN_VALUE;
		int min_y = Integer.MAX_VALUE;
		int max_y = Integer.MIN_VALUE;
		for (City city : cities) {
			min_x = Math.min(min_x, city.getX());
			max_x = Math.max(max_x, city.getX());
			min_y = Math.min(min_y, city.getY());
			max_y = Math.max(max_y, city.getY());
		}
		// draw cities
		int width = 300; // getHeight();
		int height = 400; // getWidth();
		int x0 = 50;
		int y0 = 50;
		int x1 = width - 50;
		int y1 = height - 50;
		int dx = x1 - x0;
		int dy = y1 - y0;
		int n = cities.size();
		for (int i = 0; i < n; i++) {
			int x = x0 + (cities.get(i).getX() - min_x) * dx / (max_x - min_x);
			int y = y1 - (cities.get(i).getY() - min_y) * dy / (max_y - min_y);
			int c = cities.get(i).getCluster();
			g.setColor(new Color(255 * c / 3, 255 * (3 - c) / 3, 0));
			g.fillOval(200 + y - 1, 300 - x - 1, 2, 2);
		}
	}
	
}