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
 * Created with IntelliJ IDEA.
 * User: Zenit
 * Date: 28.08.13
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 */
public class LoginFrame extends SIPCommFrame implements ServiceListener, ActionListener, KeyListener, Skinnable
{
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

    public LoginFrame()/* throws HeadlessException, IOException*/ {

        setIconImage(Resources.getImage("plugin.eureka.APP_ICON").getImage());
        String title = Resources.getString("plugin.eureka.LOGIN_FRAME_TITLE");
        setTitle(title);

        setLayout(new BorderLayout());
        setContentPane(new JLabel(Resources.getImage("plugin.eureka.FRAME_BACKGROUND")));
        setLayout(new FlowLayout());

//		==================================================

        JPanel panelLogo = new JPanel(new BorderLayout());
        panelLogo.setOpaque(false);

        ImageIcon image = Resources.getImage("plugin.eureka.SPLASH");
        JLabel label = new JLabel("", image, JLabel.CENTER);
        panelLogo.add( label, BorderLayout.CENTER );

        add(panelLogo);

//		==================================================

//        Панель капчи

        JPanel panelRegform = new JPanel();
        panelRegform.setLayout(new BoxLayout(panelRegform, BoxLayout.Y_AXIS));


        JPanel panelPhone = new JPanel(new GridLayout(1,2));

        JLabel lPhoneNumber = new JLabel(Resources.getString("plugin.eureka.LOGIN_FRAME_PHONE_LABEL"));
        lPhoneNumber.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 30));

        tfPhoneNumber = new JTextField(10);
        tfPhoneNumber.setName("tfPhoneNumber");
        tfPhoneNumber.addKeyListener(this);

        panelPhone.add(lPhoneNumber);
        panelPhone.add(tfPhoneNumber);

        panelPhone.setOpaque(false);
//		  panelPhone.setBorder(BorderFactory.createMatteBorder(5, 10, 5, 5, new Color(0, 0, 0, 0)));
        panelPhone.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        JPanel panelCode = new JPanel(new GridLayout(1,2));

        JLabel lConfirmCode = new JLabel(Resources.getString("plugin.eureka.LOGIN_FRAME_CODE_LABEL"));
        lConfirmCode.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 30));

        tfConfirmCode = new JTextField(10);
        tfConfirmCode.setName("tfConfirmCode");
        tfConfirmCode.addKeyListener(this);

        panelCode.add(lConfirmCode);
        panelCode.add(tfConfirmCode);

        panelCode.setOpaque(false);
//		  panelCode.setBorder(BorderFactory.createMatteBorder(5, 10, 10, 5, new Color(0, 0, 0, 0)));
        panelCode.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        JPanel panelRegisterProposal = new JPanel(new FlowLayout(FlowLayout.RIGHT));



        JButton lRegisterProposal = new JButton(Resources.getString("plugin.eureka.LOGIN_FRAME_NOT_REGISTERED"));
        lRegisterProposal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lRegisterProposal.setFocusPainted(false);
        lRegisterProposal.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));
        lRegisterProposal.setContentAreaFilled(false);
        lRegisterProposal.setBorderPainted(false);
        lRegisterProposal.setOpaque(false);

        lRegisterProposal.setActionCommand("buttonRegisterProposal");
        lRegisterProposal.addActionListener(this);
        panelRegisterProposal.add(lRegisterProposal);





//		  JLabel lRegisterProposal = new JLabel("<html><i>Еще не зарегистрированы?</i><html>");
//		  lRegisterProposal.setCursor(new Cursor(Cursor.HAND_CURSOR));
//		  lRegisterProposal.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
//		  panelRegisterProposal.setAlignmentX(Component.LEFT_ALIGNMENT);

//		  panelRegisterProposal.add(lRegisterProposal);


        panelRegisterProposal.setBackground( new Color(0, 0, 0, 30) );
//		  panelRegisterProposal.setBorder(BorderFactory.createMatteBorder(5, 10, 5, 5, new Color(0, 0, 0, 0)));
        panelRegisterProposal.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));





        panelRegform.add(panelPhone);
        panelRegform.add(panelCode);
        panelRegform.add(panelRegisterProposal);



        panelRegform.setBackground( new Color(0, 0, 0, 30) );
        panelRegform.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));


        add(panelRegform);

//		==================================================

        JPanel panelAutoSetts = new JPanel(new BorderLayout());
        panelAutoSetts.setOpaque(false);

        JButton labelAutoSetts = new JButton(Resources.getString("plugin.eureka.LOGIN_FRAME_AUTOSEETINGS"));
        labelAutoSetts.setCursor(new Cursor(Cursor.HAND_CURSOR));
        labelAutoSetts.setFocusPainted(false);
        labelAutoSetts.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));
        labelAutoSetts.setContentAreaFilled(false);
        labelAutoSetts.setBorderPainted(false);
        labelAutoSetts.setOpaque(false);

        labelAutoSetts.setActionCommand("buttonAutoSetts");
        labelAutoSetts.addActionListener(this);

        panelAutoSetts.add( labelAutoSetts, BorderLayout.CENTER );

        add(panelAutoSetts);



//		==================================================

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setOpaque(false);

        bEnter = new JButton(Resources.getString("plugin.eureka.LOGIN_FRAME_ENTER_BUTTON"));
        bEnter.setName("bEnter");
        bEnter.setActionCommand("bEnter");
        bEnter.addActionListener(this);
        bEnter.addKeyListener(this);

        bCancel = new JButton(Resources.getString("plugin.eureka.LOGIN_FRAME_CANCEL_BUTTON"));
        bCancel.setName("bCancel");
        bCancel.setActionCommand("bCancel");
        bCancel.addActionListener(this);
        bCancel.addKeyListener(this);

        panelButtons.add(bEnter);
        panelButtons.setBorder(BorderFactory.createEmptyBorder(15, 180, 0, 5));
        panelButtons.add(bCancel);

        add(panelButtons);


        setResizable(false);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                receivedCode = receiveCode();
                JOptionPane.showMessageDialog(null, receivedCode);
            }
        }).start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if("buttonAutoSetts".equals(e.getActionCommand()))
            buttonAutoSettsAction();
        else if("buttonRegisterProposal".equals(e.getActionCommand()))
            buttonRegisterProposal();
        else if("bEnter".equals(e.getActionCommand()))
            buttonEnterAction();
        else if("bCancel".equals(e.getActionCommand()))
            buttonCancelAction();
    }

    private void buttonAutoSettsAction() {
//		TODO: SET WHEN MAIN FUNCIONAL WILL BE DEVELOPING
        JOptionPane.showMessageDialog(this, "Загрузка автонастроек...    /прописать при полном функионале/");
    }

    private void buttonRegisterProposal() {
//		TODO: SET WHEN MAIN FUNCIONAL WILL BE DEVELOPING
        JOptionPane.showMessageDialog(this, "Перейти к регистрации...    /прописать при полном функионале/");
    }

    private void buttonEnterAction() {
//		TODO: SET WHEN MAIN FUNCIONAL WILL BE DEVELOPING

        if(isPhoneNumberValid() && isCodeValid())
            JOptionPane.showMessageDialog(this, "Перейти к главному окну...    /прописать при полном функионале/");
//			setVisible(false);
//			dispose();
        else
            return;
    }

    private void buttonCancelAction() {
//		TODO: SET WHEN MAIN FUNCIONAL WILL BE DEVELOPING
        JOptionPane.showMessageDialog(this, "Закрыть окно...    /прописать при полном функионале/");
        setVisible(false);
        dispose();
    }

    private boolean isPhoneNumberValid(){

        if(tfPhoneNumber.getText().length()==0){
            tfPhoneNumber.setBackground(new Color(255, 180, 180));
            showMessageDialog("Enter a phone number");
            return false;
        }
        else if(!hasOnlyDigits(tfPhoneNumber.getText())){
            tfPhoneNumber.setBackground(new Color(255, 180, 180));
            showMessageDialog("Your phone has symbols are not numbers");
            return false;
        }
        else
            tfPhoneNumber.setBackground(Color.WHITE);
        return true;
    }

    private boolean isCodeValid(){

        if(tfConfirmCode.getText().length()==0){
            tfConfirmCode.setBackground(new Color(255, 180, 180));
            showMessageDialog("You have not entered code");
            return false;
        }
        else if(!receivedCode.equals(tfConfirmCode.getText())){
            tfConfirmCode.setBackground(new Color(255, 180, 180));
            showMessageDialog("Your have entered invaled code.\nPlease, enter it more carefully");
            return false;
        }
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

    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void serviceChanged(ServiceEvent serviceEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void loadSkin() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
