package willian.duarte.rachel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BaseDAO extends SQLiteOpenHelper {
    public static final String TBL_CLOTH = "tbl_cloth";

    public static final String TBL_CLOTH_ID = "id";
    public static final String TBL_CLOTH_NAME = "name";
    public static final String TBL_CLOTH_TYPE = "type";
    public static final String TBL_CLOTH_PICTURE = "picture";
    public static final String TBL_CLOTH_DATES = "dates";

    public static final String[] TBL_CLOTH_COLUMNS = {
            BaseDAO.TBL_CLOTH_ID,
            BaseDAO.TBL_CLOTH_NAME,
            BaseDAO.TBL_CLOTH_TYPE,
            BaseDAO.TBL_CLOTH_PICTURE,
            BaseDAO.TBL_CLOTH_DATES
    };

    public static final String DATABASE_NAME = "rachel.sqlite";
    public static final int DATABASE_VESION = 1;

    public static final String CREATE_TBL_CLOTH =
            "create table " + BaseDAO.TBL_CLOTH + "(" +
                    BaseDAO.TBL_CLOTH_ID        + " integer primary key AUTOINCREMENT," +
                    BaseDAO.TBL_CLOTH_NAME      + " text null," +
                    BaseDAO.TBL_CLOTH_TYPE      + " text null," +
                    BaseDAO.TBL_CLOTH_PICTURE   + " blob null," +
                    BaseDAO.TBL_CLOTH_DATES     + " text null);";

    public BaseDAO(Context context) {
        super(context, BaseDAO.DATABASE_NAME, null, BaseDAO.DATABASE_VESION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL_CLOTH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
