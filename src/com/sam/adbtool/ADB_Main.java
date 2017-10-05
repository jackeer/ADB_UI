package com.sam.adbtool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ADB_Main {

	private static Display display;
	private static Text tx1;
	private static Button btn1;
	private static Button btn2;
	private static Button btn3;
	private static Button btn4;
	private static Button btn5;
	private static Button btn6;

	static String stremulator = null;

	// private static String str_args = "";

	static String FileOpem(Shell shell) {

		FileDialog fd = new FileDialog(shell, SWT.OPEN);
		fd.setText("Open");
		fd.setFilterPath("./");
		String[] filterExt = { "*.apk" };
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		System.out.println(selected);

		return selected;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("ADB Test Tool");

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.makeColumnsEqualWidth = true;

		shell.setLayout(gridLayout);
		shell.setSize(640, 480);

		tx1 = new Text(shell, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tx1.setText("This ADB Tool");
		// tx1.setFont(SWTResourceManager.getFont("微軟正黑體", 10, 0));
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalAlignment = SWT.CENTER;
		gridData.verticalAlignment = SWT.CENTER;
		gridData.horizontalSpan = 5;
		gridData.widthHint = 600;
		gridData.heightHint = 360;
		tx1.setLayoutData(gridData);

		GridData gridData2 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		btn1 = new Button(shell, SWT.PUSH);
		btn1.setText("ADB Devices");
		btn1.setLayoutData(gridData2);
		btn2 = new Button(shell, SWT.PUSH);
		btn2.setText("ADB Install APP");
		btn2.setLayoutData(gridData2);
		btn3 = new Button(shell, SWT.PUSH);
		btn3.setText("ADB Filetr APP");
		btn3.setLayoutData(gridData2);
		btn4 = new Button(shell, SWT.PUSH);
		btn4.setText("ADB Select Device");
		btn4.setLayoutData(gridData2);
		btn5 = new Button(shell, SWT.PUSH);
		btn5.setText("Null");
		btn5.setLayoutData(gridData2);
		btn6 = new Button(shell, SWT.PUSH);
		btn6.setText("ADB Delete APP");
		btn6.setLayoutData(gridData2);

		btn1.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unused")
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 1 Called!");
				tx1.setText(btn1.getText() + " Called!");

				String path = System.getProperty("user.dir")
						+ "\\platform-tools\\";
				System.out.println(path);
				tx1.setText(btn1.getText() + "\n" + path);

				// final String commandString = "cmd /c adb devices";
				final String commandString = ".\\platform-tools\\adb.exe devices";
				// final String commandString = "ping 8.8.8.8";

				try {
					String listString = null;

					Process process = Runtime.getRuntime().exec(commandString);

					InputStream istream = process.getInputStream();

					BufferedReader iReader = new BufferedReader(
							new InputStreamReader(istream));

					String input = iReader.readLine();

					for (int i = 0; input != null; i++, input = iReader
							.readLine()) {
						// 若需執行結果可將input傳出
						System.out.println(input);
						if (listString != null) {
							listString = listString + "\n" + input;
						} else {
							listString = input;
						}
					}
					if (listString != null) {
						tx1.setText(listString);
					} else {
						tx1.setText("String Null... ");
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		btn2.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unused")
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 2 Called!");
				tx1.setText(btn2.getText() + " Called!");

				String path = FileOpem(shell);
				tx1.setText(tx1.getText() + "\n" + path);

				final String commandString;

				if (stremulator != null) {
					commandString = ".\\platform-tools\\adb.exe " + stremulator
							+ "install " + path;
				} else {
					commandString = ".\\platform-tools\\adb.exe " + "install "
							+ path;
				}

				System.out.println(commandString);

				try {
					String listString = null;

					Process process = Runtime.getRuntime().exec(commandString);

					InputStream istream = process.getInputStream();

					BufferedReader iReader = new BufferedReader(
							new InputStreamReader(istream));

					String input = iReader.readLine();

					for (int i = 0; input != null; i++, input = iReader
							.readLine()) {
						// 若需執行結果可將input傳出
						System.out.println(input);
						if (listString != null) {
							listString = listString + "\n" + input;
						} else {
							listString = input;
						}
					}
					if (listString != null) {
						tx1.setText(listString);
					} else {
						tx1.setText("None File... ");
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});

		btn3.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unused")
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 3 Called!");
				// tx1.setText(btn3.getText() + " Called!");

				String strfiler = tx1.getSelectionText();

				System.out.println(strfiler);

				final String commandString;

				if (stremulator != null) {
					commandString = ".\\platform-tools\\adb.exe " + stremulator
							+ "shell pm list packages " + strfiler;
				} else {
					commandString = ".\\platform-tools\\adb.exe "
							+ "shell pm list packages " + strfiler;
				}

				System.out.println(commandString);

				try {
					String listString = null;

					Process process = Runtime.getRuntime().exec(commandString);

					InputStream istream = process.getInputStream();

					BufferedReader iReader = new BufferedReader(
							new InputStreamReader(istream));

					String input = iReader.readLine();

					for (int i = 0; input != null; i++, input = iReader
							.readLine()) {
						// 若需執行結果可將input傳出
						System.out.println(input);
						if (listString != null) {
							listString = listString + "\n" + input.replace("package:","");
						} else {
							listString = input.replace("package:","");
						}
					}
					if (listString != null) {
						tx1.setText(listString);
					} else {
						tx1.setText("String Null... ");
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		btn4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 4 Called!");
				// tx1.setText(btn4.getText() + " Called!");

				stremulator = tx1.getSelectionText();

				if (stremulator == "") {
					tx1.setText("None Select Device!!");
					stremulator = null;
					return;
				} else {
					stremulator = "-s " + stremulator;
				}

				System.out.println(stremulator);
				tx1.setText(stremulator + " is be Select!");

			}
		});

		btn5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 5 Called!");
				tx1.setText(btn5.getText() + " Called!");
			}
		});

		btn6.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unused")
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 6 Called!");
				// tx1.setText(btn6.getText() + " Called!");

				String apkpackage = tx1.getSelectionText();

				if (apkpackage == "") {
					tx1.setText("None Select Apk Package!!");
					return;
				}

				System.out.println(apkpackage);

				final String commandString;

				if (stremulator != null) {
					commandString = ".\\platform-tools\\adb.exe " + stremulator
							+ "uninstall " + apkpackage;
				} else {
					commandString = ".\\platform-tools\\adb.exe "
							+ "uninstall " + apkpackage;
				}

				System.out.println(commandString);

				try {
					String listString = null;

					Process process = Runtime.getRuntime().exec(commandString);

					InputStream istream = process.getInputStream();

					BufferedReader iReader = new BufferedReader(
							new InputStreamReader(istream));

					String input = iReader.readLine();

					for (int i = 0; input != null; i++, input = iReader
							.readLine()) {
						// 若需執行結果可將input傳出
						System.out.println(input);
						if (listString != null) {
							listString = listString + "\n" + input;
						} else {
							listString = input;
						}
					}
					if (listString != null) {
						tx1.setText(listString);
					} else {
						tx1.setText("String Null... ");
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		Control[] controls = new Control[] { btn1, btn2, btn3 };
		shell.setTabList(controls);

		// shell.pack(); // 佈局重新計算大小重設

		shell.open();

		// run the event loop as long as the window is open
		while (!shell.isDisposed()) {
			// read the next OS event queue and transfer it to a SWT event
			if (!display.readAndDispatch()) {
				// if there are currently no other OS event to process
				// sleep until the next OS event is available
				display.sleep();
			}
			// tx1.setText(getStr_arg1());
		}
		// disposes all associated windows and their components
		display.dispose();
	}
}
