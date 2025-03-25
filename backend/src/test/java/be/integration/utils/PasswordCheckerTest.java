package be.integration.utils;

import junit.framework.TestCase;

public class PasswordCheckerTest extends TestCase {

    public void testIsPasswordStrong() {
        assertTrue(PasswordChecker.isPasswordStrong("P4ssword&Str0ng"));
    }

    public void testIsPasswordWeak() {
        assertFalse(PasswordChecker.isPasswordStrong("password"));
    }

    public void testIsUppercaseNeeded() {
        assertFalse(PasswordChecker.isPasswordStrong("p4ssword&str0ng"));
    }

    public void testIsLowercaseNeeded() {
        assertFalse(PasswordChecker.isPasswordStrong("P4ASSWORD&STR0NG"));
    }

    public void testIsNumberNeeded() {
        assertFalse(PasswordChecker.isPasswordStrong("Password&Strong"));
    }

    public void testIsSpecialCharNeeded() {
        assertFalse(PasswordChecker.isPasswordStrong("PasswordStr0ng"));
    }

    public void testIsSizeNeededNeeded() {
        assertFalse(PasswordChecker.isPasswordStrong("PasswordS"));
    }

    public void testCheckPasswordWrongPassword() {
        assertFalse(PasswordChecker.checkPasswordHash("password", "xxx"));
    }

    public void testCheckPasswordRightPassword() {
        String hashed = PasswordHasher.hashPassword("password");
        assertTrue(PasswordChecker.checkPasswordHash("password",hashed ));
    }
}