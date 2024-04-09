package com.triana.base_datos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddQuoteActivity extends AppCompatActivity {

    private EditText editTextLibro;
    private EditText editTextAutor;
    private EditText editTextCategoria;
    private Button botonAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);

        //bind views
        editTextLibro = (EditText) findViewById(R.id.editTextLibro);
        editTextAutor = (EditText) findViewById(R.id.editTextAutor);
        editTextCategoria = (EditText) findViewById(R.id.editTextCategoria);
        botonAgregar = (Button) findViewById(R.id.botonAgregar);

        //listener
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get text
                String libro = editTextLibro.getText().toString();
                String autor = editTextAutor.getText().toString();
                String categoria = editTextCategoria.getText().toString();

                //check if empty
                if (libro.isEmpty()){
                    editTextLibro.setError("No puede estar vacío");
                    return;
                }
                if (autor.isEmpty()){
                    editTextAutor.setError("No puede estar vacío");
                    return;
                }
                if (categoria.isEmpty()){
                    editTextCategoria.setError("No puede estar vacío");
                    return;
                }

                //add to database
                agregarLibroABD(libro, autor, categoria);
            }
        });
    }

    private void agregarLibroABD(String libro, String autor, String categoria) {
        //create a hashmap
        HashMap<String, Object> libroHashmap = new HashMap<>();
        libroHashmap.put("libro", libro);
        libroHashmap.put("autor", autor);
        libroHashmap.put("categoria", categoria);

        //instantiate database connection
        FirebaseDatabase baseDeDatos = FirebaseDatabase.getInstance();
        DatabaseReference referenciaLibros = baseDeDatos.getReference("libros");

        String clave = referenciaLibros.push().getKey();
        libroHashmap.put("clave", clave);

        referenciaLibros.child(clave).setValue(libroHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddQuoteActivity.this, "Agregado", Toast.LENGTH_SHORT).show();
                editTextLibro.getText().clear();
                editTextAutor.getText().clear();
                editTextCategoria.getText().clear();
            }
        });
    }
}