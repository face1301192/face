/**
 *
 */
package jp.ac.asojuku.jousen.sample;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author student
 *
 */
public class DBclass extends SQLiteOpenHelper {

	public DBclass(Context context) {
		// TODO 自動生成されたコンストラクター・スタブ4
		super(context,"face.db",null,2);
	}

	/* (非 Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自動生成されたメソッド・スタブ

			db.execSQL("CREATE TABLE item (_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER NOT NULL,name TEXT NOT NULL,value INTEGER NOT NULL, detail TEXT, icon TEXT NOT NULL,count INTEGER)");
			db.execSQL("CREATE TABLE point (_id INTEGER PRIMARY KEY AUTOINCREMENT, point INTEGER DEFAULT 0)");
			db.execSQL("CREATE TABLE clear_point (_id INTEGER PRIMARY KEY AUTOINCREMENT, level INTEGER NOT NULL, bonus_point INTEGER NOT NULL)");
			db.execSQL("CREATE TABLE handed (_id INTEGER PRIMARY KEY AUTOINCREMENT, handed INTEGER NOT NULL)");
			db.execSQL("CREATE TABLE share (_id INTEGER PRIMARY KEY AUTOINCREMENT, sns INTEGER NOT NULL,word TEXT NOT NULL)");

	}

	/* (非 Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自動生成されたメソッド・スタブ
		db.execSQL("DROP TABLE item");
		db.execSQL("DROP TABLE point");
		db.execSQL("DROP TABLE clear_point");
		db.execSQL("DROP TABLE handed");
		db.execSQL("DROP TABLE share");
		onCreate(db);
	}

	//アイテム挿入
	public void insertItem(SQLiteDatabase db,String id,String name,String value,String detail,String icon,String count){

		String sql = "INSERT INTO item(id,name,value,detail,icon,count) VALUES(?,?,?,?,?,?)";
		db.execSQL(sql, new String[]{id,name,value,detail,icon,count});
	}

	//ポイント挿入
	public void insertPoint(SQLiteDatabase db,String point){

		String sql = "INSERT INTO point(point) VALUES(?)";
		db.execSQL(sql, new String[]{point});
	}

	//クリアポイント挿入
	public void insertClear_point(SQLiteDatabase db,String level,String bonus_point){

		String sql = "INSERT INTO clear_point(level,bonus_point) VALUES(?,?)";
		db.execSQL(sql, new String[]{level,bonus_point});
	}

	//利き手挿入
	public void insertHanded(SQLiteDatabase db,String handed){

		String sql = "INSERT INTO handed(handed) VALUES(?)";
		db.execSQL(sql, new String[]{handed});
	}

	//共有挿入
	public void insertShare(SQLiteDatabase db,String sns,String word){

		String sql = "INSERT INTO share(sns,word) VALUES(?,?)";
		db.execSQL(sql, new String[]{sns,word});
	}

	//アイテム更新
	
	//ポイント更新
	
	//レコード数を数えるメソッド（データ未挿入判別に使用）
	public boolean getRecordCount(SQLiteDatabase db) {
		try{
        String sql = "SELECT COUNT(*) FROM point";
        SQLiteCursor cursor = (SQLiteCursor)db.rawQuery(sql, null);

        String result = cursor.getString(cursor.getColumnIndex("_id"));
        Log.v("log", result);
        return false;
		}catch(CursorIndexOutOfBoundsException e){
			return true;
		}
    }


}
