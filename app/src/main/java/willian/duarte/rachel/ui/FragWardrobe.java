package willian.duarte.rachel.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import willian.duarte.rachel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragWardrobe extends Fragment {


    public FragWardrobe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_frag_wardrobe, container, false);
    }

}
