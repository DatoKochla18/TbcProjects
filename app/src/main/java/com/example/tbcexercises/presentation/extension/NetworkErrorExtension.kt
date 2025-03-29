package com.example.tbcexercises.presentation.extension

import com.example.tbcexercises.R
import com.example.tbcexercises.domain.util.error.NetworkError


fun NetworkError.asStringResource():Int{
    return  when(this){
        NetworkError.ConnectionError -> R.string.connection_error_plz_try_again
        NetworkError.EmptyResponse -> R.string.empty_response_try_again
        is NetworkError.HttpError -> R.string.http_error_something_went_wrong
        is NetworkError.ServerError -> R.string.nikas_backend_aint_backing
    }
}