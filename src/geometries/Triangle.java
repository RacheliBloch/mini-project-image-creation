package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.*;

public class Triangle extends Polygon
{
	Point3D x,y,z;
	//constructors
	public Triangle(Point3D a,Point3D b,Point3D c)
	{
		this(Color.BLACK,a,b,c);
	}
	public Triangle(Color emm,Point3D a,Point3D b,Point3D c)
	{
		this(new Material(0,0,0),emm,a,b,c);
	}
	public Triangle(Material material,Color emm,Point3D a,Point3D b,Point3D c)
	{
		super(material,emm,a,b,c);
	}
	//@return point x
	 public Point3D get_x()
	 {
	     return x;
	 }
	 //@return point y
	  public Point3D get_y() 
	  {
	      return y;
	  }
	  //@return point z
	    public Point3D get_z()
	    {
	        return z;
	    }
	    
	    @Override
	    public String toString() 
		{
		        return "point1:"+x.toString()+"point2:"+y.toString()+"point3:"+z.toString();
		} 
	    
	    @Override
	    public Vector getNormal(Point3D _point) {
	        return super.getNormal(_point);
	    }

	    @Override
	    public List<GeoPoint> findIntersections(Ray ray) 
	    {
	        List<GeoPoint> planeIntersections = _plane.findIntersections(ray);
	        if (planeIntersections == null) return null;

	        Point3D p0 = ray.getP0();
	        Vector v = ray.getDir();

	        Vector v1 = _vertices.get(0).subtract(p0);
	        Vector v2 = _vertices.get(1).subtract(p0);
	        Vector v3 = _vertices.get(2).subtract(p0);

	        double s1 = v.dotProduct(v1.crossProduct(v2));
	        if (primitives.Util.isZero(s1)) return null;
	        double s2 = v.dotProduct(v2.crossProduct(v3));
	        if (primitives.Util.isZero(s2)) return null;
	        double s3 = v.dotProduct(v3.crossProduct(v1));
	        if (primitives.Util.isZero(s3)) return null;

	        if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0))
	        {
	            //for GeoPoint
	            List<GeoPoint> result = new LinkedList<>();
	            for (GeoPoint geo : planeIntersections) 
	            {
	                result.add(new GeoPoint(this, geo.getPoint()));
	            }
	            return result;
	        }
	        return null;
	    }

}
