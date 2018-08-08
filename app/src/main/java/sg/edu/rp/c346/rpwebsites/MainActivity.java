package sg.edu.rp.c346.rpwebsites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Spinner spn1;
    Spinner spn2;
    Button btnGo;
//    WebView wvp;

    ArrayList<String> alCategory;
    ArrayAdapter<String> aaCategory;

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        int posCat = spn1.getSelectedItemPosition();
        int posSubCat = spn2.getSelectedItemPosition();
        prefEdit.putInt("cat",posCat);
        prefEdit.putInt("subCat",posSubCat);
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int catPos = prefs.getInt("cat",0);
        int catSubPos = prefs.getInt("subCat",0);
        spn1.setSelection(catPos);
        spn2.setSelection(catSubPos);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spn1 = findViewById(R.id.spinner1);
        spn2 = findViewById(R.id.spinner2);
        btnGo = findViewById(R.id.buttonGo);
//        wvp = findViewById(R.id.webViewPage);
//
//        wvp.setWebViewClient(new WebViewClient());


        //Initialise the arraylist
        alCategory = new ArrayList<>();

        //Create an arrayadapter using the default spinner layout and the arraylist
        aaCategory = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,alCategory);

        //Bind the arrayadapter to the spinner
        spn2.setAdapter(aaCategory);

        String[] strNumbers = getResources().getStringArray(R.array.subcategory);

//Convert Array to List and add to the ArrayList
        alCategory.addAll(Arrays.asList(strNumbers));


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos =spn1.getSelectedItemPosition();
                int pos2 = spn2.getSelectedItemPosition();

                Intent intent = new Intent(getBaseContext(),webView.class);

                if (pos == 0 && pos2 == 0){
                    intent.putExtra("URL", "https://www.rp.edu.sg/");
                } else if (pos == 0 && pos2 == 1){
                    intent.putExtra("URL", "https://www.rp.edu.sg/student-life");
                } else if (pos == 1 && pos2 == 0){
                    intent.putExtra("URL", "https://www.rp.edu.sg/soi/full-time-diplomas/details/r47");
                } else if (pos == 1 && pos2 == 1){
                    intent.putExtra("URL", "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12");
                }
                startActivity(intent);
            }
        });



        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        alCategory.clear();
                        //Get the string-array and store as an Array
                        String[] strNumbers = getResources().getStringArray(R.array.subcategory);
                        alCategory.addAll(Arrays.asList(strNumbers));
                        spn2.setAdapter(aaCategory);
                        aaCategory.notifyDataSetChanged();
                        break;
                    case 1:
                        alCategory.clear();
                        String[] strNumbers1 = getResources().getStringArray(R.array.subcategory2);
                        alCategory.addAll(Arrays.asList(strNumbers1));
                        spn2.setAdapter(aaCategory);
                        aaCategory.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
