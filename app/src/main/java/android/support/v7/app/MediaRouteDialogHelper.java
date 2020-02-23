package android.support.v7.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.mediarouter.R;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class MediaRouteDialogHelper {
    MediaRouteDialogHelper() {
    }

    public static int getDialogWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        boolean isPortrait = metrics.widthPixels < metrics.heightPixels;
        TypedValue value = new TypedValue();
        context.getResources().getValue(isPortrait ? R.dimen.mr_dialog_fixed_width_minor : R.dimen.mr_dialog_fixed_width_major, value, true);
        if (value.type == 5) {
            return (int) value.getDimension(metrics);
        }
        if (value.type == 6) {
            return (int) value.getFraction((float) metrics.widthPixels, (float) metrics.widthPixels);
        }
        return -2;
    }

    public static <E> boolean listUnorderedEquals(List<E> list1, List<E> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public static <E> Set<E> getItemsAdded(List<E> before, List<E> after) {
        HashSet<E> set = new HashSet<>(after);
        set.removeAll(before);
        return set;
    }

    public static <E> Set<E> getItemsRemoved(List<E> before, List<E> after) {
        HashSet<E> set = new HashSet<>(before);
        set.removeAll(after);
        return set;
    }

    public static <E> HashMap<E, Rect> getItemBoundMap(ListView listView, ArrayAdapter<E> adapter) {
        HashMap<E, Rect> itemBoundMap = new HashMap<>();
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        for (int i = 0; i < listView.getChildCount(); i++) {
            E item = adapter.getItem(firstVisiblePosition + i);
            View view = listView.getChildAt(i);
            itemBoundMap.put(item, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        }
        return itemBoundMap;
    }

    public static <E> HashMap<E, BitmapDrawable> getItemBitmapMap(Context context, ListView listView, ArrayAdapter<E> adapter) {
        HashMap<E, BitmapDrawable> itemBitmapMap = new HashMap<>();
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        for (int i = 0; i < listView.getChildCount(); i++) {
            itemBitmapMap.put(adapter.getItem(firstVisiblePosition + i), getViewBitmap(context, listView.getChildAt(i)));
        }
        return itemBitmapMap;
    }

    private static BitmapDrawable getViewBitmap(Context context, View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return new BitmapDrawable(context.getResources(), bitmap);
    }
}
