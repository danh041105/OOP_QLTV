package qltv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class lsmt extends JFrame {

    private String currentUser; 
    private boolean isAdmin; 
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private PhieumuonDAO phieuMuonDAO = new PhieumuonDAO();

    public lsmt(String username, boolean isAdmin) {
        this.currentUser = username;
        this.isAdmin = isAdmin;

        setTitle("Lịch Sử Mượn Trả Sách");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Chỉ đóng form này, không tắt app
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel("LỊCH SỬ MƯỢN TRẢ SÁCH");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.decode("#005a9e"));
        
        JLabel lblSub = new JLabel("Chào, " + username + " | Trang chủ > Lịch sử");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSub.setForeground(Color.GRAY);
        
        titlePanel.add(lblTitle);
        titlePanel.add(lblSub);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);

        JButton btnBack = new JButton("Quay lại trang chủ");
        btnBack.setBackground(Color.decode("#6c757d")); // Màu xám ghi cho khác biệt
        btnBack.setForeground(Color.BLUE);
        btnBack.setFocusPainted(false);

        btnBack.addActionListener(e -> {
            this.dispose(); 
            
            if (isAdmin) {
   
                new giaodienadmin(currentUser).setVisible(true);
            } else {

                new giaodiensv(currentUser).setVisible(true);
            }
        });
        
        searchPanel.add(btnBack);
        searchPanel.add(Box.createHorizontalStrut(15)); 

        txtSearch = new JTextField(20);
        txtSearch.setPreferredSize(new Dimension(200, 35));
        
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(Color.decode("#005a9e"));
        btnSearch.setForeground(Color.BLUE);
        
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                filterTable(txtSearch.getText());
            }
        });

        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST); 
        
        add(headerPanel, BorderLayout.NORTH);

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTable(txtSearch.getText());
            }
        });

        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new EmptyBorder(10, 20, 20, 20));
        scrollPane.getViewport().setBackground(Color.WHITE); 

        table = new JTable();
        table.setRowHeight(40); 
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(Color.decode("#e6f3ff"));

        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                label.setBackground(Color.decode("#005a9e")); 
                label.setForeground(Color.WHITE);            
                label.setFont(new Font("Arial", Font.BOLD, 14));

                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE)); 
                
                return label;
            }
        });
        // --------------------------------------------------

        loadData();

        table.setDefaultRenderer(Object.class, new CustomStatusRenderer());

        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                userDAO dao = new userDAO();
                if(isAdmin){
                      new giaodienadmin(dao.getMaADMIN_isLogin()).setVisible(true);
                }else{
                      new giaodiensv(dao.getMSV_isLogin()).setVisible(true);
                }
                
            }
        });
    }

    private void loadData() {

        if (isAdmin) {

            model = phieuMuonDAO.getLichSuMuonTra(null);
        } else {

            model = phieuMuonDAO.getLichSuMuonTra(currentUser);
        }
        
        table.setModel(model);

        if (!isAdmin) {

            if (table.getColumnModel().getColumnCount() > 0) {
                 try {
                     table.removeColumn(table.getColumn("Mã SV"));

                     table.getColumnModel().removeColumn(table.getColumnModel().getColumn(table.getColumnModel().getColumnIndex("Họ Tên SV")));
                 } catch (IllegalArgumentException e) {

                     System.out.println("Không tìm thấy cột cần xóa: " + e.getMessage());
                 }
            }
        }
    }

    private void filterTable(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        if (query.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    class CustomStatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            String colName = table.getColumnName(column);
            String text = (value != null) ? value.toString() : "";

            if (!isSelected) {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }

            if (colName.equals("Trạng Thái")) {
                setHorizontalAlignment(SwingConstants.CENTER);

                if (text.equalsIgnoreCase("Đang mượn") || text.contains("Trễ")) {
                    c.setForeground(Color.RED);
                    c.setFont(new Font("Arial", Font.BOLD, 14));
                } else if (text.equalsIgnoreCase("Đã trả")) {
                    c.setForeground(Color.decode("#009933")); 
                    c.setFont(new Font("Arial", Font.BOLD, 14));
                }
            } 

            else if (colName.equals("Hình Phạt")) {
                if (text.equals("Không")) {
                     c.setForeground(Color.decode("#009933")); 
                     setText("Không");
                } else {
                     c.setForeground(Color.RED); 
                     c.setFont(new Font("Arial", Font.BOLD, 14));
                     setText("⚠ " + text);
                }
            } 
            else {
                setHorizontalAlignment(SwingConstants.LEFT); 
            }

            return c;
        }
    }

}