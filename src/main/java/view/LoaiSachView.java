package view;

import controller.LoaiSachController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import model.LoaiSach;
import qltv.giaodiensv;
import qltv.userDAO;

/**
 * @author ADMIN
 */
public class LoaiSachView extends JFrame {

    private JPanel mainPanel, gridPanel;
    private JScrollPane scrollPane;

    private LoaiSachController controller;

    private static final Color main_color = new Color(236, 240, 241);
    private static final Color title_color = new Color(52, 73, 94);
    private static final Color border_color = new Color(189, 195, 199);

    public LoaiSachView(String title) {
        super(title);
        controller = new LoaiSachController(this);
    }

    public void doShow() {
        addControl();
        controller.loadData();
        
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                userDAO dao = new userDAO();
                new giaodiensv(dao.getMSV_isLogin()).setVisible(true);
            }
        });
        setVisible(true);
    }
    private void addControl() {
        createMainPanel();
        add(mainPanel);
    }

    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(main_color);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("THỂ LOẠI", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(title_color);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setBackground(main_color);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }


    public JPanel createCard(LoaiSach loaiSach) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border_color, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        cardPanel.setPreferredSize(new Dimension(200, 120));
        cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblTen = new JLabel(loaiSach.getTenLoaiSach(), SwingConstants.CENTER);
        lblTen.setFont(new Font("Arial", Font.BOLD, 18));
        lblTen.setForeground(title_color);
        lblTen.setHorizontalAlignment(SwingConstants.CENTER);
        lblTen.setVerticalAlignment(SwingConstants.CENTER);

        cardPanel.add(lblTen, BorderLayout.CENTER);

        cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.viewBooksByCategory(loaiSach);
                dispose();
            }
            //Hiệu ứng
            @Override
            public void mouseEntered(MouseEvent e) {
                cardPanel.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cardPanel.setBackground(Color.WHITE);
            }
        });

        return cardPanel;
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }
}