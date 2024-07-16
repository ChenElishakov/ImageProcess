import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImagePanel extends JPanel {

    private BufferedImage originalImage;
    private BufferedImage displayedImage;
    private int mouseX = -1;

    public ImagePanel(BufferedImage image) {
        this.originalImage = image;
        this.displayedImage = cloneImage(image);
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);



        g.drawImage(originalImage, 0, 0, null);


        if (mouseX >= 0 && mouseX < originalImage.getWidth()) {
            BufferedImage leftPart = originalImage.getSubimage(0, 0, mouseX, originalImage.getHeight());
            BufferedImage rightPart =displayedImage.getSubimage(mouseX, 0, originalImage.getWidth() - mouseX, originalImage.getHeight());


            g.drawImage(leftPart, 0, 0, null);
            g.drawImage(rightPart, mouseX, 0, null);


            g.setColor(Color.CYAN);
            g.drawLine(mouseX, 0, mouseX, originalImage.getHeight());
        }
    }



    public void showOriginal() {
        displayedImage = cloneImage(originalImage);
        repaint();
    }

    public void applyGrayScale() {
        displayedImage = grayScale(cloneImage(originalImage));
        repaint();
    }

    public void applyInvertColors() {
        displayedImage = invertColors(cloneImage(originalImage));
        repaint();
    }

    public void applyMirror() {
        displayedImage = mirror(cloneImage(originalImage));
        repaint();
    }

    public void applyDrawBorders() {
        displayedImage = drawBorders(cloneImage(originalImage));
        repaint();
    }

    public void applyColorShiftRight() {
        displayedImage = colorShiftRight(cloneImage(originalImage));
        repaint();
    }

    public void applyColorShiftLeft() {
        displayedImage = colorShiftLeft(cloneImage(originalImage));
        repaint();
    }

    public void applyPosterize() {
        displayedImage = posterize(cloneImage(originalImage));
        repaint();
    }
    public void applySepia() {
        displayedImage =sepia(cloneImage(originalImage));
        repaint();
    }

    public void applyAddNoise() {
        displayedImage =addNoise(cloneImage(originalImage));
        repaint();
    }
    public void applyVintage() {
        displayedImage =vintage(cloneImage(originalImage));
        repaint();
    }
    public void applyTintImage() {
        displayedImage =tintImage(cloneImage(originalImage));
        repaint();
    }

    public void setImage(Image image) {
        if (image instanceof BufferedImage) {
            originalImage = (BufferedImage) image;
        } else {
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();
            originalImage = bufferedImage;
        }
        BufferedImage currentImage = originalImage;
        repaint();
    }

    private BufferedImage cloneImage(BufferedImage image) {
        BufferedImage clone = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = clone.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return clone;
    }

    private BufferedImage grayScale(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color gray = new Color(avg, avg, avg);
                image.setRGB(x, y, gray.getRGB());
            }
        }
        return image;
    }

    private BufferedImage negative(BufferedImage image) {
        BufferedImage negativeImage = cloneImage(originalImage);
        for (int x = 0; x < negativeImage.getWidth(); x++) {
            for (int y = 0; y < negativeImage.getHeight(); y++) {
                Color originalColor = new Color(negativeImage.getRGB(x, y));
                Color negativeColor = new Color(255 - originalColor.getRed(),
                        255 - originalColor.getGreen(), 255 - originalColor.getBlue());
                negativeImage.setRGB(x, y, negativeColor.getRGB());
            }
        }
        return image;
    }


    private BufferedImage invertColors(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                int r = 255 - color.getRed();
                int g = 255 - color.getGreen();
                int b = 255 - color.getBlue();
                Color invertedColor = new Color(r, g, b);
                image.setRGB(x, y, invertedColor.getRGB());
            }
        }
        return image;
    }


    private BufferedImage mirror(BufferedImage bufferedImage) {
        for (int x = 0; x < bufferedImage.getWidth() / 2; x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                Color currentColor = new Color(bufferedImage.getRGB(x, y));
                bufferedImage.setRGB(x, y, new Color(bufferedImage.getRGB(bufferedImage.getWidth() - x - 1, y)).getRGB());
                bufferedImage.setRGB(bufferedImage.getWidth() - x - 1, y, currentColor.getRGB());
            }
        }
        return bufferedImage;
    }

    private BufferedImage drawBorders(BufferedImage bufferedImage) {
        int threshold = 30;
        for (int x = 0; x < bufferedImage.getWidth() - 1; x++) {
            for (int y = 0; y < bufferedImage.getHeight() - 1; y++) {
                boolean border = false;
                Color currentPixel = new Color(bufferedImage.getRGB(x, y));
                Color rightNeighbor = new Color(bufferedImage.getRGB(x + 1, y));
                int redDiff = Math.abs(currentPixel.getRed() - rightNeighbor.getRed());
                int greenDiff = Math.abs(currentPixel.getGreen() - rightNeighbor.getGreen());
                int blueDiff = Math.abs(currentPixel.getBlue() - rightNeighbor.getBlue());
                int totalDiff = redDiff + greenDiff + blueDiff;
                if (totalDiff > threshold) {
                    border = true;
                } else {
                    Color downNeighbor = new Color(bufferedImage.getRGB(x, y + 1));
                    redDiff = Math.abs(currentPixel.getRed() - downNeighbor.getRed());
                    greenDiff = Math.abs(currentPixel.getGreen() - downNeighbor.getGreen());
                    blueDiff = Math.abs(currentPixel.getBlue() - downNeighbor.getBlue());
                    totalDiff = redDiff + greenDiff + blueDiff;
                    if (totalDiff > threshold) {
                        border = true;
                    }

                }
                if (border) {
                    bufferedImage.setRGB(x, y, Color.BLACK.getRGB());

                } else {
                    bufferedImage.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }
        return bufferedImage;
    }

    private BufferedImage colorShiftRight(BufferedImage shiftedImage) {
        shiftedImage = cloneImage(originalImage);
        for (int x = 0; x < shiftedImage.getWidth(); x++) {
            for (int y = 0; y < shiftedImage.getHeight(); y++) {
                Color originalColor = new Color(shiftedImage.getRGB(x, y));
                Color newColor = new Color(originalColor.getGreen(), originalColor.getBlue(), originalColor.getRed());
                shiftedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return shiftedImage;
    }

    private BufferedImage colorShiftLeft(BufferedImage shiftedImage) {
        shiftedImage = cloneImage(originalImage);
        for (int x = 0; x < shiftedImage.getWidth(); x++) {
            for (int y = 0; y < shiftedImage.getHeight(); y++) {
                Color originalColor = new Color(shiftedImage.getRGB(x, y));
                Color newColor = new Color(originalColor.getBlue(), originalColor.getRed(), originalColor.getGreen());
                shiftedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return shiftedImage;
    }

    private BufferedImage posterize(BufferedImage bufferedImage) {
        int numLevels = 4;
        int divisor = 256 / numLevels;
        BufferedImage currentImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                Color color = new Color(originalImage.getRGB(x, y));
                int red = Math.min(Math.round(Math.round(color.getRed() / divisor) * divisor), 255);
                int green = Math.min(Math.round(Math.round(color.getGreen() / divisor) * divisor), 255);
                int blue = Math.min(Math.round(Math.round(color.getBlue() / divisor) * divisor), 255);
                Color newColor = new Color(red, green, blue);
                currentImage.setRGB(x, y, newColor.getRGB());
            }

        }
        return currentImage;
    }

    private BufferedImage sepia(BufferedImage bufferedImage) {
        BufferedImage currentImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                Color color = new Color(originalImage.getRGB(x, y));
                int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                int sepiaRed = Math.min(255, gray + 40);
                int sepiaGreen = Math.min(255, gray + 20);
                int sepiaBlue = Math.min(255, gray);
                Color newColor = new Color(sepiaRed, sepiaGreen, sepiaBlue);
                currentImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return currentImage;
    }
    private BufferedImage addNoise(BufferedImage image) {
        Random random = new Random();
        int noiseLevel = 20; // Adjust noise level as needed
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                int r = color.getRed() + random.nextInt(noiseLevel * 2 + 1) - noiseLevel;
                int g = color.getGreen() + random.nextInt(noiseLevel * 2 + 1) - noiseLevel;
                int b = color.getBlue() + random.nextInt(noiseLevel * 2 + 1) - noiseLevel;
                r = Math.min(255, Math.max(0, r));
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));
                Color noisyColor = new Color(r, g, b);
                image.setRGB(x, y, noisyColor.getRGB());
            }
        }
        return image;
    }

    private BufferedImage vintage(BufferedImage image) {
        image = sepia(cloneImage(image));
        image = addNoise(image);
        return image;
    }

    private BufferedImage tintImage(BufferedImage bufferedImage) {
        final int constant = 50;
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                Color color = new Color(bufferedImage.getRGB(x, y));
                int average = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color newColor = new Color(average, Math.min(255,average+constant), average);
                bufferedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return bufferedImage;
    }
}
