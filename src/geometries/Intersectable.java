package geometries;

import java.util.List;
import primitives.*;

/**
 * Interface representing an intersectable geometry object.
 */
public abstract class Intersectable {

	/**
	 * Finds the intersection points between a given ray and the geometry.
	 *
	 * @param ray the ray to intersect with the geometry
	 * @return a list of intersection points, or null if there are no intersections
	 */
	public List<Point> findIntersections(Ray ray){
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp->gp.point).toList();
	}
	
	public static class GeoPoint { 
	    public Geometry geometry; 
	    public Point point; 
	    
	    public GeoPoint(Geometry geometry, Point point) {
	    	this.geometry = geometry;
	    	this.point = point;
	    }
	    
	    /**
		 * Checks if this point is equal to another object.
		 *
		 * @param obj The object to compare to.
		 * @return true if the objects are equal, false otherwise.
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			return obj instanceof GeoPoint other && geometry.equals(other.geometry)//
					&& point.equals(other.point);
		}

		/**
		 * Returns a string representation of this point.
		 *
		 * @return A string representation of this point.
		 */
		@Override
		public String toString() {
			return "" + geometry + "" + point;
		}
	} 
	
	public final List<GeoPoint> findGeoIntersections(Ray ray){
		return findGeoIntersectionsHelper(ray);
	}
	
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
	
}
