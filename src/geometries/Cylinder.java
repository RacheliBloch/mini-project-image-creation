package geometries;
import java.util.List;

import primitives.*;


public class Cylinder extends Tube
{
	double _height;

	//constructors
	public Cylinder(Color emm,double r, Ray ray,double height)
	{
		this(new Material(0,0,0),emm,r,ray,height);
	}
	public Cylinder(double r, Ray ray,double height)
	{
		this(Color.BLACK,r,ray,height);
	}
	public Cylinder(Material material,Color emm,double r, Ray ray,double height)
	{		
		super(emm,r, ray);
		this._material=material;
		_height=height;
	}
		@Override
		public Vector getNormal(Point3D p){return super.getNormal(p);}

	
	public double getH()
	{
		return _height;
	}
	@Override
	public String toString() 
	{
		 return "height:"+_height;
	} 
	    
	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	} 
	
	

}
