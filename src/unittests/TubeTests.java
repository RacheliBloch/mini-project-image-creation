/**
 * 
 */
package unittests;
import geometries.*;
import primitives.*;

import static org.junit.Assert.*;

import org.junit.Test;

/**
  * @author Efrat&Racheli
 *Unit tests for primitives.Tube class
 */
public class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		 // ============ Equivalence Partitions Tests ==============
		Tube t = new Tube(1,new Ray(new Point3D(1.0,0.0,0.0), new Vector(0,1,0)));
       assertEquals("Wrong normal to Cylinder", new Vector(1.0, 0.0, 0.0), t.getNormal(new Point3D(2, 0, 0)));
	}

	/**
     * Test method for intersection Points with ray and tube
     */
	public void testFindIntersectionPoints()
	{
		
	}
}
