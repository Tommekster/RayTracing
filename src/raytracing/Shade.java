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
    Shade(int code){
        this(new Color(code));
    }
    Shade(Color c){
        r = c.getRed()/255.0;
        g = c.getGreen()/255.0;
        b = c.getBlue()/255.0;
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
    
    Shade mul(Shade s){
        return new Shade(r*s.r,g*s.g,b*s.b);
    }
    
    Shade mul(double scalar){
        return new Shade(r*scalar, g*scalar, b*scalar);
    }
    
    Shade div(double scalar){
        r /= scalar;
        g /= scalar;
        b /= scalar;
        
        return this;
    }
    
    Shade norm(){
        if(r > 1) r = 1;
        else if(r < 0) r = 0;
        if(g > 1) g = 1;
        else if(g < 0) g = 0;
        if(b > 1) b = 1;
        else if(b < 0) b = 0;
        
        return this;
    }
    
    int toRGB(){
        return (new Color((float)r,(float)g,(float)b)).getRGB();
    }
}
