package mx.ipn.escom.plantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mx.ipn.escom.plantapp.utilidades.Utilidades;

public class misPlantas extends AppCompatActivity {
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_plantas);
        TextView saludo=findViewById(R.id.textViewmisplantas);

        String nombre_usuario = consultarNombreUsuario();
        saludo.setText("Hola "+ nombre_usuario +", estas son tus plantas:");


        String [] listaPlantas = consultarPlantas();
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,listaPlantas));
        /*
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,listaPlantas, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);*/
    }

    private String consultarNombreUsuario() {
        String nombre;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_plantapp",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();


        String sql = "SELECT "+Utilidades.CAMPO_NOMBRE_USUARIO+ " FROM "+Utilidades.TABLA_USUARIO+"";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        nombre = cursor.getString(0);
        db.close();
        return nombre;
    }

    @SuppressLint("Range")
    private String[] consultarPlantas() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_plantapp",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();

        String sql = "SELECT "+Utilidades.CAMPO_ESPECIE_PLANTA+ " FROM "+Utilidades.TABLA_PLANTAS+"";
        Cursor cursor = db.rawQuery(sql,null);


        ArrayList<String> ListaPlantas = new ArrayList<String>();



        if(cursor.moveToFirst()){
            do{
                if(!cursor.isClosed()){
                    String planta = cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_ESPECIE_PLANTA));
                    ListaPlantas.add(planta);
                }
                if(cursor.isLast()){
                    cursor.close();
                }

            }while(!cursor.isClosed() && cursor.moveToNext());
        }
        db.close();

        return ListaPlantas.toArray(new String[ListaPlantas.size()]);
    }

    private Integer[] consultarIDplantas() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_plantapp",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();

        String sql = "SELECT "+Utilidades.CAMPO_ID_PLANTA+ " FROM "+Utilidades.TABLA_PLANTAS+"";
        Cursor cursor = db.rawQuery(sql,null);


        ArrayList<Integer> ListaIDs = new ArrayList<Integer>();



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
        db.close();

        return ListaIDs.toArray(new Integer[ListaIDs.size()]);
    }





    public void gotoRegistroPlanta(View view) {
        startActivity(new Intent(misPlantas.this, registroPlantaActivity.class));
    }

    public void gotoVerPlanta(View view) {
        startActivity(new Intent(misPlantas.this, verPlantaActivity.class));
    }

    public void seleccionarPlanta (View view){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {

            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_plantapp",null,1);
            long ItemId = spinner.getSelectedItemId();
            Integer[] PlantasIds=consultarIDplantas();
            int Id_Planta_seleccionada =PlantasIds[(int) ItemId];

            Intent intent = new Intent(getApplicationContext(), verPlantaActivity.class);
            intent.putExtra("ITEM_SELECCIONADO", Id_Planta_seleccionada);
            //Toast.makeText(getApplicationContext(), ""+Id_Planta_seleccionada, Toast.LENGTH_SHORT).show();
            startActivity(intent);
            //gotoVerPlanta(view);
        }else{
            Toast.makeText(getApplicationContext(), "¡Al parecer no tienes ninguna planta seleccionada!", Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizarPlantas(View view) {
        String [] listaPlantas = consultarPlantas();
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,listaPlantas));
    }

    public void borrarPlanta(View view){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_plantapp",null,1);
            long ItemId = spinner.getSelectedItemId();
            Integer[] PlantasIds=consultarIDplantas();
            Integer Id_Planta_borrar =PlantasIds[(int) ItemId];
            SQLiteDatabase db = conn.getWritableDatabase();
            db.execSQL(Utilidades.BORRAR_PLANTA+Id_Planta_borrar);
            Toast.makeText(getApplicationContext(), "Planta borrada con éxito", Toast.LENGTH_SHORT).show();

            db.close();

        }else{
            Toast.makeText(getApplicationContext(), "¡Al parecer no tienes ninguna planta seleccionada!", Toast.LENGTH_SHORT).show();
        }

        actualizarPlantas(view);
    }


}