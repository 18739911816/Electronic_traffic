package com.ut.electronictraffic.interfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dataBase extends SQLiteOpenHelper
{
  public static final String CARD_ACCOUNT_INFO = "CarAccountInfo";
  public static final String CARD_ETC_INFO = "CarEtcInfo";
  public static final String CARD_ID = "CarId";
  public static final String CARD_MSG = "MsgInfo";
  public static final String CARD_PARK_INFO = "CarParkInfo";
  private static final String DATABASE_NAME = "CarInfo_msg.db";
  private static final int DATABASE_VERSION = 1;
  private static final String TABLE_NAME = "info_table";

  public dataBase(Context paramContext)
  {
    super(paramContext, "CarInfo_msg.db", null, 1);
  }

  public void delete(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramInt);
    localSQLiteDatabase.delete("info_table", "CarId=?", arrayOfString);
  }

  public void delete(String paramString)
  {
    getWritableDatabase().delete("info_table", "column = ?", new String[] { paramString });
  }

  public long insert(int paramInt, String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    if (paramInt == 0)
      localContentValues.put("CarEtcInfo", paramString);
    while (true)
    {
      return localSQLiteDatabase.insert("info_table", null, localContentValues);
      if (paramInt == 1)
      {
        localContentValues.put("CarParkInfo", paramString);
        continue;
      }
      if (paramInt == 3)
      {
        localContentValues.put("MsgInfo", paramString);
        continue;
      }
      localContentValues.put("CarAccountInfo", paramString);
    }
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("CREATE TABLE info_table (CarId INTEGER primary key autoincrement, CarParkInfo text, CarEtcInfo text, MsgInfo text, CarAccountInfo text);");
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS info_table");
    onCreate(paramSQLiteDatabase);
  }

  public Cursor select()
  {
    return getReadableDatabase().query("info_table", null, null, null, null, null, null);
  }

  public void update(int paramInt1, int paramInt2, String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = Integer.toString(paramInt1);
    ContentValues localContentValues = new ContentValues();
    if (paramInt2 == 0)
      localContentValues.put("CarEtcInfo", paramString);
    while (true)
    {
      localSQLiteDatabase.update("info_table", localContentValues, "CarId = ?", arrayOfString);
      return;
      if (paramInt2 == 1)
      {
        localContentValues.put("CarParkInfo", paramString);
        continue;
      }
      localContentValues.put("CarAccountInfo", paramString);
    }
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.interfaces.dataBase
 * JD-Core Version:    0.6.0
 */