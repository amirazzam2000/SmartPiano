package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ViewUtils {
    public final static String PAUSE = "Shared/src/view/assets" +
            "/noun_pause_3202007.bmp";
    public final static String RECORD = "Shared/src/view/assets" +
            "/noun_Record_341326.bmp";
    public final static String EDIT = "Shared/src/view/assets" +
            "/noun_edit_857979.bmp";
    public final static String BACK = "Shared/src/view/assets" +
            "/noun_back_1227057.bmp";
    public final static String DELETE = "Shared/src/view/assets" +
            "/noun_Delete_2025452.bmp";
    public final static String MUTE =
            "Shared/src/view/assets/noun_Mute_241382.bmp";
    public final static String UNMUTE =
            "Shared/src/view/assets/interface.bmp";
    public final static String PLAY =
            "Shared/src/view/assets/interface2.bmp";
    public final static String SETTINGS =
            "Shared/src/view/assets/settings.bmp";


    ViewUtils() {
    }

    public static Image getScaledImageFromPath(String path, String defaultPath,
                                         int width, int height) {
        BufferedImage profileImage = null;

        try {
            profileImage = ImageIO.read(new File(path));
        } catch (IllegalArgumentException | IOException var8) {
            try {
                profileImage = ImageIO.read(new File("Server/src/view/assets" +
                        "/noun_back_1227057.bmp"));
            } catch (IOException var7) {
                profileImage = new BufferedImage(width, height, 0);
            }
        }

        return profileImage.getScaledInstance(width, height, 4);
    }
}