/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import java.io.IOException;

/**
 *
 * @author zikmundt
 */
public class RayTracing {
    public static final double MINIMAL_VALUE = 1E-9;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Painture p = new Painture(new Scene1());
        System.out.println("generating image...");
        p.createImage();
        System.out.println("saving to image.png");
        p.saveFile("image.png");
    }
    
}
