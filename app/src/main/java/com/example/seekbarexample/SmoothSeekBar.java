package com.example.seekbarexample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.SeekBar;

import androidx.appcompat.widget.AppCompatSeekBar;

import java.util.logging.Logger;

public class SmoothSeekBar extends AppCompatSeekBar implements SeekBar.OnSeekBarChangeListener {

    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;

    private boolean mNeedCallListener = true;

    private ValueAnimator mAnimator;

    public SmoothSeekBar(Context context) {
        super(context);
        init(context);
    }

    public SmoothSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SmoothSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void init(Context context) {
        Context mContext = context;
    }

    @Override
    public void setOnSeekBarChangeListener(
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        mOnSeekBarChangeListener = onSeekBarChangeListener;
        super.setOnSeekBarChangeListener(this);
    }

    public void setProgress(final int progress, Drawable thumb) {

    }

    @Override
    public void setProgress(final int progress) {
        super.setProgress(progress);
        /*final int currentProgress = getProgress();
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator.removeAllUpdateListeners();
            mAnimator.removeAllListeners();
            mAnimator = null;
            mNeedCallListener = true;
        }
        mAnimator = ValueAnimator.ofInt(currentProgress, progress);
        mAnimator.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                if (value == progress) {
                    mNeedCallListener = true;
                } else {
                    mNeedCallListener = false;
                }
                SmoothSeekBar.super.setProgress(value);
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();*/
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser || mNeedCallListener) {
            if (mOnSeekBarChangeListener != null) {
                mOnSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onStartTrackingTouch(seekBar);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onStopTrackingTouch(seekBar);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                final int width = getWidth();
                final int available = width - getPaddingLeft() - getPaddingRight();
                int x = (int) event.getX();
                float scale;
                float progress = 0;
                if (x < getPaddingLeft()) {
                    scale = 0.0f;
                } else if (x > width - getPaddingRight()) {
                    scale = 1.0f;
                } else {
                    scale = (float) (x - getPaddingLeft()) / (float) available;
                }
                final int max = getMax();
                progress += scale * max;
                if (progress < 0) {
                    progress = 0;
                } else if (progress > max) {
                    progress = max;
                }

                if (Math.abs(progress - getProgress()) < 10)
                    return super.onTouchEvent(event);
                else
                    return false;
            default:
                return super.onTouchEvent(event);
        }
    }
}