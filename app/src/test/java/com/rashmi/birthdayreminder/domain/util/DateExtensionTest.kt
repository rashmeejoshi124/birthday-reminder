package com.rashmi.birthdayreminder.domain.util

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate

class DateExtensionTest {

    @Test
    fun `nextBirthday returns today when birthday is today`() {
        val today = LocalDate.of(2026, 6, 10)
        val birthday = LocalDate.of(1998, 6, 10)
        val result = birthday.nextBirthday(today)
        assertEquals(LocalDate.of(2026, 6, 10), result)
    }

    @Test
    fun `nextBirthday returns upcoming birthday in same year`() {
        val today = LocalDate.of(2026, 6, 10)
        val birthday = LocalDate.of(1998, 6, 12)
        val result = birthday.nextBirthday(today)
        assertEquals(LocalDate.of(2026, 6, 12), result)
    }

    @Test
    fun `nextBirthday returns next year when birthday already passed`() {
        val today = LocalDate.of(2026, 6, 10)
        val birthday = LocalDate.of(1998, 1, 5)
        val result = birthday.nextBirthday(today)
        assertEquals(LocalDate.of(2027, 1, 5), result)
    }

    @Test
    fun `daysUntilNextBirthday returns 0 when birthday is today`() {
        val today = LocalDate.of(2026, 3, 16)
        val birthday = LocalDate.of(1998, 3, 16)
        val result = birthday.daysUntilNextBirthday(today)
        assertEquals(0, result)
    }

    @Test
    fun `daysUntilNextBirthday returns days until upcoming birthday`() {
        val today = LocalDate.of(2026, 3, 16)
        val birthday = LocalDate.of(1998, 6, 12)
        val result = birthday.daysUntilNextBirthday(today)
        assertEquals(88, result)
    }

    @Test
    fun `birthdayLabel returns today when birthday is today`() {
        val birthday = LocalDate.of(1998, 3, 16)
        val result = birthday.birthdayLabel()
        assertEquals("Today \uD83C\uDF89", result)
    }

    @Test
    fun `birthdayLabel returns days until upcoming birthday`() {
        val birthday = LocalDate.of(1998, 6, 12)
        val result = birthday.birthdayLabel()
        assertEquals("88 days left", result)
    }

}