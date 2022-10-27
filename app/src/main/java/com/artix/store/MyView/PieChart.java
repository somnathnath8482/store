package com.artix.store.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.artix.store.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PieChart extends View implements View.OnClickListener{


    private  float TextMarginTop;
    private  float TextMarginLeft;
    private  String Text;
    private boolean mShowText;
    private int textPos = 0;
    private int layWidth = 0;
    private Paint textPaint;
    private Paint piePaint;
    private Paint shadowPaint;
    private float textHeight=0;
    private int mTextWidth =0;
    private float diameter;
    private int minw;
    private int w;
    private int minh;
    private int h;
    private float xpad;
    private float ypad;
    List<PiData> piData = new ArrayList<>();
    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        super.setOnClickListener(this);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PieChart,
                0, 0);

        try {
            mShowText = a.getBoolean(R.styleable.PieChart_showText, false);
            textPos = a.getInteger(R.styleable.PieChart_labelPosition, 0);
            textHeight  = a.getDimension(R.styleable.PieChart_TextSize, 0);
            Text  = a.getString(R.styleable.PieChart_Text);
            TextMarginTop  = a.getDimension(R.styleable.PieChart_TextMarginTop,650);
            TextMarginLeft  = a.getDimension(R.styleable.PieChart_TextMarginLeft,0);
        } finally {
            a.recycle();
        }

        init();

    }

    public boolean isShowText() {
        return mShowText;
    }

    public void setShowText(boolean showText) {
        mShowText = showText;
        invalidate();
        requestLayout();
    }


 public void SetList(List<PiData>  piData ) {
        this.piData = piData;
        invalidate();
        requestLayout();
    }





    ///-----------------------------------------------------------//


    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

                 //RectF cc = new RectF(0, 0, w,h-(h-w));
                 RectF cc = calculateBounds();
        //canvas.drawRect(cc,piePaint);
        /*canvas.drawArc(cc,
                0,
                90,
                true, piePaint);
        piePaint.setColor(getResources().getColor(R.color.purple_200));

        canvas.drawArc(cc,
                90,
                90,
                true, piePaint);
        piePaint.setColor(getResources().getColor(R.color.teal_700));
        canvas.drawArc(cc,
                180,
                90,
                true, piePaint);
        piePaint.setColor(getResources().getColor(R.color.purple_700));
        canvas.drawArc(cc,
                270,
                90,
                true, piePaint);*/
        //textPaint.setTextAlign();
        int total  = 0;
        for (int i=0;i<piData.size();i++){
            PiData item = piData.get(i);
            total+=item.getPoint();
        }
        float previous_ang=0;
         for (int i=0;i<piData.size();i++){
            PiData item = piData.get(i);
        float toAnge = (float)item.getPoint()/total*200;

            //total+=item.getPoint();
             Random rnd = new Random();
             int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
             item.setColor(color);
             piePaint.setColor(color);
             canvas.drawArc(cc,
                     previous_ang,
                     previous_ang+toAnge,
                     true, piePaint);

             Path path = new Path();
             path.addArc(cc, previous_ang,
                     60);
             canvas.drawTextOnPath(item.getTitle(), path, 20, 20, textPaint);
             previous_ang = previous_ang+toAnge;
        }


        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        piePaint.setColor(color);
        canvas.drawArc(cc,
                previous_ang,
                360-previous_ang,
                true, piePaint);






    }





    private RectF calculateBounds() {
        int availableWidth  = getWidth() - getPaddingLeft() - getPaddingRight();
        int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        int sideLength = Math.min(availableWidth, availableHeight);

        float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
        float top = getPaddingTop() + (availableHeight - sideLength) / 2f;

        return new RectF(left, top, left + sideLength, top + sideLength);
    }


    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(getResources().getColor(R.color.white));
        ///textPaint.setTextAlign(Paint.Align.RIGHT);
        if (textHeight == 0) {
            textHeight = textPaint.getTextSize();
        } else {
            textPaint.setTextSize(textHeight);
        }

        piePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        piePaint.setStyle(Paint.Style.FILL);
        piePaint.setTextSize(textHeight);
        piePaint.setColor(getResources().getColor(com.labters.lottiealertdialoglibrary.R.color.red_app));

        shadowPaint = new Paint(0);
        shadowPaint.setColor(0xff101010);
        shadowPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        xpad = (float)(getPaddingLeft() + getPaddingRight());
        ypad = (float)(getPaddingTop() + getPaddingBottom());

        // Account for the label
        if (mShowText) xpad += mTextWidth;

        float ww = (float)w - xpad;
        float hh = (float)h - ypad;

        // Figure out how big we can make the pie.
        diameter = Math.min(ww, hh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try for a width based on our minimum
        minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        w  = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        minh = MeasureSpec.getSize(w) - (int)mTextWidth + getPaddingBottom() + getPaddingTop();
        h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        h = h==0?550:h;
        w = w==0?500:w;
        setMeasuredDimension(w, h);
    }

}
