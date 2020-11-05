import com.main.DatabaseReader
import org.junit.jupiter.api.Test

class DatabaseReaderTest {

    //MasterPassword123!?

    @Test
    fun test() {
        val databaseName = "BankingDatabase.kdbx"
        val stream = javaClass.getResourceAsStream(databaseName)


        val passwordStore = DatabaseReader(
                stream,
                "MasterPassword123!?"
        )

        val credentials = passwordStore.getCredentials()
        println(credentials)


    }
}
