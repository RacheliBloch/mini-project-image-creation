package renderer;
import geometries.Intersectable.GeoPoint;
import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
//import static primitives.Util.alignZero;
/**
 * rendering the image
 * 
 * @author Racheli & Efrat
 */
public class Render 
{
    private Scene _scene;
    private ImageWriter _imageWriter;
  //  private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

	/**
	 * number of rays to the beam from point(soft shadow)
	 */
	 private static final int NUM_OF_RAYES=80;
	 private double _supersamplingDensity = 1;
	 private double SOFT_SHADOW=1;
	 
    public Render(Scene _scene) 
    {
        this._scene = _scene;
    }

    public Render(ImageWriter imageWriter, Scene scene) 
    {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }

    public Scene get_scene() 
    {
        return _scene;
    }

	private int _threads = 1;
	private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
	private boolean _print = false; // printing progress percentage
	/**
	* Set multithreading <br>
	* - if the parameter is 0 - number of coress less SPARE (2) is taken
	* @param threads number of threads
	* @return the Render object itself
	*/
	public Render setMultithreading(int threads)
	{
	if (threads < 0) throw new IllegalArgumentException("Multithreading must be 0 or higher");
	if (threads != 0) _threads = threads;
	else 
	{
	int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
	_threads = cores <= 2 ? 1 : cores;
	}
	return this;
	}

	/**
	* Set debug printing on
	* @return the Render object itself
	*/
	public Render setDebugPrint() { _print = true; return this; }
	
	/**
	* Pixel is an internal helper class whose objects are associated with a Render object that
	* they are generated in scope of. It is used for multithreading in the Renderer and for follow up
	* its progress.<br/>
	* There is a main follow up object and several secondary objects - one in each thread.
	*/
	private class Pixel 
	{
	private long _maxRows = 0; // Ny
	private long _maxCols = 0; // Nx
	private long _pixels = 0; // Total number of pixels: Nx*Ny
	public volatile int row = 0; // Last processed row
	public volatile int col = -1; // Last processed column
	private long _counter = 0; // Total number of pixels processed
	private int _percents = 0; // Percent of pixels processed
	private long _nextCounter = 0; // Next amount of processed pixels for percent progress
	/**
	* The constructor for initializing the main follow up Pixel object
	* @param maxRows the amount of pixel rows
	* @param maxCols the amount of pixel columns
	*/
	public Pixel(int maxRows, int maxCols) 
	{
	_maxRows = maxRows;_maxCols = maxCols; _pixels = maxRows * maxCols;
	_nextCounter = _pixels / 100;
	if (Render.this._print) System.out.printf("\r %02d%%", _percents);
	}
	/**
	* Default constructor for secondary Pixel objects
	*/
	public Pixel() {}
	
	/**
	* Public function for getting next pixel number into secondary Pixel object.
	* The function prints also progress percentage in the console window.
	* @param target target secondary Pixel object to copy the row/column of the next pixel
	* @return true if the work still in progress, -1 if it's done
	*/
	public boolean nextPixel(Pixel target)
	{
	int percents = nextP(target);
	if (_print && percents > 0) System.out.printf("\r %02d%%", percents);
	if (percents >= 0) return true;
	if (_print) System.out.printf("\r %02d%%", 100);
	return false;
	}
	
	/**
	* Internal function for thread-safe manipulating of main follow up Pixel object - this function is
	* critical section for all the threads, and main Pixel object data is the shared data of this critical
	* section.<br/>
	* The function provides next pixel number each call.
	* @param target target secondary Pixel object to copy the row/column of the next pixel
	* @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
	* finished, any other value - the progress percentage (only when it changes)
	*/
	private synchronized int nextP(Pixel target) 
	{
	++col; ++_counter;
	if (col < _maxCols)
	{
	target.row = this.row; target.col = this.col;
	if (_print && _counter == _nextCounter) 
	{
	++_percents;_nextCounter = _pixels * (_percents + 1) / 100; return _percents;
	}
	return 0;
	}
	++row;
	if (row < _maxRows) 
	{
	col = 0;
	if (_print && _counter == _nextCounter) 
	{
	++_percents; _nextCounter = _pixels * (_percents + 1) / 100; return _percents;
	}
	return 0;
	}
	return -1;
	}
	}
    /**
     * Filling the buffer according to the geometries that are in the scene.
     * This function does not creating the picture file, but create the buffer pf pixels
     */
	public void renderImage()
	{
		
		 Camera camera = _scene.get_camera();
	     java.awt.Color background = _scene.get_background().getColor();
	     double distance = _scene.get_distance();
	     
	     int Nx = _imageWriter.getNx();
	     int Ny = _imageWriter.getNy();
	     double width = _imageWriter.getWidth();
	     double height = _imageWriter.getHeight();
	     final Pixel thePixel = new Pixel(Ny, Nx); // Main pixel management object
	     // Generate threads
			Thread[] threads = new Thread[_threads];
			for (int i = _threads - 1; i >= 0; --i) {
				threads[i] = new Thread(() -> {
					Pixel pixel = new Pixel();
					while (thePixel.nextPixel(pixel)) {
					

						Ray ray = camera.constructRayThroughPixel(Nx, Ny, pixel.col, pixel.row, distance, width, height);
		                GeoPoint closestPoint=findClosestIntersection(ray);
		              
		                if (closestPoint == null) {
		                	
		                	_imageWriter.writePixel(pixel.col, pixel.row, background);
		                } else {
		                	
		                	_imageWriter.writePixel(pixel.col, pixel.row, calcColor(closestPoint,ray).getColor());
		                }
						
					}
				});
			}

			// Start threads
			for (Thread thread : threads) thread.start();

			// Wait for all threads to finish
			for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
			if (_print) System.out.printf("\r100%%\n");
	}
    /**
     * Finding the closest point to the P0 of the camera.
     *
     * @param intersectionPoints list of points, the function should find from
     *                           this list the closet point to P0 of the camera in the scene.
     * @return the closest point to the camera
     */

    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) 
    {
        GeoPoint result = null;
        double mindist = Double.MAX_VALUE;

        Point3D p0 = this._scene.get_camera().get_p0();

        for (GeoPoint geo : intersectionPoints)
        {
            Point3D pt = geo.getPoint();
            double distance = p0.distance(pt);
            if (distance < mindist)
            {
                mindist = distance;
                result = geo;
            }
        }
        return result;
    }

    /**
     * Printing the grid with a fixed interval between lines
     *
     * @param interval The interval between the lines.
     */
    public void printGrid(int interval, java.awt.Color colorsep)
    {
        double rows = this._imageWriter.getNy();
        double collumns = _imageWriter.getNx();
        //Writing the lines.
        for (int row = 0; row < rows; ++row) 
        {
            for (int collumn = 0; collumn < collumns; ++collumn) 
            {
                if (collumn % interval == 0 || row % interval == 0) 
                {
                    _imageWriter.writePixel(collumn, row, colorsep);
                }
            }
        }
    }


    public void writeToImage() 
    {
        _imageWriter.writeToImage();
    }

    /**
     * Find intersections of a ray with the scene geometries and get the
     * intersection point that is closest to the ray head. If there are no
     * intersections, null will be returned.
     *
     * @param ray intersecting the scene
     * @return the closest point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        if (ray == null) {
            return null;
        }
        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.getP0();

        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.getPoint());
            if (distance < closestDistance) {
                closestPoint = geoPoint;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }

    private Color calcColor(GeoPoint geoPoint, Ray inRay) {
        Color color = calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);
        color = color.add(_scene.get_ambientLight().getIntensity());
        return color;
    }
    private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) 
    {
        if (level == 1 || k < MIN_CALC_COLOR_K) 
        {
            return Color.BLACK;
        }

        Color result = geoPoint.getGeometry().getEmmission();
        Point3D pointGeo = geoPoint.getPoint();

        Vector v = pointGeo.subtract(_scene.get_camera().get_p0()).normalize();
        Vector n = geoPoint.getGeometry().getNormal(pointGeo);

        Material material = geoPoint.getGeometry().get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        double kr = geoPoint.getGeometry().get_material().getKr();
        double kt = geoPoint.getGeometry().get_material().getKt();
        double kkr = k * kr;
        double kkt = k * kt;

        result = getLightSourcesColors(geoPoint, k, result, v, n, nShininess, kd, ks);

        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(pointGeo, inRay, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null) {
                result = result.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }
        }
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(pointGeo, inRay, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null) {
                result = result.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
        }
        return result;
    }

    private Color getLightSourcesColors(GeoPoint geoPoint, double k, Color result, Vector v, Vector n, int nShininess, double kd, double ks) {
        Point3D pointGeo = geoPoint.getPoint();
        List<LightSource> lightSources = _scene.get_lights();
        if (lightSources != null) {
            for (LightSource lightSource : lightSources) 
            {
                Vector l = lightSource.getL(pointGeo);
                double nl = primitives.Util.alignZero(n.dotProduct(l));
                double nv =  primitives.Util.alignZero(n.dotProduct(v));
                if (nl * nv > 0) {
//                if (sign(nl) == sign(nv) && nl != 0 && nv != 0) {
                    double ktr = transparency(lightSource, l, n, geoPoint);
                    if (ktr * k > MIN_CALC_COLOR_K) {
//                    if (unshaded(lightSource, l, n, geoPoint)) {
                        Color ip = lightSource.getIntensity(pointGeo).scale(ktr);
                        result = result.add(
                                calcDiffusive(kd, nl, ip),
                                calcSpecular(ks, l, n, nl, v, nShininess, ip));
                    }
                }
            }
        }
        return result;
    }
   
    /**
	 * @param light light source
	 * @param l vector between  light source and a given point
	 * @param n normal to raise the point in £ to fix the floating point problem
	 * @param geopoint point value on the geometry which the vector cuts
	 * @return value of transparency partial shading in case the object/s that block the light source from the point have transparency at some level or another. 
	 * implementaion of soft shadow: creat a beam of ray from every dark point. calculat the ktr of every ray and return the average of the ktr from all rayes in the beam.
	 * the larger the number of the rayes in beam, the more blurry the shadow's edge.
	 * 
	 */

	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) 
	{
		if(SOFT_SHADOW==0)//doesn't use the soft shadow improvement
		{
			Vector lightDirection = l.scale(-1); // from point to light source
	        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);
	        Point3D pointGeo = geopoint.getPoint();

	        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(lightRay);
	        if (intersections == null) 
	        {
	            return 1d;
	        }
	        double lightDistance = light.getDistance(pointGeo);
	        double ktr = 1d;
	        for (GeoPoint gp : intersections) 
	        {
	            if (primitives.Util.alignZero(gp.getPoint().distance(pointGeo) - lightDistance) <= 0) 
	            {
	                ktr *= gp.getGeometry().get_material().getKt();
	                if (ktr < MIN_CALC_COLOR_K) 
	                {
	                    return 0.0;
	                }
	            }
	        }
	        return ktr;
		}
		else 
		{
			double sum_ktr = 0;
        List<Ray> rays = constructRayBeamThroughPoint(light, l, n, geopoint);
        for (Ray ray : rays) 
        {
            List<GeoPoint> intersections = _scene.get_geometries().findIntersections(ray);
            if (intersections != null) 
            {
                double lightDistance = light.getDistance(geopoint.getPoint());
                double ktr = 1;
                for (GeoPoint geo : intersections) 
                {
                    if (primitives.Util.alignZero(geo.getPoint().distance(geopoint.getPoint()) - lightDistance) <= 0) //check if the geometry is before the light source or behind
                    {
                        ktr *= geo.getGeometry().get_material().getKt();
                        if (ktr < MIN_CALC_COLOR_K) 
                        {
                            ktr = 0;
                            break;
                        }
                    }
                }
                sum_ktr += ktr;
            } 
            else
                sum_ktr += 1;
          }
        return sum_ktr/rays.size();
		}
    }

    private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n) 
    {
        return new Ray(pointGeo, inRay.getDir(), n);
    }

    private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n)
    {
        //r=v-2*(v*n)*n
        Vector v = inRay.getDir();
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
    }

    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component coef
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param nl         dot-product n*l
     * @param V          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point
     * @author Racheli & Efrat
     */
	

	private Color calcDiffusive(double kd, double nl,Color ip) {
		if (nl < 0) nl = -nl;
        return ip.scale(nl * kd);
	}
	
	private Color calcSpecular(double ks,Vector l,Vector n, double nl, Vector v, int nShininess, Color lightIntensity) {
		
		double p = nShininess;
        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -primitives.Util.alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        return lightIntensity.scale(ks * Math.pow(minusVR, p));
	}
	/**
	 * 
	 * @param light the light source 
	 * @param l vector between  light source and a given point
	 * @param n normal to raise the point in £ to fix the floating point problem
	 * @param geopoint point value on the geometry which the vector cuts
	 * @return beam of rays from point ,in transparency find the intersection points with all of the rays in the beam and return the ktr for every ray.
	 * in the end transparency returns the average of ktr from all the rays
	 */

    private List<Ray> constructRayBeamThroughPoint(LightSource light, Vector l, Vector n, GeoPoint geopoint){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);
        List<Ray> beam = new ArrayList<>();
        beam.add(lightRay);
        double r = light.getRadius();
        if(r==0)return beam;//in case the light is direction light so it doesn't have radius
        Point3D p0 = lightRay.getP0();
        Vector v = lightRay.getDir();
		Vector vx = (new Vector(-v.getHead().get_y().get(), v.getHead().get_x().get(),0)).normalized(); 
		Vector vy = (v.crossProduct(vx)).normalized();
		
        Point3D pC = lightRay.getPoint(light.getDistance(p0));
        for (int i=0; i<NUM_OF_RAYES-1; i++)//number of rays less the direct ray to the light(lightRay)
        {
            // create random polar system coordinates of a point in circle of radius r
            double cosTeta = ThreadLocalRandom.current().nextDouble(-1, 1);
            double sinTeta = Math.sqrt(1 - cosTeta*cosTeta);
            double d = ThreadLocalRandom.current().nextDouble(-r, r);
            // Convert polar coordinates to Cartesian ones
            double x = d*cosTeta;
            double y = d*sinTeta;
            // pC - center of the circle
            // p0 - start of central ray, v - its direction, distance - from p0 to pC
            Point3D point = pC;
            if (!primitives.Util.isZero(x)) point = point.add(vx.scale(x));
            if (!primitives.Util.isZero(y)) point = point.add(vy.scale(y));
            beam.add(new Ray(p0, point.subtract(p0))); // normalized inside Ray constructor
        }
        return beam;
    } 

}