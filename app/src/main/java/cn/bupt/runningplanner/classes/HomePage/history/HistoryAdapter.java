package cn.bupt.runningplanner.classes.HomePage.history;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bupt.runningplanner.Dialog;
import cn.bupt.runningplanner.R;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private List<HistoryItem> mitemList = null;
    private Context mContext = null;
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView date;
        TextView time;
        TextView distance;
        public ViewHolder(View view){
            super(view);
            date = (TextView)view.findViewById(R.id.date);
            time = (TextView)view.findViewById(R.id.timee);
            distance = (TextView)view.findViewById(R.id.distance);
        }
    }

    public HistoryAdapter(List<HistoryItem> ItemList, Context context){
        mitemList=ItemList;
        mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historyitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryItem item = mitemList.get(position);

        double distance = item.getDistance()/1000;
        long time = item.getTime()/60000;
        long currentTime = item.getCurrentTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(currentTime);

        holder.distance.setText(new Double(distance).toString());
        holder.time.setText(new Long(time).toString());
        holder.date.setText(formatter.format(date));
        }

    @Override
    public int getItemCount() {
        return mitemList.size();
    }
}

