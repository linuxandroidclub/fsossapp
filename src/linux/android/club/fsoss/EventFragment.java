package linux.android.club.fsoss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_event, container, false);
		
		((TextView)rootView.findViewById(R.id.eventDummyText)).setText("Events portion here");
		
		return rootView;
	}

}
