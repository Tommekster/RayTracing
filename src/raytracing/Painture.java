/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author zikmundt
 */
public class Painture {
    int width = 640;
    int height = 480;
    /*int width = 1024;
    int height = 768;*/
    double scale = 2;
    BufferedImage buffer = null;
    Scene scene;
    Sampler sampler = new Sampler.Regular(4);
    Projection projection = new Projection.Perspective(height,new Point(0, 300, 300), new Point(0, 0, 0), 45);
    //Projection projection = new Projection.Orthogonal();

    public Painture(Scene s) {
        scene = s;
    }
    
    
    
    void createImage(){
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        
        
        Sphere sphere = new Sphere(new Point(), 60, new Shade(1,0,0));
        
        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                trace(x, y);
            }
        }
    }

    void trace(int x, int y) {
        Shade shade = new Shade();
        for(int row = 0; row < sampler.samples; row++){
            for(int col = 0; col < sampler.samples; col++){
                Point sample = sampler.sample(row, col, x-width/2, y-height/2).mul(scale);
                Ray ray = projection.createRay(sample);
                
                Hit hit = scene.hitObject(ray);
                if(hit != null){
                    shade.add(hit.getShade(scene));
                } else {
                    shade.add(scene.background);
                }
            }
        }
        buffer.setRGB(x, height-y-1 /* reverse coord*/, shade.div(sampler.samples*sampler.samples).toRGB());
    }
    
    void saveFile(String filename) throws IOException{
        File file = new File(filename);
        ImageIO.write(buffer, "PNG", file);
    }
}
