package hackathon.rajasthan.rajasthantourism.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import hackathon.rajasthan.rajasthantourism.Interfaces.ItemClickListener;
import hackathon.rajasthan.rajasthantourism.ProductActivity;
import hackathon.rajasthan.rajasthantourism.R;
import hackathon.rajasthan.rajasthantourism.model.Type;

/**
 * Created by MY on 15-03-2018.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private Context context;
    private List<Type> listCategories = new ArrayList<>();

    public class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mName;
        ImageView mDp;
        ItemClickListener itemClickListener;


        public CategoriesViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.txtCategory);
            mDp = itemView.findViewById(R.id.imgDownload);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

    }

    public CategoriesAdapter(List<Type> listCategories, Context context) {
        this.listCategories = listCategories;
        this.context = context;

    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.categories_item, parent, false);
        return new CategoriesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, final int position) {

        for (int i=0;i<listCategories.size();i++){
            Log.d("typeLi",listCategories.get(i).getName());
        }


        if (listCategories.get(position).getName()!=null){
        holder.mName.setText(listCategories.get(position).getName());}

        if (listCategories.get(position).getDpurl()!=null){
        Uri uri = Uri.parse(listCategories.get(position).getDpurl());
        Glide.with(context)
                .load(uri)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mDp);}
        final Type clickitem = listCategories.get(position);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, ProductActivity.class);

                intent.putExtra("TypeName", clickitem.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(CategoriesViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.mDp.setBackground(ContextCompat.getDrawable(context, R.drawable.image_circle_colourless));
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }
}
