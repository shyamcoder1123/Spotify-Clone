package com.example.newspotifyclone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.example.newspotifyclone.R;

public class MusicVisualizerView extends View {
    private Paint linePaint;
    private int[] lineHeights;
    private int lineCount = 20;
    private int lineHeightIncrement = 10;
    private Handler handler = new Handler();
    private Runnable animationRunnable;

    public MusicVisualizerView(Context context) {
        super(context);
        init();
    }

    public MusicVisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();

        linePaint.setColor(getResources().getColor(R.color.visualizer_line_color));
        linePaint.setStrokeWidth(getResources().getDimension(R.dimen.visualizer_line_width));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        if (lineHeights == null) {
            lineHeights = new int[lineCount];
            for (int i = 0; i < lineCount; i++) {
                lineHeights[i] = height / 2;
            }
        }

        for (int i = 0; i < lineCount; i++) {
            float startX = i * (linePaint.getStrokeWidth() * 2);
            float startY = height / 2;
            float stopX = startX;
            float stopY = startY - lineHeights[i];

            canvas.drawLine(startX, startY, stopX, stopY, linePaint);
        }
    }

    public void startAnimation() {
        animationRunnable = new Runnable() {
            @Override
            public void run() {
                updateLineHeights();
                invalidate();
                handler.postDelayed(this, 200); // Adjust the delay for smoother or faster animation
            }
        };
        handler.post(animationRunnable);
    }

    public void stopAnimation() {
        if (animationRunnable != null) {
            handler.removeCallbacks(animationRunnable);
            animationRunnable = null;
        }
    }

    private void updateLineHeights() {
        // Implement logic to update line heights based on audio data or any other source.
        // This is where you would update the lineHeights array with real audio data.
        // For a real application, you may want to use an audio visualization library.
        // Here's a simplified example:

        for (int i = 0; i < lineCount; i++) {
            lineHeights[i] += lineHeightIncrement;
            if (lineHeights[i] > getHeight() / 2) {
                lineHeights[i] = 0;
            }
        }
    }
}


//public class VisualizerView extends View {
//    private Paint paint;
//    private float[] amplitudes;
//    private int numOfLines = 6; // Number of lines in the visualizer
//    int lineSpacing=4;
//    private Random random = new Random();
//
//    public VisualizerView(Context context) {
//        super(context);
//        init();
//    }
//
//    public VisualizerView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    private void init() {
//        paint = new Paint();
//        paint.setColor(Color.GREEN);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5);
//    }
//
//    public void setAmplitudes(float[] amplitudes) {
//        this.amplitudes = amplitudes;
//        invalidate();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        if (amplitudes == null) {
//            return;
//        }
//
//        int width = getWidth();
//        int height = getHeight();
//        int lineHeight = height / 3;
//
//        for (int i = 0; i < numOfLines; i++) {
//            float amplitude = amplitudes[i % amplitudes.length] * 3; // Scale the amplitude
//            float x = (width+lineSpacing) * i / numOfLines;
//            float startY = height / 2 - amplitude * height / 2;
//            float endY = height / 2 + amplitude * height / 2;
//
//            paint.setColor(getRandomColor());
//            canvas.drawLine(x, startY, x, endY, paint);
//        }
//    }
//
//    private int getRandomColor() {
//        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
//    }
//}

