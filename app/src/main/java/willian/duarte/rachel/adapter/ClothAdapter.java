package willian.duarte.rachel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import willian.duarte.rachel.R;
import willian.duarte.rachel.model.Cloth;

public class ClothAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Cloth> clothes;
    private static ClickListener clickListener;

    public ClothAdapter(Context context, ArrayList<Cloth> pessoas) {
        this.context = context;
        this.clothes = pessoas;
    }

    public void setClothes(ArrayList<Cloth> clothes){
        this.clothes = clothes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.clothes_row,parent,false);

        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder h = (ViewHolder) holder;

        Cloth c = clothes.get(position);
        Log.e("LogsClothAdapter", "Tá setando a posição "+position+" dessa porra...");
        h.ivPicture.setImageBitmap(c.getPicture());
        h.ivType.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),c.getTypeDrawableId()));
        h.ivType.setBackgroundColor(context.getResources().getColor(R.color.white));
    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private final ImageView ivPicture;
        private final ImageView ivType;
        private final CardView cvCard;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            ivPicture = itemView.findViewById(R.id.row_iv_picture);
            ivType = itemView.findViewById(R.id.row_iv_type);
            cvCard = itemView.findViewById(R.id.row_cv_cardview);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(view,getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(view,getAdapterPosition());
            return true;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener){
        ClothAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
        void onItemLongClick(View v, int position);
    }

}//fecha classe