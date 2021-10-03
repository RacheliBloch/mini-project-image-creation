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
public class DirectionalLight extends Light implements LightSource
{
	private Vector _direction;
	
	//constructor
	public DirectionalLight(Color color,Vector direction)
	{
		super(color);
		this._direction=direction.normalized();
	}
	
	//getters
	public Color getIntensity(Point3D p)
	{
		return super._intensity;
	}

	public Vector getL(Point3D p) {
		return this._direction;
	}

    @Override
    public double getDistance(Point3D point) 
    {
        return Double.POSITIVE_INFINITY;
    }

    /**
    * @return 0, direction light doesn't have radius
    */
 	@Override
 	public double getRadius() 
 	{
 		return 0;
 	}
}
