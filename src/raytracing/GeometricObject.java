/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

/**
 *
 * @author zikmundt
 */
public abstract class GeometricObject {
    Shade shade;
    MaterialType type = MaterialType.Diffuse;
    double ior = 1;
    double [] albedo = {0.18, 0.18, 0.18}; 
    
    abstract double hit(Ray ray);
    abstract Normal getPointNormal(Point p);
    double retVal(double t){
        if(t > RayTracing.MINIMAL_VALUE) return t;
        else return 0;
    }
    
    enum MaterialType{
        Diffuse, Reflection, ReflectionAndRefraction
    }
}
