package com.example.taskmasterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmasterapp.Models.TaskModule;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<TaskModule> tasksList ;
    private RecyclerViewClickListener listener;



    public RecyclerAdapter(List<TaskModule> tasksList, RecyclerViewClickListener listener) {
        this.tasksList = tasksList;
        this.listener = listener;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskModule t = tasksList.get(position);
        holder.titleTextView.setText(t.getTitle());
        holder.bodyTextView.setText(t.getBody());
        holder.stateTextView.setText(t.getState());

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titleTextView;
        TextView bodyTextView;
        TextView stateTextView;



        public ViewHolder( View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            bodyTextView = itemView.findViewById(R.id.bodyTextView);
            stateTextView = itemView.findViewById(R.id.stateTextView);
            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {
            listener.onClick(itemView, getBindingAdapterPosition());

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

}
