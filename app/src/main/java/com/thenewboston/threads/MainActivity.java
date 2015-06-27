//Thread: making things faster. Java loads the program in order from top down.
// However, this means that if you have an early long calculation on, the user will have to wait a long time and can't do anything else.
// Threads solves that problem so the app doesn't have to wait for the other method to load before loading later methods.
package com.thenewboston.threads;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBuckysButton(View view) {

        final Handler handler = new Handler() { //set Handler to run update interface, threads aren't allowed to update interface because its bad practice and will crash the program

            @Override
            public void handleMessage(Message msg) {

                //This updates the interface from "Hello" to "Nice Job Hoss!"
                TextView buckysText = (TextView) findViewById(R.id.buckysText);
                buckysText.setText("Nice Job Hoss!");
            }
        };

        Runnable r = new Runnable() { //Runnable: stick the code in here that you want to run in the background
            @Override
            public void run() {
                //just a wait 10 second application to use as an example of a calculation that takes a long time
                long futureTime = System.currentTimeMillis() + 10000;
                while(System.currentTimeMillis() < futureTime){
                    synchronized (this){ //synchronized prevents multiple threads from bumping into each other, basically it "synchronizes it"
                        try{
                            wait(futureTime-System.currentTimeMillis());
                        }catch(Exception e){}
                    }
                }
                //-----------------------------------------------------------------------------------------------------------------------------------

                handler.sendEmptyMessage(0); //send empty message from "handleMessage" above
            }
        };

        Thread buckysThread = new Thread(r);
            buckysThread.start();


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
