package com.example.ionut.infoschedule.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ionut.infoschedule.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeachersAdapter extends RecyclerView.Adapter<TeachersAdapter.ViewHolder> {
    private ArrayList<String> data;

    public TeachersAdapter(ArrayList<String> data){
        this.data = data;
    }

    @NonNull
    @Override
    public TeachersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_adapter_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeachersAdapter.ViewHolder adapter, int i) {
        adapter.bind();
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_)
        TextView tvTeacherName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(){
            int position = getAdapterPosition();
            tvTeacherName.setText(data.get(position));
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Click " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}
