package it.unibo.coffebreak.view.api.resources;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;

public interface Resource {
    BufferedImage loadImage(String path) throws IOException;

    Font loadFont(String path, float size) throws IOException, FontFormatException;

}
