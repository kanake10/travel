package com.example.travel.presentation

sealed class Screen(val route: String) {
    object SignInScreen: Screen("sign_in")
    object PropertyScreen: Screen("property_list")
    object PropertyDetailScreen: Screen("details")
}
