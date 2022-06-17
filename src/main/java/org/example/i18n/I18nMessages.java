package org.example.i18n;

public class I18nMessages {
    String bundleReference;

    private I18nMessages(String bundleReference) {
        this.bundleReference = bundleReference;
    }

    public static I18nMessages inputBoardSize() {
        return new I18nMessages("input_board_size");
    }

    public static I18nMessages inputBombsPercentage() {
        return new I18nMessages("input_bombs_percentage");
    }

    public static I18nMessages inputPlay() {
        return new I18nMessages("input_play");
    }

    public static I18nMessages alertExploded() {
        return new I18nMessages("alert_exploded");
    }

    public static I18nMessages alertWin() {
        return new I18nMessages("alert_win");
    }

    public static I18nMessages inputExitOrPlayAgain() {
        return new I18nMessages("input_exit_or_play_again");
    }

    public static I18nMessages inputErrorBoardSize() {
        return new I18nMessages("input_error_board_size");
    }

    public static I18nMessages inputErrorBoardSizeInvalidSizes() {
        return new I18nMessages("input_error_board_size_invalid_sizes");
    }

    public static I18nMessages inputErrorBombsPercentageOutOfRange() {
        return new I18nMessages("input_error_bombs_percentage_out_of_range");
    }

    public static I18nMessages inputErrorBombsPercentageInvalid() {
        return new I18nMessages("input_error_bombs_percentage_invalid");
    }

    public static I18nMessages inputErrorPlayInvalidValues() {
        return new I18nMessages("input_error_play_invalid_values");
    }

    public static I18nMessages inputErrorPlayInvalidMark() {
        return new I18nMessages("input_error_play_invalid_mark");
    }

    public static I18nMessages inputErrorPlayInvalidInput() {
        return new I18nMessages("input_error_play_invalid_input");
    }
}
