package willian.duarte.rachel.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import willian.duarte.rachel.R;

public class Cloth {
    public static final int TYPE_SHIRT = 1;
    public static final int TYPE_PANT = 2;
    public static final int TYPE_UNDERWEAR = 3;
    public static final int TYPE_ACCESSORIE = 4;

    private int id;
    private String name;
    private int type;
    private Bitmap picture;
    private ArrayList<Date> dates;
    private Context context;

    public Cloth(Context context, String name, int type) {
        this.name = name;
        this.type = type;
        this.context = context;
        dates = new ArrayList<>();
    }

    public Cloth(int id, String name, int type, Bitmap picture){
        this.id = id;
        this.name = name;
        this.type = type;
        this.picture = picture;
        dates = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates = dates;
    }

    public void addDate(Date date){
        this.dates.add(date);
    }

    public Drawable getTypeDrawable(){
        switch (type){
            case TYPE_SHIRT:
                return context.getResources().getDrawable(R.drawable.ic_shirt_96px);
            case TYPE_PANT:
                return context.getResources().getDrawable(R.drawable.ic_pants_96px);
            case TYPE_UNDERWEAR:
                return context.getResources().getDrawable(R.drawable.ic_underwear_96px);
            case TYPE_ACCESSORIE:
                return context.getResources().getDrawable(R.drawable.ic_accessories_96px);
        }
        return null;
    }

    public int getTypeDrawableId(){
        switch (type){
            case TYPE_SHIRT:
                return R.drawable.ic_shirt_primary_96px;
            case TYPE_PANT:
                return R.drawable.ic_pants_primary_96px;
            case TYPE_UNDERWEAR:
                return R.drawable.ic_underwear_primary_96px;
            case TYPE_ACCESSORIE:
                return R.drawable.ic_accessories_primary_96px;
            default:
                return R.drawable.ic_shirt_96px;
        }
    }

    @Override
    public String toString() {
        return "Cloth{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", picture='" + picture + '\'' +
                ", dates=" + dates +
                '}';
    }
}
