package com.xingyi.simpletodo;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by ValuedAcerCustomer on 16/1/2017.
 */

@Database(name = TaskDatabase.NAME, version = TaskDatabase.VERSION)
public class TaskDatabase {
    public static final String NAME = "TaskDb";

    public static final int VERSION = 1;
}
