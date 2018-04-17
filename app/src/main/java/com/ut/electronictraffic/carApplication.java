package com.ut.electronictraffic;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;

import com.ut.electronictraffic.classes.BusStation;
import com.ut.electronictraffic.classes.EnvSensor;
import com.ut.electronictraffic.classes.Factory;
import com.ut.electronictraffic.classes.StreetLight;
import com.ut.electronictraffic.classes.TrafficLight;
import com.ut.electronictraffic.classes.carActivity;

public class carApplication extends Application {
    private carApplication app = this;
    private carActivity car;
    private EnvSensor envSensor;
    private Factory factory;
    private StreetLight light;
    private Handler m_handler;
    private SharedPreferences sharedPrefs;
    private BusStation station;
    private TrafficLight traffic;

    public carApplication getAppInstance() {
        return this.app;
    }

    public BusStation getBusStation() {
        return this.station;
    }

    public carActivity getCarClass() {
        return this.car;
    }

    public EnvSensor getEnvSensor() {
        return this.envSensor;
    }

    public Factory getFactory() {
        return this.factory;
    }

    public Handler getHandler() {
        return this.m_handler;
    }

    public SharedPreferences getSharedPreferences() {
        return this.sharedPrefs;
    }

    public StreetLight getStreetLight() {
        return this.light;
    }

    public TrafficLight getTraffic() {
        return this.traffic;
    }

    public void init(Handler paramHandler) {
        this.m_handler = paramHandler;
        this.envSensor = new EnvSensor(this);
        this.light = new StreetLight(this, paramHandler);
        this.traffic = new TrafficLight(this, paramHandler);
        this.station = new BusStation(this, paramHandler);
        this.factory = new Factory(this);
        this.car = new carActivity(this, paramHandler);
    }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.carApplication
 * JD-Core Version:    0.6.0
 */