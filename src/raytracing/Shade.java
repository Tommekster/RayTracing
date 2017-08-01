/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import java.awt.Color;

/**
 *
 * @author zikmundt
 */
public class Shade {
    double r,g,b;
    Shade(){
        this(0,0,0);
    }
    Shade(double _r, double _g, double _b){
        r = _r;
        g = _g;
        b = _b;
    }
    Shade(Shade s){
        r = s.r;
        g = s.g;
        b = s.b;
    }
    
    void add(Shade s){
        r += s.r;
        g += s.g;
        b += s.b;
    }
    
    Shade div(double scalar){
        r /= scalar;
        g /= scalar;
        b /= scalar;
        
        return this;
    }
    
    int toRGB(){
        return (new Color((float)r,(float)g,(float)b)).getRGB();
    }
}
