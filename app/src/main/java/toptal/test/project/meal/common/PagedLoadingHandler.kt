package toptal.test.project.meal.common

abstract class PagedLoadingHandler {

    private var pageSize: Int = 30

    private var isLoadingPage: Boolean = false
        private set

    var nextPage: Int? = null
        set(value) {
            field = value
            isLoadingPage = false
        }

    abstract fun onLoadNextPage(page: Int)

    private val hasNextPage
        get() = nextPage != null

    fun checkForNewPage(position: Int, totalItemCount: Int) {
        //If we are not loading anything, and there are more pages
        if (!isLoadingPage && hasNextPage) {
            //Load new page if the 1st page has been loaded and when there's about half a page items
            // left until display:
            if (totalItemCount > 0 && position >= totalItemCount - pageSize / 2) {
                isLoadingPage = true
                onLoadNextPage(nextPage!!)
            }
        }
    }

}
