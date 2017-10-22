package com.sam.adbtool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TcpipDialog extends Dialog {

	private static Text tx1;

	TcpipDialog(Shell shell) {
		super(shell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		// return super.createDialogArea(parent);
		Composite container = (Composite) super.createDialogArea(parent);

		// ".\\platform-tools\\adb.exe shell ip -f inet addr show wlan0";
		final String commandString;

		if (ADB_Main.getStremulator() != null) {
			commandString = ".\\platform-tools\\adb.exe "
					+ ADB_Main.getStremulator()
					+ "shell ip -f inet addr show wlan0";
		} else {
			commandString = ".\\platform-tools\\adb.exe "
					+ "shell ip -f inet addr show wlan0";
		}

		System.out.println(commandString);
		
		try {
			new ProcessExecutor(commandString, getTx1(), "String Null... ");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		final Text tx2 = new Text(container, SWT.SINGLE | SWT.BORDER);
		tx2.setText("1234    ");

		Button button = new Button(container, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		button.setText("Set Tcpip Port");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Pressed");
				String str_port = tx2.getText().trim();

				String regex = "^([0-9]{4})$"; // 匹配 port 的正則
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(str_port);

				if (m.find()) {
					System.out.println(m.group()); // 獲得匹配的 port
				} else {
					tx1.append("Port Format Error!! \n");
					tx1.append("RANG: xxxx \n");
					return;
				}

				// System.out.println(str_port);

				final String commandString;

				if (ADB_Main.getStremulator() != null) {
					commandString = ".\\platform-tools\\adb.exe "
							+ ADB_Main.getStremulator() + "tcpip " + str_port;
				} else {
					commandString = ".\\platform-tools\\adb.exe " + "tcpip "
							+ str_port;
				}

				System.out.println(commandString);

				try {
					new ProcessExecutor(commandString, getTx1(),
							"String Null... ");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		return container;
	}

	/**
	 * overriding this methods allows you to set the title of the custom dialog
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("ADB Tcpip Dialog");
	}

	@Override
	protected Point getInitialSize() {
		// return super.getInitialSize();
		return new Point(220, 140);
	}

	public static Text getTx1() {
		return tx1;
	}

	public static void setTx1(Text tx1) {
		TcpipDialog.tx1 = tx1;
	}

}
