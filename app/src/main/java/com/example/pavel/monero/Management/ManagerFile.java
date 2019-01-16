package com.example.pavel.monero.Management;

import android.content.Context;
import android.util.Log;
import com.example.pavel.monero.utils.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import io.reactivex.Observable;

public class ManagerFile implements IManagementFile {

    private Context context;

    public ManagerFile(Context context) {
        this.context = context;
    }

    @Override
    public Observable<Boolean> saveSelect(Integer type, String FILENAME) {
        return Observable.create((s) -> s.onNext(saveString(context, String.valueOf(type), FILENAME)));
    }

    @Override
    public Observable<Boolean> saveSelect(String type, String FILENAME) {
        return Observable.create((s) -> s.onNext(saveString(context, type, FILENAME)));
    }

    @Override
    public Observable <String>readSelect(String FILENAME, int type) {
        return Observable.create((s) -> s.onNext(loadString(context, FILENAME)));
    }

    private String loadString(Context context, String FILENAME) {
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(FILENAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (Exception e) {
            return Constants.ERROR_DOES_NOT_EXIST;
        }
        return ret;
    }

    private boolean saveString(Context context, String type, String FILENAME) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(type);
            outputStreamWriter.close();
            return true;
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }
    }
}
