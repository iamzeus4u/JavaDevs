package com.zeus.javadevs;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeus.javadevs.dummy.DevContent;

/**
 * A fragment representing a single dev detail screen.
 * This fragment is either contained in a {@link devListActivity}
 * in two-pane mode (on tablets) or a {@link devDetailActivity}
 * on handsets.
 */
public class devDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_USERNAME = "item_username";

    /**
     * The dummy username this fragment is presenting.
     */
    private DevContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public devDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_USERNAME)) {
            // Load the dummy username specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load username from a username provider.
            mItem = DevContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_USERNAME));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.username);
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dev_detail, container, false);

        // Show the dummy username as text in a TextView.
        if (mItem != null) {

            new devListActivity.DownloadImageTask((ImageView) rootView.findViewById(R.id.dev_pix)).execute(mItem.profilePix);
            //((ImageView) rootView.findViewById(R.id.dev_pix)).setText(mItem.profileURL);
            ((TextView) rootView.findViewById(R.id.dev_detail)).setText(mItem.profileURL);
        }

        return rootView;
    }
}
