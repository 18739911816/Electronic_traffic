package com.ut.electronictraffic.classes;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.ut.electronictraffic.carApplication;
import com.ut.electronictraffic.interfaces.RECT;
import com.ut.electronictraffic.interfaces.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class BusStation
{
  private static final int SHOW_BUSSTATION_INFO = 5000;
  private static final int SHOW_BUSSTATION_TIME = 4000;
  private int BUS_NUM = 0;
  private carActivity bus;
  private int car_x = 0;
  private int car_y = 0;
  private EnvSensor light;
  private Handler mBusHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      switch (paramMessage.what)
      {
      default:
        return;
      case 5000:
      }
      removeMessages(5000);
      Bundle localBundle = paramMessage.getData();
      int i = localBundle.getInt("bus_id");
      int j = localBundle.getInt("car_id");
      Random localRandom = new Random();
      BusStation.this.BUS_NUM = (10 + localRandom.nextInt(20) % 11);
      if (i == 1)
      {
        BusStation.sendMsgToHandle(BusStation.this.mHandler, 1010, BusStation.this.getBusSationInfo(j, 0), 50);
        return;
      }
      BusStation.sendMsgToHandle(BusStation.this.mHandler, 1010, BusStation.this.getBusSationInfo(j, 1), 50);
    }
  };
  private Handler mHandler;
  private HashMap<String, RECT> map_station = new HashMap();

  public BusStation(Context paramContext, Handler paramHandler)
  {
    for (int i = 0; ; i++)
    {
      if (i >= Utils.STATION.length)
      {
        this.mHandler = paramHandler;
        this.light = ((carApplication)paramContext).getEnvSensor();
        return;
      }
      RECT localRECT = new RECT();
      localRECT.x = Utils.STATION[i][0];
      localRECT.y = Utils.STATION[i][1];
      localRECT.direct = Utils.STATION[i][2];
      localRECT.angle = Utils.STATION[i][3];
      this.map_station.put("car_id_" + i, localRECT);
    }
  }

  private void sendMsg(Handler paramHandler, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramHandler != null)
    {
      Message localMessage = new Message();
      localMessage.what = paramInt1;
      Bundle localBundle = new Bundle();
      localBundle.putInt("car_id", paramInt2);
      localBundle.putInt("bus_id", paramInt3);
      localMessage.setData(localBundle);
      paramHandler.sendMessageDelayed(localMessage, paramInt4);
    }
  }

  public static void sendMsgToHandle(Handler paramHandler, int paramInt1, Map<String, Integer> paramMap, int paramInt2)
  {
    Message localMessage = paramHandler.obtainMessage();
    localMessage.what = paramInt1;
    Bundle localBundle = new Bundle();
    localBundle.clear();
    Iterator localIterator = paramMap.keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        localMessage.setData(localBundle);
        paramHandler.sendMessageDelayed(localMessage, paramInt2);
        return;
      }
      Object localObject = localIterator.next();
      localBundle.putInt(localObject.toString(), ((Integer)paramMap.get(localObject)).intValue());
    }
  }

  public int getBusCapacity(int paramInt)
  {
    return this.BUS_NUM;
  }

  public HashMap<String, Integer> getBusSationInfo(int paramInt1, int paramInt2)
  {
    new HashMap();
    HashMap localHashMap = this.light.getEnvSensorInfo();
    localHashMap.put("car_id", Integer.valueOf(paramInt1));
    localHashMap.put("stationId", Integer.valueOf(paramInt2));
    localHashMap.put("distance", Integer.valueOf(getDistance(paramInt2)));
    localHashMap.put("bus_num", Integer.valueOf(2));
    localHashMap.put("peoper_num", Integer.valueOf(this.BUS_NUM));
    localHashMap.put("station_id", Integer.valueOf(this.car_y));
    return localHashMap;
  }

  public int getBusSpeed(int paramInt)
  {
    return this.BUS_NUM;
  }

  public int getDistance(int paramInt)
  {
    Iterator localIterator = this.map_station.entrySet().iterator();
    boolean bool = localIterator.hasNext();
    int i = 0;
    int j = 0;
    if (!bool);
    while (true)
    {
      return 100 * Math.abs(this.car_x - i) + 90 * Math.abs(this.car_y - j);
      Entry localEntry = (Entry)localIterator.next();
      Object localObject1 = localEntry.getKey();
      Object localObject2 = localEntry.getValue();
      if (!("car_id_" + paramInt).equals(localObject1))
        break;
      i = ((RECT)localObject2).x;
      j = ((RECT)localObject2).y;
    }
  }

  public boolean isBusSation(RECT paramRECT)
  {
    return this.map_station.containsValue(paramRECT);
  }

  public void setBusStationRect(RECT paramRECT)
  {
    this.car_x = paramRECT.x;
    this.car_y = paramRECT.y;
  }

  public void showInfo(int paramInt1, int paramInt2)
  {
    sendMsg(this.mBusHandler, 5000, paramInt1, paramInt2, 20);
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.classes.BusStation
 * JD-Core Version:    0.6.0
 */