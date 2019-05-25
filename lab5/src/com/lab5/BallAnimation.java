package com.lab5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

public class BallAnimation implements ActionListener, KeyListener {
    private static final float speed = 0.01f;
    private static final float rotationSpeed = 0.03f;
    private TransformGroup ball;
    private Transform3D transform3D = new Transform3D();

    private boolean w = false;
    private boolean s = false;
    private boolean a = false;
    private boolean d = false;
    private boolean space = false;

    private float x = 0;
    private float y = 0; private int angle = 0;

    private float radius = 1f;
    private double scale = 1;
    private double delta = 0.01;

    public BallAnimation(TransformGroup ball) {
        this.ball = ball;
        this.ball.getTransform(this.transform3D);

        x = (float)(radius * Math.sin(Math.toRadians(angle)));
        y = (float)(-radius * Math.cos(Math.toRadians(angle)));
        transform3D.setTranslation(new Vector3f(x, y, 0));
        ball.setTransform(transform3D);

        System.out.println(ball.getBounds());

        Timer timer = new Timer(10, this);
        timer.start();
    }

    private void Move() {
  	  	if (w) {
  	  		y += speed;
  	  	} else if (s) {
			y -= speed;
	  	} else if (a) {
			x -= speed;
        } else if (d) {
        	x += speed;
        } else if (space) {
            Transform3D rotation = new Transform3D();
            rotation.rotX(-rotationSpeed);
            transform3D.mul(rotation);

            transform3D.setTranslation(new Vector3f(x, y, 0));

            if ( scale < 0.01 ) {
                delta = -delta;
            } else if (scale > 0.99) {
                delta = -delta;
            }
            scale += delta;

            // circular movement
            x = (float)(radius * Math.sin(Math.toRadians(angle)));
            y = (float)(-radius * Math.cos(Math.toRadians(angle)));
            angle++;
        }
        transform3D.setTranslation(new Vector3f(x, y, 0));
        ball.setTransform(transform3D);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	Move();
        System.out.println(ball.getBounds());
    }
    
    @Override
    public void keyPressed(KeyEvent ev) {
    	switch (ev.getKeyChar()) {
	    	case 'w': w = true; break;
	    	case 's': s = true; break;
	    	case 'a': a = true; break;
	    	case 'd': d = true; break;
            case ' ': space = true; break;
		}
    }
    
    @Override
    public void keyReleased(KeyEvent ev) {
    	switch (ev.getKeyChar()) {
	    	case 'w': w = false; break;
	    	case 's': s = false; break;
	    	case 'a': a = false; break;
	    	case 'd': d = false; break;
            case ' ': space = false; break;
    	}
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //just do nothing please
    }
}
