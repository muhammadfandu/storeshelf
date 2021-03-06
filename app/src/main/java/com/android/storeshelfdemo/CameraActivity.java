package com.android.storeshelfdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import java.io.File;
import java.io.IOException;


public class CameraActivity extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    TextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(android.R.color.black));

        String picture1Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_1.png";
        String picture2Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_2.png";
        String picture3Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_3.png";
        String picture4Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_4.png";
        String picture5Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_5.png";
        String picture6Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_6.png";

        ImageView imgPreviewLeft = findViewById(R.id.imagePreviewLeft);
        ImageView imgPreviewRight = findViewById(R.id.imagePreviewRight);
        ImageView imgPreviewTop = findViewById(R.id.imagePreviewTop);
        ImageView imgPreviewBottom = findViewById(R.id.imagePreviewBottom);

        textureView = findViewById(R.id.view_finder);
        ImageButton imgCapture = findViewById(R.id.imgCapture);
        ImageButton imgNext = findViewById(R.id.imgNext);
        Button btnNomor = findViewById(R.id.buttonNomor);
        imgCapture.setRotation(90);
        imgNext.setRotation(90);
        btnNomor.setRotation(90);

        if (allPermissionsGranted()) {
            startCamera(); //start camera if permission has been granted by user
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        int pictureId = getIntent().getIntExtra("pictureNumber", 0);
        if (pictureId == 1) {
            btnNomor.setText("Foto 1");
        } else if (pictureId == 2) {
            btnNomor.setText("Foto 2");
        } else if (pictureId == 3) {
            btnNomor.setText("Foto 3");
        } else if (pictureId == 4) {
            btnNomor.setText("Foto 4");
        } else if (pictureId == 5) {
            btnNomor.setText("Foto 5");
        } else if (pictureId == 6) {
            btnNomor.setText("Foto 6");
        }

        File file = new File(picture1Path);
        Bitmap im = BitmapFactory.decodeFile(file.getAbsolutePath());

        if (pictureId == 1) {
            File myFile2 = new File(picture2Path);
            if (myFile2.exists()) {
                Bitmap imgRight = downScaleImage(picture2Path);
                Bitmap imgRightRot = rotate(imgRight, 90);
                Bitmap imgRightRotTranslate = Bitmap.createBitmap(imgRightRot.getWidth(),imgRightRot.getHeight() + 800, imgRightRot.getConfig());

                Canvas translateCanvas = new Canvas(imgRightRotTranslate);
                Matrix translateMatrix = new Matrix();
                // Set x y translate value..
                translateMatrix.setTranslate(0, 800);
                Paint paint = new Paint();
                translateCanvas.drawBitmap(imgRightRot, translateMatrix, paint);

                imgPreviewBottom.setImageBitmap(imgRightRotTranslate);
            }
        } else if (pictureId == 2) {
            File myFile1 = new File(picture1Path);
            if (myFile1.exists()) {
                Bitmap imgLeft = downScaleImage(picture1Path);
                Bitmap imgLeftRot = rotate(imgLeft, 90);
                Bitmap imgLeftRotTranslate = Bitmap.createBitmap(imgLeftRot.getWidth(),imgLeftRot.getHeight() - 800, imgLeftRot.getConfig());

                Canvas translateCanvas = new Canvas(imgLeftRotTranslate);
                Matrix translateMatrix = new Matrix();
                // Set x y translate value..
                translateMatrix.setTranslate(0, -800);
                Paint paint = new Paint();
                translateCanvas.drawBitmap(imgLeftRot, translateMatrix, paint);

                imgPreviewTop.setImageBitmap(imgLeftRotTranslate);

            }
            File myFile3 = new File(picture3Path);
            if (myFile3.exists()) {
                Bitmap imgRight = downScaleImage(picture3Path);
                Bitmap imgRightRot = rotate(imgRight, 90);
                Bitmap imgRightRotTranslate = Bitmap.createBitmap(imgRightRot.getWidth(),imgRightRot.getHeight() + 800, imgRightRot.getConfig());

                Canvas translateCanvas = new Canvas(imgRightRotTranslate);
                Matrix translateMatrix = new Matrix();
                // Set x y translate value..
                translateMatrix.setTranslate(0, 800);
                Paint paint = new Paint();
                translateCanvas.drawBitmap(imgRightRot, translateMatrix, paint);

                imgPreviewBottom.setImageBitmap(imgRightRotTranslate);
            }
        } else if(pictureId == 3){
            File myFile2 = new File(picture2Path);
            if(myFile2.exists()){
                Bitmap imgLeft = downScaleImage(picture2Path);
                Bitmap imgLeftRot = rotate(imgLeft, 90);
                Bitmap imgLeftRotTranslate = Bitmap.createBitmap(imgLeftRot.getWidth(),imgLeftRot.getHeight() - 800, imgLeftRot.getConfig());

                Canvas translateCanvas = new Canvas(imgLeftRotTranslate);
                Matrix translateMatrix = new Matrix();
                // Set x y translate value..
                translateMatrix.setTranslate(0, -800);
                Paint paint = new Paint();
                translateCanvas.drawBitmap(imgLeftRot, translateMatrix, paint);

                imgPreviewTop.setImageBitmap(imgLeftRotTranslate);
            }
        }else if(pictureId == 4){
            File myFile1 = new File(picture1Path);
            if(myFile1.exists()){
                Bitmap imgTop = downScaleImage(picture1Path);
                Bitmap imgTopRot = rotate(imgTop, 90);
                Bitmap imgTopRotTranslate = Bitmap.createBitmap(imgTopRot.getWidth() + 600,imgTopRot.getHeight(), imgTopRot.getConfig());

                Canvas translateCanvas = new Canvas(imgTopRotTranslate);
                Matrix translateMatrix = new Matrix();
                // Set x y translate value..
                translateMatrix.setTranslate(600, 0);
                Paint paint = new Paint();
                translateCanvas.drawBitmap(imgTopRot, translateMatrix, paint);

                imgPreviewRight.setImageBitmap(imgTopRotTranslate);
            }
            File myFile5 = new File(picture5Path);
            if(myFile5.exists()){
                Bitmap imgRight= downScaleImage(picture5Path);
                Bitmap imgRightRot = rotate(imgRight, 90);
                Bitmap imgRightRotTranslate = Bitmap.createBitmap(imgRightRot.getWidth(),imgRightRot.getHeight() + 800, imgRightRot.getConfig());

                Canvas translateCanvas = new Canvas(imgRightRotTranslate);
                Matrix translateMatrix = new Matrix();
                // Set x y translate value..
                translateMatrix.setTranslate(0, 800);
                Paint paint = new Paint();
                translateCanvas.drawBitmap(imgRightRot, translateMatrix, paint);

                imgPreviewBottom.setImageBitmap(imgRightRotTranslate);
            }
        }
        else if(pictureId == 5){
            File myFile2 = new File(picture2Path);
            if(myFile2.exists()){
                Bitmap imgTop = downScaleImage(picture2Path);
                Bitmap imgTopRot = rotate(imgTop, 90);
                Bitmap imgTopRotTranslate = Bitmap.createBitmap(imgTopRot.getWidth() + 600,imgTopRot.getHeight(), imgTopRot.getConfig());

                Canvas translateCanvas = new Canvas(imgTopRotTranslate);
                Matrix translateMatrix = new Matrix();
                // Set x y translate value..
                translateMatrix.setTranslate(600, 0);
                Paint paint = new Paint();
                translateCanvas.drawBitmap(imgTopRot, translateMatrix, paint);

                imgPreviewRight.setImageBitmap(imgTopRotTranslate);
            }
            File myFile6 = new File(picture6Path);
            if(myFile6.exists()){
                Bitmap imgRight = downScaleImage(picture6Path);
                Bitmap imgRightRot = rotate(imgRight, 90);
                Bitmap imgRightRotTranslate = Bitmap.createBitmap(imgRightRot.getWidth(),imgRightRot.getHeight() + 800, imgRightRot.getConfig());

                Canvas translateCanvas = new Canvas(imgRightRotTranslate);
                Matrix translateMatrix = new Matrix();
                // Set x y translate value..
                translateMatrix.setTranslate(0, 800);
                Paint paint = new Paint();
                translateCanvas.drawBitmap(imgRightRot, translateMatrix, paint);

                imgPreviewBottom.setImageBitmap(imgRightRotTranslate);
            }
            File myFile4 = new File(picture4Path);
            if(myFile4.exists()){
                Bitmap imgLeft = downScaleImage(picture4Path);
                Bitmap imgLeftRot = rotate(imgLeft, 90);
                Bitmap imgLeftRotTranslate = Bitmap.createBitmap(imgLeftRot.getWidth(),imgLeftRot.getHeight() - 800, imgLeftRot.getConfig());

                Canvas translateCanvas = new Canvas(imgLeftRotTranslate);
                Matrix translateMatrix = new Matrix();
                // Set x y translate value..
                translateMatrix.setTranslate(0, -800);
                Paint paint = new Paint();
                translateCanvas.drawBitmap(imgLeftRot, translateMatrix, paint);

                imgPreviewTop.setImageBitmap(imgLeftRotTranslate);
            }

        }
        else if(pictureId == 6){
            File myFile3 = new File(picture3Path);
            if(myFile3.exists()){
                Bitmap imgTop = downScaleImage(picture3Path);
                Bitmap imgTopRot = rotate(imgTop, 90);
                Bitmap imgTopRotTranslate = Bitmap.createBitmap(imgTopRot.getWidth() + 600,imgTopRot.getHeight(), imgTopRot.getConfig());

                Canvas translateCanvas = new Canvas(imgTopRotTranslate);
                Matrix translateMatrix = new Matrix();
                // Set x y translate value..
                translateMatrix.setTranslate(600, 0);
                Paint paint = new Paint();
                translateCanvas.drawBitmap(imgTopRot, translateMatrix, paint);

                imgPreviewRight.setImageBitmap(imgTopRotTranslate);
            }
            File myFile5 = new File(picture5Path);
            if(myFile5.exists()){
                Bitmap imgRight = downScaleImage(picture5Path);
                Bitmap imgRightRot = rotate(imgRight, 90);
                Bitmap imgRightRotTranslate = Bitmap.createBitmap(imgRightRot.getWidth(),imgRightRot.getHeight() - 800, imgRightRot.getConfig());

                Canvas translateCanvas = new Canvas(imgRightRotTranslate);
                Matrix translateMatrix = new Matrix();
                // Set x y translate value..
                translateMatrix.setTranslate(0, -800);
                Paint paint = new Paint();
                translateCanvas.drawBitmap(imgRightRot, translateMatrix, paint);

                imgPreviewTop.setImageBitmap(imgRightRotTranslate);
            }
        }
    }

    private void startCamera() {

        CameraX.unbindAll();

        Rational aspectRatio = new Rational(textureView.getWidth(), textureView.getHeight());
        Size screen = new Size(textureView.getWidth(), textureView.getHeight()); //size of the screen

        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
        Preview preview = new Preview(pConfig);

        preview.setOnPreviewOutputUpdateListener(
                new Preview.OnPreviewOutputUpdateListener() {
                    //to update the surface texture we  have to destroy it first then re-add it
                    @Override
                    public void onUpdated(Preview.PreviewOutput output) {
                        ViewGroup parent = (ViewGroup) textureView.getParent();
                        parent.removeView(textureView);
                        parent.addView(textureView, 0);

                        textureView.setSurfaceTexture(output.getSurfaceTexture());
                        updateTransform();
                    }
                });


        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();
        final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);

        findViewById(R.id.imgCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pictureId = getIntent().getIntExtra("pictureNumber", 0);
                File file;
                if (pictureId == 1) {
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_1.png");
                } else if (pictureId == 2) {
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_2.png");
                } else if (pictureId == 3) {
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_3.png");
                } else if (pictureId == 4) {
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_4.png");
                } else if (pictureId == 5) {
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_5.png");
                } else if (pictureId == 6) {
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_6.png");
                } else {
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + System.currentTimeMillis() + "_0.png");
                }

//                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + System.currentTimeMillis() + ".png");
                imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        String msg = "Gambar " + getIntent().getIntExtra("pictureNumber", 0) + " berhasil disimpan";
                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        String msg = "Pic capture failed : " + message;
                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                        if (cause != null) {
                            cause.printStackTrace();
                        }
                    }
                });
            }
        });

        //bind to lifecycle:
        CameraX.bindToLifecycle((LifecycleOwner) this, preview, imgCap);
    }

    private void updateTransform() {
        Matrix mx = new Matrix();
        float w = textureView.getMeasuredWidth();
        float h = textureView.getMeasuredHeight();

        float cX = w / 2f;
        float cY = h / 2f;

        int rotationDgr;
        int rotation = (int) textureView.getRotation();

        switch (rotation) {
            case Surface.ROTATION_0:
                rotationDgr = 0;
                break;
            case Surface.ROTATION_90:
                rotationDgr = 90;
                break;
            case Surface.ROTATION_180:
                rotationDgr = 180;
                break;
            case Surface.ROTATION_270:
                rotationDgr = 270;
                break;
            default:
                return;
        }

        mx.postRotate((float) rotationDgr, cX, cY);
        textureView.setTransform(mx);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
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

    Bitmap downScaleImage(String path) {
        File file = new File(path);
        Bitmap im = BitmapFactory.decodeFile(file.getAbsolutePath());

        final int maxSize = 960;
        int outWidth;
        int outHeight;
        int inWidth = im.getWidth();
        int inHeight = im.getHeight();
        if (inWidth > inHeight) {
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
        }

        Bitmap imCompress = Bitmap.createScaledBitmap(im, outWidth, outHeight, false);
        return imCompress;
    }

    public void back(View view) {
        finish();
    }

    public void nextImage(View view) {
        int pictureId = getIntent().getIntExtra("pictureNumber", 0);
        if (pictureId == 1) {
            Intent intent = getIntent();
            int pictureNumber = 2;
            intent.putExtra("pictureNumber", pictureNumber);
            finish();
            startActivity(intent);
        } else if (pictureId == 2) {
            Intent intent = getIntent();
            int pictureNumber = 3;
            intent.putExtra("pictureNumber", pictureNumber);
            finish();
            startActivity(intent);
        } else if (pictureId == 3) {
            Intent intent = getIntent();
            int pictureNumber = 4;
            intent.putExtra("pictureNumber", pictureNumber);
            finish();
            startActivity(intent);
        } else if (pictureId == 4) {
            Intent intent = getIntent();
            int pictureNumber = 5;
            intent.putExtra("pictureNumber", pictureNumber);
            finish();
            startActivity(intent);
        } else if (pictureId == 5) {
            Intent intent = getIntent();
            int pictureNumber = 6;
            intent.putExtra("pictureNumber", pictureNumber);
            finish();
            startActivity(intent);
        } else if (pictureId == 6) {
            finish();
        }
    }

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);
            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}