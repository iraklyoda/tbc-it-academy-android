package com.iraklyoda.userssocialapp.domain.use_case.auth

import com.iraklyoda.userssocialapp.data.repository.FakeLogInRepository
import com.iraklyoda.userssocialapp.domain.common.Resource
import com.iraklyoda.userssocialapp.domain.model.LoginSession
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LogInUserUseCaseImplTest {
    private lateinit var logInUserUseCaseImpl: LogInUserUseCase
    private lateinit var fakeLogInRepository: FakeLogInRepository
    private lateinit var fakeSavePreferenceValueUseCase: FakeSavePreferenceValueUseCase

    // Run Setup Before Test Case
    @Before
    fun setUp() {
        fakeLogInRepository = FakeLogInRepository()
        fakeSavePreferenceValueUseCase = FakeSavePreferenceValueUseCase()

        logInUserUseCaseImpl = LogInUserUseCaseImpl(
            loginRepository = fakeLogInRepository,
            savePreferenceValueUseCase = fakeSavePreferenceValueUseCase
        )
    }

    @Test
    fun `Successful login returns user session with token`() {
        runBlocking {
            val result = mutableListOf<Resource<LoginSession>>()

            val flow = logInUserUseCaseImpl(
                email = "test@gmail.com",
                password = "***********",
                rememberMe = true
            )

            flow.collect {
                result.add(it)
            }
            // Assert success
            assert(result.first() is Resource.Success)

            val session = (result.first() as Resource.Success).data
            assert(session.token == FakeLogInRepository.TOKEN_KEY)
        }
    }
}