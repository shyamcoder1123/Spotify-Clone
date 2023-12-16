package com.example.newspotifyclone.helper;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.ImageView;

public class RandomLinearGradient {

    private static int gradientColors;
    public static void setRandomLinearGradient(ImageView imageView){
        int startColor = getRandomColor();
        int endColor = getRandomColor();

        gradientColors = (startColor << 16) | endColor;
        imageView.setColorFilter(gradientColors);

    }
    public static void setRandomLinearGradientWithThreeColors(ImageView imageView){
        int startColor = getRandomColor();
        int middleColor = getRandomColor();
        int endColor = getRandomColor();

        int gradientColors = (startColor << 16) | (middleColor << 8) | endColor;
        imageView.setColorFilter(gradientColors, PorterDuff.Mode.SRC_ATOP);

    }
    private static int getRandomColor(){
        return Color.rgb((int) (Math.random()*256),(int) (Math.random()*256),(int) (Math.random()*256));
    }
    public static int getGradientColors() {
        return gradientColors;
    }

    public static void setGradientColors(int gradientColors) {
        RandomLinearGradient.gradientColors = gradientColors;
    }
}
