package linux.android.club.fsoss.notes;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotesDataSource {
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { NotesTable.COLUMN_ID, NotesTable.COLUMN_TITLE, NotesTable.COLUMN_NOTE };
	
	public NotesDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}
	
	public void open() {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Note addNote(String title, String note) {
		ContentValues values = new ContentValues();
		values.put(NotesTable.COLUMN_TITLE, title);
		values.put(NotesTable.COLUMN_NOTE, note);
		long id = db.insert(NotesTable.TABLE_NAME, null, values);
		Cursor cursor = db.query(NotesTable.TABLE_NAME, allColumns, NotesTable.COLUMN_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();
		Note newNote = cursorToNote(cursor);
		cursor.close();
		return newNote;
	}

	private Note cursorToNote(Cursor cursor) {
		Note note = new Note();
		note.setId(cursor.getLong(0));
		note.setTitle(cursor.getString(1));
		note.setNote(cursor.getString(2));
		return note;
	}
	
	public void delete(Note note) {
		long id = note.getId();
		db.delete(NotesTable.TABLE_NAME, NotesTable.COLUMN_ID + " = " + id, null);
	}
	
	public Note updateNote(Note note) {
		ContentValues values = new ContentValues();
		values.put(NotesTable.COLUMN_TITLE, note.getTitle());
		values.put(NotesTable.COLUMN_NOTE, note.getNote());
		db.update(NotesTable.TABLE_NAME, values, NotesTable.COLUMN_ID + " = " + note.getId(), null);
		Cursor cursor = db.query(NotesTable.TABLE_NAME, allColumns, NotesTable.COLUMN_ID + " = " + note.getId(), null, null, null, null);
		cursor.moveToFirst();
		Note newNote = cursorToNote(cursor);
		cursor.close();
		return newNote;
	}
	
	public List<Note> getAllNotes() {
		List<Note> notes = new ArrayList<Note>();
		Cursor cursor = db.query(NotesTable.TABLE_NAME, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Note note = cursorToNote(cursor);
			notes.add(note);
			cursor.moveToNext();
		}
		cursor.close();
		return notes;
	}
	
	public Note getNoteById(long id) {
		Cursor cursor = db.query(NotesTable.TABLE_NAME, allColumns, NotesTable.COLUMN_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();
		Note newNote = cursorToNote(cursor);
		cursor.close();
		return newNote;
	}
}
