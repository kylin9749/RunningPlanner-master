package cn.bupt.runningplanner.classes.HomePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bupt.runningplanner.Dialog;
import cn.bupt.runningplanner.MainActivity;
import cn.bupt.runningplanner.R;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;
import static java.security.AccessController.getContext;

public class waterfulAdapter extends RecyclerView.Adapter<waterfulAdapter.ViewHolder> {

    private List<waterfulItem> mItemList;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        public ViewHolder(View view){
            super(view);
            imageView=(ImageView) view.findViewById(R.id.news_image);
            textView=(TextView) view.findViewById(R.id.news_title);
        }
    }

    public waterfulAdapter(List<waterfulItem> ItemList,Context context){
        mItemList=ItemList;
        mContext = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.waterful_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                waterfulItem fruit = mItemList.get(position);

                Intent intent=new Intent(mContext,Dialog.class);//你要跳转的界面
                mContext.startActivity(intent);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                waterfulItem fruit = mItemList.get(position);
               // Toast.makeText(v.getContext(), "you clicked image " + fruit.getName(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext,Dialog.class);//你要跳转的界面
                mContext.startActivity(intent);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        waterfulItem item = mItemList.get(position);
        holder.imageView.setImageResource(item.getImageId());
        holder.textView.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


}
