package com.example.seekbarexample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

public class SlideSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    /**  Factor */
    private float factor = 1;
    public float getFactor(){return factor;}
    public void setFactor(float factor){this.factor = factor;}

    /** Constructors */
    public SlideSeekBar(Context context) {
        super(context);
        this.customiseOnTouch();
    }

    public SlideSeekBar(Context context, AttributeSet attrs){
        super(context, attrs);
        this.customiseOnTouch();
    }

    public SlideSeekBar(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs);
        this.customiseOnTouch();
    }

    /** Touch gesture */
    private OnSeekBarChangeListener onSlideSeekBarChangeListener;
    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener){
        this.onSlideSeekBarChangeListener = onSeekBarChangeListener;

        super.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!b)
                    onSlideSeekBarChangeListener.onProgressChanged(seekBar, i, b);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    };

    public void customiseOnTouch(){
        this.tapGestureListener = new GestureDetector(this.getContext(), new TapGestureListener());
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //  Start tracking
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    onSlideSeekBarChangeListener.onStartTrackingTouch(SlideSeekBar.this);}

                //  Stop tracking
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    onSlideSeekBarChangeListener.onStopTrackingTouch(SlideSeekBar.this);

                //  Progress change
                tapGestureListener.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    GestureDetector tapGestureListener;
    private class TapGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e){
            float percentageMoved = e.getX()/(float)(SlideSeekBar.this.getRight()-SlideSeekBar.this.getLeft());
            int newProgress = (int)(percentageMoved*SlideSeekBar.this.getMax());

            if (newProgress == SlideSeekBar.this.getProgress())
                return true;

            SlideSeekBar.this.setProgress(newProgress);
            onSlideSeekBarChangeListener.onProgressChanged(SlideSeekBar.this, getProgress(), true);
            return true;
        }

        public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
            float percentageMoved = -distanceX/(float)(SlideSeekBar.this.getRight()-SlideSeekBar.this.getLeft());
            int newProgress = SlideSeekBar.this.getProgress() + (int)(percentageMoved*SlideSeekBar.this.getMax()*factor);

            if (newProgress == SlideSeekBar.this.getProgress())
                return true;

            SlideSeekBar.this.setProgress(newProgress);
            onSlideSeekBarChangeListener.onProgressChanged(SlideSeekBar.this, getProgress(), true);
            return true;
        }
    }
}
