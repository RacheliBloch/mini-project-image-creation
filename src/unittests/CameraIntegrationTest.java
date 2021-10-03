package unittests;
/*
 * @author Racheli&Efrat
 */

import elements.*;
import primitives.*;
import geometries.*;
import geometries.Intersectable.GeoPoint;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;

public class CameraIntegrationTest {

    //construct Ray Through Pixel With Sphere Tests
    @Test
    public void constructRayThroughPixelWithSphere1() 
    {
    	Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Sphere sph =  new Sphere(1, new Point3D(0, 0, 3));
//        Ray ray = cam1.constructRayThroughPixel(3,3,0,0,1,3,3);
//        List<Point3D> results =  sph.findIntersections(ray);
        List<GeoPoint> results;
        int count = 0;
        int Nx =3;
        int Ny =3;
        
        for (int i = 0; i < 3; ++i) 
        {
            for (int j = 0; j < 3; ++j)
            {
                results = sph.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("too bad",2,count);
        System.out.println("count: "+count);
    }
    
    @Test
    public void constructRayThroughPixelWithSphere2() 
    {
        Camera cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        Sphere sph =  new Sphere(2.5, new Point3D(0, 0, 2.5));
        List<GeoPoint> results;

        int count = 0;
        int Nx =3;
        int Ny =3;

        for (int i = 0; i < 3; ++i) 
        {
            for (int j = 0; j < 3; ++j) 
            {
                results = sph.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("too bad",18,count);
        System.out.println("count: "+count);
    }
    
    @Test
    public void constructRayThroughPixelWithSphere3()
    {
        Camera cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
    	 Sphere sph =  new Sphere(2, new Point3D(0, 0, 2));	
    	 List<GeoPoint> results;

         int count = 0;
         int Nx =3;
         int Ny =3;

         for (int i = 0; i < 3; ++i) 
         {
             for (int j = 0; j < 3; ++j) 
             {
                 results = sph.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                 if (results != null)
                     count += results.size();
             }
         }
         assertEquals("too bad",10,count);
         System.out.println("count: "+count);
    }

    @Test
    public void constructRayThroughPixelWithSphere4()
    {
        Camera cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
    	 Sphere sph =  new Sphere(4, new Point3D(0, 0, 0.5));	
    	 List<GeoPoint> results;

         int count = 0;
         int Nx =3;
         int Ny =3;

         for (int i = 0; i < 3; ++i) 
         {
             for (int j = 0; j < 3; ++j) 
             {
                 results = sph.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                 if (results != null)
                     count += results.size();
             }
         }
         assertEquals("too bad",9,count);
         System.out.println("count: "+count);
    }
    
    @Test
    public void constructRayThroughPixelWithSphere5()
    {
        Camera cam = new Camera(new Point3D(0, 0, -0.25), new Vector(0, 0, 1), new Vector(0, -1, 0));
    	 Sphere sph =  new Sphere(0.5, new Point3D(0, 0, -1));	
    	 List<GeoPoint> results;

         int count = 0;
         int Nx =3;
         int Ny =3;

         for (int i = 0; i < 3; ++i) 
         {
             for (int j = 0; j < 3; ++j) 
             {
                 results = sph.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                 if (results != null)
                     count += results.size();
             }
         }
         assertEquals("too bad",0,count);
         System.out.println("count: "+count);
    }
    
    //construct Ray Through Pixel With Plane Tests
    @Test
    public void constructRayThroughPixelWithPlane1()
    {
        Camera cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
    	Plane pln=new Plane(new Point3D(0,0,4),new Vector(0, 0, 1));
    	 List<GeoPoint> results;

         int count = 0;
         int Nx =3;
         int Ny =3;

         for (int i = 0; i < 3; ++i) 
         {
             for (int j = 0; j < 3; ++j) 
             {
                 results = pln.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                 if (results != null)
                     count += results.size();
             }
         }
         assertEquals("too bad",9,count);
         System.out.println("count: "+count);
    }
    
    @Test
    public void constructRayThroughPixelWithPlane2()
    {
        Camera cam =  new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Plane pln=new Plane(new Point3D(0,0,2),new Point3D(1,-3,1),new Point3D(0,-6,0));
    	 List<GeoPoint> results;

         int count = 0;
         int Nx =3;
         int Ny =3;

         for (int i = 0; i < 3; ++i) 
         {
             for (int j = 0; j < 3; ++j) 
             {
                 results = pln.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                 if (results != null)
                     count += results.size();
             }
         }
         assertEquals("too bad",9,count);
         System.out.println("count: "+count);
    }
  
    @Test
    public void constructRayThroughPixelWithPlane3()
    {
        Camera cam =  new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
    	Plane pln=new Plane(new Point3D(0,0,5),new Point3D(0,-5,0),new Point3D(1,-2.5,2.5));
    	 List<GeoPoint> results;

         int count = 0;
         int Nx =3;
         int Ny =3;

         for (int i = 0; i < 3; ++i) 
         {
             for (int j = 0; j < 3; ++j) 
             {
                 results = pln.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                 if (results != null)
                     count += results.size();
             }
         }
         assertEquals("too bad",6,count);
         System.out.println("count: "+count);
    }
    
    //construct Ray Through Pixel With Triangle Tests
    @Test
    public void constructRayThroughPixelWithTriangle1()
    {
        Camera cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
       Triangle triangle=new Triangle(new Point3D(0, -1, 2),new Point3D(1, 1, 2),new Point3D(-1, 1, 2));
    	 List<GeoPoint> results;

         int count = 0;
         int Nx =3;
         int Ny =3;

         for (int i = 0; i < 3; ++i) 
         {
             for (int j = 0; j < 3; ++j) 
             {
                 results = triangle.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                 if (results != null)
                     count += results.size();
             }
         }
         assertEquals("too bad",1,count);
         System.out.println("count: "+count);
    }
    
    @Test
    public void constructRayThroughPixelWithTriangle2()
    {
        Camera cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
       Triangle triangle=new Triangle(new Point3D(0, -20, 2),new Point3D(1, 1, 2),new Point3D(-1, 1, 2));
    	 List<GeoPoint> results;

         int count = 0;
         int Nx =3;
         int Ny =3;

         for (int i = 0; i < 3; ++i) 
         {
             for (int j = 0; j < 3; ++j) 
             {
                 results = triangle.findIntersections(cam.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                 if (results != null)
                     count += results.size();
             }
         }
         assertEquals("too bad",2,count);
         System.out.println("count: "+count);
    }
}
