package com.aafilogic.fc6.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.activity.PersonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LikeUserAdapter extends RecyclerView.Adapter<LikeUserAdapter.MyViewHolder> {

    public List<PersonUtils> LPersonUtils;
    public String user_id, token;
    private Context context;


    public LikeUserAdapter(Context context, List personUtils) {
        this.context = context;
        this.LPersonUtils = personUtils;
        Log.e("getLike",String.valueOf(personUtils.size()));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.like_user_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PersonUtils pu = LPersonUtils.get(position);
        Log.e("getLike_user_name",pu.getLike_user_name());

        holder.post_user_name.setText(pu.getLike_user_name());
        Picasso.with(context).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + pu.getLike_user_image()).placeholder( R.drawable.progress_animation ).into(holder.postuserimage);

    }

    @Override
    public int getItemCount() {
        return LPersonUtils.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView post_user_name;
        ImageView  postuserimage;
        public MyViewHolder(View itemView) {
            super(itemView);

            postuserimage = (ImageView) itemView.findViewById(R.id.postuserimage);
            post_user_name = (TextView) itemView.findViewById(R.id.post_user_name);

        }
    }
}
