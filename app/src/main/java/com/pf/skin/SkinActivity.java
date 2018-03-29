package com.pf.skin;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pf.skin_core.SkinManager;

public class SkinActivity extends AppCompatActivity {

    private static final String TAG = "SkinActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
    }

    public void change(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.e(TAG, "change: path:" + path);
        String skinPath = path + "/app-skin-debug.skin";
        boolean result = SkinManager.getInstance().loadSkin(skinPath);
        // 换肤
        Toast.makeText(this, "" + result, Toast.LENGTH_SHORT).show();
    }

    public void restore(View view) {
        SkinManager.getInstance().loadSkin(null);
    }
}