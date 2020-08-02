package com.codedao.noichu.presentation.main.favorite

import androidx.lifecycle.ViewModelProviders
import com.codedao.noichu.R
import com.codedao.noichu.common.di.AppComponent
import com.codedao.noichu.presentation.base.BaseFragment
import com.codedao.noichu.presentation.main.MainViewModel

class FavoriteFragment: BaseFragment<MainViewModel>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_favorite
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