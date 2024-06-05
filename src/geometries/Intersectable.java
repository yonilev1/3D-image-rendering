package geometries;

import java.util.List;
import primitives.*;

/**
 * Interface representing an intersectable geometry object.
 */
public interface Intersectable {

	/**
	 * Finds the intersection points between a given ray and the geometry.
	 *
	 * @param ray the ray to intersect with the geometry
	 * @return a list of intersection points, or null if there are no intersections
	 */
	public List<Point> findIntersections(Ray ray);

}
