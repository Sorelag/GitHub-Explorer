import com.example.githubexplorer.data.DetailRepository
import com.example.githubexplorer.data.GitHubRepository
import com.example.githubexplorer.data.Repository
import com.example.githubexplorer.viewmodels.MainViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

// TODO: Fix the test
class ViewModelTest {

    @Mock
    private lateinit var mockGitHubRepository: GitHubRepository
    @Mock
    private lateinit var mockDetailRepository: DetailRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {

        MockitoAnnotations.openMocks(this)

        viewModel = MainViewModel(mockGitHubRepository, mockDetailRepository)
    }

    @Test
    fun fetchData_updatesState() = run {

        val mockData = getRepositories()

        `when`(mockGitHubRepository.getRepositories()).thenReturn(mockData)

        viewModel.getRepositories()

        assertEquals(mockData, viewModel.repositories.value)
    }
}

fun getRepositories(): Flow<List<Repository>> = flow {
    val repositories = listOf(
        Repository(
            name = "Name",
            description = "Description",
            stargazerCount = 1,
            primaryLanguage = "Kotlin",
            avatarUrl = "url",
            owner = "owner"
        )
    )
    emit(repositories)
}
