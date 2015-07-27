package jp.ac.asojuku.jousen.sample;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	SQLiteDatabase db;
	DBclass dbclass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbclass = new DBclass(this);
		db = dbclass.getWritableDatabase();

		if(dbclass.getRecordCount(db)){
			//アイテムの挿入
			dbclass.insertItem(db,"1", "スピードアップ", "100", "一定時間自分の移動速度が上昇します", "speedup.png", "0");
			dbclass.insertItem(db,"2", "スピードダウン", "400", "一定時間顔の移動速度が減少します", "speeddown.png", "0");
			dbclass.insertItem(db,"3", "無敵", "1000", "一定時間顔と接触してもゲームオーバーになりません", "star.png", "0");
			dbclass.insertItem(db,"4", "バナナ", "200", "地面にバナナを仕掛けます。顔が踏むと一定時間麻痺します", "banana.png", "0");
			dbclass.insertItem(db,"5", "ブロック", "500", "その場にブロックを置きます", "brock.png", "0");

			//ポイントの挿入
			dbclass.insertPoint(db,"0");

			//クリアポイントの挿入
			dbclass.insertClear_point(db,"1", "60");
			dbclass.insertClear_point(db,"2", "60");
			dbclass.insertClear_point(db,"3", "120");
			dbclass.insertClear_point(db,"4", "240");
			dbclass.insertClear_point(db,"5", "480");

			//利き手の挿入
			dbclass.insertHanded(db,"1");
			dbclass.insertHanded(db,"2");

			//共有の挿入
			dbclass.insertShare(db,"1", "顔から逃げろ!!!!顔が迫ってくる!今すぐアプリをダウンロードして逃げよう!facebookで共有するとアイテムも貰えるよ!");
			dbclass.insertShare(db,"2", "顔から逃げろ!!!!顔が迫ってくる!今すぐアプリをダウンロードして逃げよう!twitterで共有するとアイテムも貰えるよ!");
		}

		//デバッグ(ポイント処理)
		dbclass.updatePoint(db, -500);
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
//		dbclass = new DBclass(this);
//		db = dbclass.getWritableDatabase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	//バックグラウンド移行時にデータベースをクローズ
	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
		db.close();
	}
}
