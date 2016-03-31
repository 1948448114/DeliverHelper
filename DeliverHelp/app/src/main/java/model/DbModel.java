package model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LJ on 2015/9/28.
 */
public class DbModel extends SQLiteOpenHelper{
    private static final String DATABASENAME = "deliver.db";
    private static final int DATABASEVERSION = 2;

    public DbModel(Context context){
        super(context,DATABASENAME,null,DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE deliver(id varchar(20),name varchar(20),phone varchar(20),content varchar(100),state Integer)");
        Log.w("db create","ok");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("db update","ok");
        db.execSQL("DROP TABLE IF EXISTS deliver");
        onCreate(db);
    }


    public void payment(){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
    }

    public void save(deliver deliver){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into deliver(id,name,phone,content,state) values(?,?,?,?,?)",new Object[]{deliver.getId(),deliver.getName(),deliver.getPhone(),deliver.getContent(),deliver.getState()});
    }

    public void delete(Integer id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from deliver where id=?",new String[]{id.toString()});
    }
    public List<HashMap<String,String>> getAll(){
        List<HashMap<String,String>> delivers = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = getReadableDatabase();
        Integer state=0;
        Cursor cursor = db.rawQuery("select * from deliver where state=?",new String[]{state.toString()});
        while(cursor.moveToNext()){
            Log.w("getallid","ok");
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            HashMap<String,String> item = new HashMap<String,String>(
            );
            item.put("id",id);
            item.put("name",name);
            item.put("phone",phone);
            item.put("content",content);
//            deliver item = new deliver(id,name,content,phone,0);
            delivers.add(item);
        }
        cursor.close();
        db.close();
        return delivers;
    }


    public void changeState(HashMap<Integer,String> record){
        try {
            SQLiteDatabase db = getReadableDatabase();
            String sql;
            Integer state = 1;
            for (int i = 0; i < record.size(); i++) {
                sql = "update deliver set state="+state.toString()+" where id="+record.get(i);
                Log.w("change state",sql);
                db.execSQL(sql);
            }
            db.close();

            Log.w("update result",getAll().toString());
        }
        catch (Exception e){
            Log.w("update error",e.toString());
        }
    }
    public void onfinish(){
        try {
//            getWritableDatabase().execSQL("DROP TABLE IF EXISTS deliver");
            getWritableDatabase().execSQL("delete from deliver");
        }
        catch(Exception e){
            getWritableDatabase().execSQL("DROP TABLE IF EXISTS deliver");
            Log.w("error",e.toString());
        }
    }
}
