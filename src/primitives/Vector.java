package primitives;

public class Vector
{	
	protected Point3D _head;
	
	//get function
	public Point3D getHead()
	{
		return _head;
	}
	
	//constructor
	public Vector(Vector v)
	{
		_head=new Point3D(v._head);
	}
	public Vector(Point3D p)throws IllegalArgumentException
	{
		if(p.equals(p.ZERO))
			throw new IllegalArgumentException("the zero vector");
		_head=new Point3D(p);
	}
	public Vector(Coordinate _x1,Coordinate _y1,Coordinate _z1)throws IllegalArgumentException
	{
		Point3D temp=new Point3D(_x1,_y1,_z1);
		if(temp.equals(temp.ZERO))
			throw new IllegalArgumentException("the zero vector");
		_head=new Point3D(_x1,_y1,_z1);
	}
	public Vector(double _x1,double _y1,double _z1)throws IllegalArgumentException
	{
		Point3D temp=new Point3D(_x1,_y1,_z1);
		if(temp.equals(temp.ZERO))
			throw new IllegalArgumentException("the zero vector");
		_head=new Point3D(_x1,_y1,_z1);
	}
	
	//override functions
	   @Override
	   public boolean equals(Object obj) {
	      if (this == obj) return true;
	      if (obj == null) return false;
	      if (!(obj instanceof Vector)) return false;
	      Vector oth = (Vector)obj;
	      return _head.equals(oth._head);
	   }
	   @Override
	   public String toString()
	   {
		   return _head.toString();
	   }

	//actions

public Vector subtract(Vector v)
{
Vector nv=new Vector(this._head.subtract(v._head));
return nv;
}
public Vector add(Vector v)
{
Vector nv=new Vector(this._head.add(v));
return nv;
}
public Vector scale(double tmp)
{
	return new Vector(_head._x.get()*tmp,_head._y.get()*tmp,_head._z.get()*tmp);
}
public double dotProduct(Vector vector)
{
       return _head.get_x().get() * vector._head.get_x().get() +_head.get_y().get() * vector._head.get_y().get() + _head.get_z().get() * vector._head.get_z().get();
}
public Vector crossProduct(Vector v)
{
	return new Vector(this._head.get_y().get()*v._head.get_z().get()-this._head.get_z().get()*v._head.get_y().get(),
			this._head.get_z().get()*v._head.get_x().get()-this._head.get_x().get()*v._head.get_z().get(),
			this._head.get_x().get()*v._head.get_y().get()-this._head.get_y().get()*v._head.get_x().get());
	 
}
public double lengthSquared()
{
return _head.distanceSquared(_head.ZERO);
}
public double length()
{
return Math.sqrt(this.lengthSquared());
}
public Vector normalize()
{
//this._head = scale(1/this.length())._head;
	double len = length();
	_head = new Point3D(_head._x._coord/len,_head._y._coord/len,_head._z._coord/len);
	
return this;
}
public Vector normalized()
{
return (new Vector(this).normalize());
}




}
