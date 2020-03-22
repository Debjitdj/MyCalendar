package tech.a0by0.mycalendar;

import android.os.Bundle;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;

import javax.annotation.Nullable;

public class MainActivity extends ReactActivity {

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */


  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new ReactActivityDelegate(this, getMainComponentName()) {
      @Override
      protected @Nullable Bundle getLaunchOptions() {
        Bundle initialProperties = new Bundle();
        initialProperties.putBoolean("startingDayOfWeekSunday", AppConstants.StartingDayOfWeekSunday);
        return initialProperties;
      }
    };
  }

  @Override
  protected String getMainComponentName() {
    return "SettingsPage";
  }

}
