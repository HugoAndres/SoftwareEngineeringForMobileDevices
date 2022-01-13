package mx.ipn.escom.plantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mx.ipn.escom.plantapp.utilidades.Utilidades;

public class verPlantaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_planta);


        TextView nombre = (TextView) findViewById(R.id.textViewNombrePlanta);
        TextView especie = (TextView) findViewById(R.id.textViewEspecie);
        TextView tipo = (TextView) findViewById(R.id.textViewTipo);
        TextView edad = (TextView) findViewById(R.id.textViewEdad);
        TextView maceta = (TextView) findViewById(R.id.textViewMaceta);
        TextView detalles = (TextView) findViewById(R.id.textViewDetalles);
        ImageView imagen = (ImageView) findViewById(R.id.imageViewPlantaImg);

        Intent intent = getIntent();
        int index_seleccionado =intent.getIntExtra("ITEM_SELECCIONADO",-1);
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_plantapp",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();

        String sql = "SELECT "+ Utilidades.CAMPO_ESPECIE_PLANTA+ " , "+Utilidades.CAMPO_TIPO_PLANTA+" , "+ Utilidades.CAMPO_EDAD_PLANTA+" , "+Utilidades.CAMPO_IMAGEN_PLANTA+" , "+Utilidades.CAMPO_DETALLES_PLANTA+" , "+Utilidades.CAMPO_MACETA+" FROM "+Utilidades.TABLA_PLANTAS+" WHERE "+Utilidades.CAMPO_ID_PLANTA+" = "+index_seleccionado;
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        nombre.setText(cursor.getString(0));
        especie.setText("Especie: "+cursor.getString(0));


        if(cursor.getString(1).equals("")){
            tipo.setText("Tipo: Sin tipo especificado");
        }else{
            tipo.setText("Tipo: "+cursor.getString(1));
        }
        if(cursor.getString(2).equals("")){
            edad.setText("Edad: Sin edad especificada");
        }else{
            edad.setText("Edad: "+cursor.getString(2));
        }

        byte [] byteArray = cursor.getBlob(3);
        if(byteArray.length!=0){
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imagen.setImageBitmap(bmp);
        }

        detalles.setText("Detalles: "+cursor.getString(4));
        if(cursor.getString(5).equals("1")){
            maceta.setText("Maceta: Si");
        }else{
            maceta.setText("Maceta: No");
        }


        cursor.close();

    /*
        if(cursor.moveToFirst()){
            do{
                if(!cursor.isClosed()){
                    @SuppressLint("Range") Integer planta_id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_ID_PLANTA)));
                    ListaIDs.add(planta_id);
                }
                if(cursor.isLast()){
                    cursor.close();
                }

            }while(!cursor.isClosed() && cursor.moveToNext());
        }

        */

        db.close();



    }
}