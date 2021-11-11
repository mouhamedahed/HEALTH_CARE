package tn.esprit.health_care.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Infermier {
    @PrimaryKey(autoGenerate = true)
    private int idf ;
    @ColumnInfo
    private String dep ;
    @ColumnInfo
    private String localisation ;
    @ColumnInfo
    private String price ;
    @ColumnInfo
    private int idUser ;

    public Infermier() {
    }

    public int getIdf() {
        return idf;
    }

    public void setIdf(int idf) {
        this.idf = idf;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Infermier(String dep, String localisation, String price, int idUser) {
        this.dep = dep;
        this.localisation = localisation;
        this.price = price;
        this.idUser = idUser;
    }
}
