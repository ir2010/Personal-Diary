package com.ir_sj.personaldiary;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;


import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;

import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    Bundle a;
    ConstraintLayout constraintLayout;
    com.google.android.material.floatingactionbutton.FloatingActionButton add,file,folder;
    int b=0;
    FloatingActionButton addFile, addFolder;
    FileExplorerFragment fef = new FileExplorerFragment();
    BottomNavigationView navView;
    File currentDir;
    int currentNightMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        a = getIntent().getExtras();

        ImageView imageView = findViewById(R.id.image);
        TextView username = findViewById(R.id.userName);

        currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

       GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if(user != null) {
            username.setText(user.getDisplayName());
            //Uri photoUrl = user.getPhotoUrl();
            /*try {
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUrl);
                //imageView.setImageBitmap(bitmap);
            }catch(IOException e)
            {
                e.printStackTrace();
            }*/

        }
        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        //imageView.setImageURI(photoUrl);

        navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.delete, R.id.share, R.id.more)
                .build();
        navView.setVisibility(View.GONE);
        currentDir = new File("/data/data/com.ir_sj.personaldiary/");
        setSupportActionBar(toolbar);

        BottomNavigationItemView more = findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("popupmenu");
                showPopupMenu(view);
            }
        });

        loadFragment();

        addFile = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_newfile);
        addFolder =(com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab_newfolder);

        addFile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Create File");
                alertDialog.setMessage("Enter File Name");

                final EditText input = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_note_add_black_24dp);

                alertDialog.setPositiveButton("Create",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    String rootPath = "/data/data/com.ir_sj.personaldiary/";
                                    /*File root = new File(rootPath);
                                    if (!root.exists()) {
                                        root.mkdirs();
                                    }*/
                                    File f = new File(rootPath + input.getText());
                                    if (f.exists()) {
                                        alertDialog.setMessage("File of this name already exists!");
                                    }
                                    f.createNewFile();

                                    FileOutputStream out = new FileOutputStream(f);

                                    out.flush();
                                    out.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                loadFragment();

                                //Intent EditorIntent =  new Intent(MainActivity.this, EditorActivity.class);
                                //startActivity(EditorIntent);
                            }
                        });

                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });
        addFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this,R.style.Theme_AppCompat_Dialog_MinWidth);
                alertDialog.setTitle("Create Folder");
                alertDialog.setMessage("Enter Folder Name");

                final EditText input = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_create_new_folder_black_24dp);

                alertDialog.setPositiveButton("Create",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //File new_folder = getDir("Diary",MODE_PRIVATE);
                                // populate_folder();
                                File mydir = getDir(String.valueOf(input.getText()), Context.MODE_PRIVATE); //Creating an internal dir;
                                loadFragment();
                                try
                                {
                                    File diaryfile = new File(mydir, String.valueOf(input.getText())); //Getting a file within the dir.
                                    FileOutputStream out = new FileOutputStream(diaryfile);
                                }
                                catch(FileNotFoundException e)
                                {
                                    alertDialog.setMessage((CharSequence) e);
                                }
                            }

                        });

                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        });

        final GridView grid =(GridView) findViewById(R.id.fileGrid);
        /*grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                navView.setVisibility(View.VISIBLE);
                return false;
            }
        });*/

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/


    }

    @Override
    public void onBackPressed()
    {
        if(navView.getVisibility()== View.VISIBLE)
            navView.setVisibility(View.GONE);
        else
            super.onBackPressed();
    }


    @Override
    protected void onResume() {
        System.out.println("resume");
        Intent intent = getIntent();
        String str = intent.getStringExtra("activity");
        System.out.println(str);
        if(str=="editor"){
            System.out.println("edit");
            super.onResume();
            loadFragment();}
        else
            super.onResume();

    }

    public void loadFragment()
    {
        FileExplorerFragment fragment = new FileExplorerFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fileFragment,fragment);
        fragmentTransaction.commit();
        /*FragmentManager fragmentManager = getFragmentManager();
        Fragment myFragment = fragmentManager.findFragmentById(R.id.fileFragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(myFragment);
        fragmentTransaction.commit();*/
        //FileExplorerFragment fragment = new FileExplorerFragment();
        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.hide(getFragmentManager().findFragmentById(R.id.fileFragment));
        //ft.detach(getFragmentManager().findFragmentById(R.id.fileFragment));
        //ft.attach(getFragmentManager().findFragmentById(R.id.fileFragment));
        //ft.commit();
    }

    /*public void reloadFragment()
    {
        FileExplorerFragment fragment = new FileExplorerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
          fragmentTransaction.commit();
    }*/
    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this,"Successfully Signed Out",Toast.LENGTH_LONG).show();
                        Intent lip=new Intent(MainActivity.this,LoginOptions.class);
                        startActivity(lip);
                        finish();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(this);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent=new Intent(MainActivity.this,LoginOptions.class);
            startActivity(intent);
        }
        else{
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item>dir = fef.dir;
                FileArrayAdapter adapter = new FileArrayAdapter(getBaseContext(), R.layout.fragment_item, dir);
                adapter.getFilter().filter(s);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent help_intent=new Intent(MainActivity.this, HelpActivity.class);
        Intent dev_intent=new Intent(MainActivity.this, DevelopersActivity.class);
        switch(id)
        {
            case R.id.hidden:
                b++;
                if(b==5){
                    //Intent intent=new Intent(MainActivity.this,Folder.class);
                    b=0;
                    startActivity(new Intent(MainActivity.this,FingerprintAuth.class));
                    // Toast.makeText(MainActivity.this,"Welcome To hidden Folder",Toast.LENGTH_SHORT).show();
                }
            case R.id.signout:
                switch (item.getItemId()) {
                    // ...
                    case R.id.signout:
                        signOut();
                        break;
                }

                FirebaseAuth.getInstance().signOut();
                break;
            case R.id.changeMode: {

                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_NO:
                        // Night mode is not active, we're using the light theme
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        this.finish();
                        this.startActivity(new Intent(this, this.getClass()));
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        // Night mode is active, we're using dark theme
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        this.finish();
                        this.startActivity(new Intent(this, this.getClass()));
                        break;
                }
            }
            break;
            case R.id.help: startActivity(help_intent);
                break;
            case R.id.dev: startActivity(dev_intent);
            break;

        }


        return super.onOptionsItemSelected(item);
    }


    /*@Override
    public void onListFragmentInteraction(Item item) {
        FileExplorerFragment fef = new FileExplorerFragment();

        if(item.getImage().equalsIgnoreCase("directory_icon")||item.getImage().equalsIgnoreCase("directory_up")){
            fef.currentDir = new File(item.getPath());
            fef.fill(fef.currentDir);
        }
        else
        {
            onFileClick(item);
        }
    }*/
    /*private void onFileClick(Item o)
    {
        FileExplorerFragment fef = new FileExplorerFragment();
        //Toast.makeText(this, "Folder Clicked: "+ currentDir, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("GetPath",fef.currentDir.toString());
        intent.putExtra("GetFileName",o.getName());
        setResult(RESULT_OK, intent);
        finish();
    }*/

    public void showPopupMenu(View v) {
        System.out.println("pop");
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.more_menu, popup.getMenu());
        popup.show();
    }




    }
