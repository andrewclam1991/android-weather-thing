package com.andrewclam.weatherclient.feature.scannerx.service;

import android.bluetooth.BluetoothAdapter;
import android.support.annotation.UiThread;

import com.andrewclam.weatherclient.feature.scannerx.data.event.ScannerEventDataSource;
import com.andrewclam.weatherclient.feature.scannerx.data.result.ScannerResultDataSource;
import com.andrewclam.weatherclient.feature.scannerx.model.ScannerXEvent;
import com.andrewclam.weatherclient.feature.scannerx.model.ScannerXResult;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

class ScannerXController implements ScannerXContract.Controller {

  private final ScannerEventDataSource mEventDataSource;

  private final ScannerResultDataSource mResultDataSource;

  private final CompositeDisposable mCompositeDisposable;

  private final BluetoothAdapter.LeScanCallback mScanCallback;

  private final static int SCAN_STATE_IN_PROGRESS = 1;
  private final static int SCAN_STATE_IDLE = 0;
  private int mScanState;

  @Inject
  ScannerXController(@NonNull ScannerEventDataSource eventDataSource,
                     @NonNull ScannerResultDataSource resultDataSource) {
    mEventDataSource = eventDataSource;
    mResultDataSource = resultDataSource;
    mCompositeDisposable = new CompositeDisposable();
    mScanCallback = (device, rssi, scanRecord) -> mResultDataSource.add(ScannerXResult.result(device));
  }

  @Override
  public Flowable<ScannerXResult> getModel() {
    return mResultDataSource.get();
  }

  @Override
  public void start() {
    mCompositeDisposable.add(mEventDataSource.get()
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onEvent, this::onError)
    );
  }

  @Override
  public void stop() {
    mCompositeDisposable.clear();
  }

  @UiThread
  private void onEvent(@ScannerXEvent String event) {
    switch (event) {
      case ScannerXEvent.START_SCAN:
        startScan();
        break;
      case ScannerXEvent.STOP_SCAN:
        stopScan();
        break;
    }
  }

  @UiThread
  private void onError(Throwable throwable) {
    Timber.e(throwable, "Error in service event source.");
  }

  private void startScan() {
    if (mScanState == SCAN_STATE_IN_PROGRESS) {
      Timber.w("Scan is already in progress, start scan ignored.");
      return;
    }
    mScanState = SCAN_STATE_IN_PROGRESS;
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    mResultDataSource.add(ScannerXResult.inProgress());
    adapter.startLeScan(mScanCallback);

    mCompositeDisposable.add(Completable.fromAction(this::stopScan)
        .delay(10, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .subscribe()
    );
  }

  private void stopScan() {
    if (mScanState == SCAN_STATE_IDLE) {
      Timber.w("Scan is already idle, stop scan makes no sense.");
      return;
    }
    mScanState = SCAN_STATE_IDLE;
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    adapter.stopLeScan(mScanCallback);
    mResultDataSource.add(ScannerXResult.complete());
  }
}