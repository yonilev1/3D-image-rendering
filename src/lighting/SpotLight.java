package lighting;

import primitives.*;

/**
 * The SpotLight class represents a spot light source in the scene.
 * A spot light source has a specific position and direction, and its intensity diminishes
 * with distance and angle from the direction.
 */
public class SpotLight extends PointLight {

    private Vector direction;

    /**
     * Constructs a SpotLight with the specified intensity, position, and direction.
     *
     * @param intensity The intensity (color) of the spot light.
     * @param position  The position of the spot light.
     * @param direction The direction of the spot light.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
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
        super.setKC(kC);
        return this;
    }

    /**
     * Sets the linear attenuation factor (kL) of the spot light.
     *
     * @param kL The linear attenuation factor.
     * @return The SpotLight object itself for method chaining.
     */
    @Override
    public SpotLight setKL(double kL) {
        super.setKL(kL);
        return this;
    }

    /**
     * Sets the quadratic attenuation factor (kQ) of the spot light.
     *
     * @param kQ The quadratic attenuation factor.
     * @return The SpotLight object itself for method chaining.
     */
    @Override
    public SpotLight setKQ(double kQ) {
        super.setKQ(kQ);
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
        double factor = Math.max(0, direction.dotProduct(getL(p)));
        return super.getIntensity(p).scale(factor);
    }

    /**
     * Calculates the direction of the light from the light source to the specified point.
     *
     * @param p The point to which the light direction is calculated.
     * @return The direction of the light as a vector, or null if the point is at the light source position.
     */
    @Override
    public Vector getL(Point p) {
        return p.equals(position) ? null : p.subtract(position).normalize();
    }
}
