package view;

import dao.SachDAO;
import java.util.List;
import java.awt.*;
import java.net.URL;
import javax.swing.*;
import model.Sach;
import model.TacGia;

/**
 * @author ADMIN
 */
public class TacGiaDetailView extends JFrame {

    private final TacGia tacGia;

    public TacGiaDetailView(TacGia tacGia) {
        super("Chi tiết tác giả");
        this.tacGia = tacGia;
    }

    public void doShow() {
        addControl();
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addControl() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Tiêu đề
        JLabel lblTitle = new JLabel(tacGia.getTenTg());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblTitle);

        mainPanel.add(Box.createVerticalStrut(10));

        // Thông tin + ảnh
        JPanel infoPanel = new JPanel(new BorderLayout(15, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Ảnh
        JLabel lblImage = loadImage();
        infoPanel.add(lblImage, BorderLayout.WEST);

        // chữ
        JPanel textInfo = new JPanel();
        textInfo.setLayout(new BoxLayout(textInfo, BoxLayout.Y_AXIS));
        textInfo.setBackground(Color.WHITE);

        textInfo.add(createInfoLabel("Họ và tên: ", tacGia.getTenTg()));
        textInfo.add(createInfoLabel("Ngày sinh: ", tacGia.getNgaySinh()));
        textInfo.add(createInfoLabel("Giới tính: ", tacGia.getGioiTinh()));
        textInfo.add(createInfoLabel("Quê quán: ", tacGia.getQue()));

        infoPanel.add(textInfo, BorderLayout.CENTER);
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        JPanel scrollPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        scrollPanel.setBackground(Color.WHITE);
        scrollPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel tacPhamPanel = new JPanel(new BorderLayout());
        tacPhamPanel.setBackground(Color.WHITE);
        tacPhamPanel.add(createTacPham(), BorderLayout.CENTER);

        JPanel tieuSuPanel = new JPanel(new BorderLayout());
        tieuSuPanel.setBackground(Color.WHITE);
        tieuSuPanel.add(createTieuSu(), BorderLayout.CENTER);

        scrollPanel.add(tieuSuPanel);
        scrollPanel.add(tacPhamPanel);
        mainPanel.add(scrollPanel);

        JScrollPane scrollMain = new JScrollPane(mainPanel);
        scrollMain.setBorder(null);
        scrollMain.setPreferredSize(new Dimension(800, 500));
        add(scrollMain);

    }

    private JScrollPane createTieuSu() {
        JTextArea txt = new JTextArea(tacGia.getTieuSu());
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.setEditable(false);
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scroll = new JScrollPane(txt);
        scroll.setBorder(BorderFactory.createTitledBorder("Tiểu sử"));
        scroll.setPreferredSize(new Dimension(400, 150));
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        return scroll;
    }

    private JScrollPane createTacPham() {
        SachDAO dao = new SachDAO();
        List<Sach> dsSach = dao.getSachByTacGia(tacGia.getMaTg());

        DefaultListModel<String> model = new DefaultListModel<>();

        if (dsSach.isEmpty()) {
            model.addElement("Chưa có tác phẩm");
        } else {
            for (Sach s : dsSach) {
                model.addElement(s.getTenSach());
            }
        }

        JList<String> listTacPham = new JList<>(model);
        listTacPham.setFont(new Font("Arial", Font.PLAIN, 13));
        listTacPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(listTacPham);
        scroll.setBorder(BorderFactory.createTitledBorder("Tác phẩm"));
        scroll.setPreferredSize(new Dimension(400, 150));

        return scroll;
    }

    private JPanel createInfoLabel(String title, String value) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3));
        panel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 13));

        JLabel lblValue = new JLabel(value != null ? value : "N/A");
        lblValue.setFont(new Font("Arial", Font.PLAIN, 13));

        panel.add(lblTitle);
        panel.add(lblValue);

        return panel;
    }

    private JLabel loadImage() {
        try {
            String path = "/images/" + tacGia.getHinh();
            URL imgURL = getClass().getResource(path);

            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(200, 280, Image.SCALE_FAST);

                return new JLabel(new ImageIcon(img));
            } else {
                System.out.println("Không tìm thấy file: " + path);
            }
        } catch (Exception e) {
            System.out.println("Lỗi xử lý ảnh: " + e.getMessage());
        }

        JLabel lbl = new JLabel("No Image", SwingConstants.CENTER);
        lbl.setPreferredSize(new Dimension(200, 280));
        lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        return lbl;
    }

}
