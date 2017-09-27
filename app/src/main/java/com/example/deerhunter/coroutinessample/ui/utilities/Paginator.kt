package com.example.deerhunter.coroutinessample.ui.utilities

class Paginator(private val action: (Int) -> Unit) {

    private var isRequested = false

    var totalPagesNumber = Int.MAX_VALUE
    var currentPageNumber = 0

    fun next() {
        if (currentPageNumber > 0 && !isRequested && currentPageNumber < totalPagesNumber) {
            isRequested = true
            action(currentPageNumber + 1)
        }
    }

    fun received(pageNumber: Int) {
        this.currentPageNumber = pageNumber
        isRequested = false
    }

    fun reset() {
        currentPageNumber = 0
        totalPagesNumber = Int.MAX_VALUE
        isRequested = false
    }
}