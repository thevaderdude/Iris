
public class IrisData {
	private double sepalL;
	private double sepalW;
	private double petalL;
	private double petalW;
	private int i;
	private String irisType;
	
	public IrisData(int iter, double sL, double sW, double pL, double pW,  String iType) {
		i = iter;
		sepalL = sL;
		sepalW = sW;
		petalL = pL; 
		petalW = pW; 
		irisType = iType;
	
		
	}
	
	public double getSepalL() {
		return sepalL;
	}
	public void setSepalL(double sepalL) {
		this.sepalL = sepalL;
	}
	public double getSepalW() {
		return sepalW;
	}
	public void setSepalW(double sepalW) {
		this.sepalW = sepalW;
	}
	public double getPetalW() {
		return petalW;
	}
	public void setPetalW(double petalW) {
		this.petalW = petalW;
	}
	public double getPetalL() {
		return petalL;
	}
	public void setPetalL(double petalL) {
		this.petalL = petalL;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public String getIrisType() {
		return irisType;
	}
	public void setIrisType(String irisType) {
		this.irisType = irisType;
	}
	
	
}
