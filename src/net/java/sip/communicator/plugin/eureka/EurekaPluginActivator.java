package net.java.sip.communicator.plugin.eureka;

import net.java.sip.communicator.plugin.eureka.gui.BalanceLabel;
import net.java.sip.communicator.plugin.simpleaccreg.InitialAccountRegistrationFrame;
import net.java.sip.communicator.service.contactlist.MetaContactListService;
import net.java.sip.communicator.service.gui.*;
import net.java.sip.communicator.service.gui.Container;
import net.java.sip.communicator.service.protocol.AccountID;
import net.java.sip.communicator.service.protocol.AccountManager;
import net.java.sip.communicator.service.protocol.ProtocolProviderFactory;
import net.java.sip.communicator.util.Logger;
import net.java.sip.communicator.util.ServiceUtils;
import org.jitsi.service.configuration.ConfigurationService;
import org.jitsi.service.resources.ResourceManagementService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Zenit
 * Date: 09.07.13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */

public class EurekaPluginActivator implements BundleActivator{

    /**
     * OSGi bundle context.
     */
    public static BundleContext bundleContext;

    private static ResourceManagementService resourcesService;

    Logger logger = Logger.getLogger(EurekaPluginActivator.class);

    public void start(BundleContext bc)
            throws Exception
    {
        bundleContext = bc;

        if(!SwingUtilities.isEventDispatchThread())
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    init();
                }
            });
            return;
        }

        init();

        //register balance label
        Hashtable<String, String> containerFilter
                = new Hashtable<String, String>();
        containerFilter.put(
                Container.CONTAINER_ID,
                Container.CONTAINER_ACCOUNT_SOUTH.getID());

        containerFilter.put(
                Container.CONTAINER_ID,
                Container.CONTAINER_MAIN_WINDOW.getID());

        bc.registerService(  PluginComponent.class.getName(),
                new BalanceLabel(),
                containerFilter);

        if (logger.isInfoEnabled())
            logger.info("BALANCE INFO... [REGISTERED]");

    }

    /**
     * Initialize and displays the initial registration frame.
     */
    private void init(){

//        EurekaFrame accountRegFrame = new EurekaFrame();
        LoginFrame accountRegFrame = new LoginFrame();

//            accountRegFrame.pack();
            /*
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            double sw = screenSize.getWidth();
            double sh = screenSize.getHeight();
            int w = (int) accountRegFrame.getWidth();
            int h = (int) accountRegFrame.getWidth();
            */
            accountRegFrame.setSize(365, 300);
            accountRegFrame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2 - 250,
                Toolkit.getDefaultToolkit().getScreenSize().height/2 - 200);
//            accountRegFrame.setResizable(true);
//            accountRegFrame.setLocation(screenSize.width / 2
//                    - accountRegFrame.getWidth() / 2, screenSize.height / 2
//                    - accountRegFrame.getHeight() / 2);

            accountRegFrame.setVisible(true);
    }

    public void stop(BundleContext bc) throws Exception
    {
    }


    /**
     * Returns the <tt>MetaContactListService</tt> obtained from the bundle
     * context.
     * <p>
     * <b>Note</b>: Because this plug-in is meant to be initially displayed (if
     * necessary) and not get used afterwards, the method doesn't cache the
     * return value. Make sure you call it as little as possible if execution
     * speed is under consideration.
     * </p>
     *
     * @return the <tt>MetaContactListService</tt> obtained from the bundle
     *         context
     */
    public static MetaContactListService getContactList()
    {
        ServiceReference serviceReference =
                bundleContext.getServiceReference(MetaContactListService.class
                        .getName());

        return (MetaContactListService) bundleContext
                .getService(serviceReference);
    }

    /**
     * Returns the <tt>UIService</tt> obtained from the bundle
     * context.
     * <p>
     * <b>Note</b>: Because this plug-in is meant to be initially displayed (if
     * necessary) and not get used afterwards, the method doesn't cache the
     * return value. Make sure you call it as little as possible if execution
     * speed is under consideration.
     * </p>
     *
     * @return the <tt>MetaContactListService</tt> obtained from the bundle
     *         context
     */
    public static UIService getUIService()
    {
        ServiceReference serviceReference
                = bundleContext.getServiceReference(UIService.class.getName());

        return (UIService) bundleContext
                .getService(serviceReference);
    }

    /**
     * Returns the <tt>ResourceManagementService</tt>, through which we will
     * access all resources.
     *
     * @return the <tt>ResourceManagementService</tt>, through which we will
     * access all resources.
     */
    public static ResourceManagementService getResources()
    {
        if (resourcesService == null)
        {
            resourcesService
                    = ServiceUtils.getService(
                    bundleContext,
                    ResourceManagementService.class);
        }
        return resourcesService;
    }

    /**
     * Returns a reference to a ConfigurationService implementation currently
     * registered in the bundle context or null if no such implementation was
     * found.
     *
     * @return a currently valid implementation of the ConfigurationService.
     */
    public static ConfigurationService getConfigService()
    {
        return ServiceUtils.getService(bundleContext,
                ConfigurationService.class);
    }
}
