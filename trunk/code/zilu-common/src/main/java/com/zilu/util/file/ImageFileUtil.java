/**
 * 
 */
package com.zilu.util.file;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author chm 支持图像的放大，缩小， 以及图像的部分截取
 */
public class ImageFileUtil {

	/**
	 * 改变图像大小
	 * 
	 * @param image
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage resizeImage(File image, int maxWidth,
			int maxHeight, boolean absolute) throws IOException {
		BufferedImage source = ImageIO.read(image);
		int imageWidth = source.getWidth();
		int imageHeight = source.getHeight();
		// 是否要按比例缩放

		float aspectRadio = (float) imageWidth / (float) imageHeight;
		// 调整缩略后的图片大小
		if (!absolute) {
			if ((float) maxWidth / (float) maxHeight > aspectRadio) {
				imageWidth = (int) Math.ceil(maxHeight * aspectRadio);
				imageHeight = maxHeight;
			} else if ((float) maxWidth / (float) maxHeight < aspectRadio) {
				imageHeight = (int) Math.ceil(maxWidth / aspectRadio);
				imageWidth = maxWidth;
			} else {
				imageHeight = maxHeight;
				imageWidth = maxWidth;
			}
		} else {
			float reSizeRadio = (float) maxWidth / (float) maxHeight;
			if (reSizeRadio > aspectRadio) {
				imageWidth = maxWidth;
				imageHeight = (int) Math.ceil(imageWidth / aspectRadio);
			} else if ((float) maxWidth / (float) maxHeight < aspectRadio) {
				imageWidth = (int) Math.ceil(maxHeight * aspectRadio);
				imageHeight = maxHeight;
			} else {
				imageWidth = maxWidth;
				imageHeight = maxHeight;
			}
		}
		int type = source.getType();
		BufferedImage target = null;
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(
					imageWidth, imageHeight);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(imageWidth, imageHeight, type);
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(
				(float) imageWidth / (float) source.getWidth(),
				(float) imageHeight / (float) source.getHeight()));
		g.dispose();
		System.out.println(target);
		if (absolute) {
			target = grabImage(target, 0, 0, maxWidth, maxHeight);
		}
		return target;
	}

	/**
	 * 改变图像大小
	 * 
	 * @param image
	 * @param dest
	 *            目标文件夹（存放改变后图像的位置） 如与原文件位置相同，则会覆盖
	 * @param name
	 *            新的文件名
	 * @param maxWidth
	 * @param maxHeight
	 * @throws IOException
	 */
	public static void resizeImage(File image, File dest, String name,
			int maxWidth, int maxHeight, boolean absolute) throws IOException {
		if (name == null) {
			name = image.getName();
		}
		String abName = dest.getAbsolutePath() + "/" + name;
		File newImage = new File(abName);
		String type = name.substring(name.lastIndexOf(".") + 1);
		saveImage(resizeImage(image, maxWidth, maxHeight, absolute), newImage,
				type);
	}

	/**
	 * 保存图片
	 * 
	 * @param image
	 *            图片
	 * @param toFile
	 *            目标文件
	 * @param formatName
	 * @throws IOException
	 */
	public static void saveImage(BufferedImage image, File toFile,
			String formatName) throws IOException {
		if (formatName.toUpperCase().equals("GIF")) {
			formatName = "jpg";
		}
		toFile.mkdirs();
		ImageIO.write(image, formatName, toFile);
	}
	
	public static void flushImage(BufferedImage image, OutputStream os) throws IOException {
		ImageIO.write(image, "jpg", os);
	}

	/**
	 * 截图
	 * 
	 * @param image
	 * @param posX1
	 *            左上角点的 X 轴坐标
	 * @param posY1
	 *            左上角点的 Y 轴坐标
	 * @param posX2
	 *            右下角点的 X 轴坐标
	 * @param posY2
	 *            右下角点的 Y 轴坐标
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage grabImage(File image, int posX1, int posY1,
			int posX2, int posY2) throws IOException {
		BufferedImage source = ImageIO.read(image);
		return grabImage(source, posX1, posY1, posX2, posY2);
	}

	public static BufferedImage grabImage(BufferedImage source, int posX1,
			int posY1, int posX2, int posY2) throws IOException {
		int imageWidth = source.getWidth();
		int imageHeight = source.getHeight();
		int grabWidth = posX2 - posX1;
		int grabHeight = posY2 - posY1;
		if (grabWidth > imageWidth || grabHeight > imageHeight) {
//			throw new IOException("抓取范围不对");
			if (grabWidth > imageWidth) {
				grabWidth = imageWidth;
			}
			if (grabHeight > imageHeight) {
				grabHeight = imageHeight;
			}
		}
		return source.getSubimage(posX1, posY1, grabWidth, grabHeight);
	}

	/**
	 * 截图
	 * 
	 * @param image
	 * @param dest
	 *            目标文件夹（存放改变后图像的位置） 如与原文件位置相同，则会覆盖
	 * @param name
	 *            新的文件名
	 * @param posX1
	 * @param posY1
	 * @param posX2
	 * @param posY2
	 * @throws IOException
	 */
	public static void grabImage(File image, File dest, String name, int posX1,
			int posY1, int posX2, int posY2) throws IOException {
		if (name == null) {
			name = image.getName();
		}
		String abName = dest.getAbsolutePath() + name;
		File newImage = new File(abName);
		String type = image.getName().substring(
				image.getName().lastIndexOf(".") + 1);
		newImage.createNewFile();
		saveImage(grabImage(image, posX1, posY1, posX2, posY2), newImage, type);
	}

	/**
	 * 生成水印图片
	 * 
	 * @param is
	 *            源图像数据流
	 * @param watermark
	 *            水印
	 * @param os
	 *            带水印的图片输出流
	 * @param contrast
	 *            水印透明对比度 对比度越高，则水印显示越明显
	 * @throws IOException
	 */
	public static void createWatermark(InputStream is, File watermark,
			OutputStream os, float contrast) throws IOException {
		BufferedImage source = ImageIO.read(is);
		BufferedImage waterImage = ImageIO.read(watermark);
		int swidth = source.getWidth();
		int sheight = source.getHeight();
		BufferedImage dest = new BufferedImage(swidth, sheight,
				BufferedImage.TYPE_INT_RGB);
		// 绘制原图片
		Graphics2D g2D = dest.createGraphics();
		g2D.setBackground(Color.white);
		g2D.drawImage(source, 0, 0, null);
		// 绘制水印
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				contrast));
		int wwidth = waterImage.getWidth();
		int wheight = waterImage.getHeight();
		// 填充水印
		for (int i = 0; i < swidth; i += wwidth) {
			for (int j = 0; j < sheight; j += wheight) {
				g2D.drawImage(waterImage, i, j, null);
			}
		}
		g2D.dispose();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(dest);
		param.setQuality(80f, true);
		encoder.encode(dest);
	}

	/**
	 * 建立简单的水印图片
	 * 
	 * @param source
	 * @param watermark
	 * @param dest
	 * @param fileName
	 * @throws IOException
	 */
	public static void createSimpleWatermart(File source, File watermark,
			File dest, String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(source);
		String path = dest.getAbsolutePath();
		File outFile = new File(path + fileName);
		FileOutputStream fos = new FileOutputStream(outFile);
		createWatermark(fis, watermark, fos, 0.2f);
	}

	public static BufferedImage createVerifyImage(String word) {
		int height = 20;
		int width = 15 * word.length();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB); // 设置图片大小的
		Graphics gra = image.getGraphics();
		Random random = new Random();

		gra.setColor(getRandColor(200, 250)); // 设置背景色
		gra.fillRect(0, 0, width, height);

		gra.setColor(Color.black); // 设置字体色
		Font mFont = new Font("Times New Roman", Font.PLAIN, 18);// 设置字体
		gra.setFont(mFont);

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		gra.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gra.drawLine(x, y, x + xl, y + yl);
		}

		// 取随机产生的认证码(4位数字)
		for (int i = 0; i < word.length(); i++) {
			// 将认证码显示到图象中
			gra.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			gra.drawString(String.valueOf(word.charAt(i)), 13 * i + 6, 16);
		}
		return image;

	}

	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	public static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	
	public static void main(String arg[]) throws IOException {
		File image = new File(
				"C:\\Documents and Settings\\dell\\桌面\\wfs_img\\bb.gif");
		// File water = new File("C:\\Documents and Settings\\All
		// Users\\Documents\\My Pictures\\示例图片\\cncs.JPG");
		File dest = new File("D:/");
		resizeImage(image, dest, "aa.gif", 640, 480, true);
		// grabImage(image, dest, "222.jpg", 100, 75, 500, 400);
		// createSimpleWatermart(image, water, dest, "333.jpg");
	}
}
