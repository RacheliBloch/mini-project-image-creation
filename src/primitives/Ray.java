package primitives;

public class Ray
{

    private static final double DELTA = 0.1;
	Point3D _P0;//the point where the ray start
	Vector _Dir;//the direction of the ray
	
//get functions	
	public Point3D getP0()
	{
		return _P0;
	}
	public Vector getDir()
	{
		return _Dir;
		
	}
//constructor	
    public Ray(Ray _ray)
    {
    	_P0=_ray._P0;
    	_Dir=_ray._Dir;	
    }
    public Ray(Point3D point, Vector direction, Vector normal) 
    {
        //head+ normal.scale(±DELTA)
        this._Dir = new Vector(direction).normalized();

        double nv = normal.dotProduct(direction);

        Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
        this._P0 = point.add(normalDelta);

    }
    public Ray(Point3D _p0,Vector _dir)//throws IllegalArgumentException
    {
    	if(_dir.normalized()!=_dir)
    		//throw new IllegalArgumentException("the vector is not normelized");
    		_dir=_dir.normalize();
    	_P0=new Point3D(_p0);
    	_Dir=new Vector(_dir);	
    }
    //@return new point3D
    public Point3D getPoint(double t)
    {
    	 Vector targetVector;
         try 
         {
             targetVector = this._Dir.scale(t);
         }
         catch (Exception e) 
         {
             return this._P0;
         }
         return primitives.Util.isZero(t) ? this._P0 : this._P0.add(targetVector);
    }
    
    //OVERRIDE FUNCTIONS
    @Override
    public boolean equals(Object obj) {
       if (this == obj) return true;
       if (obj == null) return false;
       if (!(obj instanceof Ray)) return false;
       Ray oth = (Ray)obj;
       return _P0.equals(oth._P0) && _Dir.equals(oth._Dir);
    }
    @Override
    public String toString()
    {
    	return "P0: "+_P0.toString()+", Direction: "+_Dir.toString();
    }

}