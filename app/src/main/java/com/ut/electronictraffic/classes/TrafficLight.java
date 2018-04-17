package com.ut.electronictraffic.classes;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.ut.electronictraffic.interfaces.Interface;
import com.ut.electronictraffic.interfaces.RECT;
import com.ut.electronictraffic.interfaces.Utils;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class TrafficLight
{
  public static final int BEGIN_RUN = 6000;
  private static final int DEFATLT_TIME = 5000;
  private static final boolean LOG = false;
  public static final int SHOW_TIME_0 = 6001;
  public static final int SHOW_TIME_1 = 6002;
  public static final int SHOW_TIME_2 = 6003;
  public static final int SHOW_TIME_3 = 6004;
  public static final int SHOW_TIME_4 = 6005;
  public static final int SHOW_TRAFFIC_LIGHT_UI = 6006;
  private int[][] LIGHT_MODE;
  private int direct;
  private Handler mHandler;
  private Handler mHandler_main;
  private Handler mTrafficHandler;
  private HashMap<String, RECT> map_traffic;
  private HashMap<String, RECT> map_traffic_bus;
  private int mode_0;
  private int mode_1;
  private int mode_2;
  private int mode_3;
  private int mode_4;
  private int[] traffic_mode;
  private int[][] traffic_time;
  private int x;
  private int y;

  public TrafficLight(Context paramContext, Handler paramHandler)
  {
    int[] arrayOfInt1 = { 5, 2 };
    this.LIGHT_MODE = ((int[][])Array.newInstance(Integer.TYPE, arrayOfInt1));
    this.map_traffic = new HashMap();
    this.map_traffic_bus = new HashMap();
    this.mode_0 = 0;
    this.mode_1 = 0;
    this.mode_2 = 0;
    this.mode_3 = 0;
    this.mode_4 = 0;
    this.mTrafficHandler = new Handler()
    {
      public void handleMessage(Message paramMessage)
      {
        super.handleMessage(paramMessage);
        int i4;
        int i5;
        switch (paramMessage.what)
        {
        default:
          return;
        case 6000:
          int i6 = paramMessage.getData().getInt("car_id");
          int i7 = paramMessage.getData().getInt("id");
          int i8 = paramMessage.getData().getInt("angle_id");
          if (TrafficLight.this.getTrafficLightById(i7, i8) != 2)
          {
            TrafficLight.this.sendMsg(TrafficLight.this.mTrafficHandler, i6, i7, i8, 6000, 100);
            return;
          }
          TrafficLight.this.sendMsg(TrafficLight.this.mHandler, i6, i7, i8, Utils.CAR_MESSAGE_WAHT[(i6 + 6)], 100);
          return;
        case 6001:
          removeMessages(6001);
          i4 = TrafficLight.this.traffic_time[0][2];
          if (Interface.getSyncMode())
          {
            TrafficLight.this.mode_0 = Interface.getLIGHT_MODE_UI(0);
            TrafficLight.this.sendMsg(TrafficLight.this.mHandler_main, -1, 0, TrafficLight.this.mode_0, 1011, 1);
            i5 = i4;
            TrafficLight.this.sendMsg(TrafficLight.this.mTrafficHandler, -1, -1, -1, 6001, i5);
            return;
          }
          TrafficLight.this.sendMsg(TrafficLight.this.mHandler_main, -1, 0, TrafficLight.this.mode_0, 1011, 1);
          if (TrafficLight.this.mode_0 != 0)
            break;
          Interface.setLIGHT_MODE(0, 0, 1);
          i5 = TrafficLight.this.traffic_time[0][1];
        case 6002:
        case 6003:
        case 6004:
        case 6005:
        }
        while (true)
        {
          int i2;
          int i3;
          label726: int n;
          int i1;
          label1023: int k;
          int m;
          if (TrafficLight.this.mode_0 < 3)
          {
            TrafficLight localTrafficLight5 = TrafficLight.this;
            localTrafficLight5.mode_0 = (1 + localTrafficLight5.mode_0);
            break;
            if (TrafficLight.this.mode_0 == 1)
            {
              Interface.setLIGHT_MODE(0, 2, 0);
              i5 = TrafficLight.this.traffic_time[0][2];
              continue;
            }
            if (TrafficLight.this.mode_0 == 2)
            {
              Interface.setLIGHT_MODE(0, 1, 0);
              i5 = TrafficLight.this.traffic_time[0][1];
              continue;
            }
            if (TrafficLight.this.mode_0 == 3)
            {
              Interface.setLIGHT_MODE(0, 0, 2);
              i5 = TrafficLight.this.traffic_time[0][2];
              continue;
            }
          }
          else
          {
            TrafficLight.this.mode_0 = 0;
            break;
            removeMessages(6002);
            i2 = TrafficLight.this.traffic_time[1][1];
            if (Interface.getSyncMode())
            {
              TrafficLight.this.mode_1 = Interface.getLIGHT_MODE_UI(1);
              TrafficLight.this.sendMsg(TrafficLight.this.mHandler_main, -1, 1, TrafficLight.this.mode_1, 1011, 1);
              i3 = i2;
            }
            while (true)
            {
              TrafficLight.this.sendMsg(TrafficLight.this.mTrafficHandler, -1, -1, -1, 6002, i3);
              return;
              if (TrafficLight.this.mode_1 == 0)
              {
                Interface.setLIGHT_MODE(1, 0, 1);
                i2 = TrafficLight.this.traffic_time[1][1];
              }
              while (true)
              {
                TrafficLight.this.sendMsg(TrafficLight.this.mHandler_main, -1, 1, TrafficLight.this.mode_1, 1011, 1);
                if (TrafficLight.this.mode_1 >= 3)
                  break label726;
                TrafficLight localTrafficLight4 = TrafficLight.this;
                localTrafficLight4.mode_1 = (1 + localTrafficLight4.mode_1);
                i3 = i2;
                break;
                if (TrafficLight.this.mode_1 == 1)
                {
                  Interface.setLIGHT_MODE(1, 2, 0);
                  i2 = TrafficLight.this.traffic_time[1][2];
                  continue;
                }
                if (TrafficLight.this.mode_1 == 2)
                {
                  Interface.setLIGHT_MODE(1, 1, 0);
                  i2 = TrafficLight.this.traffic_time[1][1];
                  continue;
                }
                if (TrafficLight.this.mode_1 != 3)
                  continue;
                Interface.setLIGHT_MODE(1, 0, 2);
                i2 = TrafficLight.this.traffic_time[1][2];
              }
              TrafficLight.this.mode_1 = 0;
              i3 = i2;
            }
            removeMessages(6003);
            n = TrafficLight.this.traffic_time[2][1];
            if (Interface.getSyncMode())
            {
              TrafficLight.this.mode_2 = Interface.getLIGHT_MODE_UI(2);
              TrafficLight.this.sendMsg(TrafficLight.this.mHandler_main, -1, 2, TrafficLight.this.mode_2, 1011, 1);
              i1 = n;
            }
            while (true)
            {
              TrafficLight.this.sendMsg(TrafficLight.this.mTrafficHandler, -1, -1, -1, 6003, i1);
              return;
              if (TrafficLight.this.mode_2 == 0)
              {
                Interface.setLIGHT_MODE(2, 0, 1);
                n = TrafficLight.this.traffic_time[2][1];
              }
              while (true)
              {
                TrafficLight.this.sendMsg(TrafficLight.this.mHandler_main, -1, 2, TrafficLight.this.mode_2, 1011, 1);
                if (TrafficLight.this.mode_2 >= 3)
                  break label1023;
                TrafficLight localTrafficLight3 = TrafficLight.this;
                localTrafficLight3.mode_2 = (1 + localTrafficLight3.mode_2);
                i1 = n;
                break;
                if (TrafficLight.this.mode_2 == 1)
                {
                  Interface.setLIGHT_MODE(2, 2, 0);
                  n = TrafficLight.this.traffic_time[2][2];
                  continue;
                }
                if (TrafficLight.this.mode_2 == 2)
                {
                  Interface.setLIGHT_MODE(2, 1, 0);
                  n = TrafficLight.this.traffic_time[2][1];
                  continue;
                }
                if (TrafficLight.this.mode_2 != 3)
                  continue;
                Interface.setLIGHT_MODE(2, 0, 2);
                n = TrafficLight.this.traffic_time[2][2];
              }
              TrafficLight.this.mode_2 = 0;
              i1 = n;
            }
            removeMessages(6004);
            k = TrafficLight.this.traffic_time[3][1];
            if (Interface.getSyncMode())
            {
              TrafficLight.this.mode_3 = Interface.getLIGHT_MODE_UI(3);
              TrafficLight.this.sendMsg(TrafficLight.this.mHandler_main, -1, 3, TrafficLight.this.mode_3, 1011, 1);
              m = k;
              TrafficLight.this.sendMsg(TrafficLight.this.mTrafficHandler, -1, -1, -1, 6004, m);
              return;
            }
            TrafficLight.this.sendMsg(TrafficLight.this.mHandler_main, -1, 3, TrafficLight.this.mode_3, 1011, 1);
            if (TrafficLight.this.mode_3 == 0)
            {
              Interface.setLIGHT_MODE(3, 0, 1);
              m = TrafficLight.this.traffic_time[3][1];
            }
            while (true)
            {
              int i;
              int j;
              if (TrafficLight.this.mode_3 < 3)
              {
                TrafficLight localTrafficLight2 = TrafficLight.this;
                localTrafficLight2.mode_3 = (1 + localTrafficLight2.mode_3);
                break;
                if (TrafficLight.this.mode_3 == 1)
                {
                  Interface.setLIGHT_MODE(3, 2, 0);
                  m = TrafficLight.this.traffic_time[3][2];
                  continue;
                }
                if (TrafficLight.this.mode_3 == 2)
                {
                  Interface.setLIGHT_MODE(3, 1, 0);
                  m = TrafficLight.this.traffic_time[3][1];
                  continue;
                }
                if (TrafficLight.this.mode_3 == 3)
                {
                  Interface.setLIGHT_MODE(3, 0, 2);
                  m = TrafficLight.this.traffic_time[3][2];
                  continue;
                }
              }
              else
              {
                TrafficLight.this.mode_3 = 0;
                break;
                removeMessages(6005);
                i = TrafficLight.this.traffic_time[4][1];
                if (Interface.getSyncMode())
                {
                  TrafficLight.this.mode_4 = Interface.getLIGHT_MODE_UI(4);
                  TrafficLight.this.sendMsg(TrafficLight.this.mHandler_main, -1, 4, TrafficLight.this.mode_4, 1011, 1);
                  j = i;
                }
                while (true)
                {
                  TrafficLight.this.sendMsg(TrafficLight.this.mTrafficHandler, -1, -1, -1, 6005, j);
                  return;
                  TrafficLight.this.sendMsg(TrafficLight.this.mHandler_main, -1, 4, TrafficLight.this.mode_4, 1011, 1);
                  if (TrafficLight.this.mode_4 == 0)
                  {
                    Interface.setLIGHT_MODE(4, 0, 1);
                    i = TrafficLight.this.traffic_time[4][1];
                  }
                  while (true)
                  {
                    TrafficLight.this.sendMsg(TrafficLight.this.mHandler_main, -1, 4, TrafficLight.this.mode_4, 1011, 1);
                    if (TrafficLight.this.mode_4 >= 3)
                      break label1626;
                    TrafficLight localTrafficLight1 = TrafficLight.this;
                    localTrafficLight1.mode_4 = (1 + localTrafficLight1.mode_4);
                    j = i;
                    break;
                    if (TrafficLight.this.mode_4 == 1)
                    {
                      Interface.setLIGHT_MODE(4, 2, 0);
                      i = TrafficLight.this.traffic_time[4][2];
                      continue;
                    }
                    if (TrafficLight.this.mode_4 == 2)
                    {
                      Interface.setLIGHT_MODE(4, 1, 0);
                      i = TrafficLight.this.traffic_time[4][1];
                      continue;
                    }
                    if (TrafficLight.this.mode_4 != 3)
                      continue;
                    Interface.setLIGHT_MODE(4, 0, 2);
                    i = TrafficLight.this.traffic_time[4][2];
                  }
                  label1626: TrafficLight.this.mode_4 = 0;
                  j = i;
                }
              }
              m = k;
            }
          }
          i5 = i4;
        }
      }
    };
    this.mHandler_main = paramHandler;
    int[] arrayOfInt2 = { 5, 3 };
    this.traffic_time = ((int[][])Array.newInstance(Integer.TYPE, arrayOfInt2));
    int i = 0;
    int k;
    if (i >= 5)
    {
      this.traffic_mode = new int[5];
      k = 0;
      label141: if (k < Utils.TRAFFIC_CAR.length)
        break label206;
    }
    for (int m = 0; ; m++)
    {
      if (m >= 5)
      {
        return;
        for (int j = 0; ; j++)
        {
          if (j >= 3)
          {
            i++;
            break;
          }
          this.traffic_time[i][j] = 5000;
          this.traffic_time[i][1] = 3000;
        }
        label206: RECT localRECT1 = new RECT();
        localRECT1.x = Utils.TRAFFIC_CAR[k][0];
        localRECT1.y = Utils.TRAFFIC_CAR[k][1];
        localRECT1.direct = Utils.TRAFFIC_CAR[k][2];
        localRECT1.angle = Utils.TRAFFIC_CAR[k][3];
        this.map_traffic.put("car_id_" + k, localRECT1);
        RECT localRECT2 = new RECT();
        localRECT2.x = Utils.TRAFFIC_BUS[k][0];
        localRECT2.y = Utils.TRAFFIC_BUS[k][1];
        localRECT2.direct = Utils.TRAFFIC_BUS[k][2];
        localRECT2.angle = Utils.TRAFFIC_BUS[k][3];
        this.map_traffic_bus.put("car_id_" + k, localRECT2);
        k++;
        break label141;
      }
      this.LIGHT_MODE[m][0] = 0;
      this.LIGHT_MODE[m][1] = 0;
      sendMsg(this.mTrafficHandler, -1, m, -1, Utils.TRAFFIC_MESSAGE_WAHT[m], this.traffic_time[m][0]);
    }
  }

  private int getIDByRect(RECT paramRECT, boolean paramBoolean)
  {
    return 0;
  }

  private void sendMsg(Handler paramHandler, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (paramHandler != null)
    {
      Message localMessage = new Message();
      localMessage.what = paramInt4;
      if (paramInt2 != -1)
      {
        Bundle localBundle = new Bundle();
        localBundle.putInt("car_id", paramInt1);
        localBundle.putInt("id", paramInt2);
        localBundle.putInt("angle_id", paramInt3);
        localMessage.setData(localBundle);
      }
      paramHandler.sendMessageDelayed(localMessage, paramInt5);
    }
  }

  public String getStatusTime(int paramInt1, int paramInt2)
  {
    JSONObject localJSONObject = new JSONObject();
    int i = Interface.getLIGHT_MODE(paramInt1, paramInt2);
    String str;
    if (i == 0)
      str = "Red";
    while (true)
      try
      {
        localJSONObject.put("Status", str);
        if (i != 0)
          continue;
        int j = this.traffic_time[paramInt1][0];
        localJSONObject.put("Time", j / 1000);
        return localJSONObject.toString();
        if (i != 1)
          continue;
        str = "Yellow";
        continue;
        if (i != 1)
          continue;
        j = this.traffic_time[paramInt1][1];
        continue;
        j = this.traffic_time[paramInt1][2];
        continue;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        continue;
        str = "Green";
      }
  }

  public String getTime(int paramInt)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("RedTime", this.traffic_time[paramInt][0] / 1000);
      localJSONObject.put("YellowTime", this.traffic_time[paramInt][1] / 1000);
      localJSONObject.put("GreenTime", this.traffic_time[paramInt][2] / 1000);
      return localJSONObject.toString();
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public int getTrafficLightById(int paramInt1, int paramInt2)
  {
    return Interface.getLIGHT_MODE(paramInt1, paramInt2);
  }

  public int getTrafficLightId(RECT paramRECT, boolean paramBoolean)
  {
    int i = 2;
    int j = 3;
    int k = 1;
    if (paramBoolean)
    {
      if ((paramRECT.direct == j) && (paramRECT.x == 210))
        return Interface.getLIGHT_MODE(0, k);
      if (paramRECT.direct == i)
      {
        if (paramRECT.x == 620);
        while (true)
        {
          return Interface.getLIGHT_MODE(j, k);
          j = 4;
        }
      }
      if (paramRECT.direct == k)
      {
        if (paramRECT.y == 400)
          k = 0;
        return Interface.getLIGHT_MODE(k, 0);
      }
      if (paramRECT.y == 120)
        i = j;
      return Interface.getLIGHT_MODE(i, 0);
    }
    if (paramRECT.direct == j)
    {
      if ((paramRECT.x == 235) && (paramRECT.y == 975))
        return Interface.getLIGHT_MODE(k, k);
      if ((paramRECT.x == 235) && (paramRECT.y == 510))
        return Interface.getLIGHT_MODE(0, k);
      return Interface.getLIGHT_MODE(i, k);
    }
    if (paramRECT.direct == i)
    {
      if (paramRECT.x == 620);
      while (true)
      {
        return Interface.getLIGHT_MODE(j, k);
        j = 4;
      }
    }
    if (paramRECT.direct == k)
    {
      if (paramRECT.y == 500)
        k = 0;
      return Interface.getLIGHT_MODE(k, 0);
    }
    if ((paramRECT.x == 540) && (paramRECT.y == 590))
      return Interface.getLIGHT_MODE(i, 0);
    if ((paramRECT.x == 540) && (paramRECT.y == 120))
      return Interface.getLIGHT_MODE(j, 0);
    return Interface.getLIGHT_MODE(4, 0);
  }

  public String getTrafficStatusTime(int paramInt1, int paramInt2)
  {
    JSONObject localJSONObject = new JSONObject();
    int i = Interface.getLIGHT_MODE(paramInt1, paramInt2);
    try
    {
      localJSONObject.put("TrafficId", paramInt1);
      int j;
      if (i == 0)
      {
        str = "Red";
        localJSONObject.put("Status", str);
        if (i != 0)
          break label87;
        j = this.traffic_time[paramInt1][0];
      }
      while (true)
      {
        localJSONObject.put("Time", j / 1000);
        return localJSONObject.toString();
        if (i != 1)
          break label129;
        str = "Yellow";
        break;
        label87: if (i == 1)
        {
          j = this.traffic_time[paramInt1][1];
          continue;
        }
        j = this.traffic_time[paramInt1][2];
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        localJSONException.printStackTrace();
        continue;
        label129: String str = "Green";
      }
    }
  }

  public int getTrafficTime(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
      return this.traffic_time[paramInt1][0];
    if (paramInt2 == 1)
      return this.traffic_time[paramInt1][1];
    return this.traffic_time[paramInt1][2];
  }

  public boolean isTrafficLight(RECT paramRECT, boolean paramBoolean)
  {
    if (paramBoolean)
      return this.map_traffic_bus.containsValue(paramRECT);
    return this.map_traffic.containsValue(paramRECT);
  }

  public boolean isTrafficLightGreen(RECT paramRECT, boolean paramBoolean)
  {
    return getTrafficLightId(paramRECT, paramBoolean) == 2;
  }

  public void setHandler(Handler paramHandler)
  {
    this.mHandler = paramHandler;
  }

  public void setTime(int paramInt1, String paramString, int paramInt2)
  {
    if (paramString.equals("Red"))
      this.traffic_time[paramInt1][2] = paramInt2;
    do
    {
      return;
      if (!paramString.equals("Yellow"))
        continue;
      this.traffic_time[paramInt1][1] = paramInt2;
      return;
    }
    while (!paramString.equals("Green"));
    this.traffic_time[paramInt1][2] = paramInt2;
  }

  public void setTimeConfig(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.traffic_time[paramInt1][0] = paramInt2;
    this.traffic_time[paramInt1][1] = paramInt3;
    this.traffic_time[paramInt1][2] = paramInt4;
  }

  public void setTrafficLightById(int paramInt1, int paramInt2, String paramString)
  {
    int i = -1;
    if (paramString.equals("red"))
      if (paramInt2 == 1)
        i = 0;
    do
    {
      while (true)
      {
        setTrafficStatus(paramInt1, i);
        return;
        i = 1;
      }
      if (!paramString.equals("yellow"))
        continue;
      if (paramInt2 == 1);
      for (i = 2; ; i = 0)
        break;
    }
    while (!paramString.equals("green"));
    if (paramInt2 == 1);
    for (i = 1; ; i = 3)
      break;
  }

  public void setTrafficMsg(int paramInt, RECT paramRECT, boolean paramBoolean)
  {
    int i = -1;
    Iterator localIterator;
    String str;
    if (paramBoolean)
    {
      localIterator = this.map_traffic_bus.entrySet().iterator();
      str = "";
      label26: if (localIterator.hasNext())
        break label119;
      label36: if ((!str.equalsIgnoreCase("car_id_0")) && (!str.equalsIgnoreCase("car_id_1")))
        break label160;
      i = 0;
      label61: if ((paramRECT.angle != 0) && (paramRECT.angle != 180))
        break label274;
    }
    label274: for (int j = 0; ; j = 1)
    {
      sendMsg(this.mTrafficHandler, paramInt, i / 2, j, 6000, 100);
      return;
      localIterator = this.map_traffic.entrySet().iterator();
      break;
      label119: Map.Entry localEntry = (Map.Entry)localIterator.next();
      str = (String)localEntry.getKey();
      if (!paramRECT.equals(localEntry.getValue()))
        break label26;
      break label36;
      label160: if ((str.equalsIgnoreCase("car_id_2")) || (str.equalsIgnoreCase("car_id_3")))
      {
        i = 2;
        break label61;
      }
      if ((str.equalsIgnoreCase("car_id_4")) || (str.equalsIgnoreCase("car_id_5")))
      {
        i = 4;
        break label61;
      }
      if ((str.equalsIgnoreCase("car_id_6")) || (str.equalsIgnoreCase("car_id_7")))
      {
        i = 6;
        break label61;
      }
      if ((!str.equalsIgnoreCase("car_id_8")) && (!str.equalsIgnoreCase("car_id_9")))
        break label61;
      i = 8;
      break label61;
    }
  }

  public void setTrafficStatus(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default:
      if (paramInt2 != 0)
        break;
      Interface.setLIGHT_MODE(paramInt1, 0, 1);
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    }
    do
    {
      return;
      this.mode_0 = paramInt2;
      sendMsg(this.mHandler_main, -1, 0, this.mode_0, 1011, 1);
      break;
      this.mode_1 = paramInt2;
      sendMsg(this.mHandler_main, -1, 1, this.mode_1, 1011, 1);
      break;
      this.mode_2 = paramInt2;
      sendMsg(this.mHandler_main, -1, 2, this.mode_2, 1011, 1);
      break;
      this.mode_3 = paramInt2;
      sendMsg(this.mHandler_main, -1, 3, this.mode_3, 1011, 1);
      break;
      this.mode_4 = paramInt2;
      Log.d("----", "-----mode_4 = " + this.mode_4);
      sendMsg(this.mHandler_main, -1, 4, this.mode_4, 1011, 1);
      break;
      if (paramInt2 == 1)
      {
        Interface.setLIGHT_MODE(paramInt1, 2, 0);
        return;
      }
      if (paramInt2 != 2)
        continue;
      Interface.setLIGHT_MODE(paramInt1, 1, 0);
      return;
    }
    while (paramInt2 != 3);
    Interface.setLIGHT_MODE(paramInt1, 0, 2);
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.classes.TrafficLight
 * JD-Core Version:    0.6.0
 */