package unitec.servicioandroid;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.web.client.RestTemplate;


public class MainActivity extends ActionBarActivity {

    String urlBase="http://raton-ley.rhcloud.com/tarjeta";
    String informacion="antes de conectarte";
    EditText textoNombre;
    EditText textoDiaCorte;
    Tarjeta t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    Button boton=(Button) findViewById(R.id.botonGuardar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TareaAsincronicaGuardarTarjeta t=new TareaAsincronicaGuardarTarjeta();
                t.execute(null,null,null);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   class TareaAsincronicaGuardarTarjeta extends AsyncTask<String, Integer,Integer>{
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           textoNombre=(EditText)findViewById(R.id.textoNombre);
           textoDiaCorte=(EditText)findViewById(R.id.textoDia);
           String nombre=    textoNombre.getText().toString();
           Integer diaCorte=
                   Integer.parseInt(textoDiaCorte.getText().toString());
          t=new Tarjeta();
           t.setNombre(nombre);
           t.setDiaCorte(diaCorte);
       }

       @Override
       protected Integer doInBackground(String... params) {
        try{


          informacion=   guardarTarjeta(t);
        }catch (Exception e){
             System.out.println("<<<<<<<<<<<<<"+e.getMessage());
        }
           return 0;
       }

       @Override
       protected void onPostExecute(Integer integer) {
           super.onPostExecute(integer);
           Toast.makeText(getApplicationContext(),
                   informacion,Toast.LENGTH_LONG).show();

       }
       public String guardarTarjeta(Tarjeta t)throws Exception{

           String mensaje="Sin conexion";
           //A conectarnos!!!!
         //Paso 1 generar un objeto de tipo RestTemplate
           RestTemplate restTemplate=new RestTemplate();
           //Paso 2 Leerlo
           mensaje=restTemplate.getForObject(urlBase+"/"+t.getNombre()
                   +"/"+t.getDiaCorte(),String.class);


           return mensaje;
       }
   }
}
