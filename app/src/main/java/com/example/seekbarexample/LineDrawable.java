package com.example.seekbarexample;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

class LineDrawable extends Drawable {
    private Paint mPaint;

    public LineDrawable() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
    }

    @Override
    public void draw(Canvas canvas) {
        int lvl = getLevel();
        Rect b = getBounds();
        float x = b.width() * lvl / 10000.0f;
        float y = (b.height() - mPaint.getStrokeWidth()) / 2;
        mPaint.setColor(Color.parseColor("#000000"));
        canvas.drawLine(0, y, x, y, mPaint);
        mPaint.setColor(Color.parseColor("#000000"));
        canvas.drawLine(x, y, b.width(), y, mPaint);
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}