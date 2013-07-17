package linux.android.club.fsoss;

import java.util.ArrayList;
import java.util.HashMap;

import linux.android.club.fsoss.events.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class EventFragment extends ListFragment{
	private static String url = "http://docs.blackberry.com/sampledata.json";

	private static final String TAG_VTYPE = "vehicleType";
	private static final String TAG_VCOLOR = "vehicleColor";
	private static final String TAG_FUEL = "fuel";
	private static final String TAG_TREAD = "treadType";
	
	ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
	

	ProgressBar progress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_event, container, false);
		progress = (ProgressBar) rootView.findViewById(R.id.pbEvents);
		new ProgressTask(getActivity()).execute();
		setHasOptionsMenu(true);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.options_event, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_events_refresh:
			jsonlist.clear();
			new ProgressTask(getActivity()).execute();
		case R.id.action_sort_event:
		case R.id.action_sort_speaker:
		case R.id.action_sort_time:
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class ProgressTask extends AsyncTask<String, Void, Boolean> {
		private Context context;

		public ProgressTask(FragmentActivity activity) {
			Log.i("1", "Called");
			context = activity;
		}
		
		protected void onPreExecute() {
			progress.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (progress.isShown()) {
				progress.setVisibility(View.GONE);
			}
			ListAdapter adapter = new SimpleAdapter(
					context, 
					jsonlist,
					R.layout.list_item, 
					new String[] { TAG_VTYPE, TAG_VCOLOR, TAG_FUEL, TAG_TREAD }, 
					new int[] { R.id.vehicleType, R.id.vehicleColor, R.id.fuel, R.id.treadType });

			setListAdapter(adapter);
		}

		protected Boolean doInBackground(final String... args) {
			JsonParser jParser = new JsonParser();
			// getting JSON string from URL
			JSONArray json = jParser.getJSONFromUrl(url);

			for (int i = 0; i < json.length(); i++) {
				try {
					JSONObject c = json.getJSONObject(i);
					String vtype = c.getString(TAG_VTYPE);
					String vcolor = c.getString(TAG_VCOLOR);
					String vfuel = c.getString(TAG_FUEL);
					String vtread = c.getString(TAG_TREAD);

					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_VTYPE, vtype);
					map.put(TAG_VCOLOR, vcolor);
					map.put(TAG_FUEL, vfuel);
					map.put(TAG_TREAD, vtread);
					jsonlist.add(map);
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}
	}
}
