package com.girlkun.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class ImageUtil {

    public static void saveImage(BufferedImage image, String pathFolder, String name) {
        try {
            File folder = new File(pathFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File outputfile = new File(pathFolder + "/" + name + ".png");
            ImageIO.write(image, "png", outputfile);
        } catch (Exception e) {
        }
    }

    public static void saveImage(byte[] data, String pathFolder, String name) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BufferedImage image = ImageIO.read(bis);
            File folder = new File(pathFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File outputfile = new File(pathFolder + "/" + name + ".png");
            ImageIO.write(image, "png", outputfile);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
//        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 100; j++) {
                try {
                    readmob(1, j);
                } catch (Exception e) {
                }
            }
//        }
    }

    public static void readmob(int zoomlv, int mob) {
        BufferedImage oriImage = null;
        byte[] data = null;
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream("data/girlkun/mob/x" + zoomlv + "/" + mob));
            int typeRead = dis.readByte();
//            System.out.println("type read: " + typeRead);
            if (typeRead != 0) {
                data = new byte[dis.readInt()];
                dis.read(data);
            } else {
                data = new byte[dis.readInt()];
                dis.read(data);
            }
            byte[] dataImage = new byte[dis.readInt()];
            dis.read(dataImage);
            oriImage = ImageIO.read(new ByteArrayInputStream(dataImage));
            saveImage(oriImage, "C:\\Users\\admin\\Desktop\\read mob\\x" + zoomlv, mob + "");
            if (typeRead != 0) {
                System.out.println("boss: " + mob);
                readDataNewBoss(data, typeRead, oriImage, zoomlv, mob);
            } else {
//                System.out.println("mob: " + mob);
                readDataMob(data, oriImage, zoomlv, mob);
            }

            int check = dis.readByte();
//            System.out.println("check: " + check);
            if (check == 1 || check == 2) {
                readFrameBoss(dis);
            }
        } catch (Exception e) {
        }
    }

    public static void readFrameBoss(DataInputStream dis) {

    }

    public static void readDataNewBoss(byte[] data, int typeRead, BufferedImage image, int zoomLevel, int mobId) {
        try {
            BufferedImage[] imagesInfo = null;
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            DataInputStream dis = new DataInputStream(bis);

            int nImageInfo = dis.readByte();
            imagesInfo = new BufferedImage[nImageInfo];
            for (int i = 0; i < nImageInfo; i++) {
                int id = dis.readByte();
                int x = typeRead == 1 ? dis.readUnsignedByte() : dis.readShort();
                int y = typeRead == 1 ? dis.readUnsignedByte() : dis.readShort();
                int w = dis.readUnsignedByte();
                int h = dis.readUnsignedByte();
                x *= zoomLevel;
                y *= zoomLevel;
                w *= zoomLevel;
                h *= zoomLevel;
//                if (y < 0) {
//                    y = 0;
//                }
//                if (y > image.getHeight()) {
//                    y = image.getHeight();
//                }
//                if (x + w > image.getWidth()) {
//                    w = image.getWidth() - x;
//                }
//                if (y + h > image.getHeight()) {
//                    h = image.getHeight() - y;
//                }
                try {
                    imagesInfo[i] = image.getSubimage(x, y, w, h);
                } catch (Exception e) {
                    e.printStackTrace();
//                    System.out.println("mob: " + mobId);
                }
                //từng bộ phận
                saveImage(imagesInfo[i], "C:\\Users\\admin\\Desktop\\read mob\\imginfo\\x" + zoomLevel
                        + "\\" + mobId, mobId + "_" + id);
            }

            int nFrame = dis.readShort();
            BufferedImage[] frames = new BufferedImage[nFrame];
            for (int i = 0; i < nFrame; i++) {
                BufferedImage frame = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) frame.createGraphics();
                int nSubImage = dis.readByte();
                for (int j = 0; j < nSubImage; j++) {
                    int dx = dis.readShort() * zoomLevel;
                    int dy = dis.readShort() * zoomLevel;
                    int imageId = dis.readByte();
                    g.drawImage(imagesInfo[imageId], (500 + dx), (500 + dy),
                            null);
                }
                frames[i] = frame;
                //hình quái từng frame
                saveImage(trimImage(frame), "C:\\Users\\admin\\Desktop\\read mob\\frame\\x" + zoomLevel
                        + "\\" + mobId, mobId + "_" + i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readDataMob(byte[] data, BufferedImage image, int zoomLevel, int mobId) {
        try {
            BufferedImage[] imagesInfo = null;

            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            DataInputStream dis = new DataInputStream(bis);
            int nImageInfo = dis.readByte();
            imagesInfo = new BufferedImage[nImageInfo];
            for (int i = 0; i < nImageInfo; i++) {
                int id = dis.readByte();
                int x = dis.readUnsignedByte() * zoomLevel;
                int y = dis.readUnsignedByte() * zoomLevel;
                int w = dis.readUnsignedByte() * zoomLevel;
                int h = dis.readUnsignedByte() * zoomLevel;
//                if (y < 0) {
//                    y = 0;
//                }
//                if (y > image.getHeight()) {
//                    y = image.getHeight();
//                }
//                if (x + w > image.getWidth()) {
//                    w = image.getWidth() - x;
//                }
//                if (y + h > image.getHeight()) {
//                    h = image.getHeight() - y;
//                }
                try {
                    imagesInfo[id] = image.getSubimage(x, y, w, h);
                } catch (Exception e) {
                    System.out.println(x + " - " + y + " - " + w + " - " + h);
                    System.out.println("loi doc mob: " + mobId);
                    e.printStackTrace();
//                    System.out.println("mob: " + mobId);
                }
                //từng bộ phận
                saveImage(imagesInfo[i], "C:\\Users\\admin\\Desktop\\read mob\\imginfo\\x" + zoomLevel
                        + "\\" + mobId, mobId + "_" + id);
            }

            int nFrame = dis.readShort();
            BufferedImage[] frames = new BufferedImage[nFrame];
            for (int i = 0; i < nFrame; i++) {
                BufferedImage frame = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) frame.createGraphics();
                int nSubImage = dis.readByte();
                for (int j = 0; j < nSubImage; j++) {
                    int dx = dis.readShort() * zoomLevel;
                    int dy = dis.readShort() * zoomLevel;
                    int imageId = dis.readByte();
                    g.drawImage(imagesInfo[imageId], (100 + dx), (100 + dy),
                            null);
                }
                frames[i] = frame;
                //hình quái từng frame
                saveImage(trimImage(frame), "C:\\Users\\admin\\Desktop\\read mob\\frame\\x" + zoomLevel
                        + "\\" + mobId, mobId + "_" + i);
            }

            int nAFrame = dis.readShort();
            for (int i = 0; i < nAFrame; i++) {
                int frameId = dis.readShort();
//                System.out.println("frame id: " + frameId);
            }
            //------------------------------------------------------------------
            ImageOutputStream output
                    = new FileImageOutputStream(new File("C:\\Users\\admin\\Desktop\\read mob\\framegif\\" + mobId + ".gif"));
            GifSequenceWriter writer
                    = new GifSequenceWriter(output, frames[0].getType(), 300, true);

            // write out the first image to our sequence...
            writer.writeToSequence(frames[0]);
            for (int i = 1; i < frames.length - 1; i++) {
                writer.writeToSequence(frames[i]);
            }

            writer.close();
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage trimImage(BufferedImage image) {
        WritableRaster raster = image.getAlphaRaster();
        int width = raster.getWidth();
        int height = raster.getHeight();
        int left = 0;
        int top = 0;
        int right = width - 1;
        int bottom = height - 1;
        int minRight = width - 1;
        int minBottom = height - 1;

        top:
        for (; top <= bottom; top++) {
            for (int x = 0; x < width; x++) {
                if (raster.getSample(x, top, 0) != 0) {
                    minRight = x;
                    minBottom = top;
                    break top;
                }
            }
        }
        left:
        for (; left < minRight; left++) {
            for (int y = height - 1; y > top; y--) {
                if (raster.getSample(left, y, 0) != 0) {
                    minBottom = y;
                    break left;
                }
            }
        }

        bottom:
        for (; bottom > minBottom; bottom--) {
            for (int x = width - 1; x >= left; x--) {
                if (raster.getSample(x, bottom, 0) != 0) {
                    minRight = x;
                    break bottom;
                }
            }
        }

        right:
        for (; right > minRight; right--) {
            for (int y = bottom; y >= top; y--) {
                if (raster.getSample(right, y, 0) != 0) {
                    break right;
                }
            }
        }
        try {
            return image.getSubimage(left, top, right - left + 1, bottom - top + 1);
        } catch (Exception e) {
            return image;
        }
    }

    //*********************read effect
//    public static void main(String[] args) {
//        for (int i = 1; i <= 4; i++) {
//            for (int j = 0; j < 100; j++) {
//                readEff(j, i);
//            }
//        }
////        readEff(5, 1);
//    }

    public static void readEff(int id, int zoom) {
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream("data/girlkun/effdata/x" + zoom + "/" + id));
            System.out.println(dis.readShort());
            byte[] data = new byte[dis.readInt()];
            dis.read(data);
            byte[] dataImage = new byte[dis.readInt()];
            dis.read(dataImage);
            ByteArrayInputStream bis = new ByteArrayInputStream(dataImage);
            BufferedImage oriImage = ImageIO.read(bis);
            readDataEffect(data, id, zoom, oriImage);
//            JOptionPane.showMessageDialog(null, null, "", zoom, new ImageIcon(oriImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readDataEffect(byte[] data, int idEffect, int zoom, BufferedImage oriImage) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            DataInputStream dis = new DataInputStream(bis);

            int nImageInfo = dis.readByte();
            BufferedImage[] imageInfo = new BufferedImage[nImageInfo];
            for (int i = 0; i < nImageInfo; i++) {
                int id = dis.readByte();
                int x = dis.readByte() * zoom;
                int y = dis.readByte() * zoom;
                int w = dis.readByte() * zoom;
                int h = dis.readByte() * zoom;
                imageInfo[i] = oriImage.getSubimage(x, y, w, h);
            }

            int nFrame = dis.readShort();
            for (int i = 0; i < nFrame; i++) {
                BufferedImage frame = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = frame.createGraphics();
                int nF = dis.readByte();
                for (int j = 0; j < nF; j++) {
                    int dx = dis.readShort() * zoom;
                    int dy = dis.readShort() * zoom;
                    int idImage = dis.readByte();
                    g.drawImage(imageInfo[idImage], 500 + dx, 500 + dy, null);
                }
                g.dispose();
                saveImage(trimImage(frame), "C:\\Users\\admin\\Desktop\\effect_\\x" + zoom + "\\" + idEffect, i + "");
            }
            int arrF = dis.readShort();
            for (int i = 0; i < arrF; i++) {
                System.out.println(dis.readShort());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - GirlBeo
 */
