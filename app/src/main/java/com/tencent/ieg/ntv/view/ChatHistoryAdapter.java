package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.ctrl.chat.ChatManager;
import com.tencent.ieg.ntv.ctrl.chat.ChatMsg;

public class ChatHistoryAdapter extends ArrayAdapter<ChatMsg> {
    public ChatHistoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public int getCount() {
        return ChatManager.GetInstance().getHistoryCount();
    }

    public ChatMsg getItem(int i) {
        return ChatManager.GetInstance().getHistoryDataWithIdx(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tv_msg;
        String opStr;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.ntvs_chat_view_history_item, (ViewGroup) null);
        }
        ChatMsg msg = getItem(i);
        if (!(msg == null || (tv_msg = (TextView) view.findViewById(R.id.ntvs_chat_item_msg)) == null)) {
            if (ChatManager.GetInstance().isSelf(msg.uid)) {
                if (msg.admin.equals("1")) {
                    opStr = String.format("<font color='#fadc46'>%s</font>: <font color='#fadc46'>%s</font>", new Object[]{msg.nickName, msg.msg});
                } else {
                    opStr = String.format("<font color='#fadc46'>%s</font>: <font color='#bababa'>%s</font>", new Object[]{msg.nickName, msg.msg});
                }
            } else if (msg.admin.equals("1")) {
                opStr = String.format("<font color='#93a0c7'>%s</font>: <font color='#fadc46'>%s</font>", new Object[]{msg.nickName, msg.msg});
            } else {
                opStr = String.format("<font color='#93a0c7'>%s</font>: <font color='#bababa'>%s</font>", new Object[]{msg.nickName, msg.msg});
            }
            tv_msg.setText(Html.fromHtml(opStr));
        }
        return view;
    }
}
