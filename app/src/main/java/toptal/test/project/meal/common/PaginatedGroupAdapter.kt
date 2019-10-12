package toptal.test.project.meal.common

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

internal class PaginatedGroupAdapter : GroupAdapter<GroupieViewHolder>() {

    var pagedLoadingHandler: PagedLoadingHandler? = null

    override fun onBindViewHolder(holder: GroupieViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        pagedLoadingHandler?.checkForNewPage(position, itemCount)
    }
}
