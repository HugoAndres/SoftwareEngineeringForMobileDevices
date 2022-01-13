package mx.ipn.escom.plantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mx.ipn.escom.plantapp.utilidades.Utilidades;

public class iniciarSesionActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);


    }

    public void gotoRegistroUsuario(View view) {
        startActivity(new Intent(iniciarSesionActivity.this, registroUsuarioActivity.class));
    }

    public void gotoMisPlantas(View View){
        startActivity(new Intent(iniciarSesionActivity.this, misPlantas.class));
    }


    public void IniciarSesion(View view) {
        EditText correo = (EditText) findViewById(R.id.campoCorreoUsuario);
        EditText password = (EditText) findViewById(R.id.campoPasswordUsuario);
        try{

            String correo_usuario = consultarCorreoUsuario();
            String password_usuario = consultarPasswordUsuario();
            if(correo.getText().toString().contains("@")){
                if(correo.getText().toString().equals(correo_usuario) && password.getText().toString().equals(password_usuario)){
                    correo.setText("");
                    password.setText("");
                    gotoMisPlantas(view);


                }else{
                    //Toast.makeText(getApplicationContext(), password_usuario, Toast.LENGTH_SHORT).show();


                    Toast.makeText(getApplicationContext(), "correo y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(getApplicationContext(), "El correo electronico debe de estar en formato 'example@example.com'", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "¡Ooops parece que aun no estas registrado!", Toast.LENGTH_SHORT).show();
        }


    }

    private String consultarCorreoUsuario() {
        String correo;

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_plantapp",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {"0"};
        String[] campos = {Utilidades.CAMPO_CORREO_USUARIO};

        String sql = "SELECT "+Utilidades.CAMPO_CORREO_USUARIO+ " FROM "+Utilidades.TABLA_USUARIO+"";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        correo = cursor.getString(0);



        db.close();
        return correo;
    }
    private String consultarPasswordUsuario() {
        String password;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_plantapp",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {"0"};
        String[] campos = {Utilidades.CAMPO_PASSWORD_USUARIO};

        String sql = "SELECT "+Utilidades.CAMPO_PASSWORD_USUARIO+ " FROM "+Utilidades.TABLA_USUARIO+"";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        password = cursor.getString(0);
        db.close();
        return password;
    }
}