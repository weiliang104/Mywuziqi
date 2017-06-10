package com.example.wei.mywuziqi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Wei on 2017/6/9.
 */

public class wuziqiview extends View {
    private Paint mPaint=new Paint();
    private float mLineheight;
    private  int MAX_LINE=10;
    private int mPanelWidth;
    public wuziqiview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setBackgroundColor(0x44ff0000);
    }

    private void init() {
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int width =Math.min(widthSize,heightSize);
        if (widthMode==MeasureSpec.UNSPECIFIED){
            width=heightSize;
        }else if (heightMode==MeasureSpec.UNSPECIFIED){
            width=widthSize;
        }
       setMeasuredDimension(width,width);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth=w;
        mLineheight=mPanelWidth*1.0f/MAX_LINE;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawbload(canvas);

    }

    private void drawbload(Canvas canvas) {
        //整个棋盘的宽
        int w=mPanelWidth;
        float lineHeight=mLineheight;
        for (int i=0;i<MAX_LINE;i++){
            int startX= (int) (lineHeight/2);
            int entX= (int) (w-lineHeight/2);
            int y= (int) ((0.5+i)*lineHeight);
            canvas.drawLine(startX,y,entX,y,mPaint);
            canvas.drawLine(y,startX,y,entX,mPaint);
        }
    }



}
