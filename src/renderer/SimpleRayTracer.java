package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.*;
import primitives.*;
import static primitives.Util.*;
import scene.Scene;

/**
 * A simple implementation of the RayTracerBase class. This class provides basic
 * ray tracing functionality for a 3D scene.
 */
public class SimpleRayTracer extends RayTracerBase {

	/**
	 * Constructs a new SimpleRayTracer with the specified scene.
	 *
	 * @param scene The scene to be rendered.
	 */
	public SimpleRayTracer(Scene scene) {
		super(scene);
	}

	/**
	 * Traces a ray through the scene, computes the color at the intersection point
	 * (if any), and returns the background color of the scene if no intersection is
	 * found.
	 *
	 * @param ray The ray to trace through the scene.
	 * @return The color at the intersection point or the background color if no
	 *         intersection is found.
	 */
	@Override
	public Color traceRay(Ray ray) {
		// Find intersections of the ray with the scene geometries
		var intersections = scene.geometries.findGeoIntersectionsHelper(ray);

		// If no intersections found, return the background color of the scene
		return intersections == null ? scene.background //
				: calcColor(ray.findClosestGeoPoint(intersections),ray);
	}

	/**
	 * Private method to calculate the color at a given point in the scene. For now,
	 * returns the ambient light intensity of the scene.
	 *
	 * @param closestPoint a point to find its color
	 * @return The color at the specified point.
	 */
	private Color calcColor(GeoPoint intersection,Ray ray) {
		return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray)); // Return ambient light intensity for now
	}
	
	private Color calcLocalEffects(GeoPoint gp, Ray ray) {
		Color color = gp.geometry.getEmission();
		Vector v =ray.getDirection();
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = alignZero(n.dotProduct(v));
		Material material = gp.geometry.getMaterial();
		
		if(nv == 0)
			return color;
		
		
		for(LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = alignZero(n.dotProduct(l));
			if(nl*nv > 0) {
				Color iL = lightSource.getIntensity(gp.point);
				color = color.add(iL.scale(calcDiffusive(material,nl)//
						.add(calcSpecular(material,n,l,nl,v))));
			}
		}
		return color;
	}
	
	private Double3 calcDiffusive(Material material, double nl) {
	    return material.kD.scale(nl >0 ? nl : -nl);
	}
	
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
	    Vector r = l.subtract(n.scale(2 * nl)); // Reflection vector
	    double rv = alignZero(-r.dotProduct(v));
	    
	    return rv <= 0 ? Double3.ZERO : material.kS.scale(Math.pow(rv, material.shininess));
	}


}
