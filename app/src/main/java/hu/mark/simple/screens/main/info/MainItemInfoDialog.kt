package hu.mark.simple.screens.main.info

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import hu.mark.simple.R
import hu.mark.simple.data.SimpleResponses
import hu.mark.simple.util.Util
import kotlinx.android.synthetic.main.main_item_info.view.*
import java.text.SimpleDateFormat
import java.util.*

class MainItemInfoDialog : DialogFragment() {

    private lateinit var data: SimpleResponses.Response
    private lateinit var locStart: IntArray
    private var locEnd: IntArray = IntArray(2)

    fun createDialogWith(data: SimpleResponses.Response, loc: IntArray): MainItemInfoDialog {
        val dialogFragment = MainItemInfoDialog()
        dialogFragment.data = data
        dialogFragment.locStart = loc
        return dialogFragment
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog)

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.main_item_info, container, false)

        if (view != null) {
            view.rl_info.setBackgroundColor(activity!!.resources.getColor(R.color.background_dialog))
            view.tv_info_name.text = data.name

            if (!TextUtils.isEmpty(data.icon)) {
                Util.showImage(view.iv_info_logo, data.icon!!)
            }

            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)

            val from = dateFormat.parse(data.startDate)
            val to = dateFormat.parse(data.endDate)

            view.dp_info_item_from.init(from.year + 1900, from.month, from.day, null)
            view.dp_info_item_from.minDate = from.time
            view.dp_info_item_from.maxDate = from.time
            view.dp_info_item_to.init(to.year + 1900, to.month, to.day, null)
            view.dp_info_item_to.minDate = to.time
            view.dp_info_item_to.maxDate = to.time

            view.iv_info_close.setOnClickListener {
                view.iv_info_close.getLocationOnScreen(locEnd)
                animationEnd()
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window.setLayout(width, height)

        animationStart()
    }

    private fun animationStart() {
        dialog.window.decorView.scaleX = 0f
        dialog.window.decorView.scaleY = 0f

        dialog.window.decorView.pivotX = locStart[0].toFloat()
        dialog.window.decorView.pivotY = locStart[1].toFloat()

        dialog.window.decorView
            .animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(900)
            .start()
    }

    private fun animationEnd() {
        dialog.window.decorView.pivotX = locEnd[0].toFloat()
        dialog.window.decorView.pivotY = locEnd[1].toFloat()

        dialog.window.decorView
            .animate()
            .scaleX(0f)
            .scaleY(0f)
            .setDuration(300)
            .withEndAction { dismiss() }
            .start()
    }

}