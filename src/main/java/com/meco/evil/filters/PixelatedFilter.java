package com.meco.evil.filters;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class PixelatedFilter implements Filter {

  private int pixelationStrength;

  public PixelatedFilter() {
    this(10);
  }

  public PixelatedFilter(int pixelationStrength) {
    this.pixelationStrength = pixelationStrength;
  }

  private Color getAverageFromBlock(BufferedImage img, int startX, int startY) {
    int r = 0, g = 0, b = 0;

    for (int i = 0; i < pixelationStrength; i++) {
      for (int j = 0; j < pixelationStrength; j++) {
        int x = j + startX;
        int y = i + startY;
        if(x >= img.getWidth() || y >= img.getHeight()) {
          continue;
        }
        Color color = new Color(img.getRGB(x, y));
        r += color.getRed();
        g += color.getGreen();
        b += color.getBlue();
      }

    }

    int s = pixelationStrength * pixelationStrength;
    return new Color(r / s, g / s, b / s);
  }

  private void fillBlock(BufferedImage img, Color color, int startX, int startY) {
    for (int i = 0; i < pixelationStrength; i++) {
      for (int j = 0; j < pixelationStrength; j++) {
        int x = j + startX;
        int y = i + startY;
        if(x >= img.getWidth() || y >= img.getHeight()) {
          continue;
        }
        img.setRGB(x, y, color.getRGB());
      }
    }
  }

  @Override
  public void applyFilter(BufferedImage img) {
    for (int w = 0; w < img.getWidth(); w += pixelationStrength) {
      for (int h = 0; h < img.getHeight(); h += pixelationStrength) {
        Color color = getAverageFromBlock(img, w, h);
        fillBlock(img, color, w, h);
      }
    }
  }
}
