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

	//アイテム追加
	public void updateAddItem(SQLiteDatabase db,String id){
		String sql = "UPDATE item SET count = count + 1 WHERE id = '" + id + "'";
		db.execSQL(sql);
	}

	//アイテム減少(使用した時の処理に)
	public void updateMinusItem(SQLiteDatabase db,String id){
		String sql = "UPDATE item SET count = count - 1 WHERE id = '" + id + "'";
		db.execSQL(sql);
	}

	//ポイント更新
	public void updatePoint(SQLiteDatabase db,int addpoint){
        String sql = "SELECT point FROM point WHERE _id = 1";
        SQLiteCursor cursor = (SQLiteCursor)db.rawQuery(sql, null);
        cursor.moveToFirst();

        int point = cursor.getInt(0);
        point = point + addpoint;

		String sql2 = "UPDATE point SET point = " + String.valueOf(point);
		db.execSQL(sql2);
	}

	//アイテム呼び出し(SELECT)
	//戻り値としてアイテムのテーブル内容を配列で返す
	//result[0]=id,result[1]=name,result[2]=value,result[3]=detail,result[4]=icon,result[5]=count
	public String[] getItem(SQLiteDatabase db,String id){
		String sql = "SELECT id,name,value,detail,icon,count FROM item WHERE id='"+ id +"'";
		SQLiteCursor cursor = (SQLiteCursor)db.rawQuery(sql, null);
		cursor.moveToFirst();

		String result[] = {cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)};

		return result;
	}

	//ポイント呼び出し(SELECT)
	//戻り値としてint型でポイントを返す
	public int getPoint(SQLiteDatabase db){
		String sql = "SELECT point FROM point";
		SQLiteCursor cursor = (SQLiteCursor)db.rawQuery(sql, null);
		cursor.moveToFirst();

		int result = cursor.getInt(0);

		return result;
	}

	//クリアポイント呼び出し(SELECT)
	//戻り値としてステージに対応したポイントをint型で返す
	//※5ステージ目のものが最大値となっています。
	//※levelの値が5より大きい場合は自動的にレベル5に対応したクリアポイントを返します。
	public int getClear_point(SQLiteDatabase db,int level){
		if(level > 5){
			level = 5;
		}
		String sql = "SELECT bonus_point FROM clear_point WHERE level='"+ level +"'";
		SQLiteCursor cursor = (SQLiteCursor)db.rawQuery(sql, null);
		cursor.moveToFirst();

		int result = cursor.getInt(0);

		return result;
	}

	//利き手呼び出し(SELECT)
	//戻り値をint型で返す
	public int[] getHanded(SQLiteDatabase db){
		String sql = "SELECT handed FROM handed";
		SQLiteCursor cursor = (SQLiteCursor)db.rawQuery(sql, null);
		cursor.moveToFirst();

		int result[] = new int[2];

		result[0] = cursor.getInt(0);
		cursor.moveToNext();
		result[1] = cursor.getInt(0);

		return result;
	}

	//共有呼び出し(SELECT)
	//戻り値をString型で記載内容を返す
	//※sns=1 facebook
	//※sns=2 twitter
	public String getShare(SQLiteDatabase db,int sns){

		String sql = "SELECT word FROM share";
		SQLiteCursor cursor = (SQLiteCursor)db.rawQuery(sql, null);
		cursor.moveToFirst();

		String result = cursor.getString(0);

		return result;
	}

	//レコード数を数えるメソッド（データ未挿入判別に使用）
	public boolean getRecordCount(SQLiteDatabase db) {
		try{
        String sql = "SELECT * FROM point";
        SQLiteCursor cursor = (SQLiteCursor)db.rawQuery(sql, null);
        cursor.moveToFirst();

        String result = cursor.getString(cursor.getColumnIndex("_id"));
        Log.v("log", result);
        return false;
		}catch(CursorIndexOutOfBoundsException e){
			return true;
		}
    }


}
