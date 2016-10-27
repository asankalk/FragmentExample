package au.edu.murdoch.ict376.fragmentexample;


/**
 * Created by 20160132 on 9/10/2016.
 */

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        // During initial setup, plug in the details fragment.
        if (savedInstanceState == null) {

            int index = getIntent().getExtras().getInt("index", 0);  /// zero will be the default value

            DetailsFragment details = DetailsFragment.newInstance(index);
            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();

        }

    }
}
