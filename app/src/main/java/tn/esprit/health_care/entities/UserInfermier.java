package tn.esprit.health_care.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserInfermier {
    @Embedded public User user ;
    @Relation(
            parentColumn = "id",
            entityColumn = "idUser"
    )
    public List<Infermier> infermiers;

    public UserInfermier(User user, List<Infermier> infermiers) {
        this.user = user;
        this.infermiers = infermiers;
    }

    public UserInfermier() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Infermier> getInfermiers() {
        return infermiers;
    }

    public void setInfermiers(List<Infermier> infermiers) {
        this.infermiers = infermiers;
    }

    @Override
    public String toString() {
        return "UserInfermier{" +
                "user=" + user +
                ", infermiers=" + infermiers +
                '}';
    }
}
