package com.example.fechaconta.utilitys;

import androidx.annotation.NonNull;

import com.example.fechaconta.interfaces.ModelsInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FBQuery {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static final int DESENDENTE = 0;
    public static final int ASCENDENTE = 1;


    public void queryWheretoOrderby (String caminho, String campo, Object valor, String campoOrdenar, int ordem, final ModelsInterface objeto ) {
        Query query = firebaseFirestore.collection(caminho).whereEqualTo(campo, valor);
        switch (ordem){
            case 0 :
                query.orderBy(campoOrdenar, Query.Direction.DESCENDING);
                break;
            case 1 :
                query.orderBy(campoOrdenar, Query.Direction.ASCENDING);
                break;
        }


    }
}
