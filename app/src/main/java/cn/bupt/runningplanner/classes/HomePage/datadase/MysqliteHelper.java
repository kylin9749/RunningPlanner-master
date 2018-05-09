package cn.bupt.runningplanner.classes.HomePage.datadase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MysqliteHelper extends SQLiteOpenHelper {

    /**
     * 构造函数
     * @param context  上下文对象
     * @param name    数据库名称
     * @param factory
     * @param version 数据库版本 》=1
     */
    public MysqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,version);
    }

    public MysqliteHelper(Context context)
    {
        super(context,Constant.DATABASE_NAME,null,Constant.DATABASE_VERSION);
    }


    /**
     * 数据库创建时的回调函数
     * @param db 数据库对象
     */
    @Override

    public void onCreate(SQLiteDatabase db) {
        String password="0";

        String sqltable1="create table "+Constant.TABLE_NAME1+"("
                +Constant.E_MAIL+" String primary key,"
                +Constant.USER_NAME+" String,"
                +Constant.PASSWORD+" String,"
                +Constant.SEX+" char default('0'),"
                +Constant.AGE+" int default (0),"
                +Constant.HIGH+" int default (0),"
                +Constant.WEIGHT+" int default (0),"
                +Constant.TOTAL_TIME+" long default (0),"
                +Constant.TOTAL_LONG+" double default (0),"
                +Constant.TOTAL_CALORIE+" long default (0),"
                +Constant.NUMBER+" int default (0))";
        String sqltable2="create table "+Constant.TABLE_NAME2+"("
                +Constant.E_MAIL+" String,"
                +Constant.START_TIME+" long default (0),"
                +Constant.FINISH_TIME+" long default (0),"
                +Constant.TIME+" long default (0),"
                +Constant.CALORIE+" long default (0),"
                +Constant.LONG+" double default (0))";


        db.execSQL(sqltable1);//执行sql语句
        db.execSQL(sqltable2);//执行sql语句


    }

    /**
     * 出现数据库版本更新时的回调函数
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("tag","----------onupdate-----------");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        Log.i("tag","----------onOpen-----------");
    }
}

