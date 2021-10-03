package geometries;

import primitives.Color;
import primitives.Material;

public abstract class RadialGeometry extends Geometry
{
	double _radius;
	
	
	//constructors
	/**
	 *constructor 
	 * @param r _radius value
	 */
	public RadialGeometry (double r,Box box)
	{
		super(box);
		_radius=r;
	}
	/**
	 * constructor that calls Geometry constructor with emission value
	 * @param emmission _emission value
	 * @param r _radius value
	 */
	public RadialGeometry (Color emmission,double r,Box box)
	{
		super(emmission,box);
		_radius=r;
	}
	/**
	 * constructor that calls Geometry constructor with emission and material values
	 * @param emmission _emission value
	 * @param material _material value
	 * @param r _radius value
	 */
	public RadialGeometry (Color emmission,Material material,double r,Box box)
	{
		super(emmission,material,box);
		_radius=r;
	}
	/**
	 * copy constructor
	 * @param r _radius value
	 */
	public RadialGeometry (RadialGeometry  r)
	{
		super(r.get_box());
		_radius=r.getR();
	}
	
	/** 
     * @return _radius value
     */
	public double getR()
	{
		return this._radius;
	}

}
