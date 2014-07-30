/*
* Copyright (C) 2014 Simon Norberg
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package net.simno.dmach.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public abstract class PdView extends View {

    protected static final int SHAPE_STROKE_WIDTH = 4;
    protected static final int TEXT_COLOR = Color.parseColor("#302E2C");
    protected static final int TEXT_SIZE = 24;

    protected float mShapeStrokeWidth;
    protected float mTextSize;
    protected Paint mShapePaint;
    protected Paint mTextPaint;

    protected DisplayMetrics mDm;

    public PdView(Context context) {
        super(context);
        init();
    }

    public PdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PdView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init() {
        mDm = getContext().getResources().getDisplayMetrics();
        mShapeStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SHAPE_STROKE_WIDTH, mDm);
        mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE, mDm);

        mShapePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShapePaint.setStrokeWidth(mShapeStrokeWidth);
        mShapePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(TEXT_COLOR);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStyle(Paint.Style.FILL);
        Typeface saxmono = Typeface.createFromAsset(getContext().getAssets(), "fonts/saxmono.ttf");
        mTextPaint.setTypeface(saxmono);
    }

    protected abstract float getMinX();

    protected abstract float getMinY();

    protected abstract float getMaxX();

    protected abstract float getMaxY();

    protected final float getValidX(float x) {
        float min = getMinX();
        if (x < min) {
            return min;
        }
        float max = getMaxX();
        if (x > max) {
            return max;
        }
        return x;
    }

    protected final float getValidY(float y) {
        float min = getMinY();
        if (y < min) {
            return min;
        }
        float max = getMaxY();
        if (y > max) {
            return max;
        }
        return y;
    }

    protected final float pdToX(float pdX) {
        float width = getMaxX() - getMinX();
        return (pdX * width) + getMinX();
    }

    protected final float pdToY(float pdY) {
        float height = getMaxY() - getMinY();
        return ((1 - pdY) * height) + getMinY();
    }

    protected final float xToPd(float x) {
        float width = getMaxX() - getMinX();
        float pdX = x - getMinX();
        if (pdX > 0) {
            pdX /= width;
        }
        return pdX;
    }

    protected final float yToPd(float y) {
        float height = getMaxY() - getMinY();
        float pdY = y - getMinY();
        if (pdY > 0) {
            pdY /= height;
        }
        return 1 - pdY;
    }
}
