package geometries;

import java.util.List;

import primitives.*;

/**
 * Represents a tube in three-dimensional space.
 */
public class Tube extends RadialGeometry {

	/** The axis of the tube. */
	protected final Ray axis;

	/**
	 * Constructs a new tube with the specified radius and axis.
	 *
	 * @param radius The radius of the tube.
	 * @param axis   The axis of the tube.
	 */
	public Tube(double radius, Ray axis) {
		super(radius);
		this.axis = axis;
	}

	/**
	 * Returns the normal vector at the specified point on the surface of the tube.
	 * 
	 * @param pointOnSurface The point on the surface of the tube.
	 * @return The normal vector at the specified point.
	 */
	@Override
	public Vector getNormal(Point pointOnSurface) {
		double t = this.axis.getDirection().dotProduct((pointOnSurface.subtract(this.axis.getHead())));
		return pointOnSurface.subtract(axis.getPoint(t)).normalize();
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

}