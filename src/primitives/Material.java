package primitives;

public class Material {
	
	public Double3 kD = Double3.ZERO;
			
	public Double3 kS = Double3.ZERO;
	
	public int shininess = 0;
	
	public Material setKD(Double3 kd) {
		this.kD = kd;
		return this;
	}
	
	public Material setKD(double x) {
		this.kD = new Double3(x);
		return this;
	}
	
	public Material setKS(Double3 ks) {
		this.kS = ks;
		return this;
	}
	
	public Material setKS(double x) {
		this.kS = new Double3(x);
		return this;
	}
	
	public Material setShininess(int shininess) {
		this.shininess = shininess;
		return this;
	}

}
