
public class LayerWeight {
	//activation going to
	private int i;
	
	//activation from
	private int j;
	
	//layer coming from
	private int l;
	
	private double[][] p;
	
	public LayerWeight(int i, int j, int l) {
		this.setI(i);
		this.setJ(j);
		this.setL(l);
		setP(new double[i][j]);
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public double[][] getP() {
		return p;
	}

	public void setP(double[][] p) {
		this.p = p;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	
	
}
