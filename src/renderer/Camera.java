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
	private double height = 0;

	/**
	 * The width of the view plane.
	 */
	private double width = 0;

	/**
	 * The distance from the camera to the view plane.
	 */
	private double distanceFromCamera = 0;

	/**
	 * The image writer responsible for writing pixels to an image.
	 */
	private ImageWriter imageWriter;

	/**
	 * The ray tracer responsible for tracing rays and computing colors.
	 */
	private RayTracerBase rayTracer;

	/**
	 * Depth of Field settings.
	 */
	public DOF dof = new DOF();

	/**
	 * Private constructor for the Camera class.
	 */
	private Camera() {
	}


	/**
	 * Gets the location of the camera.
	 * 
	 * @return The camera location.
	 */
	public Point getCameraLocation() {
		return p0;
	}

	/**
	 * Gets the height of the view plane.
	 * 
	 * @return The height of the view plane.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Gets the width of the view plane.
	 * 
	 * @return The width of the view plane.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Gets the distance from the camera to the view plane.
	 * 
	 * @return The distance from the camera to the view plane.
	 */
	public double getDistanceFromCamera() {
		return distanceFromCamera;
	}

	/**
	 * Gets the builder for the Camera class.
	 * 
	 * @return The Camera Builder.
	 */
	public static Builder getBuilder() {
		return new Builder();
	}

	/**
	 * Gets the up direction vector of the camera.
	 * 
	 * @return The up direction vector.
	 */
	public Vector getVup() {
		return vUp;
	}

	/**
	 * Gets the right direction vector of the camera.
	 * 
	 * @return The right direction vector.
	 */
	public Vector getVright() {
		return vRight;
	}
	
	/**
	 * Renders the image.
	 * 
	 * @return The camera object.
	 */
	public Camera renderImage() {
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();
		for (int i = 0; i < nY; ++i) {
			for (int j = 0; j < nX; ++j) {
				castRay(nX, nY, j, i);
			}
		}
		return this;
	}

	/**
	 * Casts a ray from the camera through a specific pixel.
	 * 
	 * @param nX The number of pixels in the X direction.
	 * @param nY The number of pixels in the Y direction.
	 * @param j  The pixel index in the X direction.
	 * @param i  The pixel index in the Y direction.
	 */
	private void castRay(int nX, int nY, int j, int i) {
		List<Ray> rays = new ArrayList<>();
		if (dof.getAperture() == 0 && dof.getFocalDistance() == 0) {
			rays.add(constructRay(nX, nY, j, i));
		} else {
			Point pij = findPij(nX, nY, j, i);
			rays = dof.constructRayWithDOF(pij, this);
		}
		Color pixelColor = Color.BLACK;
		for (Ray ray : rays) {
			pixelColor = pixelColor.add(rayTracer.traceRay(ray));
		}
		pixelColor = pixelColor.reduce(rays.size());
		imageWriter.writePixel(j, i, pixelColor);
	}

	/**
	 * Finds the point on the view plane corresponding to a specific pixel.
	 * 
	 * @param nX The number of pixels in the X direction.
	 * @param nY The number of pixels in the Y direction.
	 * @param j  The pixel index in the X direction.
	 * @param i  The pixel index in the Y direction.
	 * @return The point on the view plane.
	 */
	private Point findPij(int nX, int nY, int j, int i) {
		Point pij = p0.add(vTo.scale(distanceFromCamera));
		double xj = (j - ((nX - 1) / 2.0)) * (width / nX);
		double yi = (((nY - 1) / 2.0) - i) * (height / nY);

		if (!isZero(xj)) {
			pij = pij.add(vRight.scale(xj));
		}

		if (!isZero(yi)) {
			pij = pij.add(vUp.scale(yi));
		}
		return pij;
	}

	/**
	 * Constructs a ray from the camera through a specific pixel on the view plane.
	 *
	 * @param nX The number of pixels in the X direction.
	 * @param nY The number of pixels in the Y direction.
	 * @param j  The pixel index in the X direction.
	 * @param i  The pixel index in the Y direction.
	 * @return The constructed ray.
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		Point pij = findPij(nX, nY, j, i);
		return new Ray(p0, pij.subtract(p0));
	}

	/**
	 * Prints a grid on the image.
	 * 
	 * @param interval The interval between grid lines.
	 * @param color    The color of the grid lines.
	 * @return The camera object.
	 */
	public Camera printGrid(int interval, Color color) {
		int nY = imageWriter.getNy();
		int nX = imageWriter.getNx();

		for (int i = 0; i < nY; i += interval) {
			for (int j = 0; j < nX; j += 1) {
				imageWriter.writePixel(i, j, color);
			}
		}

		for (int i = 0; i < nY; i += 1) {
			for (int j = 0; j < nX; j += interval) {
				imageWriter.writePixel(i, j, color);
			}
		}

		return this;
	}

	/**
	 * Writes the image to a file.
	 */
	public void writeToImage() {
		imageWriter.writeToImage();
	}

	/**
	 * Builder class for constructing Camera objects.
	 */
	public static class Builder {

		/**
		 * Create Camera object in Builder class
		 */
		private final Camera camera = new Camera();

		/**
		 * Sets the view for the camera.
		 * 
		 * @param position The camera's position.
		 * @param p        The point the camera is looking at.
		 * @return The Builder object.
		 */
		public Builder setView(Point position, Point p) {
			camera.p0 = position;
			camera.vTo = p.subtract(position).normalize();
			if (camera.vTo.equals(new Vector(0, 0, -1)) || camera.vTo.equals(new Vector(0, 0, 1))) {
				camera.vRight = new Vector(0, -1, 0);
				camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();
			} else {
				camera.vRight = camera.vTo.crossProduct(new Vector(0, 0, 1)).normalize();
				camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();
			}
			return this;
		}

		/**
		 * Rotates the camera around its axis.
		 * 
		 * @param angle The angle to rotate the camera by.
		 * @return The Builder object.
		 */
		public Builder cameraSpin(double angle) {
			double angleRadians = Math.toRadians(angle);
			double cos = Math.cos(angleRadians);
			double sin = Math.sin(angleRadians);

			Vector originalVRight = camera.vRight;
			Vector originalVUp = camera.vUp;

			camera.vRight = new Vector(cos * originalVRight.getX() + sin * originalVUp.getX(),
					cos * originalVRight.getY() + sin * originalVUp.getY(),
					cos * originalVRight.getZ() + sin * originalVUp.getZ()).normalize();

			camera.vUp = new Vector(-sin * originalVRight.getX() + cos * originalVUp.getX(),
					-sin * originalVRight.getY() + cos * originalVUp.getY(),
					-sin * originalVRight.getZ() + cos * originalVUp.getZ()).normalize();

			return this;
		}

		/**
		 * Sets the location of the camera.
		 * 
		 * @param p The new location of the camera.
		 * @return The Builder object.
		 */
		public Builder setLocation(Point p) {
			camera.p0 = p;
			return this;
		}

		/**
		 * Sets the direction vectors of the camera.
		 * 
		 * @param vTo The forward direction vector.
		 * @param vUp The up direction vector.
		 * @return The Builder object.
		 * @throws IllegalArgumentException if vUp and vTo are not orthogonal.
		 */
		public Builder setDirection(Vector vTo, Vector vUp) {
			if (!isZero(vUp.dotProduct(vTo)))
				throw new IllegalArgumentException("vUp and vTo must be orthogonal");

			camera.vTo = vTo.normalize();
			camera.vUp = vUp.normalize();
			camera.vRight = camera.vTo.crossProduct(camera.vUp);
			return this;
		}

		/**
		 * Sets the size of the view plane.
		 * 
		 * @param width  The width of the view plane.
		 * @param height The height of the view plane.
		 * @return The Builder object.
		 * @throws IllegalArgumentException if width or height is less than or equal to
		 *                                  0.
		 */
		public Builder setVpSize(double width, double height) {
			if (alignZero(width) <= 0 || alignZero(height) <= 0) {
				throw new IllegalArgumentException("width and height must be greater than 0");
			}
			camera.width = width;
			camera.height = height;
			return this;
		}

		/**
		 * Sets the distance from the camera to the view plane.
		 * 
		 * @param distanceFromCamera The distance from the camera to the view plane.
		 * @return The Builder object.
		 * @throws IllegalArgumentException if distanceFromCamera is less than 0.
		 */
		public Builder setVpDistance(double distanceFromCamera) {
			if (distanceFromCamera < 0) {
				throw new IllegalArgumentException("distanceFromCamera must be greater than 0");
			}
			camera.distanceFromCamera = distanceFromCamera;
			return this;
		}

		/**
		 * Sets the image writer for the camera.
		 * 
		 * @param imageWriter The image writer.
		 * @return The Builder object.
		 */
		public Builder setImageWriter(ImageWriter imageWriter) {
			camera.imageWriter = imageWriter;
			return this;
		}

		/**
		 * Sets the ray tracer for the camera.
		 * 
		 * @param rayTracer The ray tracer.
		 * @return The Builder object.
		 */
		public Builder setRayTracer(RayTracerBase rayTracer) {
			camera.rayTracer = rayTracer;
			return this;
		}

		/**
		 * Sets the Depth of Field settings.
		 * 
		 * @param aperture      The aperture size.
		 * @param focalDistance The focal distance.
		 * @param numRays      The number of rays.
		 * @return The Builder object.
		 */
		public Builder setDOF(double aperture, double focalDistance, int numRays) {
			camera.dof.setAperture(aperture);
			camera.dof.setFocalDistance(focalDistance);
			camera.dof.setNumRays(numRays);
			return this;
		}

		/**
		 * Builds the Camera object.
		 * 
		 * @return The constructed Camera object.
		 * @throws MissingResourceException if any required field is missing.
		 * @throws IllegalStateException    if the view plane size or distance from the
		 *                                  camera is invalid.
		 */
		public Camera build() {
			final String MISSING_RENDERING_DATA = "Missing rendering data";

			if (camera.p0 == null)
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(),
						"Location (Point p)");
			if (camera.vTo == null)
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "vTo vector");
			if (camera.vUp == null)
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "vUp vector");
			if (isZero(camera.vTo.dotProduct(camera.vTo)))
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(),
						"vTo vector and vUp vector");
			if (camera.vRight == null)
				camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

			if (isZero(camera.width))
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "View plane width");
			if (isZero(camera.height))
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "View plane height");
			if (camera.width < 0 || camera.height < 0)
				throw new IllegalStateException("Invalid view plane size. Width and height must be greater than zero.");
			if (isZero(camera.distanceFromCamera))
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(),
						"Distance from camera");
			if (camera.distanceFromCamera < 0)
				throw new IllegalStateException("Invalid distance from camera. Must be greater than zero.");
			if (camera.imageWriter == null)
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(),
						"View plane imageWriter");
			if (camera.rayTracer == null)
				throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(),
						"View plane rayTracer");

			try {
				return (Camera) camera.clone();
			} catch (CloneNotSupportedException e) {
				throw new AssertionError();
			}
		}
	}
}
