package geometries;
import primitives.*;

public abstract class Geometry implements Intersectable
{
	protected Color _emmission;
	protected Material _material;
	protected Box _box;
	abstract public Point3D getPositionPoint();
	
	//@return the material
	public Material get_material()
	{
		return this._material;
	}
	//@return the _emmission
	public Color getEmmission()
	{
		return this._emmission;
	}
   //@return the _box
   public Box get_box()
   {
	   return _box;
   }

  /**
    * Geometry constructor with default _material value
    * @param color _emmission value
    */
   public Geometry(Color color,Box box)
   {
	   this(color,new Material(0, 0, 0),box);
	  
   }
   /**
    * default constructor
    */
   public Geometry(Box box) 
   {
	   this(Color.BLACK,new Material(0, 0, 0),box);
	   
   }
   
   /**
    * constructor
    * @param color _emission value
    * @param material _material value
    * @param box _box value
    */
   public Geometry(Color color, Material material,Box box)
   {
	   _box=box;
	   _emmission=color;
	   _material=material;
   }
   /**
    * 
    * @param box is _box
    */
      public void set_box(Box box)
      {
   	   _box=box;
      }
   /**
	 * function that calculate the normal vector to the object in the given point p
	 * @param p point3D value
	 * @return normal vector 
	 */
	public abstract Vector getNormal(Point3D p);

}
