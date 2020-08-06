package com.example.retrofitsample

import android.database.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    //http://zipcloud.ibsnet.co.jp/api/search?zipcode=1000001のドメイン名以下の部分を記述する
    //FQDNは別のところで指定する
    @GET("api/search")

    //@Queryが?zipcode=ZipCideを生成してくれる ZipCodeは関数の引数として与えられる
    //返値としてWebAPIを叩きにいくサービスが生成される
    //ジェネリクスには返値としてさきほど作成したデータの型を指定しておく
    fun apiDemo(@Query("zipcode") ZipCode: String): Call<ZipApiData>

    @GET("api/{zipcode}/search")
    fun apiDemoPath(@Path("zipcode") ZipCode: String): Call<ZipApiData>
}