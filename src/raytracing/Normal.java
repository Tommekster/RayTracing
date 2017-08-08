/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import java.util.Random;

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
    
    static Normal getRandom(){
        Random random = new Random();
        double theta = random.nextDouble()*Math.PI*2 - Math.PI;
        double r1 = random.nextDouble();
        double sinphi = Math.sqrt(1 - r1*r1);
        return new Normal(sinphi*Math.cos(theta),r1,sinphi*Math.sin(theta));
    }
    
    static Normal getRandom(Normal top){
        Normal Nt = (Math.abs(top.x) > Math.abs(top.y))?new Normal(top.z,0,-top.x):new Normal(0,-top.z,top.y);
        Normal Nb = new Normal(top.cross(Nt));
        Normal sample = getRandom();
        return new Normal(
                sample.x * Nb.x + sample.y * top.x + sample.z * Nt.x,
                sample.x * Nb.y + sample.y * top.y + sample.z * Nt.y,
                sample.x * Nb.z + sample.y * top.z + sample.z * Nt.z
        );
    }
}
