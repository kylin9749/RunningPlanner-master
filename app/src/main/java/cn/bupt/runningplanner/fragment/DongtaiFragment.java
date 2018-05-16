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
            DongtaiItem cheery1 = new DongtaiItem("长期坚持跑步的13个益处 对号入座你中几个？",R.drawable.p1);
            mItemList.add(cheery1);
            DongtaiItem cheery2 = new DongtaiItem("跑步你是否知道：如果不科学饮水 会对身体造成伤害",R.drawable.p2);
            mItemList.add(cheery2);
            DongtaiItem cheery3 = new DongtaiItem("无热身or跑太快 跑步这6大坏习惯你有吗?",R.drawable.p3);
            mItemList.add(cheery3);
            DongtaiItem cheery4 = new DongtaiItem("为何人家跑姿漂亮又不受伤？ 关键做到了这两点",R.drawable.p4);
            mItemList.add(cheery4);
            DongtaiItem cheery5 = new DongtaiItem("朴树or王力宏？ 分享10首适合跑步时的歌曲！",R.drawable.p5);
            mItemList.add(cheery5);
            DongtaiItem cheery6 = new DongtaiItem("跑步为什么肺部会隐隐作痛 4个要点是不是忘了？",R.drawable.p6);
            mItemList.add(cheery6);
            DongtaiItem cheery7 = new DongtaiItem("跑者如何提升跑步效果 这四点你做到了没?",R.drawable.p7);
            mItemList.add(cheery7);
            DongtaiItem cheery8 = new DongtaiItem("千万别忽视，跑步中心率",R.drawable.p8);
            mItemList.add(cheery8);
            DongtaiItem cheery9 = new DongtaiItem("坚持每天慢跑40分钟,一个月后你会发现不一样的你",R.drawable.p9);
            mItemList.add(cheery9);
            DongtaiItem cheery10 = new DongtaiItem("如何制定完美训练计划",R.drawable.p10);
            mItemList.add(cheery10);
            DongtaiItem cheery11 = new DongtaiItem("浪漫海滩跑步隐藏危机 潮落后运动减少肌肉损伤",R.drawable.p11);
            mItemList.add(cheery11);
            DongtaiItem cheery12 = new DongtaiItem("保持健康助跑者实现自我突破 身体疲惫索性休息",R.drawable.p12);
            mItemList.add(cheery12);
            DongtaiItem cheery13 = new DongtaiItem("业余跑者如何快速进阶？ 详细训练日记反馈明显",R.drawable.p13);
            mItemList.add(cheery13);
            DongtaiItem cheery14 = new DongtaiItem("跑步后四个必做的伸展运动 发紧肌肉得到放松",R.drawable.p14);
            mItemList.add(cheery14);
            DongtaiItem cheery15 = new DongtaiItem("跑步后膝盖疼痛该如何处理? 休息+理疗必不可少",R.drawable.p15);
            mItemList.add(cheery15);
            DongtaiItem cheery16 = new DongtaiItem("跑马后如何恢复:2天内莫洗热水澡 彻底放松3-7天",R.drawable.p16);
            mItemList.add(cheery16);
            DongtaiItem cheery17 = new DongtaiItem("研究证实慢跑更有助长寿 剧烈运动或致死亡危机",R.drawable.p17);
            mItemList.add(cheery17);
            DongtaiItem cheery18 = new DongtaiItem("5km跑参赛补水指南：超45分钟完赛必须及时补水",R.drawable.p18);
            mItemList.add(cheery18);

        }
    }
}
