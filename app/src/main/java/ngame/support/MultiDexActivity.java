package ngame.support;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import java.io.File;

public class MultiDexActivity extends Activity {
    /* access modifiers changed from: private */
    public static final String TAG = MultiDexActivity.class.getSimpleName();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        new MultidexTask().execute(new Void[0]);
    }

    private class MultidexTask extends AsyncTask<Void, Void, Void> {
        private MultidexTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... params) {
            Log.d(MultiDexActivity.TAG, "MultidexTask start");
            try {
                MultiDex.install(MultiDexActivity.this.getApplicationContext());
                return null;
            } catch (Exception e) {
                Log.w(MultiDexActivity.TAG, Log.getStackTraceString(e));
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            Log.d(MultiDexActivity.TAG, "MultidexTask stop");
            MultiDexActivity.this.deleteMarkFile();
            MultiDexActivity.this.finish();
        }
    }

    /* access modifiers changed from: private */
    public void deleteMarkFile() {
        File file = new File(getFilesDir().getAbsolutePath(), "multidex_mark_file");
        Log.d(TAG, "mark file path:" + file.getAbsolutePath());
        if (file.exists()) {
            file.delete();
        } else {
            Log.w(TAG, "mark file not exist, can not delete");
        }
    }
}
