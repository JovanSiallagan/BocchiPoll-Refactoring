import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KandidatGridPanel extends JPanel {
    private ButtonGroup waifuGroup;
    private final Color bgDark = new Color(30, 30, 30);       
    private final Color panelDark = new Color(43, 43, 43);    
    private final Color textLight = new Color(235, 235, 235); 
    private final Color borderDark = new Color(70, 70, 70); 

    public KandidatGridPanel(String[] waifus) {
        setLayout(new GridLayout(2, 5, 15, 15));
        setBackground(bgDark);
        waifuGroup = new ButtonGroup();

        for (String name : waifus) {
            add(createCharacterPanel(name));
        }
    }

    private JPanel createCharacterPanel(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(panelDark);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderDark, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        ResponsiveImagePanel img = new ResponsiveImagePanel("images/" + name.replace(" ", "_") + ".jpg");
        JRadioButton rb = new JRadioButton(name);
        rb.setActionCommand(name);
        rb.setBackground(panelDark);
        rb.setForeground(textLight);
        rb.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rb.setHorizontalAlignment(SwingConstants.CENTER);
        rb.setFocusPainted(false);
        waifuGroup.add(rb);

        MouseAdapter clickAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { rb.setSelected(true); }
        };
        img.addMouseListener(clickAdapter);
        panel.addMouseListener(clickAdapter);

        panel.add(img, BorderLayout.CENTER);
        panel.add(rb, BorderLayout.SOUTH);
        return panel;
    }

    public String getPilihan() {
        if (waifuGroup.getSelection() == null) return null;
        return waifuGroup.getSelection().getActionCommand();
    }

    public void clearPilihan() {
        waifuGroup.clearSelection();
    }
}