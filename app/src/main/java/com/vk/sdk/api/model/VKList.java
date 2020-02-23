package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.model.Identifiable;
import com.vk.sdk.api.model.VKApiModel;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VKList<T extends VKApiModel & Parcelable & Identifiable> extends VKApiModel implements List<T>, Parcelable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static Parcelable.Creator<VKList> CREATOR = new Parcelable.Creator<VKList>() {
        public VKList createFromParcel(Parcel source) {
            return new VKList(source);
        }

        public VKList[] newArray(int size) {
            return new VKList[size];
        }
    };
    private static final int NO_COUNT = -1;
    private int count = -1;
    private ArrayList<T> items = new ArrayList<>();

    public interface Parser<D> {
        D parseObject(JSONObject jSONObject) throws Exception;
    }

    static {
        boolean z;
        if (!VKList.class.desiredAssertionStatus()) {
            z = true;
        } else {
            z = false;
        }
        $assertionsDisabled = z;
    }

    public VKList() {
    }

    public VKList(List<? extends T> data) {
        if ($assertionsDisabled || data != null) {
            this.items = new ArrayList<>(data);
            return;
        }
        throw new AssertionError();
    }

    public VKList(JSONObject from, Class<? extends T> clazz) {
        fill(from, clazz);
    }

    public VKList(JSONArray from, Class<? extends T> clazz) {
        fill(from, clazz);
    }

    public VKList(JSONObject from, Parser<T> creator) {
        fill(from, creator);
    }

    public VKList(JSONArray from, Parser<T> creator) {
        fill(from, creator);
    }

    public void fill(JSONObject from, Class<? extends T> clazz) {
        if (from.has("response")) {
            JSONArray array = from.optJSONArray("response");
            if (array != null) {
                fill(array, clazz);
            } else {
                fill(from.optJSONObject("response"), clazz);
            }
        } else {
            fill(from, new ReflectParser(clazz));
        }
    }

    public void fill(JSONArray from, Class<? extends T> clazz) {
        fill(from, new ReflectParser(clazz));
    }

    public void fill(JSONObject from, Parser<? extends T> creator) {
        if (from != null) {
            fill(from.optJSONArray("items"), creator);
            this.count = from.optInt(VKApiConst.COUNT, this.count);
        }
    }

    public void fill(JSONArray from, Parser<? extends T> creator) {
        if (from != null) {
            for (int i = 0; i < from.length(); i++) {
                try {
                    T object = (VKApiModel) creator.parseObject(from.getJSONObject(i));
                    if (object != null) {
                        this.items.add(object);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public void addBefore(int id, T data) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (((Identifiable) get(i)).getId() > id || i == size - 1) {
                add(i, data);
                return;
            }
        }
    }

    public void addAfter(int id, T data) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (((Identifiable) get(i)).getId() > id || i == size - 1) {
                add(i + 1, data);
                return;
            }
        }
    }

    public T getById(int id) {
        Iterator it = iterator();
        while (it.hasNext()) {
            T item = (VKApiModel) it.next();
            if (((Identifiable) item).getId() == id) {
                return item;
            }
        }
        return null;
    }

    public VKList<T> search(String query) {
        VKList<T> result = new VKList<>();
        Pattern pattern = Pattern.compile("(?i).*\\b" + query + ".*");
        Iterator it = iterator();
        while (it.hasNext()) {
            T item = (VKApiModel) it.next();
            if (pattern.matcher(item.toString()).find()) {
                result.add(item);
            }
        }
        return result;
    }

    public int getCount() {
        return this.count != -1 ? this.count : size();
    }

    public void add(int location, T object) {
        this.items.add(location, object);
    }

    public boolean add(T object) {
        return this.items.add(object);
    }

    public boolean addAll(int location, @NonNull Collection<? extends T> collection) {
        return this.items.addAll(location, collection);
    }

    public boolean addAll(@NonNull Collection<? extends T> collection) {
        return this.items.addAll(collection);
    }

    public void clear() {
        this.items.clear();
    }

    public boolean contains(Object object) {
        return this.items.contains(object);
    }

    public boolean containsAll(@NonNull Collection<?> collection) {
        return this.items.containsAll(collection);
    }

    public boolean equals(Object object) {
        return getClass().equals(object.getClass()) && this.items.equals(object);
    }

    public T get(int location) {
        return (VKApiModel) this.items.get(location);
    }

    public int indexOf(Object object) {
        return this.items.indexOf(object);
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @NonNull
    public Iterator<T> iterator() {
        return this.items.iterator();
    }

    public int lastIndexOf(Object object) {
        return this.items.lastIndexOf(object);
    }

    public ListIterator<T> listIterator() {
        return this.items.listIterator();
    }

    @NonNull
    public ListIterator<T> listIterator(int location) {
        return this.items.listIterator(location);
    }

    public T remove(int location) {
        return (VKApiModel) this.items.remove(location);
    }

    public boolean remove(Object object) {
        return this.items.remove(object);
    }

    public boolean removeAll(@NonNull Collection<?> collection) {
        return this.items.removeAll(collection);
    }

    public boolean retainAll(@NonNull Collection<?> collection) {
        return this.items.retainAll(collection);
    }

    public T set(int location, T object) {
        return (VKApiModel) this.items.set(location, object);
    }

    public int size() {
        return this.items.size();
    }

    @NonNull
    public List<T> subList(int start, int end) {
        return this.items.subList(start, end);
    }

    @NonNull
    public Object[] toArray() {
        return this.items.toArray();
    }

    @NonNull
    public <T1> T1[] toArray(@NonNull T1[] array) {
        return this.items.toArray(array);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.items.size());
        Iterator it = iterator();
        while (it.hasNext()) {
            dest.writeParcelable((VKApiModel) it.next(), flags);
        }
        dest.writeInt(this.count);
    }

    public VKList(Parcel in) {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            this.items.add((VKApiModel) in.readParcelable(getClass().getClassLoader()));
        }
        this.count = in.readInt();
    }

    public static final class ReflectParser<D extends VKApiModel> implements Parser<D> {
        private final Class<? extends D> clazz;

        public ReflectParser(Class<? extends D> clazz2) {
            this.clazz = clazz2;
        }

        public D parseObject(JSONObject source) throws Exception {
            try {
                Constructor<? extends D> jsonConstructor = this.clazz.getConstructor(new Class[]{JSONObject.class});
                if (jsonConstructor != null) {
                    return (VKApiModel) jsonConstructor.newInstance(new Object[]{source});
                }
            } catch (Exception e) {
            }
            return ((VKApiModel) this.clazz.newInstance()).parse(source);
        }
    }

    public VKApiModel parse(JSONObject response) throws JSONException {
        throw new JSONException("Operation is not supported while class is generic");
    }
}
