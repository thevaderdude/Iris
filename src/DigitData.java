
public class DigitData {
	private double[] x = new double[785];
	private double[] y = new double[10];
	
	

	public DigitData(double[] xp, double[] yp) {
		x = xp;
		y = yp;
	}

	public double[] getX() {
		return x;
	}

	public void setX(double[] x) {
		this.x = x;
	}

	public double[] getY() {
		return y;
	}

	public void setY(double[] y) {
		this.y = y;
	}
	
}
