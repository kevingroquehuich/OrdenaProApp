package com.roque.ordenaproapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Products

@Serializable
data class ProductDetail(val id: String)

@Serializable
object Cart

@Serializable
object OrderSummary

@Serializable
object OrderList