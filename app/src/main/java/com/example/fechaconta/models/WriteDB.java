package com.example.fechaconta.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotMetadata;

import java.util.HashMap;
import java.util.Map;

public class WriteDB {
    private final String TAG = "WRITEDB";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    



    public void escrPrato (final Dishes prato, String resname){
        db.collection("Restaurant").whereEqualTo("nome", resname).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                               /* if (unic == false){
                                    Log.d(TAG, "onComplete: + de 1 resultado");
                                    break;
                                }else{*/
                                     db.collection("Restaurant").document(document.getId()).collection("Dishes").add(prato);
                                    Log.d(TAG, document.getId() + " ==== > " + document.getData());

                                /*}*/

                            }

                            Log.d(TAG, "onComplete: Sucesso ao escrever o prato");
                        }else{
                            Log.d(TAG, "onComplete: Falhou ao escrever um prato");
                        }
                    }
                });
    }

    public void escrRestaurante (Restaurant restaurant){
       /* //passa os daods do objeto pro HashMap
        Map<String, Object> rest = new HashMap<>();
        rest.put("nome", restaurant.getNome());
        rest.put("rua",restaurant.getEnder());
        rest.put("cidade",restaurant.getCidade());
        rest.put("estado", restaurant.getEstado());
        rest.put("des", restaurant.getDes());
        rest.put("media", restaurant.getMedia());
        rest.put("taval", restaurant.getTaval());
        rest.put("urlheader", restaurant.getUrlheader());
        rest.put("urlicon", restaurant.getUrlicon());*/

        //Escreve no Firestore
        db.collection("Restaurant").add(restaurant)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(TAG, "onSuccess: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: Falhou!!!");
                    }
                });



    }


}
