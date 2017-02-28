package yuana.kodemetro.com.cargallery.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import yuana.kodemetro.com.cargallery.models.Car;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/14/17
 */

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, Db.NAME, null, Db.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(Db.CarTable.CREATE);

            // TODO: 9/23/16 exec another table

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    // TODO: 2/14/17

    public boolean save(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = Db.CarTable.toContentValues(car);

        db.insert(Db.CarTable.TABLE_NAME, null, contentValues);
        db.close();

        return true;
    }

    public boolean update(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = Db.CarTable.toContentValues(car);

        String where = Db.CarTable.FIELD_ID + "='?'";
        String[] whereArgs = {String.valueOf(car.getId())};

        db.update(Db.CarTable.TABLE_NAME, contentValues, where, whereArgs);

        return true;
    }

    public List<Car> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + Db.CarTable.TABLE_NAME;

        Cursor cursor = db.rawQuery(sql, null);

        List<Car> cars = new ArrayList<>();
        while (cursor.moveToNext()) {
            Car car = Db.CarTable.parseCursor(cursor);
            cars.add(car);
        }

        cursor.close();
        return cars;
    }

    public Car findById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + Db.CarTable.TABLE_NAME + " WHERE " + Db.CarTable.FIELD_ID +
                " = " + id;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null)
            cursor.moveToFirst();

        Car car = Db.CarTable.parseCursor(cursor);

        cursor.close();
        return car;
    }

    public void delete(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = Db.CarTable.FIELD_ID + "='?'";
        String[] whereArgs = {String.valueOf(car.getId())};

        db.delete(Db.CarTable.TABLE_NAME, where, whereArgs);
    }
}
