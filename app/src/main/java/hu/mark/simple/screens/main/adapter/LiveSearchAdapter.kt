package hu.mark.simple.screens.main.adapter

import android.content.Context
import android.database.Cursor
import android.support.v4.widget.SimpleCursorAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LiveSearchAdapter(
    context: Context?,
    layout: Int,
    c: Cursor?,
    from: Array<out String>?,
    to: IntArray?,
    flags: Int
) :
    SimpleCursorAdapter(context, layout, c, from, to, flags) {

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return TextView(context)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        (view as TextView).setPadding(10, 10, 10, 10)
        view.textSize = 20f
        view.text = cursor?.getString(cursor.getColumnIndex("Filter")) ?: ""
    }
}