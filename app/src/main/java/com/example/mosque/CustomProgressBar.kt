package com.example.mosque

import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.view.LayoutInflater
import androidx.annotation.NonNull
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.progress_bar.view.*
import org.jetbrains.annotations.NonNls

class CustomProgressBar {

    lateinit var dialog: Dialog

    fun show(context: Context): Dialog{
        return show(context)
    }

    fun show(context: Context, title:CharSequence?): Dialog {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflator.inflate(R.layout.progress_bar, null)
        if (title !=null){
            view.cp_title.text = title
        }
        view.cp_bg_view.setBackgroundColor(Color.parseColor("#60000000"))
        view.cp_cardview.setCardBackgroundColor(Color.parseColor("#70000000"))
        setColorFilter(view.cp_pbar.indeterminateDrawable,
            ResourcesCompat.getColor(context.resources, R.color.colorPrimary, null)) //Progress Bar Color
        view.cp_title.setTextColor(Color.WHITE) //Text Color

        dialog = Dialog(context, R.style.CustomProgressBarTheme)
        dialog.setContentView(view)
        dialog.show()

        return dialog
    }

    private fun setColorFilter(@NonNull drawable:Drawable, color: Int) {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        }else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }
}