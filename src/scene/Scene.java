/**
 * 
 */
package scene;

import java.util.LinkedList;
import java.util.List;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

/**
 * @author Racheli & Efrat
 *
 */
public class Scene
{
	 public static class SceneBuilder
	 {
	        private String name;
	        private Color background;
	        private Camera camera;
	        private double distance;
	        private AmbientLight ambientLight;

	        public SceneBuilder(String name) 
	        {
	            this.name = name;
	        }

	        public SceneBuilder addBackground(Color background) 
	        {
	            this.background = background;
	            return this;
	        }

	        public SceneBuilder addCamera(Camera camera) 
	        {
	            this.camera = camera;
	            return this;
	        }

	        public SceneBuilder addDistance(double distance)
	        {
	            this.distance = distance;
	            return this;
	        }

	        public SceneBuilder addAmbientLight(AmbientLight ambientLight) 
	        {
	            this.ambientLight = ambientLight;
	            return this;
	        }

	        public Scene build() 
	        {
	            Scene scene = new Scene(this.name);
	            scene._camera = this.camera;
	            scene._distance = this.distance;
	            scene._background = this.background;
	            scene._ambientLight = this.ambientLight;
	            validateUserObject(scene);
	            return scene;
	        }

	        private void validateUserObject(Scene scene) 
	        {
	            //Do some basic validations to check
	            //if user object does not break any assumption of system
	        }
	    }
	private String _name;
	private Color _background;
	private AmbientLight _ambientLight;
	private Geometries _geometries;
	private Camera _camera;
	private double _distance;
	private List<LightSource> _lights;

	public Scene(String name)
	{
		this._lights=new LinkedList<LightSource>();
		this._name=name;
		this._geometries=new Geometries();
	}
	//getters
	public List<LightSource> get_lights()//@return the list of lights
	{
		return this._lights;
	}
	public String get_name()//@return the name
	{
		return this._name;
	}
	public Color get_background()//@return the background color
	{
		return this._background;
	}
	public AmbientLight get_ambientLight()//@return the ambient light of the scene
	{
		return this._ambientLight;
	}
	public Geometries get_geometries()//@return the geometries that in scene
	{
		return this._geometries;
	}
	public Camera get_camera()//@return the camera
	{
		return this._camera;
	}
	public double get_distance()//@return the distance from the camera
	{
		return this._distance;
	}
	//setters
	public void set_background(Color background)
	{
		this._background=background;
	}
	public void set_ambientLight(AmbientLight ambientLight)
	{
		this._ambientLight=ambientLight;
	}
	public void set_camera(Camera camera)
	{
		this._camera=camera;
	}
	public void set_distance(double distance)
	{
		this._distance=distance;
	}
	//add geometry
	public void addGeometries(Intersectable... geometries)
	{
		//this._geometries.add(geometries);
		for(Intersectable geo :geometries)
		{
			_geometries.add(geo);
		}
	}
	//add lights in the scene
	public void addLights(LightSource... lights)
	{
		//this._lights.addAll(_lights);
		if(_lights==null)
			_lights=new LinkedList<LightSource>();
		
		for(LightSource light :lights)
		{
			_lights.add(light);
		}
	}
}

