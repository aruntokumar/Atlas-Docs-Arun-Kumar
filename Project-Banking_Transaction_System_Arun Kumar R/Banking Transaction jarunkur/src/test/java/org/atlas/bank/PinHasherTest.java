package org.atlas.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.atlas.bank.PinHasher;


class PinHasherTest {

    @Test
    void testHashPinValid() {
        String pin = "1234";
        String hash1 = PinHasher.hashPin(pin);
        String hash2 = PinHasher.hashPin(pin);
        assertNotNull(hash1);
        assertEquals(hash1, hash2, "Hashing same PIN should return same hash");
    }

    @Test
    void testHashPinInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> PinHasher.hashPin("12"));
        assertThrows(IllegalArgumentException.class, () -> PinHasher.hashPin("abcd"));
        assertThrows(IllegalArgumentException.class, () -> PinHasher.hashPin(null));
    }

    @Test
    void testVerifyPin() {
        String pin = "4321";
        String hash = PinHasher.hashPin(pin);
        assertTrue(PinHasher.verifyPin("4321", hash));
        assertFalse(PinHasher.verifyPin("1234", hash));
    }
}
