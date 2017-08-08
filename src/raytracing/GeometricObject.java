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
    double ior;
    int phong_n = 2;
    double Kd = 1, Ks = 0.5, Ka = 0.3, Ki = 0 /* indirect light */;

    public GeometricObject(Shade shade) {
        this(shade,null,0);
    }
    public GeometricObject(Shade shade, MaterialType type) {
        this(shade,type,0);
    }
    public GeometricObject(Shade shade, MaterialType type, double ior) {
        this.shade = (shade != null)?new Shade(shade):null;
        this.type = (type == null)?MaterialType.Diffuse:type;
        this.ior = (ior > 0)?ior:(type == MaterialType.ReflectionAndRefraction)?1.2:1;
        if(type == MaterialType.ReflectionAndRefraction) {
            phong_n = 100;
        }
    }
    
    abstract double hit(Ray ray);
    abstract Normal getPointNormal(Point p);
    double retVal(double t){
        if(t > RayTracing.MINIMAL_VALUE) return t;
        else return 0;
    }
    Shade getTexture(Point uv){
        return shade;
    }
    Point getUVcoordinates(Point p){
        return new Point(0,0,0);
    }
    
    enum MaterialType{
        Texture, Diffuse, Reflection, ReflectionAndRefraction, Glossy
    }
}
