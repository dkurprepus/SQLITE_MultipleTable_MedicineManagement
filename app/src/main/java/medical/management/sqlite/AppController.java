package medical.management.sqlite;

import android.app.Application;
import android.content.Context;


import java.io.IOException;


/**
 * Created by Cloud 03 on 1/11/2018.
 */

public class AppController extends Application {
    public static AppController application;
    private String errorMessage;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
    public AppController() {
        application = this;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;


    }


    public static synchronized AppController getInstance() {
        return application;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public static AppController getApp() {
        if (application == null) {
            application = new AppController();
        }
        return application;
    }

    public static Context getAppContext() {
        if (application == null) {
            application = new AppController();
        }
        return application;
    }

}
