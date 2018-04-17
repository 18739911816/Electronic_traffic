package com.ut.electronictraffic.services;

import android.content.Context;
import android.util.Log;
import com.ut.electronictraffic.interfaces.Interface;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyNanoHTTPD extends NanoHTTPD
{
  private static final String TAG = "JasonHttpServer";
  private int Sense_id = 0;
  private Context context;
  private boolean logEnable = false;

  public MyNanoHTTPD(int paramInt)
  {
    super(paramInt);
  }

  public MyNanoHTTPD(Context paramContext, String paramString, int paramInt)
  {
    super(paramString, paramInt);
    this.context = paramContext;
  }

  public static String GetSyncInfo()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONArray localJSONArray1 = new JSONArray();
    JSONArray localJSONArray2 = new JSONArray();
    JSONArray localJSONArray3 = new JSONArray();
    JSONArray localJSONArray4 = new JSONArray();
    JSONObject localJSONObject2 = new JSONObject();
    JSONObject localJSONObject3 = new JSONObject();
    JSONObject localJSONObject4 = new JSONObject();
    JSONObject localJSONObject5 = new JSONObject();
    JSONObject localJSONObject6 = new JSONObject();
    JSONObject localJSONObject7 = new JSONObject();
    JSONObject localJSONObject8 = new JSONObject();
    JSONObject localJSONObject9 = new JSONObject();
    JSONObject localJSONObject10 = new JSONObject();
    new JSONObject();
    JSONObject localJSONObject11 = new JSONObject();
    JSONObject localJSONObject12 = new JSONObject();
    JSONObject localJSONObject13 = new JSONObject();
    JSONObject localJSONObject14 = new JSONObject();
    JSONObject localJSONObject15 = new JSONObject();
    JSONObject localJSONObject16 = new JSONObject();
    localJSONObject2.put("TrafficId", "1");
    int i = Interface.getLIGHT_MODE(0, 0);
    String str1;
    int j;
    String str2;
    label236: int k;
    String str3;
    label281: int m;
    String str4;
    label307: int n;
    String str5;
    label352: int i1;
    String str6;
    label378: int i2;
    String str7;
    label423: int i3;
    String str8;
    label449: int i4;
    String str9;
    label494: int i5;
    String str10;
    label520: String str11;
    label602: String str12;
    if (i == 0)
    {
      str1 = "red";
      localJSONObject2.put("StatusH", str1);
      j = Interface.getLIGHT_MODE(0, 1);
      if (j != 0)
        break label1025;
      str2 = "red";
      localJSONObject2.put("StatusS", str2);
      localJSONObject2.put("Time", 5);
      localJSONObject3.put("TrafficId", "2");
      k = Interface.getLIGHT_MODE(1, 0);
      if (k != 0)
        break label1045;
      str3 = "red";
      localJSONObject3.put("StatusH", str3);
      m = Interface.getLIGHT_MODE(1, 1);
      if (m != 0)
        break label1065;
      str4 = "red";
      localJSONObject3.put("StatusS", str4);
      localJSONObject3.put("Time", 5);
      localJSONObject4.put("TrafficId", "3");
      n = Interface.getLIGHT_MODE(2, 0);
      if (n != 0)
        break label1085;
      str5 = "red";
      localJSONObject4.put("StatusH", str5);
      i1 = Interface.getLIGHT_MODE(2, 1);
      if (i1 != 0)
        break label1105;
      str6 = "red";
      localJSONObject4.put("StatusS", str6);
      localJSONObject4.put("Time", 5);
      localJSONObject5.put("TrafficId", "4");
      i2 = Interface.getLIGHT_MODE(3, 0);
      if (i2 != 0)
        break label1125;
      str7 = "red";
      localJSONObject5.put("StatusH", str7);
      i3 = Interface.getLIGHT_MODE(3, 1);
      if (i3 != 0)
        break label1145;
      str8 = "red";
      localJSONObject5.put("StatusS", str8);
      localJSONObject5.put("Time", 5);
      localJSONObject6.put("TrafficId", "5");
      i4 = Interface.getLIGHT_MODE(4, 0);
      if (i4 != 0)
        break label1165;
      str9 = "red";
      localJSONObject6.put("StatusH", str9);
      i5 = Interface.getLIGHT_MODE(4, 1);
      if (i5 != 0)
        break label1185;
      str10 = "red";
      localJSONObject6.put("StatusS", str10);
      localJSONObject6.put("Time", 5);
      localJSONArray1.put(localJSONObject2);
      localJSONArray1.put(localJSONObject3);
      localJSONArray1.put(localJSONObject4);
      localJSONArray1.put(localJSONObject5);
      localJSONArray1.put(localJSONObject6);
      localJSONObject1.put("TrafficInfo", localJSONArray1);
      localJSONObject7.put("RoadLightId", "1");
      if (!Interface.getRoadLightState())
        break label1205;
      str11 = "Open";
      localJSONObject7.put("Status", str11);
      localJSONObject8.put("RoadLightId", "2");
      if (!Interface.getRoadLightState())
        break label1212;
      str12 = "Open";
      label632: localJSONObject8.put("Status", str12);
      localJSONObject9.put("RoadLightId", "3");
      if (!Interface.getRoadLightState())
        break label1219;
    }
    label1025: label1045: label1065: label1085: label1219: for (String str13 = "Open"; ; str13 = "Close")
    {
      localJSONObject9.put("Status", str13);
      localJSONArray2.put(localJSONObject7);
      localJSONArray2.put(localJSONObject8);
      localJSONArray2.put(localJSONObject9);
      localJSONObject1.put("RoadLightInfo", localJSONArray2);
      localJSONObject10.put("CarId", "1");
      localJSONObject10.put("Mode", 0);
      localJSONObject10.put("Locaton", Interface.getCarCurrentPoint(0));
      localJSONObject10.put("Path", Interface.getCarPathId(0));
      localJSONObject11.put("CarId", "1");
      localJSONObject11.put("Mode", 1);
      localJSONObject11.put("Locaton", Interface.getCarCurrentPoint(4));
      localJSONObject12.put("CarId", "2");
      localJSONObject12.put("Mode", 1);
      localJSONObject12.put("Locaton", Interface.getCarCurrentPoint(5));
      localJSONArray3.put(localJSONObject10);
      localJSONArray3.put(localJSONObject11);
      localJSONArray3.put(localJSONObject12);
      localJSONObject1.put("LocationInfo", localJSONArray3);
      localJSONObject13.put("GateId", "1");
      localJSONObject13.put("Mode", 0);
      localJSONObject13.put("Status", Interface.getEtcGateStatus(0));
      localJSONObject14.put("GateId", "2");
      localJSONObject14.put("Mode", 0);
      localJSONObject14.put("Status", Interface.getEtcGateStatus(1));
      localJSONObject15.put("GateId", "1");
      localJSONObject15.put("Mode", 1);
      localJSONObject15.put("Status", Interface.getParkGateStatus(0));
      localJSONObject16.put("GateId", "2");
      localJSONObject16.put("Mode", 1);
      localJSONObject16.put("Status", Interface.getParkGateStatus(1));
      localJSONArray4.put(localJSONObject13);
      localJSONArray4.put(localJSONObject14);
      localJSONArray4.put(localJSONObject15);
      localJSONArray4.put(localJSONObject16);
      localJSONObject1.put("GateStatusInfo", localJSONArray4);
      return localJSONObject1.toString();
      if (i == 1)
      {
        str1 = "yellow";
        break;
      }
      str1 = "green";
      break;
      if (j == 1)
      {
        str2 = "yellow";
        break label236;
      }
      str2 = "green";
      break label236;
      if (k == 1)
      {
        str3 = "yellow";
        break label281;
      }
      str3 = "green";
      break label281;
      if (m == 1)
      {
        str4 = "yellow";
        break label307;
      }
      str4 = "green";
      break label307;
      if (n == 1)
      {
        str5 = "yellow";
        break label352;
      }
      str5 = "green";
      break label352;
      label1105: if (i1 == 1)
      {
        str6 = "yellow";
        break label378;
      }
      str6 = "green";
      break label378;
      label1125: if (i2 == 1)
      {
        str7 = "yellow";
        break label423;
      }
      str7 = "green";
      break label423;
      label1145: if (i3 == 1)
      {
        str8 = "yellow";
        break label449;
      }
      str8 = "green";
      break label449;
      if (i4 == 1)
      {
        str9 = "yellow";
        break label494;
      }
      str9 = "green";
      break label494;
      if (i5 == 1)
      {
        str10 = "yellow";
        break label520;
      }
      str10 = "green";
      break label520;
      str11 = "Close";
      break label602;
      str12 = "Close";
      break label632;
    }
  }

  private String disposeAction(String paramString1, String paramString2)
  {
    new JSONArray();
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      JSONObject localJSONObject2;
      if ("GetCarSpeed".equals(paramString1))
        localJSONObject2 = new JSONObject(paramString2);
      String str28;
      try
      {
        int i = -1 + localJSONObject2.getInt("CarId");
        if (this.logEnable)
          Log.i("JasonHttpServer", "disposeAction return : " + i);
        String str1 = Interface.getCarSpeed(i);
        return str1;
        if ("GetCarLocation".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int j = -1 + localJSONObject2.getInt("CarId");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + j);
          localJSONObject1.put("CarLocation", Interface.getCarCurrentPoint(j));
          String str2 = localJSONObject1.toString();
          return str2;
        }
        if ("SetCarMove".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int k = -1 + localJSONObject2.getInt("CarId");
          String str3 = localJSONObject2.getString("CarAction");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : id = " + k + ", action = " + str3);
          if (str3.equals("Stop"));
          for (int m = 0; ; m = 1)
          {
            Interface.setCarOperate(k, m);
            localJSONObject1.put("result", "ok");
            String str4 = localJSONObject1.toString();
            return str4;
          }
        }
        if ("GetCarAccountBalance".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int n = -1 + localJSONObject2.getInt("CarId");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + n);
          localJSONObject1.put("Balance", Interface.getCusCardBalances(n));
          String str5 = localJSONObject1.toString();
          return str5;
        }
        if ("SetCarAccountRecharge".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i1 = -1 + localJSONObject2.getInt("CarId");
          int i2 = localJSONObject2.getInt("Money");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i1);
          Interface.setCusCardBalances(i1, i2);
          localJSONObject1.put("result", "ok");
          String str6 = localJSONObject1.toString();
          return str6;
        }
        if ("GetCarAccountRecord".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i3 = -1 + localJSONObject2.getInt("CarId");
          String str7 = localJSONObject2.getString("CostType");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i3);
          String str8 = Interface.getCarAccountRecord(i3, str7);
          return str8;
        }
        if ("GetTrafficLightNowStatus".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          String str9 = localJSONObject2.getString("TrafficLightId");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + 0);
          int i4 = -1 + Integer.valueOf(str9.substring(0, 1)).intValue();
          if (Integer.valueOf(str9.substring(2)).intValue() == 1);
          for (int i5 = 0; ; i5 = 1)
          {
            String str10 = Interface.getTrafficLightStatusTime(i4, i5);
            return str10;
          }
        }
        if ("SetTrafficLightNowStatus".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          String str11 = localJSONObject2.getString("TrafficLightId");
          String str12 = localJSONObject2.getString("Status");
          int i6 = localJSONObject2.getInt("Time");
          int i7 = -1 + Integer.valueOf(str11.substring(0, 1)).intValue();
          Log.i("JasonHttpServer", "disposeAction return id: " + i7 + ", state:" + str12 + ", time:" + i6);
          Interface.setTrafficLightTime(i7, str12, i6);
          localJSONObject1.put("result", "ok");
          String str13 = localJSONObject1.toString();
          return str13;
        }
        if ("GetTrafficLightConfigAction".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i8 = -1 + localJSONObject2.getInt("TrafficLightId");
          Log.i("JasonHttpServer", "disposeAction return : " + i8);
          String str14 = Interface.getTrafficLightTimeConfig(i8);
          return str14;
        }
        if ("SetTrafficLightConfig".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i9 = -1 + localJSONObject2.getInt("TrafficLightId");
          int i10 = localJSONObject2.getInt("RedTime");
          int i11 = localJSONObject2.getInt("GreenTime");
          int i12 = localJSONObject2.getInt("YellowTime");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i9);
          Interface.setTrafficLightConfig(i9, i10, i11, i12);
          localJSONObject1.put("result", "ok");
          String str15 = localJSONObject1.toString();
          return str15;
        }
        if ("GetRoadLightStatus".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i13 = -1 + localJSONObject2.getInt("RoadLightId");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i13);
          if (Interface.getRoadLightState());
          for (String str16 = "Open"; ; str16 = "Close")
          {
            localJSONObject1.put("Status", str16);
            String str17 = localJSONObject1.toString();
            return str17;
          }
        }
        if ("SetRoadLightStatusAction".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i14 = -1 + localJSONObject2.getInt("RoadLightId");
          String str18 = localJSONObject2.getString("Action");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i14);
          Interface.setRoadLightState(str18);
          localJSONObject1.put("result", "ok");
          String str19 = localJSONObject1.toString();
          return str19;
        }
        if ("SetRoadLightControlMode".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          String str20 = localJSONObject2.getString("ControlMode");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + str20);
          Interface.setRoadLightControlMode(str20);
          localJSONObject1.put("result", "ok");
          String str21 = localJSONObject1.toString();
          return str21;
        }
        if ("SetParkBlackList".equals(paramString1))
        {
          JSONObject localJSONObject3 = new JSONObject(paramString2);
          Interface.SetParkBlackList(localJSONObject3.getJSONArray("ParkBlackList"));
          localJSONObject1.put("result", "ok");
          return localJSONObject1.toString();
        }
        if ("SetParkRate".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          String str22 = localJSONObject2.getString("RateType");
          int i15 = localJSONObject2.getInt("Money");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + str22 + ",menoy = " + i15);
          Interface.setParkETCRate(1, str22, i15);
          localJSONObject1.put("result", "ok");
          String str23 = localJSONObject1.toString();
          return str23;
        }
        if ("GetParkRate".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          String str24 = localJSONObject2.getString("RateType");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + paramString1);
          if ("Count".equals(str24));
          for (int i16 = 1; ; i16 = 0)
          {
            String str25 = Interface.getParkETCRate(1, i16);
            return str25;
          }
        }
        if ("GetParkFree".equals(paramString1))
        {
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + paramString1);
          return Interface.getParkFree();
        }
        if ("GetPakingListReport".equals(paramString1))
          return Interface.GetPakingListReport();
        if ("SetEtcBlackList".equals(paramString1))
        {
          JSONObject localJSONObject4 = new JSONObject(paramString2);
          Interface.SetEtcBlackList(localJSONObject4.getJSONArray("EtcBlackList"));
          localJSONObject1.put("result", "ok");
          return localJSONObject1.toString();
        }
        if ("SetEtcRate".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          String str26 = localJSONObject2.getString("RateType");
          int i17 = localJSONObject2.getInt("Money");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + str26);
          Interface.setParkETCRate(0, str26, i17);
          localJSONObject1.put("result", "ok");
          String str27 = localJSONObject1.toString();
          return str27;
        }
        if ("GetEtcRate".equals(paramString1))
        {
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + paramString1);
          return Interface.getParkETCRate(0, 0);
        }
        if ("GetEtcListReport".equals(paramString1))
          return Interface.GetEtcListReport();
        if ("GetAllSense".equals(paramString1))
          return Interface.getSensorValue(-1);
        if (!"GetSenseByName".equals(paramString1))
          break label2034;
        localJSONObject2 = new JSONObject(paramString2);
        str28 = localJSONObject2.getString("SenseName");
        if (this.logEnable)
          Log.i("JasonHttpServer", "disposeAction return : " + str28);
        if (str28.equals("LightIntensity"));
        for (this.Sense_id = 0; ; this.Sense_id = 1)
        {
          localJSONObject1.put(str28, Interface.getSensorValue(this.Sense_id));
          String str29 = localJSONObject1.toString();
          return str29;
          if (!str28.equals("pm2.5"))
            break;
        }
      }
      catch (JSONException localJSONException1)
      {
      }
      label1970: localJSONException1.printStackTrace();
      label2034: 
      do
      {
        return null;
        if (str28.equals("co2"))
        {
          this.Sense_id = 2;
          break;
        }
        if (str28.equals("humidity"))
        {
          this.Sense_id = 3;
          break;
        }
        if (!str28.equals("temperature"))
          break;
        this.Sense_id = 4;
        break;
        if ("SetLightSenseValve".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i18 = localJSONObject2.getInt("Down");
          int i19 = localJSONObject2.getInt("Up");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i18);
          Interface.setLightSensorThreshold(i19, i18);
          localJSONObject1.put("result", "ok");
          String str30 = localJSONObject1.toString();
          return str30;
        }
        if ("GetLightSenseValve".equals(paramString1))
        {
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + paramString1);
          return Interface.getLightSensorThreshold();
        }
        if ("GetBusStationInfo".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i20 = -1 + localJSONObject2.getInt("BusStationId");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i20);
          String str31 = Interface.getBusStationInfo(i20);
          return str31;
        }
        if ("GetBusCapacity".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i21 = -1 + localJSONObject2.getInt("BusId");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i21);
          localJSONObject1.put("BusCapacity", Interface.getBusCapacity(i21));
          String str32 = localJSONObject1.toString();
          return str32;
        }
        if ("GetBusSpeed".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i22 = -1 + localJSONObject2.getInt("BusId");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i22);
          localJSONObject1.put("BusSpeed", Interface.getBusSpeed(i22));
          String str33 = localJSONObject1.toString();
          return str33;
        }
        if ("GetBusLocationAction".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i23 = 3 + localJSONObject2.getInt("BusId");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i23);
          localJSONObject1.put("BusLocaton", Interface.getCarCurrentPoint(i23));
          String str34 = localJSONObject1.toString();
          return str34;
        }
        if ("GetRoadStatus".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i24 = -1 + localJSONObject2.getInt("RoadId");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i24);
          localJSONObject1.put("Status", Interface.getRoadStatus(i24));
          String str35 = localJSONObject1.toString();
          return str35;
        }
        if ("GetEtcGateStatus".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i25 = -1 + localJSONObject2.getInt("GateId");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i25);
          localJSONObject1.put("Status", Interface.getEtcGateStatus(i25));
          String str36 = localJSONObject1.toString();
          return str36;
        }
        if ("GetParkGateStatus".equals(paramString1))
        {
          localJSONObject2 = new JSONObject(paramString2);
          int i26 = -1 + localJSONObject2.getInt("GateId");
          if (this.logEnable)
            Log.i("JasonHttpServer", "disposeAction return : " + i26);
          localJSONObject1.put("Status", "Open");
          Interface.getParkGateStatus(i26);
          String str37 = localJSONObject1.toString();
          return str37;
        }
        if ("GetCyclicInfo".equals(paramString1))
          return Interface.getSyncInfo();
      }
      while (!"GetSyncInfo".equals(paramString1));
      String str38 = GetSyncInfo();
      return str38;
    }
    catch (JSONException localJSONException2)
    {
      break label1970;
    }
  }

  private String parsebody(IHTTPSession paramIHTTPSession)
  {
    Object localObject = "";
    try
    {
      InputStream localInputStream = paramIHTTPSession.getInputStream();
      if (localInputStream == null)
      {
        Log.i("JasonHttpServer", "session.getInputStream() is null!");
        return localObject;
      }
      long l = 0L;
      Map localMap = paramIHTTPSession.getHeaders();
      if (localMap.containsKey("content-length"))
        l = Integer.parseInt((String)localMap.get("content-length"));
      if (l > 0L)
      {
        byte[] arrayOfByte = new byte[8192];
        int i = localInputStream.read(arrayOfByte, 0, (int)Math.min(l, 8192));
        if (i <= 0)
        {
          Log.i("JasonHttpServer", "http body read 0 byte!");
          return null;
        }
        String str = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(arrayOfByte, 0, i))).readLine();
        localObject = str;
      }
      return localObject;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return (String)null;
  }

  public Response serve(IHTTPSession paramIHTTPSession)
  {
    String str1 = "";
    if (paramIHTTPSession.getMethod() == Method.POST)
      str1 = parsebody(paramIHTTPSession);
    String str2 = paramIHTTPSession.getUri();
    if (this.logEnable)
      Log.i("JasonHttpServer", "Http url:" + str2 + ", body:" + str1);
    String str3 = "";
    String str4 = "";
    String[] arrayOfString = str2.split("/");
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfString.length)
      {
        Map localMap = paramIHTTPSession.getHeaders();
        if ((str4.equals("soap")) && (localMap.containsKey("soapaction")))
          str3 = ((String)localMap.get("soapaction")).toString();
        if (this.logEnable)
          Log.i("JasonHttpServer", "protocol action:" + str3 + ", type:" + str4);
        String str6 = disposeAction(str3, str1) + "\n";
        if (this.logEnable)
          Log.i("JasonHttpServer", "return :" + str6);
        return new Response(str6);
      }
      String str5 = arrayOfString[i];
      if (str5.equals("type"))
        str4 = arrayOfString[(i + 1)];
      if (!str5.equals("action"))
        continue;
      str3 = arrayOfString[(i + 1)];
    }
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.services.MyNanoHTTPD
 * JD-Core Version:    0.6.0
 */