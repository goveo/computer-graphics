package com.lab5;

import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;
import javax.vecmath.*;
import javax.media.j3d.Background;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;

public class Basketball extends JFrame {
    private static Canvas3D canvas;
    private static SimpleUniverse universe;
    private static BranchGroup root;

    private static TransformGroup post;
    private static TransformGroup ball;

    private Map<String, Shape3D> shapeMap;

    public Basketball() throws IOException {
        configureWindow();
        configureCanvas();
        configureUniverse();

        root = new BranchGroup();

        addImageBackground();

        addDirectionalLightToUniverse();
        addAmbientLightToUniverse();

        ChangeViewAngle();

        post = getPostGroup();
        ball = getBallGroup();

        root.addChild(post);
        root.addChild(ball);

        root.compile();
        universe.addBranchGraph(root);
    }


    // start initialization

    private void configureWindow() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void configureCanvas() {
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);
    }

    private void configureUniverse() {
        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
    }

    private void addImageBackground() {
        TextureLoader t = new TextureLoader("assets/street.jpg", canvas);
        Background background = new Background(t.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        background.setApplicationBounds(bounds);
        root.addChild(background);
    }

    private void addDirectionalLightToUniverse() {
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(100);

        DirectionalLight light = new DirectionalLight(new Color3f(1, 1, 1), new Vector3f(-1, -1, -1));
        light.setInfluencingBounds(bounds);

        root.addChild(light);
    }

    private void addAmbientLightToUniverse() {
        AmbientLight light = new AmbientLight(new Color3f(1, 1, 1));
        light.setInfluencingBounds(new BoundingSphere());
        root.addChild(light);
    }

    private void ChangeViewAngle() {
        ViewingPlatform vp = universe.getViewingPlatform();
        TransformGroup vpGroup = vp.getMultiTransformGroup().getTransformGroup(0);
        Transform3D vpTranslation = new Transform3D();
        vpTranslation.setTranslation(new Vector3f(0, 0, 6));
        vpGroup.setTransform(vpTranslation);
    }

    // end initialization


    // start main


    private TransformGroup getPostGroup() throws IOException {
        Shape3D post = getModelShape3D("basketball_post", "assets/Basketball_Post.obj");

        Transform3D transform3D = new Transform3D();
        transform3D.setScale(new Vector3d(0.8f, 0.8f, 0.8f));

//        TextureLoader loader = new TextureLoader("assets/stone.jpg", "RGP", new Container());
//        Texture texture = loader.getTexture();
//        texture.setBoundaryModeS(Texture.WRAP);
//        texture.setBoundaryModeT(Texture.WRAP);
//        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
//        TextureAttributes texAttr = new TextureAttributes();
//        texAttr.setTextureMode(TextureAttributes.MODULATE);
//        Appearance ap = new Appearance();
//        ap.setTexture(texture);
//        ap.setTextureAttributes(texAttr);
//        post.setAppearance(ap);

        Transform3D rotationY = new Transform3D();
        rotationY.rotY(Math.PI/4);
        transform3D.mul(rotationY);

        transform3D.setTranslation(new Vector3f(-1.1f, -0.3f, 0));

        TransformGroup group = getModelGroup(post);
        group.setTransform(transform3D);

        return group;
    }

    private TransformGroup getBallGroup() throws IOException {
        Shape3D ball = getModelShape3D("ball", "assets/ball.obj");
        Appearance ballAppearance = new Appearance();

        Material material = new Material();
        material.setEmissiveColor(new Color3f(0.2f, 0.1f, 0));
        material.setAmbientColor(new Color3f(0.5f, 0.2f, 0));
//        material.setDiffuseColor(new Color3f(10,25,71));
//        material.setSpecularColor(new Color3f(122,52,10));
//        material.setShininess(128);
        material.setLightingEnable(true);

        ballAppearance.setMaterial(material);
        ball.setAppearance(ballAppearance);

        Transform3D transform3D = new Transform3D();
        transform3D.setScale(new Vector3d(0.07f, 0.07f, 0.07f));

        Transform3D rotationY = new Transform3D();
        rotationY.rotY(Math.PI/4);
        transform3D.mul(rotationY);

        TransformGroup group = getModelGroup(ball);
        group.setTransform(transform3D);

        return group;
    }

    private TransformGroup getModelGroup(Shape3D shape) {
        TransformGroup group = new TransformGroup();
        group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        group.addChild(shape);
        return group;
    }

    private Shape3D getModelShape3D(String name, String path) throws IOException {
        Scene scene = getSceneFromFile(path);
        Map<String, Shape3D> map = scene.getNamedObjects();
        printModelElementsList(map);
        Shape3D shape = map.get(name);
        scene.getSceneGroup().removeChild(shape);
        return shape;
    }

    private Scene getSceneFromFile(String path) throws IOException {
        ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
        file.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
        return file.load(new FileReader(path));
    }

    private void printModelElementsList(Map<String, Shape3D> map) {
        for (String name : map.keySet()) {
            System.out.println("Name: " + name);
        }
    }

    private void addAppearance(Shape3D shape, String path) {
        Appearance appearance = new Appearance();
        appearance.setTexture(getTexture(path));
        TextureAttributes attrs = new TextureAttributes();
        attrs.setTextureMode(TextureAttributes.COMBINE);
        appearance.setTextureAttributes(attrs);
        shape.setAppearance(appearance);
    }

    private Texture getTexture(String path) {
        TextureLoader textureLoader = new TextureLoader(path, "LUMINANCE", canvas);
        Texture texture = textureLoader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
        return texture;
    }

    Material getMaterial(Color emissiveColor, Color ambientColor, Color diffuseColor, Color specularColor) {
        Material material = new Material();
        material.setEmissiveColor(new Color3f(emissiveColor));
        material.setAmbientColor(new Color3f(ambientColor));
        material.setDiffuseColor(new Color3f(diffuseColor));
        material.setSpecularColor(new Color3f(specularColor));
//        material.setShininess(64);
//        material.setLightingEnable(true);
        return material;
    }

    public static void main(String[] args) {
        try {
            Basketball window = new Basketball();
            BallAnimation ballMovement = new BallAnimation(ball);
            canvas.addKeyListener(ballMovement);
            window.setVisible(true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

