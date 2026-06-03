import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ResponsiveImagePanel extends JPanel {
    private Image image;
    private final Color panelDark = new Color(43, 43, 43);
    private final Color textLight = new Color(235, 235, 235);

    public ResponsiveImagePanel(String path) {
        if (new File(path).exists()) this.image = new ImageIcon(path).getImage();
        setBackground(panelDark);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int pW = getWidth(), pH = getHeight();
            int iW = image.getWidth(null), iH = image.getHeight(null);
            double scale = Math.min((double)pW/iW, (double)pH/iH);
            int dW = (int)(iW*scale), dH = (int)(iH*scale);
            
            g2d.drawImage(image, (pW-dW)/2, (pH-dH)/2, dW, dH, null);
            g2d.dispose();
        } else {
            g.setColor(textLight);
            g.drawString("[?]", getWidth()/2 - 10, getHeight()/2);
        }
    }
}