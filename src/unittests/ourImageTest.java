/**
 * 
 */
package unittests;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import elements.SpotLight;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * @author Racheli & Efrat
 *
 */
public class ourImageTest
{
	   /**
     * Sphere-Triangle shading - move spot even more close
     */
    @Test
	public void trianglesTransparentSphere() {
		Scene scene = new Scene("Test scene");
		scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.set_distance(1000);
		scene.set_background(Color.BLACK);
		scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Triangle(
						new Material(0.5,0.9, 30,0,1),
						Color.BLACK,
						new Point3D(-150, 150, 115),
						new Point3D(150, 150, 135),
						new Point3D(75, -75, 150)), 
				new Triangle( 
						new Material(0.5, 0.9, 30,0,1),
						Color.BLACK, 
						new Point3D(-150, 150, 115),
						new Point3D(-70, -70, 140), 
						new Point3D(75, -75, 150)), 
				new Sphere(
						new Material(0.2, 0.2, 30, 0.6, 0.5),
						new Color(java.awt.Color.BLUE), 
						30,
						new Point3D(60, -50, 50)),
			    new Sphere(
                		new Material(0.5, 0.5, 30,0.6,0.5), 
                        new Color(java.awt.Color.BLACK),            
                        20,
                        new Point3D(0, -50,-50)), 
                new Sphere(
                		new Material(0.5, 0.5, 30,0.6,0.5),
                        new Color(java.awt.Color.green),
                        30,
                        new Point3D(80, -10,150)),
                new Sphere(
                		new Material(0.25, 0.25, 20, 0.5,0.5),
                		new Color(java.awt.Color.RED),
                		50,
                		new Point3D(-50, -50, 50)),
                new Sphere(
                		new Material(0.25, 0.1, 20,0.45,0.5), 
                        new Color(java.awt.Color.BLACK),            
                        20,
                        new Point3D(-70, 10, 80)), 
				new Sphere(
						new Material(0.25, 0.1, 20,0.65,0.5),
						new Color(java.awt.Color.BLUE),
						33,
						new Point3D(-65,60, 60)));

		scene.addLights(new SpotLight(new Color(700, 400, 400),new Vector(0, 0, 1), 
				new Point3D(60, -50, 0),  1, 4E-5, 2E-7,10));

		ImageWriter imageWriter = new ImageWriter("ourImage", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

}
