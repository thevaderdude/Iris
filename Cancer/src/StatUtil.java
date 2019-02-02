
public class StatUtil {
	private double[] x;
	
	public StatUtil(double[] x) {
		this.x = x;
	}
	
	public double mean() {
		double m = 0;
		for (int i = 0; i < x.length; i++) {
			m += x[i];
		}
		return m /x.length;
	}
	
	public double sd() {
		double sd = 0;
		for (int i = 0; i < x.length; i++) {
			sd += Math.pow(x[i] - this.mean(), 2);
		}
		sd /= x.length;
		return Math.sqrt(sd);
		
	}
	
	public double[] zScore() {
		double[] x1 = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			x1[i] = (x[i] - this.mean()) / this.sd();
		}
		return x1;
	}
}
