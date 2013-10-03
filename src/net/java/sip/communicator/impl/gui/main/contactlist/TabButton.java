package net.java.sip.communicator.impl.gui.main.contactlist;

import net.java.sip.communicator.impl.gui.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Zenit
 * Date: 02.10.13
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public class TabButton extends JButton {

    public boolean isActive;
    Image bgImage;

    public TabButton(String text) {
        super(text);
        bgImage  = ImageLoader.getImage(ImageLoader.CALL_HISTORY_BUTTON);;


    }

    @Override
    public void paint(Graphics g) {


        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        setContentAreaFilled(false);
        setBorder(null);

        if (isActive) {
            setOpaque(false);
        } else {
            setOpaque(true);
        }
        super.paint(g);
    }
}
