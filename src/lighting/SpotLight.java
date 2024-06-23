package lighting;

import primitives.*;
import static primitives.Util.*;

/**
 * The SpotLight class represents a spot light source in the scene.
 * A spot light source has a specific position and direction, and its intensity diminishes
 * with distance and angle from the direction.
 */
public class SpotLight extends PointLight {

    private Vector direction;
    private double narrowBeam = 1;

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
    	double cosinus = alignZero(direction.dotProduct(getL(p)));
    	return narrowBeam != 1 ? super.getIntensity(p).scale(Math.pow(Math.max(0, cosinus), narrowBeam))//
    			:super.getIntensity(p).scale(Math.max(0, cosinus));
    }
    
    public SpotLight setNarrowBeam(double narrowBeam) {
    	this.narrowBeam = narrowBeam;
    	return this;
    }
}
