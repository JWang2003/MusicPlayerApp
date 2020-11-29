package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class SettingsMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SeekBar rickrollChanceSlider;
    TextView rickrollOddsDisplay;
    ConstraintLayout mConstraintLayout;
    int rickrollOdds = 1;
    String pfpSelection;
    String themeSelection;
    int pfpSelectionIndex;
    int themeSelectionIndex;
    int pfp;
    int theme;
    int flag;

    @Override
    protected void onPause(){
        super.onPause();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("rickrollodds", rickrollOdds);
        if (!(pfp == 0)) {
            editor.putInt("PFP", pfp);
        }
        if (!(theme == 0)) {
            editor.putInt("Theme", theme);
        }
        if (!(theme == 0)) {
            editor.putInt("Flag", flag);
        }
        editor.putInt("PFPIndex", pfpSelectionIndex);
        editor.putInt("themeIndex", themeSelectionIndex);
        // Commit the edits!
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Restore preferences
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        rickrollOdds = settings.getInt("rickrollodds", 0);
        pfp = settings.getInt("PFP", 0);
        theme = settings.getInt("Theme", 0);
        flag = settings.getInt("Flag", 0);
        pfpSelectionIndex = settings.getInt("PFPIndex", 0);
        themeSelectionIndex = settings.getInt("themeIndex", 0);

        // This checks if page is not null aka, page is being reloaded
        setContentView(R.layout.settings_main);
        System.out.println(rickrollOdds);
        // Go back to mainactivity
        System.out.println("pfp: "+ pfp);

        // Connect to XML
        Button back = findViewById(R.id.back);
        mConstraintLayout = findViewById(R.id.settingsscreen);

        if (theme != 0) {
            mConstraintLayout.setBackgroundColor(theme);
        } else {
            mConstraintLayout.setBackgroundColor(R.drawable.gradientbackgroundred);
        }

        // Set up back button
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setSelections();
                Intent intent = new Intent(SettingsMain.this, MainActivity.class);
                intent.putExtra("EXTRA_RICKROLL_ODDS_UPPERLIMIT", rickrollOdds);
                startActivity(intent);
            }
        });

        final Button apply = findViewById(R.id.applyButton);
        apply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setSelections();
            }
        });


        // FLAG THEME SELECTION SPINNER
        Spinner themeSpinner = (Spinner) findViewById(R.id.countrySpinner);
        ArrayAdapter<CharSequence> themeAdapter = ArrayAdapter.createFromResource(this, R.array.flags_array, R.layout.item_spinner);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(themeAdapter);
        themeSpinner.setOnItemSelectedListener(this);

        // PFP THEME SELECTION SPINNER
        Spinner pfpSpinner = (Spinner) findViewById(R.id.pfpSpinner);
        ArrayAdapter<CharSequence> pfpAdapter = ArrayAdapter.createFromResource(this, R.array.pfps_array, R.layout.item_spinner);
        pfpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pfpSpinner.setAdapter(pfpAdapter);
        pfpSpinner.setOnItemSelectedListener(this);

        // PULL SELECTIONS FROM STORED
        themeSpinner.setSelection(themeSelectionIndex);
        pfpSpinner.setSelection(pfpSelectionIndex);

        // RICKROLL CHANCE SEEK BAR
        rickrollOddsDisplay = (TextView) findViewById(R.id.rickrollOddsDisplay);
        rickrollChanceSlider = (SeekBar) findViewById(R.id.rickrollOddsSlider);
        rickrollChanceSlider.setProgress(rickrollOdds - 1);
        rickrollOddsDisplay.setText("1 : " + rickrollOdds);
        rickrollChanceSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rickrollOdds = progress + 1;
                rickrollOddsDisplay.setText("1 : " + rickrollOdds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.countrySpinner:
                themeSelectionIndex = parent.getSelectedItemPosition();
                themeSelection = parent.getSelectedItem().toString();
                break;
            case R.id.pfpSpinner:
                pfpSelectionIndex = parent.getSelectedItemPosition();
                pfpSelection = parent.getSelectedItem().toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    void setSelections(){
        switch (pfpSelection) {
            case "Xi":
                pfp = R.drawable.pfp_xi;
                break;
            case "Trudeau":
                pfp = R.drawable.pfp_trudeau;
                break;
            case "Johnson":
                pfp = R.drawable.pfp_johnson;

                break;
            case "Biden":
                pfp = R.drawable.pfp_biden;

                break;
        }
        switch (themeSelection) {
            case "China":
                theme = R.drawable.gradientbackgroundred;
                flag = R.drawable.flag_china;
                break;
            case "Canada":
                System.out.println("Selected Canada");
                theme = R.drawable.gradientbackgroundred;
                flag = R.drawable.flag_canada;
                break;
            case "UK":
                theme = R.drawable.gradientbackgroundblue;
                flag = R.drawable.flag_uk;
                break;
            case "USA":
                theme = R.drawable.gradientbackgroundblue;
                flag = R.drawable.flag_usa;
                break;
        }
    }


}