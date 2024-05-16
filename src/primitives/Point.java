package primitives;

public class Point {
	final Double3 xyz;
	
	public static Point ZERO = new Point(Double3.ZERO);
	
	Point(double x, double y, double z) {
		this.xyz = new Double3(x,y,z);
	}
	
	Point(Double3 xyz) {
		this.xyz = xyz;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		return obj instanceof Point other && xyz.equals(other.xyz);
	}
	@Override
	public String toString() { return "" + xyz; }
	
	//
	public Point add(Vector vec) {
		return new Point(xyz.add(vec.xyz));
	}
	
	public Vector substract(Point po) {
		return new Vector(xyz.subtract(po.xyz));
		
	}
	
	public double distance(Point po) {
		 return Math.sqrt(distanceSquared(po));
	}
	
	public double distanceSquared(Point po) {
		double newX = this.xyz.d1 - po.xyz.d1;
		double newY = this.xyz.d2 - po.xyz.d2;
		double newZ = this.xyz.d3 - po.xyz.d2;
		return newX * newX + newY * newY + newZ * newZ;
	}
}
