package ubb.scs.map.zboruri.domain;

import java.io.Serial;
import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private ID id;

    public ID getId() {
        return this.id;
    }

    public void setId(ID otherId) {
        this.id = otherId;
    }

}