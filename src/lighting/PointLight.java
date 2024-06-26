package lighting;

import primitives.*;

/**
 * The PointLight class represents a point light source in the scene. A point
 * light source has a specific position and its intensity diminishes with
 * distance according to the attenuation factors kC, kL, and kQ.
 */
public class PointLight extends Light implements LightSource {

	/** The position of the point light. */
	protected Point position;

	/** The constant attenuation factor. */
	private double kC = 1;

	/** The linear attenuation factor. */
	private double kL = 0;

	/** The quadratic attenuation factor. */
	private double kQ = 0;

	/**
	 * Constructs a PointLight with the specified intensity and position.
	 *
	 * @param intensity The intensity (color) of the point light.
	 * @param position  The position of the point light.
	 */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
	}

	/**
	 * Sets the constant attenuation factor (kC) of the point light.
	 *
	 * @param kC The constant attenuation factor.
	 * @return The PointLight object itself for method chaining.
	 */
	public PointLight setKC(double kC) {
		this.kC = kC;
		return this;
	}

	/**
	 * Sets the linear attenuation factor (kL) of the point light.
	 *
	 * @param kL The linear attenuation factor.
	 * @return The PointLight object itself for method chaining.
	 */
	public PointLight setKL(double kL) {
		this.kL = kL;
		return this;
	}

	/**
	 * Sets the quadratic attenuation factor (kQ) of the point light.
	 *
	 * @param kQ The quadratic attenuation factor.
	 * @return The PointLight object itself for method chaining.
	 */
	public PointLight setKQ(double kQ) {
		this.kQ = kQ;
		return this;
	}

	/**
	 * Calculates the intensity (color) of the light at the specified point.
	 *
	 * @param p The point at which to calculate the light intensity.
	 * @return The intensity of the light at the specified point.
	 */
	@Override
	public Color getIntensity(Point p) {
		double d = position.distance(p);
		return super.getIntensity().scale(1d / (kC + kL * d + kQ * d * d));
	}

	/**
	 * Calculates the direction of the light from the light source to the specified
	 * point.
	 *
	 * @param p The point to which the light direction is calculated.
	 * @return The direction of the light as a vector, or null if the point is at
	 *         the light source position.
	 */
	@Override
	public Vector getL(Point p) {
		return p.equals(position) ? null : p.subtract(position).normalize();
	}
}
