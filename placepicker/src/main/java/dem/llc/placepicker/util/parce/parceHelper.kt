package dem.llc.placepicker.util.parce

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Parcelable

inline fun <reified T:Parcelable> Intent.getParcelable(name: String) : T? = when{
    SDK_INT >= 33 -> getParcelableExtra(name, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(name) as T?
}