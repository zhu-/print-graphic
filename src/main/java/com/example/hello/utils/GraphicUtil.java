package com.example.hello.utils;

import com.example.hello.vo.PaperVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class GraphicUtil {
    private final static String CREDENTIAL_TEMPLATE = "credential/paperCredential.png";
    private final static String FONT_FILENAME = "font/simsun.ttf";
    private final static float FONT_SIZE = 18f;
    private final static int START_X_1 = 126;
    private final static int START_X_2 = 444;
    private final static int START_Y = 170;
    private final static float GRID_HEIGHT = 42.5f;
    private final static int TEXT_WIDTH = 16;
    private final static int AMOUNT_NUMBER_LENGTH = 12;
    private final static int NOTE_SHOW_LENGTH = 32;

    private Map<String, Rectangle> CREDENTIAL_FIELD_MAP = new HashMap<String, Rectangle>() {
        {
            put("created_date", new Rectangle(START_X_1 + 4 * TEXT_WIDTH,START_Y, 0, 0));
            put("due_date", new Rectangle(START_X_1 + 5 * TEXT_WIDTH,(int)(START_Y + GRID_HEIGHT), 0, 0));
            put("holder", new Rectangle(START_X_1 + 5 * TEXT_WIDTH,(int)(START_Y + 2 * GRID_HEIGHT), 0, 0));
            put("amount", new Rectangle(718, (int)(START_Y + 4 * GRID_HEIGHT), 40, 42));
            put("if_transfer", new Rectangle(START_X_1 + 6 * TEXT_WIDTH,(int)(START_Y + 5 * GRID_HEIGHT), 0, 0));
            put("if_delay", new Rectangle(START_X_2 + 4 * TEXT_WIDTH,(int)(START_Y + 5 * GRID_HEIGHT), 0, 0));

            put("status", new Rectangle(START_X_2 + 4 * TEXT_WIDTH,START_Y, 0, 0));
            put("encode", new Rectangle(START_X_2 + 4 * TEXT_WIDTH,(int)(START_Y + GRID_HEIGHT), 0, 0));
            put("origin_holder", new Rectangle(START_X_2 + 7 * TEXT_WIDTH,(int)(START_Y + 2 * GRID_HEIGHT), 0, 0));
            put("note", new Rectangle(START_X_1 + 2 * TEXT_WIDTH,(int)(START_Y + 7 * GRID_HEIGHT), 0, 0));
        }
    };

    private static Font dynamicFont = null;

    public File createCertificate(PaperVo paperVo) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(CREDENTIAL_TEMPLATE);
        if (inputStream == null) {
            log.error("File {} does not exist.", CREDENTIAL_TEMPLATE);
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        File tempFile = null;
        try {
            final BufferedImage image = ImageIO.read(inputStream);
            inputStream.close();

            String note = paperVo.getNote();
            if (note != null && note.length() > NOTE_SHOW_LENGTH) {
                note = note.substring(0, NOTE_SHOW_LENGTH - 1);
            }

            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setColor(Color.RED);

            Font font = loadFont(FONT_SIZE);
            if (font == null) {
                log.error("Failed to set font.");
            } else {
                g2d.setFont(font);
                log.info("Set font successfully.");
            }

            writeImage(g2d, "created_date", dateFormat.format(paperVo.getCreatedDate()));
            writeImage(g2d, "due_date", dateFormat.format(paperVo.getDueDate()));
            writeImage(g2d, "encode", paperVo.getEncode());
            writeImage(g2d, "holder", paperVo.getHolderEnterpriseName());
            writeImage(g2d, "origin_holder", paperVo.getPrimaryEnterpriseName());
            writeImage(g2d, "if_transfer", paperVo.getIfTransfer() > 0 ? "Y" : "N");
            writeImage(g2d, "if_delay", paperVo.getIfDelay() > 0 ? "Y" : "N");
            writeImage(g2d, "note", note);
            writeAmount(g2d, paperVo.getAmount());

            g2d.dispose();

            tempFile = File.createTempFile("paperCertificate", ".png");
            ImageIO.write(image, "png", tempFile);
            log.info("created file path: {}" , tempFile.getAbsoluteFile());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }

        return tempFile;
    }

    private Font loadFont(float fontSize) {
        if (dynamicFont == null) {
            log.info("Try to load font.");
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(FONT_FILENAME);
            try {
                dynamicFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            try {
                inputStream.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return dynamicFont == null ? null : dynamicFont.deriveFont(fontSize);
    }

    private void writeAmount(Graphics graphics, long amount) {
        for (int i = 0; i < AMOUNT_NUMBER_LENGTH && amount > 0; i++, amount /= 10) {
            writeImage(graphics, (int)(CREDENTIAL_FIELD_MAP.get("amount").x - i * CREDENTIAL_FIELD_MAP.get("amount").width) + (i / 2), CREDENTIAL_FIELD_MAP.get("amount").y, String.valueOf(amount % 10)/*BIG_NUMBER_LIST[(int)(amount % 10)]*/);
        }
    }

    private void writeImage(Graphics graphics, String name,  String text) {
        writeImage(graphics, CREDENTIAL_FIELD_MAP.get(name).x, CREDENTIAL_FIELD_MAP.get(name).y, text);
    }

    private void writeImage(Graphics graphics, int x, int y,  String text) {
        if (text == null) {
            log.error("The text should not be null.");
            return;
        }

        graphics.drawString(text, x, y);
    }
}
