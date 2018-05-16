package cn.bupt.runningplanner.classes.HomePage.history;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.imageutils.BitmapUtil;

import cn.bupt.runningplanner.Dialog;
import cn.bupt.runningplanner.R;
import cn.bupt.runningplanner.Util.bitmapUtil;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private List<HistoryItem> mitemList;
    private Context mContext ;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView date;
        TextView time;
        TextView distance;
        ImageView route;

        public ViewHolder(View view){
            super(view);
            date = (TextView)view.findViewById(R.id.date);
            time = (TextView)view.findViewById(R.id.timee);
            distance = (TextView)view.findViewById(R.id.distance);
            route = (ImageView) view.findViewById(R.id.route);
        }
    }

    public HistoryAdapter(List<HistoryItem> ItemList, Context context){
        mitemList=ItemList;
        mContext = context;
    }
    public void addList(List mitemList){
        this.mitemList=mitemList;
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
        String date = item.getCurrentTime();

        String route_string = item.getRoute();

        //holder.route.setImageResource(R.drawable.news11);
        bitmapUtil.setImageBitmapKylin(route_string,holder.route);
        holder.distance.setText(new Double(distance).toString());
        holder.time.setText(new Long(time).toString());
        holder.date.setText(date);

        }

    @Override
    public int getItemCount() {
        return mitemList.size();
    }
}

