package in.ka4tik.ribbit;

import android.app.Application;

import com.parse.Parse;

public class RibbitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "f5iothZDTOV3s2HAB24AOgGub3nHeSSM2Bs6mMLj", "cRyYSYM0Xzk2xBlex3mfyGHhaaY4ZXHRgd9hINJN");

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();

    }

}
