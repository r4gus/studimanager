package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import logging.MyLogger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 * Class that is used to store information for configuring the application.
 *
 * @author David Sugar
 */
public class Config {

    private static final String PATH = "config/config.json";

    private String timetablePath = "files/timetable.json";

    private Language language = Language.EN;   // used to specify the preferred language

    @JsonCreator
    public Config(@JsonProperty("timetablePath") String timetablePath,
                  @JsonProperty("language") Language language) {
        if(timetablePath    != null) this.timetablePath = timetablePath;
        if(language         != null) this.language      = language;
    }

    public Config() {}

    public String getTimetablePath() {
        return timetablePath;
    }

    public void setTimetablePath(String timetablePath) {
        this.timetablePath = timetablePath;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * Store the configuration data.
     * @throws IOException if the file can't be processed or the give file doesn't exist.
     */
    public void store() throws IOException {
        storeJson(PATH);
    }

    /**
     * Store the config data in a {@code Json} file at the specified {@code path}.
     * @param path Path to store the Json file at.
     * @throws IOException if the file can't be processed or the give file doesn't exist.
     */
    public void storeJson(String path) throws IOException {
        MyLogger.LOGGER.entering(getClass().toString(), "storeJson", path);

        ObjectMapper objectMapper = new ObjectMapper();

        try (FileOutputStream fout = new FileOutputStream(path)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fout, this);
        } catch (JsonProcessingException exc) {
            MyLogger.LOGGER.log(Level.SEVERE, exc.getMessage() + "\nSpecified Path: " + path);
            throw exc;
        } catch (IOException exc) {
            MyLogger.LOGGER.log(Level.WARNING, exc.getMessage() + "\nSpecified Path: " + path);
            throw exc;
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "storeJson");
    }

    /**
     * Load the configuration data.
     * @return Config object
     */
    public static Config load() throws IOException {
        return loadJson(PATH);
    }

    /**
     * Load the specified Json file.
     * @param path Path of the Json file to load.
     * @return
     */
    public static Config loadJson(String path) throws IOException {
        MyLogger.LOGGER.entering("src.config.Config", "loadJson", path);

        ObjectMapper objectMapper = new ObjectMapper();
        Config config = null;

        try (FileInputStream fin = new FileInputStream(path)) {
            config = objectMapper.readValue(fin, Config.class);
        } catch (JsonProcessingException exc) {
            MyLogger.LOGGER.log(Level.SEVERE, exc.getMessage() + "\nSpecified Path: " + path);
            throw exc;
        } catch (IOException exc) {
            MyLogger.LOGGER.log(Level.WARNING, exc.getMessage() + "\nSpecified Path: " + path);
            throw exc;
        }

        MyLogger.LOGGER.exiting("src.config.Config", "loadJson", config);

        return config;
    }
}
