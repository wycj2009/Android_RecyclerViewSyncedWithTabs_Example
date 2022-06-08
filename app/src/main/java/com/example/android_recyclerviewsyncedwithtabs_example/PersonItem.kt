package com.example.android_recyclerviewsyncedwithtabs_example

data class PersonItem(
        val viewType: ViewType,
        val name: String
) {
    enum class ViewType {
        Horizontal,
        Vertical
    }
}
