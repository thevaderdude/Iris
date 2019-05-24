import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Brain {
	private int m;
	private int layers;
	private double[][] x;
	private double[][] y;
	private LayerWeight[] p;
	private int[][] f = new int[2][10];
	private double ls = 3.0;
	private Activation[] a;
	private int[] tests;
	private int batchSize;
	private DigitData[] data;
	boolean bool;
	private Delta[] d;

	public Brain(int l2, int l3, int batch, boolean boolP, boolean threelayers) {
		batchSize = batch;
		
		bool = boolP;
		if (bool) 
			m = 200;
		
		else {
			m = 70000;
		}
		 tests = new int [m/10];
		
		if (threelayers) {
			layers = 3;
			p = new LayerWeight[layers-1];
			a  = new Activation[layers];
			d = new Delta[layers];
			p[0] = new LayerWeight(l2+1,785,0);
			p[1] = new LayerWeight(10,l2+1,1);
			a[0] = new Activation(0, 785);
			a[1] = new Activation(1, l2+1);
			a[2] = new Activation(2, 10);
			d[1] = new Delta(1, l2+1);
			d[2] = new Delta(2, 10);

		}
		else {
		layers = 4;
		p = new LayerWeight[layers-1];
		a  = new Activation[layers];
		d = new Delta[layers];	
		p[0] = new LayerWeight(l2+1,785,0);
		p[1] = new LayerWeight(l3+1,l2+1,1);
		p[2] = new LayerWeight(10,l3+1,2);
		a[0] = new Activation(0, 785);
		a[1] = new Activation(1, l2+1);
		a[2] = new Activation(2, l3+1);
		a[3] = new Activation(3, 10);
		d[1] = new Delta(1, l2+1);
		d[2] = new Delta(2, l3+1);
		d[3] = new Delta(3, 10);
		}
		x = new double[m][785];
		y = new double[m][10];
		data = new DigitData[m];
		
	}

	public void initWeights() {
		for (int k = 0; k < p.length; k++) {
			for (int i = 0; i < p[k].getI(); i++) {
				for (int j = 0; j < p[k].getJ(); j++) {
					p[k].getP()[i][j] = Math.random();
					if (Math.random() > 0.5)
						p[k].getP()[i][j] *= -1;
				}
			}
		}
	}


	public void loadData() throws IOException {
		if (bool) {
			//m = 200;
			f[0][0] = m/20;
			f[0][1] = m/20;
			f[0][2] = m/20;
			f[0][3] = m/20;
			f[0][4] = m/20;
			f[0][5] = m/20;
			f[0][6] = m/20;
			f[0][7] = m/20;
			f[0][8] = m/20;
			f[0][9] = m/20;
			f[1][0] = m/20;
			f[1][1] = m/20;
			f[1][2] = m/20;
			f[1][3] = m/20;
			f[1][4] = m/20;
			f[1][5] = m/20;
			f[1][6] = m/20;
			f[1][7] = m/20;
			f[1][8] = m/20;
			f[1][9] = m/20;
		}
		else {
			//m = 70000;
			f[0][0] = 980;
			f[0][1] = 1135;
			f[0][2] = 1032;
			f[0][3] = 1010;
			f[0][4] = 982;
			f[0][5] = 892;
			f[0][6] = 958;
			f[0][7] = 1028;
			f[0][8] = 974;
			f[0][9] = 1009;
			f[1][0] = 5923;
			f[1][1] = 6742;
			f[1][2] = 5958;
			f[1][3] = 6131;
			f[1][4] = 5842;
			f[1][5] = 5421;
			f[1][6] = 5918;
			f[1][7] = 6265;
			f[1][8] = 5851;
			f[1][9] = 5949;
		}
		int n = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < f[0][i]; j++) {
				BufferedImage image = ImageIO.read(new File("Test/Test" + i + "_" +  0 + "" + (j+1) + ".png"));
				//System.out.println("Read:" + i +  " " + (j+1));
				int pixel = 1;
				x[n][0] = 1;
				for (int w = 0; w < image.getWidth(); w++) {
					for (int h = 0; h < image.getHeight(); h++) {
						x[n][pixel] = new Color(255 - image.getRGB(w, h)).getRed() /255.0;
						pixel++;
					}
				}
				y[n][i] = 1.0;
				n++;	
			}
			System.out.println("Got to " + n);

			for (int j = 0; j < f[1][i]; j++) {
				BufferedImage image = ImageIO.read(new File("Train/Train" + i + "_" +  0 + "" + (j+1) + ".png"));
				//System.out.println("Read:" + i +  " " + (j+1));
				int pixel = 1;
				for (int w = 0; w < image.getWidth(); w++) {
					for (int h = 0; h < image.getHeight(); h++) {
						x[n][pixel] = (255 - new Color(image.getRGB(w, h)).getRed()) /255.0;
						pixel++;
					}
				}
				y[n][i] = 1.0;
				n++;	
			}
			System.out.println("Got to " + n);

		}

	}
	
	public void decideTest() {
		for (int i = 0; i < m/10; i++) {
			tests[i] = new Random().nextInt(m);
		}
	}
	
	public void test() {
		double right = 0; 
		for (int i = 0; i < m/10; i++) {
			double[] results = new double[10];
			for (int j = 0; j < 10; j++) {
				results[j] = this.h(tests[i], j);
			
			//if (i ==0)
				//System.out.println(i + "  " + results[j]);
			}
			if (y[tests[i]][this.max(results)] > 0.99)
				right++;
		}
		System.out.println("Right " + (right / (m/10)));
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
		for (int i = 0; i < 10; i++) {
			System.out.println(y[3][i]);
		}
	}

	public double a(int lp, int ip, int mp) {
		if (lp == 0)
			return x[mp][ip];
		//System.out.println("did x");
		if (ip == 0 	&& lp != layers-1) {
			//System.out.println("Gold");
			return 1;
		}
		double a = 0;
		for (int j = 0; j < p[lp-1].getJ(); j++) {
			a += this.a(lp-1, j, mp) * p[lp-1].getP()[ip][j];

		}
		//System.out.println("did " + lp + ", " + ip);
		return this.sigmoid(a);
	}

	public double sigmoid(double par) {
		return 1/(1+ Math.pow(Math.E, par * -1));
	}

	public double cost() {
		double j = 0;
		for (int i = 0; i < 20; i++) {
			for (int k = 0; k < 10; k++) {
				j +=  Math.pow((this.h(i, k) - y[i][k]), 2);
			}
		}
		return j / 20 ;
	}

	public double h(int x, int j) {
		return this.a(layers-1, j, x);
	}

	public void learn(int epoch) {

		System.out.println(this.cost());
		for (int e = 0; e < epoch; e++) {
			LayerWeight[] tempP = new LayerWeight[layers-1];
			
			for (int m1 = 0; m1 < m / batchSize; m1++) {
				for (int i = 0; i < layers-1; i++) {
					tempP[i] = new LayerWeight(p[i].getI(), p[i].getJ(), p[i].getL());
				}
				for (int m2 = (m1 * batchSize); m2 < (m1 * batchSize) + batchSize; m2++) {
					//int fart = 0;
					for (int l = 0; l < layers; l++) {
						for (int j = 0; j < a[l].getJ(); j++) {
							a[l].getA()[j] = this.a(l, j, m2);
						}
					}
					
					for (int l = 1; l < layers-1; l++) {
						for (int j = 0; j < p[l].getJ(); j++) {
							d[l].getD()[j] = this.d(l, j, m2);
						}
					}
					
					for (int layer = 0; layer < layers-1; layer++) {
						for (int i = 0; i < p[layer].getI(); i++) {
							for (int j = 0; j < p[layer].getJ(); j++) {
								//	System.out.println(m1 + "  One  " + fart);
								//fart++;
								tempP[layer].getP()[i][j] += d[layer+1].getD()[i] * a[layer].getA()[j];
							}
						}
					}
					//	System.out.println("One");
				}

				for (int layer = 0; layer < layers-1; layer++) {
					for (int i = 0; i < p[layer].getI(); i++) {
						for (int j = 0; j < p[layer].getJ(); j++) {
							tempP[layer].getP()[i][j] /= batchSize;
							tempP[layer].getP()[i][j] *= ls;
							//System.out.println(tempP[layer].getP()[i][j]);
							p[layer].getP()[i][j] -= tempP[layer].getP()[i][j];

						}
					}
				}
				
			}
			//System.out.println(e + "  " );
			if (e % 10 == 0) {
				System.out.println(e + "  " + this.cost());	
			 this.test();
			}
		}


	}
	
	
	public void shuffleData() {
		for (int m1 = 0; m1 < m; m1++) {
			data[m1] = new  DigitData(x[m1], y[m1]);
			}
		this.shuffle();
		for (int m1 = 0; m1 < m; m1++) {
			x[m1] = data[m1].getX();
			y[m1] = data[m1].getY();
			}
		}
		
	
	
	public void shuffle() {
		int n = data.length;
		Random random = new Random();
		for (int i = 0; i < data.length; i++) {

			int rV = i + random.nextInt(n - i);

			DigitData rE = data[rV];
			data[rV] = data[i];
			data[i] = rE;
		}
	}
	
	
	public double d(int lp, int jp, int mp) {

		if (lp == layers-1) {
			//System.out.println("EVANNNNN");
			return a[lp].getA()[jp] - y[mp][jp];

		}

		double d = 0;
		for (int i = 0; i < p[lp].getI(); i++) {
			d += p[lp].getP()[i][jp] * this.d(lp+1, i, mp);
		}
		d *=  a[lp].getA()[jp] * (1-  a[lp].getA()[jp]); 
		return d;
	}

}
