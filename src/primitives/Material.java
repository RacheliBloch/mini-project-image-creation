/**
 * 
 */
package primitives;

/**
 * @author Racheli & Efrat
 *
 */
public class Material 
{
	double _kD;
	double _kS;
	int _nShininess;
    private final double _kr;
    private final double _kt;
	
	//constructor
	public Material(double kD,double kS,int nShininess,double kt,double kr)
	{
		this._kD=kD;
		this._kS=kS;
		this._nShininess=nShininess;
		this._kt=kt;
		this._kr=kr;
	}
	
	    public Material(double kD, double kS, int nShininess) 
	    {
	        this(kD, kS, nShininess, 0, 0);
	    }

	    public Material(Material material)//copy constructor
	    {
	        this(material._kD, material._kS, material._nShininess, material._kt, material._kr);
	    }

	//getters
	public double get_kD()//@return the defuse
	{
		return this._kD;
	}
	public double get_kS()//@return the specular 
	{
		return this._kS;
	}	
	public int get_nShininess()//@return the amount of shininess
	{
		return this._nShininess;
	}

    public double getKr()//@return the refrection 
    {
        return _kr;
    }

    public double getKt()//@return the transparent
    {
        return _kt;
    }
	

}
