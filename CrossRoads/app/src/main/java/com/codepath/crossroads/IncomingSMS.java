package com.codepath.crossroads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.codepath.crossroads.activities.donors.DonorOfferListActivity;
import com.codepath.crossroads.activities.reviewer.ReviewerOfferListActivity;
import com.codepath.crossroads.models.User;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.HashMap;

/**
 * Created by ivan on 10/26/14.
 */
public class IncomingSMS extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
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



                            ParseCloud.callFunctionInBackground("authenticateConfirmation", parameters, new FunctionCallback<String>() {

                                public void done(String result, ParseException e) {

                                    if (e == null) {

                                        ParseObject user = User.parseUserObject();

                                        try {
                                            user.fetchIfNeeded();
                                        } catch (ParseException innerE) {
                                            innerE.printStackTrace();
                                        }

                                        Toast.makeText(context,R.string.automaticSMSConfirmation,Toast.LENGTH_LONG).show();


                                        boolean isAdmin = user.getBoolean("isAdmin");

                                        if (isAdmin) {
                                            Intent i = new Intent(context, ReviewerOfferListActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            context.startActivity(i);
                                        } else {
                                            Intent i = new Intent(context, DonorOfferListActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            context.startActivity(i);
                                        }
                                    }
                                    else {
                                        Toast.makeText(context,R.string.automaticSMSError,Toast.LENGTH_LONG).show();
                                    }

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
