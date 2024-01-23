package com.contoh.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiniSudoku extends JFrame implements ActionListener {

    private static final int GRID_SIZE = 3;
    private static final int NUM_RANDOM_NUMBERS = 2;

    private JTextField[][] fields;
    private JButton checkButton;
    private JButton newGameButton;
    private JButton pauseButton;
    private int[][] board;
    private int[][] originalNumbers;
    private boolean isPaused;
    private Timer timer;
    private int seconds;
    private int minutes;
    private JLabel timerLabel;

    public MiniSudoku() {
        setTitle("Enhance TicTacToe (Mini Sudoku 3x3) by Hasan-2200018068");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 400));

        fields = new JTextField[GRID_SIZE][GRID_SIZE];
        JPanel panel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));

        // Create Sudoku grid
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                fields[i][j] = new JTextField(4);
                fields[i][j].addActionListener(this); // Add action listener for each text field
                panel.add(fields[i][j]);
            }
        }

        // Check button
        checkButton = new JButton("Check");
        checkButton.addActionListener(this);

        // New Game button
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);

        // Pause button
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(checkButton);
        buttonPanel.add(newGameButton);
        buttonPanel.add(pauseButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        timerLabel = new JLabel("Timer: 00:00");
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        add(timerLabel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        initializeTimer();
        initializeGame();
    }

    private void initializeTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    seconds++;
                    if (seconds == 60) {
                        seconds = 0;
                        minutes++;
                    }
                    timerLabel.setText("Timer: " + String.format("%02d:%02d", minutes, seconds));
                }
            }
        });
    }

    public void initializeGame() {
        do {
            board = new int[GRID_SIZE][GRID_SIZE];
            originalNumbers = new int[GRID_SIZE][GRID_SIZE];

            java.util.Random random = new java.util.Random();

            for (int k = 0; k < NUM_RANDOM_NUMBERS; k++) {
                int i = random.nextInt(GRID_SIZE);
                int j = random.nextInt(GRID_SIZE);
                originalNumbers[i][j] = random.nextInt(3) + 1;
                board[i][j] = originalNumbers[i][j];
            }
        } while (!isSudokuComplete());

        updateUIWithSudoku();

        seconds = 0;
        minutes = 0;
        timer.start();
    }
    // Metode getter untuk mendapatkan nilai dari variabel private
    public int[][] getBoard() {
        return board;
    }

    public int[][] getOriginalNumbers() {
        return originalNumbers;
    }

    private void updateUIWithSudoku() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                fields[i][j].setText(originalNumbers[i][j] != 0 ? String.valueOf(originalNumbers[i][j]) : "");
                fields[i][j].setEditable(originalNumbers[i][j] == 0);

                // Set a larger font size for the displayed numbers
                Font font = new Font("Arial", originalNumbers[i][j] != 0 ? Font.BOLD : Font.PLAIN, 20);
                fields[i][j].setFont(font);
            }
        }
    }


    private void checkEndGame() {
        // Check if the game is completed
        if (isSudokuComplete()) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Congratulations! You have completed the Mini Sudoku puzzle!");
        }
    }



    private boolean isUniqueRows() {
        for (int i = 0; i < GRID_SIZE; i++) {
            boolean[] used = new boolean[GRID_SIZE + 1]; // Mark used numbers (1 to GRID_SIZE)

            for (int j = 0; j < GRID_SIZE; j++) {
                int num = fields[i][j].getText().isEmpty() ? 0 : Integer.parseInt(fields[i][j].getText());
                if (num != 0 && used[num]) {
                    return false; // Number is repeated in the row
                }
                used[num] = true;
            }
        }
        return true; // All rows have unique numbers
    }


    private boolean isUniqueColumns() {
        for (int j = 0; j < GRID_SIZE; j++) {
            boolean[] used = new boolean[GRID_SIZE + 1]; // Mark used numbers (1 to GRID_SIZE)

            for (int i = 0; i < GRID_SIZE; i++) {
                int num = fields[i][j].getText().isEmpty() ? 0 : Integer.parseInt(fields[i][j].getText());
                if (num != 0 && used[num]) {
                    return false; // Number is repeated in the column
                }
                used[num] = true;
            }
        }
        return true; // All columns have unique numbers
    }


    private boolean isUniqueGrids() {
        int subgridSize = (int) Math.sqrt(GRID_SIZE);

        for (int startRow = 0; startRow < GRID_SIZE; startRow += subgridSize) {
            for (int startCol = 0; startCol < GRID_SIZE; startCol += subgridSize) {
                boolean[] used = new boolean[GRID_SIZE + 1]; // Mark used numbers (1 to GRID_SIZE)

                for (int i = startRow; i < startRow + subgridSize; i++) {
                    for (int j = startCol; j < startCol + subgridSize; j++) {
                        int num = fields[i][j].getText().isEmpty() ? 0 : Integer.parseInt(fields[i][j].getText());
                        if (num != 0 && used[num]) {
                            return false; // Number is repeated in the subgrid
                        }
                        used[num] = true;
                    }
                }
            }
        }
        return true; // All subgrids have unique numbers
    }

    private boolean isSudokuComplete() {
        return isUniqueRows() && isUniqueColumns() && isUniqueGrids() && isInputValid();
    }

    private boolean isInputValid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                String input = fields[i][j].getText().trim();
                if (!input.isEmpty()) {
                    try {
                        int num = Integer.parseInt(input);
                        if (num < 1 || num > 3) {
                            JOptionPane.showMessageDialog(this, "Invalid input! Please enter numbers between 1 and 3.");
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid numbers.");
                        return false;
                    }
                }
            }
        }
        return true;
    }



    private void resetTimer() {
        seconds = 0;
        minutes = 0;
        timerLabel.setText("Timer: " + String.format("%02d:%02d", minutes, seconds));
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == newGameButton) {
                timer.stop();
                initializeGame();
                resetTimer();
                timer.start();
                isPaused = false;
            } else if (e.getSource() == pauseButton) {
                isPaused = true;
                timer.stop();
                int option = JOptionPane.showConfirmDialog(this,
                        "Game is paused. Do you want to continue?", "Pause",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    isPaused = false;
                    timer.start();
                } else {
                    resetTimer();
                }
            } else if (e.getSource() == checkButton) {
                if (isUniqueRows() && isUniqueColumns()) {
                    timer.stop();
                    JOptionPane.showMessageDialog(this, "Congratulations You Win!");
                    int option = JOptionPane.showConfirmDialog(this,
                            "Do you want play again?", "Win!",
                            JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        timer.stop();
                        initializeGame();
                        resetTimer();
                        timer.start();
                        isPaused = false;
                    } else {
                        resetTimer();
                        System.exit(0);
                    }
                } else {
                    if (!isInputValid()) {
                        JOptionPane.showMessageDialog(this, "Invalid input! Please enter numbers between 1 and 3.");
                    } else {
                        JOptionPane.showMessageDialog(this, "There are duplicate numbers in rows or columns. Keep trying!");
                    }
                }
            } else {
                checkEndGame();
            }
        } catch (NumberFormatException ex) {
            // Tangani NumberFormatException jika konversi gagal
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid numbers.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniSudoku());
    }
}

