package com.garena.overlay;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.garena.msdk.R;

public class RecordResultView extends FrameLayout {
    private ImageView imageResult;
    private TextView textResult;

    public RecordResultView(Context context) {
        this(context, (AttributeSet) null);
    }

    public RecordResultView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordResultView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.msdk_video_record_result_view, this);
        this.imageResult = (ImageView) findViewById(R.id.img_result);
        this.textResult = (TextView) findViewById(R.id.text_result);
    }

    public void setResult(boolean success) {
        if (success) {
            this.imageResult.setImageResource(R.drawable.msdk_successicon);
            this.textResult.setText(R.string.video_record_success);
            return;
        }
        this.imageResult.setImageResource(R.drawable.msdk_failicon);
        this.textResult.setText(R.string.video_record_fail);
    }
}
