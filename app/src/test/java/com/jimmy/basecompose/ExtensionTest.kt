package com.jimmy.basecompose

import com.jimmy.basecompose.core.util.formatUtcToLocalDateTime
import com.jimmy.basecompose.core.util.getFirstSentence
import com.jimmy.basecompose.core.util.isValidEmail
import com.jimmy.basecompose.core.util.isValidPassword
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import java.util.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals

class ExtensionTest {

    @Test
    fun `formatToLocalDateTime should convert UTC to local format`() {
        val input = "2025-04-22T08:28:00Z"

        // Expected output depends on system timezone.
        // Let's simulate Asia/Jakarta (UTC+7) for predictable test
        val expected = "22 April 2025, 15:28"

        val originalZone = TimeZone.getDefault()
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"))
            val result = input.formatUtcToLocalDateTime()
            assertEquals(expected, result)
        } finally {
            TimeZone.setDefault(originalZone)
        }
    }


    @Test
    fun `getFirstSentence should return sentence until first period`() {
        val summary = "NASA’s budget dropped. It shocked the world. Many were disappointed."
        val expected = "NASA’s budget dropped."
        val result = summary.getFirstSentence()
        assertEquals(expected, result)
    }

    @Test
    fun `getFirstSentence should return full text if no period`() {
        val summary = "No punctuation here"
        val expected = "No punctuation here"
        val result = summary.getFirstSentence()
        assertEquals(expected, result)
    }

    @Test
    fun testValidEmails() {
        assertTrue("user@example.com".isValidEmail())
        assertTrue("john.doe@mail.co".isValidEmail())
    }

    @Test
    fun testInvalidEmails() {
        assertFalse("userexample.com".isValidEmail())
        assertFalse("user@.com".isValidEmail())
        assertFalse("user@com".isValidEmail())
    }

    @Test
    fun testValidPasswords() {
        assertTrue("P@ssw0rd".isValidPassword())
        assertTrue("Abc#1234".isValidPassword())
    }

    @Test
    fun testInvalidPasswords() {
        assertFalse("password".isValidPassword()) // no digit/symbol
        assertFalse("12345678".isValidPassword()) // no letter/symbol
        assertFalse("Passw0rd".isValidPassword()) // no symbol
        assertFalse("P@1".isValidPassword())       // too short
        assertFalse("p@ssw0rd".isValidPassword()) // no uppercase
    }
}