package tech.a0by0.mycalendar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Scanner;

import stanford.androidlib.SimpleActivity;

public class About extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView about = findViewById(R.id.about);
        TextView v = findViewById(R.id.v);
        ImageButton facebook_logo = findViewById(R.id.facebook_logo);

        Scanner scan = new Scanner(getResources().openRawResource(R.raw.aboutapp));
        Scanner version = new Scanner((getResources().openRawResource(R.raw.version)));
        scan.useDelimiter("\\Z");
        version.useDelimiter("\\Z");
        String line1 = scan.next();
        String line2 = version.next();
        about.setText(line1);
     //   about.setText(Html.fromHtml(getString(R.string.html)));
 //       about.loadData("<p style=\"text-align: justify\">"+ line1 + "</p>", "text/html", "UTF-8");
        v.setText(line2);

        facebook_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100011152871634"));
                startActivity(browserIntent);
            }
        });

    }

    public void back_to_calendar(View view) {
        finish();
    }
}
