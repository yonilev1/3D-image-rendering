/**
 * 
 */
package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	/** Scene for the tests */
	private final Scene scene = new Scene("Test scene");
	/** Camera builder for the tests with triangles */
	private final Camera.Builder cameraBuilder = Camera.getBuilder()
			.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)).setRayTracer(new SimpleRayTracer(scene));

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheres() {
		scene.geometries.add(
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(100).setKT(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)));
		scene.lights.add(new SpotLight(new Color(1000, 600, 0), new Vector(-1, -1, -2), new Point(-100, -100, 500))
				.setKL(0.0004).setKQ(0.0000006));

		cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(150, 150)
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)).build().renderImage().writeToImage();
	}

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheresOnMirrors() {
		scene.geometries.add(
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)).setMaterial(
						new Material().setKD(0.25).setKS(0.25).setShininess(20).setKT(new Double3(0.5, 0, 0))),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
						.setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000))
						.setEmission(new Color(20, 20, 20)).setMaterial(new Material().setKR(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)).setEmission(new Color(20, 20, 20))
						.setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Vector(-1, -1, -4), new Point(-750, -750, -150))
				.setKL(0.00001).setKQ(0.000005));

		cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000).setVpSize(2500, 2500)
				.setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500)).build().renderImage()
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		scene.geometries.add(
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.6)));
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Vector(0, 0, -1), new Point(60, 50, 0)).setKL(4E-5)
				.setKQ(2E-7));

		cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
				.setImageWriter(new ImageWriter("refractionShadow", 600, 600)).build().renderImage().writeToImage();
	}
	
	/** Geometry combination including refraction and reflection */
	@Test
	public void geometryCombinationTest() {
		Camera.Builder camera = Camera.getBuilder().setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)).setRayTracer(new SimpleRayTracer(scene));

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add(
				new Sphere(new Point(0,0, 0), 40d).setEmission(new Color(YELLOW))
						.setMaterial(new Material().setKD(0.3).setKS(0.5).setShininess(10).setKR(0.5)),
				new Triangle(new Point(0, 20, 0), new Point(20, 70, 0), new Point(-20, 70, 0))
						.setMaterial(new Material().setKD(0.2).setKS(0.6).setShininess(8).setKT(0.6))
						.setEmission(new Color(RED)));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)), //
				new Sphere(new Point(60, 50, -50),30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Vector(0, 0, -1), new Point(60, 50, 0)) //
				.setKL(4E-5).setKQ(2E-7));
		
		camera.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
		.setImageWriter(new ImageWriter("GeometryCombination", 600, 600)).build().renderImage()
		.writeToImage();
	}

	/**
	 * Mega geometry combination test including all features
	 */
	@Test
	public void megaTest() {
		
		Camera.Builder camera = Camera.getBuilder().setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)).setRayTracer(new SimpleRayTracer(scene));

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add(
				new Sphere(new Point(-80, 10, -50), 30d)
						.setMaterial(new Material().setKD(0.3).setKS(0.5).setShininess(10).setKT(0.55).setKR(0.5)),
				new Sphere(new Point(60, 0, 0), 35d)
						.setMaterial(new Material().setKD(0.6).setKS(0.2).setShininess(3).setKR(0.4).setKT(0.2))
						.setEmission(new Color(252, 148, 3)),
				new Sphere(new Point(-15, -40, 10), 25d)
						.setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(5).setKR(0.7).setKT(0.2))
						.setEmission(new Color(252, 3, 252)),

				new Plane(new Point(0, 160, 0), new Vector(0, 1, 0)).setMaterial(new Material().setKD(0.9).setKR(0.4))
						.setEmission(new Color(170, 170, 170)),

				new Polygon(new Point(-80, 80, -60), new Point(80, 80, -60), new Point(80, 100, 10),
						new Point(-80, 100, 10)).setMaterial(new Material().setKD(0.6).setKT(0.1))
						.setEmission(new Color(3, 250, 190)),

				new Triangle(new Point(60, 60, 20), new Point(100, 70, 0), new Point(70, 100, 0))
						.setMaterial(new Material().setKD(0.2).setKS(0.6).setShininess(8).setKT(0.6))
						.setEmission(new Color(250, 70, 0)),

				new Triangle(new Point(-20, 20, -60), new Point(30, 70, 0), new Point(-20, 70, 0))
						.setMaterial(new Material().setKD(0.2).setKS(0.6).setShininess(8).setKT(0.6))
						.setEmission(new Color(RED)));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, -75, -150)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, -70, -140), new Point(75, -75, -150)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)), //
				new Sphere(new Point(60, 50, -50),30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Vector(0, 0, -1), new Point(60, 50, 0)) //
				.setKL(4E-5).setKQ(2E-7));

		camera.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
		.setImageWriter(new ImageWriter("MegaMega", 600, 600)).build().renderImage()
		.writeToImage();
	}
}
