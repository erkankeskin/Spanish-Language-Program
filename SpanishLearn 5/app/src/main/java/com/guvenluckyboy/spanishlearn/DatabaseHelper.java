package com.guvenluckyboy.spanishlearn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by güvenluckyboy on 2.12.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String  DATABASE_NAME = "appDB.db",
                                cumleLevel = "user_level",
                                cumleCOL_1 = "ID",
                                cumleCOL_2 = "LEVEL",
                                cumleStrings = "cumle_strings",
                                cumleStrCOL_1 = "ID",
                                cumleStrCOL_2 = "WRONG_SENTENCE",
                                cumleStrCOL_3 = "TRUE_SENTENCE",
                                dialogLevel = "dialog_level",
                                dialogCOL_1 = "grade",
                                dialogCOL_2 = "level",
                                dialogCOL_3 = "wait_point",
                                dialogStrings ="dialog_strings",
                                dialogStrCOL_1 = "grade",
                                dialogStrCOL_2 = "level",
                                dialogStrCOL_3 = "dialog",
                                kelimeLevel = "kelime_level",
                                kelimeCOL_1="ID",
                                kelimeCOL_2="LEVEL",
                                kelimeStrings = "kelime_strings",
                                kelimeStrCOL_1="ID",
                                kelimeStrCOL_2="kelime",
                                kelimeStrCOL_3="anlam";

    String[] kelimee = {"manzana",
            "cama",
            "gato",
            "libro",
            "feliz",
            "desayuno",
            "leer",
            "rey",
            "amor",
            "reloj",
            "café",
            "cuchillo",
            "lluvia",
            "noche",
            "cocina",
            "puerta",
            "coche",
            "helado",
            "mesa",
            "invierno",
            "mar",
            "futuro",
            "volver",
            "computador",
            "camisa",
            "comida",
            "semana",
            "enfermera",
            "sillón",
            "humanidad",
            "rodilla",
            "dolor",
            "espacio",
            "corbata",
            "carretera",
            "alfombra",
            "valiente",
            "martillo",
            "flor",
            "lapiz"
    };

    String[] anlam = {"elma",
            "yatak",
            "kedi",
            "kitap",
            "mutlu",
            "kahvaltı",
            "okumak",
            "kral",
            "aşk",
            "saat",
            "kahve",
            "bıçak",
            "yağmur",
            "gece",
            "mutfak",
            "kapı",
            "araba",
            "dondurma",
            "masa",
            "kış",
            "deniz",
            "gelecek",
            "geri dönmek",
            "bilgisayar",
            "gömlek",
            "yemek",
            "hafta",
            "hemşire",
            "koltuk",
            "insanlık",
            "diz",
            "acı",
            "uzay",
            "kıravat",
            "yol",
            "halı",
            "cesur",
            "çekiç",
            "çiçek",
            "kurşun kalem"
    };


    String[] wrongSeq = {"está relleno. pan El",
            "no hoy. Marisa vino",
            "pesos. Esto treinta cuesta",
            "El ahora. sale autobús" ,
            "rota. silla está La" ,
            "Estoy la abuela. esperando" ,
            "un Tengo pequeño. hijo" ,
            "es nuevo. Mi auto" ,
            "Él mucho. está dormiendo" ,
            "lavada. alfombra La fue",

            "auto Quiero un comprar mejor." ,
            "hacer la debes tarea. Tú" ,
            "muy Ella camina despacio. siempre" ,
            "Te peinas una reina. como" ,
            "dejes No la nevera abierta." ,
            "voy mañana. de viaje Me" ,
            "Quieres galletitas té con un?" ,
            "basura hoy Saqué temprano. la" ,
            "gustaría Me tener más dinero." ,
            "amiga. con Estoy mi contenta",

            "Necesito mojar las plantas más veces." ,
            "La mesa es grande y ancha." ,
            "Elsol es un planeta solar." ,
            "Mi reloj amarillo no funciona más." ,
            "No me gusta el vino caliente." ,
            "La taza está en la mesa." ,
            "Una mujer se cayó del techo." ,
            "Me sentí mal a la mañana." ,
            "Los vecinos se quejan de ruído." ,
            "La cucaracha está abajo del sofá.",

            "Yo pienso que el mundo anda mal." ,
            "Los inviernos de turquía son muy árduos." ,
            "Las páginas de este periódico están amarillas." ,
            "El sábio tiempo tiene respuesta para todo." ,
            "Hay muchos árboles frutales en este parque." ,
            "Me gustaría proponer algunos nuevos ideales aquí." ,
            "Nadie apareció en la fiesta de hoy." ,
            "María no me quiso dar la mochila." ,
            "Mi teléfono sonó hoy a la mañana." ,
            "Mi madre me dijo para no dormir."
    };
    String[] realStr={"El pan está relleno.",
            "Marisa no vino hoy.",
            "Esto cuesta treinta pesos.",
            "El autobús sale ahora." ,
            "La silla está rota." ,
            "Estoy esperando la abuela." ,
            "Tengo un hijo pequeño." ,
            "Mi auto es nuevo." ,
            "Él está dormiendo mucho." ,
            "La alfombra fue lavada.",

            "Quiero comprar un auto mejor." ,
            "Tú debes hacer la tarea." ,
            "Ella siempre camina muy despacio." ,
            "Te peinas como una reina." ,
            "No dejes la nevera abierta." ,
            "Me voy de viaje mañana." ,
            "Quieres un té con galletitas?" ,
            "Saqué la basura hoy temprano." ,
            "Me gustaría tener más dinero." ,
            "Estoy contenta con mi amiga.",

            "Necesito mojar las plantas más veces." ,
            "La mesa es grande y ancha." ,
            "Elsol es un planeta solar." ,
            "Mi reloj amarillo no funciona más." ,
            "No me gusta el vino caliente." ,
            "La taza está en la mesa." ,
            "Una mujer se cayó del techo." ,
            "Me sentí mal a la mañana." ,
            "Los vecinos se quejan de ruído." ,
            "La cucaracha está abajo del sofá.",

            "Yo pienso que el mundo anda mal." ,
            "Los inviernos de turquía son muy árduos." ,
            "Las páginas de este periódico están amarillas." ,
            "El sábio tiempo tiene respuesta para todo." ,
            "Hay muchos árboles frutales en este parque." ,
            "Me gustaría proponer algunos nuevos ideales aquí." ,
            "Nadie apareció en la fiesta de hoy." ,
            "María no me quiso dar la mochila." ,
            "Mi teléfono sonó hoy a la mañana." ,
            "Mi madre me dijo para no dormir."
    };


    String[] dialog = {"2-¡hola!-1-Ah! ¡Hola! ¿Qué tal?-2-Muy bien. ¿Cómo te llamas?-1-Me llamo sistema. Y tú, ¿cómo te llamas?-"+
                        "2-Me llamo Sergio. ¿De dónde eres?-1-Soy de aquí, de Estambul, Turquía.-2-Yo soy de francés. ¡Mucho gusto de conocerte!-"+
                        "1-¡El gusto es mío!",

                        "1-Actualmente, trabajas como ingeniero?" +
                        "-2-Sí, trabajo como ingeniero desde 1990." +
                        "-1-Y te gusta?" +
                        "-2-Hay mucho que hacer, pero pagan bien." +
                        "-1-Qué bien! Suerte y que te vaya bien." +
                        "-2-Muchas gracias, a tí también, saludos a los niños." +
                        "-1-Gracias. Les mandaré.",

                        "1-No te olvides de pagar la tasa de inscripción en el banco." +
                        "-2-Las clases cuándo empiezan?" +
                        "-1-El próximo lunes, 19 de Abril." +
                        "-2-Hay mucha gente ya inscripta?" +
                        "-1-Diez estudiantes hasta ahora, pero no te preocupes, pues las clases son bastante anchas." +
                        "-2-Gracias, señora. Buen día!" +
                        "-1-De n ada. Igualmente.",

                        "1-Oye, juan, qué vas a hacer hoy por la noche?" +
                        "-2-No sé, creo que voy a dormir. Siempre me acuesto temprano." +
                        "-1-Vamos, hombre, no seas flojo, anímate!" +
                        "-2-Está bien, manoel, qué no hago por los amigos?" +
                        "-1-Estupendo! Entonces nos vemos por la noche, de acuerdo? A las 8 en la casa de teresa." +
                        "-2-Vale, hasta pronto.",

                        "1-El invierno pasado fue muy frío." +
                        "-2-Sí, este año no hace tanto frío, igual abrígate bien, porque el clima puede ponerse más pesado." +
                        "-1-Es cierto. Voy a correr por el parque ahora. Quieres venir?" +
                        "-2-Estoy muy atareada, más tarde hablamos.",

                        "1-Mis padres van a llegar de viaje hoy. Ya dejé todo ordenado y comida en la nevera. Mi madre se enoja si todo no está arreglado." +
                        "-2-Qué buena hija eres!" +
                        "-1-Es que van a llegar cansados. Son muchas horas de vuelo." +
                        "-2-Y disfrutaron?" +
                        "-1-Sí, bastante. Van a llegar contentos." +
                        "-1-Me alegro mucho.",

                        "1- Me he enterado de la triste notícia. Lo siento mucho. Ojalá puedas descansar un poco." +
                        "-2-Gracias, anita, por tu consideración. Mi abuelo falleció ya muy enfermo. Hace mucho venía sufriendo en el hospital." +
                        "-1-Si necesitas de algo, por favor, avísame." +
                        "-2-Vale. Muchas gracias. Por ahora estamos bien. Ya todo se terminó." +
                        "-1-Sí, y seguiremos orando por su alma.",

                        "1-Tengo intención de alquilar un pequenõ departamento este año." +
                        "-2-Eso es cierto?" +
                        "-1-Sí, pero quiero que la renta no sea tan cara." +
                        "-2-Puedo ayudarte a buscar. Me voy a fijar en algunos periodicos." +
                        "-1-Sería perfecto. Me gustaría que sea sólo 1 año de contrato." +
                        "-2-Bueno, qué así sea.",

                        "1-Me pongo felices que ustedes se hicieron amigos." +
                        "-2-Es muy buena persona, manolo." +
                        "-1-Es verdad. Lo conozco hace diez años." +
                        "-2-Tanto? Qué guay!" +
                        "-1-Sí. Una lástima que vivimos lejos, pero antes eramos vecinos." +
                        "-2-La amistad sigue mismo de lejos. Él te quiere mucho." +
                        "-1-Y yo a él.",

                        "1-Cocinas bien o mal?" +
                        "-2-Sé hacer lo básico. Y tú?" +
                        "-1-Me encanta cocinar pollo, arroz con pescado, cosas dulces también." +
                        "-2-Qué rico! Invítame un día." +
                        "-1-Pues sí. Hay que arreglar pronto." +
                        "-2-Vale. Yo llevo un vino suave.",

                        "1-Tengo dolor de espalda y en las rodillas, doctor." +
                        "-2-Es mala postura. Ponte derecha que vas a ver como mejoras." +
                        "-1-Hay alguna medicina para el dolor?" +
                        "-2-Pues sí, ahora te escribo la receta." +
                        "-1-Gracias, doctor. Voy a cuidar mi postura también." +
                        "-2-Así es. Una buena postura es una buena salúd.",


                        "1-Perdón, me gustaría ir hasta la plaza de mayo. Cuál metro debo tomar?" +
                        "-2-Linea b, es una linea azul y bajas en la estación del mismo nombre." +
                        "-1-Vale. La plaza está muy lejos de ahi?" +
                        "-2-Dos cuadras. Caminás todo derecho." +
                        "-1- Excelente. Gracias por tomar tu tiempo." +
                        "-2-De nada."};


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + cumleLevel + " (" +
                cumleCOL_1 + " integer primary key autoincrement," +
                cumleCOL_2 + " integer)");

        ContentValues contentValues = new ContentValues();
        contentValues.put(cumleCOL_2, 0);
        for (int i=0;i<4;i++)
            db.insert(cumleLevel, null, contentValues);

        db.execSQL("create table " + cumleStrings + " (" +
                cumleStrCOL_1 + " integer primary key autoincrement," +
                cumleStrCOL_2 + " string,"+ cumleStrCOL_3+" string)");

        for(int i=0; i<wrongSeq.length; i++)
            db.execSQL("insert into " + cumleStrings + "(" + cumleStrCOL_2 + "," + cumleStrCOL_3 + ") values ('"+wrongSeq[i]+"','"+realStr[i]+"')");

        db.execSQL("create table " + dialogLevel + " (" +
                dialogCOL_1 + " integer," +
                dialogCOL_2 + " integer," + dialogCOL_3 + " integer)");

        for(int i=0; i<4; i++)
            for(int j=0; j<3; j++){
                db.execSQL("insert into " + dialogLevel + " values ("+ Integer.toString(i+1)+","+Integer.toString(j+1)+","+"0"+")");
            }

        db.execSQL("create table " + dialogStrings + " (" +
                dialogStrCOL_1 + " integer," +
                dialogStrCOL_2 + " integer," + dialogStrCOL_3 + "string)");

        for(int i=0; i<4; i++)
            for(int j=0; j<3; j++){
                db.execSQL("insert into " + dialogStrings + " values (" + Integer.toString(i + 1) + "," + Integer.toString(j + 1) + ",'" +dialog[i*3+j]+ "')");
            }

        db.execSQL("create table " + kelimeLevel + " (" +
                kelimeCOL_1 + " integer primary key autoincrement," +
                kelimeCOL_2 + " integer)");



        contentValues.put(kelimeCOL_2, 0);
        for (int i=0;i<4;i++)
            db.insert(kelimeLevel, null, contentValues);


        db.execSQL("create table " + kelimeStrings + " (" +
                kelimeStrCOL_1 + " integer primary key autoincrement," +
                kelimeStrCOL_2 + " string,"+
                kelimeStrCOL_3 + " string)"
                );



        for(int i=0; i<40; i++)
            db.execSQL("insert into " + kelimeStrings + "(" + kelimeStrCOL_2 + "," + kelimeStrCOL_3 + ") values ('"+kelimee[i]+"','"+anlam[i]+"')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + cumleLevel);
        onCreate(db);
    }

    public boolean insertData(int level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(cumleCOL_2, 0);
        long result = db.insert(cumleLevel,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getData(String TABLE_NAME,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where id=?",new String[] { Integer.toString(id) });
        return res;
    }

    public boolean updatelevel(int id, int level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(cumleCOL_1,id);
        contentValues.put(cumleCOL_2,level);

        db.update(cumleLevel, contentValues, "ID = ?",new String[] { Integer.toString(id) });
        return true;
    }

    public boolean updatekelimelevel(int id, int level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(kelimeCOL_1,id);
        contentValues.put(kelimeCOL_2,level);

        db.update(kelimeLevel, contentValues, "ID = ?",new String[] { Integer.toString(id) });
        return true;
    }

    public boolean updateDialogLevel(int grade,int level,int wait_point){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dialogCOL_1,grade);
        contentValues.put(dialogCOL_2,level);
        contentValues.put(dialogCOL_3,wait_point);

        db.update(dialogLevel, contentValues, dialogCOL_1+" = ? AND "+dialogCOL_2+" = ?" ,new String[] { Integer.toString(grade),Integer.toString(level)});

        return true;
    }

    public Cursor getDialogLevel(int grade,int level) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+dialogLevel+" where "+ dialogCOL_1 +"=? and "+ dialogCOL_2 +"=?",new String[] { Integer.toString(grade),Integer.toString(level)});
        return res;
    }

    public Cursor getDialogStr(int grade,int level) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+dialogStrings+" where "+ dialogStrCOL_1 +"=? and "+ dialogStrCOL_2 +"=?",new String[] { Integer.toString(grade),Integer.toString(level)});
        return res;
    }


}
