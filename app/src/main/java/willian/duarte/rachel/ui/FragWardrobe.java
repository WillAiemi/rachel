package willian.duarte.rachel.ui;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import willian.duarte.rachel.R;
import willian.duarte.rachel.adapter.ClothAdapter;
import willian.duarte.rachel.dao.ClothDAO;
import willian.duarte.rachel.model.Cloth;
import willian.duarte.rachel.model.Date;


public class FragWardrobe extends Fragment {

    private static final Date ARG_DATE = null;
    private static final String TAG = "LogsFragWardrobe";
    private TextView tvTest;

    private RecyclerView rvClothes;
    private ArrayList<Cloth> clothes;
    private ClothAdapter adapter;

    private ClothDAO clothDAO;

    public FragWardrobe() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_wardrobe, container, false);
        // start
        init(v);
        Log.e(TAG,"setou o init");
        setTextOnTextView(getArguments());
        Log.e(TAG,"até aqui era pra funcionar");
        clothDAO.openDBWritable();
        Log.e(TAG,"abriu o banco");

        clothes = clothDAO.select();
        Log.e(TAG,"pegou o select das roupas");
        adapter = new ClothAdapter(v.getContext(),clothes);
        rvClothes.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(v.getContext(),3);

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvClothes.setLayoutManager(layoutManager);

        adapter.setOnItemClickListener(new ClothAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //era pra dar intent pros details...
            }

            @Override
            public void onItemLongClick(View v, final int position) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(getResources().getString(R.string.alert_delete_title));
                alert.setMessage(getResources().getString(R.string.alert_delete_message));
                alert.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clothDAO.delete(clothes.get(position));
//                        onResume();
                        clothes = clothDAO.select();
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton(getResources().getString(R.string.cancel), null);
                alert.show();
            }
        });


        adapter.notifyDataSetChanged();
        Log.e(TAG,"notificou a diferença");

        //finish
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onPause() {
        super.onPause();
        clothDAO.closeDB();
    }

    @Override
    public void onResume() {
        super.onResume();
        clothDAO.openDBWritable();
        adapter.setClothes(clothDAO.select());
        adapter.notifyDataSetChanged();
    }

    private void init(View v){
        tvTest = v.findViewById(R.id.tv_teste);

        rvClothes = v.findViewById(R.id.fragwardrobe_rv_clothes);
        clothes = new ArrayList<>();



        Log.e(TAG,"até aqui de boa do init");
        clothDAO = new ClothDAO(getActivity());
        Log.e(TAG,"será q é o contexto q tá fodendo?");
    }

    public static FragWardrobe newInstance(Date date) {
        Bundle args = new Bundle();
        FragWardrobe fragment = new FragWardrobe();
        args.putParcelable("ARG_DATE", date);
        fragment.setArguments(args);
        return fragment;
    }

    private void setTextOnTextView(Bundle arguments){
        if (arguments != null && arguments.containsKey("ARG_DATE")){
            tvTest.setText(getArguments().getParcelable("ARG_DATE").toString());
        }
    }

    private void toast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    } // close toast();
}
