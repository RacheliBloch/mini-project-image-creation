/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * @author Racheli & Efrat
 *
 */
public interface LightSource
{
    /**
     * Get light source intensity as it reaches a point I<sub>P</sub>
     *
     * @param p the lighted point
     * @return intensity I<sub>P</sub>
     */
    Color getIntensity(Point3D p);

    /**
     * Get normalized vector in the direction from light source
     * towards the lighted point
     *
     * @param p the lighted point
     * @return light to point vector
     */
    Vector getL(Point3D p);
    
    double getDistance(Point3D point);
    
    /**
	 * @return radius of the light source (for soft shadow)
	 */
	double getRadius();

}
