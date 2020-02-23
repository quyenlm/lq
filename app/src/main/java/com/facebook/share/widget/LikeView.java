package com.facebook.share.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.FacebookException;
import com.facebook.common.R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.internal.LikeBoxCountView;
import com.facebook.share.internal.LikeButton;

@Deprecated
public class LikeView extends FrameLayout {
    private static final int NO_FOREGROUND_COLOR = -1;
    private AuxiliaryViewPosition auxiliaryViewPosition = AuxiliaryViewPosition.DEFAULT;
    private BroadcastReceiver broadcastReceiver;
    private LinearLayout containerView;
    /* access modifiers changed from: private */
    public LikeActionControllerCreationCallback creationCallback;
    private int edgePadding;
    private boolean explicitlyDisabled = true;
    private int foregroundColor = -1;
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.DEFAULT;
    private int internalPadding;
    private LikeActionController likeActionController;
    private LikeBoxCountView likeBoxCountView;
    private LikeButton likeButton;
    private Style likeViewStyle = Style.DEFAULT;
    /* access modifiers changed from: private */
    public String objectId;
    /* access modifiers changed from: private */
    public ObjectType objectType;
    /* access modifiers changed from: private */
    public OnErrorListener onErrorListener;
    private FragmentWrapper parentFragment;
    private TextView socialSentenceView;

    public interface OnErrorListener {
        void onError(FacebookException facebookException);
    }

    @Deprecated
    public enum ObjectType {
        UNKNOWN("unknown", 0),
        OPEN_GRAPH("open_graph", 1),
        PAGE("page", 2);
        
        public static ObjectType DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = UNKNOWN;
        }

        public static ObjectType fromInt(int enumValue) {
            for (ObjectType objectType : values()) {
                if (objectType.getValue() == enumValue) {
                    return objectType;
                }
            }
            return null;
        }

        private ObjectType(String stringValue2, int value) {
            this.stringValue = stringValue2;
            this.intValue = value;
        }

        public String toString() {
            return this.stringValue;
        }

        public int getValue() {
            return this.intValue;
        }
    }

    @Deprecated
    public enum Style {
        STANDARD("standard", 0),
        BUTTON("button", 1),
        BOX_COUNT("box_count", 2);
        
        static Style DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = STANDARD;
        }

        static Style fromInt(int enumValue) {
            for (Style style : values()) {
                if (style.getValue() == enumValue) {
                    return style;
                }
            }
            return null;
        }

        private Style(String stringValue2, int value) {
            this.stringValue = stringValue2;
            this.intValue = value;
        }

        public String toString() {
            return this.stringValue;
        }

        /* access modifiers changed from: private */
        public int getValue() {
            return this.intValue;
        }
    }

    @Deprecated
    public enum HorizontalAlignment {
        CENTER("center", 0),
        LEFT("left", 1),
        RIGHT("right", 2);
        
        static HorizontalAlignment DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = CENTER;
        }

        static HorizontalAlignment fromInt(int enumValue) {
            for (HorizontalAlignment horizontalAlignment : values()) {
                if (horizontalAlignment.getValue() == enumValue) {
                    return horizontalAlignment;
                }
            }
            return null;
        }

        private HorizontalAlignment(String stringValue2, int value) {
            this.stringValue = stringValue2;
            this.intValue = value;
        }

        public String toString() {
            return this.stringValue;
        }

        /* access modifiers changed from: private */
        public int getValue() {
            return this.intValue;
        }
    }

    @Deprecated
    public enum AuxiliaryViewPosition {
        BOTTOM("bottom", 0),
        INLINE("inline", 1),
        TOP("top", 2);
        
        static AuxiliaryViewPosition DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = BOTTOM;
        }

        static AuxiliaryViewPosition fromInt(int enumValue) {
            for (AuxiliaryViewPosition auxViewPosition : values()) {
                if (auxViewPosition.getValue() == enumValue) {
                    return auxViewPosition;
                }
            }
            return null;
        }

        private AuxiliaryViewPosition(String stringValue2, int value) {
            this.stringValue = stringValue2;
            this.intValue = value;
        }

        public String toString() {
            return this.stringValue;
        }

        /* access modifiers changed from: private */
        public int getValue() {
            return this.intValue;
        }
    }

    @Deprecated
    public LikeView(Context context) {
        super(context);
        initialize(context);
    }

    @Deprecated
    public LikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        initialize(context);
    }

    @Deprecated
    public void setObjectIdAndType(String objectId2, ObjectType objectType2) {
        String objectId3 = Utility.coerceValueIfNullOrEmpty(objectId2, (String) null);
        if (objectType2 == null) {
            objectType2 = ObjectType.DEFAULT;
        }
        if (!Utility.areObjectsEqual(objectId3, this.objectId) || objectType2 != this.objectType) {
            setObjectIdAndTypeForced(objectId3, objectType2);
            updateLikeStateAndLayout();
        }
    }

    @Deprecated
    public void setLikeViewStyle(Style likeViewStyle2) {
        if (likeViewStyle2 == null) {
            likeViewStyle2 = Style.DEFAULT;
        }
        if (this.likeViewStyle != likeViewStyle2) {
            this.likeViewStyle = likeViewStyle2;
            updateLayout();
        }
    }

    @Deprecated
    public void setAuxiliaryViewPosition(AuxiliaryViewPosition auxiliaryViewPosition2) {
        if (auxiliaryViewPosition2 == null) {
            auxiliaryViewPosition2 = AuxiliaryViewPosition.DEFAULT;
        }
        if (this.auxiliaryViewPosition != auxiliaryViewPosition2) {
            this.auxiliaryViewPosition = auxiliaryViewPosition2;
            updateLayout();
        }
    }

    @Deprecated
    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment2) {
        if (horizontalAlignment2 == null) {
            horizontalAlignment2 = HorizontalAlignment.DEFAULT;
        }
        if (this.horizontalAlignment != horizontalAlignment2) {
            this.horizontalAlignment = horizontalAlignment2;
            updateLayout();
        }
    }

    @Deprecated
    public void setForegroundColor(int foregroundColor2) {
        if (this.foregroundColor != foregroundColor2) {
            this.socialSentenceView.setTextColor(foregroundColor2);
        }
    }

    @Deprecated
    public void setOnErrorListener(OnErrorListener onErrorListener2) {
        this.onErrorListener = onErrorListener2;
    }

    @Deprecated
    public OnErrorListener getOnErrorListener() {
        return this.onErrorListener;
    }

    @Deprecated
    public void setFragment(Fragment fragment) {
        this.parentFragment = new FragmentWrapper(fragment);
    }

    @Deprecated
    public void setFragment(android.app.Fragment fragment) {
        this.parentFragment = new FragmentWrapper(fragment);
    }

    @Deprecated
    public void setEnabled(boolean enabled) {
        this.explicitlyDisabled = true;
        updateLikeStateAndLayout();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        setObjectIdAndType((String) null, ObjectType.UNKNOWN);
        super.onDetachedFromWindow();
    }

    private void parseAttributes(AttributeSet attrs) {
        TypedArray a;
        if (attrs != null && getContext() != null && (a = getContext().obtainStyledAttributes(attrs, R.styleable.com_facebook_like_view)) != null) {
            this.objectId = Utility.coerceValueIfNullOrEmpty(a.getString(R.styleable.com_facebook_like_view_com_facebook_object_id), (String) null);
            this.objectType = ObjectType.fromInt(a.getInt(R.styleable.com_facebook_like_view_com_facebook_object_type, ObjectType.DEFAULT.getValue()));
            this.likeViewStyle = Style.fromInt(a.getInt(R.styleable.com_facebook_like_view_com_facebook_style, Style.DEFAULT.getValue()));
            if (this.likeViewStyle == null) {
                throw new IllegalArgumentException("Unsupported value for LikeView 'style'");
            }
            this.auxiliaryViewPosition = AuxiliaryViewPosition.fromInt(a.getInt(R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position, AuxiliaryViewPosition.DEFAULT.getValue()));
            if (this.auxiliaryViewPosition == null) {
                throw new IllegalArgumentException("Unsupported value for LikeView 'auxiliary_view_position'");
            }
            this.horizontalAlignment = HorizontalAlignment.fromInt(a.getInt(R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment, HorizontalAlignment.DEFAULT.getValue()));
            if (this.horizontalAlignment == null) {
                throw new IllegalArgumentException("Unsupported value for LikeView 'horizontal_alignment'");
            }
            this.foregroundColor = a.getColor(R.styleable.com_facebook_like_view_com_facebook_foreground_color, -1);
            a.recycle();
        }
    }

    private void initialize(Context context) {
        this.edgePadding = getResources().getDimensionPixelSize(R.dimen.com_facebook_likeview_edge_padding);
        this.internalPadding = getResources().getDimensionPixelSize(R.dimen.com_facebook_likeview_internal_padding);
        if (this.foregroundColor == -1) {
            this.foregroundColor = getResources().getColor(R.color.com_facebook_likeview_text_color);
        }
        setBackgroundColor(0);
        this.containerView = new LinearLayout(context);
        this.containerView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        initializeLikeButton(context);
        initializeSocialSentenceView(context);
        initializeLikeCountView(context);
        this.containerView.addView(this.likeButton);
        this.containerView.addView(this.socialSentenceView);
        this.containerView.addView(this.likeBoxCountView);
        addView(this.containerView);
        setObjectIdAndTypeForced(this.objectId, this.objectType);
        updateLikeStateAndLayout();
    }

    private void initializeLikeButton(Context context) {
        this.likeButton = new LikeButton(context, this.likeActionController != null && this.likeActionController.isObjectLiked());
        this.likeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LikeView.this.toggleLike();
            }
        });
        this.likeButton.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    }

    private void initializeSocialSentenceView(Context context) {
        this.socialSentenceView = new TextView(context);
        this.socialSentenceView.setTextSize(0, getResources().getDimension(R.dimen.com_facebook_likeview_text_size));
        this.socialSentenceView.setMaxLines(2);
        this.socialSentenceView.setTextColor(this.foregroundColor);
        this.socialSentenceView.setGravity(17);
        this.socialSentenceView.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
    }

    private void initializeLikeCountView(Context context) {
        this.likeBoxCountView = new LikeBoxCountView(context);
        this.likeBoxCountView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    }

    /* access modifiers changed from: private */
    public void toggleLike() {
        if (this.likeActionController != null) {
            Activity activity = null;
            if (this.parentFragment == null) {
                activity = getActivity();
            }
            this.likeActionController.toggleLike(activity, this.parentFragment, getAnalyticsParameters());
        }
    }

    private Activity getActivity() {
        Context context = getContext();
        while (!(context instanceof Activity) && (context instanceof ContextWrapper)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        throw new FacebookException("Unable to get Activity.");
    }

    private Bundle getAnalyticsParameters() {
        Bundle params = new Bundle();
        params.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, this.likeViewStyle.toString());
        params.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_AUXILIARY_POSITION, this.auxiliaryViewPosition.toString());
        params.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_HORIZONTAL_ALIGNMENT, this.horizontalAlignment.toString());
        params.putString("object_id", Utility.coerceValueIfNullOrEmpty(this.objectId, ""));
        params.putString("object_type", this.objectType.toString());
        return params;
    }

    /* access modifiers changed from: private */
    public void setObjectIdAndTypeForced(String newObjectId, ObjectType newObjectType) {
        tearDownObjectAssociations();
        this.objectId = newObjectId;
        this.objectType = newObjectType;
        if (!Utility.isNullOrEmpty(newObjectId)) {
            this.creationCallback = new LikeActionControllerCreationCallback();
            if (!isInEditMode()) {
                LikeActionController.getControllerForObjectId(newObjectId, newObjectType, this.creationCallback);
            }
        }
    }

    /* access modifiers changed from: private */
    public void associateWithLikeActionController(LikeActionController likeActionController2) {
        this.likeActionController = likeActionController2;
        this.broadcastReceiver = new LikeControllerBroadcastReceiver();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
        filter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR);
        filter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
        localBroadcastManager.registerReceiver(this.broadcastReceiver, filter);
    }

    private void tearDownObjectAssociations() {
        if (this.broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this.broadcastReceiver);
            this.broadcastReceiver = null;
        }
        if (this.creationCallback != null) {
            this.creationCallback.cancel();
            this.creationCallback = null;
        }
        this.likeActionController = null;
    }

    /* access modifiers changed from: private */
    public void updateLikeStateAndLayout() {
        boolean enabled;
        if (!this.explicitlyDisabled) {
            enabled = true;
        } else {
            enabled = false;
        }
        if (this.likeActionController == null) {
            this.likeButton.setSelected(false);
            this.socialSentenceView.setText((CharSequence) null);
            this.likeBoxCountView.setText((String) null);
        } else {
            this.likeButton.setSelected(this.likeActionController.isObjectLiked());
            this.socialSentenceView.setText(this.likeActionController.getSocialSentence());
            this.likeBoxCountView.setText(this.likeActionController.getLikeCountString());
            enabled &= this.likeActionController.shouldEnableView();
        }
        super.setEnabled(enabled);
        this.likeButton.setEnabled(enabled);
        updateLayout();
    }

    private void updateLayout() {
        int viewGravity;
        View auxView;
        int i = 1;
        FrameLayout.LayoutParams containerViewLayoutParams = (FrameLayout.LayoutParams) this.containerView.getLayoutParams();
        LinearLayout.LayoutParams buttonLayoutParams = (LinearLayout.LayoutParams) this.likeButton.getLayoutParams();
        if (this.horizontalAlignment == HorizontalAlignment.LEFT) {
            viewGravity = 3;
        } else {
            viewGravity = this.horizontalAlignment == HorizontalAlignment.CENTER ? 1 : 5;
        }
        containerViewLayoutParams.gravity = viewGravity | 48;
        buttonLayoutParams.gravity = viewGravity;
        this.socialSentenceView.setVisibility(8);
        this.likeBoxCountView.setVisibility(8);
        if (this.likeViewStyle == Style.STANDARD && this.likeActionController != null && !Utility.isNullOrEmpty(this.likeActionController.getSocialSentence())) {
            auxView = this.socialSentenceView;
        } else if (this.likeViewStyle == Style.BOX_COUNT && this.likeActionController != null && !Utility.isNullOrEmpty(this.likeActionController.getLikeCountString())) {
            updateBoxCountCaretPosition();
            auxView = this.likeBoxCountView;
        } else {
            return;
        }
        auxView.setVisibility(0);
        ((LinearLayout.LayoutParams) auxView.getLayoutParams()).gravity = viewGravity;
        LinearLayout linearLayout = this.containerView;
        if (this.auxiliaryViewPosition == AuxiliaryViewPosition.INLINE) {
            i = 0;
        }
        linearLayout.setOrientation(i);
        if (this.auxiliaryViewPosition == AuxiliaryViewPosition.TOP || (this.auxiliaryViewPosition == AuxiliaryViewPosition.INLINE && this.horizontalAlignment == HorizontalAlignment.RIGHT)) {
            this.containerView.removeView(this.likeButton);
            this.containerView.addView(this.likeButton);
        } else {
            this.containerView.removeView(auxView);
            this.containerView.addView(auxView);
        }
        switch (this.auxiliaryViewPosition) {
            case TOP:
                auxView.setPadding(this.edgePadding, this.edgePadding, this.edgePadding, this.internalPadding);
                return;
            case BOTTOM:
                auxView.setPadding(this.edgePadding, this.internalPadding, this.edgePadding, this.edgePadding);
                return;
            case INLINE:
                if (this.horizontalAlignment == HorizontalAlignment.RIGHT) {
                    auxView.setPadding(this.edgePadding, this.edgePadding, this.internalPadding, this.edgePadding);
                    return;
                } else {
                    auxView.setPadding(this.internalPadding, this.edgePadding, this.edgePadding, this.edgePadding);
                    return;
                }
            default:
                return;
        }
    }

    private void updateBoxCountCaretPosition() {
        switch (this.auxiliaryViewPosition) {
            case TOP:
                this.likeBoxCountView.setCaretPosition(LikeBoxCountView.LikeBoxCountViewCaretPosition.BOTTOM);
                return;
            case BOTTOM:
                this.likeBoxCountView.setCaretPosition(LikeBoxCountView.LikeBoxCountViewCaretPosition.TOP);
                return;
            case INLINE:
                this.likeBoxCountView.setCaretPosition(this.horizontalAlignment == HorizontalAlignment.RIGHT ? LikeBoxCountView.LikeBoxCountViewCaretPosition.RIGHT : LikeBoxCountView.LikeBoxCountViewCaretPosition.LEFT);
                return;
            default:
                return;
        }
    }

    private class LikeControllerBroadcastReceiver extends BroadcastReceiver {
        private LikeControllerBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            Bundle extras = intent.getExtras();
            boolean shouldRespond = true;
            if (extras != null) {
                String broadcastObjectId = extras.getString(LikeActionController.ACTION_OBJECT_ID_KEY);
                shouldRespond = Utility.isNullOrEmpty(broadcastObjectId) || Utility.areObjectsEqual(LikeView.this.objectId, broadcastObjectId);
            }
            if (shouldRespond) {
                if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_UPDATED.equals(intentAction)) {
                    LikeView.this.updateLikeStateAndLayout();
                } else if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR.equals(intentAction)) {
                    if (LikeView.this.onErrorListener != null) {
                        LikeView.this.onErrorListener.onError(NativeProtocol.getExceptionFromErrorData(extras));
                    }
                } else if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET.equals(intentAction)) {
                    LikeView.this.setObjectIdAndTypeForced(LikeView.this.objectId, LikeView.this.objectType);
                    LikeView.this.updateLikeStateAndLayout();
                }
            }
        }
    }

    private class LikeActionControllerCreationCallback implements LikeActionController.CreationCallback {
        private boolean isCancelled;

        private LikeActionControllerCreationCallback() {
        }

        public void cancel() {
            this.isCancelled = true;
        }

        public void onComplete(LikeActionController likeActionController, FacebookException error) {
            if (!this.isCancelled) {
                if (likeActionController != null) {
                    if (!likeActionController.shouldEnableView()) {
                        error = new FacebookException("Cannot use LikeView. The device may not be supported.");
                    }
                    LikeView.this.associateWithLikeActionController(likeActionController);
                    LikeView.this.updateLikeStateAndLayout();
                }
                if (!(error == null || LikeView.this.onErrorListener == null)) {
                    LikeView.this.onErrorListener.onError(error);
                }
                LikeActionControllerCreationCallback unused = LikeView.this.creationCallback = null;
            }
        }
    }
}
