package com.example.wei.mywuziqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Wei on 2017/6/9.
 */

public class wuziqiview extends View {
    private Paint mPaint = new Paint();
    private float mLineheight;
    private int MAX_LINE = 10;
    private int MAX_COUNT = 5;
    private int mPanelWidth;
    private Bitmap blackPiece;
    private Bitmap whilePiece;
    private float rationPieceOfLineHeight = 3 * 1.0f / 4;
    private boolean isWhite;
    private ArrayList<Point> mwhite = new ArrayList<>();
    private ArrayList<Point> mblack = new ArrayList<>();
    private boolean IsGameOver;
    private boolean IsWhileWinner;

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
        blackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
        whilePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (IsGameOver) {
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = getvilePoint(x, y);
            if (mwhite.contains(p) || mblack.contains(p)) {
                return false;
            }
            if (isWhite) {
                mwhite.add(p);
            } else {
                mblack.add(p);
            }
            invalidate();
            isWhite = !isWhite;
            return true;
        }
        return true;
    }

    private Point getvilePoint(int x, int y) {
        return new Point((int) (x / mLineheight), (int) (y / mLineheight));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = Math.min(widthSize, heightSize);
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;
        mLineheight = mPanelWidth * 1.0f / MAX_LINE;
        //通过特定的比例对棋子进行缩放
        int pieceWidth = (int) (mLineheight * rationPieceOfLineHeight);
        whilePiece = Bitmap.createScaledBitmap(whilePiece, pieceWidth, pieceWidth, false);
        blackPiece = Bitmap.createScaledBitmap(blackPiece, pieceWidth, pieceWidth, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawbload(canvas);
        drawPieces(canvas);
        checkGameOver();
    }

    private void checkGameOver() {
        boolean whiteWin = checkFiveLine(mwhite);
        boolean blackWin = checkFiveLine(mwhite);
        if (whiteWin || blackWin) {
            IsGameOver = true;
            IsWhileWinner = whiteWin;
            String text = IsWhileWinner ? "白棋胜利" : "黑棋胜利";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFiveLine(ArrayList<Point> mwhite) {
        for (Point p : mwhite) {
            int x = p.x;
            int y = p.y;
            boolean win = checkHorizonal(x, y, mwhite);
            if (win){
                return true;
            }
            win=checkVarcail(x,y,mwhite);
            if (win){
                return true;
            }
            win=checkLeftLine(x,y,mwhite);
            if (win){
                return true;
            }
            win=checkRightLine(x,y,mwhite);
            if (win){
                return true;
            }
        }

        return false;
    }

    private boolean checkHorizonal(int x, int y, ArrayList<Point> mwhite) {
        int count = 1;
        for (int i = 1; i < MAX_COUNT; i++) {
            if (mwhite.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for (int i = 1; i < MAX_COUNT; i++) {
            if (mwhite.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        return false;
    }
    private boolean checkVarcail(int x, int y, ArrayList<Point> mwhite) {
        int count = 1;
        for (int i = 1; i < MAX_COUNT; i++) {
            if (mwhite.contains(new Point(x , y- i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for (int i = 1; i < MAX_COUNT; i++) {
            if (mwhite.contains(new Point(x , y+ i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        return false;
    }
    private boolean checkLeftLine(int x, int y, ArrayList<Point> mwhite) {
        int count = 1;
        for (int i = 1; i < MAX_COUNT; i++) {
            if (mwhite.contains(new Point(x +i, y- i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for (int i = 1; i < MAX_COUNT; i++) {
            if (mwhite.contains(new Point(x -i, y+i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        return false;
    }
    private boolean checkRightLine(int x, int y, ArrayList<Point> mwhite) {
        int count = 1;
        for (int i = 1; i < MAX_COUNT; i++) {
            if (mwhite.contains(new Point(x +i, y+ i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for (int i = 1; i < MAX_COUNT; i++) {
            if (mwhite.contains(new Point(x -i, y-i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        return false;
    }
    private void drawPieces(Canvas canvas) {
        for (int i = 0; i < mwhite.size(); i++) {
            Point whithPoint = mwhite.get(i);
            canvas.drawBitmap(whilePiece,
                    (whithPoint.x + (1 - rationPieceOfLineHeight) / 2) * mLineheight,
                    (whithPoint.y + (1 - rationPieceOfLineHeight) / 2) * mLineheight, null);
        }
        for (int i = 0; i < mblack.size(); i++) {
            Point blackPoint = mblack.get(i);
            canvas.drawBitmap(blackPiece,
                    (blackPoint.x + (1 - rationPieceOfLineHeight) / 2) * mLineheight,
                    (blackPoint.y + (1 - rationPieceOfLineHeight) / 2) * mLineheight, null);
        }
    }

    private void drawbload(Canvas canvas) {
        //整个棋盘的宽
        int w = mPanelWidth;
        float lineHeight = mLineheight;
        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int entX = (int) (w - lineHeight / 2);
            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, entX, y, mPaint);
            canvas.drawLine(y, startX, y, entX, mPaint);
        }
    }


}
