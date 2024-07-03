package com.example.compose.studyhub.util.InfiniteScrolling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.compose.studyhub.data.UserRepository
import kotlin.reflect.KFunction3

@Composable
fun <Any> firstLoad(id: Int, loadFunction: KFunction3<Int, String, (List<Any>?) -> Unit, Unit>): List<Any>? {
    var carreras by remember { mutableStateOf<List<Any>?>(null) }
        UserRepository.getToken()?.let { token ->
            println(id)
            loadFunction(id,token) { success ->
                carreras = success
            }
    }
    return carreras
}