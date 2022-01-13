package mx.ipn.escom.plantapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import mx.ipn.escom.plantapp.utilidades.Utilidades;

public class registroPlantaActivity extends AppCompatActivity {

    ImageView imagen;
    Bitmap imagenBitmap;
    EditText tipo;
    EditText especie;
    EditText edad;
    EditText detalles;
    CheckBox maceta;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_planta);
        imagenBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.planta_default);
    }

    public void cargarImagen(View View){
        imagen =(ImageView) findViewById(R.id.campoImagenPlanta);
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            try {
                imagenBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "No sepudo cargar el Bitmap", Toast.LENGTH_SHORT).show();
            }
            imagen.setImageURI(path);

        }
    }

    public void registroPlanta(View view){

        especie = (EditText)findViewById(R.id.campoEspeciePlanta);
        tipo = (EditText)findViewById(R.id.campoTipoPlanta);
        edad = (EditText)findViewById(R.id.campoEdadPlanta);
        detalles = (EditText)findViewById(R.id.campoDetallesPlanta);
        imagen = (ImageView) findViewById(R.id.campoImagenPlanta);
        maceta = (CheckBox)findViewById(R.id.campoMacetaPlanta);


        //Uri imageUri = imagenV.getBitmap();
        /*
        Bitmap imagenBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.imagenV.getDrawable());
        */

        ////???
        //Bitmap imagenBitmap = ((BitmapDrawable)(imagenV.getDrawable())).getBitmap();
        ////???
        if(!especie.getText().toString().equals("")&&!detalles.getText().toString().equals("")){

            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            imagenBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
            byte [] imagenBytes = byteArray.toByteArray();

            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_plantapp",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_ID_PLANTA,id++);
            values.put(Utilidades.CAMPO_ESPECIE_PLANTA,especie.getText().toString());
            values.put(Utilidades.CAMPO_TIPO_PLANTA,tipo.getText().toString());
            values.put(Utilidades.CAMPO_EDAD_PLANTA,edad.getText().toString());
            values.put(Utilidades.CAMPO_DETALLES_PLANTA,detalles.getText().toString());
            values.put(Utilidades.CAMPO_IMAGEN_PLANTA,imagenBytes);
            values.put(Utilidades.CAMPO_MACETA, maceta.isChecked());


            long idResultante=db.insert(Utilidades.TABLA_PLANTAS, Utilidades.CAMPO_ID_PLANTA,values);

            if(idResultante ==-1){
                Toast.makeText(getApplicationContext(), "Planta no registrada, vuelvelo a intentar", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "¡Planta registrada exitósamente !", Toast.LENGTH_SHORT).show();
                especie.setText("");
                tipo.setText("");
                edad.setText("");
                detalles.setText("");
                maceta.setChecked(false);
                imagen.setImageResource(android.R.drawable.ic_input_add);
                imagenBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.planta_default);
                gotoMisPlantas(view);
            }
            db.close();



            //"@android:drawable/ic_input_add"

        }else{
            Toast.makeText(getApplicationContext(), "Al menos debes de ponerle un nombre y una descripción", Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoMisPlantas(View view) {
        startActivity(new Intent(registroPlantaActivity.this, misPlantas.class));
    }





}