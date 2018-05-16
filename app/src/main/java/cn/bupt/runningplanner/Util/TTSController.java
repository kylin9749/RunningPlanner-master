package cn.bupt.runningplanner.Util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.autonavi.tbt.TrafficFacilityInfo;

import java.util.LinkedList;

/**
 * 当前DEMO的播报方式是队列模式。其原理就是依次将需要播报的语音放入链表中，播报过程是从头开始依次往后播报。
 */
public class TTSController implements AMapNaviListener, ICallBack {


    @Override
    public void onCompleted(int code) {
        if (handler != null) {
            handler.obtainMessage(1).sendToTarget();
        }
    }


    public static TTSController ttsManager;
    private Context mContext;
    private SystemTTS systemTTS;
    private LinkedList<String> wordList = new LinkedList<String>();
    private final int TTS_PLAY = 1;
    private final int CHECK_TTS_PLAY = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TTS_PLAY:
                    if (wordList.size() > 0) {
                        systemTTS.playText(wordList.removeFirst());
                    }
                    break;
                case CHECK_TTS_PLAY:
                    if (!systemTTS.isPlaying()) {
                        handler.obtainMessage(1).sendToTarget();
                    }
                    break;
            }

        }
    };


    private TTSController(Context context) {
        mContext = context.getApplicationContext();
        systemTTS = SystemTTS.getInstance(mContext);
    }

    public void init() {
        if (systemTTS != null) {
            systemTTS.init();
        }
    }

    public static TTSController getInstance(Context context) {
        if (ttsManager == null) {
            ttsManager = new TTSController(context);
        }
        return ttsManager;
    }

    public void stopSpeaking() {
        if (systemTTS != null) {
            systemTTS.stopSpeak();
        }
        wordList.clear();
    }

    public void destroy() {
        if (systemTTS != null) {
            systemTTS.destroy();
        }
        ttsManager = null;
    }

    /****************************************************************************
     * 以下都是导航相关接口
     ****************************************************************************/


    @Override
    public void onArriveDestination() {
    }

    @Override
    public void onArrivedWayPoint(int arg0) {
    }

    @Override
    public void onCalculateRouteFailure(int arg0) {
        if (wordList != null)
            wordList.addLast("路线规划失败");
    }

    @Override
    public void onEndEmulatorNavi() {
    }

    @Override
    public void onGetNavigationText(int arg0, String arg1) {
    }


    @Override
    public void onInitNaviFailure() {
    }

    @Override
    public void onInitNaviSuccess() {
    }

    @Override
    public void onLocationChange(AMapNaviLocation arg0) {
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        if (wordList != null)
            wordList.addLast("前方路线拥堵，路线重新规划");
    }

    @Override
    public void onReCalculateRouteForYaw() {
        if (wordList != null)
            wordList.addLast("路线重新规划");
    }

    @Override
    public void onStartNavi(int arg0) {
    }

    @Override
    public void onTrafficStatusUpdate() {
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] infoArray) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] infoArray) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {

    }


    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateRouteSuccess(int[] routeIds) {

    }

    @Override
    public void notifyParallelRoad(int parallelRoadType) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] infos) {

    }


    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int type) {

    }

    @Override
    public void onGetNavigationText(String playText) {
        if (wordList != null)
            wordList.addLast(playText);
        handler.obtainMessage(CHECK_TTS_PLAY).sendToTarget();
    }

    @Override
    @Deprecated
    public void OnUpdateTrafficFacility(TrafficFacilityInfo arg0) {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }
}
