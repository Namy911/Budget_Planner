package com.endava.budgetplanner.common.ext

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun hideKeyboardFrom(view: View) {
    val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}