package com.beetalk.sdk.plugin.impl.vk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.beetalk.sdk.plugin.impl.vk.VKShareDialogBuilder;
import com.garena.msdk.R;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.httpClient.VKHttpClient;
import com.vk.sdk.api.httpClient.VKImageOperation;
import com.vk.sdk.api.model.VKApiLink;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPhotoSize;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.model.VKWallPostResult;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.api.photo.VKUploadWallPhotoRequest;
import com.vk.sdk.util.VKStringJoiner;
import com.vk.sdk.util.VKUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class VKShareDialogDelegate {
    static final /* synthetic */ boolean $assertionsDisabled = (!VKShareDialogDelegate.class.desiredAssertionStatus());
    private static final String SHARE_IMAGES_KEY = "ShareImages";
    private static final String SHARE_LINK_KEY = "ShareLink";
    private static final int SHARE_PHOTO_CORNER_RADIUS = 3;
    private static final int SHARE_PHOTO_HEIGHT = 100;
    private static final int SHARE_PHOTO_MARGIN_LEFT = 10;
    private static final String SHARE_TEXT_KEY = "ShareText";
    private static final String SHARE_UPLOADED_IMAGES_KEY = "ShareUploadedImages";
    /* access modifiers changed from: private */
    public final DialogFragmentI dialogFragmentI;
    /* access modifiers changed from: private */
    public VKUploadImage[] mAttachmentImages;
    private UploadingLink mAttachmentLink;
    private CharSequence mAttachmentText;
    private VKPhotoArray mExistingPhotos;
    /* access modifiers changed from: private */
    public VKShareDialogBuilder.VKShareDialogListener mListener;
    private LinearLayout mPhotoLayout;
    private HorizontalScrollView mPhotoScroll;
    private Button mSendButton;
    private ProgressBar mSendProgress;
    private EditText mShareTextField;
    View.OnClickListener sendButtonPress = new View.OnClickListener() {
        public void onClick(View view) {
            VKShareDialogDelegate.this.setIsLoading(true);
            if (VKShareDialogDelegate.this.mAttachmentImages == null || VKSdk.getAccessToken() == null) {
                VKShareDialogDelegate.this.makePostWithAttachments((VKAttachments) null);
                return;
            }
            new VKUploadWallPhotoRequest(VKShareDialogDelegate.this.mAttachmentImages, Long.valueOf(Long.parseLong(VKSdk.getAccessToken().userId)).longValue(), 0).executeWithListener(new VKRequest.VKRequestListener() {
                public void onComplete(VKResponse response) {
                    VKShareDialogDelegate.this.makePostWithAttachments(new VKAttachments((List<? extends VKAttachments.VKApiAttachment>) (VKPhotoArray) response.parsedModel));
                }

                public void onError(VKError error) {
                    VKShareDialogDelegate.this.setIsLoading(false);
                    if (VKShareDialogDelegate.this.mListener != null) {
                        VKShareDialogDelegate.this.mListener.onVkShareError(error);
                    }
                }
            });
        }
    };

    public interface DialogFragmentI {
        void dismissAllowingStateLoss();

        Activity getActivity();

        Dialog getDialog();

        Resources getResources();
    }

    public VKShareDialogDelegate(DialogFragmentI dialogFragmentI2) {
        this.dialogFragmentI = dialogFragmentI2;
    }

    public void setAttachmentImages(VKUploadImage[] images) {
        this.mAttachmentImages = images;
    }

    public void setText(CharSequence textToPost) {
        this.mAttachmentText = textToPost;
    }

    public void setAttachmentLink(String linkTitle, String linkUrl) {
        this.mAttachmentLink = new UploadingLink(linkTitle, linkUrl);
    }

    public void setUploadedPhotos(VKPhotoArray photos) {
        this.mExistingPhotos = photos;
    }

    public void setShareDialogListener(VKShareDialogBuilder.VKShareDialogListener listener) {
        this.mListener = listener;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = this.dialogFragmentI.getActivity();
        View mInternalView = View.inflate(context, R.layout.msdk_vk_share_dialog, (ViewGroup) null);
        if ($assertionsDisabled || mInternalView != null) {
            mInternalView.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VKShareDialogDelegate.this.dialogFragmentI.dismissAllowingStateLoss();
                    if (VKShareDialogDelegate.this.mListener != null) {
                        VKShareDialogDelegate.this.mListener.onVkShareCancel();
                    }
                }
            });
            this.mSendButton = (Button) mInternalView.findViewById(R.id.sendButton);
            this.mSendProgress = (ProgressBar) mInternalView.findViewById(R.id.sendProgress);
            this.mPhotoLayout = (LinearLayout) mInternalView.findViewById(R.id.imagesContainer);
            this.mShareTextField = (EditText) mInternalView.findViewById(R.id.shareText);
            this.mPhotoScroll = (HorizontalScrollView) mInternalView.findViewById(R.id.imagesScrollView);
            LinearLayout mAttachmentLinkLayout = (LinearLayout) mInternalView.findViewById(R.id.attachmentLinkLayout);
            this.mSendButton.setOnClickListener(this.sendButtonPress);
            if (savedInstanceState != null) {
                this.mShareTextField.setText(savedInstanceState.getString(SHARE_TEXT_KEY));
                this.mAttachmentLink = (UploadingLink) savedInstanceState.getParcelable(SHARE_LINK_KEY);
                this.mAttachmentImages = (VKUploadImage[]) savedInstanceState.getParcelableArray(SHARE_IMAGES_KEY);
                this.mExistingPhotos = (VKPhotoArray) savedInstanceState.getParcelable(SHARE_UPLOADED_IMAGES_KEY);
            } else if (this.mAttachmentText != null) {
                this.mShareTextField.setText(this.mAttachmentText);
            }
            this.mPhotoLayout.removeAllViews();
            if (this.mAttachmentImages != null) {
                for (VKUploadImage mAttachmentImage : this.mAttachmentImages) {
                    addBitmapToPreview(mAttachmentImage.mImageData);
                }
                this.mPhotoLayout.setVisibility(0);
            }
            if (this.mExistingPhotos != null) {
                processExistingPhotos();
            }
            if (this.mExistingPhotos == null && this.mAttachmentImages == null) {
                this.mPhotoLayout.setVisibility(8);
            }
            if (this.mAttachmentLink != null) {
                ((TextView) mAttachmentLinkLayout.findViewById(R.id.linkTitle)).setText(this.mAttachmentLink.linkTitle);
                ((TextView) mAttachmentLinkLayout.findViewById(R.id.linkHost)).setText(VKUtil.getHost(this.mAttachmentLink.linkUrl));
                mAttachmentLinkLayout.setVisibility(0);
            } else {
                mAttachmentLinkLayout.setVisibility(8);
            }
            Dialog result = new Dialog(context);
            result.requestWindowFeature(1);
            result.setContentView(mInternalView);
            result.setCancelable(true);
            return result;
        }
        throw new AssertionError();
    }

    public void onStart() {
        int width = -2;
        if (Build.VERSION.SDK_INT >= 13) {
            Display display = ((WindowManager) this.dialogFragmentI.getActivity().getSystemService("window")).getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x - (this.dialogFragmentI.getResources().getDimensionPixelSize(R.dimen.vk_share_dialog_view_padding) * 2);
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.dialogFragmentI.getDialog().getWindow().getAttributes());
        lp.height = -2;
        lp.width = width;
        this.dialogFragmentI.getDialog().getWindow().setAttributes(lp);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(SHARE_TEXT_KEY, this.mShareTextField.getText().toString());
        if (this.mAttachmentLink != null) {
            outState.putParcelable(SHARE_LINK_KEY, this.mAttachmentLink);
        }
        if (this.mAttachmentImages != null) {
            outState.putParcelableArray(SHARE_IMAGES_KEY, this.mAttachmentImages);
        }
        if (this.mExistingPhotos != null) {
            outState.putParcelable(SHARE_UPLOADED_IMAGES_KEY, this.mExistingPhotos);
        }
    }

    /* access modifiers changed from: private */
    public void setIsLoading(boolean loading) {
        if (loading) {
            this.mSendButton.setVisibility(8);
            this.mSendProgress.setVisibility(0);
            this.mShareTextField.setEnabled(false);
            this.mPhotoLayout.setEnabled(false);
            return;
        }
        this.mSendButton.setVisibility(0);
        this.mSendProgress.setVisibility(8);
        this.mShareTextField.setEnabled(true);
        this.mPhotoLayout.setEnabled(true);
    }

    private void processExistingPhotos() {
        ArrayList<String> photosToLoad = new ArrayList<>(this.mExistingPhotos.size());
        Iterator it = this.mExistingPhotos.iterator();
        while (it.hasNext()) {
            VKApiPhoto photo = (VKApiPhoto) it.next();
            photosToLoad.add("" + photo.owner_id + '_' + photo.id);
        }
        new VKRequest("photos.getById", VKParameters.from(VKApiConst.PHOTO_SIZES, 1, "photos", VKStringJoiner.join((Collection<?>) photosToLoad, ",")), VKPhotoArray.class).executeWithListener(new VKRequest.VKRequestListener() {
            public void onComplete(VKResponse response) {
                Iterator it = ((VKPhotoArray) response.parsedModel).iterator();
                while (it.hasNext()) {
                    VKApiPhoto photo = (VKApiPhoto) it.next();
                    if (photo.src.getByType(VKApiPhotoSize.Q) != null) {
                        VKShareDialogDelegate.this.loadAndAddPhoto(photo.src.getByType(VKApiPhotoSize.Q));
                    } else if (photo.src.getByType(VKApiPhotoSize.P) != null) {
                        VKShareDialogDelegate.this.loadAndAddPhoto(photo.src.getByType(VKApiPhotoSize.P));
                    } else if (photo.src.getByType(VKApiPhotoSize.M) != null) {
                        VKShareDialogDelegate.this.loadAndAddPhoto(photo.src.getByType(VKApiPhotoSize.M));
                    }
                }
            }

            public void onError(VKError error) {
                if (VKShareDialogDelegate.this.mListener != null) {
                    VKShareDialogDelegate.this.mListener.onVkShareError(error);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadAndAddPhoto(String photoUrl) {
        loadAndAddPhoto(photoUrl, 0);
    }

    /* access modifiers changed from: private */
    public void loadAndAddPhoto(final String photoUrl, final int attempt) {
        if (attempt <= 10) {
            VKImageOperation op = new VKImageOperation(photoUrl);
            op.setImageOperationListener(new VKImageOperation.VKImageOperationListener() {
                public void onComplete(VKImageOperation operation, Bitmap image) {
                    if (image == null) {
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            public void run() {
                                VKShareDialogDelegate.this.loadAndAddPhoto(photoUrl, attempt + 1);
                            }
                        }, 1000);
                    } else {
                        VKShareDialogDelegate.this.addBitmapToPreview(image);
                    }
                }

                public void onError(VKImageOperation operation, VKError error) {
                }
            });
            VKHttpClient.enqueueOperation(op);
        }
    }

    /* access modifiers changed from: private */
    public void addBitmapToPreview(Bitmap sourceBitmap) {
        Bitmap b;
        int i;
        if (this.dialogFragmentI.getActivity() != null && (b = VKUIHelper.getRoundedCornerBitmap(sourceBitmap, 100, 3)) != null) {
            ImageView iv = new ImageView(this.dialogFragmentI.getActivity());
            iv.setImageBitmap(b);
            iv.setAdjustViewBounds(true);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
            if (this.mPhotoLayout.getChildCount() > 0) {
                i = 10;
            } else {
                i = 0;
            }
            params.setMargins(i, 0, 0, 0);
            this.mPhotoLayout.addView(iv, params);
            this.mPhotoLayout.invalidate();
            this.mPhotoScroll.invalidate();
        }
    }

    /* access modifiers changed from: private */
    public void makePostWithAttachments(VKAttachments attachments) {
        if (attachments == null) {
            attachments = new VKAttachments();
        }
        if (this.mExistingPhotos != null) {
            attachments.addAll(this.mExistingPhotos);
        }
        if (this.mAttachmentLink != null) {
            attachments.add(new VKApiLink(this.mAttachmentLink.linkUrl));
        }
        String message = this.mShareTextField.getText().toString();
        Long userId = Long.valueOf(Long.parseLong(VKSdk.getAccessToken().userId));
        VKApi.wall().post(VKParameters.from(VKApiConst.OWNER_ID, userId, "message", message, VKApiConst.ATTACHMENTS, attachments.toAttachmentsString())).executeWithListener(new VKRequest.VKRequestListener() {
            public void onError(VKError error) {
                VKShareDialogDelegate.this.setIsLoading(false);
                if (VKShareDialogDelegate.this.mListener != null) {
                    VKShareDialogDelegate.this.mListener.onVkShareError(error);
                }
            }

            public void onComplete(VKResponse response) {
                VKShareDialogDelegate.this.setIsLoading(false);
                VKWallPostResult res = (VKWallPostResult) response.parsedModel;
                if (VKShareDialogDelegate.this.mListener != null) {
                    VKShareDialogDelegate.this.mListener.onVkShareComplete(res.post_id);
                }
                VKShareDialogDelegate.this.dialogFragmentI.dismissAllowingStateLoss();
            }
        });
    }

    public void onCancel(DialogInterface dialog) {
        if (this.mListener != null) {
            this.mListener.onVkShareCancel();
        }
    }

    private static class UploadingLink implements Parcelable {
        public static final Parcelable.Creator<UploadingLink> CREATOR = new Parcelable.Creator<UploadingLink>() {
            public UploadingLink createFromParcel(Parcel source) {
                return new UploadingLink(source);
            }

            public UploadingLink[] newArray(int size) {
                return new UploadingLink[size];
            }
        };
        public String linkTitle;
        public String linkUrl;

        public UploadingLink(String title, String url) {
            this.linkTitle = title;
            this.linkUrl = url;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.linkTitle);
            dest.writeString(this.linkUrl);
        }

        private UploadingLink(Parcel in) {
            this.linkTitle = in.readString();
            this.linkUrl = in.readString();
        }
    }
}
