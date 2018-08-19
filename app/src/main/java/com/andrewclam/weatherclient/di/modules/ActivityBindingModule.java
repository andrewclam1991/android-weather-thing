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
 *
 * com.andrewclam.weatherclient.di.modules.ActivityBindingModule
 */

package com.andrewclam.weatherclient.di.modules;

import dagger.Module;

/**
 * We want Dagger.Android to create a Sub-component which has a parent Component of whichever
 * module ActivityBindingModule is on.
 * <p>
 * In our case that will be AppComponent. The beautiful part about this setup is that you never
 * need to tell AppComponent that it is going to have all these sub-components
 * nor do you need to tell these sub-components that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the
 * specified modules and be aware of a scope annotation @ActivityScoped
 * <p>
 * When Dagger.Android annotation processor runs it will create the sub-components for us.
 */
@Module
public abstract class ActivityBindingModule {
//  @NonNull
//  @ActivityScoped
//  @ContributesAndroidInjector(modules = {MonitorModule.class, TrackingModule.class,
//      ProgressModule.class})
//  abstract DashboardActivity dashboardActivity();
//
//  @NonNull
//  @ServiceScoped
//  @ContributesAndroidInjector(modules = {TrackingModule.class})
//  abstract TrackingService trackingService();
}