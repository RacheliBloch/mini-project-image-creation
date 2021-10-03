/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import org.junit.Test;
import primitives.*;
import primitives.Vector;
import geometries.*;
import primitives.Util.*;


/**
  * @author Efrat&Racheli
 *Unit tests for primitives.Vector class
 */
public class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract() {
		 Vector v1 = new Vector(1.0, 1.0, 1.0);
	     Vector v2 = new Vector(-1.0, -1.0, -1.5);
	     
	     Vector v3 = v1.subtract(v2);
	     assertEquals("the substract is wrong",new Vector(2.0,2.0,2.5),v3);

	      v2 = v2.subtract(v1);
          assertEquals("the substract is wrong",new Vector(-2.0, -2.0, -2.5),v2);
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testadd() {

        Vector v1 = new Vector(1.0, 1.0, 1.0);
        Vector v2 = new Vector(-1.0, -1.0, -1.5);

        v1 = v1.add(v2);

        assertEquals("the addition is wrong",new Vector(0.0,0.0,-0.5),v1);

        v2 = v2.add(v1);

        assertEquals("the addition is wrong",new Vector(-1.0, -1.0, -2.0),v2);
    }
	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() 
	{
		Vector a=new Vector(0.0,1.0,-2.5);
		a=a.scale(3.0);
		assertEquals("the scale is wrong",new Vector(0.0, 3.0,-7.5),a);
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct()
	{
		Vector a=new Vector(0.0,2.5,-6.0);
		Vector b=new Vector(4.0,3.5,4.5);
		assertTrue("dotProduct returns wrong value",(Double.compare(a.dotProduct(b),-18.25)==0));

        Vector c=new Vector(1,0,1);
        Vector d=new Vector(-1,0,1);
        assertTrue("for orthogonal vectors the result is not zero",(Double.compare(c.dotProduct(d),0))==0);
    }

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() 
	{
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", primitives.Util.isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", primitives.Util.isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

	}
	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared()
	{
		 // ============ Equivalence Partitions Tests ==============
		Vector a=new Vector(1,1,1);
		Vector b=new Vector(-1,-1,-1);
		Vector c=new Vector(0.5,2.5,3);
		assertTrue("the LengthSquared is wrong",(Double.compare(a.lengthSquared(),3)==0));
		assertTrue("the LengthSquared is wrong",(Double.compare(b.lengthSquared(),3)==0));
		assertTrue("the LengthSquared is wrong",(Double.compare(c.lengthSquared(),15.5)==0));

	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	public void testLength()
	{
		 // ============ Equivalence Partitions Tests ==============
			Vector a=new Vector(1,1,1);
			Vector b=new Vector(-1,-1,-1);
			Vector c=new Vector(0.5,2.5,3);
			assertTrue("the LengthSquared is wrong",(Double.compare(a.length(),Math.sqrt(3))==0));
			assertTrue("the LengthSquared is wrong",(Double.compare(b.length(),Math.sqrt(3))==0));
			assertTrue("the LengthSquared is wrong",(Double.compare(c.length(),Math.sqrt(15.5))==0));
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	public void testNormalize() {
		 // ============ Equivalence Partitions Tests ==============
		Vector v=new Vector(3,0,0);
		v.normalize();
		assertEquals("normalize() result is not a unit vector",1,v.length(),1e-10);
		Vector v1=new Vector(-3,0,0);
		v1.normalize();
		assertEquals("normalize() result is not a unit vector",1,v1.length(),1e-10);
		
	}




	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
    public void testNormalized()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector v1=new Vector(2,0,0);
        Vector v2=v1.normalized();
        assertTrue("normalizated() function does not create a new vector",!v2.equals(v1));
        assertEquals("normalized() result is not a unit vector",1, v2.length(),1e-10);
        Vector v3=new Vector(-2,0,0);
        Vector v4=v3.normalized();
        assertTrue("normalizated() function does not create a new vector",!v4.equals(v3));
        assertEquals("normalized() result is not a unit vector",1,v4.length(),1e-10);
    
    }
}
