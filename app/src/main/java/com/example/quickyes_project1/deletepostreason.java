package com.example.quickyes_project1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link deletepostreason#newInstance} factory method to
 * create an instance of this fragment.
 */
public class deletepostreason extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public deletepostreason() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment deletepostreason.
     */
    // TODO: Rename and change types and number of parameters
    public static deletepostreason newInstance(String param1, String param2) {
        deletepostreason fragment = new deletepostreason();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView rv;
    reasontodeleteallpostdisplayadpater myadpter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_deletepostreason, container, false);
        rv=view.findViewById(R.id.deletepostreasonrecyclerview);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseAuth fba=FirebaseAuth.getInstance();
        FirebaseUser fbu=fba.getCurrentUser();
        FirestoreRecyclerOptions<allpostretrivedataclass> options = new FirestoreRecyclerOptions.Builder<allpostretrivedataclass>()
                .setQuery(FirebaseFirestore.getInstance().collection("Deletepost").whereEqualTo("Email",fbu.getEmail()), allpostretrivedataclass.class)
                .build();
        myadpter=new reasontodeleteallpostdisplayadpater(options);
        rv.setAdapter(myadpter);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        myadpter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        myadpter.stopListening();
    }
}