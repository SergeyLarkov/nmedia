package ru.netology.nmedia

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import ru.netology.nmedia.data.Post
import java.util.*

fun toText(value: Int): String {
    val i: Int
    val d: Int
    val units: String
    if (value >= 1_000_000) {
        i = value / 1_000_000
        d = (value % 1_000_000) / 100_000
        units = "M"
    } else if (value >= 1_000) {
        i = value / 1_000
        d = if (value >= 10_000) 0 else (value % 1_000) / 100
        units = "K"
    } else {
        i = value
        d = 0
        units = ""
    }
    return if (d != 0) i.toString() + "." + d.toString() + units else i.toString() + units
}

fun hideKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken,0)
}

fun showKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view,0)
    //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
}

fun getPostsList(): List<Post> {
 val posts = listOf<Post>(
    Post(1,
        "Аффтор",
        Date(2022,1,1).toString(),
        "С новым годом!",
        "",
        false,
        1099,
        680,
        2656),
    Post(2,
        "Аффтор",
        Date(2022,1,1,0,0,0).toString(),
        "мультик!",
        "https://www.youtube.com/watch?v=0xF8tOoT-PU",
        false,
        0,
        0,
        0),
     Post(3,
         "Аффтор",
         Date(2022,1,1,0,0,0).toString(),
         "Happy New Year!",
         "",
         false,
         0,
         0,
         0),
     Post(4,
         "User",
         Date(2022,1,1,0,0,0).toString(),
         "Всем привет!",
         "",
         false,
         0,
         0,
         0),
     Post(3,
         "User",
         Date(2022,1,1,0,0,0).toString(),
         "Еще мультик",
         "https://www.youtube.com/watch?v=MhWOFOzKM2M",
         false,
         0,
         0,
         0))
    return posts
}