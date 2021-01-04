package com.aucto.core

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList

/**
 * @author GWL
 *
 */
abstract class BaseViewModel : ViewModel() {

    open var toolBarTitle: ObservableField<String> = ObservableField()
    open var toolBarColor: ObservableField<Int> = ObservableField()
    val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPrefetchDistance(2)
        .setPageSize(5)
        .build()
//    open var toolBarTitle: String = ""
//    open var toolBarColor: Int = R.color.white
}

