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
public class Light {
    Point position;
    float brightness;
    Shade color;

    public Light(Point position, float brightness, Shade color) {
        this.position = position;
        this.brightness = brightness;
        this.color = color;
    }
    
    
}
