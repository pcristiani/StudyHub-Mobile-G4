package com.example.compose.studyhub

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.Destinations.INICIO_ROUTE
import com.example.compose.studyhub.Destinations.LOGIN_ROUTE
import com.example.compose.studyhub.Destinations.QUESTION_RESULTS_ROUTE
import com.example.compose.studyhub.Destinations.QUESTION_ROUTE
import com.example.compose.studyhub.Destinations.REGISTER_ROUTE
import com.example.compose.studyhub.ui.component.registrarEstudiante.QuestionRoute
import com.example.compose.studyhub.ui.route.InicioRoute
import com.example.compose.studyhub.ui.route.LoginRoute
import com.example.compose.studyhub.ui.route.RegisterRoute

///
object Destinations {
   
   const val INICIO_ROUTE = "inicio"
   const val REGISTER_ROUTE = "signup/{ci}"
   const val LOGIN_ROUTE = "login/{ci}"
   const val QUESTION_ROUTE = "questionLogin"
   const val QUESTION_RESULTS_ROUTE = "screenNovedades"
}


///
@Composable
fun StudyHubNavHost(
   navController: NavHostController = rememberNavController(),
                   ) {
   NavHost(navController = navController, startDestination = INICIO_ROUTE) {
      composable(INICIO_ROUTE) {
         InicioRoute(
            onNavigateToLogin = { navController.navigate("login/$it") },
            onLoginInvitado = { navController.navigate(QUESTION_ROUTE) },
                    )
      }
      
      composable(LOGIN_ROUTE) {
         val startingCi = it.arguments?.getString("ci")
         LoginRoute(
            ci = startingCi,
            onLoginSubmitted = { navController.navigate(QUESTION_RESULTS_ROUTE)},
            onNavigateToRegister = {navController.navigate("signup/$it")},
            onLoginInvitado = { navController.navigate(QUESTION_ROUTE) },
            onNavUp = navController::navigateUp,
                   )
      }
      
      composable(REGISTER_ROUTE) {
         val ci = it.arguments?.getString("ci")
         RegisterRoute(
            ci = ci,
            onRegisterSubmitted = { navController.navigate(INICIO_ROUTE) },
            onLoginInvitado = { navController.navigate(QUESTION_ROUTE) },
            onNavUp = navController::navigateUp,
                      )
      }
      
      composable(QUESTION_ROUTE) {
         val startingEmail = it.arguments?.getString("email")
         QuestionRoute(
            email = startingEmail,
            onQuestionComplete = { navController.navigate(INICIO_ROUTE) },
            onNavUp = navController::navigateUp,
                      )
      }
      
      composable(QUESTION_RESULTS_ROUTE) {
         val startingEmail = it.arguments?.getString("email")
         val navController = rememberNavController()
         SetupNavGraph(navController = navController)
      }
   }
   
} // QuestionResultScreen2 { navController.navigate(INICIO_ROUTE) }
