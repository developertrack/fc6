package com.aafilogic.fc6;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aafilogic.fc6.activity.PersonUtils;
import com.aafilogic.fc6.activity.teamdetailActivty;

import java.util.List;


public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    public List<PersonUtils> personUtils;
    private Context context;

    public TeamAdapter(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teamlistdetail, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(personUtils.get(position));

        try {


            final PersonUtils pu = personUtils.get(position);

            holder.team_name.setText("Team #FC6" + pu.getTeam_name());


            holder.team_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent tm_int = new Intent(context, teamdetailActivty.class);
                    tm_int.putExtra("group_id", pu.getTeam_name());
                    tm_int.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(tm_int);


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

        public TextView team_name, post_user_name, post_time, like_count, comment_count, discp, quantity;
        //   public TextView pJobProfile;
        ImageView post_image, post_user_image;
        Button add;

        public ViewHolder(View itemView) {
            super(itemView);

            team_name = (TextView) itemView.findViewById(R.id.teamname);


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

