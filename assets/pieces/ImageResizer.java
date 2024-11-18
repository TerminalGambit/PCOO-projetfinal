import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageResizer {
    private static final int TARGET_WIDTH = 64;
    private static final int TARGET_HEIGHT = 64;

    public static void main(String[] args) {
        // Directory containing the images
        String imageDirectory = "./"; // Change this to your directory if necessary

        File folder = new File(imageDirectory);
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("The specified directory does not exist or is not a directory.");
            return;
        }

        // Iterate over all PNG files in the directory
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".png")) {
                try {
                    resizeImage(file);
                } catch (IOException e) {
                    System.err.println("Error processing file: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    private static void resizeImage(File file) throws IOException {
        // Read the original image
        BufferedImage originalImage = ImageIO.read(file);

        // Create a new BufferedImage with the target dimensions
        BufferedImage resizedImage = new BufferedImage(TARGET_WIDTH, TARGET_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        
        // Draw the original image resized into the new BufferedImage
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage.getScaledInstance(TARGET_WIDTH, TARGET_HEIGHT, Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose();

        // Overwrite the original file with the resized image
        ImageIO.write(resizedImage, "png", file);
        System.out.println("Resized: " + file.getName());
    }
}
