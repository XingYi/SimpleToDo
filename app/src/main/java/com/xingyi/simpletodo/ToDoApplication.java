package com.xingyi.simpletodo;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by ValuedAcerCustomer on 17/1/2017.
 */

public class ToDoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // This instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
        // add for verbose logging
        //FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
    }
}
