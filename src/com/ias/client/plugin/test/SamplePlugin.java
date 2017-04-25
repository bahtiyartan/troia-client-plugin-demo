package com.ias.client.plugin.test;

import java.rmi.RemoteException;

import com.ias.client.plugin.iasAbstractPlugin;
import com.ias.client.plugin.iasPluginAction;
import com.ias.client.plugin.iasPluginValidationParameters;

@SuppressWarnings("serial")
/**
 * plugin passes all action parameters to frame to print on a textfield
 */
public class SamplePlugin extends iasAbstractPlugin {

	public SamplePluginFrame Frame;
	private String m_strUniqueInstanceKey;

	protected SamplePlugin(SamplePluginFrame pFrame,
	      String pUIK) throws RemoteException {
		super();

		Frame = pFrame;
		m_strUniqueInstanceKey = pUIK;
	}

	/**
	 * called when a PLUGINACTION command runs on application
	 * server.
	 * 
	 * this demo plugin converts iasPluginAction to a string
	 */
	@Override
	public boolean doAction(iasPluginAction pAction) {
		StringBuilder sb = new StringBuilder();

		sb.append("Class:").append(pAction.getActionClass());

		sb.append("\nType: ").append(pAction.getActionType());

		sb.append("\nValue: ")
		      .append(pAction.getActionValue());

		sb.append("\nSource: ").append(pAction.getSource());

		sb.append("\nSessionId: ")
		      .append(pAction.getSessionId());

		sb.append("\nTransactionId: ")
		      .append(pAction.getTransactionId());

		sb.append("\nUsername: ")
		      .append(pAction.getUsername());

		sb.append("\nTransaction: ")
		      .append(pAction.getTransaction());

		sb.append("\n");

		Frame.handleAction(sb.toString(),
		      pAction.getSource());

		return true;
	}

	/**
	 * called when PLUGINVALIDATE command runs on appliaction
	 * server. PLUGINVALIDATE command sends all validation
	 * parameters to all plugins which contains data
	 * (language,database etc) about session.
	 * 
	 * After this parameters is checked by plugin, if given params
	 * is valid for plugin must send true.
	 * 
	 * If multiple plugins are available, a pop up message appears
	 * on client to allow user select target plugin for given
	 * action.
	 */
	@Override
	protected boolean validatePlugin(
	      iasPluginValidationParameters params) {
		// return true/false after validation paramters
		// checked
		return true;
	}

	/**
	 * plugin service sends only related actions to this plugin
	 */
	@Override
	public String[] getRelatedIncomingActionClasses() {
		return new String[] { "BITOOL" };
	}

	@Override
	public String getAppName() {
		return "BITOOL - Business Analytics";
	}

	@Override
	protected void disconnecting() {
		Frame = null;
	}

	@Override
	public String getAppInstanceKey() {
		return m_strUniqueInstanceKey;
	}
}
