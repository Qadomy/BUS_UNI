package com.example.bus_uni;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_uni.Register.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final PostAdapterOnClickHandler mClickHandler;
    private final Context context;
    private ArrayList<Post> posts = new ArrayList<>();

    public PostAdapter(Context context, PostAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int id = R.layout.post_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean attachImmediatelyToViewGroup = false;
        View view = inflater.inflate(id, viewGroup, attachImmediatelyToViewGroup);
        return new PostViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder PostViewHolder, int position) {
        Post post = posts.get(position);
        PostViewHolder.postName.setText(post.getText());
        PostViewHolder.date.setText(post.getDate());
        PostViewHolder.userName.setText(post.getUser_name());
        //TODO: UserImage (Circular) doesn't appear-,-
        Toast.makeText(context, post.getUser_image(), Toast.LENGTH_LONG).show();
        if (post.getImage_url() != null && !post.getImage_url().isEmpty()) {
            Picasso.with(context)
                    .load(post.getImage_url())
                    .into(PostViewHolder.postImage);
        }
    //    if (post.getUser_image() != null && !post.getUser_image().isEmpty()) {
            Picasso.with(context)
                    .load(post.getUser_image())
                    .into(PostViewHolder.userImage);
       // }

    }

    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        return posts.size();
    }

    public void setPostsData(ArrayList<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public interface PostAdapterOnClickHandler {
        void onClick(Post post);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView postName, date, userName;
        final ImageView postImage, userImage;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postName = itemView.findViewById(R.id.post_content);
            postImage = itemView.findViewById(R.id.post_pic);
            userImage = itemView.findViewById(R.id.circular_user_image);
            date = itemView.findViewById(R.id.post_date);
            userName = itemView.findViewById(R.id.User_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Post Post = posts.get(adapterPosition);
            mClickHandler.onClick(Post);
        }
    }
}