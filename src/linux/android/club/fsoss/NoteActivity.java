package linux.android.club.fsoss;

import linux.android.club.fsoss.notes.NotesDataSource;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends Activity {	
	EditText etNoteTitle, etNote;
	NotesDataSource ds;
	ContentValues data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		
		etNoteTitle = (EditText) findViewById(R.id.etNoteTitle);
		etNote = (EditText) findViewById(R.id.etNote);
		Bundle note = getIntent().getExtras();
		ds = new NotesDataSource(this);
		ds.open();
		if(note != null) {
			etNoteTitle.setText(note.getString("NOTE_TITLE"));
			etNote.setText(note.getString("NOTE_CONTENT"));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_save:
			if(etNoteTitle.getText().length() == 0 || etNote.getText().length() == 0) {
				AlertDialog.Builder noteAlertBuilder = new AlertDialog.Builder(this);
				noteAlertBuilder.setMessage(R.string.alert_message).setTitle(R.string.alert_title);
				noteAlertBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				AlertDialog noteAlertDialog = noteAlertBuilder.create();
				noteAlertDialog.show();
			} else {
				ds.addNote(etNoteTitle.getText().toString(), etNote.getText().toString());
				Toast.makeText(this, "Note saved", Toast.LENGTH_LONG).show();
				this.finish();
			}
			return true;
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		ds.close();
	}

}
