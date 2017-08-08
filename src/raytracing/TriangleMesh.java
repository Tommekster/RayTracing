/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author zikmundt
 */
public class TriangleMesh extends GeometricObject{
    int nTriang = 0;
    //Point [] vertices;
    int [] trgVertInd;
    Triangle [] triangles; 
    BoundingBox boundingBox;

    public TriangleMesh(Point [] vertices, int [] faceIndex, int [] vertexIndex, Shade _shade) {
        this(vertices, null, faceIndex, vertexIndex, _shade);
    }
    
    public TriangleMesh(Point [] vertices, Normal [] normals, int [] faceIndex, int [] vertexIndex, Shade _shade) {
        this(vertices, null, faceIndex, vertexIndex, null, _shade);
    }
        
    public TriangleMesh(Point [] vertices, Normal [] normals, int [] faceIndex, int [] vertexIndex, Point [] uvs, Shade _shade) {
        super(_shade);
        boundingBox = new BoundingBox(vertices, null);
        
        // finds how many triangles will be created 
        for(int faceVert : faceIndex){ // for each face
            nTriang += faceVert - 2;
        }
        
        // allocate memory
        trgVertInd = new int[3 * nTriang];
        triangles = new Triangle[nTriang];
        
        // generate triangles vertex
        int k = 0;
        int l = 0;
        int t = 0;
        for(int face = 0; face < faceIndex.length; face++){
            for(int j = 0; j < faceIndex[face] - 2; j++){
                trgVertInd[l] = vertexIndex[k]; // v0
                trgVertInd[l + 1] = vertexIndex[k + j + 1]; // v_{j+1}
                trgVertInd[l + 2] = vertexIndex[k + j + 2]; // v_{j+1}
                Triangle triangle = new Triangle(vertices[vertexIndex[k]], 
                        vertices[vertexIndex[k+j+1]], 
                        vertices[vertexIndex[k+j+2]], 
                        null);
                if(normals != null){
                    triangle.vertNormals = new Normal[] {
                        normals[k], 
                        normals[k+j+1], 
                        normals[k+j+2]
                    };
                }
                if(uvs != null){
                    triangle.uvs = new Point [] {
                        uvs[k],
                        uvs[k+j+1],
                        uvs[k+j+2]
                    };
                }
                triangles[t++] = triangle;
                
                l += 3;
            }
            k += faceIndex[face];
        }
    }
    
    Object [] hitTriangle(Ray ray) {
        Triangle triangle = null;
        Double t_near = new Double(0); 
        if(boundingBox.hit(ray) > RayTracing.MINIMAL_VALUE){
            for(int i = 0; i < nTriang; i++) {
                double t = triangles[i].hit(ray);
                if(t == 0) continue;
                if(triangle == null || t < t_near) {
                    t_near = t;
                    triangle = triangles[i];
                }
            }
        }
        return new Object [] {t_near,triangle};
    }

    @Override
    double hit(Ray ray) {
        return (Double) hitTriangle(ray)[0];
    }

    @Override
    Normal getPointNormal(Point p) {
        return new Normal(1,1,0);
    }
    
    TriangleMesh transform(TransformMatrix m){
        for(Triangle t : triangles){
            t.transform(m);
        }
        boundingBox = new BoundingBox(triangles, null);
        return this;
    }
    
    void setTexture(Texture texture){
        for(Triangle t : triangles){
            t.texture = texture;
        }
    }
    
    static TriangleMesh generateLObject(Shade _shade) {
        return new TriangleMesh(new Point[]{
                new Point(-50,-50,50),
                new Point(50,-50,50),
                new Point(50,-50,-50),
                new Point(-50,-50,-50),
                new Point(-50,50,-50),
                new Point(-50,50,50)
            }, 
            new int[] {4,4}, new int[] {0, 1, 2, 3, 0, 3, 4, 5}, _shade);
    }
    
    static TriangleMesh loadGeoFile(String filename, Shade _shade) throws FileNotFoundException, IOException{
        File file = new File(filename);
        int numFaces = 0;
        int [] faceIndex = null;
        int numVertInd = 0;
        int [] vertexIndex = null;
        Point [] vertices = null;
        Normal [] normals = null;
        Point [] uvs = null;
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int nl = 0;
            while ((line = br.readLine()) != null) {
                int i = 0;
                String [] spl = line.split("\\s+");
                switch(nl){
                    case 0: // number of faces
                        numFaces = new Integer(spl[0]);
                        break;
                    case 1: // faces index
                        faceIndex = new int [spl.length];
                        for(String s : spl){
                            int n = new Integer(s);
                            faceIndex[i++] = n;
                            numVertInd += n;
                        }
                        break;
                    case 2: // vertex index
                        vertexIndex = new int [numVertInd];
                        for(String s : spl){
                            vertexIndex[i++] = new Integer(s);
                        }
                        break;
                    case 3: // vertex positions
                        {
                            vertices = new Point[spl.length/3];
                            for(int j = 0; j < spl.length; j += 3){
                                vertices[i++] = new Point(
                                        new Double(spl[j]),
                                        new Double(spl[j+1]),
                                        new Double(spl[j+2])
                                );
                            }
                        }
                        break;
                    case 4: // normals
                        {
                            normals = new Normal[spl.length/3];
                            for(int j = 0; j < spl.length; j += 3){
                                normals[i++] = new Normal(
                                        new Double(spl[j]),
                                        new Double(spl[j+1]),
                                        new Double(spl[j+2])
                                );
                            }
                        }
                        break;
                    case 5: // uvs
                        {
                            uvs = new Point[spl.length/2];
                            for(int j = 0; j < spl.length; j += 2){
                                uvs[i++] = new Point(
                                        new Double(spl[j]),
                                        new Double(spl[j+1]),
                                        0
                                );
                            }
                        }
                        break;
                    default:
                        break;
                }
                nl++;
            }
        }
        
        if(faceIndex == null || vertexIndex == null || vertices == null) return null;
        return new TriangleMesh(vertices, normals, faceIndex, vertexIndex, uvs, _shade);
    }
}
