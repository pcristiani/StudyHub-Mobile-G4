package com.example.compose.studyhub

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.compose.studyhub.data.User
import com.example.compose.studyhub.data.UserRepository
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
   const val EDITAR_PERFIL_ROUTE = "editarPerfil"
}


///
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StudyHubNavHost(
   navController: NavHostController = rememberNavController(),
                   ) {
   val startDestination: String

   startDestination = if(UserRepository.user == User.NoUserLoggedIn) {
      INICIO_ROUTE
   }else{
      QUESTION_RESULTS_ROUTE
   }


   NavHost(navController = navController, startDestination = startDestination
      ) {
      composable(INICIO_ROUTE) {
         InicioRoute(
            onNavigateToLogin = {

               if(it.isNotEmpty()){navController.navigate("login/$it")} },
                    )
      }
      
      composable(LOGIN_ROUTE) {
         val startingCi = it.arguments?.getString("ci")
         LoginRoute(
            ci = startingCi,
            onLoginSubmitted = { navController.navigate(QUESTION_RESULTS_ROUTE)},
            onNavigateToRegister = {navController.navigate("signup/$it")},
            onNavUp = navController::navigateUp,
                   )
      }
      
      composable(REGISTER_ROUTE) {
         val ci = it.arguments?.getString("ci")
         RegisterRoute(
            ci = ci,
            onRegisterSubmitted = { navController.navigate(INICIO_ROUTE) },
            onNavUp = navController::navigateUp,
                      )
      }
      
   /*    composable(QUESTION_ROUTE) {
         val startingEmail = it.arguments?.getString("email")
         QuestionRoute(
            email = startingEmail,
            onQuestionComplete = { navController.navigate(INICIO_ROUTE) },
            onNavUp = navController::navigateUp,
                      )
      } */
      
      composable(QUESTION_RESULTS_ROUTE) {
         val navController = rememberNavController()
         SetupNavGraph(navController = navController)
      }

   }



} // QuestionResultScreen2 { navController.navigate(INICIO_ROUTE) }
