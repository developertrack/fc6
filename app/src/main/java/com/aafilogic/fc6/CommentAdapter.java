package com.aafilogic.fc6;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.activity.AppController;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.PersonUtils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private static final String TAG_T = "Comment Adapter";
    public List<PersonUtils> personUtils;

    public String user_id, token;
    private Context context;

    public CommentAdapter(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setTag(personUtils.get(position));

        try {


            final SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
            user_id = pref.getString("user_id", "");


            final PersonUtils pu = personUtils.get(position);


            if (pu.getUserid().equals(user_id)) {

                holder.delete_post_img.setVisibility(View.VISIBLE);

            } else {

                holder.delete_post_img.setVisibility(View.GONE);

            }


            holder.tv_commenter_name.setText(pu.getCommenter_name());

            holder.tv_comment_text.setText("  " + pu.getComment_text() + "  ");


            Picasso.with(context).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + pu.getComment_img()).placeholder( R.drawable.progress_animation ).into(holder.img_comment_img);


            holder.delete_post_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {


                        token = pref.getString("token", "");


                        String tag_string_req = "req_POST";

                        String url = "https://www.aafilogicinfotech.com/demo/FC6/api/delete_comment.php";


                        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String result = response.toString();
                                // loading.dismiss();


                                Toast.makeText(context, result, Toast.LENGTH_LONG).show();

                                    personUtils.remove(position);
                                    notifyItemRemoved(position);
                                    notifyDataSetChanged();



                                try {


                                    JSONObject jObj = new JSONObject(response);


                                } catch (JSONException e) {
                                    // JSON error
                                    e.printStackTrace();
//                                    Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }


                            }
                        }, new Response.ErrorListener()

                        {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
//                                Toast.makeText(context,
//                                        error.getMessage(), Toast.LENGTH_LONG).show();
                                personUtils.remove(position);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                                //  hideDialog();
                            }
                        }

                        ) {


                            @Override
                            protected Map<String, String> getParams() {
                                // Posting parameters to login url
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("user_id", user_id);
                                params.put("token", token);
                                params.put("comment_id", pu.getComment_id());
                                return params;


                            }


                        };


                        // Adding request to request queue
                        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


                    } catch (Exception e) {


                        e.printStackTrace();
                    }


                }
            });


        } catch (Exception e) {

            e.printStackTrace();
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
        ImageView post_image, post_user_image, like_icon, comment_icon, img_comment_img, delete_post_img;
        Button add;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_commenter_name = (TextView) itemView.findViewById(R.id.commenter_name);
            tv_comment_text = (TextView) itemView.findViewById(R.id.comment_text);

            img_comment_img = (ImageView) itemView.findViewById(R.id.commenter_image);

            delete_post_img = (ImageView) itemView.findViewById(R.id.delete_comment);


            //  pJobProfile = (TextView) itemView.findViewById(R.id.pJobProfiletxt);

            /**
             add.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {


            PersonUtils cpu = (PersonUtils) view.getTag();




            // Toast.makeText(view.getContext(),"hello add to cart option",Toast.LENGTH_LONG).show();

            //  String data=cpu.getSupp_name()+"\n"+cpu.getCutP()+"\n"+cpu.getTotalP()+"\n"+cpu.getDiscountP()+"\n"+cpu.getQuantity()+"\n"+cpu.getImg_url()+"\n"+cpu.getOverview();

            /** try{




            Intent i=new Intent(context,Cart.class);
            i.putExtra("itemname",cpu.getSupp_name());
            i.putExtra("itemprice",cpu.getTotalP());
            i.putExtra("itemquantity","1");
            i.putExtra("totalprice",cpu.getTotalP());
            i.putExtra("image", "http://mygrobo.com/uploads/product/" + cpu.getImg_url());
            context.startActivity(i);




            }catch (Exception e){

            e.printStackTrace();
            }





            }
            });
             **/


            itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {


                                                /**
                                                 PersonUtils cpu = (PersonUtils) view.getTag();






                                                 //   Toast.makeText(view.getContext(), "ID is : "+cpu.getId()+"\n"+cpu.getSupp_name(), Toast.LENGTH_SHORT).show();



                                                 SharedPreferences prefs = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                                 String token = prefs.getString("token", null);
                                                 String customer_id = prefs.getString("customer_id", null);








                                                 Intent intent = new Intent(context, Product_Detail.class);
                                                 intent.putExtra("id",cpu.getId());
                                                 intent.putExtra("customer_id",customer_id);
                                                 intent.putExtra("token",token);
                                                 context.startActivity(intent);


                                                 **/
                                                /**


                                                 Toast.makeText(view.getContext(), cpu.getPname(), Toast.LENGTH_SHORT).show();

                                                 Toast.makeText(view.getContext(), cpu.getPrice(), Toast.LENGTH_SHORT).show();

                                                 Toast.makeText(view.getContext(), cpu.getBrand(), Toast.LENGTH_SHORT).show();

                                                 Toast.makeText(view.getContext(), cpu.getOverview(), Toast.LENGTH_SHORT).show();

                                                 Toast.makeText(view.getContext(), cpu.getLocation(), Toast.LENGTH_SHORT).show();


                                                 **/


                                                //  Toast.makeText(view.getContext(), cpu.getSupp_name(), Toast.LENGTH_SHORT).show();

                                                // holder.price.setText(pu.getPrice());
                                                // holder.location.setText(pu.getLocation());


                                                /**
                                                 *
                                                 *
                                                 Intent intent = new Intent(context, Product_Detail_Page.class);
                                                 intent.putExtra("pname",cpu.getSupp_name());
                                                 intent.putExtra("cut_price",cpu.getCutP());
                                                 intent.putExtra("total_price",cpu.getTotalP());
                                                 intent.putExtra("disc_price",cpu.getDiscountP());
                                                 intent.putExtra("quantity",cpu.getQuantity());
                                                 intent.putExtra("img_url",cpu.getImg_url());
                                                 intent.putExtra("description",cpu.getOverview());

                                                 context.startActivity(intent);
                                                 **/

                                                //     Toast.makeText(view.getContext(),cpu.getSupp_name()+"\n"+cpu.getCutP()+"\n"+cpu.getTotalP()+"\n"+cpu.getDiscountP()+"\n"+cpu.getQuantity()+"\n"+cpu.getImg_url()+"\n"+cpu.getOverview(),Toast.LENGTH_LONG).show();


                                            }
                                        }

            );

        }
    }
}

