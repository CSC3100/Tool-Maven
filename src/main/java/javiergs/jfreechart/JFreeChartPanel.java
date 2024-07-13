package javiergs.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;

/**
 * This is a JPanel that contains a JFreeChart with a PieChart.
 * The data is hardcoded in the createDataset method.
 *
 * @author javiergs
 * @version 1.0
 */
public class JFreeChartPanel extends JPanel {
	
	public JFreeChartPanel() {
		PieDataset dataset = createDataset();
		JFreeChart chart = ChartFactory.createPieChart("Popular Languages", dataset, false, true, false);
		chart.setBackgroundPaint(new Color(172, 248, 199));
		chart.getPlot().setBackgroundPaint(new Color(172, 248, 199));
		chart.getPlot().setOutlineVisible(false);
		ChartPanel chartPanel = new ChartPanel(chart);
		setLayout(new GridLayout(1, 1));
		add(chartPanel);
	}
	
	private static PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Java", new Double(22.42));
		dataset.setValue("Python", new Double(21.01));
		dataset.setValue("JavaScript", new Double(14.27));
		dataset.setValue("C/C++", new Double(9.66));
		dataset.setValue("SQL", new Double(5.49));
		dataset.setValue("PHP", new Double(5.16));
		dataset.setValue("C#", new Double(2.1));
		dataset.setValue("Swift", new Double(2.05));
		dataset.setValue("Golang", new Double(1.24));
		return dataset;
	}
	
}