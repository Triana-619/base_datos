package com.triana.base_datos;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ListView listaLibros;
    private FloatingActionButton botonFlotante;
    private AdaptadorLibros adaptadorLibros;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaLibros = (ListView) findViewById(R.id.listaLibros);
        botonFlotante = (FloatingActionButton) findViewById(R.id.botonFlotante);

        databaseManager = new DatabaseManager();
        adaptadorLibros = new AdaptadorLibros(this, databaseManager.getListaDeLibros());

        listaLibros.setAdapter(adaptadorLibros);

        databaseManager.cargarLibrosDeDB(adaptadorLibros);

        botonFlotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(MainActivity.this, AddQuoteActivity.class);
                startActivity(intento);
            }
        });
    }
}
