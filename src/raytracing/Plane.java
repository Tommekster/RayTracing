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
public class Plane extends GeometricObject{
    Point point;
    Normal normal;

    Texture texture = null;
    Texture bumpTexture = null;
    Normal texNormal = null;
    Normal Nt = null;
    double texWidth;
    double texHeight;
    double bumpRatio;
    
    Plane(Point _point, Normal _normal, Shade _shade){
        this(_point, _normal, _shade, null, 0);
    }
    
    Plane(Point _point, Normal _normal, Shade _shade, MaterialType _type){
        this(_point, _normal, _shade, _type, 0);
    }
    
    Plane(Point _point, Normal _normal, Shade _shade, MaterialType _type, double _ior){
        super(_shade,_type,_ior);
        point = new Point(_point);
        normal = new Normal(_normal);
    }

    @Override
    double hit(Ray ray) {
        // condition: (p-a)*n = 0
        double t = point.sub(ray.origin).dot(normal)/(ray.direction.dot(normal));
        
        return retVal(t);
    }
    
    @Override
    Normal getPointNormal(Point p) {
        if(bumpTexture == null) return normal;
        Point uv = getUVcoordinates(p);
        Vector bumpVec = bumpTexture.getBump(uv);
        Normal bump = new Normal(bumpVec.x, bumpRatio, bumpVec.y);
        
        return new Normal(
                bump.x * texNormal.x + bump.y * normal.x + bump.z * Nt.x,
                bump.x * texNormal.y + bump.y * normal.y + bump.z * Nt.y,
                bump.x * texNormal.z + bump.y * normal.z + bump.z * Nt.z
        );
    }

    @Override
    Shade getTexture(Point uv) {
        if(texture == null) {
            return shade.mul(((uv.x > 0.5)?1:(-1))*((uv.y > 0.5)?1:(-1)) > 0 ? 1 : 0.5);            
        }
        return texture.getColor(uv);
    }

    void setTexture(Texture texture, Point direction, double width, double height){
        texWidth = width;
        texHeight = height;
        this.texture = texture;
        Vector pa = direction.sub(point);
        texNormal = new Normal(pa.sub(normal.mul(normal.dot(pa))));
    }

    void setBumpTexture(Texture texture, Point direction, double width, double height, double ratio){
        texWidth = width;
        texHeight = height;
        this.bumpTexture = texture;
        Vector pa = direction.sub(point);
        texNormal = new Normal(pa.sub(normal.mul(normal.dot(pa))));
        Nt = new Normal(normal.cross(texNormal));
        bumpRatio = ratio;
    }

    @Override
    Point getUVcoordinates(Point p){
        if(texNormal == null) {
            texNormal = (Math.abs(normal.x) > Math.abs(normal.y))?new Normal(normal.z,0,-normal.x):new Normal(0,-normal.z,normal.y);
            texWidth = 10;
            texHeight = 10;
        }
        Point po = p.sub(point);
        //Point pop = po.sub(normal.mul(normal.dot(p)));
	double y = texNormal.dot(po)/texHeight;
        double x = po.sub(texNormal.mul(texNormal.dot(po))).getMagnitude()/texWidth;
        y -= Math.floor(y);
        x -= Math.floor(x);
        return new Point(x,y,0);
    }

    /*static class Finite {
        
    }*/
}
