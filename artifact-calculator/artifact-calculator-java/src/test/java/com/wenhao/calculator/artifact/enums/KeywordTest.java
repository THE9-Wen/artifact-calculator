package com.wenhao.calculator.artifact.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeywordTest {

    @Test
    void test_getAccurate() {
        assertEquals(0.09328f, Keyword.ATK.getAccurate(0.093f));
        assertEquals(33.065f, Keyword.ATK_ABS.getAccurate(33f));
        assertEquals(0.12432f, Keyword.CRIT_DMG.getAccurate(0.124f));
    }
}
