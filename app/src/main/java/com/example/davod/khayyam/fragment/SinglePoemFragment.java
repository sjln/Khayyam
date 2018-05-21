package com.example.davod.khayyam.fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davod.khayyam.PoemHolder;
import com.example.davod.khayyam.R;
import com.example.davod.khayyam.activity.PoemMenuActivity;
import com.example.davod.khayyam.adapter.RecyclerViewAdapterPoems;
import com.example.davod.khayyam.dataModel.DataModelPoems;
import com.example.davod.khayyam.saveData.KhayyamDatabaseOpenHelper;
import com.example.davod.khayyam.saveData.SettingSharedPrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DaVoD on 1/24/2018.
 */

public class SinglePoemFragment extends Fragment {

    private View view;
    private int id;
    private DataModelPoems model;
    private KhayyamDatabaseOpenHelper openHelper ;
    private SettingSharedPrefManager prefManager;
    private List<DataModelPoems> models = new ArrayList<>();
    private String[] poem;
    private byte fontName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_single_poem,container,false);
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        setupLayout(view);
    }


    @SuppressLint("SetTextI18n")
    public void setupLayout(View view) {

        TextView txt_title = view.findViewById(R.id.txt_title_single_poem);
        TextView txt1 = view.findViewById(R.id.textView1_single_poem);
        TextView txt2 = view.findViewById(R.id.textView2_single_poem);
        TextView txt3 = view.findViewById(R.id.textView3_single_poem);
        TextView txt4 = view.findViewById(R.id.textView4_single_poem);

        txt_title.setText(model.getTitle_cover());

        poem = PoemHolder.getPoem(model.getPoem_id(), model.getNumber_of_poem());

        txt1.setText("       "+poem[0]+"     ");
        txt2.setText("       "+poem[1]+"     ");
        txt3.setText("       "+poem[2]+"     ");
        txt4.setText("       "+poem[3]+"     ");

        prefManager = new SettingSharedPrefManager(getContext());

        txt1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,prefManager.getSetting().getFontSize());
        txt2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,prefManager.getSetting().getFontSize());
        txt3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,prefManager.getSetting().getFontSize());
        txt4.setTextSize(TypedValue.COMPLEX_UNIT_DIP,          prefManager.getSetting().getFontSize());

    }

    public static SinglePoemFragment newInstance(int id,DataModelPoems model) {
        Bundle args = new Bundle();
        args.putInt(PoemMenuActivity.ID,id);
        args.putParcelable(RecyclerViewAdapterPoems.MODEL,model);
        SinglePoemFragment fragment = new SinglePoemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        id = getArguments().getInt(PoemMenuActivity.ID,0);
        model = getArguments().getParcelable(RecyclerViewAdapterPoems.MODEL);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.single_poem_menu, menu);
        openHelper =  new KhayyamDatabaseOpenHelper(getContext());
        if (!openHelper.getPoems().isEmpty()) {
            if (openHelper.getPoemIsFavorite(model.getId()) == 1) {
                menu.findItem(R.id.add_favorites).setIcon(R.drawable.vc_favorite_red_24dp);
            }
        } else
            menu.findItem(R.id.add_favorites).setIcon(R.drawable.vc_favorite);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text = poem[0]+"/"+poem[1]+"\n"+poem[2]+"/"+poem[3];
        switch (item.getItemId()) {
            case R.id.add_favorites:
                if (openHelper.getPoemIsFavorite(model.getId()) == 0) {
                    item.setIcon(R.drawable.vc_favorite_red_24dp);
                    model.setPoem_id(id);
                    models.add(model);
                    openHelper.addPoems(models);
                    setIsFavorite(model.getId());
//                    VectorDrawable vector = (VectorDrawable) ContextCompat.getDrawable(this, R.drawable.vc_favorite);
//                    vector.setColorFilter(ContextCompat.getColor(this, R.color.red), PorterDuff.Mode.SRC_IN);
                } else {
                    item.setIcon(R.drawable.vc_favorite);
                    KhayyamDatabaseOpenHelper openHelper = new KhayyamDatabaseOpenHelper(getContext());
                    openHelper.deletePoem(model.getId());
//                    VectorDrawable vector = (VectorDrawable) ContextCompat.getDrawable(this, R.drawable.vc_favorite);
//                    vector.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);
                }
                break;
            case R.id.copy:
                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("copy",text);
                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getContext(),"متن کپی شد",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,text);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,getResources().getText(R.string.send_to)));
                FirebaseAnalytics.getInstance(getActivity());
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setIsFavorite(int id) {
        KhayyamDatabaseOpenHelper openHelper = new KhayyamDatabaseOpenHelper(getContext());
        openHelper.setPoemsIsFavorite(id);
    }


}
