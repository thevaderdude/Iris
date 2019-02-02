import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Brain b = new Brain();
		b.loadData();
		//System.out.println(b.getCost());
		b.train(100000);
		//b.displayData();
		//System.out.println(b.getYHat(958));
		//b.debug();
		
	}

}
