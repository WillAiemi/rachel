package willian.duarte.rachel.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import willian.duarte.rachel.Manifest;
import willian.duarte.rachel.R;
import willian.duarte.rachel.dao.ClothDAO;
import willian.duarte.rachel.model.Cloth;

public class AddClothes extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 22311;
    private static final int CAMERA_REQUEST = 22312;

    private static final String TAG = "LogsAddClothes";

    private EditText etName;

    private ImageView ivPicture;

    private Button btShirt;
    private Button btPants;
    private Button btUnderwear;
    private Button btAccessories;

    private Button btAdd;

    private CardView cvType;
    private Bitmap imageBitmap;
    private ClothDAO clothDAO;

    private int typeSelected = 0;
    private boolean imageSelected = false;

    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);
        Log.d(TAG,"Setou o layout");
        init();
        Log.d(TAG,"Deu init");
        clothDAO.openDBWritable();
        Log.d(TAG,"abriu o DB");
    }

    @Override
    protected void onResume() {
        super.onResume();
        clothDAO.openDBWritable();
        Log.d(TAG,"abriu o DB");
    }

    @Override
    protected void onPause() {
        super.onPause();
        clothDAO.closeDB();
        Log.d(TAG,"fechou o DB");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addclothes_bt_add:
                Log.d(TAG,"Entrou no onClick do BT ADD");
                if (!imageSelected){
                    alertForImage();
                } else {
                    addToDatabase();
                }
                return;
            case R.id.addclothes_iv_picture:
                alertForChooseCameraGallery();
                break;
            case R.id.addclothes_bt_shirt:
                onTypeSelected(Cloth.TYPE_SHIRT);
                break;
            case R.id.addclothes_bt_pants:
                onTypeSelected(Cloth.TYPE_PANT);
                break;
            case R.id.addclothes_bt_underwear:
                onTypeSelected(Cloth.TYPE_UNDERWEAR);
                break;
            case R.id.addclothes_bt_accessories:
                onTypeSelected(Cloth.TYPE_ACCESSORIE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()){
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }
            if (!f.exists()){
                toast("Error while capturing image");
                return;
            }
            try {
                Uri uri = Uri.fromFile(f);
                CropImage.activity(uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setAspectRatio(1,1)
                        .setFixAspectRatio(true)
                        .start(this);
            } catch (Exception e){
                e.printStackTrace();
            }

        } else if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ) {
            filePath = data.getData();
            CropImage.activity(filePath)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setAspectRatio(1,1)
                    .setFixAspectRatio(true)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri uri = result.getUri();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ivPicture.setImageBitmap(bitmap);
                imageBitmap = bitmap;
                imageSelected = true;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addToDatabase(){
        if (typeSelected == 0){
            Log.d(TAG,"if o tipo não for selecionado");
            cvType.setCardBackgroundColor(getResources().getColor(R.color.red));
            toast(getResources().getString(R.string.select_a_type));
            Log.d(TAG,"não selecionou o tipo TONTO");
            return;
        }
        if (etName.getText().toString().isEmpty()){
            Log.d(TAG,"etnome tá vazio");
            etName.setText(getTypeString(typeSelected));
            Log.d(TAG,"agr ta preenchido");
        }

        Cloth c = new Cloth(
                AddClothes.this,
                etName.getText().toString(),
                typeSelected);
        Log.d(TAG,"criou uma new cloth");
        if (imageSelected){
            Log.d(TAG,"se a imagem foi selecionada");
            c.setPicture(imageBitmap);
            Log.d(TAG,"setou a imagem no cloth");
        } else {
            Log.d(TAG,"a imagem n foi selecionada");
            Bitmap icon = BitmapFactory.decodeResource(getResources(),R.mipmap.empty_camera);
            Log.d(TAG,"setou o icone");
            c.setPicture(icon);
            Log.d(TAG,"colocou o icone padrao no cloth");
        }
        clothDAO.insert(c);
        Log.d(TAG,"inseriu no banco o cloth");
        toast(getResources().getString(R.string.cloth_added_success));
        Log.d(TAG,"deu a toast de sucesso");
        startActivity(new Intent(AddClothes.this,MainActivity.class));
        Log.d(TAG,"deu a intent pra sair");
        finish();
    }

    private void alertForChooseCameraGallery(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AddClothes.this);
        alert.setTitle(getResources().getString(R.string.alert_choose_title));
        alert.setMessage(getResources().getString(R.string.alert_choose_message));
        alert.setPositiveButton(getResources().getString(R.string.camera), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                takePicture();
            }
        });
        alert.setNeutralButton(getResources().getString(R.string.gallery), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chooseImage();
            }
        });
        alert.setNegativeButton(getResources().getString(R.string.cancel),null);
        alert.show();
    }

    private void alertForImage(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AddClothes.this);
        alert.setTitle(getResources().getText(R.string.alert_image_title));
        alert.setMessage(getResources().getText(R.string.alert_image_message));
        alert.setPositiveButton(getResources().getText(R.string.alert_image_positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addToDatabase();
            }
        });
        alert.setNeutralButton(getResources().getText(R.string.add_an_image), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertForChooseCameraGallery();
            }
        });
        alert.setNegativeButton(getResources().getText(R.string.cancel),null);
        Log.d(TAG,"buildou a alert");
        alert.show();
    }

    private void onTypeSelected(int type) {
        switch (this.typeSelected) {
            case Cloth.TYPE_SHIRT:
                btShirt.setBackground(getResources().getDrawable(R.drawable.ic_shirt_96px));
                break;
            case Cloth.TYPE_PANT:
                btPants.setBackground(getResources().getDrawable(R.drawable.ic_pants_96px));
                break;
            case Cloth.TYPE_UNDERWEAR:
                btUnderwear.setBackground(getResources().getDrawable(R.drawable.ic_underwear_96px));
                break;
            case Cloth.TYPE_ACCESSORIE:
                btAccessories.setBackground(getResources().getDrawable(R.drawable.ic_accessories_96px));
                break;
            default:
                break;
        }
        cvType.setCardBackgroundColor(getResources().getColor(R.color.white));
        this.typeSelected = type;

        switch (type){
            case Cloth.TYPE_SHIRT:
                btShirt.setBackground(getResources().getDrawable(R.drawable.ic_shirt_primary_96px));
                break;
            case Cloth.TYPE_PANT:
                btPants.setBackground(getResources().getDrawable(R.drawable.ic_pants_primary_96px));
                break;
            case Cloth.TYPE_UNDERWEAR:
                btUnderwear.setBackground(getResources().getDrawable(R.drawable.ic_underwear_primary_96px));
                break;
            case Cloth.TYPE_ACCESSORIE:
                btAccessories.setBackground(getResources().getDrawable(R.drawable.ic_accessories_primary_96px));
                break;
        }
    }

    private String getTypeString(int type){
        switch (type) {
            case Cloth.TYPE_SHIRT:
                return getResources().getString(R.string.shirt);
            case Cloth.TYPE_PANT:
                return getResources().getString(R.string.pants);
            case Cloth.TYPE_UNDERWEAR:
                return getResources().getString(R.string.underwear);
            case Cloth.TYPE_ACCESSORIE:
                return getResources().getString(R.string.accessories);
        }
        return null;
    }

    private void takePicture() {
        Dexter.withActivity(this)
                .withPermission(android.Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(),"temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(f));
                        startActivityForResult(intent,CAMERA_REQUEST);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        toast(getResources().getString(R.string.permission_camera_denied));
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void chooseImage() {
        Dexter.withActivity(this)
                .withPermissions(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()){
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), PICK_IMAGE_REQUEST);
                            return;
                        }
                        if (report.isAnyPermissionPermanentlyDenied()){
                            toast(getResources().getString(R.string.permission_permanently_denied));
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }


    private void uploadImage() {
        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            toast("Uploaded");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            toast("Failed "+e.getMessage());
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
    }

    private void init(){
        etName = findViewById(R.id.addclothes_et_name);
        ivPicture = findViewById(R.id.addclothes_iv_picture);
        ivPicture.setOnClickListener(this);

        btShirt = findViewById(R.id.addclothes_bt_shirt);
        btShirt.setOnClickListener(this);

        btPants = findViewById(R.id.addclothes_bt_pants);
        btPants.setOnClickListener(this);

        btUnderwear = findViewById(R.id.addclothes_bt_underwear);
        btUnderwear.setOnClickListener(this);

        btAccessories = findViewById(R.id.addclothes_bt_accessories);
        btAccessories.setOnClickListener(this);

        btAdd = findViewById(R.id.addclothes_bt_add);
        btAdd.setOnClickListener(this);
        cvType = findViewById(R.id.addclothes_cv_types);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        clothDAO = new ClothDAO(AddClothes.this);
    }
}
