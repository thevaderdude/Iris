
public class Delta {
	private double[] d;
	private int j;
	private int l;
	
	public Delta(int layer, int index) {
		setJ(index);
		l = layer;
		setD(new double[index]);
	}

	public double[] getD() {
		return d;
	}

	public void setD(double[] d) {
		this.d = d;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
}
