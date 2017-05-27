package edu.salleurl.ls30394.foodfinderapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.R;

/**
 * Created by avoge on 25/05/2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private Context context;

    private String username;

    private List<String> comments;


    public CommentsAdapter(Context c, String username){
        this.context = c;
        this.username = username;

        comments = new ArrayList<>();
    }

    public void addComment(String comment){
        comments.add(comment);
        notifyDataSetChanged();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {

        holder.comment_user.setText("@"+username+":");
        holder.comment_text.setText(comments.get(position));

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    /**
     * Clase auxiliar que define un ViewHolder de comentarios para nuestra RecyclerView
     */
    static class CommentViewHolder extends RecyclerView.ViewHolder {

        protected TextView comment_user;

        protected TextView comment_text;

        public CommentViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false));

            comment_text = (TextView) itemView.findViewById(R.id.comment_text);
            comment_user = (TextView) itemView.findViewById(R.id.comment_user);
        }
    }
}
