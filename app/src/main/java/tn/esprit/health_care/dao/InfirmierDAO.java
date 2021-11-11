package tn.esprit.health_care.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tn.esprit.health_care.entities.Infermier;

@Dao
public interface InfirmierDAO {

    @Insert
    void insertOne(Infermier infermier);

    @Delete
    void deleteInf(Infermier infermier);

    @Query("select * from Infermier")
    List<Infermier> getAllInf();
}
