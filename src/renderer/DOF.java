package renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * DOF (Depth of Field) class represents the depth of field effect in the
 * camera. It is characterized by the aperture size and the focal distance.
 */
public class DOF {

	/**
	 * Radius of the camera's aperture.
	 */
	private double aperture;

	/**
	 * Distance to the focal plane.
	 */
	private double focalDistance;

	/**
	 * Default constructor.
	 */
	public DOF() {
	}

	/**
	 * Gets the focal distance.
	 *
	 * @return the focal distance
	 */
	public double getFocalDistance() {
		return focalDistance;
	}

	/**
	 * Gets the aperture size.
	 *
	 * @return the aperture size
	 */
	public double getAperture() {
		return aperture;
	}

	/**
	 * Sets the focal distance.
	 *
	 * @param focalDistance the focal distance to set
	 */
	public void setFocalDistance(double focalDistance) {
		this.focalDistance = focalDistance;
	}

	/**
	 * Sets the aperture size.
	 *
	 * @param aperture the aperture size to set
	 */
	public void setAperture(double aperture) {
		this.aperture = aperture;
	}

	/**
	 * Constructs a list of rays for depth of field effect.
	 *
	 * @param pij        the point on the view plane
	 * @param thisCamera the camera object
	 * @return the list of rays for depth of field effect
	 */
	public List<Ray> constructRayWithDOF(Point pij, Camera thisCamera) {
		List<Ray> rays = new ArrayList<>();
		Point focalPoint = pij.add(pij.subtract(thisCamera.getCameraLocation()).normalize().scale(focalDistance));
		Random rand = new Random();
		int numRays = 10; 
		for (int k = 0; k < numRays; k++) {
			double angle = 2 * Math.PI * rand.nextDouble();
			double radius = aperture * Math.sqrt(rand.nextDouble());
			double apertureX = radius * Math.cos(angle);
			double apertureY = radius * Math.sin(angle);

			Point randomAperturePoint = thisCamera.getCameraLocation().add(thisCamera.getVright().scale(apertureX))
					.add(thisCamera.getVup().scale(apertureY));
			Vector rayDirection = focalPoint.subtract(randomAperturePoint).normalize();
			rays.add(new Ray(randomAperturePoint, rayDirection));
		}

		return rays;
	}
}
