package unittests.renderer;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.*;
import geometries.*;
import lighting.*;
import primitives.*;
import org.junit.jupiter.api.Test;

public class SnowmanScene {
	
	@Test
    public void SMT() {
        Scene scene = new Scene("Snowman Scene");

        // Snowman Body
        scene.geometries.add(
                new Sphere(new Point(0, 0, -150), 50d).setEmission(new Color(255, 255, 255))
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
                new Sphere(new Point(0, 0, -50), 40d).setEmission(new Color(255, 255, 255))
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
                new Sphere(new Point(0, 0, 30), 30d).setEmission(new Color(255, 255, 255))
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20))
        );

        // Snowman Face
        scene.geometries.add(
                new Sphere(new Point(-10, 10, 50), 5d).setEmission(new Color(0, 0, 0))  // Left Eye
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
                new Sphere(new Point(10, 10, 50), 5d).setEmission(new Color(0, 0, 0))   // Right Eye
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
                new Sphere(new Point(0, -10, 60), 5d).setEmission(new Color(255, 165, 0))  // Nose
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
                new Sphere(new Point(0, 20, 45), 3d).setEmission(new Color(0, 0, 0))  // Left Button
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
                new Sphere(new Point(0, 30, 45), 3d).setEmission(new Color(0, 0, 0))  // Right Button
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20))
        );

        // Trees in the background
        for (int i = -200; i <= 200; i += 100) {
            scene.geometries.add(
                    new Sphere(new Point(i, 50, -300), 30).setEmission(new Color(34, 139, 34))  // Tree Base
                            .setMaterial(new Material().setKD(0.3).setKS(0.3).setShininess(20)),
                    new Sphere(new Point(i, 80, -300), 25).setEmission(new Color(0, 100, 0))  // Tree Middle
                            .setMaterial(new Material().setKD(0.3).setKS(0.3).setShininess(20)),
                    new Sphere(new Point(i, 100, -300), 20).setEmission(new Color(0, 100, 0))  // Tree Top
                            .setMaterial(new Material().setKD(0.3).setKS(0.3).setShininess(20))
            );
        }

        // Falling snow
        for (int i = -300; i <= 300; i += 30) {
            for (int j = -300; j <= 300; j += 30) {
                scene.geometries.add(
                        new Sphere(new Point(i, j, 200), 2d).setEmission(new Color(255, 255, 255))
                                .setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(10))
                );
            }
        }

        // Add lights
        scene.lights.add(new PointLight(new Color(250, 250, 250), new Point(100, 200, 200)).setKL(0.001).setKQ(0.00001));
        scene.lights.add(new DirectionalLight(new Color(155, 155, 155), new Vector(-1, -1, -1)));
        scene.lights.add(new SpotLight(new Color(255, 100, 100), new Vector(-1, -1, -2), new Point(200, 150, 100))
                .setKL(0.0005).setKQ(0.00005));  // Red tint spot light from the side
        scene.lights.add(new PointLight(new Color(100, 255, 100), new Point(-100, 200, 100))
                .setKL(0.0005).setKQ(0.00005));  // Green tint point light from the left
        scene.lights.add(new DirectionalLight(new Color(100, 100, 255), new Vector(1, -1, -1)));  // Blue tint directional light from the opposite direction

        // Configure and render the camera
        Camera.Builder cameraBuilder = Camera.getBuilder().setRayTracer(new SimpleRayTracer(scene))
                .setVpSize(800, 800);
                
                
         cameraBuilder.setLocation(new Point(1000, 1000, 1000))
                .setDirection(new Vector(-1, -1, -1), new Vector(-1, -1, 2))
                .setVpDistance(1000)
                .setImageWriter(new ImageWriter("SnowmanScene", 1000, 1000))
                .setDOF(10, 450, 20)
                .build()
                .renderImage().writeToImage();
    }
}
