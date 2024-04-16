package com.triana.base_datos;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;

public class AdaptadorLibros extends ArrayAdapter<HashMap<String, String>> {

    private ArrayList<HashMap<String, String>> listaDeLibros;

    public AdaptadorLibros(Context context, ArrayList<HashMap<String, String>> listaDeLibros) {
        super(context, android.R.layout.simple_list_item_2, android.R.id.text1, listaDeLibros);
        this.listaDeLibros = listaDeLibros;
    }

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
}
