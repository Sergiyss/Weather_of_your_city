package com.example.todaysweatherforecast;

import android.content.Context;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;


public class WorkWithFiles extends AppCompatActivity {

    private String filename;
    private String generateText="";

    public void setFiles(String filename){
        this.filename = filename;
    }


    public void writeToFile(String[] arr, Context context) {


        for(int i =0; i < arr.length; i++){
            if(i != arr.length-1){
                generateText += arr[i] + ",";
            }else {
                generateText += arr[i];
            }
        }

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename+".txt",
                    Context.MODE_PRIVATE));
            outputStreamWriter.write(generateText);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public String[] getArrayTextFiles(Context context){

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filename+".txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret.split(",");
    }


}
