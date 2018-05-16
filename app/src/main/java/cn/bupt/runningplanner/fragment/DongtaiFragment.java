package cn.bupt.runningplanner.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.bupt.runningplanner.R;
import cn.bupt.runningplanner.classes.HomePage.DongtaiAdapter;
import cn.bupt.runningplanner.classes.HomePage.DongtaiItem;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DongtaiFragment extends Fragment {


    public DongtaiFragment() {
        // Required empty public constructor
    }

    private List<DongtaiItem> mItemList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dongtai, container, false);

        initWaterful();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DongtaiAdapter adapter = new DongtaiAdapter(mItemList,this.getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }



    public void initWaterful(){
        for (int i=0;i<1;i++){
            DongtaiItem cheery = new DongtaiItem("愉快不妨试试跑步：让心情来决定训练方式",R.drawable.news11);
            mItemList.add(cheery);
            DongtaiItem cheery1 = new DongtaiItem("长期坚持跑步的13个益处 对号入座你中几个？",R.drawable.news21);
            mItemList.add(cheery1);
            DongtaiItem cheery2 = new DongtaiItem("运动后浑身酸痛怎么办？| 吴栋说跑步",R.drawable.news31);
            mItemList.add(cheery2);
            DongtaiItem cheery3 = new DongtaiItem("跑步你是否知道：如果不科学饮水 会对身体造成伤害",R.drawable.news41);
            mItemList.add(cheery3);
            DongtaiItem cheery4 = new DongtaiItem("无热身or跑太快 跑步这6大坏习惯你有吗？",R.drawable.news51);
            mItemList.add(cheery4);
            DongtaiItem cheery5 = new DongtaiItem("为何人家跑姿漂亮又不受伤？ 关键做到了这两点",R.drawable.news61);
            mItemList.add(cheery5);
            DongtaiItem cheery6 = new DongtaiItem("朴树or王力宏？ 分享10首适合跑步时的歌曲！",R.drawable.news71);
            mItemList.add(cheery6);
            DongtaiItem cheery7 = new DongtaiItem("跑步为什么肺部会隐隐作痛 4个要点是不是忘了？",R.drawable.news81);
            mItemList.add(cheery7);
            DongtaiItem cheery8 = new DongtaiItem("跑者如何提升跑步效果 这四点你做到了没？",R.drawable.news91);
            mItemList.add(cheery8);

        }
    }
}
