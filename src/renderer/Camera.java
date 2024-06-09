package renderer;

import primitives.*;
import primitives.Vector;

import static primitives.Util.*;

import java.util.*;

/**
 * Camera class represents a camera in a 3D scene. The camera is characterized
 * by its location and orientation vectors (vRight, vUp, vTo). It also has
 * properties defining the view plane's dimensions and its distance from the
 * camera.
 */
public class Camera implements Cloneable {

	/**
	 * The location of the camera.
	 */
	private Point p0;

	/**
	 * The forward direction vector of the camera.
	 */
	private Vector vTo;

	/**
	 * The up direction vector of the camera.
	 */
	private Vector vUp;

	/**
	 * The right direction vector of the camera.
	 */
	private Vector vRight;

	/**
	 * The height of the view plane.
	 */
	protected double height = 0;

	/**
	 * The width of the view plane.
	 */
	protected double width = 0;

	/**
	 * The distance from the camera to the view plane.
	 */
	protected double distanceFromCamera = 0;

	/**
	 * Private constructor to enforce the use of the builder.
	 */
	private Camera() {
	}

	/**
	 * Gets the height of the view plane.
	 * 
	 * @return the height of the view plane.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Gets the width of the view plane.
	 * 
	 * @return the width of the view plane.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Gets the distance from the camera to the view plane.
	 * 
	 * @return the distance from the camera to the view plane.
	 */
	public double getDistanceFromCamera() {
		return distanceFromCamera;
	}

	/**
	 * Returns a new Builder instance for constructing a Camera.
	 * 
	 * @return a new Builder instance.
	 */
	public static Builder getBuilder() {
		return new Builder();
	}

	/**
	 * Constructs a ray from the camera through a specific pixel on the view plane.
	 * 
	 * @param nX the number of pixels in the X direction.
	 * @param nY the number of pixels in the Y direction.
	 * @param j  the pixel index in the X direction.
	 * @param i  the pixel index in the Y direction.
	 * @return the constructed ray.
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		 // Calculate the image center
        Point pc = p0.add(vTo.scale(distanceFromCamera));

        // Calculate the x and y offsets of the pixel
        double xj = (j - (nX - 1) / 2.0) * (width / nX);
        double yi = -(i - (nY - 1) / 2.0) * ( height / nY);

        // Calculate the pixel's center point on the view plane
        Point pij = pc;

        if (!isZero(xj)) {
            pij = pij.add(vRight.scale(xj));
        }

        if (!isZero(yi)) {
            pij = pij.add(vUp.scale(yi));
        }

        // Return the constructed ray
        return new Ray(p0, pij.subtract(p0).normalize());
    
	}

	/**
	 * Builder class for constructing a Camera instance.
	 */
	public static class Builder {

		/**
		 * A new Camera instance being built.
		 */
		final Camera camera = new Camera();

		/**
		 * Sets the location of the camera.
		 * 
		 * @param p the point representing the camera's location.
		 * @return the builder instance.
		 */
		public Builder setLocation(Point p) {
			camera.p0 = p;
			return this;
		}

		/**
		 * Sets the direction of the camera. This method ensures that the provided
		 * vectors are orthogonal and normalizes them before setting.
		 * 
		 * @param vUp the up direction vector.
		 * @param vTo the forward direction vector.
		 * @return the builder instance.
		 * @throws IllegalArgumentException if the vectors are not orthogonal.
		 */
		public Builder setDirection(Vector vUp, Vector vTo) {
			// Check if vectors are orthogonal
			if (!isZero(vUp.dotProduct(vTo))) {
				throw new IllegalArgumentException("vUp and vTo must be orthogonal");
			}
			// Normalize the vectors
			camera.vUp = vUp.normalize();
			camera.vTo = vTo.normalize();
			// Calculate vRight as the cross product of vTo and vUp
			camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
			return this;
		}

		/**
		 * Sets the size of the view plane.
		 * 
		 * @param length the length of the view plane.
		 * @param height the height of the view plane.
		 * @return the builder instance.
		 * @throws IllegalArgumentException if the length or height are not greater than
		 *                                  0.
		 */
		public Builder setVpSize(double length, double height) {
			if (length < 0 || height < 0) {
				throw new IllegalArgumentException("length and height must be greater than 0");
			}
			camera.width = length;
			camera.height = height;
			return this;
		}

		/**
		 * Sets the distance from the camera to the view plane.
		 * 
		 * @param distanceFromCamera the distance from the camera to the view plane.
		 * @return the builder instance.
		 * @throws IllegalArgumentException if the distance from the camera is not
		 *                                  greater than 0.
		 */
		public Builder setVpDistance(double distanceFromCamera) {
			if (distanceFromCamera < 0) {
				throw new IllegalArgumentException("distanceFromCamera must be greater than 0");
			}
			camera.distanceFromCamera = distanceFromCamera;
			return this;
		}

		/**
		 * Builds the Camera instance.
		 * 
		 * @return a clone of the constructed Camera.
		 * @throws MissingResourceException if any of the required fields are not set.
		 */
		public Camera build() {

			String MISSING_RENDERING_DATA = "Missing rendering data";
			String CAMERA_CLASS_NAME = "Camera";

			if (camera.p0 == null) {
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "Location (Point p)");
			}
			if (camera.vTo == null) {
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "vTo vector");
			}
			if (camera.vUp == null) {
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "vUp vector");
			}
			if (camera.vRight == null) {
				camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "vRight vector");
			}
			if (camera.width == 0) {
				throw new MissingResourceException(Camera.class.getName(), CAMERA_CLASS_NAME, "View plane length");
			}
			if (camera.height == 0) {
				throw new MissingResourceException(Camera.class.getName(), CAMERA_CLASS_NAME, "View plane height");
			}
			if (camera.distanceFromCamera == 0) {
				throw new MissingResourceException(Camera.class.getName(), CAMERA_CLASS_NAME, "Distance from camera");
			}
			// Validate the view plane size
			if (camera.width < 0 || camera.height < 0) {
				throw new IllegalStateException(
						"Invalid view plane size. Length and height must be greater than zero.");
			}

			// Validate the distance from the camera to the view plane
			if (camera.distanceFromCamera < 0) {
				throw new IllegalStateException("Invalid distance from camera. Must be greater than zero.");
			}
			// Return a clone of the camera instance
			try {
				return (Camera) camera.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
		}

	}

}
