package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Represents a cylinder in three-dimensional space. A cylinder is defined by
 * its height and a ray that defines its axis.
 */
public class Cylinder extends Tube {

	/** The height of the cylinder. */
	private final double height;

	/**
	 * Constructs a new cylinder with the specified height, axis ray, and radius.
	 *
	 * @param height  The height of the cylinder.
	 * @param axisRay The ray that defines the axis of the cylinder.
	 * @param radius  The radius of the cylinder.
	 */
	public Cylinder(double height, Ray axisRay, double radius) {
		super(radius, axisRay);
		this.height = height;
	}

	/**
	 * Returns the normal vector at the specified point on the surface of the
	 * cylinder.
	 * 
	 * @param pointOnSurface The point on the surface of the cylinder.
	 * @return The normal vector at the specified point.
	 */
	@Override
	public Vector getNormal(Point pointOnSurface) {
		Point pHead = axis.getHead(); // Base point of the axis
		Vector vecDir = axis.getDirection(); // Direction of the axis

		// Project pointOnSurface onto the axis to get the parameter t
		double t = vecDir.dotProduct(pointOnSurface.subtract(pHead));

		// Check if the point is on the bottom base
		if (Util.isZero(t)) {
			return vecDir.scale(-1); // Normal pointing inward
		}

		// Check if the point is on the top base
		if (t >= height) {
			return vecDir; // Normal pointing outward
		}

		// If the point is on the curved surface
		Point o = pHead.add(vecDir.scale(t)); // Projection of the point onto the axis
		return pointOnSurface.subtract(o).normalize();
	}
}
