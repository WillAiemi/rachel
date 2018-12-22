package willian.duarte.rachel.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import willian.duarte.rachel.R;
import willian.duarte.rachel.model.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragWardrobe extends Fragment {

    private static final Date ARG_DATE = null;
    private static final String TAG = "LogsFragWardrobe";
    private TextView tvTest;

    public FragWardrobe() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_wardrobe, container, false);

        tvTest = v.findViewById(R.id.tv_teste);
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("ARG_DATE")){
            tvTest.setText(getArguments().getParcelable("ARG_DATE").toString());
        }


        return v;
    }

    public static FragWardrobe newInstance(Date date) {
        Bundle args = new Bundle();
        FragWardrobe fragment = new FragWardrobe();
        args.putParcelable("ARG_DATE", date);
        fragment.setArguments(args);
        return fragment;
    }

    public void setTextViewText(String text){

    }

}
