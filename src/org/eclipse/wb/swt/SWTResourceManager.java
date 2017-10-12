package org.eclipse.wb.swt;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class SWTResourceManager {
	private static Map<RGB, Color> m_colorMap = new HashMap();

	private static Map<String, Image> m_imageMap = new HashMap();
	private static final int MISSING_IMAGE_SIZE = 10;
	public static final int TOP_LEFT = 1;
	public static final int TOP_RIGHT = 2;
	public static final int BOTTOM_LEFT = 3;
	public static final int BOTTOM_RIGHT = 4;
	protected static final int LAST_CORNER_KEY = 5;
	private static Map<Image, Map<Image, Image>>[] m_decoratedImageMap = new Map[5];

	private static Map<String, Font> m_fontMap = new HashMap();

	private static Map<Font, Font> m_fontToBoldFontMap = new HashMap();

	private static Map<Integer, Cursor> m_idToCursorMap = new HashMap();

	public static Color getColor(int systemColorID) {
		Display display = Display.getCurrent();
		return display.getSystemColor(systemColorID);
	}

	public static Color getColor(int r, int g, int b) {
		return getColor(new RGB(r, g, b));
	}

	public static Color getColor(RGB rgb) {
		Color color = (Color) m_colorMap.get(rgb);
		if (color == null) {
			Display display = Display.getCurrent();
			color = new Color(display, rgb);
			m_colorMap.put(rgb, color);
		}
		return color;
	}

	public static void disposeColors() {
		for (Color color : m_colorMap.values()) {
			color.dispose();
		}
		m_colorMap.clear();
	}

	protected static Image getImage(InputStream stream) throws IOException {
		try {
			Display display = Display.getCurrent();
			ImageData data = new ImageData(stream);
			Image localImage;
			if (data.transparentPixel > 0) {
				return new Image(display, data, data.getTransparencyMask());
			}
			return new Image(display, data);
		} finally {
			stream.close();
		}
	}

	public static Image getImage(String path) {
		Image image = (Image) m_imageMap.get(path);
		if (image == null) {
			try {
				image = getImage(new FileInputStream(path));
				m_imageMap.put(path, image);
			} catch (Exception e) {
				image = getMissingImage();
				m_imageMap.put(path, image);
			}
		}
		return image;
	}

	public static Image getImage(Class<?> clazz, String path) {
		String key = clazz.getName() + '|' + path;
		Image image = (Image) m_imageMap.get(key);
		if (image == null) {
			try {
				image = getImage(clazz.getResourceAsStream(path));
				m_imageMap.put(key, image);
			} catch (Exception e) {
				image = getMissingImage();
				m_imageMap.put(key, image);
			}
		}
		return image;
	}

	private static Image getMissingImage() {
		Image image = new Image(Display.getCurrent(), 10, 10);

		GC gc = new GC(image);
		gc.setBackground(getColor(3));
		gc.fillRectangle(0, 0, 10, 10);
		gc.dispose();

		return image;
	}

	public static Image decorateImage(Image baseImage, Image decorator) {
		return decorateImage(baseImage, decorator, 4);
	}

	public static Image decorateImage(Image baseImage, Image decorator,
			int corner) {
		if ((corner <= 0) || (corner >= 5)) {
			throw new IllegalArgumentException("Wrong decorate corner");
		}
		Map cornerDecoratedImageMap = m_decoratedImageMap[corner];
		if (cornerDecoratedImageMap == null) {
			cornerDecoratedImageMap = new HashMap();
			m_decoratedImageMap[corner] = cornerDecoratedImageMap;
		}
		Map decoratedMap = (Map) cornerDecoratedImageMap.get(baseImage);
		if (decoratedMap == null) {
			decoratedMap = new HashMap();
			cornerDecoratedImageMap.put(baseImage, decoratedMap);
		}

		Image result = (Image) decoratedMap.get(decorator);
		if (result == null) {
			Rectangle bib = baseImage.getBounds();
			Rectangle dib = decorator.getBounds();

			result = new Image(Display.getCurrent(), bib.width, bib.height);

			GC gc = new GC(result);
			gc.drawImage(baseImage, 0, 0);
			if (corner == 1)
				gc.drawImage(decorator, 0, 0);
			else if (corner == 2)
				gc.drawImage(decorator, bib.width - dib.width, 0);
			else if (corner == 3)
				gc.drawImage(decorator, 0, bib.height - dib.height);
			else if (corner == 4) {
				gc.drawImage(decorator, bib.width - dib.width, bib.height
						- dib.height);
			}
			gc.dispose();

			decoratedMap.put(decorator, result);
		}
		return result;
	}

	public static void disposeImages() {
//		for (Image image : m_imageMap.values()) {
//			image.dispose();
//		}
//		m_imageMap.clear();
//
//		for (int i = 0; i < m_decoratedImageMap.length; i++) {
//			Map cornerDecoratedImageMap = m_decoratedImageMap[i];
//			if (cornerDecoratedImageMap != null) {
//				for (Map decoratedMap : cornerDecoratedImageMap.values()) {
//					for (Image image : decoratedMap.values()) {
//						image.dispose();
//					}
//					decoratedMap.clear();
//				}
//				cornerDecoratedImageMap.clear();
//			}
//		}
	}

	public static Font getFont(String name, int height, int style) {
		return getFont(name, height, style, false, false);
	}

	public static Font getFont(String name, int size, int style,
			boolean strikeout, boolean underline) {
		String fontName = name + '|' + size + '|' + style + '|' + strikeout
				+ '|' + underline;
		Font font = (Font) m_fontMap.get(fontName);
		if (font == null) {
			FontData fontData = new FontData(name, size, style);
			if ((strikeout) || (underline)) {
				try {
					Class logFontClass = Class
							.forName("org.eclipse.swt.internal.win32.LOGFONT");
					Object logFont = FontData.class.getField("data").get(
							fontData);
					if ((logFont != null) && (logFontClass != null)) {
						if (strikeout) {
							logFontClass.getField("lfStrikeOut").set(logFont,
									Byte.valueOf((byte) 1));
						}
						if (underline)
							logFontClass.getField("lfUnderline").set(logFont,
									Byte.valueOf((byte) 1));
					}
				} catch (Throwable e) {
					System.err
							.println("Unable to set underline or strikeout (probably on a non-Windows platform). "
									+ e);
				}
			}
			font = new Font(Display.getCurrent(), fontData);
			m_fontMap.put(fontName, font);
		}
		return font;
	}

	public static Font getBoldFont(Font baseFont) {
		Font font = (Font) m_fontToBoldFontMap.get(baseFont);
		if (font == null) {
			FontData[] fontDatas = baseFont.getFontData();
			FontData data = fontDatas[0];
			font = new Font(Display.getCurrent(), data.getName(),
					data.getHeight(), 1);
			m_fontToBoldFontMap.put(baseFont, font);
		}
		return font;
	}

	public static void disposeFonts() {
		for (Font font : m_fontMap.values()) {
			font.dispose();
		}
		m_fontMap.clear();

		for (Font font : m_fontToBoldFontMap.values()) {
			font.dispose();
		}
		m_fontToBoldFontMap.clear();
	}

	public static Cursor getCursor(int id) {
		Integer key = Integer.valueOf(id);
		Cursor cursor = (Cursor) m_idToCursorMap.get(key);
		if (cursor == null) {
			cursor = new Cursor(Display.getDefault(), id);
			m_idToCursorMap.put(key, cursor);
		}
		return cursor;
	}

	public static void disposeCursors() {
		for (Cursor cursor : m_idToCursorMap.values()) {
			cursor.dispose();
		}
		m_idToCursorMap.clear();
	}

	public static void dispose() {
		disposeColors();
		disposeImages();
		disposeFonts();
		disposeCursors();
	}
}

/*
 * Location: F:\Jackey\tools_ch\TestingTools.jar Qualified Name:
 * org.eclipse.wb.swt.SWTResourceManager JD-Core Version: 0.6.2
 */