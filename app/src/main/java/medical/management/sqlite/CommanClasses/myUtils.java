package medical.management.sqlite.CommanClasses;

import android.widget.Toast;

import medical.management.sqlite.AppController;

public class myUtils {

public static void ShowToast(String toastMsg){
    Toast.makeText(AppController.getAppContext(),toastMsg , Toast.LENGTH_SHORT).show();
}
}
