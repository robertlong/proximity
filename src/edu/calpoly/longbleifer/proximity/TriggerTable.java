package edu.calpoly.longbleifer.proximity;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class that provides helpful database table accessor variables and manages
 * basic required database functionality.  
 */
public class TriggerTable {
	
	public static final String TABLE_NAME = "triggers";
	
	public static final String KEY_ID = "_id";
	public static final int COL_ID = 0;
	
	public static final String KEY_UUID = "uuid";
	public static final int COL_UUID = COL_ID + 1;
	
	public static final String KEY_NAME = "name";
	public static final int COL_NAME = COL_ID + 2;
	
	public static final String KEY_LATITUDE = "latitude";
	public static final int COL_LATITUDE = COL_ID + 3;
	
	public static final String KEY_LONGITUDE = "longitude";
	public static final int COL_LONGITUDE = COL_ID + 4;
	
	public static final String KEY_DISCOVERED = "discovered";
	public static final int COL_DISCOVERED = COL_ID + 5;
	
	
	public static final String[] COLUMN_NAMES = {KEY_ID, KEY_UUID, KEY_NAME, KEY_LATITUDE, KEY_LONGITUDE, KEY_DISCOVERED};
	
	/** SQLite database creation statement. Auto-increments IDs of inserted jokes.
	 * Joke IDs are set after insertion into the database. */
	public static final String DATABASE_CREATE = "create table " + TABLE_NAME+ " (" + 
			KEY_ID + " integer primary key autoincrement, " + 
			KEY_UUID	+ " text not null, " + 
			KEY_NAME	+ " text not null, " +
			KEY_LATITUDE	+ " real not null, " +
			KEY_LONGITUDE	+ " real not null, " +
			KEY_DISCOVERED + " text not null);";
	
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

