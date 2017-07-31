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
    
    void normalize(){
        double magnitude = Math.sqrt(x*x + y*y + z*z);
        
        x /= magnitude;
        y /= magnitude;
        z /= magnitude;
    }
}
