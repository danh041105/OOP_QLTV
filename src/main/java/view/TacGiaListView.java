package view;

import controller.TacGiaController;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.List;
import javax.swing.*;
import model.TacGia;
import qltv.giaodiensv;
import qltv.userDAO;

public class TacGiaListView extends JFrame {

    private JPanel gridPanel;
    private JScrollPane scrollPane;

    private TacGiaController controller;

    private static final Color main_color = new Color(236, 240, 241);
    private static final Color title_color = new Color(52, 73, 94);
    private static final Color card_color = new Color(44, 62, 80);
    private static final Color border_color = new Color(220, 220, 220);

    public TacGiaListView(String title) {
        super(title);
    }

    public void doShow() {
        addControl();
        controller = new TacGiaController(this);
        controller.initView();
        pack();
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
    }

    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(main_color);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("TÁC GIẢ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(title_color);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(0, 3, 5, 5));
        gridPanel.setBackground(main_color);

        scrollPane = new JScrollPane(gridPanel);
        scrollPane.setPreferredSize(new Dimension(850, 600));
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    public JPanel createCard(TacGia tacGia) {
        JPanel cardPanel = new JPanel(new BorderLayout(0, 5));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setPreferredSize(new Dimension(160, 250));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border_color, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel topPanel = new JPanel(new BorderLayout(0, 5));
        topPanel.setBackground(Color.WHITE);

        JLabel lblTen = new JLabel(tacGia.getTenTg(), SwingConstants.CENTER);
        lblTen.setPreferredSize(new Dimension(0, 30));
        lblTen.setFont(new Font("Arial", Font.BOLD, 14));
        lblTen.setForeground(card_color);
        topPanel.add(lblTen, BorderLayout.NORTH);

        JLabel lblImg = loadImage(tacGia);
        topPanel.add(lblImg, BorderLayout.CENTER);

        cardPanel.add(topPanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        infoPanel.setBackground(Color.WHITE);

        JLabel lblNgaySinh = new JLabel("Ngày sinh: " + tacGia.getNgaySinh(), SwingConstants.CENTER);
        lblNgaySinh.setFont(new Font("Arial", Font.PLAIN, 13));
        lblNgaySinh.setPreferredSize(new Dimension(0, 25));

        JLabel lblQue = new JLabel("Quê: " + tacGia.getQue(), SwingConstants.CENTER);
        lblQue.setFont(new Font("Arial", Font.PLAIN, 13));

        infoPanel.add(lblNgaySinh);
        infoPanel.add(lblQue);

        cardPanel.add(infoPanel, BorderLayout.SOUTH);

        cardPanel.addMouseListener(new MouseAdapter() {

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

    private JLabel loadImage(TacGia tacGia) {
        try {
            URL imgURL = getClass().getResource("/images/" + tacGia.getHinh());

            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(120, 150, Image.SCALE_SMOOTH);
                return new JLabel(new ImageIcon(img), SwingConstants.CENTER);
            } else {
                System.out.println("Không tìm thấy file ảnh");
            }
        } catch (Exception e) {
            System.err.println("Lỗi load ảnh tác giả: " + e.getMessage());
        }

        return createPlaceholderImage();
    }

    private JLabel createPlaceholderImage() {
        JLabel lbl = new JLabel("No Image", SwingConstants.CENTER);
        lbl.setPreferredSize(new Dimension(120, 150));
        lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return lbl;
    }

    public void displayTacGia(List<TacGia> danhSach) {
        gridPanel.removeAll();

        for (TacGia tacGia : danhSach) {
            gridPanel.add(createCard(tacGia));
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
