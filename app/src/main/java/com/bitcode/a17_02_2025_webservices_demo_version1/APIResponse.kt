package com.bitcode.a17_02_2025_webservices_demo_version1

data class APIResponse(
    var page : Int,
    var per_page : Int,
    var total : Int,
    var total_pages : Int,
    var data : ArrayList<User>,
    var support : Support
)
