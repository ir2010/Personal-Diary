package com.ir_sj.personaldiary;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ir_sj.personaldiary.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditorActivityFragment extends Fragment {

    public EditorActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editor, container, false);
    }
}
