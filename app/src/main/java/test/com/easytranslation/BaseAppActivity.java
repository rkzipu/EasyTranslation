package test.com.easytranslation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

import test.com.easytranslation.deps.DaggerDeps;
import test.com.easytranslation.deps.Deps;
import test.com.easytranslation.networking.NetworkModule;

/**
 * Created by ennur on 6/28/16.
 */
public class BaseAppActivity extends AppCompatActivity{
    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    public Deps getDeps() {
        return deps;
    }
}
