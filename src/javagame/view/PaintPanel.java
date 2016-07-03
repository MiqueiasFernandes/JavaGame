/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author mfernandes
 */
public class PaintPanel extends JPanel {

    private Image image;
    private MediaTracker media;

    public PaintPanel(Image image) {
        this.image = image;
        media = new MediaTracker(this);

    }

    void addImageTraker() {
        try {
            media.addImage(image, 0);
            media.waitForID(0);
        } catch (InterruptedException ex) {
            System.err.println("erro ao pintar frame: " + ex);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void setImage(Image image) {
        this.image = image;
        addImageTraker();
        this.invalidate();
        this.repaint();
    }
}
