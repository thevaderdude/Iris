
public class Activation {
	private int l;
	private int j;
	private double[] a;
	
	public Activation(int layer, int index) {
		setL(layer);
		setJ(index);
		setP(new double[index]);
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public double[] getA() {
		return a;
	}

	public void setP(double[] p) {
		this.a = p;
	}
	
}
