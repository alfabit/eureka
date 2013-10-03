package net.java.sip.communicator.impl.gui.main.contactlist;

import net.java.sip.communicator.impl.gui.main.UINotificationGroup;
import net.java.sip.communicator.impl.gui.main.UINotificationManager;
import net.java.sip.communicator.impl.gui.main.call.CallHistoryButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Zenit
 * Date: 02.10.13
 * Time: 14:38
 * To change this template use File | Settings | File Templates.
 */
public class TestHistoryButtonUnreadMess extends JButton implements ActionListener {

    CallHistoryButton b;

    public TestHistoryButtonUnreadMess(String text, Component b) {
        super(text);
        this.b = (CallHistoryButton) b;
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
//        Collection<UINotificationGroup> notificationGroups
//                = UINotificationManager.getNotificationGroups();

        ArrayList notificationGroups = new ArrayList();
        notificationGroups.add(new UINotificationGroup("AAAA", "aaa"));
        notificationGroups.add(new UINotificationGroup("DDD", "bbb"));
        notificationGroups.add(new UINotificationGroup("qaz", "zzzzzzzz"));

        if (!b.isHistoryVisible && notificationGroups.size() > 0)
        {
            b.setNotificationView(notificationGroups);
        }
        else
        {
            b.setHistoryView();
        }

        b.revalidate();
        this.repaint();
    }
}
