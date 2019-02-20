import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		Brain b = new Brain(16,16,10, true, true);
		b.loadData();
		b.initWeights();
		b.debug();
		b.shuffleData();
		b.decideTest();
		b.test();
		b.learn(50000);
		b.test();
		
	}
	
}
