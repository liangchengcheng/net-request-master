package mvp_master.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import mvp_master.libs.net.Net;
import mvp_master.libs.net.Result;
import mvp_master.libs.okhttp.OkHttpStack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //未测试，以下仅为示例代码
        Net.get("url",
                new CommParser<User>("user") {
                }, new Net.Callback<User>() {
                    @Override
                    public void callback(Result<User> result) {
                        if (result.getStatus() == Result.SUCCESS) {
                            User user = result.getResult();

                        } else {

                        }
                    }
                }, getClass().getName());
    }

    @Override
    protected void onDestroy() {
        Net.cancel(getClass().getName());
        super.onDestroy();
    }

}
