package utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * ThemeUtils - Unified styling utility for the Library Management System
 * Provides consistent Material Design-inspired visual theme across all views
 */
public final class ThemeUtils {

    // ===== COLOR PALETTE =====
    public static final Color PRIMARY = new Color(37, 99, 235);
    public static final Color PRIMARY_DARK = new Color(29, 78, 216);
    public static final Color PRIMARY_LIGHT = new Color(219, 234, 254);
    public static final Color SECONDARY = new Color(99, 102, 241);
    public static final Color ACCENT = new Color(244, 63, 94);

    public static final Color SUCCESS = new Color(34, 197, 94);
    public static final Color SUCCESS_DARK = new Color(22, 163, 74);
    public static final Color WARNING = new Color(245, 158, 11);
    public static final Color DANGER = new Color(239, 68, 68);
    public static final Color DANGER_DARK = new Color(220, 38, 38);
    public static final Color INFO = new Color(59, 130, 246);

    public static final Color BG_MAIN = new Color(248, 250, 252);
    public static final Color BG_CARD = new Color(255, 255, 255);
    public static final Color BG_SIDEBAR = new Color(15, 23, 42);
    public static final Color BG_SIDEBAR_HOVER = new Color(30, 41, 59);
    public static final Color BG_SIDEBAR_ACTIVE = new Color(37, 99, 235);
    public static final Color BG_INPUT = new Color(249, 250, 251);
    public static final Color BG_TABLE_ALT = new Color(248, 250, 252);

    public static final Color TEXT_PRIMARY = new Color(15, 23, 42);
    public static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    public static final Color TEXT_MUTED = new Color(148, 163, 184);
    public static final Color TEXT_BLACK = Color.BLACK;
    public static final Color TEXT_WHITE = new Color(255, 255, 255);
    public static final Color TEXT_ON_PRIMARY = new Color(255, 255, 255);

    public static final Color BORDER = new Color(226, 232, 240);
    public static final Color BORDER_FOCUS = new Color(37, 99, 235);

    // ===== FONTS =====
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_SUBHEADING = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BODY_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_SMALL_BOLD = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_TABLE_HEADER = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_TABLE_CELL = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_LABEL_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_SIDEBAR = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SIDEBAR_ACTIVE = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_LINK = new Font("Segoe UI", Font.PLAIN, 13);

    // ===== GRADIENT COLORS =====
    public static final Color[] GRADIENT_PRIMARY = { new Color(37, 99, 235), new Color(99, 102, 241) };
    public static final Color[] GRADIENT_DARK = { new Color(15, 23, 42), new Color(30, 41, 59) };
    public static final Color[] GRADIENT_SUCCESS = { new Color(34, 197, 94), new Color(16, 185, 129) };
    public static final Color[] GRADIENT_DANGER = { new Color(239, 68, 68), new Color(244, 63, 94) };

    private ThemeUtils() {
    }

    // ===== PANEL CREATION =====

    public static JPanel createRoundedPanel(Color bgColor, int radius) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                g2.dispose();
                super.paintComponent(g);
            }
        };
    }

    public static JPanel createCardPanel(int padding) {
        JPanel panel = createRoundedPanel(BG_CARD, 12);
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(padding, padding, padding, padding));
        return panel;
    }

    public static JPanel createGradientPanel(Color[] colors, int height) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, colors[0], 0, height, colors[1]);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
    }

    // ===== BUTTON CREATION =====

    public static JButton createPrimaryButton(String text) {
        return createStyledButton(text, PRIMARY, TEXT_WHITE, 38, 10);
    }

    public static JButton createSuccessButton(String text) {
        return createStyledButton(text, SUCCESS, TEXT_WHITE, 38, 10);
    }

    public static JButton createDangerButton(String text) {
        return createStyledButton(text, DANGER, TEXT_WHITE, 38, 10);
    }

    public static JButton createSecondaryButton(String text) {
        JButton btn = createStyledButton(text, BG_CARD, TEXT_PRIMARY, 38, 10);
        btn.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        return btn;
    }

    public static JButton createWarningButton(String text) {
        return createStyledButton(text, WARNING, TEXT_WHITE, 38, 10);
    }

    public static JButton createSmallButton(String text, Color bg, Color fg) {
        return createStyledButton(text, bg, fg, 32, 8);
    }

    public static JButton createSidebarButton(String text, boolean isActive) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_SIDEBAR);
        btn.setForeground(isActive ? TEXT_WHITE : new Color(148, 163, 184));
        btn.setBackground(isActive ? BG_SIDEBAR_ACTIVE : BG_SIDEBAR);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(12, 20, 12, 20));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        return btn;
    }

    public static JButton createLinkButton(String text, Color linkColor) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_LINK);
        btn.setForeground(linkColor);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(5, 5, 5, 5));
        return btn;
    }

    private static JButton createStyledButton(String text, Color bg, Color fg, int height, int arc) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(bg.brighter());
                } else if (getModel().isPressed()) {
                    g2.setColor(bg.darker());
                } else {
                    g2.setColor(bg);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BUTTON);
        btn.setForeground(fg);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(6, 16, 6, 16));
        btn.setPreferredSize(new Dimension(120, height));
        return btn;
    }

    // ===== INPUT FIELD CREATION =====

    public static JTextField createTextField(int columns) {
        JTextField tf = new JTextField(columns) {
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hasFocus() ? BORDER_FOCUS : BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
            }
        };
        tf.setFont(FONT_BODY);
        tf.setBackground(BG_INPUT);
        tf.setForeground(TEXT_PRIMARY);
        tf.setCaretColor(PRIMARY);
        tf.setBorder(new EmptyBorder(8, 12, 8, 12));
        tf.setPreferredSize(new Dimension(tf.getPreferredSize().width, 38));
        return tf;
    }

    public static JPasswordField createPasswordField(int columns) {
        JPasswordField pf = new JPasswordField(columns) {
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hasFocus() ? BORDER_FOCUS : BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
            }
        };
        pf.setFont(FONT_BODY);
        pf.setBackground(BG_INPUT);
        pf.setForeground(TEXT_PRIMARY);
        pf.setCaretColor(PRIMARY);
        pf.setBorder(new EmptyBorder(8, 12, 8, 12));
        pf.setPreferredSize(new Dimension(pf.getPreferredSize().width, 38));
        return pf;
    }

    public static <T> JComboBox<T> createComboBox(T[] items) {
        JComboBox<T> cb = new JComboBox<>(items);
        cb.setFont(FONT_BODY);
        cb.setBackground(BG_INPUT);
        cb.setForeground(TEXT_PRIMARY);
        cb.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        cb.setPreferredSize(new Dimension(cb.getPreferredSize().width, 38));
        return cb;
    }

    // ===== LABEL CREATION =====

    public static JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_SECONDARY);
        return lbl;
    }

    public static JLabel createBoldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL_BOLD);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    // ===== TABLE STYLING =====

    public static void styleTable(JTable table) {
        table.setFont(FONT_TABLE_CELL);
        table.setForeground(TEXT_PRIMARY);
        table.setBackground(BG_CARD);
        table.setSelectionBackground(PRIMARY_LIGHT);
        table.setSelectionForeground(PRIMARY_DARK);
        table.setRowHeight(40);
        table.setGridColor(BORDER);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_TABLE_HEADER);
        header.setForeground(TEXT_WHITE);
        header.setBackground(PRIMARY);
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 42));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                setFont(FONT_TABLE_HEADER);
                setForeground(TEXT_WHITE);
                setBackground(PRIMARY);
                setOpaque(true);
                setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(51, 102, 204)));
                return this;
            }
        };
        header.setDefaultRenderer(headerRenderer);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? BG_CARD : BG_TABLE_ALT);
                    c.setForeground(TEXT_PRIMARY);
                }
                setFont(FONT_TABLE_CELL);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return c;
            }
        });
    }

    public static JScrollPane styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scrollPane.getViewport().setBackground(BG_CARD);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    // ===== SIDEBAR CREATION =====

    public static JPanel createSidebar(int width) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(BG_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(width, 0));
        sidebar.setBorder(new EmptyBorder(0, 0, 0, 0));
        return sidebar;
    }

    public static JPanel createSidebarHeader(String title, String subtitle) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_SIDEBAR);
        header.setBorder(new EmptyBorder(25, 20, 25, 20));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG_SIDEBAR);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(TEXT_WHITE);

        JLabel lblSub = new JLabel(subtitle);
        lblSub.setFont(FONT_SMALL);
        lblSub.setForeground(new Color(148, 163, 184));

        content.add(lblTitle);
        content.add(Box.createVerticalStrut(4));
        content.add(lblSub);

        header.add(content, BorderLayout.CENTER);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        return header;
    }

    public static JPanel createSidebarUser(String username, String role) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(new Color(30, 41, 59));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PRIMARY);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(TEXT_WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                String initials = username != null && !username.isEmpty() ? username.substring(0, 1).toUpperCase()
                        : "U";
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(initials)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(initials, x, y);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        avatar.setPreferredSize(new Dimension(36, 36));
        avatar.setOpaque(false);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(new Color(30, 41, 59));

        JLabel lblName = new JLabel(username);
        lblName.setFont(FONT_BODY_BOLD);
        lblName.setForeground(TEXT_WHITE);

        JLabel lblRole = new JLabel(role);
        lblRole.setFont(FONT_SMALL);
        lblRole.setForeground(new Color(148, 163, 184));

        info.add(lblName);
        info.add(lblRole);

        panel.add(avatar, BorderLayout.WEST);
        panel.add(info, BorderLayout.CENTER);

        // Cố định chiều cao để không bị giãn theo chiều dọc trong BoxLayout
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        return panel;
    }

    public static JSeparator createSidebarSeparator() {
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setForeground(new Color(51, 65, 85));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    // ===== STAT CARD =====

    public static JPanel createStatCard(String title, String value, Color accentColor, int width, int height) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(accentColor);
                g2.fillRect(0, 0, 4, getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(width, height));
        card.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValue.setForeground(accentColor);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(FONT_BODY);
        lblTitle.setForeground(TEXT_SECONDARY);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.add(lblTitle, BorderLayout.NORTH);
        content.add(lblValue, BorderLayout.CENTER);

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    // ===== TOP BAR =====

    public static JPanel createTopBar(String title, String breadcrumb, JButton... extraButtons) {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(15, 25, 15, 25)));

        JPanel left = new JPanel(new BorderLayout());
        left.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(FONT_HEADING);
        lblTitle.setForeground(TEXT_PRIMARY);

        JLabel lblBreadcrumb = new JLabel(breadcrumb);
        lblBreadcrumb.setFont(FONT_SMALL);
        lblBreadcrumb.setForeground(TEXT_MUTED);

        left.add(lblTitle, BorderLayout.NORTH);
        left.add(lblBreadcrumb, BorderLayout.SOUTH);

        topBar.add(left, BorderLayout.WEST);

        if (extraButtons.length > 0) {
            JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            right.setOpaque(false);
            for (JButton btn : extraButtons) {
                right.add(btn);
            }
            topBar.add(right, BorderLayout.EAST);
        }

        return topBar;
    }

    public static JPanel createToolbar(JTextField searchField, JButton searchBtn, JButton... actionBtns) {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        toolbar.setBackground(BG_CARD);
        toolbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(10, 25, 10, 25)));

        toolbar.add(new JLabel("Tìm kiếm:"));
        toolbar.add(searchField);
        toolbar.add(searchBtn);

        if (actionBtns.length > 0) {
            toolbar.add(Box.createHorizontalStrut(10));
            JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
            sep.setPreferredSize(new Dimension(1, 25));
            toolbar.add(sep);
            toolbar.add(Box.createHorizontalStrut(5));
            for (JButton btn : actionBtns) {
                toolbar.add(btn);
            }
        }

        return toolbar;
    }

    public static JLabel createStatusBadge(String text, Color bgColor, Color textColor) {
        JLabel badge = new JLabel("  " + text + "  ") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(FONT_SMALL_BOLD);
        badge.setForeground(textColor);
        badge.setOpaque(false);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setBorder(new EmptyBorder(4, 12, 4, 12));
        return badge;
    }

    public static void addExitConfirmation(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        frame,
                        "Bạn có chắc chắn muốn thoát chương trình không?",
                        "Xác nhận thoát",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
}
