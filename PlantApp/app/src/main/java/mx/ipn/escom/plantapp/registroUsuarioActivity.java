package mx.ipn.escom.plantapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import mx.ipn.escom.plantapp.utilidades.Utilidades;

public class registroUsuarioActivity extends AppCompatActivity {

    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        //ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_plantapp",null,1);

        if(EstaRegistrado()){

        }

    }

    private boolean EstaRegistrado() {



        return true;

    }

    public void cargarImagen(View View){
        imagen=(ImageView) findViewById(R.id.campoImagenPlanta);
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            imagen.setImageURI(path);
        }
    }

    public void registroUsuario(View view) {


        EditText nombre = (EditText)findViewById(R.id.campoNombreUsuario);
        EditText correo = (EditText)findViewById(R.id.campoCorreoUsuario);
        EditText password = (EditText)findViewById(R.id.campoPasswordUsuario);

        if(correo.getText().toString().contains("@")){
            if(!password.getText().toString().equals("") && !correo.getText().toString().equals("") && !nombre.getText().toString().equals("")) {
                if(!password.getText().toString().contains(" ")) {
                    ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_plantapp", null, 1);
                    SQLiteDatabase db = conn.getWritableDatabase();

                    //Asegurarse de que no haya otro usuario
                    db.execSQL(Utilidades.BORRAR_USUARIO);

                    ContentValues values = new ContentValues();
                    values.put(Utilidades.CAMPO_CORREO_USUARIO, correo.getText().toString());
                    values.put(Utilidades.CAMPO_NOMBRE_USUARIO, nombre.getText().toString());
                    values.put(Utilidades.CAMPO_ID_USUARIO, 1);
                    values.put(Utilidades.CAMPO_PASSWORD_USUARIO, password.getText().toString());

                    long idResultante = db.insert(Utilidades.TABLA_USUARIO, Utilidades.CAMPO_ID_USUARIO, values);
                    if (idResultante != -1) {
                        nombre.setText("");
                        correo.setText("");
                        password.setText("");
                        Toast.makeText(getApplicationContext(), "¡Haz sido registrado exitosamente!", Toast.LENGTH_SHORT).show();
                        db.close();
                        gotoIniciarSesion(view);
                    } else {
                        Toast.makeText(getApplicationContext(), "id: " + idResultante, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "La contraseña no puede contener espacios.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Debes de completar todos los campos para registrarte.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "El correo electrónico debe de estar en formato example@example.com", Toast.LENGTH_SHORT).show();
        }

    }

    public void gotoIniciarSesion(View view) {
        startActivity(new Intent(registroUsuarioActivity.this, iniciarSesionActivity.class));
    }


}