package willian.duarte.rachel.model;

import java.util.ArrayList;

public class Cloth {
    private static final int TYPE_SHIRT = 0;
    private static final int TYPE_PANT = 1;
    private static final int TYPE_UNDERWEAR = 2;
    private static final int TYPE_ACCESSORIE = 3;

    private String name;
    private int type;
    private String picture;
    private ArrayList<Date> dates;

    public Cloth(String name, int type, String picture) {
        this.name = name;
        this.type = type;
        this.picture = picture;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
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
