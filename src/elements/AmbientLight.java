/**
 * 
 */
package elements;

import primitives.Color;

/**
 * @author Racheli&Efrat
 *
 */
public class AmbientLight extends Light
{
	private Color Ia;
	private double Ka;
	
	//constructor which update the intensity by 'Ia' multiplied by 'Ka'
	public AmbientLight(Color ia,double ka)
	{
		super(ia.scale(ka));
		this.Ia=ia;
		this.Ka=ka;
	}
	public Color GetIntensity()
	{
		return this._intensity;
	}

}
