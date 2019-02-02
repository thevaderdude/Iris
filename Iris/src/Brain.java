import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
public class Brain  {
	private IrisData[] data = new IrisData[150];
	private double[][] x = new double[150][5];
	private double[][] y = new double[150][3];
	private double[][] p = new double[3][5];
	private double ls = 0.003;
	
	public void loadData() throws FileNotFoundException {
		Scanner in = new Scanner(new File("IrisData.txt"));
		for (int i = 0; i < 150; i++) {
			data[i] = new IrisData(in.nextInt(), in.nextDouble(), in.nextDouble(), 
					in.nextDouble(), in.nextDouble(), in.next());	
		}
	}

	public void displayData() {
		for (int i = 0; i < 150;  i ++) {
			System.out.println(data[i].getI() + " "  + data[i].getSepalL() + " " + 
					data[i].getSepalW() + " " + data[i].getPetalL() + " " + data[i].getPetalW() + " " + data[i].getIrisType());
		}
	}

	public void shuffleData() {
		int n = data.length;
		Random random = new Random();
		for (int i = 0; i < data.length; i++) {

			int rV = i + random.nextInt(n - i);

			IrisData rE = data[rV];
			data[rV] = data[i];
			data[i] = rE;
		}
	}

	public void setArrays() {
		for (int i = 0; i < 150; i++) {
			x[i][0] = 1;
			x[i][1] = data[i].getSepalL();
			x[i][2] = data[i].getSepalW();
			x[i][3] = data[i].getPetalL();
			x[i][4] = data[i].getPetalW();
			if (data[i].getIrisType().equals("setosa"))
				y[i][0] = 1;
			else if (data[i].getIrisType().equals("versicolor"))
				y[i][1] = 1;
			else 
				y[i][2] = 1;
		}
	}
	
	public void setP() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				p[i][j] = 5.0;
			}
		}
	}
	
	public double h(int num , int flower) {
		double h = 0;
		for (int i = 0; i < 5; i++) {
			h += p[flower][i] * x[num][i];
		}
		return this.sigmoid(h);
	}
	
	public double cost(int k) {
		double j = 0;
		for (int i = 0; i < 150; i++) {
			j += (y[i][k] * Math.log(this.h(i, k)) - ((1 - y[i][k]) * Math.log(1-this.h(i, k))));
		}
		return j /-150;
	}
	
	public void learn(int num) {
		for (int f = 0; f < 3; f++) {
			System.out.println("Start Cost " + f + ": " + this.cost(f));
			for (int i = 0; i < num; i++) {
				double[] pTemp = new double[5];
				for (int j = 0; j < 5; j++) {
					for (int k = 0; k < 150; k++) {
						pTemp[j] += (h(k, f) - y[k][f]) * x[k][j];
					}
					pTemp[j] /= 150;
					pTemp[j] *= ls;
				}
				for (int l = 0; l < 5; l++) {
					p[f][l] -= pTemp[l];
				}
			}
			System.out.println("End Cost " + f + ": " + this.cost(f));
		}
	}
	
	public void test() {
		double[] avg = new double[3];
		int[] correct = new int[3];
		System.out.println("Testing...");
		for (int i = 0; i < 150; i++) {
			double[] result = new double[3];
			for (int j = 0; j < 3; j++) {
				result[j] = this.h(i, j);
				//System.out.println(result[j]);
			}
			int max = this.max(result);
			if (y[i][max] == 1) {
				correct[max]++;
			}
			avg[max] += result[max];
			
		}
		double percent = 0;
		for (int i = 0; i < 3; i++) {
			percent += correct[i];
			avg[i] /= 50;
			avg[i] *= 100;
		}
		
		percent /= 150;
		percent *= 100;
		System.out.println("Percent Correct: " + percent);
		System.out.println(correct[0] + "  " + correct[1] +  "  " + correct[2]);
		for (int i = 0; i < 3; i++) {
			System.out.println(avg[i]);
		}
		
	}
	
	public double sigmoid(double par) {
		return 1/(1+ Math.pow(Math.E, par * -1));
	}


	public int max(double[] par) {
		int max = 0;
		for (int i = 1; i < par.length; i++) {
			if (par[i] > par[max]) {
				max = i;
			}
		}
		return max;
	}
	
	public void debug() {
		for (int i = 0; i < 150; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(y[i][j] + "  ");
			}
			System.out.println();
		}
	}
	
	

}