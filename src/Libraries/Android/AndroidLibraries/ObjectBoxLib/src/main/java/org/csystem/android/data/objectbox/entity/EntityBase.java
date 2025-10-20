package org.csystem.android.data.objectbox.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class EntityBase {
    @Id
    public long id;

    public EntityBase()
    {
    }
    public EntityBase(long id)
    {
        this.id = id;
    }
}
