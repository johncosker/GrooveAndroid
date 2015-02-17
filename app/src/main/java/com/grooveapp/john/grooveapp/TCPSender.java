package com.grooveapp.john.grooveapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by john on 1/22/15.
 */
public class TCPSender extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate=null;
    private String data = null;
    private Socket socket;
    String response;
    private static final int SERVERPORT = 5505;
    public PrintWriter out;
    public BufferedReader in ;
    public String mTask = "";

    protected String doInBackground(String... strings) {
        if(3 == strings.length){
            mTask = strings[2];
        }
        try {
            InetAddress serverAddr = InetAddress.getByName(strings[0]);
            socket = new Socket(serverAddr, SERVERPORT);
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        try{
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.print(strings[1]);
            out.flush();

            response = in.readLine();
            out.close();
            in.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    protected void onPostExecute(String response){
        if(delegate != null)
            delegate.processFinish(response, mTask);
    }
}
