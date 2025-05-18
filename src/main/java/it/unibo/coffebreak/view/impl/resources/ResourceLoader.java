package it.unibo.coffebreak.view.impl.resources;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import it.unibo.coffebreak.view.api.resources.Resource;

public final class ResourceLoader implements Resource {
    private static final Map<String, BufferedImage> IMAGE_CACHE = new HashMap<>();
    private static final Map<String, Font> FONT_CACHE = new HashMap<>();

    @Override
    public BufferedImage loadImage(final String path) throws IOException {
        return IMAGE_CACHE.computeIfAbsent(path, p -> {
            try (InputStream is = getClass().getResourceAsStream(p)) {
                if (is == null) {
                    throw new ResourceException("Resource not found: " + p);
                }
                final BufferedImage img = ImageIO.read(is);
                if (img == null) {
                    throw new ResourceException("Invalid image format: " + path);
                }
                return img;
            } catch (final IOException e) {
                throw new ResourceException("Failed to load image: " + p, e);
            }
        });

    }

    @Override
    public Font loadFont(final String path, final float size) throws IOException, FontFormatException {
        final String cacheKey = path + "|" + size;
        return FONT_CACHE.computeIfAbsent(cacheKey, k -> {
            try (InputStream is = getClass().getResourceAsStream(path)) {
                if (is == null) {
                    throw new ResourceException("Font not found: " + path);
                }
                return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
            } catch (FontFormatException | IOException e) {
                throw new ResourceException("Failed to load font: " + path, e);
            }
        });
    }

    public static void clearCache() {
        IMAGE_CACHE.values().forEach(BufferedImage::flush);
        IMAGE_CACHE.clear();
        FONT_CACHE.clear();
    }

    private static class ResourceException extends RuntimeException {
        ResourceException(final String message) {
            super(message);
        }

        ResourceException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
