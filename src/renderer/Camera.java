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
public class Camera  implements Cloneable {
    

    private Point p0 ; // The location of the camera
    private Vector vTo; // The forward direction vector of the camera
    private Vector vUp; // The up direction vector of the camera
    private Vector vRight; // The right direction vector of the camera
    private boolean isDof = false; 

    private double height = 0; // The height of the view plane
    private double width = 0; // The width of the view plane
    private double distanceFromCamera = 0; // The distance from the camera to the view plane

    private ImageWriter imageWriter; // The image writer responsible for writing pixels to an image
    private RayTracerBase rayTracer; // The ray tracer responsible for tracing rays and computing colors
    public DOF dof = new DOF();

    private Camera() {
  
    }

    // Getters and Setters
   
    
    public Point getCameraLocation() {
        return p0;
    }
    
    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistanceFromCamera() {
        return distanceFromCamera;
    }

    public static Builder getBuilder() {
        return new Builder();
    }
    
    public Vector getVup() {
        return vUp;
    }
    public Vector getVright() {
        return vRight;
    }
    public boolean getIsDof() {
        return isDof;
    }
    
    
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
       
    private void castRay(int nX, int nY, int j, int i) {
		List<Ray> rays = new ArrayList<>();
		if (!getIsDof()) {
			rays.add(constructRay(nX, nY,j, i));}
		else{
			Point pij =  findPij( nX,  nY,  j,  i);
            rays= dof.constructRayWithDOF(pij, this);
            }
        Color pixelColor = Color.BLACK;
        for (Ray ray : rays) {
            pixelColor = pixelColor.add(rayTracer.traceRay(ray));
        }
        pixelColor = pixelColor.reduce(rays.size());
        imageWriter.writePixel(j, i, pixelColor);
    }
    
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
     * @param nX the number of pixels in the X direction.
     * @param nY the number of pixels in the Y direction.
     * @param j  the pixel index in the X direction.
     * @param i  the pixel index in the Y direction.
     * @return the constructed ray.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
    	Point pij =  findPij( nX,  nY,  j,  i);
        return  new Ray (p0, pij.subtract(p0));
    }
        
   
    /**
     * Constructs a ray from the camera through a specific pixel on the view plane
     * with depth of field effect.
     *
     * @param nX the number of pixels in the X direction.
     * @param nY the number of pixels in the Y direction.
     * @param j  the pixel index in the X direction.
     * @param i  the pixel index in the Y direction.
     * @return the list of rays for depth of field effect.
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

    public void writeToImage() {
        imageWriter.writeToImage();
    }

    public static class Builder {
        private final Camera camera = new Camera();

        public Builder() {}

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

        public Builder cameraSpin(double angle) {
            double angleRadians = Math.toRadians(angle);
            double cos = Math.cos(angleRadians);
            double sin = Math.sin(angleRadians);

            Vector originalVRight = camera.vRight;
            Vector originalVUp = camera.vUp;

            camera.vRight = new Vector(
                cos * originalVRight.getX() + sin * originalVUp.getX(),
                cos * originalVRight.getY() + sin * originalVUp.getY(),
                cos * originalVRight.getZ() + sin * originalVUp.getZ()
            ).normalize();

            camera.vUp = new Vector(
                -sin * originalVRight.getX() + cos * originalVUp.getX(),
                -sin * originalVRight.getY() + cos * originalVUp.getY(),
                -sin * originalVRight.getZ() + cos * originalVUp.getZ()
            ).normalize();

            return this;
        }
        
        public Builder setLocation(Point p) {
            camera.p0 = p;
            return this;
        }
        
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (!isZero(vUp.dotProduct(vTo)))
                throw new IllegalArgumentException("vUp and vTo must be orthogonal");

            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            camera.vRight = camera.vTo.crossProduct(camera.vUp);
            return this;
        }

        public Builder setVpSize(double width, double height) {
            if (alignZero(width) <= 0 || alignZero(height) <= 0) {
                throw new IllegalArgumentException("width and height must be greater than 0");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        public Builder setVpDistance(double distanceFromCamera) {
            if (distanceFromCamera < 0) {
                throw new IllegalArgumentException("distanceFromCamera must be greater than 0");
            }
            camera.distanceFromCamera = distanceFromCamera;
            return this;
        }

        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }
        
        /**
         * Sets the ray tracer for the camera configuration.
         * 
         * @param rayTracer The ray tracer to be set.
         * @return This Builder object.
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }
        
        public Builder setDOF(double aperture, double focalDistance) {
            camera.dof.setAperture(aperture);
            camera.dof.setFocalDistance(focalDistance);
            camera.isDof = true;
            return this;
        }

        public Camera build() {
            final String MISSING_RENDERING_DATA = "Missing rendering data";

            if (camera.p0 == null)
                throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "Location (Point p)");
            if (camera.vTo == null)
                throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "vTo vector");
            if (camera.vUp == null)
                throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "vUp vector");
            if (isZero(camera.vTo.dotProduct(camera.vTo)))
                throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "vTo vector and vUp vectore");
            if (camera.vRight == null)
                camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            if (isZero(camera.width))
                throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "View plane width");
            if (isZero(camera.height))
                throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "View plane height");
            if (camera.width < 0 || camera.height < 0)
                throw new IllegalStateException("Invalid view plane size. Width and height must be greater than zero.");
            if (isZero(camera.distanceFromCamera))
                throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "Distance from camera");
            if (camera.distanceFromCamera < 0)
                throw new IllegalStateException("Invalid distance from camera. Must be greater than zero.");
            if (camera.imageWriter == null)
                throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "View plane imageWriter");
            if (camera.rayTracer == null)
                throw new MissingResourceException(MISSING_RENDERING_DATA, Camera.class.getName(), "View plane rayTracer");

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }
}
