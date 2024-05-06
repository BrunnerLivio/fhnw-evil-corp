package com.meco.evil.filters;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class BlackAndWhiteFilter implements Filter {
    @Override
    public void applyFilter(BufferedImage img) {

        for (int w = 0; w < img.getWidth(); w++) {
            for (int h = 0; h < img.getHeight(); h++) {
                Color color = new Color(img.getRGB(w, h));

                double r = color.getRed();
                double g = color.getGreen();
                double b = color.getBlue();

                double avg = (r + g + b) / 3;
                Color newColor = Color.WHITE;
                if (avg >= 128) {
                    newColor = Color.BLACK;
                }
                img.setRGB(w, h, newColor.getRGB());
            }
        }
    }

}