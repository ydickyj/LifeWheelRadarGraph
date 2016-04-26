package com.github.glomadrian.velocimeterlibrary.painter.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import com.github.glomadrian.velocimeterlibrary.utils.DimensionUtils;

/**
 * @author Adrián García Lomas
 */
public class ProgressVelocimeterPainterImp implements ProgressVelocimeterPainter {

  private RectF circle;
  protected Paint paint;
  private int color;
  private int startAngle = 150;
  private int width;
  private int height;
  private float plusAngle = 0;
  private float max;
  private int strokeWidth;
  private int blurMargin;
  private int lineWidth;
  private int lineSpace;
  private Context context;

  public ProgressVelocimeterPainterImp(int color, float max, int margin, Context context) {
    this.color = color;
    this.max = max;
    this.blurMargin = margin;
    this.context = context;
    initSize();
    init();
  }

  private void initSize() {
    this.lineWidth = DimensionUtils.getSizeInPixels(15, context);
    this.lineSpace = DimensionUtils.getSizeInPixels(2, context);
    this.strokeWidth = DimensionUtils.getSizeInPixels(65, context);
  }

  private void init() {
    initPainter();
  }

  private void initPainter() {
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setAntiAlias(true);
    paint.setStrokeWidth(strokeWidth);
    paint.setColor(color);
    paint.setStyle(Paint.Style.STROKE);
    paint.setPathEffect(new DashPathEffect(new float[] { lineWidth, lineSpace }, 0));
  }

  private void initExternalCircle() {
    int padding = strokeWidth / 2 + blurMargin;
    circle = new RectF();
    circle.set(padding, padding, width - padding, height - padding);
  }

  //oval :指定圆弧的外轮廓矩形区域。  startAngle: 圆弧起始角度，单位为度。
  // sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度,从右中间开始为零度。
  // useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。
  @Override public void draw(Canvas canvas) {
    canvas.drawArc(circle, startAngle, plusAngle, false, paint);
  }

  @Override public void setColor(int color) {
    this.color = color;
    paint.setColor(color);
  }

  @Override public int getColor() {
    return color;
  }

  @Override public void onSizeChanged(int height, int width) {
    this.width = width;
    this.height = height;
    initExternalCircle();
  }

  public void setValue(float value) {
    this.plusAngle = (242f * value) / max;
  }

  public float getMax() {
    return max;
  }

  public void setMax(float max) {
    this.max = max;
  }
}
