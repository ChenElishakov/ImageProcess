import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFrame extends JFrame {

    private ImagePanel imagePanel;

    public ImageFrame(File imageFile) {
        setTitle("Image Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        try {
            BufferedImage image = ImageIO.read(imageFile);
            imagePanel = new ImagePanel(image);
            add(imagePanel, BorderLayout.CENTER);
            JComboBox<String> filterComboBox = new JComboBox<>(new String[]{"Original", "Gray Scale", "Invert Colors", "Mirror", "Draw Borders" , "Color Shift Right" ,"Color Shift Left" , "Posterize" , "Sepia" , "Add Noise" , "Vintage" , "Tint Image"});
            filterComboBox.addActionListener(e -> applyFilter((String) filterComboBox.getSelectedItem()));

            JPanel controlPanel = new JPanel();
            controlPanel.add(filterComboBox);
            add(controlPanel, BorderLayout.SOUTH);

            setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }


    }

    private void applyFilter(String filter) {
        switch (filter) {

            case "Original":
                imagePanel.showOriginal();
                break;

            case "Gray Scale":
                imagePanel.applyGrayScale();
                break;

            case "Invert Colors":
                imagePanel.applyInvertColors();
                break;

            case "Mirror":
                imagePanel.applyMirror();
                break;

            case "Draw Borders":
                imagePanel.applyDrawBorders();
                break;

            case "Color Shift Right":
                imagePanel.applyColorShiftRight();
                break;

            case "Color Shift Left":
                imagePanel.applyColorShiftLeft();
                break;

            case "Posterize":
                imagePanel.applyPosterize();
                break;

            case "Sepia":
                imagePanel.applySepia();
                break;

            case "Add Noise":
                imagePanel.applyAddNoise();
                break;

            case "Vintage":
                imagePanel.applyVintage();
                break;

            case "Tint Image":
                imagePanel.applyTintImage();
                break;


            default:
                break;

        }


    }
}
