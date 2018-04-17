package com.ut.electronictraffic;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.ut.electronictraffic.classes.EnvSensor;
import com.ut.electronictraffic.classes.Factory;
import com.ut.electronictraffic.classes.StreetLight;
import com.ut.electronictraffic.classes.TrafficLight;
import com.ut.electronictraffic.classes.carActivity;
import com.ut.electronictraffic.services.MyNanoHTTPD;
import com.ut.electronictraffic.services.NanoHTTPD;

public class Settings extends Activity
  implements OnItemSelectedListener
{
  private static int CAR_NUMBER = 0;
  private static final int RESULT_SYNC = 10;
  private static boolean bStart = false;
  private Button bt_start;
  private carActivity car;
  private carApplication carApp;
  private int car_id;
  private int car_num;
  private int car_value;
  private Editor editor;
  private EditText et_1;
  private EditText et_2;
  private EditText et_3;
  private EditText et_4;
  private EditText et_5;
  private EditText et_6;
  private EditText et_7;
  private int etc_cost;
  private EditText ev_ip;
  private Factory factory;
  private String ip = "";
  private StreetLight light;
  private int lot_cost;
  private NanoHTTPD nanoHttpd = null;
  private String remote_ip = "";
  private int remote_port = 8890;
  private EnvSensor sensor;
  private SharedPreferences sharedPrefs;
  private Spinner sp_car;
  private Spinner sp_traffic;
  private int thresholdH;
  private int thresholdL;
  private TrafficLight traffic;
  private int traffic_id;
  private int traffic_time;
  private TextView tx_ip;
  private WifiManager wifiManager;

  private void initView()
  {
    this.thresholdL = this.sharedPrefs.getInt("thresholdL", 110);
    this.thresholdH = this.sharedPrefs.getInt("thresholdH", 190);
    this.traffic_id = this.sharedPrefs.getInt("traffic_id", 1);
    this.traffic_time = this.sharedPrefs.getInt("traffic_time_" + this.traffic_id, 5);
    this.lot_cost = this.sharedPrefs.getInt("lot_cost", 2);
    this.etc_cost = this.sharedPrefs.getInt("etc_cost", 5);
    this.car_id = this.sharedPrefs.getInt("car_id", 1);
    this.car_value = this.sharedPrefs.getInt("car_value_" + this.car_id, 0);
    this.car_num = this.sharedPrefs.getInt("car_num", 1);
    this.et_1 = ((EditText)findViewById(2131296287));
    this.et_2 = ((EditText)findViewById(2131296298));
    this.et_1.setText(this.thresholdL);
    this.et_2.setText(this.thresholdH);
    this.et_3 = ((EditText)findViewById(2131296300));
    this.et_3.setText(this.traffic_time);
    this.et_4 = ((EditText)findViewById(2131296303));
    this.et_4.setText(this.lot_cost);
    this.et_5 = ((EditText)findViewById(2131296304));
    this.et_5.setText(this.etc_cost);
    this.et_6 = ((EditText)findViewById(2131296306));
    this.et_6.setText(this.car_value);
    this.et_7 = ((EditText)findViewById(2131296302));
    this.et_7.setText(this.car_num);
    this.ev_ip = ((EditText)findViewById(2131296309));
    String str = this.sharedPrefs.getString("remoteIp", "192.168.11.20");
    this.ev_ip.setText(str);
    this.sp_traffic = ((Spinner)findViewById(2131296340));
    this.sp_traffic.setSelection(this.traffic_id);
    this.sp_car = ((Spinner)findViewById(2131296291));
    this.sp_car.setSelection(this.car_id);
    this.bt_start = ((Button)findViewById(2131296310));
    this.tx_ip = ((TextView)findViewById(2131296311));
  }

  private String intToIp(int paramInt)
  {
    return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "." + (0xFF & paramInt >> 24);
  }

  private void openSetttingsApk()
  {
    Intent localIntent = new Intent();
    localIntent.setClassName("com.android.settings", "com.android.settings.Settings");
    startActivity(localIntent);
  }

  private boolean saveInfo()
  {
    this.thresholdL = Integer.parseInt(this.et_1.getText().toString());
    this.thresholdH = Integer.parseInt(this.et_2.getText().toString());
    this.traffic_time = Integer.parseInt(this.et_3.getText().toString());
    this.lot_cost = Integer.parseInt(this.et_4.getText().toString());
    this.etc_cost = Integer.parseInt(this.et_5.getText().toString());
    this.car_value = Integer.parseInt(this.et_6.getText().toString());
    int i = Integer.parseInt(this.et_7.getText().toString());
    this.sensor.setLihgtSensorThreshold(this.thresholdL, this.thresholdH);
    this.factory.setFactoryCost(1, "Count", this.lot_cost);
    this.factory.setFactoryCost(0, "Count", this.etc_cost);
    this.factory.addFactoryCost(this.car_id, this.car_value);
    this.editor.putInt("thresholdL", this.thresholdL);
    this.editor.putInt("thresholdH", this.thresholdH);
    this.editor.putInt("traffic_time_" + this.traffic_id, this.traffic_time);
    this.editor.putInt("lot_cost", this.lot_cost);
    this.editor.putInt("etc_cost", this.etc_cost);
    this.editor.putInt("car_value_" + this.car_id, this.car_value);
    this.editor.putInt("car_num", i);
    this.editor.commit();
    if (this.car_num != i)
    {
      this.car_num = i;
      return true;
    }
    return false;
  }

  private void startHTTP()
  {
    try
    {
      if (!this.nanoHttpd.isAlive())
      {
        this.nanoHttpd.start();
        Log.e("DEMO", this.nanoHttpd.isAlive() + "---, ip = " + this.ip);
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void stopHTTP()
  {
    try
    {
      this.nanoHttpd.stop();
      Log.e("DEMO", this.nanoHttpd.isAlive() + "---, ip = " + this.ip);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void onClickButton(View paramView)
  {
    int i = paramView.getId();
    Intent localIntent = new Intent(this, MainActivity.class);
    switch (i)
    {
    case 2131296290:
    case 2131296291:
    case 2131296292:
    case 2131296295:
    case 2131296296:
    case 2131296297:
    case 2131296298:
    case 2131296299:
    case 2131296300:
    case 2131296301:
    case 2131296302:
    case 2131296303:
    case 2131296304:
    case 2131296305:
    case 2131296306:
    case 2131296308:
    case 2131296309:
    case 2131296311:
    case 2131296314:
    case 2131296317:
    case 2131296318:
    case 2131296319:
    case 2131296320:
    case 2131296321:
    case 2131296322:
    case 2131296323:
    case 2131296324:
    case 2131296325:
    case 2131296326:
    case 2131296327:
    case 2131296328:
    case 2131296329:
    case 2131296330:
    case 2131296331:
    default:
      return;
    case 2131296312:
      openSetttingsApk();
      return;
    case 2131296313:
      String str = this.ev_ip.getText().toString();
      this.editor.putString("remoteIp", str);
      this.editor.commit();
      setResult(10, localIntent);
      finish();
      return;
    case 2131296310:
      boolean bool2;
      if (bStart)
      {
        stopHTTP();
        this.bt_start.setText("开启服务");
        this.tx_ip.setVisibility(8);
        this.tx_ip.setText("");
        boolean bool1 = bStart;
        bool2 = false;
        if (!bool1)
          break label375;
      }
      while (true)
      {
        bStart = bool2;
        return;
        startHTTP();
        if (!this.nanoHttpd.isAlive())
          break;
        this.bt_start.setText("关闭服务");
        this.tx_ip.setVisibility(0);
        this.tx_ip.setText(this.ip);
        break;
        bool2 = true;
      }
    case 2131296288:
      Integer.parseInt(this.et_1.getText().toString());
      Integer.parseInt(this.et_2.getText().toString());
      return;
    case 2131296293:
      int k = Integer.parseInt(this.et_3.getText().toString());
      this.editor.putInt("traffic_time_" + this.traffic_id, k);
      this.editor.commit();
      return;
    case 2131296294:
      Integer.parseInt(this.et_4.getText().toString());
      return;
    case 2131296289:
      Integer.parseInt(this.et_5.getText().toString());
      return;
    case 2131296307:
      int j = Integer.parseInt(this.et_6.getText().toString());
      this.factory.addFactoryCost(this.car_id, j);
      this.editor.putInt("car_value_" + this.car_id, j);
      this.editor.commit();
      return;
    case 2131296315:
      this.light.setStreetLightMode(0);
      return;
    case 2131296316:
      this.light.setStreetLightMode(1);
      return;
    case 2131296333:
      label375: if (saveInfo())
        setResult(-1, localIntent);
      while (true)
      {
        finish();
        return;
        setResult(0, localIntent);
      }
    case 2131296332:
    }
    setResult(0, localIntent);
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    getWindow().addFlags(128);
    getWindow().setFlags(1024, 1024);
    setContentView(2130903046);
    this.sharedPrefs = getSharedPreferences("car_traffic_info", 0);
    this.editor = this.sharedPrefs.edit();
    this.carApp = ((carApplication)getApplication()).getAppInstance();
    this.light = this.carApp.getStreetLight();
    this.traffic = this.carApp.getTraffic();
    this.factory = this.carApp.getFactory();
    this.sensor = this.carApp.getEnvSensor();
    initView();
    this.wifiManager = ((WifiManager)getSystemService("wifi"));
    if (!this.wifiManager.isWifiEnabled())
      this.wifiManager.setWifiEnabled(true);
    this.ip = intToIp(this.wifiManager.getConnectionInfo().getIpAddress());
    this.nanoHttpd = new MyNanoHTTPD(this, this.ip, 8890);
  }

  public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    switch (paramView.getId())
    {
    case 2131296323:
    case 2131296324:
    case 2131296327:
    default:
      return;
    case 2131296340:
      this.traffic_id = paramInt;
      this.et_3.setText(this.sharedPrefs.getInt(new StringBuilder("traffic_time_").append(this.traffic_id).toString(), 5));
      return;
    case 2131296291:
      this.car_id = paramInt;
      this.et_6.setText(this.sharedPrefs.getInt(new StringBuilder("car_value_").append(this.car_id).toString(), 5));
      return;
    case 2131296319:
    }
    this.car_id = paramInt;
    CAR_NUMBER = paramInt + 1;
  }

  public void onNothingSelected(AdapterView<?> paramAdapterView)
  {
  }

  public void onResume()
  {
    super.onResume();
    this.wifiManager = ((WifiManager)getSystemService("wifi"));
    this.ip = intToIp(this.wifiManager.getConnectionInfo().getIpAddress());
    if (bStart)
    {
      this.bt_start.setText("关闭服务");
      this.tx_ip.setVisibility(0);
      this.tx_ip.setText(this.ip);
      bStart = true;
    }
    while (true)
    {
      String str = this.sharedPrefs.getString("remoteIp", "192.168.1.20");
      this.ev_ip.setText(str);
      return;
      this.bt_start.setText("开启服务");
      this.tx_ip.setVisibility(8);
      bStart = false;
    }
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.Settings
 * JD-Core Version:    0.6.0
 */