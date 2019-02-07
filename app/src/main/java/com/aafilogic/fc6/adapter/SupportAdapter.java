package com.aafilogic.fc6.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.PersonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SupportAdapter extends RecyclerView.Adapter<SupportAdapter.ViewHolder> {

    private static final String TAG_T = "Comment Adapter";
    public List<PersonUtils> personUtils;

    public String user_id, token;
    private Context context;

    public SupportAdapter(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;

    }


    @Override
    public SupportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_support, parent, false);
        SupportAdapter.ViewHolder viewHolder = new SupportAdapter.ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final SupportAdapter.ViewHolder holder, final int position) {
        holder.itemView.setTag(personUtils.get(position));

        try {
            SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);

            String user_image = pref.getString("user_image", "");

            final String username = pref.getString("user_name", "");
            final PersonUtils pu = personUtils.get(position);

            if(pu.getCommenter_name().equals("0")){
                holder.tv_commenter_name.setText(username);
            }else{
                holder.tv_commenter_name.setText("Admin");

            }

            holder.tv_comment_text.setText("  " + pu.getComment_text() + "  ");


            holder.post_time.setText(pu.getPost_add_time());

            if(pu.getCommenter_name().equals("0")){
                Picasso.with(context).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image).placeholder( R.drawable.progress_animation ).into(holder.img_comment_img);

//                holder.tv_commenter_name.setText("Admin");
            }else{

            }

        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return personUtils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_commenter_name, tv_comment_text, post_text, post_user_name, post_time, like_count, comment_count, discp, quantity;
        public String commenter_name;
        public String comment_text;
        public String comment_img;
        //   public TextView pJobProfile;
        ImageView post_image, post_user_image, like_icon, comment_icon, img_comment_img, delete_post_img,open_page;
        Button add;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_commenter_name = (TextView) itemView.findViewById(R.id.messagename);
            tv_comment_text = (TextView) itemView.findViewById(R.id.messsagedetail);

            post_time = (TextView) itemView.findViewById(R.id.msg_date);

            img_comment_img = (ImageView) itemView.findViewById(R.id.notificationimage);


        }
    }
}

