package willian.duarte.rachel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import willian.duarte.rachel.helper.BaseDAO;
import willian.duarte.rachel.model.Cloth;
import willian.duarte.rachel.model.Date;

public class ClothDAO {
    private SQLiteDatabase database;
    private BaseDAO dbHelper;

    public ClothDAO(Context context) {
        Log.d("LogClothDAO","tá construindo");
        dbHelper = new BaseDAO(context);
        Log.d("LogClothDAO","construiu de boa");
    }

    public void openDBWritable() {
        database = dbHelper.getWritableDatabase();
    }

    public void openDBReadable(){
        database = dbHelper.getReadableDatabase();
    }

    public void closeDB() {
        dbHelper.close();
    }

    public long insert(Cloth cloth) {
        ContentValues cv = new ContentValues();
        cv.put(BaseDAO.TBL_CLOTH_NAME, cloth.getName());
        cv.put(BaseDAO.TBL_CLOTH_TYPE, cloth.getType());
        cv.put(BaseDAO.TBL_CLOTH_PICTURE, getBytesFromBitmap(cloth.getPicture()));
        Gson gson = new Gson();
        cv.put(BaseDAO.TBL_CLOTH_DATES, gson.toJson(cloth.getDates()));

        return database.insert(BaseDAO.TBL_CLOTH, null, cv);
    }

    public long update(Cloth cloth) {
        int id = cloth.getId();

        ContentValues cv = new ContentValues();
        cv.put(BaseDAO.TBL_CLOTH_NAME, cloth.getName());
        cv.put(BaseDAO.TBL_CLOTH_TYPE, cloth.getType());
        cv.put(BaseDAO.TBL_CLOTH_PICTURE, getBytesFromBitmap(cloth.getPicture()));
        Gson gson = new Gson();
        cv.put(BaseDAO.TBL_CLOTH_DATES, gson.toJson(cloth.getDates()));

        return database.update(
                BaseDAO.TBL_CLOTH,
                cv,
                BaseDAO.TBL_CLOTH_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public int delete(Cloth cloth) {
        int id = cloth.getId();

        return database.delete(
                BaseDAO.TBL_CLOTH,
                BaseDAO.TBL_CLOTH_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public ArrayList<Cloth> select() {
        Log.d("LogClothDAO","Entrou no select");
        ArrayList<Cloth> cloths = new ArrayList<>();

        Cursor cursor = database.query(
                BaseDAO.TBL_CLOTH,
                BaseDAO.TBL_CLOTH_COLUMNS,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Cloth cloth = new Cloth(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    getBitmapFromBytesArray(cursor.getBlob(3))
            );

            if (cursor.getString(4) != null){
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Date>>(){}.getType();
                ArrayList<Date> dates = gson.fromJson(cursor.getString(4),type);
                cloth.setDates(dates);
            }
            cloths.add(cloth);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("LogClothDAO","deu tudo certo, retornando....");
        return cloths;
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    private Bitmap getBitmapFromBytesArray(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
