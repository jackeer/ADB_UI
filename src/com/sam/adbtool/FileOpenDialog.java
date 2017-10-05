package com.sam.adbtool;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class FileOpenDialog {

	static Shell shell;

	FileOpenDialog(Shell shell) {

		FileOpenDialog.shell = shell;

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
