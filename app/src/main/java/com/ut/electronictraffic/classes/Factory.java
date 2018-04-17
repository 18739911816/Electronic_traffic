package com.ut.electronictraffic.classes;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.ut.electronictraffic.interfaces.RECT;
import com.ut.electronictraffic.interfaces.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class Factory
{
  public static final int BEGIN_RUN = 9000;
  private static final int DEFATLT_TIME = 2000;
  private static final boolean LOG;
  private int[] ETC_value = new int[2];
  private int[] LOT_CAR_VALUE;
  private int[] LOT_value = new int[2];
  private ETC etc = new ETC();
  private ParkingLot lot = new ParkingLot();
  private Handler mFactoryHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      switch (paramMessage.what)
      {
      default:
        return;
      case 9000:
      }
      int i = paramMessage.getData().getInt("car_id");
      int j = paramMessage.getData().getInt("angle_id");
      if (!Factory.this.isFactoryEtcLotValid(i, j))
      {
        Factory.this.sendMsg(Factory.this.mFactoryHandler, i, j, 9000, 100);
        return;
      }
      Factory.this.sendMsg(Factory.this.mHandler, i, j, Utils.CAR_MESSAGE_WAHT[(i + 6)], 100);
    }
  };
  private Handler mHandler;
  private int mSetParkLotFree = -1;
  private HashMap<String, RECT> map_Factory_Etc_za = new HashMap();
  private HashMap<String, RECT> map_Factory_Lot = new HashMap();
  private HashMap<String, RECT> map_Factory_Lot_za = new HashMap();

  public Factory(Context paramContext)
  {
    int i = 0;
    int j;
    if (i >= Utils.PARKINGLOT_ZA.length)
    {
      j = 0;
      label105: if (j < Utils.ETC_ZA.length)
        break label313;
    }
    for (int k = 0; ; k++)
    {
      if (k >= Utils.PARKINGLOT.length)
      {
        this.LOT_CAR_VALUE = new int[5];
        int[] arrayOfInt1 = this.LOT_CAR_VALUE;
        int[] arrayOfInt2 = this.LOT_CAR_VALUE;
        int[] arrayOfInt3 = this.LOT_CAR_VALUE;
        int[] arrayOfInt4 = this.LOT_CAR_VALUE;
        this.LOT_CAR_VALUE[4] = 100;
        arrayOfInt4[3] = 100;
        arrayOfInt3[2] = 100;
        arrayOfInt2[1] = 100;
        arrayOfInt1[0] = 100;
        int[] arrayOfInt5 = this.LOT_value;
        this.LOT_value[1] = 50;
        arrayOfInt5[0] = 50;
        int[] arrayOfInt6 = this.ETC_value;
        this.ETC_value[1] = 50;
        arrayOfInt6[0] = 50;
        return;
        RECT localRECT1 = new RECT();
        localRECT1.x = Utils.PARKINGLOT_ZA[i][0];
        localRECT1.y = Utils.PARKINGLOT_ZA[i][1];
        localRECT1.direct = Utils.PARKINGLOT_ZA[i][2];
        localRECT1.angle = Utils.PARKINGLOT_ZA[i][3];
        this.map_Factory_Lot_za.put("car_id_" + i, localRECT1);
        i++;
        break;
        label313: RECT localRECT2 = new RECT();
        localRECT2.x = Utils.ETC_ZA[j][0];
        localRECT2.y = Utils.ETC_ZA[j][1];
        localRECT2.direct = Utils.ETC_ZA[j][2];
        localRECT2.angle = Utils.ETC_ZA[j][3];
        this.map_Factory_Etc_za.put("car_id_" + j, localRECT2);
        j++;
        break label105;
      }
      RECT localRECT3 = new RECT();
      localRECT3.x = Utils.PARKINGLOT[k][0];
      localRECT3.y = Utils.PARKINGLOT[k][1];
      localRECT3.direct = Utils.PARKINGLOT[k][2];
      localRECT3.angle = Utils.PARKINGLOT[k][3];
      this.map_Factory_Lot.put("car_id_" + k, localRECT3);
    }
  }

  private void sendMsg(Handler paramHandler, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramHandler != null)
    {
      Message localMessage = new Message();
      localMessage.what = paramInt3;
      Bundle localBundle = new Bundle();
      localBundle.putInt("car_id", paramInt1);
      localBundle.putInt("angle_id", paramInt2);
      localMessage.setData(localBundle);
      paramHandler.sendMessageDelayed(localMessage, paramInt4);
    }
  }

  public void addFactoryCost(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = this.LOT_CAR_VALUE;
    arrayOfInt[paramInt1] = (paramInt2 + arrayOfInt[paramInt1]);
  }

  public void adusFactoryCost(int paramInt1, int paramInt2, int paramInt3)
  {
    int[] arrayOfInt = this.LOT_CAR_VALUE;
    arrayOfInt[paramInt1] -= getPriceByMode(paramInt2, paramInt3);
  }

  public boolean bPortEtcBlackId(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0)
      return this.etc.isBlackId(paramInt2);
    return this.lot.isBlackId(paramInt2);
  }

  public int getETCLOTId(RECT paramRECT, boolean paramBoolean)
  {
    int i = 0;
    Iterator localIterator;
    String str;
    if (paramBoolean)
    {
      localIterator = this.map_Factory_Etc_za.entrySet().iterator();
      str = "";
      label24: if (localIterator.hasNext())
        break label65;
      label34: if (!str.equalsIgnoreCase("car_id_0"))
        break label109;
      i = 0;
    }
    label65: label109: 
    do
    {
      return i;
      localIterator = this.map_Factory_Lot_za.entrySet().iterator();
      break;
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      str = (String)localEntry.getKey();
      if (paramRECT.equals(localEntry.getValue()))
        break label34;
      i++;
      break label24;
    }
    while (!str.equalsIgnoreCase("car_id_1"));
    return 1;
  }

  public int getFactoryCost(int paramInt)
  {
    return this.LOT_CAR_VALUE[paramInt];
  }

  public int getParkLotFree()
  {
    return this.mSetParkLotFree;
  }

  public int getPriceByMode(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0)
      return this.etc.getPrice(paramInt2);
    return this.lot.getPrice(paramInt2);
  }

  public boolean isFactoryEtcLotValid(int paramInt1, int paramInt2)
  {
    return (this.LOT_CAR_VALUE[paramInt2] > 0) && (!bPortEtcBlackId(paramInt1, paramInt2));
  }

  public boolean isFactoryEtcZa(RECT paramRECT)
  {
    return this.map_Factory_Etc_za.containsValue(paramRECT);
  }

  public boolean isFactoryLot(RECT paramRECT)
  {
    return this.map_Factory_Lot.containsValue(paramRECT);
  }

  public boolean isFactoryLotZa(RECT paramRECT)
  {
    return this.map_Factory_Lot_za.containsValue(paramRECT);
  }

  public void setFactoryCost(int paramInt1, String paramString, int paramInt2)
  {
    if (paramString.equals("Count"));
    for (int i = 1; paramInt1 == 1; i = 0)
    {
      this.LOT_value[i] = paramInt2;
      this.lot.setPrice(i, paramInt2);
      return;
    }
    this.ETC_value[i] = paramInt2;
    this.etc.setPrice(i, paramInt2);
  }

  public void setFactoryMsg(int paramInt1, int paramInt2)
  {
    sendMsg(this.mFactoryHandler, paramInt1, paramInt2, 9000, 10);
  }

  public void setHandler(Handler paramHandler)
  {
    this.mHandler = paramHandler;
  }

  public void setParkLotFree(int paramInt)
  {
    this.mSetParkLotFree = paramInt;
  }

  public void setPortEtcBlackId(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0)
    {
      this.etc.addBlackId(paramInt2);
      return;
    }
    this.lot.addBlackId(paramInt2);
  }

  private class ETC
  {
    private List<Object> blackId = new ArrayList();
    private int mMode = 1;
    private int[] price = new int[2];
    private int totalCost;

    ETC()
    {
      int[] arrayOfInt = this.price;
      this.price[1] = 5;
      arrayOfInt[0] = 5;
      this.totalCost = 100;
      this.mMode = 1;
    }

    public void addBlackId(int paramInt)
    {
      this.blackId.add(Integer.valueOf(paramInt));
    }

    public void addTotolCost(int paramInt)
    {
      this.totalCost = (paramInt + this.totalCost);
    }

    public int getCost()
    {
      return this.totalCost;
    }

    public HashMap<String, Integer> getETCInfo()
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("price", Integer.valueOf(this.price[0]));
      localHashMap.put("totalcost", Integer.valueOf(this.totalCost));
      return localHashMap;
    }

    public int getMode()
    {
      return this.mMode;
    }

    public int getPrice(int paramInt)
    {
      if (this.mMode == 1)
        return this.price[1];
      return this.price[0];
    }

    public boolean isBlackId(int paramInt)
    {
      return this.blackId.contains(Integer.valueOf(paramInt));
    }

    public void setCost(int paramInt)
    {
      this.totalCost -= paramInt;
    }

    public void setMode(int paramInt)
    {
      this.mMode = paramInt;
    }

    public void setPrice(int paramInt1, int paramInt2)
    {
      this.price[paramInt1] = paramInt2;
    }
  }

  private class ParkingLot
  {
    private List<Object> blackId = new ArrayList();
    private int carNum;
    private int mMode = 1;
    private int[] price = new int[2];
    private int totalCost;
    private int totalNum;

    ParkingLot()
    {
      int[] arrayOfInt = this.price;
      this.price[1] = 5;
      arrayOfInt[0] = 5;
      this.totalCost = 100;
      this.mMode = 1;
    }

    public void addBlackId(int paramInt)
    {
      this.blackId.add(Integer.valueOf(paramInt));
    }

    public void addTotolCost(int paramInt)
    {
      this.totalCost = (paramInt + this.totalCost);
    }

    public int getCarNumber()
    {
      return this.carNum;
    }

    public int getCost()
    {
      return this.totalCost;
    }

    public int getMode()
    {
      return this.mMode;
    }

    public HashMap<String, Integer> getParkingLotInfo()
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("price", Integer.valueOf(this.price[0]));
      localHashMap.put("carnum", Integer.valueOf(this.carNum));
      localHashMap.put("totalnum", Integer.valueOf(this.totalNum));
      return localHashMap;
    }

    public int getPrice(int paramInt)
    {
      if (paramInt == 1)
        return this.price[1];
      return this.price[0];
    }

    public boolean isBlackId(int paramInt)
    {
      return this.blackId.contains(Integer.valueOf(paramInt));
    }

    public void setCost(int paramInt)
    {
      this.totalCost -= paramInt;
    }

    public void setMode(int paramInt)
    {
      this.mMode = paramInt;
    }

    public void setPrice(int paramInt1, int paramInt2)
    {
      this.price[paramInt1] = paramInt2;
    }
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.classes.Factory
 * JD-Core Version:    0.6.0
 */