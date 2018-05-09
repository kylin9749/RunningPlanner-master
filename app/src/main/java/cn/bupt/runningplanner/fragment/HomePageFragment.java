package cn.bupt.runningplanner.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.bupt.runningplanner.R;
import cn.bupt.runningplanner.classes.HomePage.waterfulAdapter;
import cn.bupt.runningplanner.classes.HomePage.waterfulItem;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment {


    public HomePageFragment() {
        // Required empty public constructor
    }

    private List<waterfulItem> mItemList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        initWaterful();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        waterfulAdapter adapter = new waterfulAdapter(mItemList,this.getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }



    public void initWaterful(){
        for (int i=0;i<1;i++){
            waterfulItem cheery = new waterfulItem("愉快不妨试试跑步：让心情来决定训练方式",R.drawable.news11);
            mItemList.add(cheery);
            waterfulItem cheery1 = new waterfulItem("长期坚持跑步的13个益处 对号入座你中几个？",R.drawable.p1);
            mItemList.add(cheery1);
            waterfulItem cheery2 = new waterfulItem("跑步你是否知道：如果不科学饮水 会对身体造成伤害",R.drawable.p2);
            mItemList.add(cheery2);
            waterfulItem cheery3 = new waterfulItem("无热身or跑太快 跑步这6大坏习惯你有吗?",R.drawable.p3);
            mItemList.add(cheery3);
            waterfulItem cheery4 = new waterfulItem("为何人家跑姿漂亮又不受伤？ 关键做到了这两点",R.drawable.p4);
            mItemList.add(cheery4);
            waterfulItem cheery5 = new waterfulItem("朴树or王力宏？ 分享10首适合跑步时的歌曲！",R.drawable.p5);
            mItemList.add(cheery5);
            waterfulItem cheery6 = new waterfulItem("跑步为什么肺部会隐隐作痛 4个要点是不是忘了？",R.drawable.p6);
            mItemList.add(cheery6);
            waterfulItem cheery7 = new waterfulItem("跑者如何提升跑步效果 这四点你做到了没?",R.drawable.p7);
            mItemList.add(cheery7);
            waterfulItem cheery8 = new waterfulItem("千万别忽视，跑步中心率",R.drawable.p8);
            mItemList.add(cheery8);
            waterfulItem cheery9 = new waterfulItem("坚持每天慢跑40分钟,一个月后你会发现不一样的你",R.drawable.p9);
            mItemList.add(cheery9);
            waterfulItem cheery10 = new waterfulItem("如何制定完美训练计划",R.drawable.p10);
            mItemList.add(cheery10);
            waterfulItem cheery11 = new waterfulItem("浪漫海滩跑步隐藏危机 潮落后运动减少肌肉损伤",R.drawable.p11);
            mItemList.add(cheery11);
            waterfulItem cheery12 = new waterfulItem("保持健康助跑者实现自我突破 身体疲惫索性休息",R.drawable.p12);
            mItemList.add(cheery12);
            waterfulItem cheery13 = new waterfulItem("业余跑者如何快速进阶？ 详细训练日记反馈明显",R.drawable.p13);
            mItemList.add(cheery13);
            waterfulItem cheery14 = new waterfulItem("跑步后四个必做的伸展运动 发紧肌肉得到放松",R.drawable.p14);
            mItemList.add(cheery14);
            waterfulItem cheery15 = new waterfulItem("跑步后膝盖疼痛该如何处理? 休息+理疗必不可少",R.drawable.p15);
            mItemList.add(cheery15);
            waterfulItem cheery16 = new waterfulItem("跑马后如何恢复:2天内莫洗热水澡 彻底放松3-7天",R.drawable.p16);
            mItemList.add(cheery16);
            waterfulItem cheery17 = new waterfulItem("研究证实慢跑更有助长寿 剧烈运动或致死亡危机",R.drawable.p17);
            mItemList.add(cheery17);
            waterfulItem cheery18 = new waterfulItem("5km跑参赛补水指南：超45分钟完赛必须及时补水",R.drawable.p18);
            mItemList.add(cheery18);

        }
    }
}
