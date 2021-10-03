package geometries;


import java.util.List;
import primitives.*;


public class Tube extends RadialGeometry
{
	Ray axisRay;
	/**
	* constructor with default _emmission value
	* @param r _radius value
	* @param ray axisRay value
	*/
	public Tube(Color emm,double r,Ray ray)
	{
	this(new Material(0,0,0),emm,r,ray);
	}
	public Tube(double r,Ray ray)
	{
	this(Color.BLACK,r,ray);
	}
	public Tube(Material material,Color emm,double r,Ray ray)
	{
	super(emm,r,null);
	//super(r);
	this._material=material;
	//this._emmission=emm;
	axisRay=new Ray(ray);
	}
	
	public Ray getRay()
	{
	return axisRay;
	}
	
	
	public Vector getNormal(Point3D _point)
	{
	  Point3D o=axisRay.getP0();
	  Vector v=axisRay.getDir();
	  double t=_point.subtract(o).dotProduct(v);
	  if(!primitives.Util.isZero(t))
	  {
	  o=o.add(v.scale(t));
	  }
	  return _point.subtract(o).normalize();
	 
	}
	
	@Override
	public String toString()
	    {
	return "ray:"+axisRay.toString();
	}
	
	@Override
	public boolean IsIntersectionBox(Ray ray) {
	
	return true;
	}
	@Override
	public Point3D getPositionPoint() {
	return this.getRay().getP0();
	}
	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		return null;
	}


}

