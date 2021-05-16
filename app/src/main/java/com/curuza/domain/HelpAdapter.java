package com.curuza.domain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.curuza.R;
import com.curuza.data.help.Help;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder>  {

    private List<Help> mHelpList;
    private Help help;
    private Context mContext;
    private OnItemListener mOnitemListener;

    public interface OnItemListener{
        void onItemClick(int position);
    }
    public HelpAdapter(List<Help> helpList, Context mContext,OnItemListener OnitemListener) {
        this.mHelpList = helpList;
        this.mContext = mContext;
        this.mOnitemListener = OnitemListener;
    }

    public void setHelpList(List<Help> helpList) {
        mHelpList = helpList;
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_item,parent,false);
        return new MyViewHolder(v,mOnitemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Help help = mHelpList.get(position);
        holder.pict.setImageResource(help.getmHelpImage());
        holder.name.setText(help.getName());
        Log.d(HelpAdapter.class.getSimpleName(), "Help = " + help);

    }

    @Override
    public int getItemCount() {
        return mHelpList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView pict;
        public LinearLayout container;
        OnItemListener mOnItemListener;
        private final Context context;

        public MyViewHolder(@NonNull View itemView,OnItemListener onitemListener) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.name);
            pict = itemView.findViewById(R.id.img_help);
            container= itemView.findViewById(R.id.container);
            mOnItemListener =  onitemListener;

        }


    }
}
