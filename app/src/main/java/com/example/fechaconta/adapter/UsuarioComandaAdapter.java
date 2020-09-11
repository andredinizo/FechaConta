package com.example.fechaconta.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsuarioComandaAdapter extends RecyclerView.Adapter<UsuarioComandaAdapter.MyViewHolder> {

    private List<String> listaUsuarioId;
    private Usuario usuarioDaVez;

    public UsuarioComandaAdapter() {

        this.listaUsuarioId = Usuario.CheckIn.getInstance().getListaUsuarioMesa();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_mesa, parent, false);

        return new UsuarioComandaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("User").document(listaUsuarioId.get(position)).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        usuarioDaVez = Objects.requireNonNull(task.getResult()).toObject(Usuario.class);
                        Log.d("UsuarioDaVez", usuarioDaVez.getEmail());
                        assert usuarioDaVez != null;
                        holder.nomeUser.setText(usuarioDaVez.getNome());
                        Picasso.get().load(usuarioDaVez.getPhotoUrl()).fit().into(holder.imagemUser);

                    }
                });


    }


    @Override
    public int getItemCount() {
        return listaUsuarioId.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nomeUser;
        private CircleImageView imagemUser;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeUser = itemView.findViewById(R.id.nomeUserMesa);
            imagemUser = itemView.findViewById(R.id.imagemUsuarioMesa);

        }


    }
}
