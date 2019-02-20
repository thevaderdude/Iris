import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Brain {
	int layers = 3;
	int m = 569;
	private double[][] x = new double[m][31];
	private double[] y = new double[m];
	private LayerWeight[] p;
	private double ls = 0.1;
	
	public Brain(int nodes) {
		p  = new LayerWeight[layers -1];
		p[0] = new LayerWeight(nodes+1,31,0);
		p[1] = new LayerWeight(1, nodes+1, 1);
		
	}
	
	public void loadData() throws FileNotFoundException {
		Scanner in = new Scanner(new File("data.txt"));
		for (int i = 0; i < m; i++) {
			in.next();
			x[i][0] = 1;
			if(in.next().equals("M"))
				y[i] = 1.0;
			for (int j = 1; j < 31; j++) {
				x[i][j] = in.nextDouble();
			}
		}
	}
	
	public void debugData() {
		for (int i = 0; i < m; i++) {
			System.out.print(y[i] + "   ");
			for (int j = 0; j < 31; j++) {
				System.out.print(x[i][j] + "  ");
			}
			System.out.println();
		}
	}
	
	
	
	public void scale() {
		double[][] scalar = new double[31][m];
		for (int i = 0; i < 31; i++) {
			for (int j = 0; j < m; j++) {
				scalar[i][j] = x[j][i];
			}
		}
		for (int i = 1; i < 31; i++) {
				scalar[i] = new StatUtil(scalar[i]).zScore();
			}
		for (int i = 0; i < 31; i++) {
			for (int j = 0; j < m; j++) {
				x[j][i] = scalar[i][j];
			}
		}
	}
	
	public void randomizeWeights() {
		for (int k = 0; k < p.length; k++) {
			for (int i = 0; i < p[k].getI(); i++) {
				for (int j = 0; j < p[k].getJ(); j++) {
					p[k].getP()[i][j] = Math.random() * 1;
					if (Math.random() > 0.5)
						p[k].getP()[i][j] *= -1;
					}
			}
		}
	}
	
	public void test() {
		double percent = 0;
		for (int i = 500; i < m; i++) {
			if(this.h(i) > 0.5 && y[i] > 0.5)
				percent++;
			else if (this.h(i) < 0.5 && y[i] < 0.5) {
				percent++;
			}
		}
		System.out.println(percent / 69 + " Correct");
	}
	
	public void learn(int epoch) {
		System.out.println(this.cost());
		for (int e = 0; e < epoch; e++) {
			LayerWeight[] tempP = new LayerWeight[layers-1];
			//tempP[0] = new LayerWeight(p[0].getI(), p[0].getJ(), p[0].getL());
			//tempP[1] = new LayerWeight(p[1].getI(), p[1].getJ(), p[1].getL());
			for (int m1 = 0; m1 < 50; m1++) {
				tempP[0] = new LayerWeight(p[0].getI(), p[0].getJ(), p[0].getL());
				tempP[1] = new LayerWeight(p[1].getI(), p[1].getJ(), p[1].getL());

				for (int m2 = m1 *10; m2 < (m1 * 10) + 10; m2++) {
				for (int layer = 0; layer < layers-1; layer++) {
					for (int i = 0; i < p[layer].getI(); i++) {
						for (int j = 0; j < p[layer].getJ(); j++) {
							tempP[layer].getP()[i][j] =+ this.d(layer + 1, i,m2) * this.a(layer, j,m2);
						}
					}
				}
				
			}
			for (int layer = 0; layer < layers-1; layer++) {
				for (int i = 0; i < p[layer].getI(); i++) {
					for (int j = 0; j < p[layer].getJ(); j++) {
						tempP[layer].getP()[i][j] /= 10;
						tempP[layer].getP()[i][j] *= ls;
						//System.out.println(tempP[layer].getP()[i][j]);
						p[layer].getP()[i][j] -= tempP[layer].getP()[i][j];
						
					}
				}
			}
			}
			System.out.println(e + "  " + this.cost());
		}
		
		
	}
	
	public double d(int lp, int jp, int mp) {
		
		if (lp == layers-1) {
			return this.a(lp, jp, mp) - y[mp];
			
		}
		double d = 0;
		for (int i = 0; i < p[lp].getI(); i++) {
			d += p[lp].getP()[i][jp] * this.d(lp+1, i, mp);
		}
		d *= this.a(lp,jp, mp) * (1- this.a(lp, jp, mp)); 
		return d;
	}
	
	
	public double a(int lp, int ip, int mp) {
		if (lp == 0)
			return x[mp][ip];
		if (ip == 0 && lp == 1) {
			return 1;
		}
		double a = 0;
		for (int j = 0; j < p[lp-1].getJ(); j++) {
			a += this.a(lp-1, j, mp) * p[lp-1].getP()[ip][j];
			
		}
		return this.sigmoid(a);
	}
	
	public double sigmoid(double par) {
		return 1/(1+ Math.pow(Math.E, par * -1));
	}
	
	public double cost() {
		double j = 0;
		for (int i = 0; i < m; i++) {
			j += (y[i] * Math.log(this.h(i))) + ((1 - y[i]) * Math.log(1-this.h(i)));
		}
		return j / -m ;
	}
	
	public double h(int x) {
		return this.a(2, 0, x);
	}
	
	public void debug() {
		System.out.println(p[1].getJ());
	}
}
