package com.tencent.ieg.ntv.view;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.ctrl.chat.ChatManager;
import com.tencent.ieg.ntv.ctrl.chat.CommonWordCtrl;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.model.NTVDefine;
import java.util.regex.Pattern;

public class ChatContentFragment extends BaseContentFragment {
    InputFilter emojiFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[🀀-🏿]|[🐀-🟿]|[☀-⟿]", 66);

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (!this.emoji.matcher(source).find() && !ChatContentFragment.containsEmoji(source)) {
                return null;
            }
            return "";
        }
    };
    private IEventListener onMsgSendResult = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            if (ChatContentFragment.this.sendMsgTxt != null) {
                ChatContentFragment.this.sendMsgTxt.setText("");
            }
        }
    };
    /* access modifiers changed from: private */
    public TextView sendMsgTxt;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ntvs_chat_view, container, false);
        initUI(v, inflater);
        return v;
    }

    public void onShow() {
        super.onShow();
    }

    public void onHidden() {
        super.onHidden();
    }

    @TargetApi(3)
    private void initUI(View v, LayoutInflater inflater) {
        ChatHistoryAdapter adapter = new ChatHistoryAdapter(getContext(), R.layout.ntvs_chat_view_history_item);
        ListView lv = (ListView) v.findViewById(R.id.ntvs_chat_history_list);
        lv.setSelector(new ColorDrawable(0));
        lv.setTranscriptMode(2);
        this.sendMsgTxt = (TextView) v.findViewById(R.id.ntvs_sendMsg_Text);
        this.sendMsgTxt.setFilters(new InputFilter[]{this.emojiFilter});
        this.sendMsgTxt.setHint(TVShowManager.getInstance().getI18NText(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_CHAT_INPUT_HINT)));
        EventManager.getInstance().register(5022, this.onMsgSendResult);
        this.sendMsgTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int actID, KeyEvent keyEvent) {
                if (actID != 4) {
                    return false;
                }
                ChatManager.GetInstance().sendNewMsg(textView.getText().toString());
                return false;
            }
        });
        final ImageView iv = (ImageView) v.findViewById(R.id.common_word);
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CommonWordCtrl.getInstance().showCommonWord(ChatContentFragment.this.getContext(), iv);
            }
        });
        lv.setAdapter(adapter);
        ChatManager.GetInstance().setAdpter(adapter);
    }

    public static boolean containsEmoji(CharSequence source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            if (!isEmojiCharacter(source.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return codePoint == 0 || codePoint == 9 || codePoint == 10 || codePoint == 13 || (codePoint >= ' ' && codePoint <= 55295) || ((codePoint >= 57344 && codePoint <= 65533) || (codePoint >= 0 && codePoint <= 65535));
    }
}
