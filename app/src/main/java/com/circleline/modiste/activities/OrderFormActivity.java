package com.circleline.modiste.activities;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.media.ExifInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.circleline.modiste.R;
import com.circleline.modiste.adapters.CustomerSpinnerAdapter;
import com.circleline.modiste.adapters.MeasurementSpinnerAdapter;
import com.circleline.modiste.fragments.DatePickerFragment;
import com.circleline.modiste.models.Customer;
import com.circleline.modiste.models.Measurement;
import com.circleline.modiste.models.OrderDB;
import com.circleline.modiste.util.Constant;
import com.circleline.modiste.util.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;

public class OrderFormActivity extends AppCompatActivity {

    private static final String TAG = "OrderFormActivity";
    @BindView(R.id.spnr_pemesan)
    Spinner spnr_pemesan;

    @BindView(R.id.spnr_measurement)
    Spinner spnr_measurement;

    @BindView(R.id.edtx_tglselesai)
    EditText edtx_tglselesai;


    @BindView(R.id.edtx_cutting)
    EditText edtx_cutting;

    @BindView(R.id.edtx_harga)
    EditText edtx_harga;

    @BindView(R.id.imvw_foto)
    ImageView imvw_foto;

    @BindView(R.id.imvw_foto_bahan)
    ImageView imvw_foto_bahan;

    @BindView(R.id.edtx_ket_bahan)
    EditText edtx_ket_bahan;

    private final static int ALL_PERMISSIONS_RESULT = 107;

    List<Customer> customerList = new ArrayList<Customer>();
    private CustomerSpinnerAdapter customerAdapter;

    List<Measurement> measurementList = new ArrayList<Measurement>();
    private MeasurementSpinnerAdapter measurementAdapter;

    private Customer selectedCustomer;
    private Measurement selectedMeasurement;
    private long orderID = -1;
    private OrderDB order;

    Bitmap myBitmap;
    Bitmap myBitmapBahan;
    Uri picUri;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);
        ButterKnife.bind(this);

        List<Customer> list = Customer.listAll(Customer.class);
        generateCustomerList(list);

        List<Measurement> tmpList = new ArrayList<Measurement>();
        generateMeasurementList(tmpList);


        edtx_tglselesai.setClickable(true);

        measurementAdapter = new MeasurementSpinnerAdapter(OrderFormActivity.this,android.R.layout.simple_spinner_item,measurementList);
        spnr_measurement.setAdapter(measurementAdapter);
        spnr_measurement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selectedMeasurement = measurementAdapter.getItem(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        customerAdapter = new CustomerSpinnerAdapter(OrderFormActivity.this,android.R.layout.simple_spinner_item,customerList);
        spnr_pemesan.setAdapter(customerAdapter);
        spnr_pemesan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selectedCustomer = customerAdapter.getItem(i);
                    Toast.makeText(OrderFormActivity.this,selectedCustomer.getNama().toString(),Toast.LENGTH_LONG).show();
                    List<Measurement> measurements = Measurement.find(Measurement.class,"id_customer = ?",selectedCustomer.getId().toString());
                    generateMeasurementList(measurements);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(getIntent().hasExtra("orderID")){
            orderID = getIntent().getLongExtra("orderID",-1);
            order = OrderDB.find(OrderDB.class,"id = ?",String.valueOf(orderID)).get(0);
            edtx_tglselesai.setText(order.getTglSelesai());
            edtx_cutting.setText(order.getCutting());
            edtx_harga.setText(order.getHarga());
            loadImageFromStorage(String.valueOf(orderID));

            selectedCustomer = Customer.find(Customer.class,"id = ?",String.valueOf(order.getIdCustomer())).get(0);
            int size = customerList.size();
            int pemesan = -1;
            for(int i = 0;i< size;i++){
                if(customerList.get(i).getId() != null) {
                    if (customerList.get(i).getId().equals(selectedCustomer.getId())) {
                        pemesan = i;
                    }
                }
            }
            spnr_pemesan.setSelection(pemesan);

            selectedMeasurement = Measurement.find(Measurement.class,"id = ?",String.valueOf(order.getIdMeasurement())).get(0);
            List<Measurement> measurements = Measurement.find(Measurement.class,"id_customer = ?",selectedCustomer.getId().toString());
            generateMeasurementList(measurements);

            size = measurementList.size();
            int measurement = -1;
            for(int i = 0;i< size;i++){
                if(measurementList.get(i).getId() != null) {
                    if (measurementList.get(i).getId().equals(selectedMeasurement.getId())) {
                        measurement = i;
                    }
                }
            }

            spnr_measurement.setSelection(measurement);
        }

        permissions.add(CAMERA);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

    }

    @OnClick(R.id.btn_upload)
    void onUploadFoto(){
        startActivityForResult(getPickImageChooserIntent(), Constant.UPLOAD_FOTO_REQUEST);
    }

    @OnClick({R.id.btn_upload_bahan})
    void onUploadFotoBahan() {
        startActivityForResult(getPickImageChooserIntent(),Constant.UPLOAD_FOTO_BAHAN_REQUEST);
    }

    @OnClick(R.id.edtx_tglselesai)
    void onTglSelesaiClick(){
        showDatePickerDialog();
    }

    @OnClick(R.id.btn_datepicker)
    void onDatepicker(){
        showDatePickerDialog();
    }
    @OnClick(R.id.btn_datepicker_cutting)
    void onCutting(){
        showDatePickerDialogCutting();
    }

    @OnClick(R.id.btn_tambahpemesan)
    void onTambahPemesan() {
        Intent intent = new Intent(OrderFormActivity.this, CustomerFormActivity.class);
        startActivityForResult(intent, Constant.CREATE_CUSTOMER_REQUEST);
    }

    @OnClick(R.id.btn_tambahmeasurement)
    void onTambahMeasurement(){
        Intent intent = new Intent(OrderFormActivity.this, MeasurementFormActivity.class);
        intent.putExtra("idCustomer",selectedCustomer.getId());
        startActivityForResult(intent, Constant.CREATE_MEASUREMENT_REQUEST);
    }



    @OnClick(R.id.btn_simpan)
    void onSimpan(){
        if(getIntent().hasExtra("orderID")){
            long id = getIntent().getLongExtra("orderID",-1);
            OrderDB updatedOrder = OrderDB.find(OrderDB.class,"id = ?",String.valueOf(id)).get(0);
            updatedOrder.setTglSelesai(edtx_tglselesai.getText().toString());
            updatedOrder.setCutting(edtx_cutting.getText().toString());
            updatedOrder.setHarga(edtx_harga.getText().toString());
            updatedOrder.setIdCustomer(selectedCustomer.getId().toString());
            updatedOrder.setIdMeasurement(selectedMeasurement.getId().toString());
            updatedOrder.setKetBahan(edtx_ket_bahan.getText().toString());

            updatedOrder.save();
        } else {
            OrderDB neworder = new OrderDB(selectedCustomer.getId().toString()
                    ,selectedMeasurement.getId().toString()
                    , DateUtil.getDate()
                    ,edtx_tglselesai.getText().toString()
                    ,edtx_cutting.getText().toString()
                    ,edtx_harga.getText().toString()
                    ,edtx_ket_bahan.getText().toString()
            );
            neworder.save();
            if(myBitmap != null){
                String id = String.valueOf(neworder.getId());
                saveToInternalStorage(myBitmap,id);
            }
            if(myBitmapBahan != null){
                String id = String.valueOf(neworder.getId())+ "bahan";
                saveToInternalStorage(myBitmapBahan,id);
            }

        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.CREATE_CUSTOMER_REQUEST ) {
            if (resultCode == RESULT_OK) {
                List<Customer> list = Customer.listAll(Customer.class);
                generateCustomerList(list);
                customerAdapter.notifyDataSetChanged();
                spnr_pemesan.setSelection(list.size()+1);
            }
        } else if(requestCode == Constant.CREATE_MEASUREMENT_REQUEST){
            if(resultCode == RESULT_OK){
                List<Measurement> measurements = Measurement.find(Measurement.class,"id_customer = ?",selectedCustomer.getId().toString());
                generateMeasurementList(measurements);
                measurementAdapter.notifyDataSetChanged();
                spnr_measurement.setSelection(measurements.size()+1);
            }
        } else if(requestCode == Constant.UPLOAD_FOTO_REQUEST){
            if(resultCode == RESULT_OK){
                if (getPickImageResultUri(data) != null) {
                    picUri = getPickImageResultUri(data);

                    try {
                        myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                        myBitmap = getResizedBitmap(myBitmap, 500);

                        imvw_foto.setImageBitmap(myBitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    myBitmap = bitmap;
                    imvw_foto.setImageBitmap(myBitmap);

                }
            }
        } else if(requestCode == Constant.UPLOAD_FOTO_BAHAN_REQUEST){
            if(resultCode == RESULT_OK){
                if (getPickImageResultUri(data) != null) {
                    picUri = getPickImageResultUri(data);

                    try {
                        myBitmapBahan = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                        myBitmapBahan = getResizedBitmap(myBitmapBahan, 500);

                        imvw_foto_bahan.setImageBitmap(myBitmapBahan);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    myBitmapBahan = bitmap;
                    imvw_foto_bahan.setImageBitmap(myBitmapBahan);

                }
            }
        }
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private void loadImageFromStorage(String filename)
    {

        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath=new File(directory,filename);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));
            ImageView img=(ImageView)findViewById(R.id.imvw_foto);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage,String filename){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,filename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }



    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setOnDatePickerSet(new DatePickerFragment.DatePickerListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                edtx_tglselesai.setText(new StringBuilder().append(day).append("/")
                        .append(month).append("/").append(year));
            }
        });
        newFragment.show(getFragmentManager(),"datePicker");

    }
    public void showDatePickerDialogCutting() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setOnDatePickerSet(new DatePickerFragment.DatePickerListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                edtx_cutting.setText(new StringBuilder().append(day).append("/")
                        .append(month).append("/").append(year));
            }
        });
        newFragment.show(getFragmentManager(),"datePicker");

    }

    private void generateCustomerList(List<Customer> list){
        customerList.clear();
        Customer hint = new Customer("Pilih Pemesan","-1","-1");
        customerList.add(hint);
        customerList.addAll(list);
    }
    private void generateMeasurementList(List<Measurement> list){
        measurementList.clear();
        Measurement hint = new Measurement();
        hint.setNama("Pilih Ukuran");
        measurementList.add(hint);
        measurementList.addAll(list);
    }

    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }
    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    /**
     * Get the URI of the selected image from {@link #getPickImageChooserIntent()}.

     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {

                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                //Log.d("API123", "permisionrejected " + permissionsRejected.size());

                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri");
    }

}
