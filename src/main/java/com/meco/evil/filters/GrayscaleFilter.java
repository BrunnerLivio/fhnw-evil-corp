package com.meco.evil.filters;

import java.awt.image.BufferedImage;
import java.awt.Color;


public class GrayscaleFilter implements Filter {

  @Override
  public void applyFilter(BufferedImage img) {
    
    for (int w = 0; w < img.getWidth(); w++) {
      for (int h = 0; h < img.getHeight(); h++) {
        Color color = new Color(img.getRGB(w, h));

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        float[] hsv = new float[3];
        Color.RGBtoHSB(r, g, b, hsv);
        Color newColor = Color.getHSBColor(hsv[0], 0, hsv[2]);
        img.setRGB(w, h, newColor.getRGB());
      }
    }
  }
}
