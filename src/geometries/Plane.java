package geometries;
import java.util.List;

import primitives.*;

public class Plane extends Geometry
{
	Point3D _p;
	Vector _normal;

	//constructors:
	/**
	 * Plane  constructor receiving 3 Point3D value
     * @param p1  point _p
	 * @param p2 vector normal
	 * @param p3 vector normal
	 */
	public Plane(Point3D p1,Point3D p2,Point3D p3)
	{
		super(new Box(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY));
		this._p=p1;//relation point of the plane
		Vector v1=p2.subtract(p1);
		Vector v2=p3.subtract(p2);
		this._normal=(v1.crossProduct(v2)).normalize();
	}
	/**
	 * Plane  constructor receiving Point3D value and Vector
	 * @param p is point p1
	 * @param normal is Vector normal
	 */
	public Plane(Point3D p,Vector normal)
	{
		super(new Box(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY));
		_p=new Point3D(_p);
		_normal=new Vector(normal);
	}
	/**
	 * constructor with default material
	 * @param emmission _emission value
	 * @param p1 point p1
	 * @param p2  vector normal
	 * @param p3 vector normal
	 */
	public Plane(Color emissionLight, Point3D p1, Point3D p2, Point3D p3)
	{
		this(new Material(0,0,0),emissionLight,p1,p2,p3);
	}
	public Plane(Material material,Color emmission,Point3D p1,Vector v )
	{
		super(emmission, material,new Box(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY));
		this._p=p1;//relation point of the plane
		_normal=v;
	}
	/**
	 * constructor that calls Geometry constructor with emission and material values 
	 * @param emmission is _emission value
	 * @param material is _material value
	 * @param p1 is point _p
	 * @param p2 is vector normal
	 * @param p3 is vector normal
	 */
    public Plane(Material material,Color emissionLight, Point3D p1, Point3D p2, Point3D p3)
    {
    	super(emissionLight,material,new Box(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY));
		this._p=p1;//relation point of the plane
		Vector v1=p2.subtract(p1);
		Vector v2=p3.subtract(p2);
		_normal=(v1.crossProduct(v2)).normalize();
    }

	//getters
	public Point3D getP()
	{
		return _p;
	}
	public Vector getNormal(Point3D p) 
	{
		return this._normal;
	}
	public Vector getNormal() 
	{
		return this._normal;
	}


	@Override
	public String toString()
	{
		return "point: "+_p.toString()+", normal: "+_normal.toString();
	}
	
	@Override
	/**
	 * @param ray
     * @return list of Point3D that intersect the given ray with the plane
	 */
	public List<GeoPoint> findIntersections (Ray ray)
	{
		if(!IsIntersectionBox(ray))
		{
			return null;
		}
		  Vector p0Q;
	        try {
	            p0Q = _p.subtract(ray.getP0());
	        } catch (IllegalArgumentException e) {
	            return null; // ray starts from point Q - no intersections
	        }

	        double nv = _normal.dotProduct(ray.getDir());
	        if (primitives.Util.isZero(nv)) // ray is parallel to the plane - no intersections
	            return null;

	        double t = primitives.Util.alignZero(_normal.dotProduct(p0Q) / nv);

	        return t <= 0 ? null : List.of(new GeoPoint(this,ray.getPoint(t)));
	 }
	@Override
	public boolean IsIntersectionBox(Ray ray) {

		return this._box.IntersectionBox(ray);
	}
	@Override
	public Point3D getPositionPoint() {
		return this._p;
	}

}
