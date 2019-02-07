package com.aafilogic.fc6;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aafilogic.fc6.activity.PersonUtils;

import java.util.List;


public class RankingTeamAdapter extends RecyclerView.Adapter<RankingTeamAdapter.ViewHolder> {

    public List<PersonUtils> personUtils;
    private Context context;

    public RankingTeamAdapter(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(personUtils.get(position));

        try {


            final PersonUtils pu = personUtils.get(position);


            // Picasso.with(context).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + pu.getUser_image()).into(holder.user_image);


            holder.user_name.setText(pu.getUser_name());
            holder.workouts.setText("Workouts :  " + pu.getWorkout_count());
            holder.meals.setText("Meals :  " + pu.getMeal_count());
            holder.tv_team_points.setText(pu.getTotal_points());
            holder.tv_rank.setText("#"+String.valueOf(Integer.valueOf(position)+1));


        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return personUtils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView team_name, user_name, post_time, workouts, meals, discp, quantity, tv_rank, tv_team_points;
        //   public TextView pJobProfile;
        ImageView post_image, user_image;
        Button add;


        public ViewHolder(View itemView) {
            super(itemView);

            //  user_image = (ImageView) itemView.findViewById(R.id.user_image);


            user_name = (TextView) itemView.findViewById(R.id.user_name);
            workouts = (TextView) itemView.findViewById(R.id.workouttext);
            meals = (TextView) itemView.findViewById(R.id.mealno);


            tv_rank = (TextView) itemView.findViewById(R.id.tv_rank);
            tv_team_points = (TextView) itemView.findViewById(R.id.team_points);


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

