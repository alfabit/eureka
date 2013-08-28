/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.gui.main.chatroomslist;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import net.java.sip.communicator.impl.gui.*;
import net.java.sip.communicator.impl.gui.main.*;
import net.java.sip.communicator.impl.gui.main.chat.*;
import net.java.sip.communicator.impl.gui.main.chat.conference.*;
import net.java.sip.communicator.plugin.desktoputil.*;
import net.java.sip.communicator.plugin.desktoputil.SwingWorker;
import net.java.sip.communicator.service.protocol.*;
import net.java.sip.communicator.util.*;

import org.jitsi.util.*;

/**
 * The dialog containing a list of all chat rooms of the user and
 * also interface for create a new chat room, join a chat room, search all
 * chat rooms, etc.
 *
 * @author Yana Stamcheva
 * @author Lyubomir Marinov
 * @author Damian Minkov
 */
public class ChatRoomTableDialog
    extends SIPCommDialog
    implements ActionListener
{
    /**
     * The global/shared <code>ChatRoomTableDialog</code> currently showing.
     */
    private static ChatRoomTableDialog chatRoomTableDialog;

    /**
     * A JComboBox which will allow to select an account for joining a room.
     */
    private JComboBox providersCombo;

    /**
     * An editable JComboBox which will allow to set a room name, and gives
     * suggestions regarding to its content.
     */
    private JComboBox roomsCombo = null;

    /**
     * The add chat room button.
     */
    private final JButton addButton = new JButton("+");

    /**
     * The remove chat room button.
     */
    private final JButton removeButton = new JButton("-");

    /**
     * The ok button.
     */
    private final JButton okButton
        = new JButton(
                GuiActivator.getResources().getI18NString("service.gui.JOIN"));

    /**
     * The cancel button.
     */
    private final JButton cancelButton
        = new JButton(
                GuiActivator.getResources().getI18NString(
                        "service.gui.CANCEL"));

    /**
     * The editor for the chat room name.
     */
    private JTextField editor = null;

    /**
     * The available chat rooms list.
     */
    private ChatRoomTableUI chatRoomsTableUI = null;

    private final ChatRoomList chatRoomList
        = GuiActivator.getUIService().getConferenceChatManager()
                .getChatRoomList();

    /**
     * The <tt>ChatRoomList.ChatRoomProviderWrapperListener</tt> instance which
     * has been registered with {@link #chatRoomList} and which is to be
     * unregistered when this instance is disposed in order to prevent this
     * instance from leaking.
     */
    private final ChatRoomList.ChatRoomProviderWrapperListener
        chatRoomProviderWrapperListener
            = new ChatRoomList.ChatRoomProviderWrapperListener()
            {
                public void chatRoomProviderWrapperAdded(
                    ChatRoomProviderWrapper provider)
                {
                    providersCombo.addItem(provider);
                }

                public void chatRoomProviderWrapperRemoved(
                    ChatRoomProviderWrapper provider)
                {
                    providersCombo.removeItem(provider);
                }
            };

    /**
     * Shows a <code>ChatRoomTableDialog</code> creating it first if necessary.
     * The shown instance is shared in order to prevent displaying multiple
     * instances of one and the same <code>ChatRoomTableDialog</code>.
     */
    public static void showChatRoomTableDialog()
    {
        if (chatRoomTableDialog == null)
        {
            chatRoomTableDialog
                = new ChatRoomTableDialog(
                        GuiActivator.getUIService().getMainFrame());

            /*
             * When the global/shared ChatRoomTableDialog closes, don't keep a
             * reference to it and let it be garbage-collected.
             */
            chatRoomTableDialog.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosed(WindowEvent e)
                {
                    if (chatRoomTableDialog == e.getWindow())
                        chatRoomTableDialog = null;
                }
            });
        }
        chatRoomTableDialog.setVisible(true);
    }

    /**
     * Creates an instance of <tt>MyChatRoomsDialog</tt> by specifying the
     * parent window.
     *
     * @param parentWindow the parent window of this dialog
     */
    public ChatRoomTableDialog(MainFrame parentWindow)
    {
        super(parentWindow);

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        this.setTitle(GuiActivator.getResources()
            .getI18NString("service.gui.MY_CHAT_ROOMS_TITLE"));

        this.init();
    }

    /**
     * Initializes this chat room dialog.
     */
    private void init()
    {
        this.getContentPane().setLayout(new BorderLayout(5,5));

        JPanel northPanel = new TransparentPanel(new BorderLayout(5, 5));
        northPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        JPanel labels = new TransparentPanel(new GridLayout(2, 2, 5, 5));

        labels.add(new JLabel(GuiActivator.getResources()
            .getI18NString("service.gui.ACCOUNT")));
        labels.add(new JLabel(GuiActivator.getResources()
            .getI18NString("service.gui.ROOM_NAME")));

        JPanel valuesPanel = new TransparentPanel(new GridLayout(2, 2, 5, 5));
        providersCombo = createProvidersCombobox();

        roomsCombo = new JComboBox();
        roomsCombo.setEditable(true);
        roomsCombo.setPreferredSize(providersCombo.getPreferredSize());
        editor = ((JTextField)roomsCombo.getEditor().getEditorComponent());

        // when provider is changed we load providers rooms list
        // so we can show them in the combobox below
        providersCombo.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e)
            {
                if(e.getStateChange() == ItemEvent.SELECTED)
                    loadProviderRooms();
            }
        });

        // if a room is selected enable buttons
        roomsCombo.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e)
            {
                if(e.getStateChange() == ItemEvent.SELECTED
                    && roomsCombo.getSelectedIndex() > -1)
                {
                    okButton.setEnabled(true);
                    addButton.setEnabled(true);
                }
                else if((roomsCombo.getSelectedIndex() == -1
                        || e.getStateChange() == ItemEvent.DESELECTED)
                        && editor.getText().trim().length() <= 0)
                {
                    okButton.setEnabled(false);
                    addButton.setEnabled(false);
                }
            }
        });

        valuesPanel.add(providersCombo);
        valuesPanel.add(roomsCombo);

        northPanel.add(labels, BorderLayout.WEST);
        northPanel.add(valuesPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new TransparentPanel(new BorderLayout(5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));
        JPanel westButtonPanel = new TransparentPanel();
        JPanel eastButtonPanel = new TransparentPanel();

        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        addButton.setToolTipText(GuiActivator.getResources()
            .getI18NString("service.gui.CHAT_ROOM_SAVE_BUTTON_TOOLTIP"));
        removeButton.setToolTipText(GuiActivator.getResources()
            .getI18NString("service.gui.CHAT_ROOM_REMOVE_BUTTON_TOOLTIP"));
        okButton.setToolTipText(GuiActivator.getResources()
            .getI18NString("service.gui.JOIN_CHAT_ROOM"));

        addButton.setEnabled(false);
        removeButton.setEnabled(false);

        westButtonPanel.add(addButton);
        westButtonPanel.add(removeButton);
        eastButtonPanel.add(cancelButton);
        eastButtonPanel.add(okButton);

        buttonPanel.add(westButtonPanel, BorderLayout.WEST);
        buttonPanel.add(eastButtonPanel, BorderLayout.EAST);

        chatRoomsTableUI = new ChatRoomTableUI(this);
        chatRoomsTableUI.setOpaque(false);
        chatRoomsTableUI.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // this controls and the whole dialog width
        northPanel.setPreferredSize(new Dimension(600, 80));

        this.getContentPane().add(northPanel, BorderLayout.NORTH);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        this.getContentPane().add(chatRoomsTableUI, BorderLayout.CENTER);

        loadProviderRooms();

        // when we are typing we clear any selection in the available and saved
        // rooms
        editor.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e)
            {}

            public void keyPressed(KeyEvent e)
            {}

            public void keyReleased(KeyEvent e)
            {
                chatRoomsTableUI.clearSelection();

                if(editor.getText().trim().length() > 0)
                {
                    okButton.setEnabled(true);
                    addButton.setEnabled(true);
                }
                else
                {
                    okButton.setEnabled(false);
                    addButton.setEnabled(false);
                }
            }
        });
        // when we select a room from the available ones we clear anything
        // typed for the room name and set the room we selected
        chatRoomsTableUI.addSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e)
            {
                if(!e.getValueIsAdjusting())
                {
                    ChatRoomWrapper room = chatRoomsTableUI.getSelectedRoom();

                    if(room != null)
                    {
                        editor.setText(room.getChatRoomName());
                        providersCombo.setSelectedItem(room.getParentProvider());
                        okButton.setEnabled(true);
                        removeButton.setEnabled(true);
                    }
                    else
                        removeButton.setEnabled(false);
                }
            }
        });

        //register listener to listen for newly added chat room providers
        // and for removed ones
        chatRoomList.addChatRoomProviderWrapperListener(
                chatRoomProviderWrapperListener);
    }

    /**
     * Creates the providers combobox and filling its content.
     * @return
     */
    private JComboBox createProvidersCombobox()
    {
        Iterator<ChatRoomProviderWrapper> providers
            = chatRoomList.getChatRoomProviders();
        JComboBox chatRoomProvidersCombobox = new JComboBox();

        while (providers.hasNext())
            chatRoomProvidersCombobox.addItem(providers.next());

        chatRoomProvidersCombobox.setRenderer(new ChatRoomProviderRenderer());

        return chatRoomProvidersCombobox;
    }

    /**
     * Handles <tt>ActionEvent</tt>s triggered by a button click.
     * @param e the action event.
     */
    public void actionPerformed(ActionEvent e)
    {
        JButton sourceButton = (JButton) e.getSource();
        if(sourceButton.equals(addButton))
        {
            String chatRoomName = editor.getText();

            if(StringUtils.isNullOrEmpty(chatRoomName))
                return;

            ChatRoomWrapper chatRoomWrapper =
                GuiActivator
                    .getUIService()
                    .getConferenceChatManager()
                    .createChatRoom(chatRoomName.trim(),
                        getSelectedProvider().getProtocolProvider(),
                        new ArrayList<String>(), "", false, true);

            chatRoomWrapper.getNickname();

        }
        else if(sourceButton.equals(removeButton))
        {
            chatRoomsTableUI.removeSelectedRoom();
        }
        else if(sourceButton.equals(okButton))
        {
            ChatRoomWrapper selectedRoom = chatRoomsTableUI.getSelectedRoom();

            if(selectedRoom == null)
            {
                if(editor.getText() != null
                        && editor.getText().trim().length() > 0)
                {
                    ChatRoomWrapper chatRoomWrapper =
                    GuiActivator.getUIService().getConferenceChatManager()
                        .createChatRoom(
                            editor.getText().trim(),
                            getSelectedProvider().getProtocolProvider(),
                            new ArrayList<String>(),
                            "",
                            false,
                            false);

                    String nickName = chatRoomWrapper.getNickname();
                    
                    if(nickName == null)
                        return;

                    GuiActivator.getUIService().getConferenceChatManager()
                        .joinChatRoom(chatRoomWrapper, nickName, null);

                    ChatWindowManager chatWindowManager =
                        GuiActivator.getUIService().getChatWindowManager();
                    ChatPanel chatPanel =
                        chatWindowManager.getMultiChat(chatRoomWrapper, true);

                    chatWindowManager.openChat(chatPanel, true);
                }
            }
            else
            {
                if(selectedRoom.getChatRoom() != null)
                {
                    if (!selectedRoom.getChatRoom().isJoined())
                    {
                        String savedNick =
                            ConfigurationUtils.getChatRoomProperty(
                                selectedRoom.getParentProvider()
                                    .getProtocolProvider(), selectedRoom
                                    .getChatRoomID(), "userNickName");
                        if (savedNick == null)
                        {
                            
                           
                            savedNick = selectedRoom.getNickname();
                            if(savedNick == null)
                                return;
                        }
                        GuiActivator.getUIService()
                            .getConferenceChatManager()
                            .joinChatRoom(selectedRoom, savedNick, null);
                    }
                    else
                        chatRoomsTableUI.openChatForSelection();
                }
                else
                {
                    // this is not a server persistent room we must create it
                    // and join
                    ChatRoomWrapper chatRoomWrapper =
                    GuiActivator.getUIService().getConferenceChatManager()
                        .createChatRoom(
                            selectedRoom.getChatRoomName(),
                            getSelectedProvider().getProtocolProvider(),
                            new ArrayList<String>(),
                            "",
                            false,
                            true);

                    String savedNick =
                        ConfigurationUtils.getChatRoomProperty(
                            chatRoomWrapper.getParentProvider()
                                .getProtocolProvider(), chatRoomWrapper
                                .getChatRoomID(), "userNickName");

                    if (savedNick == null)
                    {
                        savedNick = chatRoomWrapper.getNickname();
                        if(savedNick == null)
                            return;
                    }
                    
                    GuiActivator.getUIService().getConferenceChatManager()
                        .joinChatRoom(chatRoomWrapper,savedNick,null);

                }
            }

            // in all cases we dispose this dialog
            dispose();
        }
        else if(sourceButton.equals(cancelButton))
        {
            dispose();
        }
    }

    @Override
    protected void close(boolean isEscaped)
    {
        dispose();
    }

    /**
     * Releases the resources allocated by this instance throughout its
     * lifetime.
     */
    @Override
    public void dispose()
    {
        if (chatRoomTableDialog == this)
            chatRoomTableDialog = null;

        chatRoomList.removeChatRoomProviderWrapperListener(
                chatRoomProviderWrapperListener);
        chatRoomsTableUI.dispose();

        super.dispose();
    }

    /**
     * Loads the rooms hosted on the selected provider.
     * Loads it in different thread so it won't block the caller.
     */
    public void loadProviderRooms()
    {
        okButton.setEnabled(false);
        roomsCombo.setEnabled(false);

        new LoadProvidersWorker().start();
    }

    /**
     * Returns the selected provider in the providers combo box.
     *
     * @return the selected provider
     */
    public ChatRoomProviderWrapper getSelectedProvider()
    {
        return (ChatRoomProviderWrapper)providersCombo.getSelectedItem();
    }

    /**
     * Cell renderer for the providers combo box: displays the protocol name
     * with its associated icon.
     */
    class ChatRoomProviderRenderer
        extends JLabel
        implements ListCellRenderer
    {
        /**
         * The renderer.
         */
        public ChatRoomProviderRenderer()
        {
            setOpaque(true);
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
            this.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        }

        public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus)
        {
            ChatRoomProviderWrapper provider = (ChatRoomProviderWrapper)value;

            if(provider == null)
                return this;

            if (isSelected)
            {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else
            {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setText(provider.getProtocolProvider()
                .getAccountID().getAccountAddress());

            setIcon(new ImageIcon(provider.getProtocolProvider()
                    .getProtocolIcon().getIcon(ProtocolIcon.ICON_SIZE_16x16)));

            return this;
        }
    }

    /**
     * SwingWorker that will load rooms list and show them in the ui.
     */
    private class LoadProvidersWorker
        extends SwingWorker
    {
        /**
         * List of rooms.
         */
        private List<String> rooms;

        /**
         * Worker thread.
         * @return
         * @throws Exception
         */
        @Override
        protected Object construct()
            throws
            Exception
        {
            rooms = GuiActivator.getUIService()
                .getConferenceChatManager()
                    .getExistingChatRooms(getSelectedProvider());

            return null;
        }

        /**
         * Called on the event dispatching thread (not on the worker thread)
         * after the <code>construct</code> method has returned.
         */
        @Override
        protected void finished()
        {
            roomsCombo.removeAllItems();

            // if there is no room list coming from provider
            if(rooms == null)
            {
                roomsCombo.setEnabled(true);
                //okButton.setEnabled(true);
                return;
            }

            Collections.sort(rooms);

            for(String room : rooms)
                roomsCombo.addItem(room);

            // select nothing
            roomsCombo.setSelectedIndex(-1);

            roomsCombo.setEnabled(true);
        }
    }
}
