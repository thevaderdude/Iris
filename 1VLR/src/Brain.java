import java.util.Scanner; 
import java.io.File;
import java.io.FileNotFoundException;
public class Brain {
	int dS = 135;
	int[] x = new int[dS];
	int[] y = new int[dS];
	double a = 10.0;
	double b = 10.0;
	double ls = 0.0000001;
	
	public void loadData() throws FileNotFoundException {
		Scanner in = new Scanner(new File("testData.txt"));
		for (int i = 0; i < dS; i++) {
			x[i] = in.nextInt();
			//System.out.println(x[i]);
			y[i] = in.nextInt();
			//System.out.println(y[i]);
		}
	}
	
	public double getYHat(int x) {
		return a  + (b * x);
	}
	
	public double getCost() {
		int div = dS * 2;
		double cost = 0.0;
		for (int i = 0; i < dS; i++) {
			cost += Math.pow(this.getYHat(x[i]) - y[i], 2);
		}
		cost /= div;
		return cost;
	}
	
	public void train(int lim) {
		System.out.println(this.getCost());
		for (int k = 0; k < lim; k++) {
			double a1 = 0;
			double b1 = 0;
			for (int i = 0; i < dS; i++) {
				a1 += this.getYHat(x[i]) - y[i];
				b1 += (this.getYHat(x[i]) - y[i]) * x[i];
			}
			a1 /= dS;
			a1 *= ls * 10000;
			b1 /= dS;
			b1 *= ls;
			a -= a1;
			b -= b1;
			
		}  
		System.out.println(this.getCost());
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		
	}
	
	public void displayData() {
		for (int i = 0; i < dS; i++ ) {
			System.out.println(x[i]);
		}
		for (int i = 0; i < dS; i++) {
			System.out.println(y[i]);
		}
	}
	
	public void debug() {
		
		for (int i = 0; i < 100; i++) {
			System.out.println(i + " " + i);
		}
	}
}
