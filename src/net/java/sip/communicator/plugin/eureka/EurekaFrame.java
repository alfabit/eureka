package net.java.sip.communicator.plugin.eureka;

import net.java.sip.communicator.plugin.desktoputil.*;
import net.java.sip.communicator.service.gui.*;
import net.java.sip.communicator.util.Logger;
import net.java.sip.communicator.util.skin.Skinnable;
import org.jitsi.service.configuration.ConfigurationService;
import org.osgi.framework.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Zenit
 * Date: 09.07.13
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */

public class EurekaFrame
        extends SIPCommFrame
        implements ServiceListener, ActionListener, KeyListener, Skinnable
{

    private ConfigurationService configurationService;

    private SIPCommTextField tfPhoneNumber;
    private JLabel lConfirmationProgress;
    private JLabel lPressConfirmCode;
    private JLabel lCodeValue;
    private JPanel p1;
    private SIPCommTextField tfCodeValue;
    private SIPCommButton bConfirmCode;
    private JLabel lUsersettings;

    private String receivedCode;






    /**
     * The <tt>Logger</tt> used by this <tt>InitialAccountRegistrationFrame</tt>
     * for logging output.
     */
    private final Logger logger
            = Logger.getLogger(EurekaFrame.class);

//    private final Collection<AccountRegistrationPanel> registrationForms =
//            new Vector<AccountRegistrationPanel>();

    /**
     * Creates an instance of <tt>NoAccountFoundPage</tt>.
     */
    public EurekaFrame()
    {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // A new mega change in file!

        setLayout(new BorderLayout());
        JLabel background=new JLabel(Resources.getImage("plugin.eureka.AUTHORIZE_FRAME_BACKGROUND"));
        setTitle(Resources.getString("plugin.eureka.FRAME_AUTHORIZE_TITLE"));
        add(background);
        background.setLayout(new FlowLayout());

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gblMainPanel = new GridBagConstraints();
        gblMainPanel.insets = new Insets(0, 15, 10, 5);

//		===============================================

        p1 = new JPanel(new GridBagLayout());
        p1.setBackground(new Color(90, 180, 120, 50));

        GridBagConstraints gblPanel1 = new GridBagConstraints();

        JLabel lPhoneNumber = new JLabel(Resources.getString("plugin.eureka.SET_PHONE_INVITATION_LABEL"));
        gblPanel1.gridx = 0;
        gblPanel1.gridy = 0;
        gblPanel1.insets = new Insets(0, 15, 10, 5);
        gblPanel1.anchor = GridBagConstraints.WEST;
        p1.add(lPhoneNumber, gblPanel1);
        tfPhoneNumber = new SIPCommTextField("QQZZXX");
        tfPhoneNumber.setColumns(5);
        gblPanel1.gridx = 0;
        gblPanel1.gridy = 1;
        gblPanel1.fill = GridBagConstraints.HORIZONTAL;
        gblPanel1.insets = new Insets(0, 15, 10, 15);
        tfPhoneNumber.setName("tfPhoneNumber");
        tfPhoneNumber.addKeyListener(this);
        p1.add(tfPhoneNumber, gblPanel1);

        SIPCommButton bEnter = new SIPCommButton(Resources.getImage("plugin.eureka.BUTTON_BACKGROUND").getImage(),
                                                 null,
                                                 Resources.getImage("plugin.eureka.BUTTON_BACKGROUND_FOCUS").getImage(),
                                                 null,
                                                 null,
                                                 Resources.getImage("plugin.eureka.BUTTON_BACKGROUND_PRESSED").getImage()) ;
        bEnter.setText(Resources.getString("plugin.eureka.BUTTON_ENTER_TEXT")) ;
        gblPanel1.gridx = 1;
        gblPanel1.gridy = 1;
        gblPanel1.fill = GridBagConstraints.HORIZONTAL;
        gblPanel1.insets = new Insets(0, 15, 10, 15);
        bEnter.addActionListener(this);
        p1.add(bEnter, gblPanel1);

        gblMainPanel.gridx = 0;
        gblMainPanel.gridy = 0;
        panel.add(p1, gblMainPanel);

//		===============================================

        JPanel p2 = new JPanel(new GridBagLayout());
        p2.setBackground(new Color(190, 120, 180, 50));

        GridBagConstraints gblPanel2 = new GridBagConstraints();

        lConfirmationProgress = new JLabel(" ");
        gblPanel2.gridx = 0;
        gblPanel2.gridy = 0;
        gblPanel2.insets = new Insets(0, 15, 10, 5);
        gblPanel2.anchor = GridBagConstraints.WEST;
        p2.add(lConfirmationProgress, gblPanel2);

        lCodeValue = new JLabel(" ");
        gblPanel2.gridx = 0;
        gblPanel2.gridy = 1;
        gblPanel2.insets = new Insets(0, 15, 10, 5);
        gblPanel2.anchor = GridBagConstraints.WEST;
        p2.add(lCodeValue, gblPanel2);


        lPressConfirmCode = new JLabel(" ");
        gblPanel2.gridx = 0;
        gblPanel2.gridy = 2;
        gblPanel2.insets = new Insets(0, 15, 10, 5);
        gblPanel2.anchor = GridBagConstraints.WEST;
        p2.add(lPressConfirmCode, gblPanel2);

//        tfCodeValue = new JTextField(20);
        tfCodeValue = new SIPCommTextField("");
        tfCodeValue.setColumns(5);
        gblPanel2.gridx = 0;
        gblPanel2.gridy = 3;
        gblPanel2.fill = GridBagConstraints.HORIZONTAL;
        gblPanel2.insets = new Insets(0, 15, 10, 15);
        tfCodeValue.setName("tfCodeValue");
        tfCodeValue.addKeyListener(this);
        tfCodeValue.setVisible(false);
        p2.add(tfCodeValue, gblPanel2);


        bConfirmCode = new SIPCommButton(Resources.getImage("plugin.eureka.BUTTON_BACKGROUND").getImage(),
                null,
                Resources.getImage("plugin.eureka.BUTTON_BACKGROUND_PRESSED").getImage(),
                null,
                null,
                Resources.getImage("plugin.eureka.BUTTON_BACKGROUND_FOCUS").getImage());
        bConfirmCode.setText(Resources.getString("plugin.eureka.BUTTON_CONFIRM_TEXT"));
        gblPanel2.gridx = 1;
        gblPanel2.gridy = 3;
        gblPanel2.fill = GridBagConstraints.HORIZONTAL;
        gblPanel2.insets = new Insets(0, 15, 10, 15);
        bConfirmCode.setVisible(false);
        bConfirmCode.addActionListener(this);
        p2.add(bConfirmCode, gblPanel2);

        lUsersettings = new JLabel(Resources.getString("plugin.eureka.SAVED_SETTINGS_SHOW_LABEL"));
        gblPanel2.gridx = 0;
        gblPanel2.gridy = 4;
        gblPanel2.insets = new Insets(0, 15, 10, 5);
        gblPanel2.anchor = GridBagConstraints.WEST;
        lUsersettings.setVisible(false);
        p2.add(lUsersettings, gblPanel2);







        gblMainPanel.gridx = 0;
        gblMainPanel.gridy = 1;
        panel.add(p2, gblMainPanel);
//		panel.add(p3);

        background.add(panel);
    }

//    @Override
    public void actionPerformed(ActionEvent e) {

        if(Resources.getString("plugin.eureka.BUTTON_ENTER_TEXT").equals(e.getActionCommand()))
            buttonSendAction();
        else if(Resources.getString("plugin.eureka.BUTTON_CONFIRM_TEXT").equals(e.getActionCommand()))
            buttonOkAction();

    }

    private void buttonSendAction() {
        if(!isPhoneNumberValid())
            return;

        lConfirmationProgress.setText(Resources.getString("plugin.eureka.WAIT_FOR_CONFIRMATION_CODE_LABEL"));
        lCodeValue.setText(" ");

        new Thread(new Runnable()
        {

//            @Override
            public void run()
            {
                int counter = 0;

                while(counter<5){

                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e1)
                    {
                        e1.printStackTrace();
                    }



                    final String str = lCodeValue.getText() + " . "/* +counter*/;
                    lCodeValue.setText(" ");
                    javax.swing.SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // This code runs in the UI thread and can use
                            // the fetched data to fill in UI widgets.
                            lCodeValue.setText(str);
                        }
                    });

                    counter++;
                }

                lCodeValue.setText(" ");

                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        // This code runs in the UI thread and can use
                        // the fetched data to fill in UI widgets.
                        lConfirmationProgress.setText(Resources.getString("plugin.eureka.CONFIRMATION_CODE_LABEL"));
                        lCodeValue.setText(receiveCode());
                        lPressConfirmCode.setText(Resources.getString("plugin.eureka.SET_CONFIRMATION_CODE_INVITATION_LABEL"));
                    }
                });


                tfCodeValue.setVisible(true);
                bConfirmCode.setVisible(true);

            }

            private String receiveCode()
            {
                char codeChars[] = new char[9];

                for(int i = 0; i<codeChars.length; i++){

                    int randValue = 0;

                    do{
                        randValue = (int) ((((int) 'Z') - ((int) '0'))*Math.random() + ((int) '0'));
                    }
                    while(i!=5 && randValue > ((int) '9') && randValue <((int) 'A'));

                    codeChars[i] = (char) randValue;

                }

                codeChars[5] = '-';
                receivedCode = String.valueOf(codeChars);
                return receivedCode;
            }

            private boolean isPhoneNumberValid(){

                boolean isValid = true;

                if(tfPhoneNumber.getText().length()==0){
                    tfPhoneNumber.setBackground(new Color(255, 230, 230));
                    isValid = false;
                }

                return isValid;
            }

        }).start();
    }

    private void buttonOkAction() {

        if(isCodeValid()){
            lUsersettings.setVisible(true);
            String resultText = Resources.getString("plugin.eureka.SAVED_SETTINGS_SHOW_LABEL") + " \n" + loadUserSettings();
            lUsersettings.setText(resultText);
        }
    }

    private boolean isPhoneNumberValid(){

        String text =     tfPhoneNumber.getText();

        if((tfPhoneNumber.getText() == null) || (tfPhoneNumber.getText().length()==0)){
            tfPhoneNumber.setBackground(new Color(255, 180, 180));
            showMessageDialog(Resources.getString("plugin.eureka.SET_PHONE_INVITATION_LABEL_SIMPLE"));
            return false;
        }
        else if(!hasOnlyDigits(tfPhoneNumber.getText())){
            tfPhoneNumber.setBackground(new Color(255, 180, 180));
            showMessageDialog(Resources.getString("plugin.eureka.INCORRECT_PHONE_WARNING"));
            return false;
        }
        else
            tfPhoneNumber.setBackground(Color.WHITE);
        return true;
    }

    private boolean isCodeValid(){

        if((tfCodeValue.getText() == null) || (tfCodeValue.getText().length()==0)){
            tfCodeValue.setBackground(new Color(255, 180, 180));
            showMessageDialog(Resources.getString("plugin.eureka.NO_CODE_WARNING"));
            return false;
        }
        else if(!receivedCode.equals(tfCodeValue.getText())){
            tfCodeValue.setBackground(new Color(255, 180, 180));
            showMessageDialog(Resources.getString("plugin.eureka.INCORRECT_CONFIRMATION_CODE_WARNING"));
            return false;
        }
        else
            tfCodeValue.setBackground(Color.WHITE);
        return true;
    }

    private void showMessageDialog(String mess) {
        JOptionPane.showMessageDialog(this, mess);
 /*
        PopupDialog popupDialog
                = EurekaPluginActivator.getUIService()
                .getPopupDialog();

        popupDialog.showMessagePopupDialog(
                Resources.getString(
                        "service.gui.USERNAME_NULL"),
                Resources.getString(
                        "service.gui.ERROR"),
                PopupDialog.ERROR_MESSAGE);
 */
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

    private String loadUserSettings(){

        String setts = "setA : aaa \n" +
                "setB : bbb \n" +
                "setC : ccc \n" +
                "setD : ddd \n";

        return setts;
    }

//    @Override
    public void keyTyped(KeyEvent e) {


        JTextField tfAction = (JTextField) e.getSource();

        if(tfAction.getText().length()==0)
            tfAction.setBackground(Color.WHITE);
    }

//    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_ENTER){

            JTextField tfAction = (JTextField) e.getSource();

            if("tfPhoneNumber".equals(tfAction.getName()))
                buttonSendAction();
            else if("tfCodeValue".equals(tfAction.getName()))
                buttonOkAction();

        }
    }

//    @Override
    public void keyReleased(KeyEvent e) {}

    public void loadSkin() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     * Handles registration of a new account wizard.
     */
    public void serviceChanged(ServiceEvent event)
    {
        Object sService = EurekaPluginActivator.bundleContext.
                getService(event.getServiceReference());

        // we don't care if the source service is not a plugin component
        if (! (sService instanceof AccountRegistrationWizard))
            return;

        AccountRegistrationWizard wizard
                = (AccountRegistrationWizard) sService;

        if (event.getType() == ServiceEvent.REGISTERED)
        {
//            this.addAccountRegistrationForm(wizard);
        }
    }

 /*
    *//**
     * Handles the event triggered by the "service.gui.SIGN_IN" button.
     */ /*
    private class SigninActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {

            EurekaFrame initialAccountRegistrationFrame = EurekaFrame.this;
            initialAccountRegistrationFrame.setVisible(false);
            initialAccountRegistrationFrame.dispose();
        }
    }
  */
    @Override
    protected void close(boolean isEscaped)
    {
    }
}
