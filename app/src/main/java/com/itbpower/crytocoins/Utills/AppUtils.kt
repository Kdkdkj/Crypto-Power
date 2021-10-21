package Utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD

class AppUtils(applicationContext:Context) {
    private var mContext: Context? = applicationContext
    var dialog: KProgressHUD? = null
    var  ImageUrl:String?="http://apptestitb.cf/public/Uploads/"


    fun isInternetConnected(): Boolean {
        val cm = mContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun showDialog(context: Context?) {
        dialog = KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setMaxProgress(100)
            .setCancellable(false)
    }
    fun ShowMessage(context: Context?,msg:String?) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {

        }

    }

}


