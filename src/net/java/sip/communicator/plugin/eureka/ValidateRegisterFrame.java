package net.java.sip.communicator.plugin.eureka;

import net.java.sip.communicator.plugin.desktoputil.SIPCommFrame;
import net.java.sip.communicator.util.Logger;
import net.java.sip.communicator.util.skin.Skinnable;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

/**
 * Validation screen of registration into JITSI. Contains phone number field, validation code field
 * Created with IntelliJ IDEA.
 * User: Zenit
 * Date: 30.08.13
 * Time: 8:52
 * To change this template use File | Settings | File Templates.
 */
public class ValidateRegisterFrame extends SIPCommFrame implements ServiceListener, ActionListener, KeyListener, Skinnable {

    private JTextField tfPhoneNumber;
    private JTextField tfConfirmCode;
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

    public ValidateRegisterFrame() {

        setIconImage(Resources.getImage("plugin.eureka.APP_ICON").getImage());
        String title = Resources.getString("plugin.eureka.VALIDATION_FRAME_TITLE");
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

//      >> Validation form <<

        JPanel panelValidForm = new JPanel();
        panelValidForm.setLayout(new BoxLayout(panelValidForm, BoxLayout.Y_AXIS));

//		>> Header - Text <<

        String headerMess = Resources.getString("plugin.eureka.VALIDATION_HEADER_LABEL");

        JLabel lTextHeader = new JLabel(headerMess);
        lTextHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lTextHeader);

//		>> Phone Number panel <<

        JPanel panelPhone = new JPanel(new GridLayout(1,2));
        JLabel lPhoneNumber = new JLabel(Resources.getString("plugin.eureka.PHONE_NUMBER"));
        lPhoneNumber.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 30));

        tfPhoneNumber = new JTextField(10);
        tfPhoneNumber.setName("tfPhoneNumber");
        tfPhoneNumber.addKeyListener(this);

        panelPhone.add(lPhoneNumber);
        panelPhone.add(tfPhoneNumber);

        panelPhone.setOpaque(false);
//		  panelPhone.setBorder(BorderFactory.createMatteBorder(5, 10, 5, 5, new Color(0, 0, 0, 0)));
        panelPhone.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

//		>> Code panel <<

        JPanel panelCode = new JPanel(new GridLayout(1,2));

        JLabel lConfirmCode = new JLabel(Resources.getString("plugin.eureka.CODE"));
        lConfirmCode.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 30));

        tfConfirmCode = new JTextField(10);
        tfConfirmCode.setName("tfConfirmCode");
        tfConfirmCode.addKeyListener(this);

        panelCode.add(lConfirmCode);
        panelCode.add(tfConfirmCode);

        panelCode.setOpaque(false);
//		  panelCode.setBorder(BorderFactory.createMatteBorder(5, 10, 10, 5, new Color(0, 0, 0, 0)));
        panelCode.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

//			>> Footer - Text <<

        String footerMess = (Resources.getString("plugin.eureka.VALIDATION_FOOTER_LABEL"));
        JLabel lTextFooter = new JLabel(footerMess);
        lTextFooter.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        panelValidForm.add(panelPhone);
        panelValidForm.add(panelCode);


        panelValidForm.setBackground(new Color(0, 0, 0, 30));
        panelValidForm.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));


        add(panelValidForm);

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

    public void actionPerformed(ActionEvent e) {

        if("bSend".equals(e.getActionCommand()))
            buttonEnterAction();
        else if("bCancel".equals(e.getActionCommand()))
            buttonCancelAction();
    }

    /**
     * Closes Registration process and passes to Login window if all fields are filled correctly
     */
    private void buttonEnterAction() {

        if(!isPhoneNumberValid())
            return;

        if(!isCodeValid())
            return;

        setVisible(false);
        dispose();
        LoginFrame.registerFrame.setVisible(false);
        LoginFrame.registerFrame.dispose();
        LoginFrame.loginFrame.setEnabled(true);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginFrame.loginFrame.toFront();
                LoginFrame.loginFrame.repaint();
            }
        });

/*
		if(isPhoneNumberValid() && isCodeValid())
			JOptionPane.showMessageDialog(this, "Перейти к главному окну...    /прописать при полном функионале/");
		else
			return;
		*/
    }

    /**
     * Closes current window
     */
    private void buttonCancelAction() {
        setVisible(false);
        dispose();
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
     * Checks code field is not empty
     * @return total result of verification
     */
    private boolean isCodeValid(){

        if(tfConfirmCode.getText().length()==0){
            tfConfirmCode.setBackground(new Color(255, 180, 180));
            showMessageDialog(Resources.getString("plugin.eureka.REGISTRATION_NO_CODE_LABEL"));
            return false;
        }

//		else if(!tfConfirmCode.getText().contains("@")){
//			tfConfirmCode.setBackground(new Color(255, 180, 180));
//			showMessageDialog("Некорректно указан email.\nВведите более внимательно");
//			return false;
//		}
//		else if(!receivedCode.equals(tfEmail.getText())){
//			tfEmail.setBackground(new Color(255, 180, 180));
//			showMessageDialog("Your have entered invaled code.\nPlease, enter it more carefully");
//			return false;
//		}
        else
            tfConfirmCode.setBackground(Color.WHITE);
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
                tfConfirmCode.requestFocus();
            else if("tfConfirmCode".equals(componentName))
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
