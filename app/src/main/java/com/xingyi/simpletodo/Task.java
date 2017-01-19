package com.xingyi.simpletodo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Xing Yi on 16/1/2017.
 */
@Table(database = TaskDatabase.class)
public class Task extends BaseModel {
    // Leave this column public
    @PrimaryKey(autoincrement = true)
    @Column
    int id;

    @Column
    String name;

    @Column
    String description;

    @Column
    String dueDate;

    @Column(defaultValue = "0")
    boolean status;

    @Column
    long dateCreated;
    //Date dateCreated;

    public void setId(int id) { this.id = id; }
    public void setName(String name) {
        this.name = name;
    }
    public void setDateCreated(long dateCreated) { this.dateCreated = dateCreated; }
}
