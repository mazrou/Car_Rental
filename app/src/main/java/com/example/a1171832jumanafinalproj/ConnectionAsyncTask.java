package com.example.a1171832jumanafinalproj;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {

    Activity activity;

    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        ((Connect) activity).setButtonText("connecting");
        super.onPreExecute();
        ((Connect) activity).setProgress(true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            ((Connect) activity).setProgress(false);
            ((Connect) activity).setButtonText("connected");
            List<Car> cars = CarJasonParser.getObjectFromJason(s);
            assert cars != null;
            System.out.println("\n\n\n"+cars.get(1).toString()+"\n\n\n");
            ((Connect) activity).fillcars(cars);
            ((Connect) activity).setstatus(true);
        }
        catch(Exception e){
            ((Connect) activity).setstatus(false);
            ((Connect) activity).setButtonText("Try Again!");
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String data = HttpManager.getData(params[0]);
        return data;
    }


}
