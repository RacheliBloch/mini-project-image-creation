/**
 * 
 */
package elements;

import primitives.*;
import geometries.*;

/**
 * @author Racheli&Efrat
 *
 */
public class Camera
{
	//Location of viewpoint
	private Point3D _p0;
	//3 vectors for Camera orientation
	private Vector _vUp;
	private Vector _vTo;
	private Vector _vRight;

	
	/*
	 * constructor
	 * @throws IllegalArgumentException that may be thrown by the method if the vectors are non-orthogonal vectors
	 */
	public Camera(Point3D p,Vector to, Vector up)
	{
		_p0=new Point3D(p);
		_vUp=up.normalize();
		_vTo=to.normalize();
		if(!primitives.Util.isZero(up.dotProduct(to)))
			throw new IllegalArgumentException("non-orthogonal vectors");
		_vRight=to.crossProduct(up).normalize();
	}
	
	//getters
	public Point3D get_p0()//@return the p0 point
	{
		return new Point3D(this._p0);
	}
	Vector get_vUp()//@return the up vector
	{
		return new Vector(this._vUp);
	}
	Vector get_vTo()//@return the vector up
	{
		return new Vector(this._vTo);
	}
	Vector get_vRight()//@return the vector right
	{
		return new Vector(this.get_vRight());
	}
	

    public Ray constructRayThroughPixel(int nX, int nY,

                                        int j, int i, double screenDistance,

                                        double screenWidth, double screenHeight)
    {
        if (primitives.Util.isZero(screenDistance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        Point3D Pc = _p0.add(_vTo.scale(screenDistance));

        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;

        double yi =  ((i - nY/2d)*Ry + Ry/2d);
        double xj=   ((j - nX/2d)*Rx + Rx/2d);

        Point3D Pij = Pc;

        if (! primitives.Util.isZero(xj))
        {
            Pij = Pij.add(_vRight.scale(xj));
        }

        if (! primitives.Util.isZero(yi))
        {
        	Vector temp=new Vector(Pij);
            Pij =temp.subtract(_vUp.scale(yi)).getHead(); // Pij.add(_vUp.scale(-yi))
        }

        Vector Vij = Pij.subtract(_p0);
        
        return new Ray(_p0,Vij);
    }

}
