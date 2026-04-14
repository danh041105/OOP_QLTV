package view;

import dao.SachDAO;
import utils.ThemeUtils;

import java.util.List;
import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
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
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addControl() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ThemeUtils.BG_MAIN);

        // Top bar with author name and breadcrumb
        JPanel topBar = ThemeUtils.createTopBar(tacGia.getTenTg(), "Danh sách tác giả > Chi tiết tác giả");
        mainPanel.add(topBar, BorderLayout.NORTH);

        // Scrollable content
        JPanel scrollContent = new JPanel(new BorderLayout(20, 15));
        scrollContent.setBackground(ThemeUtils.BG_MAIN);
        scrollContent.setBorder(new EmptyBorder(20, 25, 20, 25));

        // Photo + info panel (card style)
        JPanel infoCard = new JPanel(new BorderLayout(20, 10));
        infoCard.setBackground(ThemeUtils.BG_CARD);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        // Author photo
        JLabel lblImage = loadImage();
        JPanel imageWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        imageWrapper.setBackground(ThemeUtils.BG_CARD);
        imageWrapper.add(lblImage);
        infoCard.add(imageWrapper, BorderLayout.WEST);

        // Author info on the right
        JPanel textInfo = new JPanel();
        textInfo.setLayout(new BoxLayout(textInfo, BoxLayout.Y_AXIS));
        textInfo.setBackground(ThemeUtils.BG_CARD);
        textInfo.setBorder(new EmptyBorder(10, 10, 10, 10));

        textInfo.add(createInfoLabel("Họ và tên", tacGia.getTenTg()));
        textInfo.add(Box.createVerticalStrut(8));
        textInfo.add(createInfoLabel("Ngày sinh", tacGia.getNgaySinh()));
        textInfo.add(Box.createVerticalStrut(8));
        textInfo.add(createInfoLabel("Giới tính", tacGia.getGioiTinh()));
        textInfo.add(Box.createVerticalStrut(8));
        textInfo.add(createInfoLabel("Quê quán", tacGia.getQue()));

        infoCard.add(textInfo, BorderLayout.CENTER);
        scrollContent.add(infoCard, BorderLayout.NORTH);

        // Two sections side by side: Tiểu sử and Tác phẩm
        JPanel twoColumnPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        twoColumnPanel.setBackground(ThemeUtils.BG_MAIN);

        // Tiểu sử section
        JPanel tieuSuCard = new JPanel(new BorderLayout());
        tieuSuCard.setBackground(ThemeUtils.BG_CARD);
        tieuSuCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        tieuSuCard.add(createTieuSu(), BorderLayout.CENTER);

        // Tác phẩm section
        JPanel tacPhamCard = new JPanel(new BorderLayout());
        tacPhamCard.setBackground(ThemeUtils.BG_CARD);
        tacPhamCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        tacPhamCard.add(createTacPham(), BorderLayout.CENTER);

        twoColumnPanel.add(tieuSuCard);
        twoColumnPanel.add(tacPhamCard);

        scrollContent.add(twoColumnPanel, BorderLayout.CENTER);

        JScrollPane scrollMain = new JScrollPane(scrollContent);
        scrollMain.setBorder(null);
        scrollMain.setBackground(ThemeUtils.BG_MAIN);
        scrollMain.getViewport().setBackground(ThemeUtils.BG_MAIN);
        scrollMain.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollMain, BorderLayout.CENTER);
    }

    private JScrollPane createTieuSu() {
        JTextArea txt = new JTextArea(tacGia.getTieuSu());
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.setEditable(false);
        txt.setFont(ThemeUtils.FONT_BODY);
        txt.setBackground(ThemeUtils.BG_INPUT);
        txt.setForeground(ThemeUtils.TEXT_PRIMARY);
        txt.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(txt);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
                "  📝 Tiểu sử  ",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                ThemeUtils.FONT_BODY_BOLD,
                ThemeUtils.TEXT_SECONDARY
        ));
        scroll.setPreferredSize(new Dimension(380, 200));
        scroll.getViewport().setBackground(ThemeUtils.BG_CARD);

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
                model.addElement("📖 " + s.getTenSach());
            }
        }

        JList<String> listTacPham = new JList<>(model);
        listTacPham.setFont(ThemeUtils.FONT_BODY);
        listTacPham.setForeground(ThemeUtils.TEXT_PRIMARY);
        listTacPham.setBackground(ThemeUtils.BG_INPUT);
        listTacPham.setSelectionBackground(ThemeUtils.PRIMARY_LIGHT);
        listTacPham.setSelectionForeground(ThemeUtils.PRIMARY_DARK);
        listTacPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listTacPham.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane scroll = new JScrollPane(listTacPham);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
                "  📚 Tác phẩm  ",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                ThemeUtils.FONT_BODY_BOLD,
                ThemeUtils.TEXT_SECONDARY
        ));
        scroll.setPreferredSize(new Dimension(380, 200));
        scroll.getViewport().setBackground(ThemeUtils.BG_CARD);

        return scroll;
    }

    private JPanel createInfoLabel(String title, String value) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        panel.setBackground(ThemeUtils.BG_CARD);

        JLabel lblTitle = new JLabel(title + ":");
        lblTitle.setFont(ThemeUtils.FONT_LABEL_BOLD);
        lblTitle.setForeground(ThemeUtils.TEXT_SECONDARY);
        lblTitle.setPreferredSize(new Dimension(110, 20));

        JLabel lblValue = new JLabel(value != null ? value : "N/A");
        lblValue.setFont(ThemeUtils.FONT_LABEL);
        lblValue.setForeground(ThemeUtils.TEXT_PRIMARY);

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
                Image img = icon.getImage().getScaledInstance(200, 280, Image.SCALE_SMOOTH);

                JLabel lbl = new JLabel(new ImageIcon(img)) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        // Shadow
                        g2.setColor(new Color(0, 0, 0, 25));
                        g2.fillRoundRect(3, 3, getWidth(), getHeight(), 12, 12);
                        // Border
                        g2.setColor(ThemeUtils.BORDER);
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                        g2.setColor(ThemeUtils.BG_CARD);
                        g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 11, 11);
                        g2.dispose();
                        super.paintComponent(g);
                    }
                };
                lbl.setPreferredSize(new Dimension(200, 280));
                lbl.setBorder(new EmptyBorder(8, 8, 8, 8));
                return lbl;
            } else {
                System.out.println("Không tìm thấy file: " + path);
            }
        } catch (Exception e) {
            System.out.println("Lỗi xử lý ảnh: " + e.getMessage());
        }

        // Placeholder
        JLabel lbl = new JLabel("No Image", SwingConstants.CENTER);
        lbl.setFont(ThemeUtils.FONT_BODY);
        lbl.setForeground(ThemeUtils.TEXT_MUTED);
        lbl.setPreferredSize(new Dimension(200, 280));
        lbl.setBackground(ThemeUtils.BG_INPUT);
        lbl.setOpaque(true);
        lbl.setBorder(BorderFactory.createLineBorder(ThemeUtils.BORDER, 1));
        return lbl;
    }
}
