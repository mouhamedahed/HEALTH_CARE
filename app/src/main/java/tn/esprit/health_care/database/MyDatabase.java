package tn.esprit.health_care.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tn.esprit.health_care.dao.InfirmierDAO;
import tn.esprit.health_care.dao.UserdDAO;
import tn.esprit.health_care.entities.Infermier;
import tn.esprit.health_care.entities.User;

@Database(entities = {User.class, Infermier.class},version = 9,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase instance ;
    public abstract UserdDAO userdDAO();
    public abstract InfirmierDAO infirmierDAO();

    public static MyDatabase getDatabase(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),MyDatabase.class,"my_db")
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance ;
    }
}
