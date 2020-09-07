package com.example.fechaconta;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.fechaconta.models.Mesa;
import com.example.fechaconta.models.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QRreader extends AppCompatActivity implements ImageAnalysis.Analyzer {

    //VARIAVEIS
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};
    PreviewView mPreviewView;
    private int REQUEST_CODE_PERMISSIONS = 1001;
    private Executor executor = Executors.newSingleThreadExecutor();
    private ImageAnalysis imageAnalysis;
    private Mesa mesa;
    private Restaurant restaurante;
    private Dialog dialogConfirma;
    private Context actvity;
    private CameraX camerax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrreader);

        dialogConfirma = new Dialog(this);
        mPreviewView = findViewById(R.id.camerapreview);


        if (allPermissionsGranted()) {
            iniciaCamera(); //inicia camera caso haja permissão
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }


    }


    //Permisões
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                iniciaCamera();
            } else {
                Toast.makeText(this, "Permissões não concedidas pelo usuário.", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }

    private boolean allPermissionsGranted() {

        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    //INICIA CAMERA
    private void iniciaCamera() {

        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {

                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);

                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this Future.
                    // This should never be reached.
                }
            }
        }, ContextCompat.getMainExecutor(this));

    }

    //PAUSA CAMERA
    private void PausaCamera(ProcessCameraProvider cameraProvider) {

        cameraProvider.unbindAll();

    }

    //FUNÇÃO ANALISE QRCODE
    //ABRE POPUP
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        imageAnalysis = new ImageAnalysis.Builder()
                .build();

        imageAnalysis.setAnalyzer(executor, new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {

                BarcodeScannerOptions QRoptions = new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                        .build();

                BarcodeScanner scanner = BarcodeScanning.getClient(QRoptions);


                int rotationDegrees = image.getImageInfo().getRotationDegrees();

                Image imagem = image.getImage();

                InputImage ImagemAnalise = null;
                if (imagem != null) {
                    ImagemAnalise = InputImage.fromMediaImage(imagem, rotationDegrees);
                }

                Task<List<Barcode>> result = scanner.process(ImagemAnalise)
                        .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                            @Override
                            public void onSuccess(List<Barcode> barcodes) {
                                int flag = 0;
                                for (Barcode barcode : barcodes) {

                                    Rect bounds = barcode.getBoundingBox();
                                    Point[] corners = barcode.getCornerPoints();
                                    String rawValue = barcode.getRawValue();

                                    scanner.close();
                                    if (BuscarMesa(rawValue)) {

                                        PausaCamera(cameraProvider);

                                        TextView nomeRestaurante;
                                        TextView codMesa;
                                        ImageView restauranteHeader;
                                        TextView btnCancelar;

                                        dialogConfirma.setContentView(R.layout.dialogo_confirmar_checkin);

                                        nomeRestaurante = dialogConfirma.findViewById(R.id.nome_restaurante_checkin);
                                        codMesa = dialogConfirma.findViewById(R.id.cod_mesa_checkin);
                                        btnCancelar = dialogConfirma.findViewById(R.id.btn_cancelar);
                                        nomeRestaurante.setText(restaurante.getNome());
                                        codMesa.setText("Mesa: " + mesa.getNu_mesa());


                                        View v = Objects.requireNonNull(dialogConfirma.getWindow()).getDecorView();
                                        v.setBackgroundResource(android.R.color.transparent);
                                        restauranteHeader = dialogConfirma.findViewById(R.id.header_confirma_checkin);

                                        FirebaseStorage storage = FirebaseStorage.getInstance();
                                        final StorageReference imagem = storage.getReference().child("Restaurantes/Header/" + restaurante.getUrlheader());

                                        Log.d("URLQR", "onSuccess2: " + imagem);
                                        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Picasso.get().load(uri).fit().into(restauranteHeader);
                                            }
                                        });

                                        dialogConfirma.show();
                                        dialogConfirma.setCanceledOnTouchOutside(false);

                                        btnCancelar.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                iniciaCamera();
                                                mesa = null;
                                                restaurante = null;
                                                dialogConfirma.cancel();

                                            }
                                        });

                                    }


                                }

                                image.close();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                scanner.close();
                                image.close();
                                // Task failed with an exception
                                // ...
                            }

                        });


            }


        });


        preview.setSurfaceProvider(mPreviewView.createSurfaceProvider());


        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageAnalysis);

    }


    //DIVIDE STRING LEITURA//BUSCA RESTAURANTE//BUSCA MESA
    private boolean BuscarMesa(String rawcode) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String restauranteId;
        String mesaId;
        String[] split;

        split = rawcode.split("//urlmesa//"); //Divide os Endereços (Restaurante e Mesa)

        if (split.length == 2) { //VERIFICA SE CONSEGUIU SEPARAR
            restauranteId = split[0];
            mesaId = split[1];


            //BuscaRestaurante

            db.collection("Restaurant")
                    .document(restauranteId)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {

                        restaurante = Objects.requireNonNull(task.getResult()).toObject(Restaurant.class);

                    }
                }
            });

            //BuscaMesa

            db.collection("Restaurant")
                    .document(restauranteId)
                    .collection("Mesas")
                    .document(mesaId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {

                        mesa = Objects.requireNonNull(task.getResult()).toObject(Mesa.class);


                    }
                }
            });


        }

        if (mesa == null) {

            return false;

        } else return true;
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {

    }
}






