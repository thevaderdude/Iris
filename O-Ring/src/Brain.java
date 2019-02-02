import java.util.Scanner; 
import java.io.File;
import java.io.FileNotFoundException;
public class Brain {
	private int[][] x = new int[23][4];
	private int[] y = new int[23];
	private double[] p = new double[4];
	private double ls = 0.00001;
	
	public void loadData() throws FileNotFoundException {
		Scanner in = new Scanner(new File("data.txt"));
		for (int i = 0; i < 23; i++) {
			x[i][0] = 1;
			x[i][1] = in.nextInt();
			y[i] = in.nextInt();
			x[i][2] = in.nextInt();
			x[i][3] = in.nextInt();
			in.nextInt();
		}
	}
	
	public void displayData() {
		for (int i = 0; i < 23; i++) {
			System.out.println(i + " " + y[i] + " " + x[i][0] + " " + x[i][1] +  " " + x[i][2] + " " + x[i][3]);
		}
	}
	
	public void setP() {
		for (int i = 0; i < 4; i++) {
			p[i] = 1;
		}
	}
	
	public double h(int i) {
		double h = 0;
		for (int k = 0; k < 4; k++) {
			h += x[i][k] * p[k];
		}
		return h;
	}
	
	public double cost() {
		double j = 0;
		for (int i = 0; i < 23; i++) {
			j += Math.pow(h(i) - y[i], 2);
		}
		return j / 46;
	}
	
	public void learn(int it) {
		System.out.println(this.cost());
		double[] pTemp = {0.0,0.0,0.0,0.0};
		for (int i = 0; i < it; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 23; k++) {
					pTemp[j] += (h(k) - y[k]) * x[k][j];
				}
				pTemp[j] /= 23;
				pTemp[j] *= ls;
			}
			for (int l = 0; l < 4; l++) {
				p[l] -= pTemp[l];
			}
			System.out.println(this.cost());
		}
		for (int i = 0; i < 4; i++) {
			System.out.println("p = " + p[i]);
		}
	}
	
	public void test(int p1, int p2, int p3) {
		System.out.print("numba rings gonna explode:" + ((p[0]) + (p[1]*p1) + (p[2]*p2) + (p[3]*p3)));
	}
}

