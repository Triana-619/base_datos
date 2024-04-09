package com.triana.base_datos;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ListView listaLibros;
    private ArrayList<HashMap<String, String>> listaDeLibros = new ArrayList<>();
    private ArrayAdapter<HashMap<String, String>> adaptadorLibros;
    private FloatingActionButton botonFlotante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaLibros = (ListView) findViewById(R.id.listaLibros);
        botonFlotante = (FloatingActionButton) findViewById(R.id.botonFlotante);

        adaptadorLibros = new ArrayAdapter<HashMap<String, String>>(this, android.R.layout.simple_list_item_2, android.R.id.text1, listaDeLibros){
            @NonNull
            @Override
            public View getView(int posicion, View convertView, @NonNull ViewGroup parent) {
                View vista = super.getView(posicion, convertView, parent);
                TextView texto1 = (TextView) vista.findViewById(android.R.id.text1);
                TextView texto2 = (TextView) vista.findViewById(android.R.id.text2);

                texto1.setText(listaDeLibros.get(posicion).get("libro"));
                texto2.setText(listaDeLibros.get(posicion).get("autor") + ", " + listaDeLibros.get(posicion).get("categoria"));

                return vista;
            }
        };

        listaLibros.setAdapter(adaptadorLibros);

        cargarLibrosDeDB();

        botonFlotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(MainActivity.this, AddQuoteActivity.class);
                startActivity(intento);
            }
        });
    }

    private void cargarLibrosDeDB() {
        //instantiate database connection
        FirebaseDatabase baseDeDatos = FirebaseDatabase.getInstance();
        DatabaseReference referenciaLibros = baseDeDatos.getReference("libros");

        referenciaLibros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeLibros.clear();
                for (DataSnapshot libroSnapshot: dataSnapshot.getChildren()) {
                    HashMap<String, String> libro = (HashMap<String, String>) libroSnapshot.getValue();
                    listaDeLibros.add(libro);
                }
                adaptadorLibros.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError errorBaseDeDatos) {
                //handle databaseError
            }
        });
    }
}