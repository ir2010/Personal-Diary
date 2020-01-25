package com.ir_sj.personaldiary;


import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
@RequiresApi(api = Build.VERSION_CODES.M)
public class AuthenticationHandler extends FingerprintManager.AuthenticationCallback {

    private  FingerprintAuth mainActivity;
    private Context context;

    public AuthenticationHandler(FingerprintAuth mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        Toast.makeText(mainActivity, "Authentication Error:"+ errString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        Toast.makeText(mainActivity, "Authentication Help"+ helpString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        Toast.makeText(mainActivity, "Authentication Succeeded", Toast.LENGTH_SHORT).show();
        mainActivity.startActivity(new Intent(mainActivity,
                HiddenFolder.class));

    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        Toast.makeText(mainActivity, "Authentication Failed" , Toast.LENGTH_SHORT).show();
    }
}
