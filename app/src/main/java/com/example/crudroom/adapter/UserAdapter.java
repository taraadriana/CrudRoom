package com.example.crudroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudroom.R;
import com.example.crudroom.database.entitas.User;

import java.util.List;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.ViewAdapter>{
    //buat list yg diambil dari entitas
    private List<User> list;
    private Context context;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int position);
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public UserAdapter(Context context, List<User> list){
        this.context = context;
        this.list = list;
    }

    //membuat constructor

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_user, parent, false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, int position) {
        holder.name.setText(list.get(position).nama);
        holder.nim.setText(String.valueOf(list.get(position).nim));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewAdapter extends RecyclerView.ViewHolder{
        TextView name, nim;

        public ViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rowName);
            nim = itemView.findViewById(R.id.rowNim);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dialog != null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
