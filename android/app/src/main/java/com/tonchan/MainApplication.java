package com.tonchan;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.PackageList;
import com.facebook.hermes.reactexecutor.HermesExecutorFactory;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.facebook.react.ReactApplication;
import io.sentry.RNSentryPackage;
import com.transistorsoft.rnbackgroundfetch.RNBackgroundFetchPackage;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.security.ProviderInstaller.ProviderInstallListener;

import java.util.Arrays;
import java.util.List;

import com.tonchan.generated.BasePackageList;

import org.unimodules.adapters.react.ModuleRegistryAdapter;
import org.unimodules.adapters.react.ReactModuleRegistryProvider;
import org.unimodules.core.interfaces.SingletonModule;

public class MainApplication extends Application implements ReactApplication {
  private final ReactModuleRegistryProvider mModuleRegistryProvider = new ReactModuleRegistryProvider(new BasePackageList().getPackageList(), Arrays.<SingletonModule>asList());

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
      List<ReactPackage> packages = new PackageList(this).getPackages();
      // Packages that cannot be autolinked yet can be added manually here
      packages.add(new RNBackgroundFetchPackage());
      packages.add(new TurtleCoinPackage());
      packages.add(new ModuleRegistryAdapter(mModuleRegistryProvider));
      return packages;
    }

    @Override
    protected String getJSMainModuleName() {
      return "index";
    }
  };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    upgradeSecurityProvider();
    SoLoader.init(this, /* native exopackage */ false);
  }

  private void upgradeSecurityProvider() {
    ProviderInstaller.installIfNeededAsync(this, new ProviderInstallListener() {
      @Override
      public void onProviderInstalled() {
      }

      @Override
      public void onProviderInstallFailed(int errorCode, Intent recoveryIntent) {
        GooglePlayServicesUtil.showErrorNotification(errorCode, MainApplication.this);
      }
    });
  }
}
