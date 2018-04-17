package com.ut.electronictraffic.interfaces;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import com.ut.electronictraffic.carApplication;
import com.ut.electronictraffic.classes.BusStation;
import com.ut.electronictraffic.classes.EnvSensor;
import com.ut.electronictraffic.classes.Factory;
import com.ut.electronictraffic.classes.StreetLight;
import com.ut.electronictraffic.classes.TrafficLight;
import com.ut.electronictraffic.classes.carActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Interface
{
  private static int[] GATE_STATUS;
  private static int[][] LIGHT_MODE;
  private static int[] LIGHT_MODE_UI;
  private static carApplication app;
  private static boolean bSyncMode = false;
  private static BusStation busStation;
  private static carActivity car;
  private static EnvSensor envSensor;
  private static Factory factory;
  private static final String host = "192.168.1.111";
  private static int location = 0;
  private static Cursor mCursor;
  private static dataBase mDateBase;
  private static final int port = 7878;
  private static String remote_ip_port;
  static BufferedReader rr;
  private static StreetLight streetLight;
  private static TrafficLight traffic;
  static BufferedWriter wr;
  private Socket socket;

  static
  {
    int[] arrayOfInt = { 5, 2 };
    LIGHT_MODE = (int[][])Array.newInstance(Integer.TYPE, arrayOfInt);
    LIGHT_MODE_UI = new int[5];
    GATE_STATUS = new int[4];
    remote_ip_port = "";
    bSyncMode = false;
    location = 0;
  }

  public Interface(Context paramContext)
  {
    app = (carApplication)paramContext;
    car = app.getCarClass();
    streetLight = app.getStreetLight();
    envSensor = app.getEnvSensor();
    factory = app.getFactory();
    busStation = app.getBusStation();
    traffic = app.getTraffic();
    for (int i = 0; ; i++)
    {
      if (i >= 5)
      {
        int[] arrayOfInt1 = GATE_STATUS;
        int[] arrayOfInt2 = GATE_STATUS;
        int[] arrayOfInt3 = GATE_STATUS;
        GATE_STATUS[3] = 0;
        arrayOfInt3[2] = 0;
        arrayOfInt2[1] = 0;
        arrayOfInt1[0] = 0;
        mDateBase = new dataBase(paramContext);
        remote_ip_port = getIpPortBySDCardXML();
        return;
      }
      LIGHT_MODE[i][0] = 0;
      LIGHT_MODE[i][1] = 0;
      LIGHT_MODE_UI[i] = 0;
    }
  }

  public static String GetEtcListReport()
  {
    Cursor localCursor = mDateBase.select();
    JSONArray localJSONArray = new JSONArray();
    localCursor.moveToFirst();
    while (true)
    {
      if (localCursor.isAfterLast())
      {
        localCursor.close();
        return localJSONArray.toString();
      }
      String str = localCursor.getString(2);
      if (str != null)
      {
        int i = localCursor.getInt(0);
        localJSONArray.put(str);
        mDateBase.delete(i);
      }
      localCursor.moveToNext();
    }
  }

  public static String GetPakingListReport()
  {
    Cursor localCursor = mDateBase.select();
    JSONArray localJSONArray = new JSONArray();
    localCursor.moveToFirst();
    while (true)
    {
      if (localCursor.isAfterLast())
      {
        localCursor.close();
        return localJSONArray.toString();
      }
      String str = localCursor.getString(1);
      if (str != null)
      {
        int i = localCursor.getInt(0);
        localJSONArray.put(str);
        mDateBase.delete(i);
      }
      localCursor.moveToNext();
    }
  }

  public static String SetEtcBlackList(JSONArray paramJSONArray)
  {
    int i = paramJSONArray.length();
    int j = 0;
    while (true)
    {
      if (j >= i)
        return "ok";
      try
      {
        int k = paramJSONArray.getJSONObject(j).getInt("CarId");
        factory.setPortEtcBlackId(0, k - 1);
        j++;
      }
      catch (JSONException localJSONException)
      {
        while (true)
          localJSONException.printStackTrace();
      }
    }
  }

  public static void SetEtcBlackListId(int paramInt1, int paramInt2)
  {
    factory.setPortEtcBlackId(paramInt1, paramInt2);
  }

  public static String SetParkBlackList(JSONArray paramJSONArray)
  {
    int i = paramJSONArray.length();
    new JSONObject();
    int j = 0;
    while (true)
    {
      if (j >= i)
        return "ok";
      try
      {
        int k = ((JSONObject)paramJSONArray.get(j)).getInt("CarId");
        factory.setPortEtcBlackId(1, k - 1);
        j++;
      }
      catch (JSONException localJSONException)
      {
        while (true)
          localJSONException.printStackTrace();
      }
    }
  }

  public static void addMSG(String paramString)
  {
    mDateBase.insert(3, paramString);
  }

  public static int getBusCapacity(int paramInt)
  {
    return busStation.getBusCapacity(paramInt);
  }

  public static int getBusSpeed(int paramInt)
  {
    return busStation.getBusSpeed(paramInt);
  }

  public static String getBusStationInfo(int paramInt)
  {
    int i = 1;
    JSONArray localJSONArray = new JSONArray();
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      localJSONObject1.put("Distance", busStation.getDistance(paramInt));
      localJSONObject1.put("BusId", 1);
      localJSONArray.put(localJSONObject1);
      JSONObject localJSONObject2 = new JSONObject();
      BusStation localBusStation = busStation;
      if (paramInt == 0);
      while (true)
      {
        localJSONObject2.put("Distance", localBusStation.getDistance(i));
        localJSONObject2.put("BusId", 2);
        localJSONArray.put(localJSONObject2);
        return localJSONArray.toString();
        i = 0;
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public static String getCarAccountRecord(int paramInt, String paramString)
  {
    Cursor localCursor = mDateBase.select();
    JSONArray localJSONArray = new JSONArray();
    localCursor.moveToFirst();
    while (true)
    {
      if (localCursor.isAfterLast())
      {
        localCursor.close();
        return localJSONArray.toString();
      }
      String str = localCursor.getString(3);
      if (str != null);
      try
      {
        JSONObject localJSONObject = new JSONObject(str);
        int i = localCursor.getInt(0);
        if (paramString.equals("All"))
        {
          localJSONArray.put(str);
          mDateBase.delete(i);
        }
        while (true)
        {
          localCursor.moveToNext();
          break;
          if (!localJSONObject.getString("Addr").equals(paramString))
            continue;
          localJSONArray.put(str);
          mDateBase.delete(i);
        }
      }
      catch (JSONException localJSONException)
      {
        while (true)
          localJSONException.printStackTrace();
      }
    }
  }

  public static int getCarCurrentPoint(int paramInt)
  {
    RECT localRECT = car.getCarCurrentPoint(paramInt);
    int i = localRECT.x;
    location = Utils.LOCATION[paramInt][i];
    return location;
  }

  public static int getCarPathId(int paramInt)
  {
    return car.getCarPathId(paramInt);
  }

  public static String getCarSpeed(int paramInt)
  {
    JSONObject localJSONObject = new JSONObject();
    int i = car.getCarSpeed(paramInt);
    try
    {
      localJSONObject.put("CarSpeed", i);
      return localJSONObject.toString();
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public static int getCusCardBalances(int paramInt)
  {
    return factory.getFactoryCost(paramInt);
  }

  public static String getCusCardRecord(int paramInt)
  {
    return "";
  }

  public static String getDateInfo()
  {
    Cursor localCursor = mDateBase.select();
    JSONArray localJSONArray = new JSONArray();
    JSONObject localJSONObject1 = new JSONObject();
    localCursor.moveToFirst();
    while (true)
    {
      if (localCursor.isAfterLast())
        localCursor.close();
      try
      {
        localJSONObject1.put("dateInfo", localJSONArray);
        return localJSONObject1.toString();
        String str = localCursor.getString(1);
        JSONObject localJSONObject2 = new JSONObject();
        try
        {
          localJSONObject2.put("info", str);
          localJSONArray.put(localJSONObject2);
          localCursor.moveToNext();
        }
        catch (JSONException localJSONException1)
        {
          while (true)
            localJSONException1.printStackTrace();
        }
      }
      catch (JSONException localJSONException2)
      {
        while (true)
          localJSONException2.printStackTrace();
      }
    }
  }

  public static String getDateTime()
  {
    return new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date(System.currentTimeMillis()));
  }

  public static String getEtcGateStatus(int paramInt)
  {
    if (GATE_STATUS[paramInt] == 1)
      return "Open";
    return "Close";
  }

  private String getIpPortBySDCardXML()
  {
    return ReadTxtFile("remote.txt");
  }

  public static int getLIGHT_MODE(int paramInt1, int paramInt2)
  {
    return LIGHT_MODE[paramInt1][paramInt2];
  }

  public static int getLIGHT_MODE_UI(int paramInt)
  {
    return LIGHT_MODE_UI[paramInt];
  }

  public static String getLightSensorThreshold()
  {
    int i = envSensor.getThresholdH();
    int j = envSensor.getThresholdL();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("Down", j);
      localJSONObject.put("Up", i);
      return localJSONObject.toString();
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public static String getParkETCRate(int paramInt1, int paramInt2)
  {
    int i = factory.getPriceByMode(paramInt1, paramInt2);
    JSONObject localJSONObject = new JSONObject();
    String str;
    if (paramInt2 == 1)
      str = "Count";
    try
    {
      while (true)
      {
        localJSONObject.put("RateType", str);
        localJSONObject.put("Money", i);
        return localJSONObject.toString();
        str = "Hour";
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public static String getParkFree()
  {
    JSONObject localJSONObject = new JSONObject();
    int i = factory.getParkLotFree();
    int j;
    if (i == 0)
      j = 1;
    try
    {
      while (true)
      {
        localJSONObject.put("ParkFreeId", j);
        return localJSONObject.toString();
        j = 2;
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public static String getParkGateStatus(int paramInt)
  {
    if (GATE_STATUS[paramInt] == 1)
      return "Open";
    return "Close";
  }

  public static String getRemoteIpPort()
  {
    return remote_ip_port;
  }

  public static boolean getRoadLightState()
  {
    return streetLight.getStreetLightOnOff();
  }

  public static int getRoadStatus(int paramInt)
  {
    return 0 + new Random().nextInt(5) % 6;
  }

  public static String getSensorValue(int paramInt)
  {
    if (paramInt == -1)
    {
      new HashMap();
      HashMap localHashMap = envSensor.getEnvSensorInfo();
      int i = ((Integer)localHashMap.get("light")).intValue();
      int j = ((Integer)localHashMap.get("pm")).intValue();
      int k = ((Integer)localHashMap.get("com2")).intValue();
      int m = ((Integer)localHashMap.get("humiture")).intValue();
      int n = ((Integer)localHashMap.get("temp")).intValue();
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("LightIntensity", i);
        localJSONObject.put("pm2.5", j);
        localJSONObject.put("co2", k);
        localJSONObject.put("humidity", m);
        localJSONObject.put("temperature", n);
        return localJSONObject.toString();
      }
      catch (JSONException localJSONException)
      {
        while (true)
          localJSONException.printStackTrace();
      }
    }
    return envSensor.getEnvSensorInfo(paramInt);
  }

  public static String getSyncInfo()
  {
    new JSONObject();
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    JSONArray localJSONArray1 = new JSONArray();
    new JSONObject();
    try
    {
      localJSONObject1.put("Location", getCarCurrentPoint(0));
      localJSONObject1.put("Speed", car.getCarSpeed(0));
      localJSONObject1.put("Mode", 0);
      localJSONObject1.put("CarId", 1);
      localJSONArray1.put(localJSONObject1);
      JSONObject localJSONObject3 = new JSONObject();
      localJSONObject3.put("Location", getCarCurrentPoint(4));
      localJSONObject3.put("Speed", car.getCarSpeed(4));
      localJSONObject3.put("Mode", 1);
      localJSONObject3.put("CarId", 1);
      localJSONArray1.put(localJSONObject3);
      JSONObject localJSONObject4 = new JSONObject();
      localJSONObject4.put("Location", getCarCurrentPoint(5));
      localJSONObject4.put("Speed", car.getCarSpeed(5));
      localJSONObject4.put("Mode", 1);
      localJSONObject4.put("CarId", 2);
      localJSONArray1.put(localJSONObject4);
      localJSONObject2.put("Car", localJSONArray1.toString());
      JSONArray localJSONArray2 = new JSONArray();
      int i = 0;
      while (true)
        if (i >= 5)
        {
          localJSONObject2.put("TrafficLight", localJSONArray2.toString());
          if (getRoadLightState())
          {
            str1 = "Open";
            localJSONArray3 = new JSONArray();
            j = 0;
            if (j < 3)
              break label520;
            localJSONObject2.put("RoadLight", localJSONArray3.toString());
            localJSONObject2.put("ParkFree", getParkFree());
            localJSONObject2.put("Sense", getSensorValue(-1));
            localJSONArray4 = new JSONArray();
            k = 0;
            if (k < 12)
              break label567;
            localJSONObject2.put("Road", localJSONArray4.toString());
            return localJSONObject2.toString();
          }
        }
        else
        {
          JSONObject localJSONObject7 = new JSONObject();
          int m = getLIGHT_MODE(i, 0);
          try
          {
            localJSONObject7.put("TrafficId", i + 1 + "-1");
            String str2;
            if (m == 0)
              str2 = "Red";
            while (true)
            {
              localJSONObject7.put("Status", str2);
              localJSONObject7.put("Time", traffic.getTrafficTime(i, m) / 1000);
              localJSONArray2.put(localJSONObject7);
              i++;
              break;
              if (m == 1)
              {
                str2 = "Yellow";
                continue;
              }
              str2 = "Green";
            }
          }
          catch (JSONException localJSONException2)
          {
            while (true)
              localJSONException2.printStackTrace();
          }
        }
    }
    catch (JSONException localJSONException1)
    {
      while (true)
      {
        JSONArray localJSONArray3;
        int j;
        JSONArray localJSONArray4;
        int k;
        localJSONException1.printStackTrace();
        continue;
        String str1 = "Close";
        continue;
        label520: JSONObject localJSONObject6 = new JSONObject();
        localJSONObject6.put("RoadLightId", j + 1);
        localJSONObject6.put("Status", str1);
        localJSONArray3.put(localJSONObject6);
        j++;
        continue;
        label567: JSONObject localJSONObject5 = new JSONObject();
        localJSONObject5.put("RoadId", k + 1);
        localJSONObject5.put("Status", getRoadStatus(k));
        localJSONArray4.put(localJSONObject5);
        k++;
      }
    }
  }

  public static boolean getSyncMode()
  {
    return bSyncMode;
  }

  public static String getTrafficLightStatusTime(int paramInt1, int paramInt2)
  {
    return traffic.getStatusTime(paramInt1, paramInt2);
  }

  public static String getTrafficLightTimeConfig(int paramInt)
  {
    return traffic.getTime(paramInt);
  }

  public static String getTrafficStatusTime(int paramInt1, int paramInt2)
  {
    return traffic.getTrafficStatusTime(paramInt1, paramInt2);
  }

  public static void insetDateInfo(int paramInt, String paramString)
  {
    mDateBase.insert(paramInt, paramString);
  }

  public static String sendMsgByHttp(String paramString)
  {
    HttpPost localHttpPost = new HttpPost(paramString);
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 3000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 3000);
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(localBasicHttpParams);
    try
    {
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
      if (localHttpResponse.getStatusLine().getStatusCode() != 200)
      {
        Log.i("GET", "Bad Request! = " + localHttpResponse.getStatusLine().getStatusCode());
        return "BadRequest";
      }
      Log.i("GET", "good Request!");
      String str = EntityUtils.toString(localHttpResponse.getEntity());
      return str;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public static void setCarOperate(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
    {
      car.stopCarHandlerMsg(paramInt1);
      return;
    }
    car.runCarHandlerMsg(paramInt1);
  }

  private static String setCmdStringById(int paramInt1, int paramInt2, int paramInt3)
  {
    JSONObject localJSONObject = new JSONObject();
    switch (paramInt1)
    {
    default:
      return "";
    case 1:
      try
      {
        localJSONObject.put("capacity", paramInt2);
        return localJSONObject.toString().replace("\n", " ") + "\n";
      }
      catch (JSONException localJSONException9)
      {
        while (true)
          localJSONException9.printStackTrace();
      }
    case 2:
      try
      {
        localJSONObject.put("car_x", paramInt2);
        localJSONObject.put("car_y", paramInt3);
        return localJSONObject.toString().replace("\n", " ") + "\n";
      }
      catch (JSONException localJSONException8)
      {
        while (true)
          localJSONException8.printStackTrace();
      }
    case 3:
      String str2;
      if (paramInt2 == 1)
        str2 = "OK";
      try
      {
        while (true)
        {
          localJSONObject.put("result", str2);
          return localJSONObject.toString().replace("\n", " ") + "\n";
          str2 = "FAIL";
        }
      }
      catch (JSONException localJSONException7)
      {
        while (true)
          localJSONException7.printStackTrace();
      }
    case 4:
      try
      {
        localJSONObject.put("blance", paramInt2);
        return localJSONObject.toString().replace("\n", " ") + "\n";
      }
      catch (JSONException localJSONException6)
      {
        while (true)
          localJSONException6.printStackTrace();
      }
    case 5:
      try
      {
        localJSONObject.put("traffictime", paramInt2);
        return localJSONObject.toString().replace("\n", " ") + "\n";
      }
      catch (JSONException localJSONException5)
      {
        while (true)
          localJSONException5.printStackTrace();
      }
    case 6:
      String str1;
      if (paramInt2 == 0)
        str1 = "OFF";
      try
      {
        while (true)
        {
          localJSONObject.put("status", str1);
          return localJSONObject.toString().replace("\n", " ") + "\n";
          str1 = "ON";
        }
      }
      catch (JSONException localJSONException4)
      {
        while (true)
          localJSONException4.printStackTrace();
      }
    case 7:
      try
      {
        localJSONObject.put("sensorvalue", paramInt2);
        return localJSONObject.toString().replace("\n", " ") + "\n";
      }
      catch (JSONException localJSONException3)
      {
        while (true)
          localJSONException3.printStackTrace();
      }
    case 8:
      try
      {
        localJSONObject.put("thresholdh", paramInt2);
        localJSONObject.put("thresholdl", paramInt3);
        return localJSONObject.toString().replace("\n", " ") + "\n";
      }
      catch (JSONException localJSONException2)
      {
        while (true)
          localJSONException2.printStackTrace();
      }
    case 9:
    }
    try
    {
      localJSONObject.put("CarSpeed", car.getCarSpeed(paramInt2));
      return localJSONObject.toString();
    }
    catch (JSONException localJSONException1)
    {
      while (true)
        localJSONException1.printStackTrace();
    }
  }

  public static String setCusCardBalances(int paramInt1, int paramInt2)
  {
    factory.addFactoryCost(paramInt1, paramInt2);
    return "";
  }

  public static void setJsonString(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    int i = factory.getPriceByMode(paramInt1, paramInt2);
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("CarId", paramInt1);
      if (paramInt2 == 1)
      {
        localJSONObject.put("ParkInTime", paramString1);
        localJSONObject.put("ParkOutTime", paramString2);
      }
      while (true)
      {
        localJSONObject.put("Money", i);
        mDateBase.insert(paramInt2, localJSONObject.toString());
        Log.d("--setJsonString--", "------------jb_string = " + localJSONObject.toString());
        return;
        localJSONObject.put("EtcInTime", paramString1);
        localJSONObject.put("EtcOutTime", paramString2);
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public static void setLIGHT_MODE(int paramInt1, int paramInt2, int paramInt3)
  {
    LIGHT_MODE[paramInt1][0] = paramInt2;
    LIGHT_MODE[paramInt1][1] = paramInt3;
  }

  public static void setLIGHT_MODE_UI(int paramInt1, int paramInt2)
  {
    LIGHT_MODE_UI[paramInt1] = paramInt2;
  }

  public static String setLightSensorThreshold(int paramInt1, int paramInt2)
  {
    envSensor.setLihgtSensorThreshold(paramInt1, paramInt2);
    return "ok";
  }

  private void setParamerByOptId(int paramInt, JSONObject paramJSONObject)
  {
    int i = -1;
    int j = -1;
    int k = -1;
    switch (paramInt)
    {
    case 17:
    default:
      return;
    case 1:
      try
      {
        int i8 = Integer.valueOf(paramJSONObject.getString("bus_id")).intValue();
        i = i8;
        getBusCapacity(i);
        return;
      }
      catch (NumberFormatException localNumberFormatException13)
      {
        while (true)
          localNumberFormatException13.printStackTrace();
      }
      catch (JSONException localJSONException13)
      {
        while (true)
          localJSONException13.printStackTrace();
      }
    case 2:
      try
      {
        int i7 = Integer.valueOf(paramJSONObject.getString("station_id")).intValue();
        i = i7;
        getBusStationInfo(i);
        return;
      }
      catch (NumberFormatException localNumberFormatException12)
      {
        while (true)
          localNumberFormatException12.printStackTrace();
      }
      catch (JSONException localJSONException12)
      {
        while (true)
          localJSONException12.printStackTrace();
      }
    case 3:
      try
      {
        int i6 = Integer.valueOf(paramJSONObject.getString("car_id")).intValue();
        i = i6;
        getCarCurrentPoint(i);
        return;
      }
      catch (NumberFormatException localNumberFormatException11)
      {
        while (true)
          localNumberFormatException11.printStackTrace();
      }
      catch (JSONException localJSONException11)
      {
        while (true)
          localJSONException11.printStackTrace();
      }
    case 4:
      try
      {
        i = Integer.valueOf(paramJSONObject.getString("car_id")).intValue();
        int i5 = Integer.valueOf(paramJSONObject.getString("status")).intValue();
        j = i5;
        setCarOperate(i, j);
        return;
      }
      catch (NumberFormatException localNumberFormatException10)
      {
        while (true)
          localNumberFormatException10.printStackTrace();
      }
      catch (JSONException localJSONException10)
      {
        while (true)
          localJSONException10.printStackTrace();
      }
    case 5:
      try
      {
        int i4 = Integer.valueOf(paramJSONObject.getString("car_id")).intValue();
        i = i4;
        getCusCardBalances(i);
        return;
      }
      catch (NumberFormatException localNumberFormatException9)
      {
        while (true)
          localNumberFormatException9.printStackTrace();
      }
      catch (JSONException localJSONException9)
      {
        while (true)
          localJSONException9.printStackTrace();
      }
    case 6:
      try
      {
        i = Integer.valueOf(paramJSONObject.getString("car_id")).intValue();
        int i3 = Integer.valueOf(paramJSONObject.getString("value")).intValue();
        j = i3;
        setCusCardBalances(i, j);
        return;
      }
      catch (NumberFormatException localNumberFormatException8)
      {
        while (true)
          localNumberFormatException8.printStackTrace();
      }
      catch (JSONException localJSONException8)
      {
        while (true)
          localJSONException8.printStackTrace();
      }
    case 7:
      try
      {
        int i2 = Integer.valueOf(paramJSONObject.getString("car_id")).intValue();
        i = i2;
        getCusCardRecord(i);
        return;
      }
      catch (NumberFormatException localNumberFormatException7)
      {
        while (true)
          localNumberFormatException7.printStackTrace();
      }
      catch (JSONException localJSONException7)
      {
        while (true)
          localJSONException7.printStackTrace();
      }
    case 8:
      try
      {
        Integer.valueOf(paramJSONObject.getString("road_id")).intValue();
        Integer.valueOf(paramJSONObject.getString("light_id")).intValue();
        return;
      }
      catch (NumberFormatException localNumberFormatException6)
      {
        localNumberFormatException6.printStackTrace();
        return;
      }
      catch (JSONException localJSONException6)
      {
        localJSONException6.printStackTrace();
        return;
      }
    case 9:
      try
      {
        i = Integer.valueOf(paramJSONObject.getString("road_id")).intValue();
        Integer.valueOf(paramJSONObject.getString("light_id")).intValue();
        int i1 = Integer.valueOf(paramJSONObject.getString("time_value")).intValue();
        k = i1;
        setTrafficLightTime(i, "", k);
        return;
      }
      catch (NumberFormatException localNumberFormatException5)
      {
        while (true)
          localNumberFormatException5.printStackTrace();
      }
      catch (JSONException localJSONException5)
      {
        while (true)
          localJSONException5.printStackTrace();
      }
    case 10:
      try
      {
        Integer.valueOf(paramJSONObject.getString("road_id")).intValue();
        Integer.valueOf(paramJSONObject.getString("light_id")).intValue();
        Integer.valueOf(paramJSONObject.getString("status")).intValue();
        return;
      }
      catch (NumberFormatException localNumberFormatException4)
      {
        localNumberFormatException4.printStackTrace();
        return;
      }
      catch (JSONException localJSONException4)
      {
        localJSONException4.printStackTrace();
        return;
      }
    case 11:
      getRoadLightState();
      return;
    case 12:
      try
      {
        Integer.valueOf(paramJSONObject.getString("status")).intValue();
        setRoadLightState("");
        return;
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        while (true)
          localNumberFormatException3.printStackTrace();
      }
      catch (JSONException localJSONException3)
      {
        while (true)
          localJSONException3.printStackTrace();
      }
    case 13:
      try
      {
        int n = Integer.valueOf(paramJSONObject.getString("sensor_id")).intValue();
        i = n;
        getSensorValue(i);
        return;
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        while (true)
          localNumberFormatException2.printStackTrace();
      }
      catch (JSONException localJSONException2)
      {
        while (true)
          localJSONException2.printStackTrace();
      }
    case 14:
      getSensorValue(-1);
      return;
    case 15:
      try
      {
        i = Integer.valueOf(paramJSONObject.getString("max")).intValue();
        int m = Integer.valueOf(paramJSONObject.getString("min")).intValue();
        j = m;
        setLightSensorThreshold(i, j);
        return;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        while (true)
          localNumberFormatException1.printStackTrace();
      }
      catch (JSONException localJSONException1)
      {
        while (true)
          localJSONException1.printStackTrace();
      }
    case 16:
    }
    getLightSensorThreshold();
  }

  public static void setParkCount(String paramString, int paramInt)
  {
  }

  public static String setParkETCRate(int paramInt1, String paramString, int paramInt2)
  {
    factory.setFactoryCost(paramInt1, paramString, paramInt2);
    return "";
  }

  public static String setParkEtcAccountRecord(int paramInt1, int paramInt2)
  {
    String str = getDateTime();
    int i = factory.getPriceByMode(paramInt2, 0);
    factory.adusFactoryCost(paramInt1, paramInt2, 0);
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("CarId", paramInt1);
      localJSONObject.put("Cost", i);
      if (paramInt2 == 0)
        localJSONObject.put("Addr", "etcout");
      while (true)
      {
        localJSONObject.put("Time", str);
        mDateBase.insert(3, localJSONObject.toString());
        return "";
        localJSONObject.put("Addr", "parkout");
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public static String setParkEtcGateStatus(int paramInt1, int paramInt2)
  {
    GATE_STATUS[paramInt1] = paramInt2;
    return "";
  }

  public static String setRoadLightControlMode(String paramString)
  {
    StreetLight localStreetLight = streetLight;
    if (paramString.equals("Auto"));
    for (int i = 0; ; i = 1)
    {
      localStreetLight.setStreetLightMode(i);
      return "ok";
    }
  }

  public static String setRoadLightState(String paramString)
  {
    StreetLight localStreetLight = streetLight;
    if (paramString.equals("Open"));
    for (boolean bool = true; ; bool = false)
    {
      localStreetLight.setStreetLightOnOff(bool);
      return "ok";
    }
  }

  public static void setSyncMode(boolean paramBoolean)
  {
    bSyncMode = paramBoolean;
  }

  public static String setTrafficLightConfig(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    traffic.setTimeConfig(paramInt1, paramInt2 * 1000, paramInt3 * 1000, paramInt4 * 1000);
    return "ok";
  }

  public static String setTrafficLightState(int paramInt1, int paramInt2, String paramString)
  {
    traffic.setTrafficLightById(paramInt1, paramInt2, paramString);
    return "ok";
  }

  public static String setTrafficLightTime(int paramInt1, String paramString, int paramInt2)
  {
    traffic.setTime(paramInt1, paramString, paramInt2 * 1000);
    return "ok";
  }

  public static void updateDateInfo(int paramInt1, int paramInt2, String paramString)
  {
    mDateBase.update(paramInt1, paramInt2, paramString);
  }

  public String ReadTxtFile(String paramString)
  {
    String str1 = "mnt/extsd/" + paramString;
    Object localObject = "";
    File localFile = new File(str1);
    if (localFile.isDirectory())
      Log.d("TestFile", "The File doesn't not exist.");
    while (true)
    {
      return localObject;
      try
      {
        FileInputStream localFileInputStream = new FileInputStream(localFile);
        if (localFileInputStream == null)
          continue;
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localFileInputStream));
        str2 = localBufferedReader.readLine();
        if (str2 == null)
        {
          localFileInputStream.close();
          return localObject;
        }
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        while (true)
        {
          String str2;
          Log.d("TestFile", "The File doesn't not exist.");
          return localObject;
          String str3 = localObject + str2;
          localObject = str3;
        }
      }
      catch (IOException localIOException)
      {
        Log.d("TestFile", localIOException.getMessage());
      }
    }
    return (String)localObject;
  }

  public void getCmdStringById(String paramString)
  {
    new JSONArray();
    try
    {
      JSONObject localJSONObject1 = new JSONObject(paramString);
      localJSONObject2 = localJSONObject1;
    }
    catch (JSONException localJSONException2)
    {
      try
      {
        while (true)
        {
          JSONObject localJSONObject3 = localJSONObject2.getJSONObject("parameter");
          setParamerByOptId(Integer.valueOf(localJSONObject3.getString("opt_id")).intValue(), localJSONObject3);
          return;
          localJSONException2 = localJSONException2;
          localJSONException2.printStackTrace();
          JSONObject localJSONObject2 = null;
        }
      }
      catch (JSONException localJSONException1)
      {
        localJSONException1.printStackTrace();
      }
    }
  }

  public String getSDPath()
  {
    boolean bool = Environment.getExternalStorageState().equals("mounted");
    File localFile = null;
    if (bool)
      localFile = Environment.getExternalStorageDirectory();
    return localFile.toString();
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.interfaces.Interface
 * JD-Core Version:    0.6.0
 */