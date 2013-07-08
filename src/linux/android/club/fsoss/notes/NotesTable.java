package linux.android.club.fsoss.notes;

import android.database.sqlite.SQLiteDatabase;

public class NotesTable {
	
	public static final String TABLE_NAME = "notes";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_NOTE = "note";
	
	public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME
			+ "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_TITLE + " TEXT NOT NULL, "
			+ COLUMN_NOTE + " TEXT NOT NULL"
			+ ");";
	
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
