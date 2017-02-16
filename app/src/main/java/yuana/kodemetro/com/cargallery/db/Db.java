package yuana.kodemetro.com.cargallery.db;

import android.content.ContentValues;
import android.database.Cursor;

import yuana.kodemetro.com.cargallery.models.Car;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/14/17
 */

class Db {

    static final int VERSION = 1;

    static final String NAME = "db_car";

    /**
     * car table
     */
    public static abstract class CarTable {

        static final String TABLE_NAME = "car";

        static final String FIELD_ID = "car_id";
        static final String FIELD_MAKE = "car_make";
        static final String FIELD_MODEL = "car_model";
        static final String FIELD_YEAR = "car_year";

        static final String CREATE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        FIELD_ID + " INT PRIMARY KEY, " +
                        FIELD_MAKE + " TEXT NOT NULL, " +
                        FIELD_MODEL + " TEXT NOT NULL, " +
                        FIELD_YEAR + " TEXT NOT NULL );";

        public static ContentValues toContentValues(Car car) {
            ContentValues values = new ContentValues();
            values.put(FIELD_ID, car.getId());
            values.put(FIELD_MAKE, car.getMake());
            values.put(FIELD_MODEL, car.getModel());
            values.put(FIELD_YEAR, car.getYear());
            return values;
        }

        public static Car parseCursor(Cursor cursor) {
            return new Car(
                    cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FIELD_MAKE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FIELD_MODEL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FIELD_YEAR))
            );
        }
    }
}
