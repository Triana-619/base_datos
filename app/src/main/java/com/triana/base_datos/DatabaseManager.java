package com.triana.base_datos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import androidx.annotation.NonNull;

public class DatabaseManager {

    private ArrayList<HashMap<String, String>> listaDeLibros = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getListaDeLibros() {
        return listaDeLibros;
    }

    public void cargarLibrosDeDB(final AdaptadorLibros adaptadorLibros) {
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

