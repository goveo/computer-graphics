import java.awt.Container;
import javax.media.j3d.*; // for transform
import javax.vecmath.Color3f;
import java.awt.Color;
import com.sun.j3d.utils.geometry.*;
import javax.vecmath.*; // for Vector3f
import com.sun.j3d.utils.image.TextureLoader;

public class Castle {
    public static Box getBody(float height, float width, float length) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Box(width, length, height, primflags, getBodyAppearence());
    }

    public static Box getWall(float height, float width, float length) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Box(width, length, height, primflags, getWoodAppearence());
    }

    private static Cylinder getTower(float height, float width) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Cylinder(width, height, primflags, getTowersAppearence());
    }

    private static Cone getTowerRoof(float height, float widht) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Cone(widht, height, primflags, getWoodAppearence());
    }

    private static Cylinder getTowerFlagHolder() {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Cylinder(0.005f, 0.1f, primflags, getWoodAppearence());
    }

    private static Box getTowerFlag() {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Box(0.001f, 0.01f, 0.03f, primflags, getFlagAppearence());
    }

    public static TransformGroup getTower(float height, float width, float xPos, float yPos){
        TransformGroup tg = new TransformGroup();

        Cylinder tower = Castle.getTower(height, width);
        Transform3D towerT = new Transform3D();
        towerT.setTranslation(new Vector3f(xPos, yPos, height*0.5f));
        towerT.setRotation(new AxisAngle4d(1, 0, 0, Math.toRadians(90)));
        TransformGroup towerTG = new TransformGroup();
        towerTG.setTransform(towerT);
        towerTG.addChild(tower);
        tg.addChild(towerTG);

        float roofHeight = 0.25f;
        Cone towerRoof = Castle.getTowerRoof(roofHeight, width);
        Transform3D towerRoofT = new Transform3D();
        towerRoofT.setTranslation(new Vector3f(xPos, yPos, height+roofHeight/2));
        towerRoofT.setRotation(new AxisAngle4d(1, 0, 0, Math.toRadians(90)));
        TransformGroup towerRoofTG = new TransformGroup();
        towerRoofTG.setTransform(towerRoofT);
        towerRoofTG.addChild(towerRoof);
        tg.addChild(towerRoofTG);


        Cylinder towerFlagHolder = Castle.getTowerFlagHolder();
        Transform3D towerFlagHolderT = new Transform3D();
        towerFlagHolderT.setTranslation(new Vector3f(xPos, yPos, height+roofHeight));
        towerFlagHolderT.setRotation(new AxisAngle4d(1, 0, 0, Math.toRadians(90)));
        TransformGroup towerFlagHolderTG = new TransformGroup();
        towerFlagHolderTG.setTransform(towerFlagHolderT);
        towerFlagHolderTG.addChild(towerFlagHolder);
        tg.addChild(towerFlagHolderTG);

        Box towerFlag = Castle.getTowerFlag();
        Transform3D towerFlagT = new Transform3D();
        towerFlagT.setTranslation(new Vector3f(xPos, yPos + 0.033f, height+roofHeight+0.03f));
        towerFlagT.setRotation(new AxisAngle4d(1, 0, 0, Math.toRadians(90)));
        TransformGroup towerFlagTG = new TransformGroup();
        towerFlagTG.setTransform(towerFlagT);
        towerFlagTG.addChild(towerFlag);
        tg.addChild(towerFlagTG);
        return tg;
    }

    private static Appearance getBodyAppearence() {
        Appearance ap = new Appearance();

        Color3f ambient = new Color3f(new Color(38,38, 38));
        Color3f emissive = new Color3f(new Color(38,38, 38));
        Color3f diffuse = new Color3f(new Color(0,22, 38));
        Color3f specular = new Color3f(new Color(22,22, 22));
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;
    }

    private static Appearance getWoodAppearence() {
        TextureLoader loader = new TextureLoader("assets\\wood.jpg", "LUMINANCE", new Container());
        Texture texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(texAttr);

        Color3f ambient = new Color3f(new Color(160, 70, 32));
        Color3f emissive = new Color3f(new Color(160, 70, 32));
        Color3f diffuse = new Color3f(new Color(160, 70, 32));
        Color3f specular = new Color3f(new Color(0,0, 0));
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;
    }

    private static Appearance getTowersAppearence() {
        TextureLoader loader = new TextureLoader("assets\\stone.jpg", "LUMINANCE", new Container());
        Texture texture = loader.getTexture();

        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(texAttr);

        Color3f ambient = new Color3f(new Color(255,255, 255));
        Color3f emissive = new Color3f(new Color(128,128, 128));
        Color3f diffuse = new Color3f(new Color(128,128, 128));
        Color3f specular = new Color3f(new Color(128,128, 128));
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;
    }

    private static Appearance getFlagAppearence() {
        Appearance ap = new Appearance();
        Color3f ambient = new Color3f(new Color(200, 0, 0));
        Color3f emissive = new Color3f(new Color(200, 0, 0));
        Color3f diffuse = new Color3f(new Color(200, 0, 0));
        Color3f specular = new Color3f(new Color(200,0, 0));
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;
    }

}
