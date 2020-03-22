package tech.a0by0.mycalendar;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import static android.content.Context.MODE_PRIVATE;

public class SettingsPageModule extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public SettingsPageModule(ReactApplicationContext reactContext) {
        super(reactContext); //required by React Native
        this.context = reactContext;
    }

    @Override
    //getName is required to define the name of the module represented in JavaScript
    public String getName() {
        return "SettingsPageModule";
    }

    @ReactMethod
    public void exitSettingsPage() {
        Intent intent = new Intent(this.context, Calendar.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(intent);
    }

    @ReactMethod
    public void setStartingDayOfWeekSunday(Boolean value){
        SQLiteDatabase db = this.context.openOrCreateDatabase("events",MODE_PRIVATE,null);
        Cursor cr = db.rawQuery("SELECT * FROM AppConstants WHERE name='StartingDayOfWeekSunday'", null);

        if(cr.getCount() == 1){
            db.execSQL("UPDATE AppConstants SET value = '" + value + "' WHERE name='StartingDayOfWeekSunday';");
        }
        else{
            ContentValues newConstant = new ContentValues();
            newConstant.put("name", "StartingDayOfWeekSunday");
            newConstant.put("value", value.toString());
            db.insert("AppConstants",null, newConstant);
        }
        AppConstants.StartingDayOfWeekSunday = value;
    }
}
