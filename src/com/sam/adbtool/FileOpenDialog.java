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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class FileOpenDialog extends Dialog {

	private static Shell shell;

	FileOpenDialog(Shell shell) {
		super(shell);

		FileOpenDialog.shell = shell;

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		// return super.createDialogArea(parent);
		Composite container = (Composite) super.createDialogArea(parent);

		Button button = new Button(container, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		button.setText("Press me");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Pressed");
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
		newShell.setText("Selection dialog");
	}

	@Override
	protected Point getInitialSize() {
		// return super.getInitialSize();
		return new Point(400, 300);
	}

	String FileOpem() {

		FileDialog fd = new FileDialog(shell, SWT.OPEN);
		fd.setText("Open");
		fd.setFilterPath("./");
		String[] filterExt = { "*apk" };
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		System.out.println(selected);

		return selected;
	}

}
