package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.io.File;

public class Interface extends JPanel {
    private JTable table;
    private JFileChooser fileChooser;
    private JTextField money;
    private List<TabletInfo> tabletsInfo;

    private Interface() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        table = getTable();
        add(new JScrollPane(table));
        add(getFileSelectBtn());
        add(new JLabel("Введите количество денег"));
        money = new JTextField();
        add(money);
        add(getPickBestTabletBtn());
        fileChooser = new JFileChooser();
    }

    private JTable getTable() {
        String[] columnNames = {"Название модели",
                "Объем памяти",
                "Рейтинг",
                "Стоимость"};
        JTable table = new JTable(new DefaultTableModel(columnNames, 0));
        table.setFillsViewportHeight(true);
        return table;
    }

    private void fillTable(File file) {
        try {
            tabletsInfo = FileParser.readFileAndValidateData(file);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (TabletInfo info : tabletsInfo) {
                model.addRow(new String[]{info.getName(),
                        String.valueOf(info.getMemorySize()),
                        String.valueOf(info.getRating()),
                        String.valueOf(info.getPrice())});
            }
        } catch (FileParserException e) {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }

    private JButton getFileSelectBtn() {
        JButton button = new JButton();
        button.setText("Выбрать файл с данными");
        button.addActionListener(e -> {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                fillTable(file);
            }
        });
        return button;
    }

    private JButton getPickBestTabletBtn() {
        JButton button = new JButton();
        button.setText("Выбрать лучший для вас планшет");
        button.addActionListener(e -> {
            float moneyF;
            try {
                moneyF = Float.parseFloat(money.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, String.format("%s - невалидный float", money.getText()));
                return;
            }
            TabletInfo tabletInfo = TabletAnalyser.pickBest(tabletsInfo, moneyF);
            if (tabletInfo == null) {
                JOptionPane.showMessageDialog(this, "Нет планшетов за указанную сумму");
            } else {
                JOptionPane.showMessageDialog(this, String.format("Лучше всего подходящий вам планшет:\n%s", tabletInfo));
            }
        });
        return button;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Interface panel = new Interface();
        panel.setOpaque(true);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }
}