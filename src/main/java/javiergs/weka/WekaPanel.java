package javiergs.weka;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * A JPanel to load a CSV file and perform clustering using Weka.
 * The results are displayed in a JTextArea and rendered in a delegate JPanel.
 *
 * @author javiergs
 * @version 1.0
 */
public class WekaPanel extends JPanel {
	
	private DelegatePanel delegatePanel;
	private JTextArea textArea;
	private ArrayList<City> cities = new ArrayList<>();
	
	public WekaPanel() {
		textArea = new JTextArea();
		textArea.setBackground(new Color(172, 248, 199));
		delegatePanel = new DelegatePanel(cities);
		JScrollPane scrollPane = new JScrollPane(textArea);
		// layout
		setLayout(new GridLayout(2, 1));
		add(delegatePanel);
		add(scrollPane);
		clustering();
	}
	
	private void clustering() {
		try {
			URL url = getClass().getResource("/usa115475.csv");
			File file = new File(url.toURI());
			String path = file.getPath().replace("%20", " ");
			textArea.append(path + "\n\n");
			// Load CSV
			CSVLoader loader = new CSVLoader();
			loader.setSource(new File(path));
			Instances train = loader.getDataSet();
			// KMeans
			SimpleKMeans kmeans = new SimpleKMeans();
			kmeans.setNumClusters(4);
			kmeans.buildClusterer(train);
			// draw clusters
			for (int i = 0; i < train.numInstances(); i++) {
				cities.add(new City(
					(int) train.instance(i).value(0),
					(int) train.instance(i).value(1),
					kmeans.clusterInstance(train.instance(i))
				));
			}
			delegatePanel.repaint();
			// evaluate
			ClusterEvaluation eval = new ClusterEvaluation();
			eval.setClusterer(kmeans);
			eval.evaluateClusterer(train);
			textArea.append(eval.clusterResultsToString());
		} catch (Exception e) {
			textArea.append("Error:\n" + e.getMessage());
		}
	}
	
}