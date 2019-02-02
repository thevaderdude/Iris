import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Brain brain = new Brain();
		brain.loadData();
		brain.shuffleData();
		brain.setArrays();
		brain.setP();
		//brain.debug();
		brain.learn(100000);
		brain.test();

	}

}
