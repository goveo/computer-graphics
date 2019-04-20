package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;

@SuppressWarnings("serial")
public class Skeleton extends JPanel implements ActionListener {
    private static int maxWidth;
    private static int maxHeight;

    Timer timer;
    int delay = 50;

    // Для анімації масштабування
    private double scale = 1;
    private double delta = 0.01;

    // Для анімації руху
    private double tx = 1;
    private double ty = 1;
    private int angle = 0;

    private int radius = 180;

    public Skeleton() {
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        // Set background color.
        g2d.setBackground(new Color(0, 0, 128));
        g2d.clearRect(0, 0, maxWidth, maxHeight);

        // frame
        g2d.translate(maxWidth/2, maxHeight/2);
        BasicStroke bs2 = new BasicStroke(20, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
        g2d.setStroke(bs2);
        g2d.drawRect(-radius * 2,-radius * 2,radius * 4,radius * 4);

        g2d.translate(tx, ty);

        // Перетворення для анімації масштабу
        g2d.scale(scale, scale);

        // house body.
        g2d.setColor(new Color(128, 0, 0));
        g2d.fillRect(-135, -25, 270, 100);

        // Set roof
        double roofPoints[][] = {
                { -135, -25 }, { 135, -25 }, { -10, -80 },
        };

        GeneralPath roof = new GeneralPath();
        roof.moveTo(roofPoints[0][0], roofPoints[0][1]);
        for (int k = 1; k < roofPoints.length; k++)
            roof.lineTo(roofPoints[k][0], roofPoints[k][1]);
        roof.closePath();

        GradientPaint gp = new GradientPaint(
                -50, 25,
                new Color(128, 128, 128),
                50, 2,
                new Color(0, 0, 0),
                true
        );
        g2d.setPaint(gp);
        g2d.fill(roof);

        // windows
        g2d.setColor(Color.yellow);

        g2d.fillRect(-75, 0,35,35);
        g2d.fillRect(5, 0,35,35);

        // stars
        g2d.fillRect(-170, -100,10,10);
        g2d.fillRect(-120, -120,10,10);
        g2d.fillRect(35, -100,10,10);
        g2d.fillRect(80, -120,10,10);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(new Skeleton());

        frame.setVisible(true);

        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth = size.width - insets.left - insets.right;
        maxHeight = size.height - insets.top - insets.bottom;
    }

    public void actionPerformed(ActionEvent e) {
        if ( scale < 0.01 ) {
            delta = -delta;
        } else if (scale > 0.99) {
            delta = -delta;
        }
        scale += delta;

        // circular movement
        tx = radius * Math.cos(Math.toRadians(angle));
        ty = -radius * Math.sin(Math.toRadians(angle));
        angle++;

        repaint();
    }
}