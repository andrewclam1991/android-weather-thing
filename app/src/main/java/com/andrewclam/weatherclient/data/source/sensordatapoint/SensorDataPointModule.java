/*
 * Copyright 2018 Andrew Chi Heng Lam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.andrewclam.weatherclient.data.source.sensordatapoint;

import android.support.annotation.NonNull;

import com.andrewclam.weatherclient.data.source.Cache;
import com.andrewclam.weatherclient.data.source.Local;
import com.andrewclam.weatherclient.data.source.Remote;
import com.andrewclam.weatherclient.data.source.Repo;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * This is used by Dagger to inject the required arguments into the {@link SensorDataPointRepository}.
 */
@Module
public abstract class SensorDataPointModule {

  @NonNull
  @Singleton
  @Binds
  @Repo
  abstract SensorDataPointDataSource providesRepository(@NonNull SensorDataPointRepository repository);

  @NonNull
  @Singleton
  @Binds
  @Cache
  abstract SensorDataPointDataSource providesCacheDataSource(@NonNull SensorDataPointCacheDataSource repository);

  @NonNull
  @Singleton
  @Binds
  @Local
  abstract SensorDataPointDataSource providesLocalDataSource(@NonNull SensorDataPointLocalDataSource dataSource);

  @NonNull
  @Singleton
  @Binds
  @Remote
  abstract SensorDataPointDataSource providesRemoteDataSource(@NonNull SensorDataPointLocalDataSource dataSource);

}