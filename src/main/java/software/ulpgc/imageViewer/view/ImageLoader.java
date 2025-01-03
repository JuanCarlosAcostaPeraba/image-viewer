package software.ulpgc.imageViewer.view;

import software.ulpgc.imageViewer.model.Image;

import java.util.List;

public interface ImageLoader {
    List<Image> load();
}