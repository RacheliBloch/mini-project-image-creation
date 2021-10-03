package primitives;
import primitives.Coordinate;

public class Point3D
{
	public static final Point3D ZERO=new Point3D(0.0,0.0,0.0);	
	Coordinate _x;
	Coordinate _y;
	Coordinate _z;

//get functions
public Coordinate get_x()
{
	return _x;
}
public Coordinate get_y()
{
	return _y;
}
public Coordinate get_z()
{
	return _z;
}

//constructor
public Point3D(Point3D _p)
{
	_x=_p._x;
	_y=_p._y;
	_z=_p._z;
}

public Point3D(Coordinate _x1,Coordinate _y1,Coordinate _z1)
{
	_x=_x1;
	_y=_y1;
	_z=_z1;
}
public Point3D(double _x1,double _y1,double _z1)
{
	_x=new Coordinate(_x1);
	_y=new Coordinate(_y1);
	_z=new Coordinate(_z1);
}


//override functions
@Override
public boolean equals(Object obj) {
   if (this == obj) return true;
   if (obj == null) return false;
   if (!(obj instanceof Point3D)) return false;
   Point3D oth = (Point3D)obj;
  return _x.equals(oth._x) && _y.equals(oth._y) && _z.equals(oth._z);
}
@Override
public String toString()
{
	return "("+_x+","+_y+","+_z+")";
}

//actions
public Vector subtract(Point3D p)
{
return new Vector
		(
			this.get_x().get() - p.get_x().get(),
			this.get_y().get() - p.get_y().get(),
			this.get_z().get() - p.get_z().get()
		);
}

public Point3D add(Vector v)
{
 return new Point3D
		 (
			this.get_x().get() + v._head.get_x().get(),
			this.get_y().get() + v._head.get_y().get(),
			this.get_z().get() + v._head.get_z().get()
		 );
}
public double distanceSquared(Point3D _p)
{
return ((this.get_x().get()-_p.get_x().get())*(this.get_x().get()-_p.get_x().get()))+
((this.get_y().get()-_p.get_y().get())*(this.get_y().get()-_p.get_y().get()))+
((this.get_z().get()-_p.get_z().get())*(this.get_z().get()-_p.get_z().get()));
}
public double distance(Point3D _p)
{
   return Math.sqrt(this.distanceSquared(_p));
}
}

