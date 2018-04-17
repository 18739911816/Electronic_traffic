package com.ut.electronictraffic.classes;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.HashMap;
import java.util.Random;

public class EnvSensor
{
  private static final int SET_SENSOR_INFO = 3001;
  private static final int SHOW_SENSOR_INFO = 3000;
  private static final int SHOW_SENSOR_INFO_TIME = 2000;
  private int DEFAULT_LIGHT_HIGH = 3190;
  private int DEFAULT_LIGHT_LOW = 1100;
  private COM2 com2;
  private Humiture humiture;
  private Light light = new Light();
  private Handler mSensorHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      switch (paramMessage.what)
      {
      default:
        return;
      case 3001:
      }
      removeMessages(3001);
      EnvSensor.this.setRandomSensor();
      EnvSensor.this.sendMsg(-1, 3001, 2000);
    }
  };
  private PM25 pm;
  private Temperature temp;

  public EnvSensor(Context paramContext)
  {
    this.light.setThreshold(this.DEFAULT_LIGHT_LOW, this.DEFAULT_LIGHT_HIGH);
    this.temp = new Temperature();
    this.pm = new PM25();
    this.com2 = new COM2();
    this.humiture = new Humiture();
    sendMsg(-1, 3001, 2000);
  }

  private void sendMsg(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mSensorHandler != null)
    {
      Message localMessage = new Message();
      localMessage.what = paramInt2;
      if (paramInt1 != -1)
      {
        Bundle localBundle = new Bundle();
        localBundle.putInt("car_id", paramInt1);
        localMessage.setData(localBundle);
      }
      this.mSensorHandler.sendMessageDelayed(localMessage, paramInt3);
    }
  }

  private void setRandomSensor()
  {
    Random localRandom = new Random();
    int i = 0 + localRandom.nextInt(4095) % 4096;
    this.light.setThreshold(i);
    int j = 0 + localRandom.nextInt(300) % 301;
    this.pm.setThreshold(j);
    int k = 0 + localRandom.nextInt(10000) % 10001;
    this.com2.setThreshold(k);
    int m = 20 + localRandom.nextInt(90) % 71;
    this.humiture.setThreshold(m);
    int n = 0 + localRandom.nextInt(50) % 51;
    this.temp.setThreshold(n);
  }

  public int getEnvSensorInfo(int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    }
    do
    {
      do
      {
        do
        {
          do
          {
            do
              return 0;
            while (this.light == null);
            return this.light.getThreshold();
          }
          while (this.pm == null);
          return this.pm.getThreshold();
        }
        while (this.com2 == null);
        return this.com2.getThreshold();
      }
      while (this.humiture == null);
      return this.humiture.getThreshold();
    }
    while (this.temp == null);
    return this.temp.getThreshold();
  }

  public HashMap<String, Integer> getEnvSensorInfo()
  {
    HashMap localHashMap = new HashMap();
    int i;
    int j;
    label38: int k;
    label60: int m;
    label82: int n;
    if (this.light == null)
    {
      i = 0;
      localHashMap.put("light", Integer.valueOf(i));
      if (this.pm != null)
        break label133;
      j = 0;
      localHashMap.put("pm", Integer.valueOf(j));
      if (this.com2 != null)
        break label145;
      k = 0;
      localHashMap.put("com2", Integer.valueOf(k));
      if (this.humiture != null)
        break label157;
      m = 0;
      localHashMap.put("humiture", Integer.valueOf(m));
      Temperature localTemperature = this.temp;
      n = 0;
      if (localTemperature != null)
        break label169;
    }
    while (true)
    {
      localHashMap.put("temp", Integer.valueOf(n));
      return localHashMap;
      i = this.light.getThreshold();
      break;
      label133: j = this.pm.getThreshold();
      break label38;
      label145: k = this.com2.getThreshold();
      break label60;
      label157: m = this.humiture.getThreshold();
      break label82;
      label169: n = this.temp.getThreshold();
    }
  }

  public int getThresholdH()
  {
    if (this.light != null)
      return this.light.getThresholdH();
    return this.DEFAULT_LIGHT_HIGH;
  }

  public int getThresholdL()
  {
    if (this.light != null)
      return this.light.getThresholdL();
    return this.DEFAULT_LIGHT_LOW;
  }

  public int isThresholdValid()
  {
    if (this.light != null)
      return this.light.isThresholdValid();
    return 0;
  }

  public void setLihgtSensorThreshold(int paramInt1, int paramInt2)
  {
    if (this.light != null)
      this.light.setThreshold(paramInt1, paramInt2);
  }

  private class COM2
  {
    private int com2_threshold = 4;

    COM2()
    {
    }

    public int getThreshold()
    {
      return this.com2_threshold;
    }

    public void setThreshold(int paramInt)
    {
      this.com2_threshold = paramInt;
    }
  }

  private class Humiture
  {
    private int hum_threshold = 34;

    Humiture()
    {
    }

    public int getThreshold()
    {
      return this.hum_threshold;
    }

    public void setThreshold(int paramInt)
    {
      this.hum_threshold = paramInt;
    }
  }

  private class Light
  {
    private int threshold = EnvSensor.this.DEFAULT_LIGHT_HIGH;
    private int thresholdH;
    private int thresholdL;

    Light()
    {
    }

    public int getThreshold()
    {
      return this.threshold;
    }

    public int getThresholdH()
    {
      return this.thresholdH;
    }

    public int getThresholdL()
    {
      return this.thresholdL;
    }

    public int isThresholdValid()
    {
      Log.d("---envSensor--", "--------threshold = " + this.threshold + ":" + this.thresholdL + ":" + this.thresholdH);
      if (this.threshold < this.thresholdL)
        return -1;
      if (this.threshold > this.thresholdH)
        return 1;
      return 0;
    }

    public void setThreshold(int paramInt)
    {
      this.threshold = paramInt;
    }

    public void setThreshold(int paramInt1, int paramInt2)
    {
      this.thresholdL = paramInt1;
      this.thresholdH = paramInt2;
    }
  }

  private class PM25
  {
    private int pm_threshold = 20;

    PM25()
    {
    }

    public int getThreshold()
    {
      return this.pm_threshold;
    }

    public void setThreshold(int paramInt)
    {
      this.pm_threshold = paramInt;
    }
  }

  private class Temperature
  {
    private int temp_threshold = 34;

    Temperature()
    {
    }

    public int getThreshold()
    {
      return this.temp_threshold;
    }

    public void setThreshold(int paramInt)
    {
      this.temp_threshold = paramInt;
    }
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.classes.EnvSensor
 * JD-Core Version:    0.6.0
 */