package com.rashmi.birthdayreminder.domain.usecase

import com.rashmi.birthdayreminder.data.db.BirthdayEntity
import com.rashmi.birthdayreminder.data.repository.IBirthdayRepo
import com.rashmi.birthdayreminder.domain.model.BirthdayData
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class BirthdayUseCaseTest {

    private lateinit var repo: IBirthdayRepo
    private lateinit var scheduleReminder: ScheduleBirthdayReminderUseCase
    private lateinit var cancelReminder: CancelBirthdayReminderUseCase
    private lateinit var useCase: BirthdayUseCase

    @Before
    fun setup() {
        repo = mockk()
        scheduleReminder = mockk(relaxed = true)
        cancelReminder = mockk(relaxed = true)
        useCase = BirthdayUseCase(repo, scheduleReminder, cancelReminder)
    }

    @Test
    fun `birthdays are sorted by upcoming date`() = runTest {
        val birthdays = listOf(
            BirthdayEntity(1, "Amit", LocalDate.of(1998, 1, 5)),
            BirthdayEntity(2, "Rahul", LocalDate.of(1998, 6, 12)),
            BirthdayEntity(3, "Sara", LocalDate.of(1998, 6, 11))
        )
        every { repo.getBirthdays() } returns flowOf(birthdays)
        val result = useCase.getSortedBirthdayList().first()
        assertEquals(
            listOf("Sara", "Rahul", "Amit"),
            result.map { it.name }
        )
    }

    @Test
    fun `addBirthday inserts birthday and schedules reminder`() = runTest {
        val birthday = BirthdayData(
            id = 0,
            name = "Rahul",
            date = LocalDate.of(1998, 6, 12)
        )

        coEvery { repo.insertBirthday(any(), any()) } returns 2
        useCase.addBirthday(birthday)
        coVerify { repo.insertBirthday(any(), any()) }
        verify {
            birthday.date?.let { scheduleReminder(2, "Rahul", it) }
        }
    }

    @Test
    fun `reminder is not scheduled if insert fails`() = runTest {
        val birthday = BirthdayData(
            id = 0,
            name = "Rahul",
            date = LocalDate.of(1998, 6, 12)
        )
        coEvery { repo.insertBirthday(any(), any()) } returns -1
        useCase.addBirthday(birthday)
        verify(exactly = 0) {
            scheduleReminder(any(), any(), any())
        }
    }

    @Test
    fun `deleteBirthday removes entry and cancels reminder`() = runTest {
        coEvery { repo.deleteBirthday(5) } just Runs
        useCase.deleteBirthday(5)
        coVerify { repo.deleteBirthday(5) }
        verify { cancelReminder(5) }
    }
}