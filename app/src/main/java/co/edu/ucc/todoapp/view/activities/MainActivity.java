package co.edu.ucc.todoapp.view.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.edu.ucc.todoapp.R;
import co.edu.ucc.todoapp.data.entidades.mapper.TaskEntityMapper;
import co.edu.ucc.todoapp.data.repository.task.TaskDataSourceFactory;
import co.edu.ucc.todoapp.data.repository.task.TaskRepository;
import co.edu.ucc.todoapp.domain.usecase.task.AddTaskLocalUseCase;
import co.edu.ucc.todoapp.domain.usecase.task.AddTaskUseCase;
import co.edu.ucc.todoapp.domain.usecase.task.GetAllTaskLocalUseCase;
import co.edu.ucc.todoapp.domain.usecase.task.GetAllTaskUseCase;
import co.edu.ucc.todoapp.view.adapters.TaskAdapter;
import co.edu.ucc.todoapp.view.presenter.IMainPresenter;
import co.edu.ucc.todoapp.view.presenter.MainPresenter;
import co.edu.ucc.todoapp.view.viewmodels.TaskViewModel;
import co.edu.ucc.todoapp.view.viewmodels.mapper.TaskViewModelMapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements IMainView,
        NavigationView.OnNavigationItemSelectedListener {

    private static final int SELECT_PICTURE = 888;
    private static final int REQUEST_TAKE_PHOTO = 777;

    @BindView(R.id.txtDescTask)
    EditText txtDescTask;

    @BindView(R.id.rvTask)
    RecyclerView rvTask;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    ImageView imgPhotoTask;

    private IMainPresenter presenter;
    private TaskAdapter taskAdapter;
    private static final int MY_PERMISSION = 999;

    FloatingActionButton btnTakePhoto;
    private String mCurrentPhotoPath;
    private Uri photoURI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);
        }

        nav_view.setNavigationItemSelectedListener(this);

        presenter = new MainPresenter(
                this,
                new AddTaskUseCase(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        new TaskRepository(
                                new TaskEntityMapper(),
                                new TaskDataSourceFactory(getApplicationContext())
                        )
                ),
                new GetAllTaskUseCase(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        new TaskRepository(
                                new TaskEntityMapper(),
                                new TaskDataSourceFactory(getApplicationContext())
                        )
                ),
                new AddTaskLocalUseCase(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        new TaskRepository(
                                new TaskEntityMapper(),
                                new TaskDataSourceFactory(getApplicationContext())
                        )
                ),
                new GetAllTaskLocalUseCase(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        new TaskRepository(
                                new TaskEntityMapper(),
                                new TaskDataSourceFactory(getApplicationContext())
                        )
                ),
                new TaskViewModelMapper()
        );

        initAdapter();
        initRecyclerView();
    }

    private void initAdapter() {
        taskAdapter = new TaskAdapter();
    }

    private void initRecyclerView() {
        rvTask.setLayoutManager(new LinearLayoutManager(this));
        rvTask.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvTask.setAdapter(taskAdapter);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessful() {
        Toast.makeText(this, R.string.msg_successful, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fbAddTask)
    @Override
    public void addTask() {
        /*String descTask = txtDescTask.getText().toString();
        presenter.addTask(descTask, false);*/

        boolean wrapInScrollView = true;
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.title)
                .customView(R.layout.popup_task, wrapInScrollView)
                .positiveText(R.string.positive)
                .show();

        View view = dialog.getCustomView();

        btnTakePhoto = view.findViewById(R.id.btnTakePhoto);
        imgPhotoTask = view.findViewById(R.id.imgPhotoTask);

        btnTakePhoto.setOnClickListener(view1 -> {
            if (requestPermission()) {
                btnTakePhoto.setEnabled(true);

                new MaterialDialog.Builder(this)
                        .title(R.string.select_image)
                        .items(new String[]{"Galeria", "Tomar Foto"})
                        .itemsCallbackSingleChoice(-1, (dialog1, view2, which, text) -> {
                            if (which == 0) {
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Escoje la Imagen"), SELECT_PICTURE);
                            } else if (which == 1) {
                                takePhoto();
                            }
                            return true;
                        })
                        .show();
            }

        });
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "co.edu.ucc.todoapp.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PICTURE:
                    Uri photo = data.getData();
                    Picasso.get().load(photo).fit().into(imgPhotoTask);
                    break;
                case REQUEST_TAKE_PHOTO:
                    Picasso.get().load(photoURI).fit().into(imgPhotoTask);
                    break;
            }
        }
    }

    private boolean requestPermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) ||
                shouldShowRequestPermissionRationale(CAMERA)) {
            Toast.makeText(this, R.string.solicitud_permisos, Toast.LENGTH_SHORT).show();
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSION);
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSION);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                btnTakePhoto.setEnabled(true);
            }
        }
    }

    @Override
    public void showTasks(List<TaskViewModel> taskViewModels) {
        taskAdapter.addLstTask(taskViewModels);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Toast.makeText(this, "Click en Settings", Toast.LENGTH_SHORT).show();
        }

        if (id == android.R.id.home) {
            drawer_layout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.contactar) {
            Toast.makeText(this, "Click en Contactar del menu", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.acerca) {
            Toast.makeText(this, "Click en Acerca del menu", Toast.LENGTH_SHORT).show();
        }

        drawer_layout.closeDrawer(GravityCompat.START);

        return true;
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "imgtask_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
