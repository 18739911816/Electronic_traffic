package com.ut.electronictraffic.classes;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.ut.electronictraffic.carApplication;

public class StreetLight
{
  private static final int SHOW_STREET_INFO = 4000;
  private static final int SHOW_STREET_TIME = 500;
  private static final int SHOW_STREET_UI_TIME = 100;
  private boolean bLightOn;
  private EnvSensor light;
  private Handler mHandler;
  private int mMode;
  private Handler mStreetHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      switch (paramMessage.what)
      {
      default:
        return;
      case 4000:
      }
      removeMessages(4000);
      StreetLight.this.showStreetLightUI();
      StreetLight.this.sendMsg(StreetLight.this.mStreetHandler, 4000, 0, 500);
    }
  };

  public StreetLight(Context paramContext, Handler paramHandler)
  {
    this.mHandler = paramHandler;
    this.mMode = -1;
    this.bLightOn = false;
    setStreetLightMode(1);
    this.light = ((carApplication)paramContext).getEnvSensor();
  }

  private void sendMsg(Handler paramHandler, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramHandler != null)
    {
      Message localMessage = new Message();
      localMessage.what = paramInt1;
      Bundle localBundle = new Bundle();
      localBundle.putInt("light_mode", paramInt2);
      localMessage.setData(localBundle);
      paramHandler.sendMessageDelayed(localMessage, paramInt3);
    }
  }

  private void showStreetLightUI()
  {
    if (this.light != null);
    switch (this.light.isThresholdValid())
    {
    case 0:
    default:
      return;
    case -1:
      this.bLightOn = true;
      sendMsg(this.mHandler, 1007, -1, 100);
      return;
    case 1:
    }
    this.bLightOn = false;
    sendMsg(this.mHandler, 1007, 1, 100);
  }

  public int getStreetLightMode()
  {
    return this.mMode;
  }

  public boolean getStreetLightOnOff()
  {
    return this.bLightOn;
  }

  public void setStreetLightMode(int paramInt)
  {
    this.mMode = paramInt;
    if (paramInt == 0)
      sendMsg(this.mStreetHandler, 4000, 0, 500);
    do
      return;
    while (this.mStreetHandler == null);
    this.mStreetHandler.removeMessages(4000);
  }

  public void setStreetLightOnOff(boolean paramBoolean)
  {
    int i = 1;
    this.bLightOn = paramBoolean;
    if (this.mStreetHandler != null)
      this.mStreetHandler.removeMessages(4000);
    if (this.mMode == i)
    {
      Handler localHandler = this.mHandler;
      if (paramBoolean)
        i = -1;
      sendMsg(localHandler, 1007, i, 100);
    }
  }

  public void setStreetLightOnOffMode(boolean paramBoolean)
  {
    this.bLightOn = paramBoolean;
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.classes.StreetLight
 * JD-Core Version:    0.6.0
 */