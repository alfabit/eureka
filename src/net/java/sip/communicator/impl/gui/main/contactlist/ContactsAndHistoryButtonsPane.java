package net.java.sip.communicator.impl.gui.main.contactlist;

import net.java.sip.communicator.impl.gui.GuiActivator;
import net.java.sip.communicator.impl.gui.main.UINotification;
import net.java.sip.communicator.impl.gui.main.UINotificationGroup;
import net.java.sip.communicator.impl.gui.main.UINotificationListener;
import net.java.sip.communicator.impl.gui.main.UINotificationManager;
import net.java.sip.communicator.impl.gui.main.call.CallHistoryButton;
import net.java.sip.communicator.impl.gui.utils.ImageLoader;
import net.java.sip.communicator.util.GuiUtils;
import net.java.sip.communicator.util.skin.Skinnable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Zenit
 * Date: 02.10.13
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */
public class ContactsAndHistoryButtonsPane extends JPanel implements UINotificationListener,
        Skinnable, ActionListener {

    /**
     * The Contacts icon.
     */
    private Image ContactsImage;

    /**
     * The history icon.
     */
    private Image historyImage;

    /**
     * The history icon.
     */
    private Image pressedImage;

    /**
     * The contacts pressed icon.
     */
    private Image pressedContactsImage;

    /**
     * The history pressed icon.
     */
    private Image pressedHistoryImage;

    /**
     * The notification image.
     */
    private Image notificationImage;

    /**
     * Indicates if the history is visible.
     */
    private boolean isHistoryVisible = false;

    /**
     * Indicates if this button currently shows the number of unread
     * notifications or the just the history icon.
     */
    private boolean isNotificationsView = false;

    /**
     * The tool tip shown by default over the history button.
     */
    private final static String callHistoryToolTip
            = GuiActivator.getResources().getI18NString(
            "service.gui.CALL_HISTORY_TOOL_TIP");

    /**
     * The tool tip shown when we're in history view.
     */
    private final static String showContactListToolTip
            = GuiActivator.getResources().getI18NString(
            "service.gui.SHOW_CONTACT_LIST_TOOL_TIP");

    /**
     * The Contacts button.
     */
    private TabButton btnContacts;

    /**
     * The History button.
     */
    private TabButton btnHistory;
//    private CallHistoryButton btnHistory;

    /**
     * The Contacts list panel.
     */
    private TreeContactList treeContactsList;

    public ContactsAndHistoryButtonsPane(TreeContactList contactList) {

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        historyImage
                = ImageLoader.getImage(ImageLoader.CALL_HISTORY_BUTTON);

        pressedImage
                = ImageLoader.getImage(ImageLoader.CALL_HISTORY_BUTTON_PRESSED);

        notificationImage
                = ImageLoader.getImage(
                ImageLoader.CALL_HISTORY_BUTTON_NOTIFICATION);

        btnContacts = new TabButton("Contacts");
        btnContacts.isActive = true;
        btnContacts.addActionListener(this);
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(btnContacts, c);

        btnHistory = new TabButton("History");
        btnHistory.isActive = false;
//        btnHistory = new CallHistoryButton();
        btnHistory.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 0;
        add(btnHistory, c);
 /*
        JButton btnTest = new JButton("TestMessage");            // 3-rd button for generating test notification about unread messages
        btnTest.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 2;
        c.gridy = 0;
        add(btnTest, c);
 */
        treeContactsList = contactList;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 140;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;

        add(contactList, c);
    }

    public void actionPerformed(ActionEvent e) {

        if(((JButton)e.getSource()).getActionCommand().equals("TestMessage")){     // unread messages notification simulation
            if (!isHistoryVisible && 5 > 0)
            {
                ArrayList notificationGroups = new ArrayList();
                notificationGroups.add(new UINotificationGroup("AAAA", "aaa"));
                notificationGroups.add(new UINotificationGroup("DDD", "bbb"));
                notificationGroups.add(new UINotificationGroup("qaz", "zzzzzzzz"));
                setNotificationView(notificationGroups);
            }
            else
            {
                setHistoryView();
            }

            this.revalidate();
            this.repaint();
        }
        else{
            if (isHistoryVisible && !isNotificationsView)
            {

                treeContactsList.setDefaultFilter(TreeContactList.presenceFilter);
                treeContactsList.applyDefaultFilter();

                isHistoryVisible = false;
            }
            else
            {
                treeContactsList.setDefaultFilter(TreeContactList.historyFilter);
                treeContactsList.applyDefaultFilter();

                UINotificationManager.removeAllNotifications();

                isHistoryVisible = true;
            }

            setHistoryView();

            treeContactsList.requestFocusInWindow();
            repaint();
        }
    }

    /**
     * Indicates that a new notification is received.
     *
     * @param notification the notification that was received
     */
    public void notificationReceived(UINotification notification) {
        Collection<UINotificationGroup> notificationGroups
                = UINotificationManager.getNotificationGroups();

        if (!isHistoryVisible && notificationGroups.size() > 0)
        {
            setNotificationView(notificationGroups);
        }
        else
        {
            setHistoryView();
        }

        this.revalidate();
        this.repaint();
    }

    /**
     * Sets the history view.
     */
    private void setHistoryView()
    {
        if (isNotificationsView)
            isNotificationsView = false;
        else
            btnHistory.setIcon(null);

        if (isHistoryVisible)
        {
//            btnContacts.setBgImage(historyImage);
//            btnHistory.setBgImage(pressedHistoryImage);

            btnContacts.setText("Not Active");
            btnContacts.setForeground(Color.green);
            btnContacts.isActive = false;

            btnHistory.setText("Active");
            btnHistory.setForeground(Color.red);
            btnHistory.isActive = true;

            btnHistory.setToolTipText(showContactListToolTip);
        }
        else
        {
//            btnContacts.setBgImage(pressedHistoryImage);
//            btnHistory.setBgImage(historyImage);

            btnContacts.setText("Active");
            btnContacts.setForeground(Color.red);
            btnContacts.isActive = true;

            btnHistory.setText("Not Active");
            btnHistory.setForeground(Color.green);
            btnHistory.isActive = false;

            btnHistory.setToolTipText(callHistoryToolTip);
        }
//        btnHistory.setText("");
    }

    /**
     * Sets the notifications view of this button.
     *
     * @param notificationGroups the list of unread notification groups
     */
    private void setNotificationView(
            Collection<UINotificationGroup> notificationGroups)
    {
        int notificationCount = 0;
        isNotificationsView = true;

        Iterator<UINotificationGroup> groupsIter
                = notificationGroups.iterator();

        String tooltipText = "<html>";

        while (groupsIter.hasNext())
        {
            UINotificationGroup group = groupsIter.next();

            tooltipText += "<b>" + group.getGroupDisplayName() + "</b><br/>";

            notificationCount += group.getUnreadNotificationsCount();

            int visibleNotifsPerGroup = 5;
            Iterator<UINotification> notifsIter = group.getUnreadNotifications();

            while (notifsIter.hasNext() && visibleNotifsPerGroup > 0)
            {
                UINotification missedCall = notifsIter.next();
                tooltipText += GuiUtils.formatTime(missedCall.getTime())
                        + "   " + missedCall.getDisplayName() + "<br/>";

                visibleNotifsPerGroup--;

                if (visibleNotifsPerGroup == 0 && notifsIter.hasNext())
                    tooltipText += GuiActivator.getResources()
                            .getI18NString("service.gui.MISSED_CALLS_MORE_TOOL_TIP",
                                    new String[]{ new Integer(
                                            notificationCount - 5).toString()});
            }
        }



        btnHistory.setToolTipText(tooltipText + "</html>");

        btnHistory.setBackground(new Color(200, 0, 0));
        btnHistory.setVerticalTextPosition(SwingConstants.TOP);

        Image iconImage = ImageLoader.getImage(notificationImage,
                new Integer(notificationCount).toString(), btnHistory);

 /*       if (isHistoryVisible)
        {

            btnHistory.setBgImage(ImageLoader.getImage(
                    pressedImage,
                    iconImage,
                    pressedImage.getWidth(null)/2
                            - notificationImage.getWidth(null)/2,
                    0));
        }
        else
        {
            btnHistory.setBgImage(ImageLoader.getImage(
                    historyImage,
                    iconImage,
                    historyImage.getWidth(null)/2
                            - notificationImage.getWidth(null)/2,
                    0));
        }
 */   }

    public void loadSkin() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
