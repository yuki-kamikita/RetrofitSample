package com.example.retrofitsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    //作成するinner class以外の記述は省略
    //今回は面倒なのでコンストラクタに処理を記述した
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //retrofitのインスタンスを生成して、様々な設定をする
        var retrofit = Retrofit.Builder().let {

            //WebAPIのアクセス先のドメインを指定する
            it.baseUrl("http://192.168.11.11:3000/")
//            it.baseUrl("http://zipcloud.ibsnet.co.jp/")

            //受信したjson形式のデータを、kotlinのデータクラスに格納するコンバータを指定する
            //今回はgoogleのgsonというコンバータのインスタンスを渡す
            it.addConverterFactory(GsonConverterFactory.create())
            it.build()
        }

        //サービスのインスタンスを生成する
        //アクセス先のURLとデータクラスの型を記述したインターフェースを指定する
        var service = retrofit.create(ApiClient::class.java)

        //これが実行させるメソッド
        //引数として郵便番号を指定する
        //enqueueは非同期処理（別スレッド）で通信を行うメソッド
        //引数としてコールバックさせるクラスを指定する
        service.apiDemo("1000001").enqueue(ZipApiDataCallback())
    }

    //Callback<T>を継承したクラスを作る
    //innerクラスだと親クラスのメンバ変数にもアクセスできるので、MainActibity内にクラスを作ると便利
    inner class ZipApiDataCallback : retrofit2.Callback<ZipApiData> {

        //データ受信時に発生すると呼ばれるメソッド
        override fun onResponse(call: Call<ZipApiData>?, response: Response<ZipApiData>?) {
            //response.body()がZipApiDataの本体になる
            //nullじゃなければログを残す
            if (response?.body()?.results != null) {
                if (response.body()!!.results!!.count() > 0) {
                    Log.v("nullpo", response.body()!!.results!![0].address1)
                    Log.v("nullpo", response.body()!!.results!![0].address2)
                    Log.v("nullpo", response.body()!!.results!![0].address3)
                }
            }
            textView.text = "ok"
        }

        //失敗したときに呼ばれるメソッド
        override fun onFailure(call: Call<ZipApiData>?, t: Throwable?) {
            Log.d("nullpo", "Call Back Failed")
        }
    }
}

// 出典：http://android.techblog.jp/archives/9614747.html

