package com.ut.electronictraffic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ut.electronictraffic.classes.BusStation;
import com.ut.electronictraffic.classes.EnvSensor;
import com.ut.electronictraffic.classes.Factory;
import com.ut.electronictraffic.classes.StreetLight;
import com.ut.electronictraffic.classes.TrafficLight;
import com.ut.electronictraffic.classes.carActivity;
import com.ut.electronictraffic.interfaces.Interface;
import com.ut.electronictraffic.interfaces.PlayListAdapter;
import com.ut.electronictraffic.interfaces.RECT;
import com.ut.electronictraffic.interfaces.Utils;
import com.ut.electronictraffic.interfaces.VideoFile;
import com.ut.electronictraffic.services.MyNanoHTTPD;
import com.ut.electronictraffic.services.NanoHTTPD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    public static final int BEGIN_RUN = 1000;
    private static final int BUTTON_NUM = 7;
    private static final int BUTTON_NUM2 = 3;
    public static final int KILL_SHOW_LOT_ETC_UI = 1021;
    private static final boolean LOG = false;
    private static final int RESULT_SYNC = 10;
    public static final int SHOW_BUSSTATION_UI = 1010;
    private static final int SHOW_DEBUG_INFO = 1028;
    public static final int SHOW_INFO_0 = 1001;
    public static final int SHOW_INFO_1 = 1002;
    public static final int SHOW_INFO_2 = 1003;
    public static final int SHOW_INFO_3 = 1004;
    public static final int SHOW_INFO_4 = 1005;
    public static final int SHOW_INFO_5 = 1006;
    private static final int SHOW_LOTETC_UI_TIME = 6000;
    public static final int SHOW_LOT_ETC_UI = 1020;
    public static final int SHOW_LOT_ETC_UI_1 = 1009;
    public static final int SHOW_LOT_ETC_UI_2 = 1017;
    public static final int SHOW_LOT_ETC_UI_3 = 1018;
    public static final int SHOW_LOT_ETC_UI_4 = 1019;
    public static final int SHOW_LOT_ETC_UI_CLOSE_1 = 1013;
    public static final int SHOW_LOT_ETC_UI_CLOSE_2 = 1014;
    public static final int SHOW_LOT_ETC_UI_CLOSE_3 = 1015;
    public static final int SHOW_LOT_ETC_UI_CLOSE_4 = 1016;
    public static final int SHOW_PARKING_LOT = 1008;
    private static final int SHOW_REPORT_ERROR = 1031;
    private static final int SHOW_REPORT_ETC_INFO = 1029;
    private static final int SHOW_REPORT_PARK_INFO = 1030;
    public static final int SHOW_STREET_LIGHT = 1007;
    private static final int SHOW_SYNC_INFO_0 = 1025;
    private static final int SHOW_SYNC_INFO_1 = 1026;
    private static final int SHOW_SYNC_INFO_2 = 1027;
    public static final int SHOW_TRAFFIC_LIGHT_UI = 1011;
    public static final int SHOW_TRAFFIC_LIGHT_UI2 = 1032;
    public static final int SHOW_TRAFFIC_LIGHT_UI3 = 1033;
    public static final int SHOW_TRAFFIC_LIGHT_UI4 = 1034;
    public static final int SHOW_TRAFFIC_LIGHT_UI5 = 1035;
    public static final int SHOW_VIEW_UI = 1012;
    private static final int TASK_BAD_REQUEST = 1024;
    private static final int TASK_LOOP_COMPLETE = 1022;
    private static final int TASK_LOOP_SYNC = 1023;
    public static final String[] TOP;
    private static int[][] car_location;
    private static final int hight = 30;
    private static List<VideoFile> list = new ArrayList();
    private static List<VideoFile> list2 = new ArrayList();
    private static int[][] mBackModeStatus;
    private static int old_angle = 0;
    private static int old_x = 0;
    private static int old_y = 0;
    private static final int width = 50;
    private boolean StreetLightmode;
    private AbsoluteLayout absoluteLayout_layout;
    private int[] angle;
    carApplication app;
    private boolean bClean;
    private boolean bRun = false;
    private boolean bStreetLightOn;
    private boolean bTurnOn = false;
    private boolean bViewInit = false;
    private Button bt_begin;
    private Button bt_start;
    private carActivity car;
    private int car_num = 1;
    private int[] direct;
    private int[] door_state = new int[4];
    int[] drawable2_id;
    int[] drawable_id;
    private Editor editor;
    private String etc_inTime = "";
    private File file = new File("/mnt/extsd/log.txt");
    Interface interf;
    private String ip = "";
    private PlayListAdapter itla;
    private PlayListAdapter itla2;
    private ImageView iv_button_light;
    private ImageView iv_button_play;
    private ImageView iv_button_s1;
    private ImageView iv_button_s2;
    private ImageView iv_button_set;
    private ImageView iv_light_10;
    private ImageView iv_light_11;
    private ImageView iv_light_2;
    private ImageView iv_light_3;
    private ImageView iv_light_4;
    private ImageView iv_light_5;
    private ImageView iv_light_6;
    private ImageView iv_light_7;
    private ImageView iv_light_8;
    private ImageView iv_light_9;
    private ImageView iv_shu_01;
    private ImageView iv_shu_02;
    private ImageView iv_za_01;
    private ImageView iv_za_02;
    private String[] mBackStatus = new String[5];
    private int mCarId = 0;
    private ProgressDialog mDialog;
    public Handler mHandler = new Handler() {
        @SuppressLint({"NewApi"})
        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            Bundle localBundle = paramMessage.getData();
            label255:
            int i25;
            JSONArray localJSONArray3;
            int i27;
            label354:
            JSONArray localJSONArray4;
            int i29;
            label420:
            label588:
            int i36;
            int i37;
            int i38;
            int i39;
            label730:
            int i42;
            int i40;
            int i43;
            label768:
            int i44;
            switch (paramMessage.what) {
                case 1021:
                default:
                case 1028:
                case 1024:
                case 1023:
                case 1022:
                    JSONObject localJSONObject4;
                    while (true) {
                        return;
                        removeMessages(1028);
                        MainActivity.this.showMSG(MainActivity.this.run(MainActivity.TOP));
                        MainActivity.this.sendMsg(1028, 20000);
                        return;
                        removeMessages(1024);
                        MainActivity.this.showBadRequest();
                        MainActivity.this.sendMsg(1023, 1000);
                        return;
                        removeMessages(1023);
                        MainActivity.this.getShaPanSyncInfo();
                        return;
                        boolean bool6;
                        int i47;
                        String str2;
                        if (!MainActivity.this.bViewInit) {
                            MainActivity localMainActivity5 = MainActivity.this;
                            if (MainActivity.this.bViewInit) {
                                bool6 = false;
                                localMainActivity5.bViewInit = bool6;
                                i47 = 0;
                                if (i47 < 2)
                                    break label420;
                            }
                        } else {
                            str2 = localBundle.getString("syncInfo");
                        }
                        try {
                            JSONObject localJSONObject1 = new JSONObject(str2);
                            JSONArray localJSONArray1 = localJSONObject1.getJSONArray("TrafficInfo");
                            int i23 = 0;
                            int i24 = localJSONArray1.length();
                            if (i23 >= i24) {
                                localJSONArray2 = localJSONObject1.getJSONArray("LocationInfo");
                                i25 = 0;
                                int i26 = localJSONArray2.length();
                                if (i25 >= i26) {
                                    localJSONArray3 = localJSONObject1.getJSONArray("RoadLightInfo");
                                    i27 = 0;
                                    int i28 = localJSONArray3.length();
                                    if (i27 < i28)
                                        break label970;
                                    localJSONArray4 = localJSONObject1.getJSONArray("GateStatusInfo");
                                    i29 = 0;
                                    int i30 = localJSONArray4.length();
                                    if (i29 < i30)
                                        break label1016;
                                    if (!Interface.getSyncMode())
                                        continue;
                                    MainActivity.this.sendMsg(1023, 1000);
                                    return;
                                    bool6 = true;
                                    break label255;
                                    for (int i48 = 0; ; i48++) {
                                        if (i48 >= 6) {
                                            i47++;
                                            break;
                                        }
                                        MainActivity.car_location[i47][i48] = 0;
                                    }
                                }
                            } else {
                                JSONObject localJSONObject5 = localJSONArray1.getJSONObject(i23);
                                i45 = localJSONObject5.getInt("TrafficId");
                                str5 = localJSONObject5.getString("StatusH");
                                str6 = localJSONObject5.getString("StatusS");
                                localJSONObject5.getInt("Time");
                                if ((str5.equals("red")) && (str6.equals("yellow"))) {
                                    i46 = 0;
                                    Interface.setLIGHT_MODE(i45 - 1, 0, 1);
                                }
                                while (true) {
                                    Interface.setLIGHT_MODE_UI(i45 - 1, i46);
                                    i23++;
                                    break;
                                    if ((!str5.equals("green")) || (!str6.equals("red")))
                                        break label588;
                                    i46 = 1;
                                    Interface.setLIGHT_MODE(i45 - 1, 2, 0);
                                }
                            }
                        } catch (JSONException localJSONException) {
                            JSONArray localJSONArray2;
                            while (true) {
                                int i45;
                                String str5;
                                String str6;
                                localJSONException.printStackTrace();
                                continue;
                                if ((str5.equals("yellow")) && (str6.equals("red"))) {
                                    i46 = 2;
                                    Interface.setLIGHT_MODE(i45 - 1, 1, 0);
                                    continue;
                                }
                                boolean bool4 = str5.equals("red");
                                int i46 = 0;
                                if (!bool4)
                                    continue;
                                boolean bool5 = str6.equals("green");
                                i46 = 0;
                                if (!bool5)
                                    continue;
                                i46 = 3;
                                Interface.setLIGHT_MODE(i45 - 1, 0, 2);
                            }
                            localJSONObject4 = localJSONArray2.getJSONObject(i25);
                            i36 = localJSONObject4.getInt("CarId");
                            i37 = localJSONObject4.getInt("Mode");
                            i38 = localJSONObject4.getInt("Locaton");
                            i39 = 1;
                            if (i37 != 0)
                                break label4036;
                        }
                    }
                    i39 = 1 + localJSONObject4.getInt("Path");
                    break;
                Utils.CAR_LOCATON[(i38 - 1)][3];
                i42 = Utils.CAR_LOCATON[(i38 - 1)][2];
                if (i40 > 3)
                    if (i42 == 0) {
                        i43 = 15;
                        (Utils.CAR_LOCATON[(i38 - 1)][0] - i43);
                        if (i40 <= 3)
                            break label4139;
                        if (i42 != 2)
                            break label4120;
                        i44 = 20;
                    }
                case 1025:
                case 1026:
                case 1027:
                case 1020:
                case 1012:
                case 1001:
                case 1002:
                case 1003:
                case 1004:
                case 1005:
                case 1006:
                case 1000:
                case 1011:
                case 1010:
                case 1007:
                case 1009:
                case 1017:
                case 1018:
                case 1019:
                case 1008:
                case 1013:
                case 1014:
                case 1015:
                case 1016:
            }
            while (true) {
                (Utils.CAR_LOCATON[(i38 - 1)][1] - i44);
                if (MainActivity.car_location[i37][i40] != i38) {
                    removeMessages(Utils.SHOW_SYNC_INFO[i40]);
                    MainActivity.car_location[i37][i40] = i38;
                    MainActivity.this.path_Index[i37][i40] = -1;
                    MainActivity.this.map.clear();
                    MainActivity.this.map.put("car_id", Integer.valueOf(i40));
                    MainActivity.this.map.put("pathid", Integer.valueOf(i41));
                    MainActivity.this.map.put("mode", Integer.valueOf(i37));
                    MainActivity.this.map.put("location", Integer.valueOf(i38));
                    Utils.sendMsgToHandle(MainActivity.this.mHandler, Utils.SHOW_SYNC_INFO[i40], MainActivity.this.map, 1);
                    break label4071;
                    label970:
                    JSONObject localJSONObject3 = localJSONArray3.getJSONObject(i27);
                    localJSONObject3.getInt("RoadLightId");
                    String str4 = localJSONObject3.getString("Status");
                    MainActivity.this.turnOnStreetLight(str4.equals("Open"));
                    i27++;
                    break label354;
                    label1016:
                    JSONObject localJSONObject2 = localJSONArray4.getJSONObject(i29);
                    int i31 = localJSONObject2.getInt("GateId");
                    int i32 = localJSONObject2.getInt("Mode");
                    String str3 = localJSONObject2.getString("Status");
                    int i33;
                    label1065:
                    MainActivity localMainActivity4;
                    if (str3.equals("Open")) {
                        i33 = 1;
                        int i34 = MainActivity.mBackModeStatus[i32][(i31 - 1)];
                        if (i33 != i34) {
                            MainActivity.mBackModeStatus[i32][(i31 - 1)] = i33;
                            localMainActivity4 = MainActivity.this;
                            if (i32 != 0)
                                break label1145;
                        }
                    }
                    label1145:
                    for (int i35 = 100; ; i35 = 101) {
                        localMainActivity4.showLotEtcUI(i31, i35, i31 - 1, str3.equals("Open"));
                        i29++;
                        break;
                        i33 = 2;
                        break label1065;
                    }
                    if (!Interface.getSyncMode())
                        removeMessages(paramMessage.what);
                    int i10 = localBundle.getInt("car_id");
                    int i11 = localBundle.getInt("mode");
                    int i12 = localBundle.getInt("pathid");
                    if (i11 == 1)
                        i12 = i10;
                    int i13 = localBundle.getInt("location");
                    if (i10 == 4)
                        Log.d("-----ii----", "--------path_Index[mode][cID] = " + MainActivity.this.path_Index[i11][i10] + ",carid = " + i10 + ", car_location = " + MainActivity.car_location[i11][i10] + ",location=" + i13);
                    int i18;
                    int i19;
                    int i20;
                    int i21;
                    if ((MainActivity.this.path_Index[i11][i10] == -1) && (i13 != 0)) {
                        i18 = Utils.CAR_RECT_SYNC[i12].length;
                        i19 = Utils.CAR_LOCATON[(i13 - 1)][2];
                        if (i10 <= 3)
                            break label1454;
                        if (i19 != 0)
                            break label1435;
                        i20 = 15;
                        if (i10 <= 3)
                            break label1479;
                        if (i19 != 2)
                            break label1460;
                        i21 = 20;
                    }
                    label1359:
                    int i14;
                    label1435:
                    label1691:
                    for (int i22 = 0; ; i22++) {
                        if (i22 >= i18)
                            ;
                        while (true) {
                            i14 = MainActivity.this.path_Index[i11][i10];
                            if (i14 != -1)
                                break label1697;
                            Log.d("-----ii----", "--------path_index = " + i14 + ",carid = " + i10 + ",location=" + i13);
                            return;
                            if (i19 == 1) {
                                i20 = 15;
                                break;
                            }
                            i20 = 0;
                            break;
                            label1454:
                            i20 = 0;
                            break;
                            label1460:
                            if (i19 == 3) {
                                i21 = 10;
                                break label1359;
                            }
                            i21 = 0;
                            break label1359;
                            label1479:
                            i21 = 0;
                            break label1359;
                            if ((Utils.CAR_LOCATON[(i13 - 1)][0] - i20 != Utils.CAR_RECT_SYNC[i12][i22][0]) || (Utils.CAR_LOCATON[(i13 - 1)][1] - i21 != Utils.CAR_RECT_SYNC[i12][i22][1]))
                                break label1691;
                            MainActivity.this.path_Index[i11][i10] = i22;
                            if (i10 == 4)
                                Log.d("-----ii----", "--------ii = " + i22 + ",carid = " + i10 + ",location=" + i13);
                            MainActivity.this.x_value[i10] = Utils.CAR_RECT_SYNC[i12][i22][0];
                            MainActivity.this.y_value[i10] = Utils.CAR_RECT_SYNC[i12][i22][1];
                            MainActivity.this.direct[i10] = Utils.CAR_RECT_SYNC[i12][i22][2];
                            MainActivity.this.angle[i10] = Utils.CAR_RECT_SYNC[i12][i22][3];
                        }
                    }
                    label1697:
                    RECT localRECT = new RECT();
                    localRECT.x = Utils.CAR_RECT_SYNC[i12][i14][0];
                    localRECT.y = Utils.CAR_RECT_SYNC[i12][i14][1];
                    localRECT.direct = Utils.CAR_RECT_SYNC[i12][i14][2];
                    localRECT.angle = Utils.CAR_RECT_SYNC[i12][i14][3];
                    boolean bool3;
                    if (i10 < 4) {
                        bool3 = false;
                        if (!MainActivity.this.app.getTraffic().isTrafficLight(localRECT, bool3))
                            break label1975;
                        MainActivity.this.app.getTraffic().getTrafficLightId(localRECT, bool3);
                        if ((localRECT.direct != 0) && (localRECT.direct != 180))
                            break label1972;
                    }
                    label1972:
                    while (true) {
                        if (2 == MainActivity.this.app.getTraffic().getTrafficLightId(localRECT, bool3))
                            break label2386;
                        MainActivity.this.map.clear();
                        MainActivity.this.map.put("car_id", Integer.valueOf(i10));
                        MainActivity.this.map.put("pathid", Integer.valueOf(i12));
                        MainActivity.this.map.put("mode", Integer.valueOf(i11));
                        MainActivity.this.map.put("location", Integer.valueOf(i13));
                        Utils.sendMsgToHandle(MainActivity.this.mHandler, Utils.SHOW_SYNC_INFO[i10], MainActivity.this.map, 200);
                        return;
                        bool3 = true;
                        break;
                    }
                    label1975:
                    if (MainActivity.this.app.getFactory().isFactoryEtcZa(localRECT)) {
                        int i17 = MainActivity.this.app.getFactory().getETCLOTId(localRECT, true);
                        if (MainActivity.mBackModeStatus[0][i17] == 2) {
                            MainActivity.this.map.clear();
                            MainActivity.this.map.put("car_id", Integer.valueOf(i10));
                            MainActivity.this.map.put("pathid", Integer.valueOf(i12));
                            MainActivity.this.map.put("mode", Integer.valueOf(i11));
                            MainActivity.this.map.put("location", Integer.valueOf(i13));
                            Utils.sendMsgToHandle(MainActivity.this.mHandler, Utils.SHOW_SYNC_INFO[i10], MainActivity.this.map, 200);
                            return;
                        }
                    } else {
                        if (MainActivity.this.app.getFactory().isFactoryLotZa(localRECT)) {
                            MainActivity.this.map.clear();
                            MainActivity.this.map.put("car_id", Integer.valueOf(i10));
                            MainActivity.this.map.put("pathid", Integer.valueOf(i12));
                            MainActivity.this.map.put("mode", Integer.valueOf(i11));
                            MainActivity.this.map.put("location", Integer.valueOf(i13));
                            Utils.sendMsgToHandle(MainActivity.this.mHandler, Utils.SHOW_SYNC_INFO[i10], MainActivity.this.map, 1500);
                            return;
                        }
                        if (MainActivity.this.app.getFactory().isFactoryLot(localRECT)) {
                            MainActivity.this.map.clear();
                            MainActivity.this.map.put("car_id", Integer.valueOf(i10));
                            MainActivity.this.map.put("pathid", Integer.valueOf(i12));
                            MainActivity.this.map.put("mode", Integer.valueOf(i11));
                            MainActivity.this.map.put("location", Integer.valueOf(i13));
                            Utils.sendMsgToHandle(MainActivity.this.mHandler, Utils.SHOW_SYNC_INFO[i10], MainActivity.this.map, 1500);
                            return;
                        }
                    }
                    label2386:
                    int[][] arrayOfInt = Utils.CAR_RECT_SYNC[i12];
                    int i15 = arrayOfInt.length;
                    if (i14 != -1)
                        if (MainActivity.this.direct[i10] == 3) {
                            if ((arrayOfInt[i14][0] <= MainActivity.this.x_value[i10]) && (MainActivity.this.x_value[i10] < arrayOfInt[((i14 + 1) % i15)][0])) {
                                int[] arrayOfInt5 = MainActivity.this.x_value;
                                arrayOfInt5[i10] = (10 + arrayOfInt5[i10]);
                                MainActivity.this.getCarRect2(i10, MainActivity.this.x_value[i10], MainActivity.this.y_value[i10], MainActivity.this.direct[i10], MainActivity.this.angle[i10], i12, i11, i13);
                                return;
                            }
                        } else if (MainActivity.this.direct[i10] == 2) {
                            if ((arrayOfInt[((i14 + 1) % i15)][0] < MainActivity.this.x_value[i10]) && (MainActivity.this.x_value[i10] <= arrayOfInt[i14][0])) {
                                int[] arrayOfInt4 = MainActivity.this.x_value;
                                arrayOfInt4[i10] = (-10 + arrayOfInt4[i10]);
                                MainActivity.this.getCarRect2(i10, MainActivity.this.x_value[i10], MainActivity.this.y_value[i10], MainActivity.this.direct[i10], MainActivity.this.angle[i10], i12, i11, i13);
                                return;
                            }
                        } else if (MainActivity.this.direct[i10] == 1) {
                            if ((arrayOfInt[i14][1] <= MainActivity.this.y_value[i10]) && (MainActivity.this.y_value[i10] < arrayOfInt[((i14 + 1) % i15)][1])) {
                                int[] arrayOfInt3 = MainActivity.this.y_value;
                                arrayOfInt3[i10] = (10 + arrayOfInt3[i10]);
                                MainActivity.this.getCarRect2(i10, MainActivity.this.x_value[i10], MainActivity.this.y_value[i10], MainActivity.this.direct[i10], MainActivity.this.angle[i10], i12, i11, i13);
                                return;
                            }
                        } else if ((MainActivity.this.direct[i10] == 0) && (arrayOfInt[((i14 + 1) % i15)][1] < MainActivity.this.y_value[i10]) && (MainActivity.this.y_value[i10] <= arrayOfInt[i14][1])) {
                            int[] arrayOfInt2 = MainActivity.this.y_value;
                            arrayOfInt2[i10] = (-10 + arrayOfInt2[i10]);
                            MainActivity.this.getCarRect2(i10, MainActivity.this.x_value[i10], MainActivity.this.y_value[i10], MainActivity.this.direct[i10], MainActivity.this.angle[i10], i12, i11, i13);
                            return;
                        }
                    int i16;
                    if (MainActivity.car_location[i11][i10] == i13) {
                        MainActivity.this.x_value[i10] = arrayOfInt[((i14 + 1) % i15)][0];
                        MainActivity.this.y_value[i10] = arrayOfInt[((i14 + 1) % i15)][1];
                        MainActivity.this.direct[i10] = arrayOfInt[((i14 + 1) % i15)][2];
                        MainActivity.this.angle[i10] = arrayOfInt[((i14 + 1) % i15)][3];
                        int[] arrayOfInt1 = MainActivity.this.path_Index[i11];
                        if (1 + MainActivity.this.path_Index[i11][i10] >= i15) {
                            i16 = 0;
                            arrayOfInt1[i10] = i16;
                        }
                    }
                    while (true) {
                        MainActivity.this.map.clear();
                        MainActivity.this.map.put("car_id", Integer.valueOf(i10));
                        MainActivity.this.map.put("pathid", Integer.valueOf(i12));
                        MainActivity.this.map.put("mode", Integer.valueOf(i11));
                        MainActivity.this.map.put("location", Integer.valueOf(i13));
                        Utils.sendMsgToHandle(MainActivity.this.mHandler, Utils.SHOW_SYNC_INFO[i10], MainActivity.this.map, 200);
                        return;
                        i16 = 1 + MainActivity.this.path_Index[i11][i10];
                        break;
                        MainActivity.car_location[i11][i10] = i13;
                    }
                    removeMessages(1020);
                    return;
                    removeMessages(1012);
                    return;
                    MainActivity.this.showCarPicture(localBundle.getInt("car_id"), localBundle.getInt("x_point"), localBundle.getInt("y_point"), localBundle.getInt("angle"), localBundle.getInt("direct"));
                    return;
                    removeMessages(1000);
                    MainActivity localMainActivity3 = MainActivity.this;
                    boolean bool2;
                    if (MainActivity.this.bRun)
                        bool2 = false;
                    while (true) {
                        localMainActivity3.bRun = bool2;
                        if (MainActivity.this.bRun)
                            break;
                        if (Interface.getSyncMode()) {
                            MainActivity.this.stopSync();
                            return;
                            bool2 = true;
                            continue;
                        }
                        MainActivity.this.stopCar();
                        return;
                    }
                    if (Interface.getSyncMode()) {
                        MainActivity.this.getShaPanSyncInfo();
                        return;
                    }
                    MainActivity.this.runAllCar();
                    return;
                    int i8 = localBundle.getInt("id");
                    int i9 = localBundle.getInt("angle_id");
                    MainActivity.this.showTrafficLightUI(i8, i9);
                    return;
                    int i5 = localBundle.getInt("car_id");
                    int i6 = localBundle.getInt("station_id");
                    String str1 = "本站台有" + localBundle.getInt("bus_num") + "辆公交车" + "\n\r" + (i5 + 1) + "号公交车上一共有" + localBundle.getInt("peoper_num") + "人" + ", 距本站台距离为" + localBundle.getInt("distance") + "\n\r" + "环境指标信息为：" + "光照传感器: " + localBundle.getInt("light") + ", PM2.5: " + localBundle.getInt("pm") + ", COM2: " + localBundle.getInt("com2") + ", 湿度 :" + localBundle.getInt("humiture") + ", 温度 :" + localBundle.getInt("temp");
                    MainActivity localMainActivity2 = MainActivity.this;
                    if (i6 < 400)
                        ;
                    for (int i7 = 0; ; i7 = 1) {
                        localMainActivity2.showToastText(i7, str1);
                        return;
                    }
                    int i4 = localBundle.getInt("light_mode");
                    MainActivity localMainActivity1 = MainActivity.this;
                    if (i4 == 1)
                        ;
                    for (boolean bool1 = false; ; bool1 = true) {
                        localMainActivity1.bStreetLightOn = bool1;
                        MainActivity.this.turnOnStreetLight(MainActivity.this.bStreetLightOn);
                        return;
                    }
                    removeMessages(1009);
                    int i3 = localBundle.getInt("car_id");
                    MainActivity.this.showLotEtcUI(i3, 100, 1, true);
                    Interface.setParkEtcGateStatus(3, 1);
                    MainActivity.this.addInfoToDateBase(i3, 0);
                    return;
                    removeMessages(1017);
                    int i2 = localBundle.getInt("car_id");
                    MainActivity.this.showLotEtcUI(i2, 101, 1, true);
                    Interface.setParkEtcGateStatus(2, 1);
                    MainActivity.this.addInfoToDateBase(i2, 1);
                    return;
                    removeMessages(1018);
                    int i1 = localBundle.getInt("car_id");
                    MainActivity.this.showLotEtcUI(i1, 101, 2, true);
                    Interface.setParkEtcGateStatus(1, 1);
                    MainActivity.this.park_inTime = Interface.getDateTime();
                    return;
                    removeMessages(1019);
                    int n = localBundle.getInt("car_id");
                    MainActivity.this.showLotEtcUI(n, 100, 2, true);
                    Interface.setParkEtcGateStatus(0, 1);
                    MainActivity.this.etc_inTime = Interface.getDateTime();
                    return;
                    removeMessages(1008);
                    return;
                    removeMessages(1013);
                    int m = localBundle.getInt("car_id");
                    MainActivity.this.showLotEtcUI(m, 100, 1, false);
                    Interface.setParkEtcGateStatus(3, 0);
                    Interface.setParkEtcAccountRecord(m, 0);
                    return;
                    removeMessages(1014);
                    int k = localBundle.getInt("car_id");
                    MainActivity.this.showLotEtcUI(k, 101, 1, false);
                    Interface.setParkEtcGateStatus(2, 0);
                    Interface.setParkEtcAccountRecord(k, 1);
                    return;
                    removeMessages(1015);
                    int j = localBundle.getInt("car_id");
                    MainActivity.this.showLotEtcUI(j, 101, 2, false);
                    Interface.setParkEtcGateStatus(1, 0);
                    return;
                    removeMessages(1016);
                    int i = localBundle.getInt("car_id");
                    MainActivity.this.showLotEtcUI(i, 100, 2, false);
                    Interface.setParkEtcGateStatus(0, 0);
                    return;
                    label4036:
                    if ((i36 != 0) && (i38 != 0))
                        if (i37 != 0)
                            break label4077;
                }
                label4071:
                label4077:
                for (i40 = i36 - 1; ; i40 = i36 + 3) {
                    if (i37 != 0)
                        break label4086;
                    i41 = i39 - 1;
                    break label730;
                    i25++;
                    break;
                }
                label4086:
                int i41 = i36 + 3;
                break label730;
                if (i42 == 1) {
                    i43 = 15;
                    break label768;
                }
                i43 = 0;
                break label768;
                i43 = 0;
                break label768;
                label4120:
                if (i42 == 3) {
                    i44 = 10;
                    continue;
                }
                i44 = 0;
                continue;
                label4139:
                i44 = 0;
            }
        }
    };
    private LayoutInflater mInflater;
    private ListView mListView = null;
    private ListView mListView2 = null;
    private int mPosition;
    private HashMap<String, Integer> map;
    private Handler messageHandler;
    private NanoHTTPD nanoHttpd = null;
    int num = 0;
    private boolean onClick = false;
    private String park_inTime = "";
    private int[][] path_Index;
    private String remote_ip = "192.168.11.20";
    private int remote_port = 8890;
    private String rp_String;
    private SharedPreferences sharedPrefs;
    private String show_line = "";
    private StreetLight streetLight;
    int[] string2_id;
    int[] string2_sum_id;
    int[] string_id;
    int[] string_sum_id;
    private TextView tvx_rp;
    private TextView tx_ip;
    private TextView txx_park;
    private myImageView view_0;
    private myImageView view_1;
    private myImageView view_2;
    private myImageView view_3;
    private myImageView view_4;
    private myImageView view_5;
    private myImageView view_6;
    private View[] view_car = new View[6];
    private View view_main = null;
    private View[] view_set;
    private WifiManager wifiManager;
    private int[] x_value;
    private int[] y_value;

    static {
        int[] arrayOfInt1 = {2, 6};
        car_location = (int[][]) Array.newInstance(Integer.TYPE, arrayOfInt1);
        int[] arrayOfInt2 = {2, 2};
        mBackModeStatus = (int[][]) Array.newInstance(Integer.TYPE, arrayOfInt2);
        TOP = new String[]{"/system/bin/top", "-n", "1"};
        old_angle = 0;
        old_x = 0;
        old_y = 0;
    }

    public MainActivity() {
        int[] arrayOfInt = {2, 6};
        this.path_Index = ((int[][]) Array.newInstance(Integer.TYPE, arrayOfInt));
        this.x_value = new int[6];
        this.y_value = new int[6];
        this.direct = new int[6];
        this.angle = new int[6];
        this.map = new HashMap();
        this.bClean = false;
        this.string_id = new int[]{2131034128, 2131034129, 2131034130, 2131034131, 2131034132, 2131034133, 2131034134};
        this.string_sum_id = new int[]{2131034135, 2131034136, 2131034137, 2131034138, 2131034139, 2131034140, 2131034141};
        this.drawable_id = new int[]{2130837565, 2130837533, 2130837543, 2130837546, 2130837535, 2130837549, 2130837560};
        this.string2_id = new int[]{2131034143, 2131034144, 2131034145};
        this.string2_sum_id = new int[]{2131034146, 2131034147, 2131034148};
        this.drawable2_id = new int[]{2130837566, 2130837551, 2130837534};
        this.view_set = new View[9];
        this.StreetLightmode = false;
        this.bStreetLightOn = false;
        this.mPosition = -1;
        this.rp_String = "";
        this.mDialog = null;
        this.messageHandler = new Handler() {
            public void handleMessage(Message paramMessage) {
                switch (paramMessage.getData().getInt("state")) {
                    default:
                        return;
                    case 1031:
                        removeMessages(1031);
                        MainActivity.this.mDialog.dismiss();
                        String str3 = paramMessage.getData().getString("data");
                        MainActivity.this.showReportString(-1, str3);
                        return;
                    case 1029:
                        removeMessages(1029);
                        MainActivity.this.mDialog.dismiss();
                        String str2 = paramMessage.getData().getString("data");
                        MainActivity.this.showReportString(0, str2);
                        return;
                    case 1030:
                }
                removeMessages(1030);
                MainActivity.this.mDialog.dismiss();
                String str1 = paramMessage.getData().getString("data");
                MainActivity.this.showReportString(1, str1);
            }
        };
    }

    private void addInfoToDateBase(int paramInt1, int paramInt2) {
        String str = Interface.getDateTime();
        if (paramInt2 == 1) {
            Interface.setJsonString(paramInt1, paramInt2, this.park_inTime, str);
            return;
        }
        Interface.setJsonString(paramInt1, paramInt2, this.etc_inTime, str);
    }

    private void addListItem(int paramInt) {
        int j;
        if ((paramInt == 0) && (list.size() == 0)) {
            j = 0;
            if (j < 7)
                ;
        }
        while (true) {
            setListApater(paramInt);
            return;
            list.add(new VideoFile(getString(this.string_id[j]), getString(this.string_sum_id[j]), this.drawable_id[j]));
            j++;
            break;
            if ((paramInt != 1) || (list2.size() != 0))
                continue;
            for (int i = 0; i < 3; i++)
                list2.add(new VideoFile(getString(this.string2_id[i]), getString(this.string2_sum_id[i]), this.drawable2_id[i]));
        }
    }

    private void addViewOnLayout() {
        int i = this.sharedPrefs.getInt("car_num", 1);
        this.car_num = i;
        this.car.initXYValue();
        int j = 0;
        if (j >= 6)
            return;
        if ((i <= j) && (j < 4))
            ;
        while (true) {
            j++;
            break;
            this.absoluteLayout_layout.addView(this.view_car[j]);
            showCarPicture(j, Utils.CAR_DEFAULT[j][0], Utils.CAR_DEFAULT[j][1], Utils.CAR_DEFAULT[j][3], Utils.CAR_DEFAULT[j][2]);
        }
    }

    private void beginRun() {
        if (this.bRun) {
            this.iv_button_play.setBackgroundResource(2130837540);
            return;
        }
        this.iv_button_play.setBackgroundResource(2130837542);
    }

    private void cleanALL() {
        destroy();
        cleanViewOnLayout();
        initView();
        this.bRun = false;
        this.bClean = true;
        ((InputMethodManager) getSystemService("input_method")).toggleSoftInput(0, 2);
        this.iv_button_play.setBackgroundResource(2130837540);
    }

    private void cleanAllAndGetShaPanSyncInfo() {
        if (this.editor != null) {
            this.editor.putInt("car_num", 1);
            this.editor.commit();
        }
        Interface.setSyncMode(true);
        cleanALL();
    }

    private void cleanViewOnLayout() {
        this.sharedPrefs.getInt("car_num", 1);
        for (int i = 0; ; i++) {
            if (i >= 6)
                return;
            this.absoluteLayout_layout.removeView(this.view_car[i]);
            this.absoluteLayout_layout.removeView(this.view_main);
        }
    }

    private void destroy() {
        if (this.mHandler != null) {
            this.mHandler.removeMessages(1001);
            this.mHandler.removeMessages(1002);
            this.mHandler.removeMessages(1003);
            this.mHandler.removeMessages(1004);
            this.mHandler.removeMessages(1005);
            this.mHandler.removeMessages(1006);
        }
        this.car.stop();
    }

    private void drawable_Draw(int paramInt1, int paramInt2, ImageView paramImageView1, ImageView paramImageView2) {
        if (paramInt2 == 0) {
            paramImageView1.setImageDrawable(getResources().getDrawable(2130837550));
            paramImageView2.setImageDrawable(getResources().getDrawable(2130837567));
        }
        do {
            return;
            if (1 == paramInt2) {
                paramImageView1.setImageDrawable(getResources().getDrawable(2130837536));
                paramImageView2.setImageDrawable(getResources().getDrawable(2130837550));
                return;
            }
            if (2 != paramInt2)
                continue;
            paramImageView1.setImageDrawable(getResources().getDrawable(2130837567));
            paramImageView2.setImageDrawable(getResources().getDrawable(2130837550));
            return;
        }
        while (3 != paramInt2);
        paramImageView1.setImageDrawable(getResources().getDrawable(2130837550));
        paramImageView2.setImageDrawable(getResources().getDrawable(2130837536));
    }

    private static long getAvailableMemory(Context paramContext) {
        return getAvailableMemory(paramContext) / 1024L;
    }

    private void getShaPanSyncInfo() {
        String str = "http://" + this.remote_ip + "/type/jason/action/";
        new ProcessThread(this.mHandler, str).start();
    }

    private void getWindowWH() {
        WindowManager localWindowManager = (WindowManager) getSystemService("window");
        int i = localWindowManager.getDefaultDisplay().getWidth();
        int j = localWindowManager.getDefaultDisplay().getHeight();
        Log.d("MainActivity", "w = " + i + ", h = " + j);
    }

    private void initView() {
        this.sharedPrefs = getSharedPreferences("car_traffic_info", 0);
        this.editor = this.sharedPrefs.edit();
        this.iv_light_2 = ((ImageView) findViewById(2131296260));
        this.iv_light_3 = ((ImageView) findViewById(2131296261));
        this.iv_light_4 = ((ImageView) findViewById(2131296262));
        this.iv_light_5 = ((ImageView) findViewById(2131296263));
        this.iv_light_6 = ((ImageView) findViewById(2131296265));
        this.iv_light_7 = ((ImageView) findViewById(2131296264));
        this.iv_light_8 = ((ImageView) findViewById(2131296266));
        this.iv_light_9 = ((ImageView) findViewById(2131296267));
        this.iv_light_10 = ((ImageView) findViewById(2131296268));
        this.iv_light_11 = ((ImageView) findViewById(2131296269));
        int i = 0;
        if (i >= 5) {
            this.absoluteLayout_layout = ((AbsoluteLayout) findViewById(2131296256));
            this.absoluteLayout_layout.setBackgroundResource(2130837516);
            this.mPosition = -1;
            this.view_0 = new myImageView(this, 0);
            this.view_1 = new myImageView(this, 1);
            this.view_2 = new myImageView(this, 2);
            this.view_3 = new myImageView(this, 3);
            this.view_4 = new myImageView(this, 4);
            this.view_5 = new myImageView(this, 5);
            this.view_car[0] = this.view_0;
            this.view_car[1] = this.view_1;
            this.view_car[2] = this.view_2;
            this.view_car[3] = this.view_3;
            this.view_car[4] = this.view_4;
            this.view_car[5] = this.view_5;
            addViewOnLayout();
            this.iv_button_play = ((ImageView) findViewById(2131296257));
            this.iv_button_play.setBackgroundResource(2130837540);
            this.iv_button_play.setOnClickListener(new OnClickListener() {
                public void onClick(View paramView) {
                    MainActivity.this.beginRun();
                    MainActivity.this.sendMsg(1000, 100);
                }
            });
            this.iv_button_set = ((ImageView) findViewById(2131296258));
            this.iv_button_set.setBackgroundResource(2130837541);
            this.iv_button_set.setOnClickListener(new OnClickListener() {
                public void onClick(View paramView) {
                    MainActivity.this.openSettingsActivity();
                }
            });
            this.iv_button_light = ((ImageView) findViewById(2131296259));
            this.iv_button_light.setBackgroundResource(2130837538);
            this.iv_button_light.setOnClickListener(new OnClickListener() {
                public void onClick(View paramView) {
                    MainActivity.this.turnOnStreetLight(MainActivity.this.bTurnOn);
                }
            });
            this.iv_button_s1 = ((ImageView) findViewById(2131296270));
            this.iv_button_s1.setOnClickListener(new OnClickListener() {
                public void onClick(View paramView) {
                    MainActivity.this.showBusStationInfo(1);
                }
            });
            this.iv_button_s2 = ((ImageView) findViewById(2131296271));
            this.iv_button_s2.setOnClickListener(new OnClickListener() {
                public void onClick(View paramView) {
                    MainActivity.this.showBusStationInfo(2);
                }
            });
            this.iv_shu_01 = ((ImageView) findViewById(2131296272));
            this.iv_za_01 = ((ImageView) findViewById(2131296273));
            this.iv_za_02 = ((ImageView) findViewById(2131296274));
            this.iv_shu_02 = ((ImageView) findViewById(2131296275));
            this.iv_shu_01.setImageResource(2130837554);
            this.iv_za_01.setImageResource(2130837568);
            this.iv_za_02.setImageResource(2130837568);
            this.iv_shu_02.setImageResource(2130837554);
            this.view_main = this.mInflater.inflate(2130903049, null);
            this.mListView = ((ListView) this.view_main.findViewById(2131296337));
            this.mListView2 = ((ListView) this.view_main.findViewById(2131296339));
            addListItem(0);
            addListItem(1);
            this.view_main.setX(1150.0F);
            this.view_main.setY(60.0F);
            this.absoluteLayout_layout.addView(this.view_main);
            return;
        }
        if (Interface.getSyncMode())
            showTrafficLightUI(i, 1);
        while (true) {
            this.mBackStatus[i] = "Close";
            i++;
            break;
            showTrafficLightUI(i, Utils.TRAFFIC_DEFAULT_MODE[i][0]);
        }
    }

    private String intToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "." + (0xFF & paramInt >> 24);
    }

    private void onPlayItemById(int paramInt) {
        if ((list == null) || (paramInt >= list.size()))
            return;
        String str = ((VideoFile) list.get(paramInt)).getText();
        Log.d("--main--", "---" + str + ", position = " + paramInt);
        switch (paramInt) {
            default:
                this.mPosition = paramInt;
                this.absoluteLayout_layout.removeView(this.view_main);
                return;
            case 0:
                if (this.view_set[0] == null)
                    this.view_set[0] = this.mInflater.inflate(2130903045, null);
                EditText localEditText5 = (EditText) this.view_set[0].findViewById(2131296287);
                this.tvx_rp = ((TextView) this.view_set[0].findViewById(2131296296));
                Button localButton21 = (Button) this.view_set[0].findViewById(2131296288);
                12 local12 = new OnClickListener(localEditText5) {
                public void onClick(View paramView) {
                    if (this.val$txx.getText().toString().isEmpty()) {
                        MainActivity.this.showToast("输入不能为空！");
                        return;
                    }
                    int i = Integer.parseInt(this.val$txx.getText().toString());
                    MainActivity.this.app.getFactory().setFactoryCost(0, "Count", i);
                    MainActivity.this.showToast("设置ETC费率成功");
                }
            };
                localButton21.setOnClickListener(local12);
                Button localButton22 = (Button) this.view_set[0].findViewById(2131296293);
                13 local13 = new OnClickListener() {
                public void onClick(View paramView) {
                    MainActivity.this.showProgressDialog();
                    new ProcessThread2(MainActivity.this, MainActivity.this.messageHandler, 0, MainActivity.this.rp_String).start();
                }
            };
                localButton22.setOnClickListener(local13);
                Button localButton23 = (Button) this.view_set[0].findViewById(2131296294);
                14 local14 = new OnClickListener() {
                public void onClick(View paramView) {
                    AbsoluteLayout localAbsoluteLayout;
                    if (MainActivity.this.view_set[0] != null) {
                        MainActivity.this.view_main.setX(1150.0F);
                        MainActivity.this.view_main.setY(60.0F);
                        localAbsoluteLayout = MainActivity.this.absoluteLayout_layout;
                        if (!MainActivity.this.bStreetLightOn)
                            break label107;
                    }
                    label107:
                    for (int i = 2130837517; ; i = 2130837516) {
                        localAbsoluteLayout.setBackgroundResource(i);
                        MainActivity.this.absoluteLayout_layout.addView(MainActivity.this.view_main);
                        MainActivity.this.absoluteLayout_layout.removeView(MainActivity.this.view_set[0]);
                        MainActivity.this.mPosition = -1;
                        return;
                    }
                }
            };
                localButton23.setOnClickListener(local14);
                Button localButton24 = (Button) this.view_set[0].findViewById(2131296289);
                15 local15 = new OnClickListener() {
                public void onClick(View paramView) {
                    ((InputMethodManager) MainActivity.this.getSystemService("input_method")).toggleSoftInput(0, 2);
                }
            };
                localButton24.setOnClickListener(local15);
                this.view_set[0].setX(1125.0F);
                AbsoluteLayout localAbsoluteLayout7 = this.absoluteLayout_layout;
                if (this.bStreetLightOn)
                    ;
                for (int i2 = 2130837520; ; i2 = 2130837509) {
                    localAbsoluteLayout7.setBackgroundResource(i2);
                    this.absoluteLayout_layout.addView(this.view_set[0]);
                    break;
                }
            case 1:
                if (this.view_set[1] == null)
                    this.view_set[1] = this.mInflater.inflate(2130903047, null);
                EditText localEditText4 = (EditText) this.view_set[1].findViewById(2131296287);
                this.txx_park = ((TextView) this.view_set[1].findViewById(2131296295));
                Button localButton17 = (Button) this.view_set[1].findViewById(2131296288);
                16 local16 = new OnClickListener(localEditText4) {
                public void onClick(View paramView) {
                    if (this.val$txx.getText().toString().isEmpty()) {
                        MainActivity.this.showToast("输入不能为空！");
                        return;
                    }
                    int i = Integer.parseInt(this.val$txx.getText().toString());
                    MainActivity.this.app.getFactory().setFactoryCost(1, "Count", i);
                    MainActivity.this.showToast("设置停车场费率成功");
                }
            };
                localButton17.setOnClickListener(local16);
                Button localButton18 = (Button) this.view_set[1].findViewById(2131296293);
                17 local17 = new OnClickListener() {
                public void onClick(View paramView) {
                    MainActivity.this.showProgressDialog();
                    new ProcessThread2(MainActivity.this, MainActivity.this.messageHandler, 1, MainActivity.this.rp_String).start();
                }
            };
                localButton18.setOnClickListener(local17);
                Button localButton19 = (Button) this.view_set[1].findViewById(2131296294);
                18 local18 = new OnClickListener() {
                public void onClick(View paramView) {
                    AbsoluteLayout localAbsoluteLayout;
                    if (MainActivity.this.view_set[1] != null) {
                        MainActivity.this.view_main.setX(1150.0F);
                        MainActivity.this.view_main.setY(60.0F);
                        localAbsoluteLayout = MainActivity.this.absoluteLayout_layout;
                        if (!MainActivity.this.bStreetLightOn)
                            break label107;
                    }
                    label107:
                    for (int i = 2130837517; ; i = 2130837516) {
                        localAbsoluteLayout.setBackgroundResource(i);
                        MainActivity.this.absoluteLayout_layout.addView(MainActivity.this.view_main);
                        MainActivity.this.absoluteLayout_layout.removeView(MainActivity.this.view_set[1]);
                        MainActivity.this.mPosition = -1;
                        return;
                    }
                }
            };
                localButton19.setOnClickListener(local18);
                Button localButton20 = (Button) this.view_set[1].findViewById(2131296289);
                19 local19 = new OnClickListener() {
                public void onClick(View paramView) {
                    ((InputMethodManager) MainActivity.this.getSystemService("input_method")).toggleSoftInput(0, 2);
                }
            };
                localButton20.setOnClickListener(local19);
                this.view_set[1].setX(1125.0F);
                AbsoluteLayout localAbsoluteLayout6 = this.absoluteLayout_layout;
                if (this.bStreetLightOn)
                    ;
                for (int i1 = 2130837521; ; i1 = 2130837510) {
                    localAbsoluteLayout6.setBackgroundResource(i1);
                    this.absoluteLayout_layout.addView(this.view_set[1]);
                    break;
                }
            case 2:
                if (this.view_set[2] == null)
                    this.view_set[2] = this.mInflater.inflate(2130903051, null);
                TextView localTextView2 = (TextView) this.view_set[2].findViewById(2131296286);
                Button localButton15 = (Button) this.view_set[2].findViewById(2131296288);
                20 local20 = new OnClickListener(localTextView2) {
                public void onClick(View paramView) {
                    HashMap localHashMap1 = MainActivity.this.app.getBusStation().getBusSationInfo(0, 0);
                    String str1 = "    1号站台有" + localHashMap1.get("bus_num") + "辆公交车" + "\n\r" + (1 + ((Integer) localHashMap1.get("car_id")).intValue()) + "号公交车上一共有" + localHashMap1.get("peoper_num") + "人" + ", 距本站台距离为" + localHashMap1.get("distance") + "\n\r" + "环境指标信息为：" + "光照传感器: " + localHashMap1.get("light") + ", PM2.5: " + localHashMap1.get("pm") + ", COM2: " + localHashMap1.get("com2") + ", 湿度 :" + localHashMap1.get("humiture") + ", 温度 :" + localHashMap1.get("temp");
                    HashMap localHashMap2 = MainActivity.this.app.getBusStation().getBusSationInfo(1, 1);
                    String str2 = "    2号站台有" + localHashMap2.get("bus_num") + "辆公交车" + "\n\r" + (1 + ((Integer) localHashMap2.get("car_id")).intValue()) + "号公交车上一共有" + localHashMap2.get("peoper_num") + "人" + ", 距本站台距离为" + localHashMap2.get("distance") + "\n\r" + "环境指标信息为：" + "光照传感器: " + localHashMap2.get("light") + ", PM2.5: " + localHashMap2.get("pm") + ", COM2: " + localHashMap2.get("com2") + ", 湿度 :" + localHashMap2.get("humiture") + ", 温度 :" + localHashMap2.get("temp");
                    this.val$tx2.setText(str1 + "\n\r\n\r\n\r" + str2);
                }
            };
                localButton15.setOnClickListener(local20);
                Button localButton16 = (Button) this.view_set[2].findViewById(2131296294);
                21 local21 = new OnClickListener() {
                public void onClick(View paramView) {
                    AbsoluteLayout localAbsoluteLayout;
                    if (MainActivity.this.view_set[2] != null) {
                        MainActivity.this.view_main.setX(1150.0F);
                        MainActivity.this.view_main.setY(60.0F);
                        localAbsoluteLayout = MainActivity.this.absoluteLayout_layout;
                        if (!MainActivity.this.bStreetLightOn)
                            break label107;
                    }
                    label107:
                    for (int i = 2130837517; ; i = 2130837516) {
                        localAbsoluteLayout.setBackgroundResource(i);
                        MainActivity.this.absoluteLayout_layout.addView(MainActivity.this.view_main);
                        MainActivity.this.absoluteLayout_layout.removeView(MainActivity.this.view_set[2]);
                        MainActivity.this.mPosition = -1;
                        return;
                    }
                }
            };
                localButton16.setOnClickListener(local21);
                this.view_set[2].setX(1125.0F);
                AbsoluteLayout localAbsoluteLayout5 = this.absoluteLayout_layout;
                if (this.bStreetLightOn)
                    ;
                for (int n = 2130837525; ; n = 2130837514) {
                    localAbsoluteLayout5.setBackgroundResource(n);
                    this.absoluteLayout_layout.addView(this.view_set[2]);
                    break;
                }
            case 3:
                if (this.view_set[3] == null)
                    this.view_set[3] = this.mInflater.inflate(2130903042, null);
                EditText localEditText3 = (EditText) this.view_set[3].findViewById(2131296287);
                Spinner localSpinner7 = (Spinner) this.view_set[3].findViewById(2131296292);
                Spinner localSpinner8 = (Spinner) this.view_set[3].findViewById(2131296291);
                Button localButton11 = (Button) this.view_set[3].findViewById(2131296288);
                22 local22 = new OnClickListener(localEditText3) {
                public void onClick(View paramView) {
                    if (this.val$txx3.getText().toString().isEmpty())
                        MainActivity.this.showToast("输入不能为空！");
                    int i;
                    do {
                        return;
                        i = Integer.parseInt(this.val$txx3.getText().toString());
                        if ((i >= 1) && (i <= 4))
                            continue;
                        MainActivity.this.showToast("输入范围为1~4!");
                        return;
                    }
                    while (MainActivity.this.car_num == i);
                    MainActivity.this.editor.putInt("car_num", i);
                    MainActivity.this.editor.commit();
                    MainActivity.this.absoluteLayout_layout.removeView(MainActivity.this.view_set[3]);
                    MainActivity.this.cleanALL();
                    MainActivity.this.showToast("设置小车数量为" + i);
                }
            };
                localButton11.setOnClickListener(local22);
                Button localButton12 = (Button) this.view_set[3].findViewById(2131296293);
                23 local23 = new OnClickListener(localSpinner8, localSpinner7) {
                public void onClick(View paramView) {
                    int i = this.val$sp_id.getSelectedItemPosition();
                    int j = this.val$sp.getSelectedItemPosition();
                    Log.d("--car path--", "---- car id = " + i + ", path = " + j);
                    MainActivity.this.car.setCarPathId(i, j);
                    MainActivity.this.car.cleanPaintPoint2(i);
                    MainActivity.this.showToast("设置小车" + (i + 1) + "号路径为线路" + (j + 1) + "成功");
                }
            };
                localButton12.setOnClickListener(local23);
                Button localButton13 = (Button) this.view_set[3].findViewById(2131296294);
                24 local24 = new OnClickListener() {
                public void onClick(View paramView) {
                    AbsoluteLayout localAbsoluteLayout;
                    if (MainActivity.this.view_set[3] != null) {
                        MainActivity.this.view_main.setX(1150.0F);
                        MainActivity.this.view_main.setY(60.0F);
                        localAbsoluteLayout = MainActivity.this.absoluteLayout_layout;
                        if (!MainActivity.this.bStreetLightOn)
                            break label107;
                    }
                    label107:
                    for (int i = 2130837517; ; i = 2130837516) {
                        localAbsoluteLayout.setBackgroundResource(i);
                        MainActivity.this.absoluteLayout_layout.addView(MainActivity.this.view_main);
                        MainActivity.this.absoluteLayout_layout.removeView(MainActivity.this.view_set[3]);
                        MainActivity.this.mPosition = -1;
                        return;
                    }
                }
            };
                localButton13.setOnClickListener(local24);
                Button localButton14 = (Button) this.view_set[3].findViewById(2131296289);
                25 local25 = new OnClickListener() {
                public void onClick(View paramView) {
                    ((InputMethodManager) MainActivity.this.getSystemService("input_method")).toggleSoftInput(0, 2);
                }
            };
                localButton14.setOnClickListener(local25);
                this.view_set[3].setX(1125.0F);
                AbsoluteLayout localAbsoluteLayout4 = this.absoluteLayout_layout;
                if (this.bStreetLightOn)
                    ;
                for (int m = 2130837518; ; m = 2130837506) {
                    localAbsoluteLayout4.setBackgroundResource(m);
                    this.absoluteLayout_layout.addView(this.view_set[3]);
                    break;
                }
            case 4:
                if (this.view_set[4] == null)
                    this.view_set[4] = this.mInflater.inflate(2130903044, null);
                TextView localTextView1 = (TextView) this.view_set[4].findViewById(2131296295);
                Button localButton9 = (Button) this.view_set[4].findViewById(2131296293);
                26 local26 = new OnClickListener(localTextView1) {
                public void onClick(View paramView) {
                    String str = Interface.getSensorValue(-1);
                    this.val$tv4.setText(str);
                }
            };
                localButton9.setOnClickListener(local26);
                Button localButton10 = (Button) this.view_set[4].findViewById(2131296294);
                27 local27 = new OnClickListener() {
                public void onClick(View paramView) {
                    AbsoluteLayout localAbsoluteLayout;
                    if (MainActivity.this.view_set[4] != null) {
                        MainActivity.this.view_main.setX(1150.0F);
                        MainActivity.this.view_main.setY(60.0F);
                        localAbsoluteLayout = MainActivity.this.absoluteLayout_layout;
                        if (!MainActivity.this.bStreetLightOn)
                            break label107;
                    }
                    label107:
                    for (int i = 2130837517; ; i = 2130837516) {
                        localAbsoluteLayout.setBackgroundResource(i);
                        MainActivity.this.absoluteLayout_layout.addView(MainActivity.this.view_main);
                        MainActivity.this.absoluteLayout_layout.removeView(MainActivity.this.view_set[4]);
                        MainActivity.this.mPosition = -1;
                        return;
                    }
                }
            };
                localButton10.setOnClickListener(local27);
                this.view_set[4].setX(1125.0F);
                AbsoluteLayout localAbsoluteLayout3 = this.absoluteLayout_layout;
                if (this.bStreetLightOn)
                    ;
                for (int k = 2130837522; ; k = 2130837511) {
                    localAbsoluteLayout3.setBackgroundResource(k);
                    this.absoluteLayout_layout.addView(this.view_set[4]);
                    break;
                }
            case 5:
                if (this.view_set[5] == null)
                    this.view_set[5] = this.mInflater.inflate(2130903052, null);
                Spinner localSpinner1 = (Spinner) this.view_set[5].findViewById(2131296340);
                Spinner localSpinner2 = (Spinner) this.view_set[5].findViewById(2131296291);
                Spinner localSpinner3 = (Spinner) this.view_set[5].findViewById(2131296319);
                Spinner localSpinner4 = (Spinner) this.view_set[5].findViewById(2131296323);
                Spinner localSpinner5 = (Spinner) this.view_set[5].findViewById(2131296324);
                Spinner localSpinner6 = (Spinner) this.view_set[5].findViewById(2131296327);
                Button localButton6 = (Button) this.view_set[5].findViewById(2131296288);
                28 local28 = new OnClickListener(localSpinner1, localSpinner2, localSpinner3) {
                public void onClick(View paramView) {
                    int i = this.val$sp1.getSelectedItemPosition();
                    int j = this.val$sp2.getSelectedItemPosition();
                    int k = this.val$sp3.getSelectedItemPosition();
                    String str;
                    if (k == 0)
                        str = "red";
                    while (true) {
                        Log.d("--traffic--", "--traffic id = " + i + ", direct = " + j + ", status = " + str);
                        Interface.setTrafficLightState(i, j, str);
                        MainActivity.this.showToast("设置红绿灯" + (i + 1) + "状态成功");
                        return;
                        if (k == 1) {
                            str = "green";
                            continue;
                        }
                        str = "yellow";
                    }
                }
            };
                localButton6.setOnClickListener(local28);
                Button localButton7 = (Button) this.view_set[5].findViewById(2131296293);
                29 local29 = new OnClickListener(localSpinner4, localSpinner5, localSpinner6) {
                public void onClick(View paramView) {
                    int i = this.val$sp4.getSelectedItemPosition();
                    int j = this.val$sp5.getSelectedItemPosition();
                    int k = this.val$sp6.getSelectedItemPosition();
                    String str;
                    int m;
                    if (j == 0) {
                        str = "Red";
                        m = 5;
                        switch (k) {
                            default:
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                        }
                    }
                    while (true) {
                        Log.d("--traffic--", "--traffic id = " + i + ", status = " + str + ", time = " + m);
                        Interface.setTrafficLightTime(i, str, m);
                        MainActivity.this.showToast("设置" + (i + 1) + "号红绿灯" + str + "时间为" + m + "秒");
                        return;
                        if (j == 1) {
                            str = "Green";
                            break;
                        }
                        str = "Yellow";
                        break;
                        m = 2;
                        continue;
                        m = 3;
                        continue;
                        m = 4;
                        continue;
                        m = 5;
                        continue;
                        m = 10;
                        continue;
                        m = 15;
                        continue;
                        m = 30;
                    }
                }
            };
                localButton7.setOnClickListener(local29);
                Button localButton8 = (Button) this.view_set[5].findViewById(2131296294);
                30 local30 = new OnClickListener() {
                public void onClick(View paramView) {
                    AbsoluteLayout localAbsoluteLayout;
                    if (MainActivity.this.view_set[5] != null) {
                        MainActivity.this.view_main.setX(1150.0F);
                        MainActivity.this.view_main.setY(60.0F);
                        localAbsoluteLayout = MainActivity.this.absoluteLayout_layout;
                        if (!MainActivity.this.bStreetLightOn)
                            break label107;
                    }
                    label107:
                    for (int i = 2130837517; ; i = 2130837516) {
                        localAbsoluteLayout.setBackgroundResource(i);
                        MainActivity.this.absoluteLayout_layout.addView(MainActivity.this.view_main);
                        MainActivity.this.absoluteLayout_layout.removeView(MainActivity.this.view_set[5]);
                        MainActivity.this.mPosition = -1;
                        return;
                    }
                }
            };
                localButton8.setOnClickListener(local30);
                this.view_set[5].setX(1125.0F);
                AbsoluteLayout localAbsoluteLayout2 = this.absoluteLayout_layout;
                if (this.bStreetLightOn)
                    ;
                for (int j = 2130837526; ; j = 2130837515) {
                    localAbsoluteLayout2.setBackgroundResource(j);
                    this.absoluteLayout_layout.addView(this.view_set[5]);
                    break;
                }
            case 6:
        }
        if (this.view_set[6] == null)
            this.view_set[6] = this.mInflater.inflate(2130903048, null);
        EditText localEditText1 = (EditText) this.view_set[6].findViewById(2131296287);
        EditText localEditText2 = (EditText) this.view_set[6].findViewById(2131296298);
        Button localButton1 = (Button) this.view_set[6].findViewById(2131296334);
        31 local31 = new OnClickListener(localEditText1, localEditText2) {
            public void onClick(View paramView) {
                if ((this.val$txx6.getText().toString().isEmpty()) || (this.val$txx62.getText().toString().isEmpty())) {
                    MainActivity.this.showToast("输入不能为空！");
                    return;
                }
                int i = Integer.parseInt(this.val$txx6.getText().toString());
                int j = Integer.parseInt(this.val$txx62.getText().toString());
                if ((i < 0) || (i > 4095) || (j < 0) || (j > 4095) || (i > j)) {
                    MainActivity.this.showToast("数字范围为0~4095且左边值必须小于右边值！");
                    return;
                }
                MainActivity.this.app.getEnvSensor().setLihgtSensorThreshold(i, j);
                MainActivity.this.showToast("设置光照阀值成功");
            }
        };
        localButton1.setOnClickListener(local31);
        Button localButton2 = (Button) this.view_set[6].findViewById(2131296289);
        32 local32 = new OnClickListener() {
            public void onClick(View paramView) {
                ((InputMethodManager) MainActivity.this.getSystemService("input_method")).toggleSoftInput(0, 2);
            }
        };
        localButton2.setOnClickListener(local32);
        Button localButton3 = (Button) this.view_set[6].findViewById(2131296288);
        Button localButton4 = (Button) this.view_set[6].findViewById(2131296293);
        33 local33 = new OnClickListener(localButton3, localButton4) {
            public void onClick(View paramView) {
                MainActivity localMainActivity1 = MainActivity.this;
                boolean bool1;
                int i;
                label47:
                String str1;
                label74:
                String str2;
                label101:
                Button localButton2;
                boolean bool3;
                if (MainActivity.this.StreetLightmode) {
                    bool1 = false;
                    localMainActivity1.StreetLightmode = bool1;
                    StreetLight localStreetLight = MainActivity.this.app.getStreetLight();
                    if (!MainActivity.this.StreetLightmode)
                        break label144;
                    i = 0;
                    localStreetLight.setStreetLightMode(i);
                    MainActivity localMainActivity2 = MainActivity.this;
                    if (!MainActivity.this.StreetLightmode)
                        break label150;
                    str1 = "设置路灯自动模式成功";
                    localMainActivity2.showToast(str1);
                    Button localButton1 = this.val$set6;
                    if (!MainActivity.this.StreetLightmode)
                        break label157;
                    str2 = "自动模式";
                    localButton1.setText(str2);
                    localButton2 = this.val$search6;
                    boolean bool2 = MainActivity.this.StreetLightmode;
                    bool3 = false;
                    if (!bool2)
                        break label164;
                }
                while (true) {
                    localButton2.setEnabled(bool3);
                    return;
                    bool1 = true;
                    break;
                    label144:
                    i = 1;
                    break label47;
                    label150:
                    str1 = "设置路灯手动模式成功";
                    break label74;
                    label157:
                    str2 = "手动模式";
                    break label101;
                    label164:
                    bool3 = true;
                }
            }
        };
        localButton3.setOnClickListener(local33);
        boolean bool;
        label1787:
        AbsoluteLayout localAbsoluteLayout1;
        if (this.StreetLightmode) {
            bool = false;
            localButton4.setEnabled(bool);
            34 local34 = new OnClickListener() {
                public void onClick(View paramView) {
                    MainActivity localMainActivity1 = MainActivity.this;
                    boolean bool;
                    MainActivity localMainActivity2;
                    if (MainActivity.this.bStreetLightOn) {
                        bool = false;
                        localMainActivity1.bStreetLightOn = bool;
                        MainActivity.this.app.getStreetLight().setStreetLightOnOff(MainActivity.this.bStreetLightOn);
                        localMainActivity2 = MainActivity.this;
                        if (!MainActivity.this.bStreetLightOn)
                            break label75;
                    }
                    label75:
                    for (String str = "黑夜模式"; ; str = "白天模式") {
                        localMainActivity2.showToast(str);
                        return;
                        bool = true;
                        break;
                    }
                }
            };
            localButton4.setOnClickListener(local34);
            Button localButton5 = (Button) this.view_set[6].findViewById(2131296294);
            35 local35 = new OnClickListener() {
                public void onClick(View paramView) {
                    AbsoluteLayout localAbsoluteLayout;
                    if (MainActivity.this.view_set[6] != null) {
                        MainActivity.this.view_main.setX(1150.0F);
                        MainActivity.this.view_main.setY(60.0F);
                        localAbsoluteLayout = MainActivity.this.absoluteLayout_layout;
                        if (!MainActivity.this.bStreetLightOn)
                            break label109;
                    }
                    label109:
                    for (int i = 2130837517; ; i = 2130837516) {
                        localAbsoluteLayout.setBackgroundResource(i);
                        MainActivity.this.absoluteLayout_layout.addView(MainActivity.this.view_main);
                        MainActivity.this.absoluteLayout_layout.removeView(MainActivity.this.view_set[6]);
                        MainActivity.this.mPosition = -1;
                        return;
                    }
                }
            };
            localButton5.setOnClickListener(local35);
            this.view_set[6].setX(1125.0F);
            localAbsoluteLayout1 = this.absoluteLayout_layout;
            if (!this.bStreetLightOn)
                break label1907;
        }
        label1907:
        for (int i = 2130837523; ; i = 2130837512) {
            localAbsoluteLayout1.setBackgroundResource(i);
            this.absoluteLayout_layout.addView(this.view_set[6]);
            break;
            bool = true;
            break label1787;
        }
    }

    private void onPlayItemById2(int paramInt) {
        if ((list2 == null) || (paramInt >= list2.size()))
            return;
        String str = ((VideoFile) list2.get(paramInt)).getText();
        Log.d("--main--", "2---" + str + ", position = " + paramInt);
        switch (paramInt) {
            default:
                return;
            case 0:
                if (this.view_set[7] == null)
                    this.view_set[7] = this.mInflater.inflate(2130903043, null);
                EditText localEditText = (EditText) this.view_set[7].findViewById(2131296287);
                ((Button) this.view_set[7].findViewById(2131296288)).setOnClickListener(new OnClickListener(localEditText) {
                    public void onClick(View paramView) {
                        String str = this.val$ed.getText().toString();
                        if ((str.isEmpty()) || (str.equals("0.0.0.0"))) {
                            MainActivity.this.showToast("无法连接网络！");
                            return;
                        }
                        MainActivity.this.remote_ip = str;
                        MainActivity.this.absoluteLayout_layout.removeView(MainActivity.this.view_set[7]);
                        MainActivity.this.cleanAllAndGetShaPanSyncInfo();
                    }
                });
                ((Button) this.view_set[7].findViewById(2131296293)).setOnClickListener(new OnClickListener() {
                    public void onClick(View paramView) {
                        ((InputMethodManager) MainActivity.this.getSystemService("input_method")).toggleSoftInput(0, 2);
                    }
                });
                ((Button) this.view_set[7].findViewById(2131296294)).setOnClickListener(new OnClickListener() {
                    public void onClick(View paramView) {
                        AbsoluteLayout localAbsoluteLayout;
                        if (MainActivity.this.view_set[7] != null) {
                            MainActivity.this.view_main.setX(1150.0F);
                            MainActivity.this.view_main.setY(60.0F);
                            localAbsoluteLayout = MainActivity.this.absoluteLayout_layout;
                            if (!MainActivity.this.bStreetLightOn)
                                break label109;
                        }
                        label109:
                        for (int i = 2130837517; ; i = 2130837516) {
                            localAbsoluteLayout.setBackgroundResource(i);
                            MainActivity.this.absoluteLayout_layout.addView(MainActivity.this.view_main);
                            MainActivity.this.absoluteLayout_layout.removeView(MainActivity.this.view_set[7]);
                            MainActivity.this.mPosition = -1;
                            return;
                        }
                    }
                });
                this.view_set[7].setX(1125.0F);
                AbsoluteLayout localAbsoluteLayout2 = this.absoluteLayout_layout;
                if (this.bStreetLightOn)
                    ;
                for (int j = 2130837519; ; j = 2130837508) {
                    localAbsoluteLayout2.setBackgroundResource(j);
                    this.absoluteLayout_layout.addView(this.view_set[7]);
                    this.absoluteLayout_layout.removeView(this.view_main);
                    this.mPosition = 7;
                    return;
                }
            case 1:
                if (this.view_set[8] == null)
                    this.view_set[8] = this.mInflater.inflate(2130903050, null);
                TextView localTextView = (TextView) this.view_set[8].findViewById(2131296286);
                if (!this.ip.isEmpty())
                    localTextView.setText(this.ip);
                ((Button) this.view_set[8].findViewById(2131296288)).setOnClickListener(new OnClickListener(localTextView) {
                    public void onClick(View paramView) {
                        MainActivity.this.startHTTP();
                        if (!MainActivity.this.ip.isEmpty())
                            this.val$tx8.setText(MainActivity.this.ip);
                    }
                });
                ((Button) this.view_set[8].findViewById(2131296294)).setOnClickListener(new OnClickListener() {
                    public void onClick(View paramView) {
                        AbsoluteLayout localAbsoluteLayout;
                        if (MainActivity.this.view_set[8] != null) {
                            MainActivity.this.view_main.setX(1150.0F);
                            MainActivity.this.view_main.setY(60.0F);
                            localAbsoluteLayout = MainActivity.this.absoluteLayout_layout;
                            if (!MainActivity.this.bStreetLightOn)
                                break label109;
                        }
                        label109:
                        for (int i = 2130837517; ; i = 2130837516) {
                            localAbsoluteLayout.setBackgroundResource(i);
                            MainActivity.this.absoluteLayout_layout.addView(MainActivity.this.view_main);
                            MainActivity.this.absoluteLayout_layout.removeView(MainActivity.this.view_set[8]);
                            MainActivity.this.mPosition = -1;
                            return;
                        }
                    }
                });
                this.view_set[8].setX(1125.0F);
                AbsoluteLayout localAbsoluteLayout1 = this.absoluteLayout_layout;
                if (this.bStreetLightOn)
                    ;
                for (int i = 2130837524; ; i = 2130837513) {
                    localAbsoluteLayout1.setBackgroundResource(i);
                    this.absoluteLayout_layout.addView(this.view_set[8]);
                    this.absoluteLayout_layout.removeView(this.view_main);
                    this.mPosition = 8;
                    return;
                }
            case 2:
        }
        openSettingsActivity();
    }

    private void openSettingsActivity() {
        Intent localIntent = new Intent();
        localIntent.setClassName("com.android.settings", "com.android.settings.Settings");
        startActivity(localIntent);
    }

    private void run() {
        runone();
    }

    private void runAllCar() {
        int i = this.sharedPrefs.getInt("car_num", 1);
        int j = 0;
        if (j >= 6)
            return;
        if ((i <= j) && (j < 4))
            ;
        while (true) {
            j++;
            break;
            runCar(j, this.view_car[j]);
        }
    }

    private void runCar(int paramInt, View paramView) {
        new Thread(new Runnable(paramInt) {
            public void run() {
                if (MainActivity.this.car != null)
                    MainActivity.this.car.run(this.val$carId);
            }
        }).start();
    }

    private void runone() {
        if (this.num < 1900) {
            showCarPicture(0, this.num, 200, 270, 3);
            showCarPicture(1, this.num, 300, 270, 3);
            showCarPicture(2, this.num, 400, 270, 3);
            showCarPicture(3, this.num, 500, 270, 3);
            showCarPicture(4, this.num, 600, 270, 3);
            showCarPicture(5, this.num, 700, 270, 3);
            sendMsg(1012, 100);
            this.num = (10 + this.num);
            return;
        }
        this.num = 0;
        sendMsg(1012, 100);
    }

    private void sendMsg(int paramInt1, int paramInt2) {
        if (this.mHandler != null) {
            Message localMessage = new Message();
            localMessage.what = paramInt1;
            this.mHandler.sendMessageDelayed(localMessage, paramInt2);
        }
    }

    private void setListApater(int paramInt) {
        List localList;
        if (paramInt == 0) {
            localList = list;
            if (localList != null)
                break label39;
            showToast("error!!!");
        }
        while (true) {
            if (paramInt != 0)
                break label180;
            registerForContextMenu(this.mListView);
            return;
            localList = list2;
            break;
            label39:
            if (localList.size() == 0)
                showToast("error!!!");
            while (true) {
                if (paramInt != 0)
                    break label123;
                this.itla = new PlayListAdapter(this);
                this.itla.setListItems(localList);
                this.mListView.setAdapter(this.itla);
                this.mListView.setChoiceMode(1);
                this.mListView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
                        MainActivity.this.onPlayItemById(paramInt);
                        MainActivity.this.itla.setSelectedItem(paramInt);
                    }
                });
                break;
                Collections.sort(localList);
            }
            label123:
            this.itla2 = new PlayListAdapter(this);
            this.itla2.setListItems(localList);
            this.mListView2.setAdapter(this.itla2);
            this.mListView2.setChoiceMode(1);
            this.mListView2.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
                    MainActivity.this.onPlayItemById2(paramInt);
                    MainActivity.this.itla2.setSelectedItem(paramInt);
                }
            });
        }
        label180:
        registerForContextMenu(this.mListView2);
    }

    private void showBadRequest() {
        showToast("无法连接到服务器端，获取信息失败。");
    }

    private void showBusStationInfo(int paramInt) {
        this.car.getBusStationInfo(0, paramInt);
    }

    private void showCarPicture(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
        LayoutParams localLayoutParams = new LayoutParams(-1, -1, paramInt2, paramInt3);
        Matrix localMatrix = new Matrix();
        switch (paramInt1) {
            default:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
        }
        while (true) {
            int i = this.view_6.getBitmap().getWidth();
            int j = this.view_6.getBitmap().getHeight();
            localMatrix.setRotate(paramInt4, i / 2, j / 2);
            switch (paramInt1) {
                default:
                    return;
                this.view_6 = this.view_0;
                continue;
                this.view_6 = this.view_1;
                continue;
                this.view_6 = this.view_2;
                continue;
                this.view_6 = this.view_3;
                continue;
                this.view_6 = this.view_4;
                continue;
                this.view_6 = this.view_5;
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
            }
        }
        this.view_0.setImageMatrix(localMatrix);
        this.view_0.setLayoutParams(localLayoutParams);
        return;
        this.view_1.setImageMatrix(localMatrix);
        this.view_1.setLayoutParams(localLayoutParams);
        return;
        this.view_2.setImageMatrix(localMatrix);
        this.view_2.setLayoutParams(localLayoutParams);
        return;
        this.view_3.setImageMatrix(localMatrix);
        this.view_3.setLayoutParams(localLayoutParams);
        return;
        this.view_4.setImageMatrix(localMatrix);
        this.view_4.setLayoutParams(localLayoutParams);
        return;
        this.view_5.setImageMatrix(localMatrix);
        this.view_5.setLayoutParams(localLayoutParams);
    }

    private void showCarPoint(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
        old_x = paramInt2;
        old_y = paramInt3;
    }

    private void showLotEtcUI(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
        int i = 2130837557;
        int j = 2130837505;
        if (paramInt2 == 100) {
            if (paramInt3 == 1) {
                ImageView localImageView4 = this.iv_shu_01;
                if (paramBoolean)
                    i = 2130837558;
                localImageView4.setImageResource(i);
                ((AnimationDrawable) this.iv_shu_01.getDrawable()).start();
                if ((paramBoolean) && (!Interface.getSyncMode())) {
                    this.map.clear();
                    this.map.put("car_id", Integer.valueOf(paramInt1));
                    Utils.sendMsgToHandle(this.mHandler, 1013, this.map, 6000);
                }
            }
            do {
                return;
                ImageView localImageView3 = this.iv_shu_02;
                if (paramBoolean)
                    i = 2130837558;
                localImageView3.setImageResource(i);
                ((AnimationDrawable) this.iv_shu_02.getDrawable()).start();
            }
            while ((!paramBoolean) || (Interface.getSyncMode()));
            this.map.clear();
            this.map.put("car_id", Integer.valueOf(paramInt1));
            Utils.sendMsgToHandle(this.mHandler, 1016, this.map, 6000);
            return;
        }
        if (paramInt3 == 1) {
            ImageView localImageView2 = this.iv_za_01;
            if (paramBoolean)
                ;
            for (int k = j; ; k = 2130837504) {
                localImageView2.setImageResource(k);
                ((AnimationDrawable) this.iv_za_01.getDrawable()).start();
                if ((!paramBoolean) || (Interface.getSyncMode()))
                    break;
                this.map.clear();
                this.map.put("car_id", Integer.valueOf(paramInt1));
                Utils.sendMsgToHandle(this.mHandler, 1014, this.map, 6000);
                return;
            }
        }
        ImageView localImageView1 = this.iv_za_02;
        if (paramBoolean)
            ;
        while (true) {
            localImageView1.setImageResource(j);
            ((AnimationDrawable) this.iv_za_02.getDrawable()).start();
            if ((!paramBoolean) || (Interface.getSyncMode()))
                break;
            this.map.clear();
            this.map.put("car_id", Integer.valueOf(paramInt1));
            Utils.sendMsgToHandle(this.mHandler, 1015, this.map, 6000);
            return;
            j = 2130837504;
        }
    }

    private void showMSG(String paramString) {
        Interface.addMSG(Interface.getDateTime() + "\n" + paramString + "\n\n");
        showToast(paramString);
    }

    private void showProgressDialog() {
        this.mDialog = ProgressDialog.show(this, "", "please waiting...", true, true);
    }

    private void showReportString(int paramInt, String paramString) {
        if (paramInt == 0)
            if (this.tvx_rp != null) {
                TextView localTextView2 = this.tvx_rp;
                if (paramString.isEmpty())
                    paramString = "no messages!";
                localTextView2.setText(paramString);
            }
        do
            return;
        while (this.txx_park == null);
        TextView localTextView1 = this.txx_park;
        if (paramString.isEmpty())
            paramString = "no messages!";
        localTextView1.setText(paramString);
    }

    private void showText() {
    }

    private void showToast(String paramString) {
        Toast localToast = Toast.makeText(this, paramString, 1);
        localToast.setGravity(85, 400, 300);
        localToast.show();
    }

    private void showToastText(int paramInt, String paramString) {
        Toast localToast = Toast.makeText(this, paramString, 0);
        if (paramInt == 0)
            localToast.setGravity(51, 600, 240);
        while (true) {
            localToast.show();
            return;
            localToast.setGravity(83, 600, 240);
        }
    }

    private void showTrafficLightUI(int paramInt1, int paramInt2) {
        if (Interface.getSyncMode()) {
            switch (paramInt1) {
                default:
                    return;
                case 0:
                    drawable_Draw(paramInt1, paramInt2, this.iv_light_4, this.iv_light_5);
                    return;
                case 1:
                    drawable_Draw(paramInt1, paramInt2, this.iv_light_2, this.iv_light_3);
                    return;
                case 2:
                    drawable_Draw(paramInt1, paramInt2, this.iv_light_6, this.iv_light_7);
                    return;
                case 3:
                    drawable_Draw(paramInt1, paramInt2, this.iv_light_8, this.iv_light_9);
                    return;
                case 4:
            }
            drawable_Draw(paramInt1, paramInt2, this.iv_light_10, this.iv_light_11);
            return;
        }
        switch (paramInt1) {
            default:
                return;
            case 0:
                drawable_Draw(paramInt1, paramInt2, this.iv_light_4, this.iv_light_5);
                return;
            case 3:
                drawable_Draw(paramInt1, paramInt2, this.iv_light_8, this.iv_light_9);
                return;
            case 4:
                drawable_Draw(paramInt1, paramInt2, this.iv_light_10, this.iv_light_11);
                return;
            case 2:
                drawable_Draw(paramInt1, paramInt2, this.iv_light_6, this.iv_light_7);
                return;
            case 1:
        }
        drawable_Draw(paramInt1, paramInt2, this.iv_light_2, this.iv_light_3);
    }

    private void showrunCar(int paramInt1, View paramView, int paramInt2) {
        if (this.car != null)
            this.car.setPaintPoint2(paramInt1, paramInt2);
        runCar(paramInt1, this.view_0);
    }

    private void startHTTP() {
        if ((this.ip.isEmpty()) || (this.ip.equals("0.0.0.0"))) {
            this.wifiManager = ((WifiManager) getSystemService("wifi"));
            if (!this.wifiManager.isWifiEnabled())
                this.wifiManager.setWifiEnabled(true);
            this.ip = intToIp(this.wifiManager.getConnectionInfo().getIpAddress());
            this.nanoHttpd = new MyNanoHTTPD(this, this.ip, 8890);
        }
        if ((this.ip.isEmpty()) || (this.ip.equals("0.0.0.0"))) {
            showToast("无法连接网络!");
            return;
        }
        try {
            if (!this.nanoHttpd.isAlive()) {
                this.nanoHttpd.start();
                Log.e("DEMO", this.nanoHttpd.isAlive() + "---, ip = " + this.ip);
            }
            showToast("打开服务器成功，可以开始与本机互联。");
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    private void stopCar() {
        if (this.car != null)
            this.car.stop();
    }

    private void stopHTTP() {
        try {
            this.nanoHttpd.stop();
            Log.e("DEMO", this.nanoHttpd.isAlive() + "---, ip = " + this.ip);
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    private void stopSync() {
        if (this.mHandler != null)
            this.mHandler.removeMessages(1023);
        for (int i = 0; ; i++) {
            if (i >= Utils.SHOW_SYNC_INFO.length) {
                this.bRun = false;
                this.bViewInit = false;
                Interface.setSyncMode(false);
                cleanALL();
                return;
            }
            this.mHandler.removeMessages(Utils.SHOW_SYNC_INFO[i]);
        }
    }

    private void turnOnStreetLight(boolean paramBoolean) {
        if (!paramBoolean) {
            int j = 2130837516;
            switch (this.mPosition) {
                default:
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
            }
            while (true) {
                this.absoluteLayout_layout.setBackgroundResource(j);
                this.bStreetLightOn = paramBoolean;
                return;
                j = 2130837509;
                continue;
                j = 2130837510;
                continue;
                j = 2130837514;
                continue;
                j = 2130837506;
                continue;
                j = 2130837511;
                continue;
                j = 2130837515;
                continue;
                j = 2130837512;
                continue;
                j = 2130837508;
                continue;
                j = 2130837513;
            }
        }
        int i = 2130837517;
        switch (this.mPosition) {
            default:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
        }
        while (true) {
            this.absoluteLayout_layout.setBackgroundResource(i);
            break;
            i = 2130837520;
            continue;
            i = 2130837521;
            continue;
            i = 2130837525;
            continue;
            i = 2130837518;
            continue;
            i = 2130837522;
            continue;
            i = 2130837526;
            continue;
            i = 2130837523;
            continue;
            i = 2130837519;
            continue;
            i = 2130837524;
        }
    }

    public String GetSyncInfo(String paramString, Context paramContext)
            throws JSONException {
        ((MainActivity) paramContext);
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
        label249:
        int k;
        String str3;
        label310:
        int m;
        String str4;
        label338:
        int n;
        String str5;
        label399:
        int i1;
        String str6;
        label427:
        int i2;
        String str7;
        label488:
        int i3;
        String str8;
        label516:
        int i4;
        String str9;
        label577:
        int i5;
        String str10;
        if (i == 0) {
            str1 = "red";
            localJSONObject2.put("StatusH", str1);
            j = Interface.getLIGHT_MODE(0, 1);
            if (j != 0)
                break label1098;
            str2 = "red";
            localJSONObject2.put("StatusS", str2);
            localJSONObject2.put("Time", this.app.getTraffic().getTrafficTime(0, 0));
            localJSONObject3.put("TrafficId", "2");
            k = Interface.getLIGHT_MODE(1, 0);
            if (k != 0)
                break label1120;
            str3 = "red";
            localJSONObject3.put("StatusH", str3);
            m = Interface.getLIGHT_MODE(1, 1);
            if (m != 0)
                break label1142;
            str4 = "red";
            localJSONObject3.put("StatusS", str4);
            localJSONObject3.put("Time", this.app.getTraffic().getTrafficTime(1, 0));
            localJSONObject4.put("TrafficId", "3");
            n = Interface.getLIGHT_MODE(2, 0);
            if (n != 0)
                break label1164;
            str5 = "red";
            localJSONObject4.put("StatusH", str5);
            i1 = Interface.getLIGHT_MODE(2, 1);
            if (i1 != 0)
                break label1186;
            str6 = "red";
            localJSONObject4.put("StatusS", str6);
            localJSONObject4.put("Time", this.app.getTraffic().getTrafficTime(2, 0));
            localJSONObject5.put("TrafficId", "4");
            i2 = Interface.getLIGHT_MODE(3, 0);
            if (i2 != 0)
                break label1208;
            str7 = "red";
            localJSONObject5.put("StatusH", str7);
            i3 = Interface.getLIGHT_MODE(3, 1);
            if (i3 != 0)
                break label1230;
            str8 = "red";
            localJSONObject5.put("StatusS", str8);
            localJSONObject5.put("Time", this.app.getTraffic().getTrafficTime(3, 0));
            localJSONObject6.put("TrafficId", "5");
            i4 = Interface.getLIGHT_MODE(4, 0);
            if (i4 != 0)
                break label1252;
            str9 = "red";
            localJSONObject6.put("StatusH", str9);
            i5 = Interface.getLIGHT_MODE(4, 1);
            if (i5 != 0)
                break label1274;
            str10 = "red";
        }
        while (true) {
            localJSONObject6.put("StatusS", str10);
            localJSONObject6.put("Time", this.app.getTraffic().getTrafficTime(4, 0));
            localJSONArray1.put(localJSONObject2);
            localJSONArray1.put(localJSONObject3);
            localJSONArray1.put(localJSONObject4);
            localJSONArray1.put(localJSONObject5);
            localJSONArray1.put(localJSONObject6);
            localJSONObject1.put("TrafficInfo", localJSONArray1);
            localJSONObject7.put("RoadLightId", "1");
            localJSONObject7.put("Status", this.app.getStreetLight().getStreetLightOnOff());
            localJSONObject8.put("RoadLightId", "2");
            localJSONObject8.put("Status", this.app.getStreetLight().getStreetLightOnOff());
            localJSONObject9.put("RoadLightId", "3");
            localJSONObject9.put("Status", this.app.getStreetLight().getStreetLightOnOff());
            localJSONArray2.put(localJSONObject7);
            localJSONArray2.put(localJSONObject8);
            localJSONArray2.put(localJSONObject9);
            localJSONObject1.put("RoadLightInfo", localJSONArray2);
            localJSONObject10.put("CarId", "1");
            localJSONObject10.put("Mode", 0);
            localJSONObject10.put("Locaton", this.car.getCarCurrentPoint(0));
            localJSONObject10.put("Path", 1);
            localJSONObject11.put("CarId", "1");
            localJSONObject11.put("Mode", 1);
            localJSONObject12.put("CarId", "2");
            localJSONObject12.put("Mode", 1);
            localJSONArray3.put(localJSONObject10);
            localJSONArray3.put(localJSONObject11);
            localJSONArray3.put(localJSONObject12);
            localJSONObject1.put("LocationInfo", localJSONArray3);
            localJSONObject13.put("GateId", "1");
            localJSONObject13.put("Mode", 0);
            localJSONObject14.put("GateId", "2");
            localJSONObject14.put("Mode", 0);
            localJSONObject15.put("GateId", "1");
            localJSONObject15.put("Mode", 1);
            localJSONObject16.put("GateId", "2");
            localJSONObject16.put("Mode", 1);
            localJSONArray4.put(localJSONObject13);
            localJSONArray4.put(localJSONObject14);
            localJSONArray4.put(localJSONObject15);
            localJSONArray4.put(localJSONObject16);
            localJSONObject1.put("GateStatusInfo", localJSONArray4);
            return localJSONObject1.toString();
            if (i == 1) {
                str1 = "yellow";
                break;
            }
            str1 = "green";
            break;
            label1098:
            if (j == 1) {
                str2 = "yellow";
                break label249;
            }
            str2 = "green";
            break label249;
            label1120:
            if (k == 1) {
                str3 = "yellow";
                break label310;
            }
            str3 = "green";
            break label310;
            label1142:
            if (m == 1) {
                str4 = "yellow";
                break label338;
            }
            str4 = "green";
            break label338;
            label1164:
            if (n == 1) {
                str5 = "yellow";
                break label399;
            }
            str5 = "green";
            break label399;
            label1186:
            if (i1 == 1) {
                str6 = "yellow";
                break label427;
            }
            str6 = "green";
            break label427;
            label1208:
            if (i2 == 1) {
                str7 = "yellow";
                break label488;
            }
            str7 = "green";
            break label488;
            label1230:
            if (i3 == 1) {
                str8 = "yellow";
                break label516;
            }
            str8 = "green";
            break label516;
            label1252:
            if (i4 == 1) {
                str9 = "yellow";
                break label577;
            }
            str9 = "green";
            break label577;
            label1274:
            if (i5 == 1) {
                str10 = "yellow";
                continue;
            }
            str10 = "green";
        }
    }

    public void getCarRect2(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
        this.map.clear();
        this.map.put("car_id", Integer.valueOf(paramInt1));
        this.map.put("pathid", Integer.valueOf(paramInt6));
        this.map.put("mode", Integer.valueOf(paramInt7));
        this.map.put("location", Integer.valueOf(paramInt8));
        showCarPicture(paramInt1, paramInt2, paramInt3, paramInt5, paramInt4);
        Utils.sendMsgToHandle(this.mHandler, Utils.SHOW_SYNC_INFO[paramInt1], this.map, 200);
    }

    public int getDoorState(int paramInt) {
        return this.door_state[paramInt];
    }

    public String getUsedPercentValue(Context paramContext) {
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 2048);
            String str1 = localBufferedReader.readLine();
            long l1 = Integer.parseInt(str1.substring(str1.indexOf("MemTotal:")).replaceAll("\\D+", ""));
            String str2 = localBufferedReader.readLine();
            long l2 = Integer.parseInt(str2.substring(str2.indexOf("MemFree:")).replaceAll("\\D+", ""));
            localBufferedReader.close();
            int i = (int) (100.0F * ((float) (l1 - l2) / (float) l1));
            String str3 = "totalMemory:" + l1 / 1024L + "MB" + ", availableSize:" + l2 / 1024L + "MB" + ", " + i + "%";
            return str3;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
        return "无结果";
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        switch (paramInt2) {
            default:
                return;
            case -1:
                cleanALL();
                return;
            case 0:
                this.bClean = false;
                return;
            case 10:
        }
        destroy();
        cleanViewOnLayout();
        this.bClean = true;
        this.iv_button_play.setBackgroundResource(2130837540);
        Interface.setSyncMode(true);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        getWindow().setFlags(1024, 1024);
        setContentView(2130903040);
        this.app = ((carApplication) getApplication()).getAppInstance();
        this.app.init(this.mHandler);
        this.interf = new Interface(this.app);
        this.car = this.app.getCarClass();
        this.streetLight = this.app.getStreetLight();
        this.mInflater = ((LayoutInflater) getSystemService("layout_inflater"));
        initView();
        this.wifiManager = ((WifiManager) getSystemService("wifi"));
        if (!this.wifiManager.isWifiEnabled())
            this.wifiManager.setWifiEnabled(true);
        this.ip = intToIp(this.wifiManager.getConnectionInfo().getIpAddress());
        this.nanoHttpd = new MyNanoHTTPD(this, this.ip, 8890);
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(2131230720, paramMenu);
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        if (paramMenuItem.getItemId() == 2131296341)
            return true;
        return super.onOptionsItemSelected(paramMenuItem);
    }

    protected void onResume() {
        super.onResume();
    }

    public String run(String[] paramArrayOfString) {
        new Thread(new Runnable(paramArrayOfString) {
            public void run() {
                try {
                    InputStream localInputStream = Runtime.getRuntime().exec(this.val$cmd).getInputStream();
                    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
                    String str;
                    do
                        str = localBufferedReader.readLine();
                    while (!str.startsWith("User"));
                    MainActivity.this.show_line = (str + "\n" + localBufferedReader.readLine());
                    if (localInputStream != null) {
                        localBufferedReader.close();
                        localInputStream.close();
                        MainActivity localMainActivity = MainActivity.this;
                        localMainActivity.show_line = (localMainActivity.show_line + "\n" + MainActivity.this.getUsedPercentValue(MainActivity.this));
                    }
                    return;
                } catch (IOException localIOException) {
                    localIOException.printStackTrace();
                }
            }
        }).start();
        return this.show_line;
    }

    private class ProcessThread extends Thread {
        private Handler mHandler = null;
        private String str_url = "";

        public ProcessThread(Handler paramString, String arg3) {
            this.mHandler = paramString;
            Object localObject;
            this.str_url = localObject;
        }

        private JSONObject setJSONObjects(int paramInt) {
            JSONObject localJSONObject = new JSONObject();
            switch (paramInt) {
                default:
                    return localJSONObject;
                case 1:
            }
            try {
                localJSONObject.put("RoadLightId", 1);
                return localJSONObject;
            } catch (JSONException localJSONException) {
                localJSONException.printStackTrace();
            }
            return localJSONObject;
        }

        public void run() {
            String str = Interface.sendMsgByHttp(this.str_url + "GetSyncInfo");
            Message localMessage = this.mHandler.obtainMessage();
            if (str.equals("BadRequest"))
                ;
            for (localMessage.what = 1024; ; localMessage.what = 1022) {
                this.mHandler.sendMessage(localMessage);
                return;
                Bundle localBundle = new Bundle();
                localBundle.putString("syncInfo", str);
                localMessage.setData(localBundle);
            }
        }
    }

    private class ProcessThread2 extends Thread {
        private Handler mHandler2 = null;
        private int mId = -1;
        private String mValue = "";

        public ProcessThread2(Handler paramInt, int paramString, String arg4) {
            this.mHandler2 = paramInt;
            this.mId = paramString;
            Object localObject;
            this.mValue = localObject;
        }

        private boolean getRadioList() {
            if (this.mId == 0)
                ;
            for (this.mValue = Interface.GetEtcListReport(); this.mValue.isEmpty(); this.mValue = Interface.GetPakingListReport())
                return false;
            return true;
        }

        public void run() {
            Message localMessage = this.mHandler2.obtainMessage();
            Bundle localBundle = new Bundle();
            Log.d("---ProcessThread2--", "-----mId = " + this.mId);
            getRadioList();
            if (this.mId == 0)
                ;
            for (int i = 1029; ; i = 1030) {
                localBundle.putInt("state", i);
                localBundle.putString("data", this.mValue);
                localMessage.setData(localBundle);
                this.mHandler2.sendMessage(localMessage);
                return;
            }
        }
    }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.MainActivity
 * JD-Core Version:    0.6.0
 */