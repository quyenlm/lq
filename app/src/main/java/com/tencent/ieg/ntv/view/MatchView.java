package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventGetInfoDataUI;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.EventMatchReserveUI;
import com.tencent.ieg.ntv.event.EventReserveResultUI;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.event.net.EventMatchInfoData;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.utils.Logger;

public class MatchView extends FrameLayout {
    /* access modifiers changed from: private */
    public static final String TAG = MatchView.class.getSimpleName();
    /* access modifiers changed from: private */
    public int iMatchId;
    private ViewGroup mDateBar;
    public TextView mDateText;
    private TextView mMatchDesc;
    private TextView mMatchScore;
    private TextView mMatchScore_t1;
    private TextView mMatchScore_t2;
    private TextView mMatchTime;
    private TextView mTag;
    private ImageView mTagBorder;
    private CircleImageView mTeamIcon1;
    private CircleImageView mTeamIcon2;
    private TextView mTeamName1;
    private TextView mTeamName2;
    private IEventListener onMatchSubResult = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            EventGetInfoDataUI evtData = (EventGetInfoDataUI) event;
            Logger.d(MatchView.TAG, "logReserve: onMatchSubResult - mTagText:" + evtData.mTagText + ", mStatus:" + evtData.mStatus + ", iMatchId:" + MatchView.this.iMatchId);
            if (evtData != null && evtData.mSubInfo != null && evtData.mSubInfo.size() > 0) {
                for (int i = 0; i < evtData.mSubInfo.size(); i++) {
                    Logger.d(MatchView.TAG, "logReserve: onMatchSubResult2 - evtData.mSubInfo.get(i):" + evtData.mSubInfo.get(i) + ", iMatchId:" + MatchView.this.iMatchId);
                    if (((Integer) evtData.mSubInfo.get(i)).intValue() == MatchView.this.iMatchId) {
                        MatchView.this.setTagInfo(evtData.mTagText, evtData.mStatus);
                    }
                }
            }
        }
    };
    private IEventListener onReserveResult = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            EventReserveResultUI evt = (EventReserveResultUI) event;
            if (evt != null) {
                Logger.d(MatchView.TAG, "logReserve: onReserveResult - mMatchId:" + evt.mMatchId + ", iMatchId:" + MatchView.this.iMatchId);
                if (evt.mMatchId == MatchView.this.iMatchId) {
                    MatchView.this.setTagInfo(evt.mTagText, evt.mStatus);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public String sReplayUrl;
    /* access modifiers changed from: private */
    public int sStatus;
    private View.OnClickListener tagClick = new View.OnClickListener() {
        public void onClick(View view) {
            if (MatchView.this.sStatus == EventMatchInfoData.MatchStatus.RESERVABLE) {
                EventManager.getInstance().post(NTVDefine.EVT_MATCH_RESERVE_CLICK_UI, new EventMatchReserveUI(EventMatchReserveUI.RESERVE_TYPE_RESERVE, MatchView.this.iMatchId));
            } else if (MatchView.this.sStatus == EventMatchInfoData.MatchStatus.RESERVED) {
                EventManager.getInstance().post(NTVDefine.EVT_MATCH_RESERVE_CLICK_UI, new EventMatchReserveUI(EventMatchReserveUI.RESERVE_TYPE_CANCEL, MatchView.this.iMatchId));
            } else if (MatchView.this.sStatus == EventMatchInfoData.MatchStatus.REPLAY) {
                EventMatchReserveUI evt = new EventMatchReserveUI(EventMatchReserveUI.RESERVE_TYPE_REPLAY, MatchView.this.iMatchId);
                evt.replayurl = MatchView.this.sReplayUrl;
                EventManager.getInstance().post(NTVDefine.EVT_MATCH_RESERVE_CLICK_UI, evt);
            }
        }
    };

    public MatchView(@NonNull Context context) {
        super(context);
        initUI();
    }

    private void initUI() {
        LayoutInflater.from(getContext()).inflate(R.layout.match_item_view, this);
        this.mMatchDesc = (TextView) findViewById(R.id.match_desc);
        this.mMatchTime = (TextView) findViewById(R.id.match_time);
        this.mMatchScore = (TextView) findViewById(R.id.match_score);
        this.mMatchScore_t1 = (TextView) findViewById(R.id.match_score_t1);
        this.mMatchScore_t2 = (TextView) findViewById(R.id.match_score_t2);
        this.mTeamName1 = (TextView) findViewById(R.id.team_name1);
        this.mTeamName2 = (TextView) findViewById(R.id.team_name2);
        this.mTag = (TextView) findViewById(R.id.tag);
        this.mTag.setOnClickListener(this.tagClick);
        this.mTagBorder = (ImageView) findViewById(R.id.tag_border);
        this.mTeamIcon1 = (CircleImageView) findViewById(R.id.team_icon1);
        this.mTeamIcon2 = (CircleImageView) findViewById(R.id.team_icon2);
        this.mDateBar = (ViewGroup) findViewById(R.id.date_bar);
        this.mDateText = (TextView) findViewById(R.id.date_text);
        this.mTeamIcon1.setImageDrawable(getResources().getDrawable(R.drawable.default_team_icon));
        this.mTeamIcon2.setImageDrawable(getResources().getDrawable(R.drawable.default_team_icon));
        EventManager.getInstance().register(NTVDefine.EVT_MATCH_RESERVE_RESULT_UPDATE_UI, this.onReserveResult);
        EventManager.getInstance().register(NTVDefine.EVT_MATCH_GET_INFO_RESULT_SUB, this.onMatchSubResult);
    }

    public void setsReplayUrl(String url) {
        this.sReplayUrl = url;
    }

    public void setMatchId(int id) {
        this.iMatchId = id;
    }

    public void setDate(String date, boolean showDate) {
        this.mDateText.setText(date);
        if (date == null || !showDate) {
            this.mDateBar.setVisibility(8);
        } else {
            this.mDateBar.setVisibility(0);
        }
    }

    public void setMatchDesc(String value) {
        this.mMatchDesc.setText(value);
    }

    public void setMatchTime(String time) {
        this.mMatchTime.setText(time);
    }

    public void setScore(String score1, String score2, boolean isFinished) {
        this.mMatchScore_t1.setText(score1);
        this.mMatchScore_t2.setText(score2);
        int colorNormal = getResources().getColor(R.color.ntvs_match_score_color_normal);
        int colorHighlight = getResources().getColor(R.color.ntvs_match_score_color_highlight);
        if (score1.equals("-") || score2.equals("-")) {
            if (isFinished) {
                colorScore(colorHighlight, colorHighlight, colorNormal);
            } else {
                colorScore(colorNormal, colorNormal, colorNormal);
            }
        } else if (isFinished) {
            try {
                int is_1 = Integer.parseInt(score1);
                int is_2 = Integer.parseInt(score2);
                if (is_1 > is_2) {
                    colorScore(colorHighlight, colorNormal, colorNormal);
                } else if (is_1 < is_2) {
                    colorScore(colorNormal, colorHighlight, colorNormal);
                } else {
                    colorScore(colorHighlight, colorHighlight, colorNormal);
                }
            } catch (Exception e) {
                colorScore(colorNormal, colorNormal, colorNormal);
            }
        } else {
            colorScore(colorNormal, colorNormal, colorNormal);
        }
    }

    private void colorScore(int s1, int s2, int p) {
        this.mMatchScore_t1.setTextColor(s1);
        this.mMatchScore_t2.setTextColor(s2);
        this.mMatchScore.setTextColor(p);
    }

    public void setTeamName(String team1, String team2) {
        this.mTeamName1.setText(team1);
        this.mTeamName2.setText(team2);
    }

    public void setTagInfo(String tagText, int status) {
        Logger.d(TAG, "logReserve: setTagInfo - tagText:" + tagText + ", status:" + status + ",matchid:" + this.iMatchId);
        this.sStatus = status;
        if (status == EventMatchInfoData.MatchStatus.NOTSHOW) {
            this.mTag.setVisibility(8);
            return;
        }
        this.mTag.setVisibility(0);
        this.mTag.setText(tagText);
        if (status == EventMatchInfoData.MatchStatus.RESERVABLE) {
            setTagStype(getResources().getColor(R.color.ntvs_match_tag_color_reservable));
        } else if (status == EventMatchInfoData.MatchStatus.REPLAY) {
            setTagStype(getResources().getColor(R.color.ntvs_match_tag_color_replay));
        } else if (status == EventMatchInfoData.MatchStatus.FINISH) {
            setTagStype(getResources().getColor(R.color.ntvs_match_tag_color_finished));
        } else {
            setTagStype(getResources().getColor(R.color.ntvs_match_tag_color_reserved));
        }
    }

    private void setTagStype(int color) {
        this.mTag.setTextColor(color);
        if (Build.VERSION.SDK_INT >= 16) {
            Drawable newDrawable = DrawableCompat.wrap(getResources().getDrawable(R.drawable.valor_k));
            DrawableCompat.setTint(newDrawable, color);
            this.mTagBorder.setScaleX(1.3f);
            this.mTagBorder.setBackground(newDrawable);
        }
    }

    public void setTeamIcon(String url1, String url2) {
        this.mTeamIcon1.setImageDrawable(getResources().getDrawable(R.drawable.default_team_icon));
        this.mTeamIcon2.setImageDrawable(getResources().getDrawable(R.drawable.default_team_icon));
        this.mTeamIcon1.setImageUrl(url1);
        this.mTeamIcon2.setImageUrl(url2);
    }
}
