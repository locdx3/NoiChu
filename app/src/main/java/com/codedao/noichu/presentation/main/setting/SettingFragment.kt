package com.codedao.noichu.presentation.main.setting

import androidx.lifecycle.ViewModelProviders
import com.codedao.noichu.R
import com.codedao.noichu.common.di.AppComponent
import com.codedao.noichu.presentation.base.BaseFragment
import com.codedao.noichu.presentation.main.MainViewModel

class SettingFragment: BaseFragment<MainViewModel>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_setting
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity?:return, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun setUpView() {
    }

    override fun observable() {
    }

    override fun fireData() {
        viewModel.checkAppExpire()
    }
}