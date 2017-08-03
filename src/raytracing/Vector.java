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
public class Vector extends Point {
    Vector(){
        super(0,0,0);
    }
    Vector(double x, double y, double z){
        super(x,y,z);
    }
    Vector(Vector v){
        super(v);
    }
    Vector(Point p){
        super(p);
    }
    
    double getMagnitude(){
        return Math.sqrt(x*x + y*y + z*z);
    }
    
    Vector normalize(){
        double magnitude = getMagnitude();
        
        x /= magnitude;
        y /= magnitude;
        z /= magnitude;
        
        return this;
    }
    
    /*Vector add(Vector v){
        return (Vector) super.add(v);
    }
    
    Vector sub(Vector v){
        return (Vector) super.sub(v);
    }*/
    
    @Override
    Vector mul(double scalar){
        return new Vector(super.mul(scalar));
    }
    
    Vector cross(Vector v){
        return new Vector(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x);
    }
}
