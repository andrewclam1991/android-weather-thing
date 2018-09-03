package com.andrewclam.weatherclient.view.scanner;

import com.andrewclam.weatherclient.view.BasePresenter;
import com.andrewclam.weatherclient.view.BaseView;

public interface ScannerViewContract {

  interface View extends BaseView {
    void showScannerInProgress(boolean isVisible);

    void onUserStartScan();

    void onUserStopScan();

  }

  interface Presenter extends BasePresenter<View> {
    void loadScannerState();

    void loadPeripherals();
  }
}
