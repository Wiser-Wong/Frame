package com.wiser.library.helper;

import javax.inject.Singleton;

import com.wiser.library.manager.WISERManage;

import dagger.Component;

@Singleton
@Component
public interface IWISERComponent {

    void inject(WISERManage manage);

}
