package com.example.compose.studyhub.util.InfiniteScrolling



fun <Any> loadMoreItems(displayableList: MutableList<Any>, list: List<Any>) {
    val currentSize = displayableList.size
    val listLength = if ((list.size - currentSize) < 13) {
        (list.size - currentSize)
    } else {
        13
    }
    for (i in 0 until listLength) {
        displayableList.add(list[currentSize + i])
    }
}