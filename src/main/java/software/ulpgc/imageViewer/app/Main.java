package software.ulpgc.imageViewer.app;

import software.ulpgc.imageViewer.model.Image;
import software.ulpgc.imageViewer.presenter.ImagePresenter;
import software.ulpgc.imageViewer.view.FileImageLoader;
import software.ulpgc.imageViewer.view.ImageDisplay;
import software.ulpgc.imageViewer.view.ImagePanel;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class Main extends JFrame {
    private ImageDisplay imageDisplay;
    private final ImagePresenter imagePresenter;

    public static void main(String[] args) {
        new Main().execute();
    }

    public Main() {
        this.setTitle("Image Viewer");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(imagePanel());

        List<Image> images = new FileImageLoader(new File("images")).load();
        imagePresenter = new ImagePresenter(images, imageDisplay);
    }

    private void execute() {
        this.setVisible(true);
    }

    private JPanel imagePanel() {
        ImagePanel panel = new ImagePanel();
        this.imageDisplay = panel;
        return panel;
    }
}