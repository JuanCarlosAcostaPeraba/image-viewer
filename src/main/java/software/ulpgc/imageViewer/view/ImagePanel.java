package software.ulpgc.imageViewer.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel implements ImageDisplay {
    private String current;
    private String future;
    private BufferedImage image;
    private BufferedImage futureImage;
    private int offset = 0;
    private Shift shift = new Shift.Null();

    public ImagePanel() {
        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (image != null) {
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();
            double imgAspect = (double) imgWidth / imgHeight;
            double panelAspect = (double) getWidth() / getHeight();

            int drawWidth, drawHeight;
            if (imgAspect > panelAspect) {
                drawWidth = getWidth();
                drawHeight = (int) (getWidth() / imgAspect);
            } else {
                drawHeight = getHeight();
                drawWidth = (int) (getHeight() * imgAspect);
            }

            int x = (getWidth() - drawWidth) / 2;
            int y = (getHeight() - drawHeight) / 2;

            g.drawImage(image, x + offset, y, drawWidth, drawHeight, null);

            if (offset != 0 && futureImage != null) {
                int futureX = offset > 0
                        ? x - drawWidth + offset
                        : x + drawWidth + offset;
                g.drawImage(futureImage, futureX, y, drawWidth, drawHeight, null);
            }
        }
    }

    private void reset() {
        offset = 0;
        repaint();
    }

    private void toggle() {
        current = future;
        image = futureImage;
        offset = 0;
        repaint();
    }

    private void setOffset(int offset) {
        this.offset = offset;
        if (offset < 0) setFuture(shift.right());
        if (offset > 0) setFuture(shift.left());
        repaint();
    }

    @Override
    public void display(String name) {
        this.current = name;
        this.image = load(name);
        repaint();
    }

    public void setFuture(String name) {
        if (name.equals(future)) return;
        this.future = name;
        this.futureImage = load(name);
    }

    private BufferedImage load(String name) {
        try {
            return ImageIO.read(new File(name));
        } catch (IOException ex) {
            System.out.println("Can´t load " + name);
            return null;
        }
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift;
    }

    @Override
    public String current() {
        return this.current;
    }

    private class MouseHandler implements MouseListener, MouseMotionListener {
        private int initial;

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            this.initial = e.getX();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (Math.abs(offset) > getWidth() / 2)
                toggle();
            else
                reset();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            setOffset(e.getX() - initial);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

    }
}