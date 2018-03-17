package hackathon.rajasthan.rajasthantourism.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
import hackathon.rajasthan.rajasthantourism.MainActivity;
import hackathon.rajasthan.rajasthantourism.Model.Constants;
import hackathon.rajasthan.rajasthantourism.Model.Destination;
import hackathon.rajasthan.rajasthantourism.R;

/**
 * Created by MY on 15-03-2018.
 */

public class DestinationsAdapter extends RecyclerView.Adapter<DestinationsAdapter.DestinationsViewHolder> {

    private Context context;
    private List<Destination> listDestinations = new ArrayList<>();

    public class DestinationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mName;
        ImageView mDp;
        ItemClickListener itemClickListener;


        public DestinationsViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.card_name);
            mDp = itemView.findViewById(R.id.card_dp);

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

    public DestinationsAdapter(List<Destination> listDestinations, Context context) {
        this.listDestinations = listDestinations;
        this.context = context;

    }

    @Override
    public DestinationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.destination_item, parent, false);
        return new DestinationsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DestinationsViewHolder holder, final int position) {

        if(Constants.currentCity.equals(listDestinations.get(position).getName())){
            holder.mDp.setBackground(ContextCompat.getDrawable(context, R.drawable.image_circle_coloured));
        }
        holder.mName.setText(listDestinations.get(position).getName());
        Uri uri = Uri.parse(listDestinations.get(position).getDpUrl());

        Glide.with(context)
                .load(uri)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mDp);
        final Destination clickitem = listDestinations.get(position);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Constants.currentCity = clickitem.getName();
                ((MainActivity)context).refreshDestination();
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(DestinationsViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.mDp.setBackground(ContextCompat.getDrawable(context, R.drawable.image_circle_colourless));
    }

    @Override
    public int getItemCount() {
        return listDestinations.size();
    }
}
