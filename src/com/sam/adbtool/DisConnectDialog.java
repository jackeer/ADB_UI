package com.sam.adbtool;

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

public class DisConnectDialog extends Dialog {

	private static Text tx1;

	protected DisConnectDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		// return super.createDialogArea(parent);
		Composite container = (Composite) super.createDialogArea(parent);

		Button button = new Button(container, SWT.PUSH);
		Button btn2 = new Button(container, SWT.PUSH);

		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));

		button.setText("DisConnect");
		btn2.setText("To Usb Mode");

		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Pressed");

				final String commandString;

				if (ADB_Main.getStremulator() != null) {
					commandString = ".\\platform-tools\\adb.exe "
							+ ADB_Main.getStremulator() + "disconnect ";
				} else {
					commandString = ".\\platform-tools\\adb.exe "
							+ "disconnect ";
				}

				System.out.println(commandString);
				getTx1().setText(commandString);

				try {
					new ProcessExecutor(commandString, getTx1(),
							"String Null... ");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btn2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Pressed");

				final String commandString;

				if (ADB_Main.getStremulator() != null) {
					commandString = ".\\platform-tools\\adb.exe "
							+ ADB_Main.getStremulator() + "usb ";
				} else {
					commandString = ".\\platform-tools\\adb.exe " + "usb ";
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
		newShell.setText("ADB DisConnect Dialog");
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
		DisConnectDialog.tx1 = tx1;
	}
}
