package com.transapp.languagetranslatorpro;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mob.adjplugin.AdjConfig;
import com.mob.coresdk.MobConfig;
import com.mob.vldplugin.VldConfig;

public class App extends Application {
    public VldConfig vldConfig;
    public MobConfig coreConfig;
    public static AdjConfig adj_config;

    @Override
    public void onCreate() {
        super.onCreate();

        coreConfig = new MobConfig();
        coreConfig.setContext(this);

        vldConfig = new VldConfig(this);

        adj_config = new AdjConfig(this, "y4zgzijjzjsw");
        adj_config.setAttrLogEventToken("sb5kzq");
        adj_config.setFirebaseInstanceIDToken("rqrr4n");
        adj_config.onCreate(adj_config);
      //  Mob.onCreate(coreConfig);
        registerActivityLifecycleCallbacks(new MobLifecycleCallbacks());
    }

    private static final class MobLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityResumed(Activity activity) {
            adj_config.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            adj_config.onPause();
        }

        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
        }
    }
}
