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
    CompositeDisposable mCompositeDisposable=new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carsObservable=Observable.fromArray("Mazda","Ferrari","volvo");




    }

    @Override
    protected void onResume() {
        super.onResume();

        mCompositeDisposable.add(
                carsObservable.observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .filter(s-> s.startsWith("M"))
                        .subscribeWith(new DisposableObserver<String>() {


                            @Override
                            public void onNext(String s) {
                                Log.d("" +getClass().getName(), ""+s);

                                /*Toast won't work because it's runs on a thread*/
                                //               Toast.makeText(MainActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                Log.d("" +getClass().getName(), "Completed-1");
                            }
                        })
        );


        mCompositeDisposable.add(
                carsObservable.observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .map(s-> "cars: "+ s +"" )
                        .subscribeWith(new DisposableObserver<String>() {
                            @Override
                            public void onNext(String s) {
                                Log.d("" +getClass().getName(), ""+s);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                Log.d("" +getClass().getName(), "Completed-2");
                            }
                        })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCompositeDisposable.clear();
    }

}
