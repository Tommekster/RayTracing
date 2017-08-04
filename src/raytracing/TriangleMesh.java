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
public class TriangleMesh extends GeometricObject{
    
    Normal [] normals;
    int nTriang = 0;
    Point [] vertices;
    int [] trgVertInd;
    Triangle [] triangles; 

    public TriangleMesh(Point [] _vertices, int [] faceIndex, int [] vertexIndex, Shade _shade) {
        vertices = _vertices;
        shade = _shade;
        int nPolys = faceIndex.length;
        
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
        for(int face = 0; face < nPolys; face++){
            for(int j = 0; j < faceIndex[face] - 2; j++){
                trgVertInd[l] = vertexIndex[k]; // v0
                trgVertInd[l + 1] = vertexIndex[k + j + 1]; // v_{j+1}
                trgVertInd[l + 2] = vertexIndex[k + j + 2]; // v_{j+1}
                triangles[t++] = new Triangle(vertices[vertexIndex[k]], 
                        vertices[vertexIndex[k+j+1]], 
                        vertices[vertexIndex[k+j+2]], 
                        null);
                l += 3;
            }
            k += faceIndex[face];
        }
    }

    @Override
    double hit(Ray ray) {
        Triangle triangle = null;
        double t_near = 0; 
        for(int i = 0; i < nTriang; i++) {
            double t = triangles[i].hit(ray);
            if(t == 0) continue;
            if(triangle == null || t < t_near) {
                t_near = t;
                triangle = triangles[i];
            }
        }
        return t_near;
    }

    @Override
    Normal getPointNormal(Point p) {
        return new Normal(1,1,0);
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
    
}
