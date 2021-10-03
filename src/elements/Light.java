/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * @author Owner
 *
 */
abstract class Light
{
	protected Color _intensity;

	//constructor
	public Light(Color color)
	{
		this._intensity=color;
	}
	//@return the field intensity
	public Color getIntensity()
	{
		return this._intensity;
	}
	
}
