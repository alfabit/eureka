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

    private boolean isActive;
    private static final Color activeColor = Color.white;
    private static final Color inactiveColor = new Color(245, 243, 231);
    private static final Color borderColor = new Color(209, 203, 177);
    private static final Color inactiveForegroundColor = new Color(138, 126, 76);

    private static final int roundArc = 6;
    private boolean isRoundedTopLeft;

    public boolean isRoundedTopLeft() {
        return isRoundedTopLeft;
    }

    public void setRoundedTopLeft(boolean roundedTopLeft) {
        isRoundedTopLeft = roundedTopLeft;
    }

    public boolean isRoundedTopRight() {
        return isRoundedTopRight;
    }

    public void setRoundedTopRight(boolean roundedTopRight) {
        isRoundedTopRight = roundedTopRight;
    }

    private boolean isRoundedTopRight;

    Image bgImage;

    public TabButton(String text) {
        super(text);
        bgImage  = ImageLoader.getImage(ImageLoader.CALL_HISTORY_BUTTON);

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        if(isActive)
        {
            setForeground(Color.black);
        }
        else
        {
            setForeground(inactiveForegroundColor);
        }
        repaint();

    }

    @Override
    public void paint(Graphics g) {


        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        setContentAreaFilled(false);
        setBorder(null);


        g.setColor(inactiveColor);
        g.fillRect(0,0,getWidth(), getHeight());

        if (isActive) {


            g.setColor(activeColor);

            if(isRoundedTopLeft || isRoundedTopRight)
            {
                int borderStart = 0;
                int borderEnd = getWidth();
                g.fillRoundRect(0,0,getWidth(),getHeight(),roundArc,roundArc);

                if (isRoundedTopRight) {
                    borderEnd-=3;
                } else {
                    g.fillRect(getWidth()-10,0,10,10);
                }
                if (isRoundedTopLeft) {
                    borderStart+=3;
                } else {
                    g.fillRect(0,0,10,10);
                }

                g.fillRect(0,10,getWidth(), getHeight()-10);

                // paint top line border
                g.setColor(borderColor);
                g.drawLine(borderStart,0,borderEnd-borderStart+1,0);
            }
            else
            {
                g.fillRect(0, 0, getWidth(), getHeight());

                // paint top line border
                g.setColor(borderColor);
                g.drawLine(0,0,getWidth(),0);
            }
        }
        else
        {
            // paint bottom line border
            g.setColor(borderColor);
            g.drawLine(0,getHeight()-1,getWidth(),getHeight()-1);
        }
        super.paint(g);
    }
}
