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
import com.example.compose.studyhub.ui.estudiante.QuestionResultScreen2
import com.example.compose.studyhub.ui.route.InicioRoute
import com.example.compose.studyhub.ui.route.LoginRoute
import com.example.compose.studyhub.ui.route.RegisterRoute

///
object Destinations {
   const val INICIO_ROUTE = "inicio"
   const val REGISTER_ROUTE = "signup/{email}"
   const val LOGIN_ROUTE = "login/{email}"
   const val QUESTION_ROUTE = "questionLogin"
   const val QUESTION_RESULTS_ROUTE = "questionresults"
}

///
@Composable
fun StudyHubNavHost(
   navController: NavHostController = rememberNavController(),
) {
   NavHost(navController = navController, startDestination = INICIO_ROUTE) {
      composable(INICIO_ROUTE) {
         println("------------------------------------ 0 NAVIGATION ")
         InicioRoute(
            onNavigateToLogin = { navController.navigate("login/$it") },
            onNavigateToRegister = { navController.navigate("signup/$it") },
            onLoginInvitado = { navController.navigate(QUESTION_ROUTE) },
         )
      }

      composable(LOGIN_ROUTE) {
         val startingEmail = it.arguments?.getString("email")
         println("------------------------------------ 1 NAVIGATION " + startingEmail)
         LoginRoute(
            email = startingEmail,
            onLoginSubmitted = { navController.navigate(QUESTION_RESULTS_ROUTE) },
            onLoginInvitado = { navController.navigate(QUESTION_ROUTE) },
            onNavUp = navController::navigateUp,
         )
      }

      composable(REGISTER_ROUTE) {
         val startingEmail = it.arguments?.getString("email")
         println("------------------------------------ 2 NAVIGATION " + startingEmail)
         RegisterRoute(
            email = startingEmail,
            onRegisterSubmitted = { navController.navigate(QUESTION_ROUTE) },
            onLoginInvitado = { navController.navigate(QUESTION_ROUTE) },
            onNavUp = navController::navigateUp,
         )
      }

      composable(QUESTION_ROUTE) {
         val startingEmail = it.arguments?.getString("email")
         println("------------------------------------ 3 NAVIGATION " + startingEmail)
         QuestionRoute(
            email = startingEmail,
            onQuestionComplete = { navController.navigate(INICIO_ROUTE) },
            onNavUp = navController::navigateUp,
         )
      }

      composable(QUESTION_RESULTS_ROUTE) {
         val startingEmail = it.arguments?.getString("email")
         println("------------------------------------ 4 NAVIGATION " + startingEmail) // val navControllers = rememberNavController()
         // SetupNavGraph(navController = navControllers)
         QuestionResultScreen2 { navController.navigate(INICIO_ROUTE) }
      }
   }
} //QuestionResultScreen2 { navController.navigate(INICIO_ROUTE) }
