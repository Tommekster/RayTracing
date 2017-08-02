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
public class Normal extends Vector{
    Normal(){
        super(0,0,0);
        normalize();
    }
    Normal(double x, double y, double z){
        super(x,y,z);
        normalize();
    }
    Normal(Normal n){
        super(n);
        normalize();
    }
    Normal(Point p){
        super(p);
        normalize();
    }
}
