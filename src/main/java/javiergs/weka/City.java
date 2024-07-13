package javiergs.weka;

/**
 * A city with x, y coordinates and a cluster.
 *
 * @author javiergs
 * @version 1.0
 */
public class City {
	
	private int x;
	private int y;
	private int cluster;
	
	public City(int x, int y, int cluster) {
		this.x = x;
		this.y = y;
		this.cluster = cluster;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getCluster() {
		return cluster;
	}
	
}