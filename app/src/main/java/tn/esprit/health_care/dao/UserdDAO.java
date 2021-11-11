package tn.esprit.health_care.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import tn.esprit.health_care.entities.Infermier;
import tn.esprit.health_care.entities.User;
import tn.esprit.health_care.entities.UserInfermier;

@Dao
public interface UserdDAO {

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("select * from User where username=(:username) and password=(:password)")
    User login(String username , String password );

    @Query("select * from User where email=(:email) or username=(:username)")
    User register(String username , String email );

    @Query("select User.phone from User where id=(:idUser)")
    public String getPhoneByIdUser(int idUser);

    @Query("select * from User where id=(:idUser)")
    public User getUserById(int idUser);

    @Query("UPDATE User set username=(:username),email=(:email),phone=(:phone)where id=(:iduser)")
    public int updateUser (String username , String email , String phone , int iduser);

    @Query("UPDATE User set password=(:password) where id=(:iduser)")
    public int updatePasswordUser (String password , int iduser);

    @Query("select User.password from User where email=(:email)")
    public String getPasswordByEmail(String email);



    @Query("select User.username from User where id=(:idUser)")
    public String getUsernameByIdUser(int idUser);

    @Transaction
    @Query("SELECT * FROM User")
    public List<UserInfermier> getUsersWithInf();



    



}
