package lighting;

import primitives.*;
import static primitives.Util.*;

/**
 * The SpotLight class represents a spot light source in the scene. A spot light
 * source has a specific position and direction, and its intensity diminishes
 * with distance and angle from the direction.
 */
public class SpotLight extends PointLight {

	/** The direction of the spot light. */
	private final Vector direction;

	/** The narrow beam factor of the spot light. Default is 1. */
	private double narrowBeam = 1;

	/**
	 * Constructs a SpotLight with the specified intensity, position, and direction.
	 *
	 * @param intensity The intensity (color) of the spot light.
	 * @param position  The position of the spot light.
	 * @param direction The direction of the spot light.
	 */
	public SpotLight(Color intensity, Vector direction, Point position) {
		super(intensity, position);
		this.direction = direction.normalize();
	}

	/**
	 * Sets the constant attenuation factor (kC) of the spot light.
	 *
	 * @param kC The constant attenuation factor.
	 * @return The SpotLight object itself for method chaining.
	 */
	@Override
	public SpotLight setKC(double kC) {
		return (SpotLight) super.setKC(kC);
	}

	/**
	 * Sets the linear attenuation factor (kL) of the spot light.
	 *
	 * @param kL The linear attenuation factor.
	 * @return The SpotLight object itself for method chaining.
	 */
	@Override
	public SpotLight setKL(double kL) {
		return (SpotLight) super.setKL(kL);
	}

	/**
	 * Sets the quadratic attenuation factor (kQ) of the spot light.
	 *
	 * @param kQ The quadratic attenuation factor.
	 * @return The SpotLight object itself for method chaining.
	 */
	@Override
	public SpotLight setKQ(double kQ) {
		return (SpotLight) super.setKQ(kQ);
	}

	/**
	 * Calculates the intensity (color) of the light at the specified point.
	 *
	 * @param p The point at which to calculate the light intensity.
	 * @return The intensity of the light at the specified point.
	 */
	@Override
	public Color getIntensity(Point p) {
		final double dirL = alignZero(direction.dotProduct(getL(p)));
		return dirL <= 0 ? Color.BLACK : super.getIntensity(p).scale(Math.pow(dirL, narrowBeam));
	}

	/**
	 * Sets the narrow beam factor of the spot light.
	 *
	 * @param narrowBeam The narrow beam factor.
	 * @return The SpotLight object itself for method chaining.
	 */
	public SpotLight setNarrowBeam(double narrowBeam) {
		this.narrowBeam = narrowBeam;
		return this;
	}

	@Override
	public double getDistance(Point point) {
		return position.distance(point);
	}
}
