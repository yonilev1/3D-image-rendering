package geometries;

import primitives.*;

/**
 * The Geometry abstract class extends Intersectable and represents a geometric
 * shape in three-dimensional space. Classes that inherit from this class must
 * provide a method to calculate the normal vector at a specified point on the
 * surface of the shape.
 */
public abstract class Geometry extends Intersectable {

	/** The emission color of the geometry. */
	protected Color emission = Color.BLACK;

	/** The material properties of the geometry. */
	private Material material = new Material();

	/**
	 * Returns the emission color of the geometry.
	 *
	 * @return The emission color of the geometry.
	 */
	public Color getEmission() {
		return this.emission;
	}

	/**
	 * Returns the material properties of the geometry.
	 *
	 * @return The material properties of the geometry.
	 */
	public Material getMaterial() {
		return this.material;
	}

	/**
	 * Sets the emission color of the geometry.
	 *
	 * @param emission The new emission color to be set.
	 * @return The Geometry object itself for method chaining.
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * Sets the material properties of the geometry.
	 *
	 * @param material The new material to be set.
	 * @return The Geometry object itself for method chaining.
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}

	/**
	 * Calculates the normal vector at the specified point on the surface of the
	 * geometry.
	 *
	 * @param pointOnSurface The point on the surface of the geometry.
	 * @return The normal vector at the specified point.
	 */
	public abstract Vector getNormal(Point pointOnSurface);
}
