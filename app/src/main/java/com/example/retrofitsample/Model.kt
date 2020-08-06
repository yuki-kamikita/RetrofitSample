package com.example.retrofitsample

data class ZipApiData(
    var message: String?,
    var results: List<Results>?,
    var status: Int
)

data class Results(
    var address1: String,
    var address2: String,
    var address3: String,
    var kana1: String,
    var kana2: String,
    var kana3: String,
    var prefcode: String,
    var zipcode: String
)