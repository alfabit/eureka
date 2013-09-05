package net.java.sip.communicator.plugin.eureka.gui;

import net.java.sip.communicator.plugin.desktoputil.TransparentPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Руслан
 * Date: 29.08.13
 * Time: 23:25
 * To change this template use File | Settings | File Templates.
 */
public class BalanceLabel extends TransparentPanel {

    public BalanceLabel() {
        super(new BorderLayout(10,10));

        add(new JLabel("BALANCE"));
    }
}
