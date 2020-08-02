package com.codedao.noichu.presentation.main.home

import androidx.lifecycle.ViewModelProviders
import com.codedao.noichu.R
import com.codedao.noichu.common.di.AppComponent
import com.codedao.noichu.common.extension.click
import com.codedao.noichu.common.extension.observe
import com.codedao.noichu.presentation.base.BaseFragment
import com.codedao.noichu.presentation.main.MainViewModel
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment<MainViewModel>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity?:return, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun setUpView() {
        homeClick.click {
            //navigate(R.id.detailFragment)
            fireData()
        }
    }

    override fun observable() {
        observe(viewModel.checkAppExpireLiveData) { expire ->
            Logger.d(expire)
            homeClick.text = "Go to detail"
        }
    }

    override fun showErrorMsg(err: Throwable) {
        super.showErrorMsg(err)
        homeClick.text = "Go to detail"
    }

    override fun fireData() {
        viewModel.checkAppExpire()
    }
}