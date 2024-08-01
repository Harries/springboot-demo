package com.et.thumbnailtor.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ThumbnailatorUtil {
    public static void main(String[] args) throws IOException {
        String originalPic = "/Users/liuhaihua/IdeaProjects/springboot-demo/thumbnailator/src/main/resources/origin-image/pexels-mike-b-130851.jpg";
        String picturePath = "/Users/liuhaihua/IdeaProjects/springboot-demo/thumbnailator/src/main/resources/output/";

        // Then output the result according to the parameters
        Thumbnails.of(originalPic)
                .size(400, 300)
                .toFile(picturePath + "climb-up.size.400X300.jpeg");

        // Then enlarge it proportionally and take the smallest value
        Thumbnails.of(originalPic)
                .size(4400, 3400)
                .toFile(picturePath + "climb-up.size.4400X3300.jpeg");

		//Then scale down and take the smallest value
        Thumbnails.of(originalPic)
                .size(200, 150)
                .toFile(picturePath + "climb-up.size.200X150.jpeg");

        // keepAspectRatio
        Thumbnails.of(originalPic)
                .size(200, 300)
                .keepAspectRatio(false)
                .toFile(picturePath + "climb-up.size.notKeepRatio.200X300.jpeg");

        // forcesize
        Thumbnails.of(originalPic)
                .forceSize(200, 300)
                .toFile(picturePath + "climb-up.forceSize.200X300.jpeg");

        // The width and height are reduced to 0.1 times of the original
        Thumbnails.of(originalPic)
                .scale(0.1f)
                .toFile(picturePath + "climb-up.scale.403X302.jpeg");


        // The width and height are both enlarged to 1.1 times the original
        Thumbnails.of(originalPic)
                .scale(1.1f)
                .toFile(picturePath + "climb-up.scale.4435X3326.jpeg");

        // rotate
        Thumbnails.of(originalPic)
                .size(400,300)
                .rotate(45)
                .toFile(picturePath + "climb-up.rotate.45.jpeg");


        // add watermark
        Thumbnails.of(originalPic)
                .size(2000,1500)
                .watermark(Positions.TOP_RIGHT, ImageIO.read(
                        new File("/Users/liuhaihua/IdeaProjects/springboot-demo/thumbnailator/src/main/resources/origin-image/watermark.jpg")), 0.5f)
                .toFile(picturePath + "climb-up.watermark.jpeg");


        // crop
        Thumbnails.of(originalPic)
                .sourceRegion(Positions.TOP_RIGHT, 1800, 1800)
                .size(400, 400)
                .toFile(picturePath + "climb-up.crop.jpeg");

        // batch operator
        Thumbnails.of(Objects.requireNonNull(new File(picturePath).listFiles()))
                .size(400, 400)
                .toFiles(Rename.PREFIX_DOT_THUMBNAIL);

    }
}