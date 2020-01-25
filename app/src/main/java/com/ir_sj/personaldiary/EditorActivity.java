package com.ir_sj.personaldiary;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;

import xute.markdeditor.EditorControlBar;
import xute.markdeditor.MarkDEditor;
import xute.markdeditor.datatype.DraftDataItemModel;
import xute.markdeditor.models.DraftModel;
import xute.markdeditor.utilities.FilePathUtils;

import static com.ir_sj.personaldiary.R.color.yellow;
import static xute.markdeditor.Styles.TextComponentStyle.BLOCKQUOTE;
import static xute.markdeditor.Styles.TextComponentStyle.H1;
import static xute.markdeditor.Styles.TextComponentStyle.H3;
import static xute.markdeditor.Styles.TextComponentStyle.NORMAL;
import static xute.markdeditor.components.TextComponentItem.MODE_OL;
import static xute.markdeditor.components.TextComponentItem.MODE_PLAIN;


public class EditorActivity extends AppCompatActivity implements EditorControlBar.EditorControlListener {

    BottomNavigationView navView;
    private final int REQUEST_IMAGE_SELECTOR = 110;
    private MarkDEditor markDEditor;
    private EditorControlBar editorControlBar;
    PopupMenu popup;
    CoordinatorLayout coordinatorLayout;
    MarkDEditor note;
    String file_path, file_name;

    @Override
    public void onBackPressed() {
        if(markDEditor.isFocused())
            Toast.makeText(getBaseContext(),"g",Toast.LENGTH_SHORT);
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        markDEditor = (MarkDEditor) findViewById(R.id.mdEditor);
        markDEditor.configureEditor(
                "www.google.com/",
                "",
                true,
                "Start Typing...",
                BLOCKQUOTE
        );
        markDEditor.loadDraft(getDraftContent());
        editorControlBar = findViewById(R.id.controlBar);
        editorControlBar.setEditorControlListener(this);
        editorControlBar.setEditor(markDEditor);



        navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.format, R.id.addPic)
                .build();
        Intent intent = getIntent();
        file_name = intent.getStringExtra("GetFileName");
        this.setTitle(intent.getStringExtra("GetFileName"));
        file_path = intent.getStringExtra("GetPath");

        BottomNavigationItemView format = findViewById(R.id.format);
        format.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupFormat(view);
            }
        });
        BottomNavigationItemView bg = findViewById(R.id.bg);
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupBg(view);
            }
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save:
                        sendMail();
                        System.out.println("save");
                        Intent back = new Intent(EditorActivity.this, MainActivity.class);
                        //back.putExtra("activity","editor");
                        System.out.println("h");
                        startActivity(back);
                        System.out.println("u");
                        break;

                    case R.id.addPic:

                        break;

                }
                return true;
            }
        });
    }

    private DraftModel getDraftContent() {
        ArrayList<DraftDataItemModel> contentTypes = new ArrayList<>();
        DraftDataItemModel heading = new DraftDataItemModel();
        heading.setItemType(DraftModel.ITEM_TYPE_TEXT);
        heading.setContent("Set Title Here...");
        heading.setMode(MODE_PLAIN);
        heading.setStyle(H1);

        DraftDataItemModel sub_heading = new DraftDataItemModel();
        sub_heading.setItemType(DraftModel.ITEM_TYPE_TEXT);
        sub_heading.setContent("Set Title Here...");
        sub_heading.setMode(MODE_PLAIN);
        sub_heading.setStyle(H3);

        DraftDataItemModel bl = new DraftDataItemModel();
        bl.setItemType(DraftModel.ITEM_TYPE_TEXT);
        bl.setContent("Quotes on your mind?");
        bl.setMode(MODE_PLAIN);
        bl.setStyle(BLOCKQUOTE);

        DraftDataItemModel body = new DraftDataItemModel();
        body.setItemType(DraftModel.ITEM_TYPE_TEXT);
        body.setContent("Content...");
        body.setMode(MODE_PLAIN);
        body.setStyle(NORMAL);

        DraftDataItemModel hrType = new DraftDataItemModel();
        hrType.setItemType(DraftModel.ITEM_TYPE_HR);

        DraftDataItemModel imageType = new DraftDataItemModel();
        imageType.setItemType(DraftModel.ITEM_TYPE_IMAGE);
        imageType.setDownloadUrl("@drawable/bg1.jpg");
        imageType.setCaption("Click here to go back!");

        DraftDataItemModel list1 = new DraftDataItemModel();
        list1.setItemType(DraftModel.ITEM_TYPE_TEXT);
        list1.setStyle(NORMAL);
        list1.setMode(MODE_OL);
        list1.setContent("ListItem1");

        DraftDataItemModel list2 = new DraftDataItemModel();
        list2.setItemType(DraftModel.ITEM_TYPE_TEXT);
        list2.setStyle(NORMAL);
        list2.setMode(MODE_OL);
        list2.setContent("ListItem2");
        contentTypes.add(heading);
        contentTypes.add(sub_heading);
        contentTypes.add(list1);
        contentTypes.add(list2);
        contentTypes.add(bl);
        contentTypes.add(body);
        contentTypes.add(hrType);
        contentTypes.add(imageType);
//    contentTypes.add(filmsList2);
//    contentTypes.add(filmsList2);
//    contentTypes.add(filmsList2);
//    contentTypes.add(imageType);
//    contentTypes.add(imageType);
//    contentTypes.add(filmsList2);
//    contentTypes.add(filmsList2);
        return new DraftModel(contentTypes);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_SELECTOR) {
            System.out.println("req");
            if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                System.out.println("ok");
                Uri uri = data.getData();
                String filePath = FilePathUtils.getPath(this, uri);
                System.out.println("path");
                addImage(filePath);
            }
        }
    }

    public void addImage(String filePath) {
        markDEditor.insertImage(filePath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMAGE_SELECTOR:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                    //Toast.makeText()"Permission not granted to access images.");
                }
                break;
        }
    }

    private void openGallery() {
        try {
            System.out.println("inside gallery");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_IMAGE_SELECTOR);
            } else {
                System.out.println("gal");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_IMAGE_SELECTOR);
                System.out.println("galo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInsertImageClicked() {
        System.out.println("clicked");
        openGallery();
    }

    @Override
    public void onInserLinkClicked() {
        markDEditor.addLink("Click Here", ".....");
    }

    public void printStack(View view) {
        sendMail();
    }

    private void sendMail() {
        System.out.println("send");
        SaveFile sf = new SaveFile();
        DraftModel dm = markDEditor.getDraft();
        String json = new Gson().toJson(dm);
        System.out.println(json);
        sf.location = file_path+"/_"+file_name;
        System.out.println(sf.location);
        sf.WriteToFile(json);
        System.out.println("written");
        Log.d("MarkDEditor", json);
    }


    public void showPopupFormat(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.format_text_menu, popup.getMenu());
        popup.show();
    }

    public void showPopupBg(View v) {
        System.out.println("pop");
        popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.bg_popup, popup.getMenu());

        /*if(selected_items.size()>1)
        {        popup.getMenu().findItem(R.id.rename).setEnabled(false);
            popup.getMenu().findItem(R.id.details).setEnabled(false);}
        popup.setGravity(Gravity.BOTTOM);*/
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                note = (MarkDEditor) findViewById(R.id.mdEditor);
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext(), R.style.Theme_AppCompat_Dialog_MinWidth);
                coordinatorLayout = findViewById(R.id.layout);
                switch (item.getItemId()) {
                    case R.id.yellow:
                        coordinatorLayout.setBackgroundColor(getResources().getColor(yellow));
                        //note.setTextColor(getResources().getColor(R.color.colorPrimary));
                        note.setBackgroundColor(getResources().getColor(yellow));

                        return true;
                    case R.id.blue:
                        coordinatorLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                        //note.setTextColor(getResources().getColor(R.color.darkb));
                        note.setBackgroundColor(getResources().getColor(R.color.blue));

                        return true;
                    case R.id.green:
                        coordinatorLayout.setBackgroundColor(getResources().getColor(R.color.lgreen));
                        //note.setTextColor(getResources().getColor(R.color.dblue));
                        note.setBackgroundColor(getResources().getColor(R.color.lgreen));
                        return true;
                    case R.id.red:
                        coordinatorLayout.setBackgroundColor(getResources().getColor(R.color.red));
                        //note.setTextColor(getResources().getColor(R.color.blue));
                        note.setBackgroundColor(getResources().getColor(R.color.red));
                        return true;
                    case R.id.defaul:
                        coordinatorLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        //note.setTextColor(getResources().getColor(R.color.black));
                        note.setBackgroundColor(getResources().getColor(R.color.white));
                        return true;
                        default: return  false;
                }
            }
        });
    }
}


