package com.example.shopping_list_via_architecture_components.ui.base

class BaseContract {

    interface Presenter<in T> {
        fun subscribe()
        fun unsubscribe()
        fun attach(view: T)
    }

    interface View {
    }
}