package primitives;

public class Ray {
	
	private final Point head;
	
	private final Vector direction;
	
	public Ray(Point po, Vector vec) {
		this.head = po;
		this .direction = vec;
	}
	
	@Override
	public final boolean equals(Object ob) {
		if (this ==ob) return true;
		return ob instanceof Ray other && //
				this.head.equals(other.head) && //
				this.direction.equals(other.direction);
	}
	
	@Override
	public String toString() {
		return "Ray:" + head + "->" + direction;
	}

}
