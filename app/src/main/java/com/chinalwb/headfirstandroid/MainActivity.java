package com.chinalwb.headfirstandroid;

import android.app.Activity;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button send = this.findViewById(R.id.send_button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Hello");

//                Intent newIntent = new Intent(Intent.ACTION_CHOOSER);
//                newIntent.putExtra(Intent.EXTRA_INTENT, intent);
//                newIntent.putExtra(Intent.EXTRA_TITLE, "Test Chooser");
//                startActivity(newIntent);

                PackageManager pm = getPackageManager();
//                ComponentName componentName = intent.resolveActivity(packageManager);

                ResolveInfo info = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
                String packageName = info.activityInfo.applicationInfo.packageName;
                String className = info.activityInfo.name;
                Log.e("XX", "package name  == " + packageName + ", className == " + className);
            }
        });
    }
}
