package test.com.easytranslation;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.com.easytranslation.api.endpoint.ITranslateEnpoint;
import test.com.easytranslation.home.MainContract;
import test.com.easytranslation.home.model.TranslationResponse;
import test.com.easytranslation.home.presenter.MainPresenter;
import test.com.easytranslation.intro.PrefManager;
import test.com.easytranslation.intro.WelcomeActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MainContract.HomeView,ClipboardManager.OnPrimaryClipChangedListener {
    private static final String TAG = "MainActivity";
    private MainPresenter mainPresenter;
    private ClipboardManager clipboard;
    private TextView tvHistory;
    private String history="History : ";
    private String queryText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvHistory=(TextView)findViewById(R.id.tvHistory);
        mainPresenter=new MainPresenter(this);
         clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.addPrimaryClipChangedListener(this);

    }

    private void networkCall() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).build();
        retrofit.create(ITranslateEnpoint.class).getTranslation(Constant.KEY,"en-bn","can","plain").enqueue(new Callback<TranslationResponse>() {
            @Override
            public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                if(response.code()==200){
                    Log.d(TAG, "onResponse()  response = [" + response.body().getText().get(0) + "]");
                }
            }

            @Override
            public void onFailure(Call<TranslationResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvHistory.setText(history);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onCompleteTransLation(String text, String meaningText) {
        history=history+"\n"+text+" : "+meaningText+"\n";
        Toast.makeText(getBaseContext(),text+":"+meaningText,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrimaryClipChanged() {
        String a=clipboard.getText().toString();
        if(queryText!=null&&!this.queryText.equals(a)){
            mainPresenter.translateWord(a);
        }else if(queryText==null){
            mainPresenter.translateWord(a);
        }
        queryText = a;
        Log.d(TAG, "onPrimaryClipChanged() called"+"Copy:\n"+this.queryText);
    }
}
