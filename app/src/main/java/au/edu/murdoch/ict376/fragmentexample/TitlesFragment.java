package au.edu.murdoch.ict376.fragmentexample;

//import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Hamid on 9/10/2016.
 */

public class TitlesFragment extends ListFragment {
    boolean mDualPane;
    int mCurCheckPosition = 0;
    int fragmentId        = 0;

    public static TitlesFragment newInstance() {
        TitlesFragment f = new TitlesFragment();

        return f;
    }

    public int getFragmentId(){
        return fragmentId;
    }

    public void setFragmentId(int id){
        fragmentId = id;
    }

    public int getmCurCheckPosition(){
        return mCurCheckPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.titles_list_layout, null);

        return v;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        //View v = inflater.inflate(R.layout.titles_list_layout, null);


        // Populate list with our static array of titles.
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, Shakespeare.TITLES));

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.

        View detailsFrame = getActivity().findViewById(R.id.details_fragment_container);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }


        if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            Button switchFragment = (Button)getActivity().findViewById(R.id.switch_fragment);
            switchFragment.setVisibility(View.INVISIBLE);

            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }else{
            Button switchFragment = (Button)getActivity().findViewById(R.id.switch_fragment);
            switchFragment.setVisibility(View.INVISIBLE);
        }


        //return  getView();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt("curChoice", mCurCheckPosition);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }


    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index){
        // use the existing fragments
        showDetails(index, -1);
    }

    void showDetails(int index, int new_fragment_id) {
        mCurCheckPosition = index;

        if (new_fragment_id == -1)  // I won't switch fragments
            new_fragment_id = fragmentId;

        if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            getListView().setItemChecked(index, true);

            if (new_fragment_id == 0){
                DetailsFragment details = DetailsFragment.newInstance(index);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details_fragment_container, details);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }else{

                DetailsFragment2 details = DetailsFragment2.newInstance(index);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details_fragment_container, details);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }

            fragmentId = new_fragment_id;

            Button switchFragment = (Button)getActivity().findViewById(R.id.switch_fragment);
            switchFragment.setVisibility(View.VISIBLE);

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsActivity.class);
            intent.putExtra("index", index);        // index of the item that will be displayed
            startActivity(intent);
        }

    }
}