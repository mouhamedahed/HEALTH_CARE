package tn.esprit.health_care;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import health_care.R;
import tn.esprit.health_care.database.MyDatabase;

public class ForgottenPassword extends AppCompatActivity {

    EditText emailTo ;
    Button resetBtn ;
    String sEmail , sPassword ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);

        emailTo = findViewById(R.id.emailTo);
        resetBtn = findViewById(R.id.resetBtn);

        sEmail = "ghassen.gataa@esprit.tn";
        sPassword = "204JMT4146";

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize properties
                Properties properties = new Properties();
                properties.put("mail.smtp.auth","true");
                properties.put("mail.smtp.starttls.enable","true");
                properties.put("mail.smtp.host","smtp.gmail.com");
                properties.put("mail.smtp.port","587");

                //Initialize Session
                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sEmail,sPassword);
                    }
                });


                try {
                    //Initialize email content
                    Message message = new MimeMessage(session);
                    //Sender email
                    message.setFrom(new InternetAddress(sEmail));
                    //Recipient email
                    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailTo.getText().toString().trim()));
                    //Email subject
                    message.setSubject("Reset Password");
                    //Email message
                    MyDatabase myDB = Room.databaseBuilder(getApplicationContext(),MyDatabase.class,"my_db")
                            .allowMainThreadQueries().build();
                    String pass = myDB.userdDAO().getPasswordByEmail(emailTo.getText().toString());
                    message.setText("welcome to our company this is your password  :"+pass+"have a nice day");

                    //Send Email
                    new SendMail().execute(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    private class SendMail extends AsyncTask<Message,String,String> {
        //Initialize progress dialog
        private ProgressDialog progressDialog ;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //Create and show progress dialog
            progressDialog = ProgressDialog.show(ForgottenPassword.this,"Please wait","Sending Mail..."
            ,true ,false);
        }

        @Override
        protected String doInBackground(Message... messages){
            try {
                //When success
                Transport.send(messages[0]);
                return "Success";
                //When error
            } catch (MessagingException e){
                e.printStackTrace();
                return "Error";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Dismiss progress dialog
            progressDialog.dismiss();
            if (s.equals("Success")) {
                //When Success
                //Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ForgottenPassword.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'>Success</font>"));
                builder.setMessage("Mail send successfully !");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        emailTo.setText("");
                    }
                });
                //Show alert dialog0
                builder.show();
            }else {
                //When error
                Toast.makeText(getApplicationContext(),"Something went wrong ? ",Toast.LENGTH_SHORT).show();
            }
        }
    }
}