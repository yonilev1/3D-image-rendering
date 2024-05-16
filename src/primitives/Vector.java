package primitives;

public class Vector extends Point {
	
	public Vector(double x, double y, double z) {
		super(x,y,z);
	}
	
	public Vector(Double3 xyz) {
		super(xyz);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		return obj instanceof Vector other && super.equals(other);
	}
	@Override
	public String toString() { return "->" + super.toString(); }
	
	public double lengthSquared() {
		return dotProduct(this);
	}
	
	public double length() {
		return Math.sqrt(lengthSquared());
		
	}
	
	public Vector add(Vector vec) {
		return new Vector(this.xyz.add(vec.xyz));
		
	}
	
	public Vector scale(double dob) {
		return new Vector(xyz.scale(dob));
	}
	
	public double dotProduct(Vector vec) {
		return xyz.d1 * vec.xyz.d1//
				+xyz.d2 * vec.xyz.d2 + xyz.d3 * vec.xyz.d3;
				
	}
	
	public Vector crossProdact(Vector vec) {
		return new Vector(xyz.d2 * vec.xyz.d3 - xyz.d3 * vec.xyz.d2,//
				xyz.d3 * vec.xyz.d1 - xyz.d1 * vec.xyz.d3,//
				xyz.d1 * vec.xyz.d2 - xyz.d2 * vec.xyz.d1);
	}
	
	public Vector normalize() {
		return scale(1/length());
	}

}