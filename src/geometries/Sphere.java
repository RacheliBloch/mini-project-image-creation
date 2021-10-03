package geometries;

import java.util.List;
import primitives.*;
	//import geometries.*;

	public class Sphere extends RadialGeometry
	{
		Point3D _center;
		//constructor:
		/**
		 * constructor with default emission and material values
		 * @param r _radius value
		 * @param _p _center value
		 */
		public Sphere(double r,Point3D _p)
		{
			this(new Material(0, 0, 0),Color.BLACK,r,_p);
		}
		/**
		 * constructor with default material value
		 * @param emm _emission value
		 * @param r _radius value
		 * @param _p _center value
		 */
		public Sphere(Color emm,double r,Point3D _p)
		{
			this(new Material(0,0,0),emm,r,_p);
		}
		/**
		 * constructor that calls  RadialGeometry constructor with emission, material and radius values
		 * @param emm _emisiion value
		 * @param material _material value
		 * @param r _radius value
		 * @param _p _center value
		 */
		public Sphere(Material material,Color emm,double r,Point3D _p)
		{
			super(emm,material,r,new Box(_p.get_x().get()-r,_p.get_x().get()+r,_p.get_y().get()-r,
	    			_p.get_y().get()+r,_p.get_z().get()-r,_p.get_z().get()+r));
	
			_center=new Point3D(_p);
	    }

		//@return the normal
		public Vector getNormal(Point3D point) 
		{
		        return new Vector(
		        		new Point3D(
		        				point.get_x().get()-_center.get_x().get(),
		        				point.get_y().get()-_center.get_y().get(),
		        				point.get_z().get()-_center.get_z().get())).normalize();
		}
		//@return the center point
		public Point3D getC()
		{
			return _center;
		}
		 @Override
		    public String toString() 
			{
			        return "center:"+_center.toString();
			}
		 @Override
		   public List<GeoPoint> findIntersections(Ray ray) 
		  {
				
				if(!IsIntersectionBox(ray))
		    	{
		    		return null;
		    	}
				  Point3D p0 = ray.getP0();
			        Vector v = ray.getDir();
			        
			        Vector u;
			        try {
			            u = _center.subtract(p0);   // p0 == _center
			        } catch (IllegalArgumentException e) {
			            return List.of(new GeoPoint(this,ray.getPoint(_radius)));
			        }
			        double tm = primitives.Util.alignZero(v.dotProduct(u));
			        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
			        double thSquared = primitives.Util.alignZero(_radius * _radius - dSquared);

			        if (thSquared <= 0) return null;

			        double th = primitives.Util.alignZero(Math.sqrt(thSquared));
			        if (th == 0) return null;

			        double t1 = primitives.Util.alignZero(tm - th);
			        double t2 = primitives.Util.alignZero(tm + th);
			        if (t1 <= 0 && t2 <= 0) return null;
			        if (t1 > 0 && t2 > 0) return List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this,ray.getPoint(t2))); //P1 , P2
			        if (t1 > 0)
			            return List.of(new GeoPoint(this,ray.getPoint(t1)));
			        else
			            return List.of(new GeoPoint(this, ray.getPoint(t2)));
		    }
			@Override
			public boolean IsIntersectionBox(Ray ray) {
				return this._box.IntersectionBox(ray);
			    }
			@Override
			public Point3D getPositionPoint() {
				return this._center;
			}
	}
