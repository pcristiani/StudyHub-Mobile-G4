package com.example.compose.studyhub.util

import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getPreviaturasGrafoRequest


fun getGraphImage(idCarrera: Int) {

    val token = UserRepository.getToken()

    var graphData: String

    if (token != null) {
        getPreviaturasGrafoRequest(idCarrera, token){success ->
            if(success!=null){
                graphData = success

                graphData = graphData.trimIndent()

                //val (nodes, edges) = parseGraph(graphData)
            }
        }
    }


}