package org.example;

import org.example.i18n.I18n;
import org.example.i18n.I18nMessages;
import org.example.minesweeper.Minesweeper;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

    final private static class BoardSize {
        public int xSize;
        public int ySize;

        public BoardSize(int xSize, int ySize) {
            this.xSize = xSize;
            this.ySize = ySize;
        }
    }

    final private static class PlayInstructions {
        public int i;
        public int j;
        public boolean isToMark;
    }

    private static I18n i18n;
    public static void main(String[] args) {
        i18n = new I18n(args);

        Scanner scanner = new Scanner(System.in);
        boolean inGame = true;

        while (inGame) {
            System.out.println(i18n.get(I18nMessages.inputBoardSize()));
            BoardSize boardSize = readBoardSize();

            System.out.println(i18n.get(I18nMessages.inputBombsPercentage()));
            int bombsPercentage = readBombsPercentage();

            Minesweeper minesweeper = Minesweeper.withRandomBombs(boardSize.xSize, boardSize.ySize, bombsPercentage);

            boolean foundBomb = false;
            boolean isAllBombsOpen = false;

            while (!foundBomb && !isAllBombsOpen) {
                System.out.println(minesweeper.toStringWithPositions());

                System.out.println(i18n.get(I18nMessages.inputPlay()));
                PlayInstructions playInstructions = readPlay();

                if (playInstructions.isToMark) {
                    minesweeper.mark(playInstructions.i-1, playInstructions.j-1);
                }
                else {
                    foundBomb = minesweeper.openAndGetIfCellIsBomb(playInstructions.i-1, playInstructions.j-1);
                    isAllBombsOpen = minesweeper.foundAllBombs();
                }
            }

            if (foundBomb) {
                System.out.println(i18n.get(I18nMessages.alertExploded()));
            }
            else {
                System.out.println(i18n.get(I18nMessages.alertWin()));
            }

            System.out.print(i18n.get(I18nMessages.inputExitOrPlayAgain()));
            inGame = !scanner.nextLine().equalsIgnoreCase("S");
        }
    }

    private static BoardSize readBoardSize() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            String[] spitedInput = input.split("x");

            if (spitedInput.length != 2) {
                System.out.println(i18n.get(I18nMessages.inputErrorBoardSize()));
                continue;
            }

            try {
                int xSize = Integer.parseInt(spitedInput[0]);
                int ySize = Integer.parseInt(spitedInput[1]);

                return new BoardSize(xSize, ySize);
            }
            catch (NumberFormatException exception) {
                System.out.println(i18n.get(I18nMessages.inputErrorBoardSizeInvalidSizes()));
            }
        }
    }

    private static int readBombsPercentage() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                int percentage = Integer.parseInt(scanner.nextLine());

                if (percentage < 1 || percentage > 100) {
                    System.out.println(i18n.get(I18nMessages.inputErrorBombsPercentageOutOfRange()));
                    continue;
                }

                return percentage;
            }
            catch (NumberFormatException exception) {
                System.out.println(i18n.get(I18nMessages.inputErrorBombsPercentageInvalid()));
            }
        }
    }

    private static PlayInstructions readPlay() {
        Scanner scanner = new Scanner(System.in);
        PlayInstructions playInstructions = new PlayInstructions();

        while (true) {
            String input = scanner.nextLine();
            String[] spitedInput = input.split(" ");

            if (spitedInput.length != 2 && spitedInput.length != 3) {
                System.out.println(i18n.get(I18nMessages.inputErrorPlayInvalidValues()));
                continue;
            }

            if (spitedInput.length == 3) {
                if (spitedInput[2].equalsIgnoreCase("M")) {
                    playInstructions.isToMark = true;
                }
                else {
                    System.out.println(i18n.get(I18nMessages.inputErrorPlayInvalidMark()));
                    continue;
                }
            }
            else {
                playInstructions.isToMark = false;
            }

            try {
                playInstructions.i = Integer.parseInt(spitedInput[0]);
                playInstructions.j = Integer.parseInt(spitedInput[1]);

                return  playInstructions;
            }
            catch (NumberFormatException exception) {
                System.out.println(i18n.get(I18nMessages.inputErrorPlayInvalidInput()));
            }
        }
    }
}