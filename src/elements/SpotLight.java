/**
 * 
 */
package elements;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * @author Efrat & Racheli
 *
 */
public class SpotLight extends PointLight
{
	private Vector _direction;
	
	//constructor
	public SpotLight(Vector dir,Color color, Point3D position, double kc, double kl, double kq)
	{
		super(color, position, kc, kl, kq);
		this._direction=new Vector(dir).normalize();
	}	

	/**
	 * constructor that calls Light constructor with color value and vector
	 * @param color _intensity value
	 * @param v direction value
	 * @param _position _position value
	 * @param kc _kC value 
	 * @param kl _kL value
	 * @param kq _kQ value
	 * @param r light source radius
	 */
	public SpotLight(Color color,Vector dir,Point3D _position,double _kC, double _kL, double _kQ,double r) 
	{
		super(color, _position, _kC, _kL, _kQ,r);
		_direction=new Vector(dir).normalize();
	}
	 /**
     * @return spotlight intensity
     */
    @Override
    public Color getIntensity(Point3D p) 
    {
        double projection = _direction.dotProduct(getL(p));

        if (primitives.Util.isZero(projection)) 
        {
            return Color.BLACK;
        }
        double factor = Math.max(0, projection);
        Color pointlightIntensity = super.getIntensity(p);

        return (pointlightIntensity.scale(factor));
    }


}
