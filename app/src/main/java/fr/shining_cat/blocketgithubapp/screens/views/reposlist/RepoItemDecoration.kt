package fr.shining_cat.blocketgithubapp.screens.views.reposlist

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RepoItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: -1
        with(outRect) {
            top = spaceSize
            left = 0
            right = 0
            bottom =
                if (itemCount > 0 && itemPosition == itemCount - 1) spaceSize else 0 // add space below last item
        }
    }
}