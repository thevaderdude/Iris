import java.io.FileNotFoundException;

public class Main {
	public static void main (String[] args) throws FileNotFoundException {
		Brain b = new Brain(12);
		b.loadData();
		//b.debugData();
		b.randomizeWeights();
		//System.out.println(b.cost());
		b.scale();
		b.test();
		b.learn(400);
		b.test();
	//	System.out.println(b.cost());
	}
}
