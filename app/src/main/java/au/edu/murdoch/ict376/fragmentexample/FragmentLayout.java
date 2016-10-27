package au.edu.murdoch.ict376.fragmentexample;


import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class FragmentLayout extends AppCompatActivity {  // FragmentActivity

    Button mSwitchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment_layout);

        // During initial setup, plug in the details fragment.
        if (savedInstanceState == null) {

            TitlesFragment titlesFragment = TitlesFragment.newInstance(); // index);
            getFragmentManager().beginTransaction().add(R.id.main_fragment_container, titlesFragment).commit();

        }

    }

    
    public void onSwitchFragment(View v){

        TitlesFragment details = (TitlesFragment)
                        getFragmentManager().findFragmentById(R.id.main_fragment_container);

        details.showDetails(details.getmCurCheckPosition(), 1 - details.getFragmentId());

    }



}
