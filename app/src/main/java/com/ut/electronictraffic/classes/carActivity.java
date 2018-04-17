package com.ut.electronictraffic.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.ut.electronictraffic.carApplication;
import com.ut.electronictraffic.interfaces.RECT;
import com.ut.electronictraffic.interfaces.Utils;
import java.util.HashMap;

public class carActivity
{
  private static final int BUS_LEN = 120;
  private static final int CAR_LEN = 80;
  private static final int LEN = 5;
  private static final boolean LOG = false;
  public static final int SHOW_CAR_BUSSTATION_TIME = 2000;
  public static final int SHOW_CAR_INFO_0 = 2001;
  public static final int SHOW_CAR_INFO_1 = 2002;
  public static final int SHOW_CAR_INFO_2 = 2003;
  public static final int SHOW_CAR_INFO_3 = 2004;
  public static final int SHOW_CAR_INFO_4 = 2005;
  public static final int SHOW_CAR_INFO_5 = 2006;
  public static final int SHOW_CAR_INFO_TIME = 100;
  public static final int SHOW_CAR_TRAFFIC_TIME = 5000;
  private static final String TAG = "carActivity";
  private static boolean bRun = false;
  private int angle = 0;
  private boolean bRandom = false;
  private int carSpeed = 20;
  private int car_Random_id = 0;
  private int car_num = -1;
  private int[] car_path_id = new int[6];
  private RECT[] car_point = new RECT[6];
  private RECT[] car_rect = new RECT[6];
  private int default_x = 1;
  private int default_y = 1;
  private int direct = 1;
  private Factory factory;
  public int id = 0;
  private Handler mCarHandler = new Handler()
  {
    int car_id = 0;

    public void handleMessage(Message paramMessage)
    {
      int i = 1;
      super.handleMessage(paramMessage);
      switch (paramMessage.what)
      {
      default:
      case 2001:
      case 2002:
      case 2003:
      case 2004:
      case 2005:
      case 2006:
      }
      int j;
      do
      {
        do
        {
          while (true)
          {
            return;
            this.car_id = paramMessage.getData().getInt("car_id");
            removeMessages(2001);
            if (!carActivity.bRun)
              continue;
            if (carActivity.this.havaOtherCars(this.car_id))
            {
              carActivity.this.sendMsg(this.car_id, 2001, 2000);
              return;
            }
            carActivity.this.getPaintPoint2(this.car_id);
            carActivity.this.factory.setParkLotFree(i);
            if (carActivity.this.traffic.isTrafficLight(carActivity.this.car_rect[this.car_id], false))
            {
              if (carActivity.this.traffic.isTrafficLightGreen(carActivity.this.car_rect[this.car_id], false))
              {
                carActivity.this.sendMsg(this.car_id, 2001, 100);
                return;
              }
              carActivity.this.traffic.setTrafficMsg(this.car_id, carActivity.this.car_rect[this.car_id], false);
              return;
            }
            if (carActivity.this.factory.isFactoryEtcZa(carActivity.this.car_rect[this.car_id]))
            {
              if (!carActivity.this.factory.isFactoryEtcLotValid(0, this.car_id))
                continue;
              carActivity.this.sendMsg(this.car_id, 2001, 2000);
              carActivity.this.map.clear();
              carActivity.this.map.put("car_id", Integer.valueOf(this.car_id));
              int i8 = carActivity.this.getMessageId(carActivity.this.car_rect[this.car_id].y);
              Utils.sendMsgToHandle(carActivity.this.m_Handler, i8, carActivity.this.map, i);
              return;
            }
            if (carActivity.this.factory.isFactoryLotZa(carActivity.this.car_rect[this.car_id]))
            {
              if (!carActivity.this.factory.isFactoryEtcLotValid(i, this.car_id))
                continue;
              carActivity.this.m_Handler.removeMessages(1021);
              carActivity.this.sendMsg(this.car_id, 2001, 2000);
              carActivity.this.map.clear();
              carActivity.this.map.put("car_id", Integer.valueOf(this.car_id));
              int i7 = carActivity.this.getMessageId(carActivity.this.car_rect[this.car_id].y);
              Log.d("carActivity", "isFactoryOrETC---0--msgid = " + i7);
              Utils.sendMsgToHandle(carActivity.this.m_Handler, i7, carActivity.this.map, i);
              return;
            }
            if (carActivity.this.factory.isFactoryLot(carActivity.this.car_rect[this.car_id]))
            {
              carActivity.this.factory.setParkLotFree(0);
              carActivity.this.sendMsg(this.car_id, 2001, 5000);
              return;
            }
            carActivity.this.sendMsg(this.car_id, 2001, 100);
            return;
            this.car_id = paramMessage.getData().getInt("car_id");
            removeMessages(2002);
            if (!carActivity.bRun)
              continue;
            if (carActivity.this.havaOtherCars(this.car_id))
            {
              carActivity.this.sendMsg(this.car_id, 2002, 2000);
              return;
            }
            carActivity.this.getPaintPoint2(this.car_id);
            carActivity.this.factory.setParkLotFree(i);
            if (carActivity.this.traffic.isTrafficLight(carActivity.this.car_rect[this.car_id], false))
            {
              if (carActivity.this.traffic.isTrafficLightGreen(carActivity.this.car_rect[this.car_id], false))
              {
                carActivity.this.sendMsg(this.car_id, 2002, 100);
                return;
              }
              carActivity.this.traffic.setTrafficMsg(this.car_id, carActivity.this.car_rect[this.car_id], false);
              return;
            }
            if (carActivity.this.factory.isFactoryEtcZa(carActivity.this.car_rect[this.car_id]))
            {
              if (!carActivity.this.factory.isFactoryEtcLotValid(0, this.car_id))
                continue;
              carActivity.this.sendMsg(this.car_id, 2002, 2000);
              carActivity.this.map.clear();
              carActivity.this.map.put("car_id", Integer.valueOf(this.car_id));
              int i6 = carActivity.this.getMessageId(carActivity.this.car_rect[this.car_id].y);
              Log.d("carActivity", "isFactoryOrETC--1--msgid = " + i6);
              Utils.sendMsgToHandle(carActivity.this.m_Handler, i6, carActivity.this.map, i);
              return;
            }
            if (carActivity.this.factory.isFactoryLotZa(carActivity.this.car_rect[this.car_id]))
            {
              if (!carActivity.this.factory.isFactoryEtcLotValid(i, this.car_id))
                continue;
              carActivity.this.sendMsg(this.car_id, 2002, 2000);
              carActivity.this.map.clear();
              carActivity.this.map.put("car_id", Integer.valueOf(this.car_id));
              int i5 = carActivity.this.getMessageId(carActivity.this.car_rect[this.car_id].y);
              Log.d("carActivity", "isFactoryOrETC---1--msgid = " + i5);
              Utils.sendMsgToHandle(carActivity.this.m_Handler, i5, carActivity.this.map, i);
              return;
            }
            if (carActivity.this.factory.isFactoryLot(carActivity.this.car_rect[this.car_id]))
            {
              carActivity.this.factory.setParkLotFree(0);
              carActivity.this.sendMsg(this.car_id, 2002, 5000);
              return;
            }
            carActivity.this.sendMsg(this.car_id, 2002, 100);
            return;
            this.car_id = paramMessage.getData().getInt("car_id");
            removeMessages(2003);
            if (!carActivity.bRun)
              continue;
            if (carActivity.this.havaOtherCars(this.car_id))
            {
              carActivity.this.sendMsg(this.car_id, 2003, 2000);
              return;
            }
            carActivity.this.getPaintPoint2(this.car_id);
            carActivity.this.factory.setParkLotFree(i);
            if (carActivity.this.traffic.isTrafficLight(carActivity.this.car_rect[this.car_id], false))
            {
              if (carActivity.this.traffic.isTrafficLightGreen(carActivity.this.car_rect[this.car_id], false))
              {
                carActivity.this.sendMsg(this.car_id, 2003, 100);
                return;
              }
              carActivity.this.traffic.setTrafficMsg(this.car_id, carActivity.this.car_rect[this.car_id], false);
              return;
            }
            if (carActivity.this.factory.isFactoryEtcZa(carActivity.this.car_rect[this.car_id]))
            {
              if (!carActivity.this.factory.isFactoryEtcLotValid(0, this.car_id))
                continue;
              carActivity.this.sendMsg(this.car_id, 2003, 2000);
              carActivity.this.map.clear();
              carActivity.this.map.put("car_id", Integer.valueOf(this.car_id));
              int i4 = carActivity.this.getMessageId(carActivity.this.car_rect[this.car_id].y);
              Log.d("carActivity", "isFactoryOrETC---2--msgid = " + i4);
              Utils.sendMsgToHandle(carActivity.this.m_Handler, i4, carActivity.this.map, i);
              return;
            }
            if (carActivity.this.factory.isFactoryLotZa(carActivity.this.car_rect[this.car_id]))
            {
              if (!carActivity.this.factory.isFactoryEtcLotValid(i, this.car_id))
                continue;
              carActivity.this.sendMsg(this.car_id, 2003, 2000);
              carActivity.this.map.clear();
              carActivity.this.map.put("car_id", Integer.valueOf(this.car_id));
              int i3 = carActivity.this.getMessageId(carActivity.this.car_rect[this.car_id].y);
              Log.d("carActivity", "isFactoryOrETC---2--msgid = " + i3);
              Utils.sendMsgToHandle(carActivity.this.m_Handler, i3, carActivity.this.map, i);
              return;
            }
            if (carActivity.this.factory.isFactoryLot(carActivity.this.car_rect[this.car_id]))
            {
              carActivity.this.factory.setParkLotFree(0);
              carActivity.this.sendMsg(this.car_id, 2003, 5000);
              return;
            }
            carActivity.this.sendMsg(this.car_id, 2003, 100);
            return;
            this.car_id = paramMessage.getData().getInt("car_id");
            removeMessages(2004);
            if (!carActivity.bRun)
              continue;
            if (carActivity.this.havaOtherCars(this.car_id))
            {
              carActivity.this.sendMsg(this.car_id, 2004, 2000);
              return;
            }
            carActivity.this.getPaintPoint2(this.car_id);
            carActivity.this.factory.setParkLotFree(i);
            if (carActivity.this.traffic.isTrafficLight(carActivity.this.car_rect[this.car_id], false))
            {
              if (carActivity.this.traffic.isTrafficLightGreen(carActivity.this.car_rect[this.car_id], false))
              {
                carActivity.this.sendMsg(this.car_id, 2004, 100);
                return;
              }
              carActivity.this.traffic.setTrafficMsg(this.car_id, carActivity.this.car_rect[this.car_id], false);
              return;
            }
            if (carActivity.this.factory.isFactoryEtcZa(carActivity.this.car_rect[this.car_id]))
            {
              if (!carActivity.this.factory.isFactoryEtcLotValid(0, this.car_id))
                continue;
              carActivity.this.sendMsg(this.car_id, 2004, 2000);
              carActivity.this.map.clear();
              carActivity.this.map.put("car_id", Integer.valueOf(this.car_id));
              int i2 = carActivity.this.getMessageId(carActivity.this.car_rect[this.car_id].y);
              Utils.sendMsgToHandle(carActivity.this.m_Handler, i2, carActivity.this.map, i);
              return;
            }
            if (!carActivity.this.factory.isFactoryLotZa(carActivity.this.car_rect[this.car_id]))
              break;
            if (!carActivity.this.factory.isFactoryEtcLotValid(i, this.car_id))
              continue;
            carActivity.this.sendMsg(this.car_id, 2004, 2000);
            carActivity.this.map.clear();
            carActivity.this.map.put("car_id", Integer.valueOf(this.car_id));
            int i1 = carActivity.this.getMessageId(carActivity.this.car_rect[this.car_id].y);
            Log.d("carActivity", "isFactoryOrETC---3--msgid = " + i1);
            Utils.sendMsgToHandle(carActivity.this.m_Handler, i1, carActivity.this.map, i);
            return;
          }
          if (carActivity.this.factory.isFactoryLot(carActivity.this.car_rect[this.car_id]))
          {
            carActivity.this.factory.setParkLotFree(0);
            carActivity.this.sendMsg(this.car_id, 2004, 5000);
            return;
          }
          carActivity.this.sendMsg(this.car_id, 2004, 100);
          return;
          this.car_id = paramMessage.getData().getInt("car_id");
          removeMessages(2005);
        }
        while (!carActivity.bRun);
        carActivity.this.station.setBusStationRect(carActivity.this.car_rect[this.car_id]);
        if (carActivity.this.havaOtherCars(this.car_id))
        {
          carActivity.this.sendMsg(this.car_id, 2005, 2000);
          return;
        }
        carActivity.this.getPaintPoint2(this.car_id);
        if (carActivity.this.traffic.isTrafficLight(carActivity.this.car_rect[this.car_id], i))
        {
          if (carActivity.this.traffic.isTrafficLightGreen(carActivity.this.car_rect[this.car_id], i))
          {
            carActivity.this.sendMsg(this.car_id, 2005, 100);
            return;
          }
          carActivity.this.traffic.setTrafficMsg(this.car_id, carActivity.this.car_rect[this.car_id], i);
          return;
        }
        if (carActivity.this.station.isBusSation(carActivity.this.car_rect[this.car_id]))
        {
          carActivity.this.sendMsg(this.car_id, 2005, 2000);
          carActivity localcarActivity2 = carActivity.this;
          int n = -4 + this.car_id;
          if (carActivity.this.car_rect[this.car_id].y == 690);
          while (true)
          {
            localcarActivity2.getBusStationInfo(n, i);
            return;
            j = 0;
          }
        }
        carActivity.this.sendMsg(this.car_id, 2005, 100);
        return;
        this.car_id = paramMessage.getData().getInt("car_id");
        removeMessages(2006);
      }
      while (!carActivity.bRun);
      carActivity.this.station.setBusStationRect(carActivity.this.car_rect[this.car_id]);
      if (carActivity.this.havaOtherCars(this.car_id))
        carActivity.this.sendMsg(this.car_id, 2006, 2000);
      while (carActivity.this.traffic.isTrafficLight(carActivity.this.car_rect[this.car_id], j))
      {
        if (carActivity.this.traffic.isTrafficLightGreen(carActivity.this.car_rect[this.car_id], j))
        {
          carActivity.this.sendMsg(this.car_id, 2006, 100);
          return;
          carActivity.this.getPaintPoint2(this.car_id);
          continue;
        }
        carActivity.this.traffic.setTrafficMsg(this.car_id, carActivity.this.car_rect[this.car_id], j);
        return;
      }
      if (carActivity.this.station.isBusSation(carActivity.this.car_rect[this.car_id]))
      {
        carActivity.this.sendMsg(this.car_id, 2006, 2000);
        carActivity localcarActivity1 = carActivity.this;
        int m = -4 + this.car_id;
        if (carActivity.this.car_rect[this.car_id].y == 690);
        while (true)
        {
          localcarActivity1.getBusStationInfo(m, j);
          return;
          int k = 0;
        }
      }
      carActivity.this.sendMsg(this.car_id, 2006, 100);
    }
  };
  private Handler m_Handler;
  private HashMap<String, Integer> map = new HashMap();
  private int old_x = 0;
  private int old_y = 0;
  public RECT rect;
  private SharedPreferences sharedPrefs;
  private BusStation station;
  private TrafficLight traffic;
  private int[] x_value = new int[6];
  private int[] y_value = new int[6];

  public carActivity(Context paramContext, Handler paramHandler)
  {
    this.m_Handler = paramHandler;
    carApplication localcarApplication = (carApplication)paramContext;
    this.traffic = localcarApplication.getTraffic();
    this.traffic.setHandler(this.mCarHandler);
    this.station = localcarApplication.getBusStation();
    this.factory = localcarApplication.getFactory();
    this.factory.setHandler(this.mCarHandler);
    initRect(paramContext);
  }

  private boolean checkCarRect2(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int i;
    if (paramInt1 == 0)
      i = this.car_Random_id;
    int[][] arrayOfInt;
    int j;
    while (true)
    {
      arrayOfInt = Utils.CAR_RECT[this.car_path_id[i]];
      j = arrayOfInt.length;
      if (paramInt4 == 3)
      {
        if ((arrayOfInt[paramInt2][0] > this.x_value[i]) || (this.x_value[i] >= arrayOfInt[((paramInt2 + 1) % j)][0]))
          break;
        int[] arrayOfInt4 = this.x_value;
        arrayOfInt4[i] = (5 + arrayOfInt4[i]);
        getCarRect2(paramInt1, this.x_value[i], this.y_value[i], paramInt4, paramInt5);
        return true;
        i = paramInt1;
        continue;
      }
      else if (paramInt4 == 2)
      {
        if ((arrayOfInt[((paramInt2 + 1) % j)][0] >= this.x_value[i]) || (this.x_value[i] > arrayOfInt[paramInt2][0]))
          break;
        int[] arrayOfInt3 = this.x_value;
        arrayOfInt3[i] = (-5 + arrayOfInt3[i]);
        getCarRect2(paramInt1, this.x_value[i], this.y_value[i], paramInt4, paramInt5);
        return true;
      }
      else if (paramInt4 == 1)
      {
        if ((arrayOfInt[paramInt2][1] > this.y_value[i]) || (this.y_value[i] >= arrayOfInt[((paramInt2 + 1) % j)][1]))
          break;
        int[] arrayOfInt2 = this.y_value;
        arrayOfInt2[i] = (5 + arrayOfInt2[i]);
        getCarRect2(paramInt1, this.x_value[i], this.y_value[i], paramInt4, paramInt5);
        return true;
      }
      else
      {
        if ((paramInt4 != 0) || (arrayOfInt[((paramInt2 + 1) % j)][1] >= this.y_value[i]) || (this.y_value[i] > arrayOfInt[paramInt2][1]))
          break;
        int[] arrayOfInt1 = this.y_value;
        arrayOfInt1[i] = (-5 + arrayOfInt1[i]);
        getCarRect2(paramInt1, this.x_value[i], this.y_value[i], paramInt4, paramInt5);
        return true;
      }
    }
    RECT localRECT = this.car_point[i];
    if (paramInt2 + 1 >= arrayOfInt.length);
    for (int k = 0; ; k = paramInt2 + 1)
    {
      localRECT.x = k;
      this.x_value[i] = arrayOfInt[((paramInt2 + 1) % j)][0];
      this.y_value[i] = arrayOfInt[((paramInt2 + 1) % j)][1];
      this.car_point[i].y = paramInt3;
      this.car_point[i].direct = arrayOfInt[((paramInt2 + 1) % j)][2];
      this.car_point[i].angle = arrayOfInt[((paramInt2 + 1) % j)][3];
      this.car_rect[i].x = this.x_value[i];
      this.car_rect[i].y = this.y_value[i];
      this.car_rect[i].direct = this.car_point[i].direct;
      this.car_rect[i].angle = this.car_point[i].angle;
      return false;
    }
  }

  private int getMessageId(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return -1;
    case 45:
      return 1009;
    case 220:
      return 1017;
    case 890:
      return 1018;
    case 975:
    }
    return 1019;
  }

  private void getPaintPoint(int paramInt)
  {
    int[][] arrayOfInt = Utils.CAR_RECT_SYNC[paramInt];
    int i = this.car_rect[paramInt].direct;
    int j = this.car_rect[paramInt].angle;
    int k = this.car_rect[paramInt].x;
    int m = this.car_rect[paramInt].y;
    switch (i)
    {
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      this.car_rect[paramInt].x = k;
      this.car_rect[paramInt].y = m;
      this.car_rect[paramInt].direct = i;
      this.car_rect[paramInt].angle = j;
      getCarRect(paramInt, k, m, i, j);
      return;
      if (arrayOfInt[(k - 1)][m] == 1)
      {
        k--;
        j = 180;
        i = 0;
        continue;
      }
      if (arrayOfInt[k][(m - 1)] == 1)
      {
        i = 2;
        j = 90;
        continue;
      }
      if (arrayOfInt[k][(m + 1)] != 1)
        continue;
      m++;
      i = 3;
      j = 270;
      continue;
      if (arrayOfInt[(k + 1)][m] == 1)
      {
        k++;
        i = 1;
        j = 0;
        continue;
      }
      if (arrayOfInt[k][(m - 1)] == 1)
      {
        m--;
        i = 2;
        j = 90;
        continue;
      }
      if (arrayOfInt[k][(m + 1)] != 1)
        continue;
      i = 3;
      j = 270;
      continue;
      if (arrayOfInt[k][(m - 1)] == 1)
      {
        m--;
        i = 2;
        j = 90;
        continue;
      }
      if (arrayOfInt[(k - 1)][m] == 1)
      {
        k--;
        j = 180;
        i = 0;
        continue;
      }
      if (arrayOfInt[(k + 1)][m] != 1)
        continue;
      i = 1;
      j = 0;
      continue;
      if (arrayOfInt[k][(m + 1)] == 1)
      {
        m++;
        i = 3;
        j = 270;
        continue;
      }
      if (arrayOfInt[(k - 1)][m] == 1)
      {
        j = 180;
        i = 0;
        continue;
      }
      if (arrayOfInt[(k + 1)][m] != 1)
        continue;
      k++;
      i = 1;
      j = 0;
    }
  }

  private void getPaintPoint2(int paramInt)
  {
    if (paramInt == 0);
    for (int i = this.car_Random_id; ; i = paramInt)
    {
      int j = this.car_point[i].direct;
      int k = this.car_point[i].angle;
      checkCarRect2(paramInt, this.car_point[i].x, this.car_point[i].y, j, k);
      return;
    }
  }

  private boolean havaOtherCars(int paramInt)
  {
    int i108;
    switch (paramInt)
    {
    default:
      i108 = 0;
    case 0:
      do
        while (true)
        {
          return i108;
          int i106 = this.car_rect[0].angle;
          int i107 = this.car_rect[4].angle;
          i108 = 0;
          if (i106 == i107)
          {
            if ((this.car_rect[0].angle != 0) || (this.car_rect[0].y >= this.car_rect[4].y))
              break label618;
            int i124 = 120 + this.car_rect[0].y;
            int i125 = this.car_rect[4].y;
            i108 = 0;
            if (i124 > i125)
            {
              int i126 = -15 + this.car_rect[0].x;
              int i127 = this.car_rect[4].x;
              i108 = 0;
              if (i126 == i127)
                i108 = 1;
            }
          }
          if (this.car_rect[0].angle == this.car_rect[5].angle)
          {
            if ((this.car_rect[0].angle != 0) || (this.car_rect[0].y >= this.car_rect[5].y))
              break label971;
            if ((120 + this.car_rect[0].y > this.car_rect[5].y) && (-15 + this.car_rect[0].x == this.car_rect[5].x))
              i108 = 1;
          }
          if ((this.car_rect[0].angle == this.car_rect[1].angle) && (this.car_num > 1))
          {
            if ((this.car_rect[0].angle != 0) || (this.car_rect[0].y >= this.car_rect[1].y))
              break label1240;
            if ((100 + this.car_rect[0].y > this.car_rect[1].y) && (this.car_rect[0].x == this.car_rect[1].x))
              i108 = 1;
          }
          if ((this.car_rect[0].angle == this.car_rect[2].angle) && (this.car_num > 2))
          {
            if ((this.car_rect[0].angle != 0) || (this.car_rect[0].y >= this.car_rect[2].y))
              break label1500;
            if ((100 + this.car_rect[0].y > this.car_rect[2].y) && (this.car_rect[0].x == this.car_rect[2].x))
              i108 = 1;
          }
          while ((this.car_rect[0].angle == this.car_rect[3].angle) && (this.car_num > 3))
          {
            if ((this.car_rect[0].angle != 0) || (this.car_rect[0].y >= this.car_rect[3].y))
              break label1760;
            if ((100 + this.car_rect[0].y <= this.car_rect[3].y) || (this.car_rect[0].x != this.car_rect[3].x))
              break;
            return true;
            if ((this.car_rect[0].angle == 270) && (this.car_rect[0].x < this.car_rect[4].x))
            {
              int i120 = 120 + this.car_rect[0].x;
              int i121 = this.car_rect[4].x;
              i108 = 0;
              if (i120 <= i121)
                break label184;
              int i122 = -10 + this.car_rect[0].y;
              int i123 = this.car_rect[4].y;
              i108 = 0;
              if (i122 != i123)
                break label184;
              i108 = 1;
              break label184;
            }
            if ((this.car_rect[0].angle == 180) && (this.car_rect[0].y > this.car_rect[4].y))
            {
              int i116 = -120 + this.car_rect[0].y;
              int i117 = this.car_rect[4].y;
              i108 = 0;
              if (i116 >= i117)
                break label184;
              int i118 = -15 + this.car_rect[0].x;
              int i119 = this.car_rect[4].x;
              i108 = 0;
              if (i118 != i119)
                break label184;
              i108 = 1;
              break label184;
            }
            int i109 = this.car_rect[0].angle;
            i108 = 0;
            if (i109 != 90)
              break label184;
            int i110 = this.car_rect[0].x;
            int i111 = this.car_rect[4].x;
            i108 = 0;
            if (i110 <= i111)
              break label184;
            int i112 = -120 + this.car_rect[0].x;
            int i113 = this.car_rect[4].x;
            i108 = 0;
            if (i112 >= i113)
              break label184;
            int i114 = -20 + this.car_rect[0].y;
            int i115 = this.car_rect[4].y;
            i108 = 0;
            if (i114 != i115)
              break label184;
            i108 = 1;
            break label184;
            if ((this.car_rect[0].angle == 270) && (this.car_rect[0].x < this.car_rect[5].x))
            {
              if ((120 + this.car_rect[0].x <= this.car_rect[5].x) || (-10 + this.car_rect[0].y != this.car_rect[5].y))
                break label289;
              i108 = 1;
              break label289;
            }
            if ((this.car_rect[0].angle == 180) && (this.car_rect[0].y > this.car_rect[5].y))
            {
              if ((-120 + this.car_rect[0].y >= this.car_rect[5].y) || (-15 + this.car_rect[0].x != this.car_rect[5].x))
                break label289;
              i108 = 1;
              break label289;
            }
            if ((this.car_rect[0].angle != 90) || (this.car_rect[0].x <= this.car_rect[5].x) || (-120 + this.car_rect[0].x >= this.car_rect[5].x) || (-20 + this.car_rect[0].y != this.car_rect[5].y))
              break label289;
            i108 = 1;
            break label289;
            if ((this.car_rect[0].angle == 270) && (this.car_rect[0].x < this.car_rect[1].x))
            {
              if ((100 + this.car_rect[0].x <= this.car_rect[1].x) || (this.car_rect[0].y != this.car_rect[1].y))
                break label399;
              i108 = 1;
              break label399;
            }
            if ((this.car_rect[0].angle == 180) && (this.car_rect[0].y > this.car_rect[1].y))
            {
              if ((-100 + this.car_rect[0].y >= this.car_rect[1].y) || (this.car_rect[0].x != this.car_rect[1].x))
                break label399;
              i108 = 1;
              break label399;
            }
            if ((this.car_rect[0].angle != 90) || (this.car_rect[0].x <= this.car_rect[1].x) || (-100 + this.car_rect[0].x >= this.car_rect[1].x) || (this.car_rect[0].y != this.car_rect[1].y))
              break label399;
            i108 = 1;
            break label399;
            if ((this.car_rect[0].angle == 270) && (this.car_rect[0].x < this.car_rect[2].x))
            {
              if ((100 + this.car_rect[0].x <= this.car_rect[2].x) || (this.car_rect[0].y != this.car_rect[2].y))
                continue;
              i108 = 1;
              continue;
            }
            if ((this.car_rect[0].angle == 180) && (this.car_rect[0].y > this.car_rect[2].y))
            {
              if ((-100 + this.car_rect[0].y >= this.car_rect[2].y) || (this.car_rect[0].x != this.car_rect[2].x))
                continue;
              i108 = 1;
              continue;
            }
            if ((this.car_rect[0].angle != 90) || (this.car_rect[0].x <= this.car_rect[2].x) || (-100 + this.car_rect[0].x >= this.car_rect[2].x) || (this.car_rect[0].y != this.car_rect[2].y))
              continue;
            i108 = 1;
          }
          if ((this.car_rect[0].angle == 270) && (this.car_rect[0].x < this.car_rect[3].x))
            if ((100 + this.car_rect[0].x > this.car_rect[3].x) && (this.car_rect[0].y == this.car_rect[3].y))
              return true;
          if ((this.car_rect[0].angle != 180) || (this.car_rect[0].y <= this.car_rect[3].y))
            break;
          if ((-100 + this.car_rect[0].y < this.car_rect[3].y) && (this.car_rect[0].x == this.car_rect[3].x))
            return true;
        }
      while ((this.car_rect[0].angle != 90) || (this.car_rect[0].x <= this.car_rect[3].x) || (-100 + this.car_rect[0].x >= this.car_rect[3].x) || (this.car_rect[0].y != this.car_rect[3].y));
      return true;
    case 1:
      int i84 = this.car_rect[1].angle;
      int i85 = this.car_rect[4].angle;
      int i86 = 0;
      if (i84 == i85)
      {
        if ((this.car_rect[1].angle != 0) || (this.car_rect[1].y >= this.car_rect[4].y))
          break label2576;
        int i102 = 120 + this.car_rect[1].y;
        int i103 = this.car_rect[4].y;
        i86 = 0;
        if (i102 > i103)
        {
          int i104 = -15 + this.car_rect[1].x;
          int i105 = this.car_rect[4].x;
          i86 = 0;
          if (i104 == i105)
            i86 = 1;
        }
      }
      if (this.car_rect[1].angle == this.car_rect[5].angle)
      {
        if ((this.car_rect[1].angle != 0) || (this.car_rect[1].y >= this.car_rect[5].y))
          break label2929;
        if ((120 + this.car_rect[1].y > this.car_rect[5].y) && (-15 + this.car_rect[1].x == this.car_rect[5].x))
          i86 = 1;
      }
      if (this.car_rect[0].angle == this.car_rect[1].angle)
      {
        if ((this.car_rect[1].angle != 0) || (this.car_rect[1].y >= this.car_rect[0].y))
          break label3198;
        if ((100 + this.car_rect[1].y > this.car_rect[0].y) && (this.car_rect[1].x == this.car_rect[0].x))
          i86 = 1;
      }
      if ((this.car_rect[1].angle == this.car_rect[2].angle) && (this.car_num > 2))
      {
        if ((this.car_rect[1].angle != 0) || (this.car_rect[1].y >= this.car_rect[2].y))
          break label3458;
        if ((100 + this.car_rect[1].y > this.car_rect[2].y) && (this.car_rect[1].x == this.car_rect[2].x))
          i86 = 1;
      }
      if ((this.car_rect[1].angle == this.car_rect[3].angle) && (this.car_num > 3))
      {
        if ((this.car_rect[1].angle != 0) || (this.car_rect[1].y >= this.car_rect[3].y))
          break label3718;
        if ((100 + this.car_rect[1].y > this.car_rect[3].y) && (this.car_rect[1].x == this.car_rect[3].x))
          i86 = 1;
      }
      while (true)
      {
        return i86;
        if ((this.car_rect[1].angle == 270) && (this.car_rect[1].x < this.car_rect[4].x))
        {
          int i98 = 120 + this.car_rect[1].x;
          int i99 = this.car_rect[4].x;
          i86 = 0;
          if (i98 <= i99)
            break;
          int i100 = -10 + this.car_rect[1].y;
          int i101 = this.car_rect[4].y;
          i86 = 0;
          if (i100 != i101)
            break;
          i86 = 1;
          break;
        }
        if ((this.car_rect[1].angle == 180) && (this.car_rect[1].y > this.car_rect[4].y))
        {
          int i94 = -120 + this.car_rect[1].y;
          int i95 = this.car_rect[4].y;
          i86 = 0;
          if (i94 >= i95)
            break;
          int i96 = -15 + this.car_rect[1].x;
          int i97 = this.car_rect[4].x;
          i86 = 0;
          if (i96 != i97)
            break;
          i86 = 1;
          break;
        }
        int i87 = this.car_rect[1].angle;
        i86 = 0;
        if (i87 != 90)
          break;
        int i88 = this.car_rect[1].x;
        int i89 = this.car_rect[4].x;
        i86 = 0;
        if (i88 <= i89)
          break;
        int i90 = -120 + this.car_rect[1].x;
        int i91 = this.car_rect[4].x;
        i86 = 0;
        if (i90 >= i91)
          break;
        int i92 = -20 + this.car_rect[1].y;
        int i93 = this.car_rect[4].y;
        i86 = 0;
        if (i92 != i93)
          break;
        i86 = 1;
        break;
        if ((this.car_rect[1].angle == 270) && (this.car_rect[1].x < this.car_rect[5].x))
        {
          if ((120 + this.car_rect[1].x <= this.car_rect[5].x) || (-10 + this.car_rect[1].y != this.car_rect[5].y))
            break label2251;
          i86 = 1;
          break label2251;
        }
        if ((this.car_rect[1].angle == 180) && (this.car_rect[0].y > this.car_rect[5].y))
        {
          if ((-120 + this.car_rect[1].y >= this.car_rect[5].y) || (-15 + this.car_rect[1].x != this.car_rect[5].x))
            break label2251;
          i86 = 1;
          break label2251;
        }
        if ((this.car_rect[1].angle != 90) || (this.car_rect[1].x <= this.car_rect[5].x) || (-120 + this.car_rect[1].x >= this.car_rect[5].x) || (-20 + this.car_rect[1].y != this.car_rect[5].y))
          break label2251;
        i86 = 1;
        break label2251;
        if ((this.car_rect[1].angle == 270) && (this.car_rect[1].x < this.car_rect[0].x))
        {
          if ((100 + this.car_rect[1].x <= this.car_rect[0].x) || (this.car_rect[1].y != this.car_rect[0].y))
            break label2353;
          i86 = 1;
          break label2353;
        }
        if ((this.car_rect[1].angle == 180) && (this.car_rect[1].y > this.car_rect[0].y))
        {
          if ((-100 + this.car_rect[1].y >= this.car_rect[0].y) || (this.car_rect[1].x != this.car_rect[0].x))
            break label2353;
          i86 = 1;
          break label2353;
        }
        if ((this.car_rect[1].angle != 90) || (this.car_rect[1].x <= this.car_rect[0].x) || (-100 + this.car_rect[1].x >= this.car_rect[0].x) || (this.car_rect[1].y != this.car_rect[0].y))
          break label2353;
        i86 = 1;
        break label2353;
        if ((this.car_rect[1].angle == 270) && (this.car_rect[1].x < this.car_rect[2].x))
        {
          if ((100 + this.car_rect[1].x <= this.car_rect[2].x) || (this.car_rect[1].y != this.car_rect[2].y))
            break label2463;
          i86 = 1;
          break label2463;
        }
        if ((this.car_rect[1].angle == 180) && (this.car_rect[1].y > this.car_rect[2].y))
        {
          if ((-100 + this.car_rect[1].y >= this.car_rect[2].y) || (this.car_rect[1].x != this.car_rect[2].x))
            break label2463;
          i86 = 1;
          break label2463;
        }
        if ((this.car_rect[1].angle != 90) || (this.car_rect[1].x <= this.car_rect[2].x) || (-100 + this.car_rect[1].x >= this.car_rect[2].x) || (this.car_rect[1].y != this.car_rect[2].y))
          break label2463;
        i86 = 1;
        break label2463;
        if ((this.car_rect[1].angle == 270) && (this.car_rect[1].x < this.car_rect[3].x))
        {
          if ((100 + this.car_rect[1].x <= this.car_rect[3].x) || (this.car_rect[1].y != this.car_rect[3].y))
            continue;
          i86 = 1;
          continue;
        }
        if ((this.car_rect[1].angle == 180) && (this.car_rect[1].y > this.car_rect[3].y))
        {
          if ((-100 + this.car_rect[1].y >= this.car_rect[3].y) || (this.car_rect[1].x != this.car_rect[3].x))
            continue;
          i86 = 1;
          continue;
        }
        if ((this.car_rect[1].angle != 90) || (this.car_rect[1].x <= this.car_rect[3].x) || (-100 + this.car_rect[1].x >= this.car_rect[3].x) || (this.car_rect[1].y != this.car_rect[3].y))
          continue;
        i86 = 1;
      }
    case 2:
      int i62 = this.car_rect[2].angle;
      int i63 = this.car_rect[4].angle;
      int i64 = 0;
      if (i62 == i63)
      {
        if ((this.car_rect[2].angle != 0) || (this.car_rect[2].y >= this.car_rect[4].y))
          break label4538;
        int i80 = 120 + this.car_rect[2].y;
        int i81 = this.car_rect[4].y;
        i64 = 0;
        if (i80 > i81)
        {
          int i82 = -15 + this.car_rect[2].x;
          int i83 = this.car_rect[4].x;
          i64 = 0;
          if (i82 == i83)
            i64 = 1;
        }
      }
      if (this.car_rect[2].angle == this.car_rect[5].angle)
      {
        if ((this.car_rect[2].angle != 0) || (this.car_rect[2].y >= this.car_rect[5].y))
          break label4891;
        if ((120 + this.car_rect[2].y > this.car_rect[5].y) && (-15 + this.car_rect[2].x == this.car_rect[5].x))
          i64 = 1;
      }
      if (this.car_rect[2].angle == this.car_rect[0].angle)
      {
        if ((this.car_rect[2].angle != 0) || (this.car_rect[2].y >= this.car_rect[0].y))
          break label5160;
        if ((100 + this.car_rect[2].y > this.car_rect[0].y) && (this.car_rect[2].x == this.car_rect[0].x))
          i64 = 1;
      }
      if (this.car_rect[2].angle == this.car_rect[1].angle)
      {
        if ((this.car_rect[2].angle != 0) || (this.car_rect[2].y >= this.car_rect[1].y))
          break label5420;
        if ((100 + this.car_rect[2].y > this.car_rect[1].y) && (this.car_rect[2].x == this.car_rect[1].x))
          i64 = 1;
      }
      if ((this.car_rect[2].angle == this.car_rect[3].angle) && (this.car_num > 3))
      {
        if ((this.car_rect[2].angle != 0) || (this.car_rect[2].y >= this.car_rect[3].y))
          break label5680;
        if ((100 + this.car_rect[2].y > this.car_rect[3].y) && (this.car_rect[2].x == this.car_rect[3].x))
          i64 = 1;
      }
      while (true)
      {
        return i64;
        if ((this.car_rect[2].angle == 270) && (this.car_rect[2].x < this.car_rect[4].x))
        {
          int i76 = 120 + this.car_rect[2].x;
          int i77 = this.car_rect[4].x;
          i64 = 0;
          if (i76 <= i77)
            break;
          int i78 = -10 + this.car_rect[2].y;
          int i79 = this.car_rect[4].y;
          i64 = 0;
          if (i78 != i79)
            break;
          i64 = 1;
          break;
        }
        if ((this.car_rect[2].angle == 180) && (this.car_rect[2].y > this.car_rect[4].y))
        {
          int i72 = -120 + this.car_rect[2].y;
          int i73 = this.car_rect[4].y;
          i64 = 0;
          if (i72 >= i73)
            break;
          int i74 = -15 + this.car_rect[2].x;
          int i75 = this.car_rect[4].x;
          i64 = 0;
          if (i74 != i75)
            break;
          i64 = 1;
          break;
        }
        int i65 = this.car_rect[2].angle;
        i64 = 0;
        if (i65 != 90)
          break;
        int i66 = this.car_rect[2].x;
        int i67 = this.car_rect[4].x;
        i64 = 0;
        if (i66 <= i67)
          break;
        int i68 = -120 + this.car_rect[2].x;
        int i69 = this.car_rect[4].x;
        i64 = 0;
        if (i68 >= i69)
          break;
        int i70 = -20 + this.car_rect[2].y;
        int i71 = this.car_rect[4].y;
        i64 = 0;
        if (i70 != i71)
          break;
        i64 = 1;
        break;
        if ((this.car_rect[2].angle == 270) && (this.car_rect[2].x < this.car_rect[5].x))
        {
          if ((120 + this.car_rect[2].x <= this.car_rect[5].x) || (-10 + this.car_rect[2].y != this.car_rect[5].y))
            break label4221;
          i64 = 1;
          break label4221;
        }
        if ((this.car_rect[2].angle == 180) && (this.car_rect[2].y > this.car_rect[5].y))
        {
          if ((-120 + this.car_rect[2].y >= this.car_rect[5].y) || (-15 + this.car_rect[2].x != this.car_rect[5].x))
            break label4221;
          i64 = 1;
          break label4221;
        }
        if ((this.car_rect[2].angle != 90) || (this.car_rect[2].x <= this.car_rect[5].x) || (-120 + this.car_rect[2].x >= this.car_rect[5].x) || (-20 + this.car_rect[2].y != this.car_rect[5].y))
          break label4221;
        i64 = 1;
        break label4221;
        if ((this.car_rect[2].angle == 270) && (this.car_rect[2].x < this.car_rect[0].x))
        {
          if ((100 + this.car_rect[2].x <= this.car_rect[0].x) || (this.car_rect[2].y != this.car_rect[0].y))
            break label4323;
          i64 = 1;
          break label4323;
        }
        if ((this.car_rect[2].angle == 180) && (this.car_rect[2].y > this.car_rect[0].y))
        {
          if ((-100 + this.car_rect[2].y >= this.car_rect[0].y) || (this.car_rect[2].x != this.car_rect[0].x))
            break label4323;
          i64 = 1;
          break label4323;
        }
        if ((this.car_rect[2].angle != 90) || (this.car_rect[2].x <= this.car_rect[0].x) || (-100 + this.car_rect[2].x >= this.car_rect[0].x) || (this.car_rect[2].y != this.car_rect[0].y))
          break label4323;
        i64 = 1;
        break label4323;
        if ((this.car_rect[2].angle == 270) && (this.car_rect[2].x < this.car_rect[1].x))
        {
          if ((100 + this.car_rect[2].x <= this.car_rect[1].x) || (this.car_rect[2].y != this.car_rect[1].y))
            break label4425;
          i64 = 1;
          break label4425;
        }
        if ((this.car_rect[2].angle == 180) && (this.car_rect[2].y > this.car_rect[1].y))
        {
          if ((-100 + this.car_rect[2].y >= this.car_rect[1].y) || (this.car_rect[2].x != this.car_rect[1].x))
            break label4425;
          i64 = 1;
          break label4425;
        }
        if ((this.car_rect[2].angle != 90) || (this.car_rect[2].x <= this.car_rect[1].x) || (-100 + this.car_rect[2].x >= this.car_rect[1].x) || (this.car_rect[2].y != this.car_rect[1].y))
          break label4425;
        i64 = 1;
        break label4425;
        if ((this.car_rect[2].angle == 270) && (this.car_rect[2].x < this.car_rect[3].x))
        {
          if ((100 + this.car_rect[2].x <= this.car_rect[3].x) || (this.car_rect[2].y != this.car_rect[3].y))
            continue;
          i64 = 1;
          continue;
        }
        if ((this.car_rect[2].angle == 180) && (this.car_rect[2].y > this.car_rect[3].y))
        {
          if ((-100 + this.car_rect[2].y >= this.car_rect[3].y) || (this.car_rect[2].x != this.car_rect[3].x))
            continue;
          i64 = 1;
          continue;
        }
        if ((this.car_rect[2].angle != 90) || (this.car_rect[2].x <= this.car_rect[3].x) || (-100 + this.car_rect[2].x >= this.car_rect[3].x) || (this.car_rect[2].y != this.car_rect[3].y))
          continue;
        i64 = 1;
      }
    case 3:
      int i40 = this.car_rect[3].angle;
      int i41 = this.car_rect[4].angle;
      int i42 = 0;
      if (i40 == i41)
      {
        if ((this.car_rect[3].angle != 0) || (this.car_rect[3].y >= this.car_rect[4].y))
          break label6492;
        int i58 = 120 + this.car_rect[3].y;
        int i59 = this.car_rect[4].y;
        i42 = 0;
        if (i58 > i59)
        {
          int i60 = -15 + this.car_rect[3].x;
          int i61 = this.car_rect[4].x;
          i42 = 0;
          if (i60 == i61)
            i42 = 1;
        }
      }
      if (this.car_rect[3].angle == this.car_rect[5].angle)
      {
        if ((this.car_rect[3].angle != 0) || (this.car_rect[3].y >= this.car_rect[5].y))
          break label6845;
        if ((120 + this.car_rect[3].y > this.car_rect[5].y) && (-15 + this.car_rect[3].x == this.car_rect[5].x))
          i42 = 1;
      }
      if (this.car_rect[3].angle == this.car_rect[1].angle)
      {
        if ((this.car_rect[3].angle != 0) || (this.car_rect[3].y >= this.car_rect[1].y))
          break label7114;
        if ((100 + this.car_rect[3].y > this.car_rect[1].y) && (this.car_rect[3].x == this.car_rect[1].x))
          i42 = 1;
      }
      if (this.car_rect[3].angle == this.car_rect[2].angle)
      {
        if ((this.car_rect[3].angle != 0) || (this.car_rect[3].y >= this.car_rect[2].y))
          break label7374;
        if ((100 + this.car_rect[3].y > this.car_rect[2].y) && (this.car_rect[3].x == this.car_rect[2].x))
          i42 = 1;
      }
      if (this.car_rect[3].angle == this.car_rect[0].angle)
      {
        if ((this.car_rect[3].angle != 0) || (this.car_rect[0].y >= this.car_rect[0].y))
          break label7634;
        if ((100 + this.car_rect[3].y > this.car_rect[0].y) && (this.car_rect[3].x == this.car_rect[0].x))
          i42 = 1;
      }
      while (true)
      {
        return i42;
        if ((this.car_rect[3].angle == 270) && (this.car_rect[3].x < this.car_rect[4].x))
        {
          int i54 = 120 + this.car_rect[3].x;
          int i55 = this.car_rect[4].x;
          i42 = 0;
          if (i54 <= i55)
            break;
          int i56 = -10 + this.car_rect[3].y;
          int i57 = this.car_rect[4].y;
          i42 = 0;
          if (i56 != i57)
            break;
          i42 = 1;
          break;
        }
        if ((this.car_rect[3].angle == 180) && (this.car_rect[3].y > this.car_rect[4].y))
        {
          int i50 = -120 + this.car_rect[3].y;
          int i51 = this.car_rect[4].y;
          i42 = 0;
          if (i50 >= i51)
            break;
          int i52 = -15 + this.car_rect[3].x;
          int i53 = this.car_rect[4].x;
          i42 = 0;
          if (i52 != i53)
            break;
          i42 = 1;
          break;
        }
        int i43 = this.car_rect[3].angle;
        i42 = 0;
        if (i43 != 90)
          break;
        int i44 = this.car_rect[3].x;
        int i45 = this.car_rect[4].x;
        i42 = 0;
        if (i44 <= i45)
          break;
        int i46 = -120 + this.car_rect[3].x;
        int i47 = this.car_rect[4].x;
        i42 = 0;
        if (i46 >= i47)
          break;
        int i48 = -20 + this.car_rect[3].y;
        int i49 = this.car_rect[4].y;
        i42 = 0;
        if (i48 != i49)
          break;
        i42 = 1;
        break;
        if ((this.car_rect[3].angle == 270) && (this.car_rect[3].x < this.car_rect[5].x))
        {
          if ((120 + this.car_rect[3].x <= this.car_rect[5].x) || (-10 + this.car_rect[3].y != this.car_rect[5].y))
            break label6183;
          i42 = 1;
          break label6183;
        }
        if ((this.car_rect[3].angle == 180) && (this.car_rect[3].y > this.car_rect[5].y))
        {
          if ((-120 + this.car_rect[3].y >= this.car_rect[5].y) || (-15 + this.car_rect[3].x != this.car_rect[5].x))
            break label6183;
          i42 = 1;
          break label6183;
        }
        if ((this.car_rect[3].angle != 90) || (this.car_rect[3].x <= this.car_rect[5].x) || (-120 + this.car_rect[3].x >= this.car_rect[5].x) || (-20 + this.car_rect[3].y != this.car_rect[5].y))
          break label6183;
        i42 = 1;
        break label6183;
        if ((this.car_rect[3].angle == 270) && (this.car_rect[3].x < this.car_rect[1].x))
        {
          if ((100 + this.car_rect[3].x <= this.car_rect[1].x) || (this.car_rect[3].y != this.car_rect[1].y))
            break label6285;
          i42 = 1;
          break label6285;
        }
        if ((this.car_rect[3].angle == 180) && (this.car_rect[3].y > this.car_rect[1].y))
        {
          if ((-100 + this.car_rect[3].y >= this.car_rect[1].y) || (this.car_rect[3].x != this.car_rect[1].x))
            break label6285;
          i42 = 1;
          break label6285;
        }
        if ((this.car_rect[3].angle != 90) || (this.car_rect[3].x <= this.car_rect[1].x) || (-100 + this.car_rect[3].x >= this.car_rect[1].x) || (this.car_rect[3].y != this.car_rect[1].y))
          break label6285;
        i42 = 1;
        break label6285;
        if ((this.car_rect[3].angle == 270) && (this.car_rect[3].x < this.car_rect[2].x))
        {
          if ((100 + this.car_rect[3].x <= this.car_rect[2].x) || (this.car_rect[3].y != this.car_rect[2].y))
            break label6387;
          i42 = 1;
          break label6387;
        }
        if ((this.car_rect[3].angle == 180) && (this.car_rect[3].y > this.car_rect[2].y))
        {
          if ((-100 + this.car_rect[3].y >= this.car_rect[2].y) || (this.car_rect[3].x != this.car_rect[2].x))
            break label6387;
          i42 = 1;
          break label6387;
        }
        if ((this.car_rect[3].angle != 90) || (this.car_rect[3].x <= this.car_rect[2].x) || (-100 + this.car_rect[3].x >= this.car_rect[2].x) || (this.car_rect[3].y != this.car_rect[2].y))
          break label6387;
        i42 = 1;
        break label6387;
        if ((this.car_rect[3].angle == 270) && (this.car_rect[3].x < this.car_rect[0].x))
        {
          if ((100 + this.car_rect[3].x <= this.car_rect[0].x) || (this.car_rect[3].y != this.car_rect[0].y))
            continue;
          i42 = 1;
          continue;
        }
        if ((this.car_rect[3].angle == 180) && (this.car_rect[3].y > this.car_rect[0].y))
        {
          if ((-100 + this.car_rect[3].y >= this.car_rect[0].y) || (this.car_rect[3].x != this.car_rect[0].x))
            continue;
          i42 = 1;
          continue;
        }
        if ((this.car_rect[3].angle != 90) || (this.car_rect[3].x <= this.car_rect[0].x) || (-100 + this.car_rect[3].x >= this.car_rect[0].x) || (this.car_rect[3].y != this.car_rect[0].y))
          continue;
        i42 = 1;
      }
    case 4:
      label184: label618: label2929: label3198: int i18 = this.car_rect[4].angle;
      label289: label971: label1240: label1500: label1760: label4221: int i19 = this.car_rect[5].angle;
      label399: label2251: label4323: label6387: int i20 = 0;
      label2353: label4425: label6492: if (i18 == i19)
      {
        if ((this.car_rect[4].angle != 0) || (this.car_rect[4].y >= this.car_rect[5].y))
          break label8476;
        int i36 = 100 + this.car_rect[4].y;
        int i37 = this.car_rect[5].y;
        i20 = 0;
        if (i36 > i37)
        {
          int i38 = this.car_rect[4].x;
          int i39 = this.car_rect[5].x;
          i20 = 0;
          if (i38 == i39)
            i20 = 1;
        }
      }
      label2463: label4538: label6845: label7114: label7374: label7634: if (this.car_rect[4].angle == this.car_rect[0].angle)
      {
        if ((this.car_rect[4].angle != 0) || (this.car_rect[4].y >= this.car_rect[0].y))
          break label8820;
        if ((100 + this.car_rect[4].y > this.car_rect[0].y) && (15 + this.car_rect[4].x == this.car_rect[0].x))
          i20 = 1;
      }
      label2576: label4891: label5160: label5420: label5680: label8134: if ((this.car_rect[4].angle == this.car_rect[1].angle) && (this.car_num > 1))
      {
        if ((this.car_rect[4].angle != 0) || (this.car_rect[4].y >= this.car_rect[1].y))
          break label9089;
        if ((100 + this.car_rect[4].y > this.car_rect[1].y) && (15 + this.car_rect[4].x == this.car_rect[1].x))
          i20 = 1;
      }
      label3458: label3718: label6183: label8247: if ((this.car_rect[4].angle == this.car_rect[2].angle) && (this.car_num > 2))
      {
        if ((this.car_rect[4].angle != 0) || (this.car_rect[4].y >= this.car_rect[2].y))
          break label9358;
        if ((100 + this.car_rect[4].y > this.car_rect[2].y) && (15 + this.car_rect[4].x == this.car_rect[2].x))
          i20 = 1;
      }
      label6285: label8360: if ((this.car_rect[4].angle == this.car_rect[3].angle) && (this.car_num > 3))
      {
        if ((this.car_rect[4].angle != 0) || (this.car_rect[4].y >= this.car_rect[3].y))
          break label9627;
        if ((100 + this.car_rect[4].y > this.car_rect[3].y) && (15 + this.car_rect[4].x == this.car_rect[3].x))
          i20 = 1;
      }
      while (true)
      {
        return i20;
        label8476: if ((this.car_rect[4].angle == 270) && (this.car_rect[4].x < this.car_rect[5].x))
        {
          int i32 = 100 + this.car_rect[4].x;
          int i33 = this.car_rect[5].x;
          i20 = 0;
          if (i32 <= i33)
            break;
          int i34 = this.car_rect[4].y;
          int i35 = this.car_rect[5].y;
          i20 = 0;
          if (i34 != i35)
            break;
          i20 = 1;
          break;
        }
        if ((this.car_rect[4].angle == 180) && (this.car_rect[4].y > this.car_rect[5].y))
        {
          int i28 = -100 + this.car_rect[4].y;
          int i29 = this.car_rect[5].y;
          i20 = 0;
          if (i28 >= i29)
            break;
          int i30 = this.car_rect[4].x;
          int i31 = this.car_rect[5].x;
          i20 = 0;
          if (i30 != i31)
            break;
          i20 = 1;
          break;
        }
        int i21 = this.car_rect[4].angle;
        i20 = 0;
        if (i21 != 90)
          break;
        int i22 = this.car_rect[4].x;
        int i23 = this.car_rect[5].x;
        i20 = 0;
        if (i22 <= i23)
          break;
        int i24 = -100 + this.car_rect[4].x;
        int i25 = this.car_rect[5].x;
        i20 = 0;
        if (i24 >= i25)
          break;
        int i26 = this.car_rect[4].y;
        int i27 = this.car_rect[5].y;
        i20 = 0;
        if (i26 != i27)
          break;
        i20 = 1;
        break;
        label8820: if ((this.car_rect[4].angle == 270) && (this.car_rect[4].x < this.car_rect[0].x))
        {
          if ((100 + this.car_rect[4].x <= this.car_rect[0].x) || (10 + this.car_rect[4].y != this.car_rect[0].y))
            break label8134;
          i20 = 1;
          break label8134;
        }
        if ((this.car_rect[4].angle == 180) && (this.car_rect[4].y > this.car_rect[0].y))
        {
          if ((-100 + this.car_rect[4].y >= this.car_rect[0].y) || (15 + this.car_rect[4].x != this.car_rect[0].x))
            break label8134;
          i20 = 1;
          break label8134;
        }
        if ((this.car_rect[4].angle != 90) || (this.car_rect[4].x <= this.car_rect[0].x) || (-100 + this.car_rect[4].x >= this.car_rect[0].x) || (20 + this.car_rect[4].y != this.car_rect[0].y))
          break label8134;
        i20 = 1;
        break label8134;
        label9089: if ((this.car_rect[4].angle == 270) && (this.car_rect[4].x < this.car_rect[1].x))
        {
          if ((100 + this.car_rect[4].x <= this.car_rect[1].x) || (10 + this.car_rect[4].y != this.car_rect[1].y))
            break label8247;
          i20 = 1;
          break label8247;
        }
        if ((this.car_rect[4].angle == 180) && (this.car_rect[4].y > this.car_rect[1].y))
        {
          if ((-100 + this.car_rect[4].y >= this.car_rect[1].y) || (15 + this.car_rect[4].x != this.car_rect[1].x))
            break label8247;
          i20 = 1;
          break label8247;
        }
        if ((this.car_rect[4].angle != 90) || (this.car_rect[4].x <= this.car_rect[1].x) || (-100 + this.car_rect[4].x >= this.car_rect[1].x) || (20 + this.car_rect[4].y != this.car_rect[1].y))
          break label8247;
        i20 = 1;
        break label8247;
        label9358: if ((this.car_rect[4].angle == 270) && (this.car_rect[4].x < this.car_rect[2].x))
        {
          if ((100 + this.car_rect[4].x <= this.car_rect[2].x) || (10 + this.car_rect[4].y != this.car_rect[2].y))
            break label8360;
          i20 = 1;
          break label8360;
        }
        if ((this.car_rect[4].angle == 180) && (this.car_rect[4].y > this.car_rect[2].y))
        {
          if ((-100 + this.car_rect[4].y >= this.car_rect[2].y) || (15 + this.car_rect[4].x != this.car_rect[2].x))
            break label8360;
          i20 = 1;
          break label8360;
        }
        if ((this.car_rect[4].angle != 90) || (this.car_rect[4].x <= this.car_rect[2].x) || (-100 + this.car_rect[4].x >= this.car_rect[2].x) || (20 + this.car_rect[4].y != this.car_rect[2].y))
          break label8360;
        i20 = 1;
        break label8360;
        label9627: if ((this.car_rect[4].angle == 270) && (this.car_rect[4].x < this.car_rect[3].x))
        {
          if ((100 + this.car_rect[4].x <= this.car_rect[3].x) || (10 + this.car_rect[4].y != this.car_rect[3].y))
            continue;
          i20 = 1;
          continue;
        }
        if ((this.car_rect[4].angle == 180) && (this.car_rect[4].y > this.car_rect[3].y))
        {
          if ((-100 + this.car_rect[4].y >= this.car_rect[3].y) || (15 + this.car_rect[4].x != this.car_rect[3].x))
            continue;
          i20 = 1;
          continue;
        }
        if ((this.car_rect[4].angle != 90) || (this.car_rect[4].x <= this.car_rect[3].x) || (-100 + this.car_rect[4].x >= this.car_rect[3].x) || (20 + this.car_rect[4].y != this.car_rect[3].y))
          continue;
        i20 = 1;
      }
    case 5:
    }
    int i = this.car_rect[5].angle;
    int j = this.car_rect[4].angle;
    int k = 0;
    if (i == j)
    {
      if ((this.car_rect[5].angle != 0) || (this.car_rect[5].y >= this.car_rect[4].y))
        break label10474;
      int i14 = 100 + this.car_rect[5].y;
      int i15 = this.car_rect[4].y;
      k = 0;
      if (i14 > i15)
      {
        int i16 = this.car_rect[5].x;
        int i17 = this.car_rect[4].x;
        k = 0;
        if (i16 == i17)
          k = 1;
      }
    }
    if (this.car_rect[5].angle == this.car_rect[0].angle)
    {
      if ((this.car_rect[5].angle != 0) || (this.car_rect[5].y >= this.car_rect[0].y))
        break label10818;
      if ((100 + this.car_rect[5].y > this.car_rect[0].y) && (15 + this.car_rect[5].x == this.car_rect[0].x))
        k = 1;
    }
    label10132: if ((this.car_rect[5].angle == this.car_rect[1].angle) && (this.car_num > 1))
    {
      if ((this.car_rect[5].angle != 0) || (this.car_rect[5].y >= this.car_rect[1].y))
        break label11087;
      if ((100 + this.car_rect[5].y > this.car_rect[1].y) && (15 + this.car_rect[5].x == this.car_rect[1].x))
        k = 1;
    }
    label10245: if ((this.car_rect[5].angle == this.car_rect[2].angle) && (this.car_num > 2))
    {
      if ((this.car_rect[5].angle != 0) || (this.car_rect[5].y >= this.car_rect[2].y))
        break label11356;
      if ((100 + this.car_rect[5].y > this.car_rect[2].y) && (15 + this.car_rect[5].x == this.car_rect[2].x))
        k = 1;
    }
    label10358: if ((this.car_rect[5].angle == this.car_rect[3].angle) && (this.car_num > 3))
    {
      if ((this.car_rect[5].angle != 0) || (this.car_rect[5].y >= this.car_rect[3].y))
        break label11625;
      if ((100 + this.car_rect[5].y > this.car_rect[3].y) && (15 + this.car_rect[5].x == this.car_rect[3].x))
        k = 1;
    }
    while (true)
    {
      return k;
      label10474: if ((this.car_rect[5].angle == 270) && (this.car_rect[5].x < this.car_rect[4].x))
      {
        int i10 = 100 + this.car_rect[5].x;
        int i11 = this.car_rect[4].x;
        k = 0;
        if (i10 <= i11)
          break;
        int i12 = this.car_rect[5].y;
        int i13 = this.car_rect[4].y;
        k = 0;
        if (i12 != i13)
          break;
        k = 1;
        break;
      }
      if ((this.car_rect[5].angle == 180) && (this.car_rect[5].y > this.car_rect[4].y))
      {
        int i6 = -100 + this.car_rect[5].y;
        int i7 = this.car_rect[4].y;
        k = 0;
        if (i6 >= i7)
          break;
        int i8 = this.car_rect[5].x;
        int i9 = this.car_rect[4].x;
        k = 0;
        if (i8 != i9)
          break;
        k = 1;
        break;
      }
      int m = this.car_rect[5].angle;
      k = 0;
      if (m != 90)
        break;
      int n = this.car_rect[5].x;
      int i1 = this.car_rect[4].x;
      k = 0;
      if (n <= i1)
        break;
      int i2 = -100 + this.car_rect[5].x;
      int i3 = this.car_rect[4].x;
      k = 0;
      if (i2 >= i3)
        break;
      int i4 = this.car_rect[5].y;
      int i5 = this.car_rect[4].y;
      k = 0;
      if (i4 != i5)
        break;
      k = 1;
      break;
      label10818: if ((this.car_rect[5].angle == 270) && (this.car_rect[5].x < this.car_rect[0].x))
      {
        if ((100 + this.car_rect[5].x <= this.car_rect[0].x) || (10 + this.car_rect[5].y != this.car_rect[0].y))
          break label10132;
        k = 1;
        break label10132;
      }
      if ((this.car_rect[5].angle == 180) && (this.car_rect[5].y > this.car_rect[0].y))
      {
        if ((-100 + this.car_rect[5].y >= this.car_rect[0].y) || (15 + this.car_rect[5].x != this.car_rect[0].x))
          break label10132;
        k = 1;
        break label10132;
      }
      if ((this.car_rect[5].angle != 90) || (this.car_rect[5].x <= this.car_rect[0].x) || (-100 + this.car_rect[5].x >= this.car_rect[0].x) || (20 + this.car_rect[5].y != this.car_rect[0].y))
        break label10132;
      k = 1;
      break label10132;
      label11087: if ((this.car_rect[5].angle == 270) && (this.car_rect[5].x < this.car_rect[1].x))
      {
        if ((100 + this.car_rect[5].x <= this.car_rect[1].x) || (10 + this.car_rect[5].y != this.car_rect[1].y))
          break label10245;
        k = 1;
        break label10245;
      }
      if ((this.car_rect[5].angle == 180) && (this.car_rect[5].y > this.car_rect[1].y))
      {
        if ((-100 + this.car_rect[5].y >= this.car_rect[1].y) || (15 + this.car_rect[5].x != this.car_rect[1].x))
          break label10245;
        k = 1;
        break label10245;
      }
      if ((this.car_rect[5].angle != 90) || (this.car_rect[5].x <= this.car_rect[1].x) || (-100 + this.car_rect[5].x >= this.car_rect[1].x) || (20 + this.car_rect[5].y != this.car_rect[1].y))
        break label10245;
      k = 1;
      break label10245;
      label11356: if ((this.car_rect[5].angle == 270) && (this.car_rect[5].x < this.car_rect[2].x))
      {
        if ((100 + this.car_rect[5].x <= this.car_rect[2].x) || (10 + this.car_rect[5].y != this.car_rect[2].y))
          break label10358;
        k = 1;
        break label10358;
      }
      if ((this.car_rect[5].angle == 180) && (this.car_rect[5].y > this.car_rect[2].y))
      {
        if ((-100 + this.car_rect[5].y >= this.car_rect[2].y) || (15 + this.car_rect[5].x != this.car_rect[2].x))
          break label10358;
        k = 1;
        break label10358;
      }
      if ((this.car_rect[5].angle != 90) || (this.car_rect[5].x <= this.car_rect[2].x) || (-100 + this.car_rect[5].x >= this.car_rect[2].x) || (20 + this.car_rect[5].y != this.car_rect[2].y))
        break label10358;
      k = 1;
      break label10358;
      label11625: if ((this.car_rect[5].angle == 270) && (this.car_rect[5].x < this.car_rect[3].x))
      {
        if ((100 + this.car_rect[5].x <= this.car_rect[3].x) || (10 + this.car_rect[5].y != this.car_rect[3].y))
          continue;
        k = 1;
        continue;
      }
      if ((this.car_rect[5].angle == 180) && (this.car_rect[5].y > this.car_rect[3].y))
      {
        if ((-100 + this.car_rect[5].y >= this.car_rect[3].y) || (15 + this.car_rect[5].x != this.car_rect[3].x))
          continue;
        k = 1;
        continue;
      }
      if ((this.car_rect[5].angle != 90) || (this.car_rect[5].x <= this.car_rect[3].x) || (-100 + this.car_rect[5].x >= this.car_rect[3].x) || (20 + this.car_rect[5].y != this.car_rect[3].y))
        continue;
      k = 1;
    }
  }

  private void sendMsg(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mCarHandler != null)
    {
      Message localMessage = new Message();
      localMessage.what = paramInt2;
      Bundle localBundle = new Bundle();
      localBundle.putInt("car_id", paramInt1);
      localMessage.setData(localBundle);
      this.mCarHandler.sendMessageDelayed(localMessage, paramInt3);
    }
  }

  public void cleanPaintPoint2(int paramInt)
  {
    if (paramInt == 0);
    for (int i = this.car_Random_id; ; i = paramInt)
    {
      int j = this.car_point[i].direct;
      int k = this.car_point[i].angle;
      this.car_point[i].x = 0;
      this.car_point[i].y = 0;
      checkCarRect2(paramInt, 0, 0, j, k);
      return;
    }
  }

  public void getBusStationInfo(int paramInt1, int paramInt2)
  {
    this.station.showInfo(paramInt1, paramInt2);
  }

  public RECT getCarCurrentPoint(int paramInt)
  {
    RECT localRECT = new RECT();
    if (paramInt == 0)
      paramInt = this.car_Random_id;
    localRECT.direct = this.car_point[paramInt].direct;
    localRECT.angle = this.car_point[paramInt].angle;
    localRECT.x = this.car_point[paramInt].x;
    localRECT.y = this.car_point[paramInt].y;
    return localRECT;
  }

  public RECT getCarCurrentRect(int paramInt)
  {
    return this.car_rect[paramInt];
  }

  public void getCarLoadPath(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
  }

  public int getCarLocation(int paramInt)
  {
    int i = Utils.CAR_RECT[paramInt].length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return paramInt;
      if ((Utils.CAR_LOCATON[j][0] != Utils.CAR_RECT[paramInt][j][0]) || (Utils.CAR_LOCATON[j][1] != Utils.CAR_RECT[paramInt][j][1]))
        continue;
      this.car_point[paramInt].x = j;
      this.car_point[paramInt].direct = Utils.CAR_RECT[paramInt][j][2];
      this.car_point[paramInt].angle = Utils.CAR_RECT[paramInt][j][3];
      return paramInt;
    }
  }

  public int getCarPathId(int paramInt)
  {
    return this.car_path_id[paramInt];
  }

  public void getCarRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.map.clear();
    this.map.put("car_id", Integer.valueOf(paramInt1));
    this.map.put("x_point", Integer.valueOf(paramInt3 * 100));
    this.map.put("y_point", Integer.valueOf(paramInt2 * 90));
    this.map.put("angle", Integer.valueOf(paramInt5));
    this.map.put("direct", Integer.valueOf(paramInt4));
    Utils.sendMsgToHandle(this.m_Handler, Utils.CAR_MESSAGE_WAHT[paramInt1], this.map, 10);
  }

  public void getCarRect2(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.map.clear();
    this.map.put("car_id", Integer.valueOf(paramInt1));
    this.map.put("x_point", Integer.valueOf(paramInt2));
    this.map.put("y_point", Integer.valueOf(paramInt3));
    this.map.put("angle", Integer.valueOf(paramInt5));
    this.map.put("direct", Integer.valueOf(paramInt4));
    this.car_rect[paramInt1].x = paramInt2;
    this.car_rect[paramInt1].y = paramInt3;
    this.car_rect[paramInt1].direct = paramInt4;
    this.car_rect[paramInt1].angle = paramInt5;
    Utils.sendMsgToHandle(this.m_Handler, Utils.CAR_MESSAGE_WAHT[paramInt1], this.map, 10);
  }

  public int getCarSpeed(int paramInt)
  {
    return this.carSpeed;
  }

  public void initRect(Context paramContext)
  {
    this.sharedPrefs = paramContext.getSharedPreferences("car_traffic_info", 0);
    for (int i = 0; ; i++)
    {
      if (i >= 5)
      {
        int k = this.sharedPrefs.getInt("lot_cost", 2);
        int m = this.sharedPrefs.getInt("etc_cost", 5);
        this.factory.setFactoryCost(1, "Count", k);
        this.factory.setFactoryCost(0, "Count", m);
        this.car_num = this.sharedPrefs.getInt("car_num", 1);
        return;
      }
      int j = this.sharedPrefs.getInt("traffic_time_" + i, 5);
      this.traffic.setTime(i, "red", j * 1000);
      this.traffic.setTime(i, "yellow", j * 1000);
      this.traffic.setTime(i, "green", j * 1000);
    }
  }

  public void initXYValue()
  {
    int i = 0;
    int j;
    label10: int k;
    if (i >= 6)
    {
      j = 0;
      if (j < 6)
        break label135;
      k = 0;
      label18: if (k < 6)
        break label210;
    }
    for (int m = 0; ; m++)
    {
      if (m >= 6)
      {
        this.car_num = this.sharedPrefs.getInt("car_num", 1);
        return;
        this.car_rect[i] = new RECT();
        this.car_rect[i].x = Utils.CAR_DEFAULT[i][0];
        this.car_rect[i].y = Utils.CAR_DEFAULT[i][1];
        this.car_rect[i].direct = Utils.CAR_DEFAULT[i][2];
        this.car_rect[i].angle = Utils.CAR_DEFAULT[i][3];
        i++;
        break;
        label135: this.car_point[j] = new RECT();
        this.car_point[j].x = 0;
        this.car_point[j].y = 0;
        this.car_point[j].direct = Utils.CAR_RECT[j][0][2];
        this.car_point[j].angle = Utils.CAR_RECT[j][0][3];
        j++;
        break label10;
        label210: this.y_value[k] = Utils.CAR_RECT[k][0][1];
        this.x_value[k] = Utils.CAR_RECT[k][0][0];
        k++;
        break label18;
      }
      this.car_path_id[m] = m;
    }
  }

  public void run(int paramInt)
  {
    bRun = true;
    sendMsg(paramInt, Utils.CAR_MESSAGE_WAHT[(paramInt + 6)], 100);
  }

  public void runCarHandlerMsg(int paramInt)
  {
    sendMsg(paramInt, Utils.CAR_MESSAGE_WAHT[(paramInt + 6)], 10);
  }

  public void setCarPathId(int paramInt1, int paramInt2)
  {
    this.car_path_id[paramInt1] = paramInt2;
  }

  public void setPaintPoint2(int paramInt1, int paramInt2)
  {
    int i;
    int j;
    if (paramInt1 == 0)
    {
      i = this.car_Random_id;
      j = Utils.CAR_RECT[i].length;
    }
    for (int k = 0; ; k++)
    {
      if (k >= j)
      {
        return;
        i = paramInt1;
        break;
      }
      if ((Utils.CAR_LOCATON[paramInt2][0] != Utils.CAR_RECT[i][k][0]) || (Utils.CAR_LOCATON[paramInt2][1] != Utils.CAR_RECT[i][k][1]))
        continue;
      this.car_point[i].x = k;
      this.car_point[i].direct = Utils.CAR_RECT[i][k][2];
      this.car_point[i].angle = Utils.CAR_RECT[i][k][3];
      return;
    }
  }

  public void setRandomID(int paramInt)
  {
    this.car_Random_id = paramInt;
  }

  public void stop()
  {
    bRun = false;
    this.bRandom = false;
    if (this.mCarHandler != null)
    {
      this.mCarHandler.removeMessages(2001);
      this.mCarHandler.removeMessages(2002);
      this.mCarHandler.removeMessages(2003);
      this.mCarHandler.removeMessages(2004);
      this.mCarHandler.removeMessages(2005);
      this.mCarHandler.removeMessages(2006);
    }
  }

  public void stopCarHandlerMsg(int paramInt)
  {
    if (this.mCarHandler != null)
      this.mCarHandler.removeMessages(Utils.CAR_MESSAGE_WAHT[(paramInt + 6)]);
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.classes.carActivity
 * JD-Core Version:    0.6.0
 */