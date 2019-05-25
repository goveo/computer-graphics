package com.lab6;

import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;
import java.util.Hashtable;
import java.util.Enumeration;
import com.sun.j3d.utils.geometry.Sphere;

public class Car extends JFrame{
    public Canvas3D myCanvas3D;

    public Car(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        SimpleUniverse universe = new SimpleUniverse(myCanvas3D);
        universe.getViewingPlatform().setNominalViewingTransform();

        // set the geometry and transformations
        createSceneGraph(universe);
        addLight(universe);

        OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
        universe.getViewingPlatform().setViewPlatformBehavior(ob);

        configureWindow();

        getContentPane().add("Center", myCanvas3D);
        setVisible(true);
    }

    private void configureWindow() {
        setTitle("Lab 6");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private TransformGroup getStartTG() {
        Transform3D startTransformation = new Transform3D();
        startTransformation.setScale(1.0/4);
        Transform3D combinedStartTransformation = new Transform3D();
        combinedStartTransformation.rotY(-3*Math.PI/2);
        combinedStartTransformation.mul(startTransformation);

        return new TransformGroup(combinedStartTransformation);
    }

    public void createSceneGraph(SimpleUniverse su){
        // loading object
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
        String name;
        BranchGroup carBranchGroup = new BranchGroup();
        Background carBackground = new Background(new Color3f(1.0f,1.0f,1.0f));

        Scene carScene = null;
        try {
            carScene = f.load("models/car.obj");
        }
        catch (Exception e){
            System.out.println("File loading failed:" + e);
        }
        Hashtable namedObjects = carScene.getNamedObjects();
        Enumeration enumer = namedObjects.keys();
        while (enumer.hasMoreElements()){
            name = (String) enumer.nextElement();
            System.out.println("Name: " + name);
        }


        // start animation
        TransformGroup carStartTransformGroup = getStartTG();

        // wheels
        int movesCount = 100; // moves count
        int movesDuration = 500; // moves for 0,3 seconds
        int startTime = 0; // launch animation after timeStart seconds


        // wheel 1
        Shape3D wheel1 = (Shape3D) namedObjects.get("wheel1");
        TransformGroup wheelTG1 = getWheelTG(movesCount, startTime, movesDuration, wheel1, true);

        Shape3D wheel2 = (Shape3D) namedObjects.get("wheel2");
        TransformGroup wheelTG2 = getWheelTG(movesCount, startTime, movesDuration, wheel2, true);

        Shape3D wheel3 = (Shape3D) namedObjects.get("wheel3");
        TransformGroup wheelTG3 = getWheelTG(movesCount, startTime, movesDuration, wheel3, false);

        Shape3D wheel4 = (Shape3D) namedObjects.get("wheel4");
        TransformGroup wheelTG4 = getWheelTG(movesCount, startTime, movesDuration, wheel4, false);

        TransformGroup sceneGroup = new TransformGroup();
        sceneGroup.addChild(wheelTG1);
        sceneGroup.addChild(wheelTG2);
        sceneGroup.addChild(wheelTG3);
        sceneGroup.addChild(wheelTG4);
        TransformGroup tgBody = new TransformGroup();
        Shape3D carBodyShape = (Shape3D) namedObjects.get("body");
        tgBody.addChild(carBodyShape.cloneTree());
        sceneGroup.addChild(tgBody.cloneTree());

        TransformGroup whiteTransXformGroup = translate(
                carStartTransformGroup,
                new Vector3f(0.0f,0.0f,0.5f));

        TransformGroup whiteRotXformGroup = rotate(whiteTransXformGroup, new Alpha(10,5000));
        carBranchGroup.addChild(whiteRotXformGroup);
        carStartTransformGroup.addChild(sceneGroup);

        // adding the car background to branch group
        BoundingSphere bounds = new BoundingSphere(new Point3d(120.0,250.0,100.0),Double.MAX_VALUE);
        carBackground.setApplicationBounds(bounds);
        carBranchGroup.addChild(carBackground);

        carBranchGroup.compile();
        su.addBranchGraph(carBranchGroup);
    }

    public TransformGroup getWheelTG(int movesCount, int startTime, int movesDuration, Shape3D wheel, boolean isBack) {
        Alpha leg1_1RotAlpha = new Alpha(movesCount, Alpha.INCREASING_ENABLE, startTime, 0, movesDuration,0,0,0,0,0);

        TransformGroup wheelTG = new TransformGroup();

        Appearance wheelAppearance = new Appearance();

        Material material = new Material();
        material.setEmissiveColor(new Color3f(0.05f, 0.05f, 0.05f));
        material.setAmbientColor(new Color3f(0.05f, 0.05f, 0.05f));
        material.setDiffuseColor(new Color3f(0.05f, 0.05f, 0.05f));
//        material.setSpecularColor(new Color3f(0.05f, 0.05f, 0.05f));
//        material.setShininess(128);
        material.setLightingEnable(true);

        wheelAppearance.setMaterial(material);
        wheel.setAppearance(wheelAppearance);

        wheelTG.addChild(wheel.cloneTree());

        Transform3D legRotAxis = new Transform3D();
        if (isBack) {
            legRotAxis.set(new Vector3d(0, -0.225, -0.54));
        } else {
            legRotAxis.set(new Vector3d(0, -0.225, 0.64));
        }
        legRotAxis.setRotation(new AxisAngle4d(0, 0, -10, Math.PI/2));

        RotationInterpolator wheel1rot = new RotationInterpolator(leg1_1RotAlpha, wheelTG, legRotAxis,(float) 0.0f, (float) Math.PI*2); // Math.PI*2
        wheel1rot.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
        wheelTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        wheelTG.addChild(wheel1rot);
        return wheelTG;
    }

    public void addLight(SimpleUniverse su){
        BranchGroup bgLight = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
        Vector3f lightDir1 = new Vector3f(-1.0f,0.0f,-0.5f);
        DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
        light1.setInfluencingBounds(bounds);
        bgLight.addChild(light1);
        su.addBranchGraph(bgLight);
    }

    TransformGroup translate(Node node,Vector3f vector){
        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(vector);
        TransformGroup group = new TransformGroup();
        group.setTransform(transform3D);
        group.addChild(node);
        return group;
    }

    TransformGroup rotate(Node node,Alpha alpha){

        TransformGroup group = new TransformGroup();
        group.setCapability(
                TransformGroup.ALLOW_TRANSFORM_WRITE);

        RotationInterpolator interpolator =
                new RotationInterpolator(alpha,group);

        interpolator.setSchedulingBounds(new BoundingSphere(
                new Point3d(0.0,0.0,0.0),1.0));

        group.addChild(interpolator);
        group.addChild(node);

        return group;

    }

    public static void main(String[] args) {
        Car start = new Car();
    }

}

