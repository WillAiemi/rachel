package willian.duarte.rachel.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;

import willian.duarte.rachel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddClothes extends Fragment {

    private FirebaseStorage storage;

    private ImageView imageView;
    private Button btOk;
    private Uri filePath;
    private static View VIEW;

    private static final int PICK_IMAGE_REQUEST = 22;

    public AddClothes() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_clothes, container, false);
        VIEW = v;
        init(v);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                chooseImage();
                toast("Choose");
            }
        });

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("OK");
            }
        });

        return v;
    }

//    private void chooseImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }

    private void init(View v){
        imageView = v.findViewById(R.id.add_clothes_iv_picture);
        btOk = v.findViewById(R.id.add_clothes_bt_ok);
    }


    private void toast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

}
