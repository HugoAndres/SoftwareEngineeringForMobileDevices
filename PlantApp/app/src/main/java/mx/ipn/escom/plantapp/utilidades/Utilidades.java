package mx.ipn.escom.plantapp.utilidades;

public class Utilidades {

    //Constantes campos tabla usuario
    public static final String TABLA_USUARIO="usuario";
    public static final String CAMPO_ID_USUARIO="id_usuario";
    public static final String CAMPO_NOMBRE_USUARIO="nombre_usuario";
    public static final String CAMPO_CORREO_USUARIO="correo_usuario";
    public static final String CAMPO_PASSWORD_USUARIO="password_usuario";
    //Constantes campos tabla plantas
    public static final String TABLA_PLANTAS="plantas";
    public static final String CAMPO_ID_PLANTA="id_planta";
    public static final String CAMPO_ESPECIE_PLANTA="especie_planta";
    public static final String CAMPO_TIPO_PLANTA="tipo_planta";
    public static final String CAMPO_EDAD_PLANTA="edad_planta";
    public static final String CAMPO_DETALLES_PLANTA="detalles_planta";
    public static final String CAMPO_IMAGEN_PLANTA="imagen_planta";
    public static final String CAMPO_MACETA="maceta";



    public static final String CREAR_TABLA_USUARIO="CREATE TABLE "+TABLA_USUARIO+" ( "+CAMPO_ID_USUARIO+" INTEGER PRIMARY KEY, "+CAMPO_NOMBRE_USUARIO+" TEXT NOT NULL, "+CAMPO_CORREO_USUARIO+" TEXT NOT NULL, "+CAMPO_PASSWORD_USUARIO+" TEXT NOT NULL)";
    public static final String CREAR_TABLA_PLANTAS="CREATE TABLE "+TABLA_PLANTAS+" ( "+CAMPO_ID_PLANTA+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_ESPECIE_PLANTA+" TEXT NOT NULL, "+CAMPO_TIPO_PLANTA+" TEXT, "+CAMPO_EDAD_PLANTA+" TEXT, "+CAMPO_DETALLES_PLANTA+" TEXT NOT NULL, "+CAMPO_IMAGEN_PLANTA+" BLOB, "+CAMPO_MACETA+" BIT NOT NULL)";


    public static final String BORRAR_USUARIO = "DELETE FROM "+TABLA_USUARIO+" WHERE "+CAMPO_ID_USUARIO+" = 1";
    public static final String BORRAR_PLANTA = "DELETE FROM "+TABLA_PLANTAS+" WHERE "+CAMPO_ID_PLANTA+" = ";
}
