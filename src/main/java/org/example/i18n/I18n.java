package org.example.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private final ResourceBundle resourceBundle;

    public I18n(String[] appArgs) {
        String language, country;

        if (appArgs.length != 2) {
            language = "pt";
            country = "BR";
        }
        else {
            language = appArgs[0];
            country = appArgs[1];
        }

        Locale locale = new Locale(language, country);
        this.resourceBundle = ResourceBundle.getBundle("MessagesBundle", locale);
    }

    public String get(I18nMessages message) {
        return resourceBundle.getString(message.bundleReference);
    }
}
