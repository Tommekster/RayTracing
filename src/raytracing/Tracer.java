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
public class Tracer {
    Scene scene;
    Sampler sampler;
    Projection projection;
    int width;
    int height;
    double scale;

    public Tracer(Scene scene, Sampler sampler, Projection projection, int width, int height, double scale) {
        this.scene = scene;
        this.sampler = sampler;
        this.projection = projection;
        this.width = width;
        this.height = height;
        this.scale = scale;
    }
    
    int trace(int x, int y) {
        Shade shade = new Shade();
        for(int row = 0; row < sampler.samples; row++){
            for(int col = 0; col < sampler.samples; col++){
                Point sample = sampler.sample(row, col, x-width/2, y-height/2).mul(scale);
                Ray ray = projection.createRay(sample);
                
                //shade.add(castRay(ray));
                Hit hit = scene.hitObject(ray);
                if(hit != null){
                    shade.add(hit.getShade(scene));
                } else {
                    shade.add(scene.background);
                }
            }
        }
        return shade.div(sampler.samples*sampler.samples).toRGB();
    }

    Shade castRay(Ray ray) {
        Hit hit = scene.hitObject(ray);
        if(hit != null){
            return new Shade(hit.getShade(scene));
        } else {
            return new Shade(scene.background);
        }
    }
    
}
