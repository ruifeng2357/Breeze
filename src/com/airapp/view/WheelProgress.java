package com.airapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class WheelProgress extends View{
    public static int MODE_FILL = 0;
    public static int MODE_DOT = 1;
    public int mMode = MODE_FILL;

    private int layout_height = 0;
    private int layout_width = 0;
    private int outsideWidth = 8;
    private int insideWidth = 8;

    private int paddingTop = 5;
    private int paddingBottom = 5;
    private int paddingLeft = 5;
    private int paddingRight = 5;

    private int fillColor = 0xFF2DA8DE;
    private int borderColor = 0xA084CEEA;
    private int backColor = 0xA084CEEA;

    private Paint fillPaint = new Paint();
    private Paint backPaint = new Paint();
    private Paint borderPaint = new Paint();

    @SuppressWarnings("unused")
    private RectF circleBounds = new RectF();
    private RectF circleOuterContour = new RectF();
    private RectF circleInnerContour = new RectF();

    int drawDelay = 0;
    int progress = 0;
    boolean isRunning = false;

    OnCompleteListener mOnCompleteListener;
    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            mOnCompleteListener.onComplete();
        }
    };

    /*
        Define Listener
     */
    public interface OnCompleteListener {
        public void onComplete();
    }

    public WheelProgress(Context context, AttributeSet attrs) {
        super(context, attrs);

        drawDelay = 10;
        isRunning = false;
        mMode = MODE_FILL;

        mOnCompleteListener = null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        if (widthWithoutPadding > heigthWithoutPadding) {
            size = heigthWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        layout_width = w;
        layout_height = h;

        setupBounds();
        setupPaints();
        invalidate();
    }

    private void setupPaints() {
        fillPaint.setColor(fillColor);
        fillPaint.setAntiAlias(true);
        fillPaint.setStyle(Style.STROKE);
        fillPaint.setStrokeWidth(outsideWidth);

        backPaint.setColor(backColor);
        backPaint.setAntiAlias(true);
        backPaint.setStyle(Style.STROKE);
        backPaint.setStrokeWidth(insideWidth);

        borderPaint.setColor(borderColor);
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Style.STROKE);
    }

    private void setupBounds() {
        int minValue = Math.min(layout_width, layout_height);

        int xOffset = layout_width - minValue;
        int yOffset = layout_height - minValue;

        paddingTop = this.getPaddingTop() + (yOffset / 2);
        paddingBottom = this.getPaddingBottom() + (yOffset / 2);
        paddingLeft = this.getPaddingLeft() + (xOffset / 2);
        paddingRight = this.getPaddingRight() + (xOffset / 2);

        int width = getWidth(); //this.getLayoutParams().width;
        int height = getHeight(); //this.getLayoutParams().height;

        circleBounds = new RectF(paddingLeft + outsideWidth,
                paddingTop + outsideWidth,
                width - paddingRight - outsideWidth,
                height - paddingBottom - outsideWidth);

        circleInnerContour = new RectF(circleBounds.left + (insideWidth / 2.0f),
                                            circleBounds.top + (insideWidth / 2.0f),
                                            circleBounds.right - (insideWidth / 2.0f),
                                            circleBounds.bottom - (insideWidth / 2.0f));

        circleOuterContour = new RectF(circleBounds.left - (insideWidth / 2.0f),
                                            circleBounds.top - (insideWidth / 2.0f),
                                            circleBounds.right + (insideWidth / 2.0f),
                                            circleBounds.bottom + (insideWidth / 2.0f));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(circleBounds, 360, 360, false, backPaint);
        canvas.drawArc(circleOuterContour, 360, 360, false, borderPaint);
        canvas.drawArc(circleInnerContour, 360, 360, false, borderPaint);
        if (mMode == MODE_FILL)
            canvas.drawArc(circleBounds, -90, progress, false, fillPaint);
        else if (mMode == MODE_DOT)
            canvas.drawArc(circleBounds, progress - 90, 30, false, fillPaint);
    }

    public void resetCount() {
        progress = 0;
        invalidate();
    }

    public void incrementProgress() {
        progress++;
        if (progress > 360) {
            if (mMode == MODE_FILL)
                isRunning = false;
        }
        postInvalidate();
    }

    public void setPeriod(int miliSecond)
    {
        if (miliSecond <= 0)
            drawDelay = 0;
        else
            drawDelay = miliSecond / 360;
    }

    final Runnable runDraw = new Runnable() {
        public void run() {
            while(isRunning == true) {
                incrementProgress();
                try {
                    Thread.sleep(drawDelay);
                } catch (Exception ex) {}
            }

            if (progress > 360)
                handler.sendEmptyMessage(0);
        }
    };

    public void startDraw()
    {
        isRunning = true;
        progress = 0;
        resetCount();
        Thread thread = new Thread(runDraw);
        thread.start();
    }

    public void stopDraw()
    {
        postInvalidate();
        isRunning = false;
    }

    public void setRepeatMode(int mode)
    {
        if (mode == 0)
            mMode = MODE_FILL;
        else
            mMode = MODE_DOT;
    }

    public void setBorderColor (int color) { borderColor = color; }

    public void setFillColor (int color) { fillColor = color; }

    public void setBackColor (int color) { backColor = color; }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.mOnCompleteListener = onCompleteListener;
    }
}
