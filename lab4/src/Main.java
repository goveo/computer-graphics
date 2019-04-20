import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

public class Main implements ActionListener {
    private TransformGroup mainTransformGroup;
    private TransformGroup viewingTransformGroup;
    private Transform3D mainTransform3D = new Transform3D();
    private Transform3D viewingTransform = new Transform3D();
    private float angle = 0;
    private float cameraHeight = 3.0f;
    private float cameraDistance = 3.5f;

    private float towerRadius = 0.7f;

    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        Timer timer = new Timer(50, this);
        SimpleUniverse universe = new SimpleUniverse();

        viewingTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();
        universe.addBranchGraph(createSceneGraph());

        timer.start();
    }

    private BranchGroup createSceneGraph() {
        BranchGroup group = new BranchGroup();

        mainTransformGroup = new TransformGroup();
        mainTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        buildCastle();
        group.addChild(mainTransformGroup);

        Background background = new Background(new Color3f(0.5f, 0.66f, 1.0f)); // white color
        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000);
        background.setApplicationBounds(sphere);
        group.addChild(background);

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
        Color3f light1Color = new Color3f(1.0f, 0.5f, 0.4f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        group.addChild(light1);

        return group;
    }

    private void buildCastle() {
        Box body1 = Castle.getBody(0.125f, 1.0f, 1.0f);
        Transform3D body1T = new Transform3D();
        body1T.setTranslation(new Vector3f());
        TransformGroup body1TG = new TransformGroup();
        body1TG.setTransform(body1T);
        body1TG.addChild(body1);
        mainTransformGroup.addChild(body1TG);

        setMainBody();
        setWalls(0.25f, 0.03f);

        setTowers();
    }

    private void setWalls(float height, float width) {
        Box wall = Castle.getWall(height, width, towerRadius);
        Transform3D wallT = new Transform3D();
        wallT.setTranslation(new Vector3f(-towerRadius, 0, height));
        TransformGroup wallTG = new TransformGroup();
        wallTG.setTransform(wallT);
        wallTG.addChild(wall);
        mainTransformGroup.addChild(wallTG);

        Box wall2 = Castle.getWall(height, width, towerRadius);
        Transform3D wall2T = new Transform3D();
        wall2T.setTranslation(new Vector3f(towerRadius, 0, height));
        TransformGroup wall2TG = new TransformGroup();
        wall2TG.setTransform(wall2T);
        wall2TG.addChild(wall2);
        mainTransformGroup.addChild(wall2TG);

        Box wall3 = Castle.getWall(height, towerRadius, width);
        Transform3D wall3T = new Transform3D();
        wall3T.setTranslation(new Vector3f(0, -towerRadius, height));
        TransformGroup wall3TG = new TransformGroup();
        wall3TG.setTransform(wall3T);
        wall3TG.addChild(wall3);
        mainTransformGroup.addChild(wall3TG);

        Box wall4 = Castle.getWall(height, towerRadius, width);
        Transform3D wall4T = new Transform3D();
        wall4T.setTranslation(new Vector3f(0, towerRadius, height));
        TransformGroup wall4TG = new TransformGroup();
        wall4TG.setTransform(wall4T);
        wall4TG.addChild(wall4);
        mainTransformGroup.addChild(wall4TG);
    }

    private void setMainBody() {
        Box body2 = Castle.getBody(0.125f, 0.3f, 0.3f);
        Transform3D body2T = new Transform3D();
        body2T.setTranslation(new Vector3f(0, 0, 0.3f));
        TransformGroup body2TG = new TransformGroup();
        body2TG.setTransform(body2T);
        body2TG.addChild(body2);
        mainTransformGroup.addChild(body2TG);
    }

    private void setTowers(){
        TransformGroup cylinderTower1 = Castle.getTower(0.8f, 0.1f,0.0f, 0.0f);

        TransformGroup cylinderTower2 = Castle.getTower(1, 0.15f, -towerRadius, towerRadius);
        TransformGroup cylinderTower3 = Castle.getTower(1, 0.15f, towerRadius, towerRadius);
        TransformGroup cylinderTower4 = Castle.getTower(1, 0.15f, towerRadius, -towerRadius);
        TransformGroup cylinderTower5 = Castle.getTower(1, 0.15f, -towerRadius, -towerRadius);
        mainTransformGroup.addChild(cylinderTower1);
        mainTransformGroup.addChild(cylinderTower2);
        mainTransformGroup.addChild(cylinderTower3);
        mainTransformGroup.addChild(cylinderTower4);
        mainTransformGroup.addChild(cylinderTower5);
    }

    // ActionListener interface
    @Override
    public void actionPerformed(ActionEvent e) {
        float delta = 0.03f;

        mainTransform3D.rotZ(angle);
        mainTransformGroup.setTransform(mainTransform3D);
        angle += delta;

        Point3d eye = new Point3d(cameraDistance, cameraDistance, cameraHeight); // spectator's eye
        Point3d center = new Point3d(.0f, .0f ,0.5f); // sight target
        Vector3d up = new Vector3d(.0f, .0f, 1.0f);; // the camera frustum
        viewingTransform.lookAt(eye, center, up);
        viewingTransform.invert();
        viewingTransformGroup.setTransform(viewingTransform);
    }
}
