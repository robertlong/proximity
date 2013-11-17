package edu.calpoly.longbleifer.proximity;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class that provides helpful database table accessor variables and manages
 * basic required database functionality.  
 */
public class TabTable {
	
	public static final String TABLE_NAME = "tabs";
	
	public static final String KEY_ID = "_id";
	public static final int COL_ID = 0;
	
	public static final String KEY_TRIGGER_ID = "trigger_id";
	public static final int COL_TRIGGER_ID = COL_ID + 1;
	
	public static final String KEY_TITLE = "title";
	public static final int COL_TITLE = COL_ID + 2;
	
	public static final String KEY_TYPE = "type";
	public static final int COL_TYPE = COL_ID + 3;
	
	public static final String KEY_POSITION = "position";
	public static final int COL_POSITION = COL_ID + 4;
	
	public static final String KEY_METADATA = "metadata";
	public static final int COL_METADATA = COL_ID + 5;
	
	
	public static final String[] COLUMN_NAMES = {KEY_ID, KEY_TRIGGER_ID, KEY_TITLE, KEY_TYPE, KEY_POSITION, KEY_METADATA};
	
	/** SQLite database creation statement. Auto-increments IDs of inserted jokes.
	 * Joke IDs are set after insertion into the database. */
	public static final String DATABASE_CREATE = "create table " + TABLE_NAME+ " (" + 
			KEY_ID + " integer primary key autoincrement, " + 
			KEY_TRIGGER_ID	 + " integer not null, " + 
			KEY_TITLE	 + " text not null, " +
			KEY_TYPE	 + " text not null, " +
			KEY_POSITION + " integer not null, " +
			KEY_METADATA + " text not null" +
			"FOREIGN KEY(" + KEY_TRIGGER_ID + ") REFERENCES "+ TriggerTable.TABLE_NAME +"(id));";
	
	/** SQLite database table removal statement. Only used if upgrading database. */
	public static final String DATABASE_DROP = "drop table if exists " + TABLE_NAME;
	
	/**
	 * Initializes the database.
	 * 
	 * @param database
	 * 				The database to initialize.	
	 */
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	/**
	 * Upgrades the database to a new version.
	 * 
	 * @param database
	 * 					The database to upgrade.
	 * @param oldVersion
	 * 					The old version of the database.
	 * @param newVersion
	 * 					The new version of the database.
	 */
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(TABLE_NAME, "Database being upgraded.");
		database.execSQL(DATABASE_DROP);
		onCreate(database);
	}
}

