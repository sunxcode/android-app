package one.mixin.android.ui.common

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_pin_bottom_sheet.view.*
import one.mixin.android.Constants.KEYS
import one.mixin.android.R
import one.mixin.android.extension.vibrate
import one.mixin.android.widget.BottomSheet
import one.mixin.android.widget.Keyboard

abstract class PinBottomSheetDialogFragment : MixinBottomSheetDialogFragment() {

    companion object {
        const val POS_PIN = 0
        const val POS_PB = 1
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        contentView = View.inflate(context, R.layout.fragment_pin_bottom_sheet, null)
        (dialog as BottomSheet).setCustomView(contentView)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        contentView.keyboard.setKeyboardKeys(KEYS)
        contentView.keyboard.setOnClickKeyboardListener(mKeyboardListener)
        contentView.keyboard.animate().translationY(0f).start()
    }

    private val mKeyboardListener: Keyboard.OnClickKeyboardListener = object : Keyboard.OnClickKeyboardListener {
        override fun onKeyClick(position: Int, value: String) {
            context?.vibrate(longArrayOf(0, 30))
            if (position == 11) {
                contentView.pin.delete()
            } else {
                contentView.pin.append(value)
            }
        }

        override fun onLongClick(position: Int, value: String) {
            context?.vibrate(longArrayOf(0, 30))
            if (position == 11) {
                contentView.pin.clear()
            } else {
                contentView.pin.append(value)
            }
        }
    }

    var callback: Callback? = null

    interface Callback {
        fun onSuccess()
    }
}