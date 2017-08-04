/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;

/**
 *
 * @author zikmundt
 */
public class Painture {
    int width = 320;
    int height = 240;
    double scale = 2;
    BufferedImage buffer = null;
    Scene scene;
    Sampler sampler;
    //Sampler sampler = new Sampler.Jittered(8);
    Projection projection;
    //Projection projection = new Projection.Orthogonal();
    Tracer tracer;

    public Painture(Scene s) {
        scene = s;
        sampler = new Sampler.Regular(1);
        projection = s.getProjection(height);
        tracer = new Tracer(scene, sampler, projection, width, height, scale);
    }
    public Painture(Scene s, Sampler _sampler) {
        scene = s;
        sampler = _sampler;
        projection = s.getProjection(height);
        tracer = new Tracer(scene, sampler, projection, width, height, scale);
    }
    public Painture(int width, int height, double scale, Scene s, Sampler _sampler) {
        scene = s;
        sampler = _sampler;
        projection = s.getProjection(height);
        tracer = new Tracer(scene, sampler, projection, width, height, scale);
        this.width = width;
        this.height = height;
        this.scale = scale;
    }
    
    void setDimensions(int width, int height, double scale){
        this.width = width;
        this.height = height;
        this.scale = scale;
        tracer.setDimensions(width, height, scale);
    }
    
    void createImage(){
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        
        
        Sphere sphere = new Sphere(new Point(), 60, new Shade(1,0,0));
        
        IntStream.iterate(0, n->n+1).limit(height).parallel().forEach(y->{
            for (int x = 0; x < width; x++){
                buffer.setRGB(x, height-y-1 /* reverse coord*/, tracer.trace(x, y));
            }
        });
    }
    
    void saveFile(String filename) throws IOException{
        File file = new File(filename);
        ImageIO.write(buffer, "PNG", file);
    }
}
