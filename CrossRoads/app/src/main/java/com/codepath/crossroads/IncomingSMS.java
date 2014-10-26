package com.codepath.crossroads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.codepath.crossroads.models.User;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;

/**
 * Created by ivan on 10/26/14.
 */
public class IncomingSMS extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    if(phoneNumber.equals("+19782527433")) {
                        // Show alert
                        int index = message.indexOf(" is your croossroads confirmation code");

                        if (index > 0) {
                            String code = message.substring(0, index);

                            HashMap<String,String> parameters  = new HashMap<String,String> ();

                            parameters.put("confirmationCode", code);
                            parameters.put("objectId", User.USER_ID);

                            ParseCloud.callFunctionInBackground("authenticateConfirmation", parameters, new FunctionCallback() {
                                @Override
                                public void done(Object o, ParseException e) {

//                                    int duration = Toast.LENGTH_LONG;
//                                    Toast toast = Toast.makeText(,R.string.automaticSMSconfirmation, duration);
//                                    toast.show();

                                }
                            });

                        }
                    }

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
}
