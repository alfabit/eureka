package net.java.sip.communicator.plugin.eureka;

import net.java.sip.communicator.plugin.desktoputil.SIPCommFrame;
import net.java.sip.communicator.util.Logger;
import net.java.sip.communicator.util.skin.Skinnable;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Register screen into JITSI. Contains phone number field, e-mail field, first name field, last name field, country combobox
 * Created with IntelliJ IDEA.
 * User: Zenit
 * Date: 30.08.13
 * Time: 1:13
 * To change this template use File | Settings | File Templates.
 */
public class RegisterFrame extends SIPCommFrame  implements ServiceListener, ActionListener, KeyListener, Skinnable {

    private JTextField tfPhoneNumber;
    private JTextField tfEmail;
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfCountry;
    private JComboBox cbCountry;
    private JButton bEnter;
    private JButton bCancel;

    private String receivedCode;

    /**
     * The <tt>Logger</tt> used by this <tt>InitialAccountRegistrationFrame</tt>
     * for logging output.
     */

    private final Logger logger
            = Logger.getLogger(EurekaFrame.class);

//    private final Collection<AccountRegistrationPanel> registrationForms =
//            new Vector<AccountRegistrationPanel>();

    public RegisterFrame() {

        LoginFrame.registerFrame = this;

        setIconImage(Resources.getImage("plugin.eureka.APP_ICON").getImage());
        String title = Resources.getString("plugin.eureka.REGISTRATION_FRAME_TITLE");
        setTitle(title);

        setContentPane(new JLabel(Resources.getImage("plugin.eureka.FRAME_BACKGROUND")));
        setLayout(new FlowLayout());

//		==================================================

//		>> Logo-image <<

        JPanel panelLogo = new JPanel(new BorderLayout());
        panelLogo.setOpaque(false);

        ImageIcon imageIcon = Resources.getImage("plugin.eureka.SPLASH");
        JLabel labelLogo = new JLabel("", imageIcon, JLabel.CENTER);
        panelLogo.add( labelLogo, BorderLayout.CENTER );

        add(panelLogo);

//		==================================================

//      >> Registration form <<

        JPanel panelRegform = new JPanel();
        panelRegform.setLayout(new BoxLayout(panelRegform, BoxLayout.Y_AXIS));



//		>> Header - Text <<

        String headerMess = Resources.getString("plugin.eureka.REGISTRATION_HEADER_LABEL");

        JLabel lTextHeader = new JLabel(headerMess);
        lTextHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lTextHeader);

//		>> Phone Number panel <<

        JPanel panelPhone = new JPanel(new GridLayout(1, 2));

        JLabel lPhoneNumber = new JLabel(Resources.getString("plugin.eureka.PHONE_NUMBER"));
        lPhoneNumber.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 30));

        tfPhoneNumber = new JTextField(10);
        tfPhoneNumber.setName("tfPhoneNumber");
        tfPhoneNumber.addKeyListener(this);

        panelPhone.add(lPhoneNumber);
        panelPhone.add(tfPhoneNumber);

        panelPhone.setOpaque(false);
//		panelPhone.setBorder(BorderFactory.createMatteBorder(5, 10, 5, 5, new Color(0, 0, 0, 0)));
        panelPhone.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

//		>> Email panel <<

        JPanel panelEmail = new JPanel(new GridLayout(1, 2));
        JLabel lEmail = new JLabel(Resources.getString("plugin.eureka.EMAIL"));
        lEmail.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 30));

        tfEmail = new JTextField(10);
        tfEmail.setName("tfEmail");
        tfEmail.addKeyListener(this);

        panelEmail.add(lEmail);
        panelEmail.add(tfEmail);

        panelEmail.setOpaque(false);
//		panelEmail.setBorder(BorderFactory.createMatteBorder(5, 10, 10, 5, new Color(0, 0, 0, 0)));
        panelEmail.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

//		>> First Name Panel <<

        JPanel panelFirstName = new JPanel(new GridLayout(1, 2));

        JLabel lFirstName = new JLabel(Resources.getString("plugin.eureka.FIRST_NAME"));
        lFirstName.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 30));

        tfFirstName = new JTextField(10);
        tfFirstName.setName("tfFirstName");
       tfFirstName.addKeyListener(this);

        panelFirstName.add(lFirstName);
        panelFirstName.add(tfFirstName);

        panelFirstName.setOpaque(false);
//		panelFirstName.setBorder(BorderFactory.createMatteBorder(5, 10, 10, 5, new Color(0, 0, 0, 0)));
        panelFirstName.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

//		>> Last Name Panel <<

        JPanel panelLastName = new JPanel(new GridLayout(1, 2));

        JLabel lLastName = new JLabel(Resources.getString("plugin.eureka.LAST_NAME"));
        lLastName.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 30));

        tfLastName = new JTextField(10);
        tfLastName.setName("tfLastName");
        tfLastName.addKeyListener(this);

        panelLastName.add(lLastName);
        panelLastName.add(tfLastName);

        panelLastName.setOpaque(false);
//		panelCode.setBorder(BorderFactory.createMatteBorder(5, 10, 10, 5, new Color(0, 0, 0, 0)));
        panelLastName.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

//		>> Country panel <<

        JPanel panelCountry = new JPanel(new GridLayout(1, 2));

        JLabel lCountry = new JLabel((Resources.getString("plugin.eureka.COUNTRY")));
        lCountry.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 30));

        String[] locales = Locale.getISOCountries();
        String[] countries = new String[locales.length];

        for (int i = 0; i < locales.length; i++) {

            Locale obj = new Locale("", locales[i]);

            countries[i] = obj.getDisplayCountry();

        }

        cbCountry = new JComboBox(countries);
        cbCountry.setName("tfCountry");
        cbCountry.addKeyListener(this);

        panelCountry.add(lCountry);
        panelCountry.add(cbCountry);

/*		tfCountry = new JTextField(10);
		tfCountry.setName("tfCountry");
		tfCountry.addKeyListener(this);

		panelCountry.add(lCountry);
		panelCountry.add(tfCountry);
*/
        panelCountry.setOpaque(false);
        // panelCode.setBorder(BorderFactory.createMatteBorder(5, 10, 10, 5, new
        // Color(0, 0, 0, 0)));
        panelCountry.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));



//		>> Footer - Text <<
        String footerMess = (Resources.getString("plugin.eureka.REGISTRATION_FOOTER_LABEL"));
        JLabel lTextFooter = new JLabel(footerMess);

        lTextFooter.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));


        panelRegform.add(panelPhone);
        panelRegform.add(panelEmail);
        panelRegform.add(panelFirstName);
        panelRegform.add(panelLastName);
        panelRegform.add(panelCountry);

        panelRegform.setBackground( new Color(0, 0, 0, 30) );
        panelRegform.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));


        add(panelRegform);

        add(lTextFooter);

//		==================================================

//		>> Button panel <<

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setOpaque(false);

        bEnter = new JButton(Resources.getString("plugin.eureka.SEND_BUTTON"));
        bEnter.setName("bSend");
        bEnter.setActionCommand("bSend");
        bEnter.addActionListener(this);
        bEnter.addKeyListener(this);

        bCancel = new JButton(Resources.getString("plugin.eureka.CANCEL_BUTTON"));
        bCancel.setName("bCancel");
        bCancel.setActionCommand("bCancel");
        bCancel.addActionListener(this);
        bCancel.addKeyListener(this);

        panelButtons.add(bEnter);
        panelButtons.setBorder(BorderFactory.createEmptyBorder(15, 180, 0, 5));
        panelButtons.add(bCancel);

        add(panelButtons);

		setResizable(false);
    }

    @Override
    protected void windowClosing(WindowEvent e) {
        super.windowClosing(e);    //To change body of overridden methods use File | Settings | File Templates.
        LoginFrame.loginFrame.setEnabled(true);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginFrame.loginFrame.toFront();
                LoginFrame.loginFrame.repaint();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {

        if("bSend".equals(e.getActionCommand()))
            buttonEnterAction();
        else if("bCancel".equals(e.getActionCommand()))
            buttonCancelAction();
    }

    /**
     * Transition to Confirmation Code window if all fields are filled correctly
     */
    private void buttonEnterAction() {

        if(!isPhoneNumberValid())
            return;

        if(!isEmailValid())
            return;

        if(0==tfFirstName.getText().length()){
            tfFirstName.setBackground(new Color(255, 180, 180));
            JOptionPane.showMessageDialog(this, Resources.getString("plugin.eureka.REGISTRATION_NO_FIRST_NAME_LABEL"));
            return;
        }
        else
            tfFirstName.setBackground(Color.WHITE);

        if(0==tfLastName.getText().length()){
            tfLastName.setBackground(new Color(255, 180, 180));
            JOptionPane.showMessageDialog(this, Resources.getString("plugin.eureka.REGISTRATION_NO_LAST_NAME_LABEL"));
            return;
        }
        else
            tfLastName.setBackground(Color.WHITE);
/*
		if(0==tfCountry.getText().length()){
			tfCountry.setBackground(new Color(255, 180, 180));
			JOptionPane.showMessageDialog(this, "Не указана страна");
			return;
		}
		else
			tfCountry.setBackground(Color.WHITE);
*/

        LoginFrame.validateRegisterFrame = new ValidateRegisterFrame();

        LoginFrame.validateRegisterFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );

        LoginFrame.validateRegisterFrame.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2 - 200,
                Toolkit.getDefaultToolkit().getScreenSize().height/2 - 175,
                400, 350);

        LoginFrame.validateRegisterFrame.setVisible(true);

//		setVisible(false);
//		dispose();

    }

    /**
     * Closes current window
     */
    private void buttonCancelAction() {

        setVisible(false);
        dispose();
        LoginFrame.loginFrame.setEnabled(true);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginFrame.loginFrame.toFront();
                LoginFrame.loginFrame.repaint();
            }
        });
    }

    /**
     * Checks phone field is not empty and has only digits
     * @return total result of verification
     */
    private boolean isPhoneNumberValid(){

        if(tfPhoneNumber.getText().length()==0){
            tfPhoneNumber.setBackground(new Color(255, 180, 180));
            showMessageDialog(Resources.getString("plugin.eureka.REGISTRATION_NO_PHONE_LABEL"));
            return false;
        }
        else if(!hasOnlyDigits(tfPhoneNumber.getText())){
            tfPhoneNumber.setBackground(new Color(255, 180, 180));
            showMessageDialog(Resources.getString("plugin.eureka.REGISTRATION_INCORRECT_PHONE_LABEL"));
            return false;
        }
        else
            tfPhoneNumber.setBackground(Color.WHITE);
        return true;
    }

    /**
     * Checks email field is not empty and has only '@'-sign
     * @return total result of verification
     */
    private boolean isEmailValid(){

        if(tfEmail.getText().length()==0){
            tfEmail.setBackground(new Color(255, 180, 180));
            showMessageDialog(Resources.getString("plugin.eureka.REGISTRATION_NO_EMAIL_LABEL"));
            return false;
        }
        else if(!tfEmail.getText().contains("@")){
            tfEmail.setBackground(new Color(255, 180, 180));
            showMessageDialog(Resources.getString("plugin.eureka.REGISTRATION_INCORRECT_EMAIL_LABEL"));
            return false;
        }
//		else if(!receivedCode.equals(tfEmail.getText())){
//			tfEmail.setBackground(new Color(255, 180, 180));
//			showMessageDialog("Your have entered invaled code.\nPlease, enter it more carefully");
//			return false;
//		}
        else
            tfEmail.setBackground(Color.WHITE);
        return true;
    }

    private void showMessageDialog(String mess) {
        JOptionPane.showMessageDialog(this, mess);
    }

    private boolean hasOnlyDigits(String number){


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


    public void keyTyped(KeyEvent e) {

        JComponent comp = (JComponent) e.getSource();

        if(  !( "bSend".equals(comp.getName())  ||   "bCancel".equals(comp.getName())  )  ){
            JTextField tfAction = (JTextField) e.getSource();

            if(tfAction.getText().length()==0)
                tfAction.setBackground(Color.WHITE);
        }
    }

    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_ENTER){

            JComponent comp = (JComponent) e.getSource();
            String componentName = comp.getName();


            if("tfPhoneNumber".equals(componentName))
                tfEmail.requestFocus();
            else if("tfEmail".equals(componentName))
                bEnter.requestFocus();


            else if("bSend".equals(componentName))
                buttonEnterAction();
            else if("bCancel".equals(componentName))
                buttonCancelAction();

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
