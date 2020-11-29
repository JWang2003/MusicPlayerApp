package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.R.id.countrySpinner;
import static com.example.R.id.pfpSpinner;
import static com.example.R.id.textView;

public class SettingsMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SeekBar rickrollChanceSlider;
    TextView rickrollOddsDisplay;
    int rickrollOdds = 6;
    String pfpSelection = "Xi";
    String themeSelection = "China";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Created oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);

        // Go back to mainactivity
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SettingsMain.this, MainActivity.class);
                intent.putExtra("EXTRA_RICKROLL_ODDS_UPPERLIMIT", rickrollOdds);
                startActivity(intent);
            }
        });

        final Button apply = findViewById(R.id.applyButton);
        apply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (pfpSelection) {
                    case "Xi":
                        // TODO: set profile picture in activity_main.xml
                        break;
                    case "Trudeau":

                        break;
                    case "Johnson":

                        break;
                    case "Biden":

                        break;
                }
                switch (themeSelection) {
                    case "China":
                        // TODO: edit backgrounds of all 3 xml files
                        break;
                    case "Canada":

                        break;
                    case "UK":

                        break;
                    case "USA":

                        break;
                }

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

        // RICKROLL CHANCE SEEK BAR
        rickrollOddsDisplay = (TextView) findViewById(R.id.rickrollOddsDisplay);
        rickrollChanceSlider = (SeekBar) findViewById(R.id.rickrollOddsSlider);
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
                themeSelection = parent.getSelectedItem().toString();
                break;
            case R.id.pfpSpinner:
                pfpSelection = parent.getSelectedItem().toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

}