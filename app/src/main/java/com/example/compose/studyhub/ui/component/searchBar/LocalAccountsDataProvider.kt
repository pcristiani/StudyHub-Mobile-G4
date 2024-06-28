
package com.example.compose.studyhub.ui.component.searchBar

import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.Account



object LocalAccountsDataProvider {

    val allUserAccounts = listOf(
        Account(
            id = 1L,
            uid = 0L,
            firstName = "Sebastián",
            lastName = "González",
            email = "s@gmail.com",
            altEmail = "hkngfan@outside.com",
            avatar = R.drawable.avatar2,
            isCurrentAccount = true
        ),
        Account(
            id = 2L,
            uid = 0L,
            firstName = "Lucas",
            lastName = "Sugo",
            email = "jeffersonloveshiking@gmail.com",
            altEmail = "jeffersonloveshiking@work.com",
            avatar = R.drawable.avatar1
        ),
        Account(
            id = 3L,
            uid = 0L,
            firstName = "Jeff",
            lastName = "Hansen",
            email = "jeffersonc@google.com",
            altEmail = "jeffersonc@gmail.com",
            avatar = R.drawable.avatar3
        )
    )

    private val allUserContactAccounts = listOf(
        Account(
            id = 4L,
            uid = 1L,
            firstName = "Sebastián",
            lastName = "González",
            email = "s@gmail.com",
            altEmail = "tracealvie@gravity.com",
            avatar = R.drawable.avatar1
        ),
        Account(
            id = 5L,
            uid = 2L,
            firstName = "Lucas",
            lastName = "Sugo",
            email = "atrabucco222@gmail.com",
            altEmail = "atrabucco222@work.com",
            avatar = R.drawable.a16_robot
        ),
        Account(
            id = 6L,
            uid = 3L,
            firstName = "Sebastián",
            lastName = "González",
            email = "sgonzalez@gmail.com",
            altEmail = "aliconnors@android.com",
            avatar = R.drawable.lenz
        ),
        Account(
            id = 7L,
            uid = 4L,
            firstName = "Sebastián",
            lastName = "González",
            email = "s@gmail.com",
            altEmail = "albertowilliams124@chromeos.com",
            avatar = R.drawable.a28_nerd
        ),
        Account(
            id = 8L,
            uid = 5L,
            firstName = "Kim",
            lastName = "Alen",
            email = "alen13@gmail.com",
            altEmail = "alen13@mountainview.gov",
            avatar = R.drawable.a21_alien
        ),
        Account(
            id = 9L,
            uid = 6L,
            firstName = "Google",
            lastName = "Express",
            email = "express@google.com",
            altEmail = "express@gmail.com",
            avatar = R.drawable.a25_supermario_128
        ),
        Account(
            id = 10L,
            uid = 7L,
            firstName = "Sebastián",
            lastName = "González",
            email = "sandraadams@gmail.com",
            altEmail = "sandraadams@textera.com",
            avatar = R.drawable.a4_student_128
        ),
        Account(
            id = 11L,
            uid = 8L,
            firstName = "Trevor",
            lastName = "Hansen",
            email = "trevorhandsen@gmail.com",
            altEmail = "trevorhandsen@express.com",
            avatar = R.drawable.a24_pikachu
        ),
        Account(
            id = 12L,
            uid = 9L,
            firstName = "Sean",
            lastName = "Holt",
            email = "sss",
            altEmail = "sholt@art.com",
            avatar = R.drawable.a27_sleepy1
        ),
        Account(
            id = 13L,
            uid = 10L,
            firstName = "Frank",
            lastName = "Hawkins",
            email = "fhawkank@gmail.com",
            altEmail = "fhawkank@thisisme.com",
            avatar = R.drawable.a29_zombie1
        )
    )

    /**
     * Get the current user's default account.
     */
    fun getDefaultUserAccount() = allUserAccounts.first()

    /**
     * Whether or not the given [Account.id] uid is an account owned by the current user.
     */
    fun isUserAccount(uid: Long): Boolean = allUserAccounts.any { it.uid == uid }

    /**
     * Get the contact of the current user with the given [accountId].
     */
    fun getContactAccountByUid(accountId: Long): Account {
        return allUserContactAccounts.first { it.id == accountId }
    }
}
