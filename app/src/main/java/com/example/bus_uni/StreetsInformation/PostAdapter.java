package com.example.bus_uni.StreetsInformation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bus_uni.Booking.BookedTicket_Adapter;
import com.example.bus_uni.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private ArrayList<Post> posted = new ArrayList<>();
    private Context mContext;

    public PostAdapter(ArrayList<Post> posted, Context mContext) {
        this.posted = posted;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.post_item, viewGroup, false);

        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {


        viewHolder.userName.setText(posted.get(position).getUserName());
        viewHolder.postText.setText(posted.get(position).getPostText());
        viewHolder.postDateTime.setText(posted.get(position).getPostDate());

        Picasso.with(mContext).load(posted.get(position).getUserImage()).into(viewHolder.userImage);
        Picasso.with(mContext).load(posted.get(position).getPostImage()).into(viewHolder.postImage);

    }

    @Override
    public int getItemCount() {
        if (posted == null)
            return 0;
        return posted.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        CircleImageView userImage;
        TextView userName, postDateTime, postText;
        ImageView postImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            cardView = (CardView) itemView.findViewById(R.id.postCardView);
            userImage = (CircleImageView)itemView.findViewById(R.id.circular_user_image);
            userName = (TextView)itemView.findViewById(R.id.User_name);
            postDateTime = (TextView)itemView.findViewById(R.id.post_date);
            postText = (TextView)itemView.findViewById(R.id.post_content);
            postImage=(ImageView)itemView.findViewById(R.id.post_pic);
        }
    }
}
