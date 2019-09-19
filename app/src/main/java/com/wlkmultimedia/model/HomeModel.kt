package com.wlkmultimedia.model

data class HomeSubModel(val tv:String,val iv:Int)
data class HomeModel(val title:String,val childList:List<HomeSubModel>)