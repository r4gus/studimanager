package config.test;

import config.Config;
import config.Language;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {
    private static final String PATH = "config/testConfig.json";
    Config config;

    @BeforeEach
    void setUp() {
        config = new Config("files/timetableTest.json", Language.GERMAN);
    }

    @AfterEach
    void tearDown() {
        File f = new File(PATH);
        f.delete();
    }

    @Test
    void loadJson() {
        try {
            config.storeJson(PATH);
        } catch (IOException exc) {
            fail();
        }

        config = null;

        /* -------- EXPECTED SUCCESS ----------------------*/
        try {
            config = Config.loadJson(PATH);
        } catch (IOException exc) {
            exc.printStackTrace();
            fail();
        }

        assertEquals("files/timetableTest.json", config.getTimetablePath());
        assertEquals(Language.GERMAN, config.getLanguage());
    }

    @Test
    void loadJson2() {
        config.setLanguage(null);

        try {
            config.storeJson(PATH);
        } catch (IOException exc) {
            fail();
        }

        config = null;

        /* -------- EXPECTED SUCCESS ----------------------*/
        try {
            config = Config.loadJson(PATH);
        } catch (IOException exc) {
            exc.printStackTrace();
            fail();
        }

        assertEquals("files/timetableTest.json", config.getTimetablePath());
        assertEquals(Language.ENGLISH, config.getLanguage());
    }
}