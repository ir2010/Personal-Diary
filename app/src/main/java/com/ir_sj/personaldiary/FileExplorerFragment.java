package com.ir_sj.personaldiary;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.FileProvider;

import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link //OnListFragmentInteractionListener}
 * interface.
 */
public class FileExplorerFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    //private OnListFragmentInteractionListener mListener;
    public File currentDir;
    private GridView grid;
    public List<Item>dir = new ArrayList<Item>();
    //MyItemRecyclerViewAdapter adapter;
    FileArrayAdapter adapter;
    private List<Item> selected_items = new ArrayList<Item>();
    int count_selected=0, flag=0;
    int task=0;
    Context c;
    PopupMenu popup;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FileExplorerFragment() {
    }


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FileExplorerFragment newInstance(int columnCount) {
        FileExplorerFragment fragment = new FileExplorerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
         c = getContext();
        setHasOptionsMenu(true);
        currentDir = new File("/data/data/com.ir_sj.personaldiary/");
        fill(currentDir);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        grid = view.findViewById(R.id.fileGrid);

        //btmNav = view.findViewById(R.id.bottom_navigation);
        //btmNav.setVisibility(View.GONE);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(c, R.style.Theme_AppCompat_Dialog_MinWidth);
        FileArrayAdapter adapter = new FileArrayAdapter(c, R.layout.fragment_item, dir);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Item item = (Item) adapterView.getItemAtPosition(i);
                if (item.getImage().equalsIgnoreCase("@drawable/ic_folder_black_24dp")) {
                    currentDir = new File(item.getPath());
                    fill(currentDir);
                    FileArrayAdapter adapter = new FileArrayAdapter(c, R.layout.fragment_item, dir);
                    grid.setAdapter(adapter);
                }   else {
                    onFileClick(item);
                }
            }
        });
        grid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        grid.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                flag =0;
                Item currentItem = (Item) grid.getItemAtPosition(i);
                System.out.println("1");
                System.out.println(currentItem.getName());
                //selected_items.add((Item) grid.getItemAtPosition(i));
                //selected_items.add(currentItem);
                //Item item = selected_items.get(count_selected);
                for(Item item: selected_items)
                {
                    System.out.println("2");
                    if(item.getName()== currentItem.getName())
                    {   //selected_items.add(currentItem);
                        System.out.println("3");
                        System.out.println(item.getName());
                        selected_items.remove(item);
                        count_selected--;
                        actionMode.setTitle(count_selected+" items selected");
                        flag=1;
                        break;
                    }
                }
                if(flag==0) {
                    System.out.println("4");
                    selected_items.add(currentItem);
                    count_selected++;
                    actionMode.setTitle(count_selected+" items selected");
                }
                for(Item gt: selected_items)
                {
                    System.out.println("Inside selected items");
                    System.out.println(gt.getName());
                }

                    //grid.setBackgroundColor(R.drawable.my_selector);
                //Toast.makeText(c, count_selected + " items selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = actionMode.getMenuInflater();
                menuInflater.inflate(R.menu.bottom_nav_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.delete:
                        alertDialog.setTitle("Delete");
                        alertDialog.setMessage("Are you sure you want to delete " + count_selected + " items?");

                        alertDialog.setIcon(R.drawable.ic_delete_black_24dp);

                        alertDialog.setPositiveButton("Delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        for (Item files : selected_items) {
                                            System.out.println("inside del");
                                            System.out.println(files.getName());
                                            System.out.println(files.getPath());
                                            File file = new File(files.getPath());
                                            System.out.println("after file");
                                            deleteDirectory(file);
                                        }
                                        fill(currentDir);
                                        FileArrayAdapter adapter = new FileArrayAdapter(c, R.layout.fragment_item, dir);
                                        grid.setAdapter(adapter);
                                        selected_items.clear();
                                    }
                                });


                        alertDialog.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        selected_items.clear();
                                    }
                                });

                        alertDialog.show();
                        break;

                    case R.id.share:
                        ArrayList<Uri> photos = new ArrayList<>();
                        for(Item files: selected_items)
                        photos.add(FileProvider.getUriForFile(c, "com.ir_sj.personaldiary", new File(files.getPath())));
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "This multiple files I'm sharing.");
                        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, photos);
                        shareIntent.setType("file/*");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(shareIntent, "Share..."));
                        selected_items.clear();
                        break;

                    case R.id.more:
                        showPopupMenu(view);
                        task=1;
                        //selected_items.clear();
                        break;
                }
                count_selected = 0;
                flag = 0;
                if(task==0)
                actionMode.finish();
                return true;
            }





            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });







        // Set the adapter
        /*if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new MyItemRecyclerViewAdapter(items, mListener));
            //recyclerView.setAdapter(adapter);*/
        return view;
        }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //if (context instanceof OnListFragmentInteractionListener) {
            //mListener = (OnListFragmentInteractionListener) context;
        //} else {
            //throw new RuntimeException(context.toString()
                    //+ " must implement OnListFragmentInteractionListener");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Item item);
    }*/



    public void fill(File f)
    {
        dir.clear();
        System.out.println(f.getAbsolutePath());
        if(!f.getAbsolutePath().equals("/data/data/com.ir_sj.personaldiary"))
        {
        System.out.println("inside if");
            dir.add(0,new Item("......",f.getParent(),"@drawable/ic_folder_black_24dp",f.getAbsoluteFile()));}
        File[]dirs = f.listFiles();
        //this.setTitle("Current Dir: "+f.getName());
        List<Item>fls = new ArrayList<Item>();
        try{
            for(File ff: dirs)
            {
                Date lastModDate = new Date(ff.lastModified());
                System.out.println("1");
                DateFormat formater = DateFormat.getDateTimeInstance();
                System.out.println("2");
                String date_modify = formater.format(lastModDate);
                System.out.println("3");
                if(ff.isDirectory())
                {
                    System.out.println("4");
                    File[] fbuf = ff.listFiles();
                    int buf = 0;
                    if(fbuf != null){
                        buf = fbuf.length;
                    }
                    else buf = 0;
                    System.out.println("5");
                    String num_item = String.valueOf(buf);
                    if(buf == 0) num_item = num_item + " item";
                    else num_item = num_item + " items";
                    //String formated = lastModDate.toString();
                    //dir.add(new Item(ff.getName(),num_item,date_modify,ff.getAbsolutePath(),"directory_icon"));
                    if(ff.getName().substring(0,4).equals("app_"))
                    System.out.println("6");
                    System.out.println(ff.getName());
                    if(!(ff.getName().equals("cache")) && !(ff.getName().equals("code_cache")) && !(ff.getName().equals("files")) )
                        dir.add(new Item(ff.getName(), num_item, date_modify, ff.getAbsolutePath(),"@drawable/ic_folder_black_24dp"));
                    //dir.add(new Item(ff.getName(),ff.getAbsolutePath(),"@drawable/ic_folder_black_24dp", ff.getAbsoluteFile()));
                    System.out.println("7");
                }
                else
                {
                    System.out.println("8");
                    fls.add(new Item(ff.getName(),ff.length() + " Bytes", date_modify, ff.getAbsolutePath(),"@drawable/ic_insert_drive_file_black_24dp"));
                    //fls.add(new Item(ff.getName(), ff.getAbsolutePath(),"@drawable/ic_insert_drive_file_black_24dp", ff.getAbsoluteFile()));
                }
            }
        }catch(Exception e)
        {
            Toast.makeText(c, "error", Toast.LENGTH_SHORT).show();
        }
        //Collections.sort(dir);
        //Collections.sort(fls);
        dir.addAll(fls);
           // dir.add(new Item("..",f.getParent(),"@drawable/ic_folder_black_24dp"));
    }

    private void onFileClick(Item o)
    {
        SaveFile sf = new SaveFile();
        //FileExplorerFragment fef = new FileExplorerFragment();
        //Toast.makeText(c, "Folder Clicked: "+ currentDir, Toast.LENGTH_SHORT).show();
        System.out.println(o.getPath().substring(0,36));
        if(o.getPath().substring(0,36).equals("/data/data/com.ir_sj.personaldiary/_"))
        { sf.location = o.getPath();
            System.out.println("if");
            String str =sf.ReadFromFile();
            Intent editorIntent = new Intent(getActivity(), SeeFile.class);
            File file = new File(o.getPath());
            editorIntent.putExtra("GetFileName",o.getName());
            editorIntent.putExtra("GetPath",file.getParent());
            editorIntent.putExtra("GetData",str);
        }
        else{
        Intent editorIntent = new Intent(getActivity(), EditorActivity.class);
        File file = new File(o.getPath());
        editorIntent.putExtra("GetFileName",o.getName());
        editorIntent.putExtra("GetPath",file.getParent());
        //setResult(RESULT_OK, intent);
        startActivity(editorIntent);}
        //finish();
    }

    public void loadFragment()
    {
        FileExplorerFragment fragment = new FileExplorerFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fileFragment,fragment);
        fragmentTransaction.commit();
    }

    public boolean deleteDirectory(File dir) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(c, R.style.Theme_AppCompat_Dialog_MinWidth);
        if (dir.isDirectory()) {
           final File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
            }
        }
        System.out.println("removing file or directory : " + dir.getName());
        return dir.delete();
    }

    public void showPopupMenu(View v) {
        System.out.println("pop");
        popup = new PopupMenu(c, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.more_menu, popup.getMenu());

        if(selected_items.size()>1)
        {        popup.getMenu().findItem(R.id.rename).setEnabled(false);
            popup.getMenu().findItem(R.id.details).setEnabled(false);}
        popup.setGravity(Gravity.BOTTOM);
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(c, R.style.Theme_AppCompat_Dialog_MinWidth);
                switch (item.getItemId()) {
                    case R.id.rename:
                        alertDialog.setTitle("Rename");
                        alertDialog.setMessage("Enter new name..");
                        final EditText input = new EditText(c);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        alertDialog.setView(input);

                        alertDialog.setIcon(R.drawable.ic_create_black_24dp);

                        alertDialog.setPositiveButton("Rename",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        for (Item files : selected_items) {
                                            System.out.println("inside ren");
                                            System.out.println(files.getName());
                                            System.out.println(files.getPath());
                                            File file = new File(files.getPath());
                                            System.out.println(file.getParent()+"/"+input.getText());
                                            File renamedFile = new File(file.getParent()+input.getText());
                                            System.out.println("after file");
                                            file.renameTo(renamedFile);
                                            try{
                                            FileWriter out= new FileWriter(renamedFile, true );
                                            }
                                            catch (Exception e){}
                                            System.out.println(files.getPath());
                                        }
                                        fill(currentDir);
                                        FileArrayAdapter adapter = new FileArrayAdapter(c, R.layout.fragment_item, dir);
                                        grid.setAdapter(adapter);
                                        selected_items.clear();
                                    }
                                });


                        alertDialog.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        selected_items.clear();
                                    }
                                });

                        alertDialog.show();

                        return true;
                    case R.id.move:
                        final GridView grid = getView().findViewById(R.id.fileGrid);
                        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                Item item = (Item) adapterView.getItemAtPosition(i);

                                if (item.getImage().equalsIgnoreCase("@drawable/ic_folder_black_24dp")) {
                                    for(Item items:selected_items)
                                    {
                                    }
                                    fill(currentDir);
                                    FileArrayAdapter adapter = new FileArrayAdapter(c, R.layout.fragment_item, dir);
                                    grid.setAdapter(adapter);
                                }   else {
                                    Snackbar.make(view,"Can't move to a file",Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                        selected_items.clear();


                        return true;
                    case R.id.details:
                        for (Item files : selected_items) {
                            System.out.println("inside del");
                            System.out.println(files.getName());
                            System.out.println(files.getPath());
                            File file = new File(files.getPath());
                            alertDialog.setTitle("Details");
                            alertDialog.setMessage("Name: "+files.getName()+" Size: "+files.getData()+" Last Modified: "+files.getDate());

                            alertDialog.setIcon(R.drawable.ic_description_black_24dp);


                            alertDialog.setNegativeButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            selected_items.clear();
                                        }
                                    });

                            alertDialog.show();

                            System.out.println("after file");

                        }

                        selected_items.clear();
                        return true;
                    case R.id.protect:

                        selected_items.clear();
                        return true;
                    default:
                        return false;
                }
                }



        });
    }

    /*
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Item o = adapter.getItem(position);
        if(o.getImage().equalsIgnoreCase("directory_icon")||o.getImage().equalsIgnoreCase("directory_up")){
            currentDir = new File(o.getPath());
            fill(currentDir);
        }
        else
        {
            onFileClick(o);
        }
    }
    private void onFileClick(Item o)
    {
        //Toast.makeText(this, "Folder Clicked: "+ currentDir, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("GetPath",currentDir.toString());
        intent.putExtra("GetFileName",o.getName());
        setResult(RESULT_OK, intent);
        finish();
    }*/
}
