package com.example.fechaconta;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.LifecycleOwner;

import com.example.fechaconta.models.Mesa;
import com.example.fechaconta.models.Restaurant;
import com.example.fechaconta.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
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

public class QRreader extends AppCompatActivity {

    //VARIAVEIS
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};
    PreviewView mPreviewView;
    private int REQUEST_CODE_PERMISSIONS = 1001;
    private Executor executor = Executors.newSingleThreadExecutor();
    private ImageAnalysis imageAnalysis;
    private Mesa mesa;
    private Usuario usuarioLogado;
    private Restaurant restaurante;
    private Dialog dialogConfirma;
    private ContentLoadingProgressBar progress;
    private FirebaseAuth mAuth;
    private String restauranteId;
    private String mesaId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrreader);


        dialogConfirma = new Dialog(this);
        mPreviewView = findViewById(R.id.camerapreview);
        progress = findViewById(R.id.progress_checkin);


        if (allPermissionsGranted()) {
            iniciaCamera(); //inicia camera caso haja permissão
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }


    }


    /*
     * REQUISITA PERMIÇÕES    *
     * */
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


    /*
     * INICIA CAMERA    *
     * */

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

    /*
     * PAUSA CAMERA    *
     * */
    private void PausaCamera(ProcessCameraProvider cameraProvider) {

        cameraProvider.unbindAll();

    }

    /*
     *
     * Função responsável por inicializar os casos de uso da camera
     *
     * Casos de uso utilizados: Preview (imagem em tempo real), Image Analyzer (Permite a leitura do QRCode), Camera Selector(Seleciona a Camera)
     *
     * */
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {

        Preview preview = new Preview.Builder() //CRIA VIZUALIZADOR DA CAMERA
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder() //CRIA UM SELETOR DE CAMERA
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        imageAnalysis = new ImageAnalysis.Builder() //CRIA UM ANALISADOR DE IMAGENS
                .build();

        imageAnalysis.setAnalyzer(executor, new ImageAnalysis.Analyzer() { //Configura a Análise
            @Override
            public void analyze(@NonNull ImageProxy image) { //Analizador recebe como entrada um imagem vindo da camera

                BarcodeScannerOptions QRoptions = new BarcodeScannerOptions.Builder() //CONFIGURA BARCODE
                        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                        .build();

                BarcodeScanner scanner = BarcodeScanning.getClient(QRoptions); //INICIA BARCODE


                int rotationDegrees = image.getImageInfo().getRotationDegrees(); //PEGA ROTAÇÃO DO CELULAR NO FRAME QUE ESTÁ SENDO ANALISADO

                Image imagem = image.getImage(); //PEGA A IMAGEM

                InputImage ImagemAnalise = null;

                if (imagem != null) {
                    ImagemAnalise = InputImage.fromMediaImage(imagem, rotationDegrees); //CONFIGURA A IMAGEM COM A ROTAÇÃO
                }

                Task<List<Barcode>> result = scanner.process(ImagemAnalise) //CRIA A TASK DE ANALIZAR
                        .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                            @Override
                            public void onSuccess(List<Barcode> barcodes) { //LEITURA REALIZADA

                                for (Barcode barcode : barcodes) {
                                    //progress.show();
                                    Rect bounds = barcode.getBoundingBox();
                                    Point[] corners = barcode.getCornerPoints();

                                    String rawValue = barcode.getRawValue(); //PEGA DADO LIDO

                                    scanner.close(); //FECHA SCANER


                                    if (BuscarMesa(rawValue)) { //VERIFICA SE O CÓDIGO LIDO É O CAMINHO DE UMA MESA

                                        PausaCamera(cameraProvider); //PAUSA A CAMERA


                                        //INSTANCIA OBJETOS DO DIALOG

                                        TextView nomeRestaurante;
                                        TextView codMesa;
                                        ImageView restauranteHeader;
                                        TextView btnCancelar;
                                        MaterialButton btnFazCheckin;

                                        //SET LAYOUT DIALOG
                                        dialogConfirma.setContentView(R.layout.dialogo_confirmar_checkin);

                                        //INSTANCIA COMPONENTES LAYOUT DIALOG
                                        nomeRestaurante = dialogConfirma.findViewById(R.id.nome_restaurante_checkin);
                                        codMesa = dialogConfirma.findViewById(R.id.cod_mesa_checkin);
                                        btnCancelar = dialogConfirma.findViewById(R.id.btn_cancelar);
                                        restauranteHeader = dialogConfirma.findViewById(R.id.header_confirma_checkin);
                                        btnFazCheckin = dialogConfirma.findViewById(R.id.btn_confirma);


                                        //PREENCHE OS CAMPOS
                                        nomeRestaurante.setText(restaurante.getNome());
                                        codMesa.setText("Mesa: " + mesa.getNu_mesa());

                                        //ARREDONDA AS BORDAS DO DIALOG
                                        View v = Objects.requireNonNull(dialogConfirma.getWindow()).getDecorView();
                                        v.setBackgroundResource(android.R.color.transparent);


                                        //BUSCA IMAGEM DO RESTAURANTE
                                        FirebaseStorage storage = FirebaseStorage.getInstance();
                                        final StorageReference imagem = storage.getReference().child("Restaurantes/Header/" + restaurante.getUrlheader());
                                        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Picasso.get().load(uri).fit().into(restauranteHeader);
                                            }
                                        });

                                        //EXIBE DIALOGO
                                        // progress.hide();
                                        dialogConfirma.show();

                                        dialogConfirma.setCanceledOnTouchOutside(false); //FAZ COM QUE NÃO FECHE SE CLICAR FORA DO DIALOGO

                                        dialogConfirma.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {
                                                iniciaCamera(); //REINICIA CAMERA

                                                //APAGA AS CLASSES CRIADAS
                                                mesa = null;
                                                restaurante = null;
                                                //progress.hide();
                                            }
                                        }); //REINICIA CAMERA QUANDO DISCARTA O DIALOGO

                                        btnCancelar.setOnClickListener(new View.OnClickListener() { //BOTÃO DE CANCELAR CHECK-IN
                                            @Override
                                            public void onClick(View v) {

                                                //iniciaCamera(); //REINICIA CAMERA

                                                //APAGA AS CLASSES CRIADAS
                                                mesa = null;
                                                restaurante = null;

                                                //FECHA DIALOGO
                                                dialogConfirma.cancel();

                                            }
                                        }); //FECHA DIALOGO E REINICIA CAMERA

                                        btnFazCheckin.setOnClickListener(new View.OnClickListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.O)
                                            @Override
                                            public void onClick(View v) {
                                                RealizaCheckIn();
                                            }
                                        }); //REALIZA CHECKIN

                                    } else {

                                        Toast.makeText(getApplication(), "Não foi possível localizar restaurante", Toast.LENGTH_SHORT).show();

                                    }


                                }

                                image.close();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) { //LEITURA NÃO FOI EFETUADA

                                scanner.close(); //FECHA SCANNER
                                image.close(); //LIMPA IMAGEM

                            }

                        });
            }


        });


        preview.setSurfaceProvider(mPreviewView.createSurfaceProvider()); //CRIA SUPERFICIE DE VIZUALIZAÇÃO DA CAMERA

        //ANEXA CICLO DE VIDA DA CAMERA COM CICLO DE VIDA DA ACTIVITY
        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageAnalysis);

    }


    /*
     * FUNÇÃO QUE VERIFICA SE O QRCODE É VALIDO E BUSCA A MESA
     * */

    private boolean BuscarMesa(String rawcode) { //BUSCA A MESA LIDA NO QR CODE

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String[] split;

        split = rawcode.split("//urlmesa//"); //DIVIDE O ENDEREÇO LIDO PARA MESA E RESTAURANTE

        if (split.length == 2) { //VERIFICA O LINK RECEBIDO É VÁLIDO
            restauranteId = split[0];
            mesaId = split[1];

            //SE O LINK É VÁLIDO, BUSCA RESTAURANTE

            db.collection("Restaurant")
                    .document(restauranteId)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {

                        restaurante = Objects.requireNonNull(task.getResult()).toObject(Restaurant.class);
                        assert restaurante != null;
                        restaurante.setID_restaurante(restauranteId);
                    }
                }
            });

            //SE O LINK É VALIDO, BUSCA MESA

            db.collection("Restaurant")
                    .document(restauranteId)
                    .collection("Mesas")
                    .document(mesaId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {

                        mesa = Objects.requireNonNull(task.getResult()).toObject(Mesa.class);
                        assert mesa != null;
                        mesa.setMesaId(mesaId);

                    }
                }
            });


        }

        if (mesa == null) { //VERIFICA SE A MESA EXISTE, CASO NÃO: RETORNA FALSE;

            return false;

        } else return true; //CASO SIM: RETORNA TRUE;
    }


    /*
     *
     * FUNÇÃO QUE REALIZA O CHECK-IN
     *
     * */

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void RealizaCheckIn() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String userID = user.getUid();

        DocumentReference usuario = db.collection("User").document(user.getUid());

        usuario.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    usuarioLogado = Objects.requireNonNull(task.getResult()).toObject(Usuario.class);
                    assert usuarioLogado != null;
                    usuarioLogado.FazCheckin(restaurante,mesa);

                    Intent intent = new Intent(QRreader.this, MainActivity.class );
                    startActivity(intent);
                    QRreader.this.finish();

                } else {

                    Toast.makeText(QRreader.this, "Não foi possível recuperar usuário", Toast.LENGTH_LONG).show();

                }
            }
        });




    }

}






