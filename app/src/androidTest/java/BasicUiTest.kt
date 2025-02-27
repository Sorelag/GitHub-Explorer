import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubexplorer.data.DETAILS
import com.example.githubexplorer.data.Repository
import com.example.githubexplorer.screens.RepositoryList
import com.example.githubexplorer.viewmodels.MainViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class BasicUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigationAndViewModel() {

        val items = listOf(
            Repository(
                name = "name1",
                description = "description",
                stargazerCount = 2,
                primaryLanguage = "Kotlin",
                avatarUrl = "url",
                owner = "owner"
            )
        )

        val mockNavController = mock(NavHostController::class.java)

        val mockViewModel = mock(MainViewModel::class.java)

        composeTestRule.setContent {
            RepositoryList(
                repositories = items,
                isLoading = true,
                viewModel = mockViewModel,
                navController = mockNavController,
                onRefresh = { mockNavController.navigate(DETAILS) }
            )
        }

        composeTestRule.onNodeWithText("name1").assertExists()

        composeTestRule.onNodeWithText("name1").performClick()

        verify(mockNavController).navigate(DETAILS)
    }
}