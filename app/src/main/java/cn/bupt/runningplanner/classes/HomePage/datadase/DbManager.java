package cn.bupt.runningplanner.classes.HomePage.datadase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbManager {
    public MysqliteHelper helper;
    public   MysqliteHelper getInstance(Context context)
    {
        if(helper==null)
        {
            helper=new MysqliteHelper(context);
        }
        return helper;
    }

    public static void execsql(SQLiteDatabase db, String sql)
    {
        if(db!=null)
        {
            if(sql!=null && "".equals(sql))
            {
                db.execSQL(sql);
            }
        }
    }



}

