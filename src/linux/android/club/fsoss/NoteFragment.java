package linux.android.club.fsoss;


import java.util.List;

import linux.android.club.fsoss.notes.Note;
import linux.android.club.fsoss.notes.NotesDataSource;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class NoteFragment extends ListFragment {
	NotesDataSource ds;
	ArrayAdapter<Note> adapter;
	View rootView;
	
	Cursor notesCursor;
	
	OnItemClickListener clickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Note note = ds.getNoteById(((Note)getListAdapter().getItem(arg2)).getId());
			Intent noteIntent = new Intent(getActivity(), NoteActivity.class);
			noteIntent.putExtra("NOTE_TITLE", note.getTitle());
			noteIntent.putExtra("NOTE_CONTENT", note.getNote());
			startActivity(noteIntent);
		}
	};
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Note note = (Note)adapter.getItem(info.position);
		switch(item.getItemId()) {
		case R.id.action_delete:
			ds.delete(note);
			adapter.remove(note);
			adapter.notifyDataSetChanged();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, R.id.action_delete, Menu.NONE, R.string.action_delete);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		registerForContextMenu(getListView());
		ds = new NotesDataSource(getActivity());
		getListView().setOnItemClickListener(clickListener);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_note, container, false);
		setHasOptionsMenu(true);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		ds.open();
		List<Note> notes = ds.getAllNotes();
		if(notes.size() == 0) {
			getListView().setEmptyView(rootView.findViewById(android.R.id.empty));
		} else {
			adapter = new ArrayAdapter<Note>(getActivity(), android.R.layout.simple_list_item_1, notes);
			setListAdapter(adapter);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		ds.close();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_add:
			Intent noteActivity = new Intent(getActivity(), NoteActivity.class);
			startActivity(noteActivity);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
