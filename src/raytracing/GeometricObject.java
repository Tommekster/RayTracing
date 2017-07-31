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
    abstract double hit(Ray ray);
    double retVal(double t){
        if(t > RayTracing.MINIMAL_VALUE) return t;
        else return 0;
    }
}
