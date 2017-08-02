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
public abstract class Light {
    float brightness;
    Shade color;

    public Light() {
        brightness = 1;
        color = new Shade();
    }
    public Light(Point position, float brightness, Shade color) {
        this.brightness = brightness;
        this.color = color;
    }
    
    abstract Vector getDirection(Point p);
    
    static class Distant extends Light{
        Normal normal;

        public Distant() {
            normal = new Normal(0,-1,0);
        }
        public Distant(Normal normal) {
            this.normal = normal;
        }

        @Override
        Vector getDirection(Point p) {
            return normal;
        }
        
        
    }
    
    static class Spherical extends Light{
        Point position;

        public Spherical() {
            position = new Point(0, 1e8, 0);
        }
        
        @Override
        Vector getDirection(Point p) {
            return new Normal(p.sub(position));
        }
        
    }
}