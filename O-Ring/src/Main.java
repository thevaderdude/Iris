import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Brain b = new Brain();
		b.loadData();
		//b.displayData();
		b.setP();
		b.learn(100000);
		b.test(6, 53, 200);
	}
}
