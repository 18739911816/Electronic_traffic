package com.ut.electronictraffic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;

public class myImageView extends View
{
  private int[] drawable_car = { 2130837529, 2130837530, 2130837531, 2130837532, 2130837527, 2130837528 };
  private Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), this.drawable_car[paramInt]);
  private Matrix mMatrix = new Matrix();

  public myImageView(Context paramContext, int paramInt)
  {
    super(paramContext);
  }

  public Bitmap getBitmap()
  {
    return this.mBitmap;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.drawBitmap(this.mBitmap, this.mMatrix, null);
    super.onDraw(paramCanvas);
  }

  public void setImageMatrix(Matrix paramMatrix)
  {
    this.mMatrix.set(paramMatrix);
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.myImageView
 * JD-Core Version:    0.6.0
 */