package com.ias.client.plugin.test;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ias.client.plugin.iasPluginEvent;
import com.ias.client.plugin.iasPluginException;
import com.ias.client.plugin.iasPluginPrededefinedActionTypes;

@SuppressWarnings("serial")
public class SamplePluginFrame extends JFrame
      implements ActionListener {

	private SamplePlugin Plugin;
	private String SessionId;

	JTextArea jIncomingActions = new JTextArea();

	JButton jConnect = new JButton("Connect");
	JButton jDisconnect = new JButton("Disconnect");

	JTextField jTargetClient = new JTextField();
	JTextField jActionType = new JTextField();
	JTextField jActionValue = new JTextField();
	JTextField jTransactionId = new JTextField();
	JButton jPostAction = new JButton("Post Action");

	public SamplePluginFrame() {
		super();

		SessionId = "Session-"
		      + ((int) (Math.random() * 1000));

		this.setTitle("Sample Plugin (" + SessionId + ")");

		jConnect.setActionCommand("connect");
		jConnect.addActionListener(this);

		jDisconnect.setActionCommand("disconnect");
		jDisconnect.addActionListener(this);
		jDisconnect.setEnabled(false);

		JPanel jIncomingActionsPanel = new JPanel(
		      new BorderLayout());

		JScrollPane iPane = new JScrollPane(jIncomingActions);

		jIncomingActionsPanel.add(iPane);
		JLabel jIncActions = new JLabel("Incoming Actions");
		jIncomingActionsPanel.add(jIncActions,
		      BorderLayout.NORTH);

		JPanel jMainPanel = new JPanel(new BorderLayout());
		jMainPanel.setBorder(BorderFactory
		      .createEmptyBorder(10, 10, 10, 10));
		jMainPanel.add(jIncomingActionsPanel);

		JPanel iHeaderPanel = new JPanel(
		      new GridLayout(1, 3));
		iHeaderPanel.add(jConnect);
		iHeaderPanel.add(jDisconnect);
		iHeaderPanel.setBorder(
		      BorderFactory.createEmptyBorder(0, 0, 10, 0));

		jMainPanel.add(iHeaderPanel, BorderLayout.NORTH);

		//
		JPanel iSouthPanel = new JPanel(new BorderLayout());

		JLabel iFormHeader = new JLabel("Outgoing Action");

		iFormHeader.setBorder(
		      BorderFactory.createEmptyBorder(10, 0, 10, 0));
		iSouthPanel.add(iFormHeader, BorderLayout.NORTH);

		JPanel iOutgoingActionForm = new JPanel(
		      new GridLayout(5, 2, 0, 3));
		iOutgoingActionForm.add(new JLabel("TargetClient"));
		iOutgoingActionForm.add(jTargetClient);

		iOutgoingActionForm.add(new JLabel("ActionType"));
		iOutgoingActionForm.add(jActionType);

		iOutgoingActionForm.add(new JLabel("ActionValue"));
		iOutgoingActionForm.add(jActionValue);

		iOutgoingActionForm.add(new JLabel("TransactionId"));
		iOutgoingActionForm.add(jTransactionId);

		iOutgoingActionForm.add(new JLabel(""));
		iOutgoingActionForm.add(jPostAction);

		jPostAction.setActionCommand("postaction");
		jPostAction.addActionListener(this);

		iSouthPanel.add(iOutgoingActionForm,
		      BorderLayout.CENTER);

		jMainPanel.add(iSouthPanel, BorderLayout.SOUTH);

		jActionType.setText(
		      iasPluginPrededefinedActionTypes.CANIASLINK);

		this.setContentPane(jMainPanel);

		enableDisablecomponents(false);
	}

	private void enableDisablecomponents(
	      boolean isConnected) {
		jConnect.setEnabled(!isConnected);
		jDisconnect.setEnabled(isConnected);

		jTargetClient.setEnabled(isConnected);
		jActionType.setEnabled(isConnected);
		jActionValue.setEnabled(isConnected);
		jTransactionId.setEnabled(isConnected);
		jPostAction.setEnabled(isConnected);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getActionCommand()
		      .equalsIgnoreCase("connect")) {

			if (Plugin == null || !Plugin.isConnected()) {

				try {

					/*************************
					 * START: Critical point
					 * 
					 * Create an instance and call
					 * establishConnection()
					 ************************/

					Plugin = new SamplePlugin(this, SessionId);
					Plugin.connect();

					/*************************
					 * END: Critical point.
					 *************************/

					enableDisablecomponents(true);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} else if (arg0.getActionCommand()
		      .equalsIgnoreCase("disconnect")) {

			try {
				Plugin.disconnect();
				Plugin = null;

				enableDisablecomponents(false);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (arg0.getActionCommand()
		      .equalsIgnoreCase("postaction")) {

			String type = jActionType.getText();
			String value = jActionValue.getText();
			String targetClient = jTargetClient.getText();

			iasPluginEvent e = new iasPluginEvent(type, value);

			try {
				Plugin.postEvent(e, targetClient);
			} catch (iasPluginException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void handleAction(String p_strAction,
	      String p_strSource) {
		jIncomingActions.setText(jIncomingActions.getText()
		      + p_strAction + "\n");

		jTargetClient.setText(p_strSource);
	}

}
