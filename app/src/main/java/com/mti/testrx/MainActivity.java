/*
 * Created by Tareq Islam on 3/1/19 7:37 PM
 *                      
 *  Last modified 3/1/19 7:36 PM
 */

package com.mti.testrx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Observable<String> carsObservable;
    Disposable mDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carsObservable=Observable.fromArray("Mazda","Ferrari","volvo");




    }

    @Override
    protected void onResume() {
        super.onResume();

       carsObservable.observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                        .filter(s-> s.startsWith("M"))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("" +getClass().getName(), ""+s);
                        Toast.makeText(MainActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

      mDisposable.dispose();
    }
}
