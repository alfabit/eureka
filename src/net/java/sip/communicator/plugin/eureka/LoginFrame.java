package net.java.sip.communicator.plugin.eureka;

import net.java.sip.communicator.service.gui.ExportedWindow;
import net.java.sip.communicator.service.gui.UIService;
import net.java.sip.communicator.util.Logger;
import net.java.sip.communicator.util.skin.Skinnable;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

/**
 * Login screen into JITSI. Contains login (Eureka© phone number) field, password field, register function, get settings function
 * Created with IntelliJ IDEA.
 * User: Zenit
 * Date: 28.08.13
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 */
public class LoginFrame extends JDialog implements ServiceListener, ActionListener, KeyListener, Skinnable {

    static JDialog loginFrame;
    static JFrame registerFrame, validateRegisterFrame;

    private JTextField tfLogin;
    private JTextField tfPass;
    private JButton bEnter;
    private JButton bClose;

    private String receivedCode;

    /**
     * The <tt>Logger</tt> used by this <tt>InitialAccountRegistrationFrame</tt>
     * for logging output.
     */
    private final Logger logger
            = Logger.getLogger(EurekaFrame.class);
    private UIService uiService;

//    private final Collection<AccountRegistrationPanel> registrationForms =
//            new Vector<AccountRegistrationPanel>();

    public LoginFrame(UIService uiService)/* throws HeadlessException, IOException*/ {
        setModal(true);

        this.uiService = uiService;
        final ExportedWindow mainWindow = this.uiService.getExportedWindow(ExportedWindow.MAIN_WINDOW);
        if (mainWindow != null) {
            mainWindow.setVisible(false);
        }

        this.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == WindowEvent.WINDOW_GAINED_FOCUS) {
                    if (mainWindow.isVisible()) {
                        mainWindow.setVisible(false);
                    }
                }
            }
        });


        loginFrame = this;


        setIconImage(Resources.getImage("plugin.eureka.APP_ICON").getImage());
        String title = Resources.getString("plugin.eureka.LOGIN_FRAME_TITLE");
        setTitle(title);

        setContentPane(new JLabel(Resources.getImage("plugin.eureka.FRAME_BACKGROUND")));
        setLayout(new FlowLayout());

//		==================================================

//		>> Logo-image <<

        JPanel panelLogo = new JPanel(new BorderLayout());
        panelLogo.setOpaque(false);

        ImageIcon imageIcon = Resources.getImage("plugin.eureka.SPLASH");
        JLabel labelLogo = new JLabel("", imageIcon, JLabel.CENTER);
        panelLogo.add(labelLogo, BorderLayout.CENTER);

        add(panelLogo);

//		==================================================

//        >> Login Form <<

        JPanel panelLoginForm = new JPanel();
        panelLoginForm.setLayout(new BoxLayout(panelLoginForm, BoxLayout.Y_AXIS));

//		>> Login panel <<

        JPanel panelLogin = new JPanel(new GridLayout(1, 2));

        JLabel lLogin = new JLabel(Resources.getString("plugin.eureka.PHONE_NUMBER"));
        lLogin.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 30));

        tfLogin = new JTextField(10);
        tfLogin.setName("tfLogin");
        tfLogin.addKeyListener(this);

        panelLogin.add(lLogin);
        panelLogin.add(tfLogin);

        panelLogin.setOpaque(false);
//		  panelPhone.setBorder(BorderFactory.createMatteBorder(5, 10, 5, 5, new Color(0, 0, 0, 0)));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

//		>> Password panel <<

        JPanel panelPass = new JPanel(new GridLayout(1, 2));

        JLabel lPass = new JLabel(Resources.getString("plugin.eureka.PASSWORD"));
        lPass.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 30));

        tfPass = new JTextField(10);
        tfPass.setName("tfPass");
        tfPass.addKeyListener(this);

        panelPass.add(lPass);
        panelPass.add(tfPass);

        panelPass.setOpaque(false);
//		  panelCode.setBorder(BorderFactory.createMatteBorder(5, 10, 10, 5, new Color(0, 0, 0, 0)));
        panelPass.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

//		==================================================

//		>> Register link panel <<

        JPanel panelRegisterProposal = new JPanel(new FlowLayout(FlowLayout.RIGHT));

//		>> Register link <<

        JButton lRegisterProposal = new JButton(Resources.getString("plugin.eureka.LOGIN_FRAME_NOT_REGISTERED"));
        lRegisterProposal.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        lRegisterProposal.setFocusPainted(false);
        lRegisterProposal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JButton but = (JButton) evt.getSource();
                but.setForeground(new Color(150, 90, 90));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                JButton but = (JButton) evt.getSource();
                but.setForeground(new Color(60, 60, 60));
            }
        });
        lRegisterProposal.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));
        lRegisterProposal.setContentAreaFilled(false);
        lRegisterProposal.setBorderPainted(true);
        lRegisterProposal.setOpaque(true);
        lRegisterProposal.setBackground(new Color(0, 0, 0, 0));

        lRegisterProposal.setActionCommand("buttonRegisterProposal");
        lRegisterProposal.addActionListener(this);
        panelRegisterProposal.add(lRegisterProposal);

        panelRegisterProposal.setBackground(new Color(0, 0, 0, 30));
//		  panelRegisterProposal.setBorder(BorderFactory.createMatteBorder(5, 10, 5, 5, new Color(0, 0, 0, 0)));
        panelRegisterProposal.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panelLoginForm.add(panelLogin);
        panelLoginForm.add(panelPass);
        panelLoginForm.add(panelRegisterProposal);

        panelLoginForm.setBackground(new Color(0, 0, 0, 30));
        panelLoginForm.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        add(panelLoginForm);

//		==================================================

//		>> Button panel <<

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setOpaque(false);

        bEnter = new JButton(Resources.getString("plugin.eureka.ENTER_BUTTON"));
        bEnter.setName("bEnter");
        bEnter.setActionCommand("bEnter");
        bEnter.addActionListener(this);
        bEnter.addKeyListener(this);

        bClose = new JButton(Resources.getString("plugin.eureka.CLOSE_BUTTON"));
        bClose.setName("bClose");
        bClose.setActionCommand("bClose");
        bClose.addActionListener(this);
        bClose.addKeyListener(this);

        panelButtons.add(bEnter);
        panelButtons.setBorder(BorderFactory.createEmptyBorder(15, 180, 0, 5));
        panelButtons.add(bClose);

        add(panelButtons);

        setResizable(false);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginFrame.loginFrame.toFront();
                LoginFrame.loginFrame.repaint();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {

        if ("buttonAutoSetts".equals(e.getActionCommand()))
            buttonAutoSettsAction();
        else if ("buttonRegisterProposal".equals(e.getActionCommand()))
            buttonRegisterProposal();
        else if ("bEnter".equals(e.getActionCommand()))
            buttonEnterAction();
        else if ("bClose".equals(e.getActionCommand()))
            buttonExitAction();
    }

    /**
     * shows "use AUTHOSETTINGS" window
     */
    private void buttonAutoSettsAction() {
//		TODO: SET WHEN MAIN FUNCIONAL WILL BE DEVELOPING
        JOptionPane.showMessageDialog(this, Resources.getString("plugin.eureka.TEMP_MESS1"));
    }

    /**
     * shows Registration window. Current window does not close
     */
    private void buttonRegisterProposal() {

        setEnabled(false);
        LoginFrame.registerFrame = new RegisterFrame();
        LoginFrame.registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        LoginFrame.registerFrame.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 325,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 225,
                650, 450);
        LoginFrame.registerFrame.setVisible(true);

    }

    /**
     * Transition to Main window if all fields are filled correctly
     */
    private void buttonEnterAction() {
//		TODO: SET WHEN MAIN FUNCIONAL WILL BE DEVELOPING

        if (isLoginValid() && isPassValid())
            JOptionPane.showMessageDialog(this, Resources.getString("plugin.eureka.TEMP_MESS2"));
//			setVisible(false);
//			dispose();
        else
            return;
    }

    /**
     * Exit from application
     */
    private void buttonExitAction() {
//		TODO: SET WHEN MAIN FUNCIONAL WILL BE DEVELOPING
        JOptionPane.showMessageDialog(this, Resources.getString("plugin.eureka.TEMP_MESS3"));
        setVisible(false);
        dispose();
    }

    /**
     * Checks phone field is not empty and has only digits
     *
     * @return total result of verification
     */
    private boolean isLoginValid() {

        if (tfLogin.getText().length() == 0) {
            tfLogin.setBackground(new Color(255, 180, 180));
            showMessageDialog(Resources.getString("plugin.eureka.LOGIN_NO_PHONE_LABEL"));
            return false;
        } else if (!hasOnlyDigits(tfLogin.getText())) {
            tfLogin.setBackground(new Color(255, 180, 180));
            showMessageDialog(Resources.getString("plugin.eureka.LOGIN_INCORRECT_PHONE_LABEL"));
            return false;
        } else
            tfLogin.setBackground(Color.WHITE);
        return true;
    }

    /**
     * Checks password field is not empty
     *
     * @return total result of verification
     */
    private boolean isPassValid() {

        if (tfPass.getText().length() == 0) {
            tfPass.setBackground(new Color(255, 180, 180));
            showMessageDialog(Resources.getString("plugin.eureka.LOGIN_NO_PASS_LABEL"));
            return false;
        }
//        else if(!receivedCode.equals(tfPass.getText())){
//            tfPass.setBackground(new Color(255, 180, 180));
//            showMessageDialog(Resources.getString("plugin.eureka.LOGIN_INCORRECT_PASS_LABEL"));
//            return false;
//        }
        else
            tfPass.setBackground(Color.WHITE);
        return true;
    }

    private void showMessageDialog(String mess) {
        JOptionPane.showMessageDialog(this, mess);
    }

    private boolean hasOnlyDigits(String number) {


        if (Pattern.matches("[0-9]+", number)) {
            return true;
        }

//    	    if (Pattern.compile("[a-z]").matcher(number).find()) {
//    	        combinations = combinations + 26;
//    	    }
//
//    	    if (Pattern.compile("[A-Z]").matcher(number).find()) {
//    	        combinations = combinations + 26;
//    	    }

        return false;
    }

    private String receiveCode() {
        char codeChars[] = new char[9];

        for (int i = 0; i < codeChars.length; i++) {

            int randValue = 0;

            do {
                randValue = (int) ((((int) 'Z') - ((int) '0')) * Math.random() + ((int) '0'));
            }
            while (i != 5 && randValue > ((int) '9') && randValue < ((int) 'A'));

            codeChars[i] = (char) randValue;

        }

        codeChars[5] = '-';
        receivedCode = String.valueOf(codeChars);
        return receivedCode;
    }

    public void keyTyped(KeyEvent e) {
        JComponent comp = (JComponent) e.getSource();

        if (!("bEnter".equals(comp.getName()) || "bClose".equals(comp.getName()))) {
            JTextField tfAction = (JTextField) e.getSource();

            if (tfAction.getText().length() == 0)
                tfAction.setBackground(Color.WHITE);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            JComponent comp = (JComponent) e.getSource();
            String componentName = comp.getName();


            if ("tfPhoneNumber".equals(componentName))
                tfPass.requestFocus();
            else if ("tfConfirmCode".equals(componentName))
                bEnter.requestFocus();


            else if ("bEnter".equals(componentName))
                buttonEnterAction();
            else if ("bClose".equals(componentName))
                buttonExitAction();

        }
    }

    public void keyReleased(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void serviceChanged(ServiceEvent serviceEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void loadSkin() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
