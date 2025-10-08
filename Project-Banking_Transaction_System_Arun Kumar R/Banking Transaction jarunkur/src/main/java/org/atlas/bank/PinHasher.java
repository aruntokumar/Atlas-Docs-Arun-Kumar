package org.atlas.bank;

import java.security.MessageDigest;
import org.atlas.bank.PinHasher;

public class PinHasher {

    // Hash a 4-digit PIN using SHA-256
    public static String hashPin(String pin) {
        if (pin == null || pin.length() != 4 || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("PIN must be exactly 4 digits");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(pin.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing PIN", e);
        }
    }

    // Verify a PIN against a stored hash
    public static boolean verifyPin(String enteredPin, String storedHash) {
        return hashPin(enteredPin).equals(storedHash);
    }
}
