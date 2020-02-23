package android.support.v7.media;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class MediaRouteSelector {
    public static final MediaRouteSelector EMPTY = new MediaRouteSelector(new Bundle(), (List<String>) null);
    static final String KEY_CONTROL_CATEGORIES = "controlCategories";
    private final Bundle mBundle;
    List<String> mControlCategories;

    MediaRouteSelector(Bundle bundle, List<String> controlCategories) {
        this.mBundle = bundle;
        this.mControlCategories = controlCategories;
    }

    public List<String> getControlCategories() {
        ensureControlCategories();
        return this.mControlCategories;
    }

    /* access modifiers changed from: package-private */
    public void ensureControlCategories() {
        if (this.mControlCategories == null) {
            this.mControlCategories = this.mBundle.getStringArrayList(KEY_CONTROL_CATEGORIES);
            if (this.mControlCategories == null || this.mControlCategories.isEmpty()) {
                this.mControlCategories = Collections.emptyList();
            }
        }
    }

    public boolean hasControlCategory(String category) {
        if (category != null) {
            ensureControlCategories();
            int categoryCount = this.mControlCategories.size();
            for (int i = 0; i < categoryCount; i++) {
                if (this.mControlCategories.get(i).equals(category)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean matchesControlFilters(List<IntentFilter> filters) {
        if (filters != null) {
            ensureControlCategories();
            int categoryCount = this.mControlCategories.size();
            if (categoryCount != 0) {
                int filterCount = filters.size();
                for (int i = 0; i < filterCount; i++) {
                    IntentFilter filter = filters.get(i);
                    if (filter != null) {
                        for (int j = 0; j < categoryCount; j++) {
                            if (filter.hasCategory(this.mControlCategories.get(j))) {
                                return true;
                            }
                        }
                        continue;
                    }
                }
            }
        }
        return false;
    }

    public boolean contains(MediaRouteSelector selector) {
        if (selector == null) {
            return false;
        }
        ensureControlCategories();
        selector.ensureControlCategories();
        return this.mControlCategories.containsAll(selector.mControlCategories);
    }

    public boolean isEmpty() {
        ensureControlCategories();
        return this.mControlCategories.isEmpty();
    }

    public boolean isValid() {
        ensureControlCategories();
        if (this.mControlCategories.contains((Object) null)) {
            return false;
        }
        return true;
    }

    public boolean equals(Object o) {
        if (!(o instanceof MediaRouteSelector)) {
            return false;
        }
        MediaRouteSelector other = (MediaRouteSelector) o;
        ensureControlCategories();
        other.ensureControlCategories();
        return this.mControlCategories.equals(other.mControlCategories);
    }

    public int hashCode() {
        ensureControlCategories();
        return this.mControlCategories.hashCode();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("MediaRouteSelector{ ");
        result.append("controlCategories=").append(Arrays.toString(getControlCategories().toArray()));
        result.append(" }");
        return result.toString();
    }

    public Bundle asBundle() {
        return this.mBundle;
    }

    public static MediaRouteSelector fromBundle(@Nullable Bundle bundle) {
        if (bundle != null) {
            return new MediaRouteSelector(bundle, (List<String>) null);
        }
        return null;
    }

    public static final class Builder {
        private ArrayList<String> mControlCategories;

        public Builder() {
        }

        public Builder(@NonNull MediaRouteSelector selector) {
            if (selector == null) {
                throw new IllegalArgumentException("selector must not be null");
            }
            selector.ensureControlCategories();
            if (!selector.mControlCategories.isEmpty()) {
                this.mControlCategories = new ArrayList<>(selector.mControlCategories);
            }
        }

        @NonNull
        public Builder addControlCategory(@NonNull String category) {
            if (category == null) {
                throw new IllegalArgumentException("category must not be null");
            }
            if (this.mControlCategories == null) {
                this.mControlCategories = new ArrayList<>();
            }
            if (!this.mControlCategories.contains(category)) {
                this.mControlCategories.add(category);
            }
            return this;
        }

        @NonNull
        public Builder addControlCategories(@NonNull Collection<String> categories) {
            if (categories == null) {
                throw new IllegalArgumentException("categories must not be null");
            }
            if (!categories.isEmpty()) {
                for (String category : categories) {
                    addControlCategory(category);
                }
            }
            return this;
        }

        @NonNull
        public Builder addSelector(@NonNull MediaRouteSelector selector) {
            if (selector == null) {
                throw new IllegalArgumentException("selector must not be null");
            }
            addControlCategories(selector.getControlCategories());
            return this;
        }

        @NonNull
        public MediaRouteSelector build() {
            if (this.mControlCategories == null) {
                return MediaRouteSelector.EMPTY;
            }
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(MediaRouteSelector.KEY_CONTROL_CATEGORIES, this.mControlCategories);
            return new MediaRouteSelector(bundle, this.mControlCategories);
        }
    }
}
