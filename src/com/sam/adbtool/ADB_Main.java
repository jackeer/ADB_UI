package com.sam.adbtool;

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
import org.eclipse.wb.swt.SWTResourceManager;

public class ADB_Main {

	private static Display display;
	private static Shell shell;

	private static Text tx1;
	private static Button btn1;
	private static Button btn2;
	private static Button btn3;
	private static Button btn4;
	private static Button btn5;
	private static Button btn6;
	private static Button btn7;
	private static Button btn8;
	private static Button btn9;

	private static String stremulator = null;

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

	static void Initial_Displayer(Shell shell) {
		shell.setText("ADB Test Tool");

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.makeColumnsEqualWidth = true;

		shell.setLayout(gridLayout);
		shell.setSize(800, 600);

		tx1 = new Text(shell, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tx1.setText("This ADB Tool");
		tx1.setFont(SWTResourceManager.getFont("微軟正黑體", 10, 0));
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalAlignment = SWT.CENTER;
		gridData.verticalAlignment = SWT.CENTER;
		gridData.horizontalSpan = 5;
		gridData.widthHint = 760;
		gridData.heightHint = 450;
		tx1.setLayoutData(gridData);
	}

	static void Initial_Var(Shell shell, GridData gridData2) {
		btn1 = new Button(shell, SWT.PUSH);
		btn1.setText("ADB Devices -l");
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
		btn5.setText("ADB Get Apk");
		btn5.setLayoutData(gridData2);
		btn6 = new Button(shell, SWT.PUSH);
		btn6.setText("ADB Delete APP");
		btn6.setLayoutData(gridData2);
		btn7 = new Button(shell, SWT.PUSH);
		btn7.setText("ADB Tcpip Port");
		btn7.setLayoutData(gridData2);
		btn8 = new Button(shell, SWT.PUSH);
		btn8.setText("ADB Connect Tcpip");
		btn8.setLayoutData(gridData2);
		btn9 = new Button(shell, SWT.PUSH);
		btn9.setText("ADB DisConnect Tcpip");
		btn9.setLayoutData(gridData2);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		display = new Display();
		shell = new Shell(display);

		Initial_Displayer(shell);

		GridData gridData2 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		Initial_Var(shell, gridData2);

		btn1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 1 Called!");
				tx1.setText(btn1.getText() + " Called!");

				String path = System.getProperty("user.dir")
						+ "\\platform-tools\\";
				System.out.println(path);
				tx1.setText(btn1.getText() + "\n" + path);

				// final String commandString = "cmd /c adb devices";
				final String commandString = ".\\platform-tools\\adb.exe devices -l";

				try {
					new ProcessExecutor(commandString, tx1, "String Null... ");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// try {
				// String listString = null;
				//
				// Process process = Runtime.getRuntime().exec(commandString);
				//
				// InputStream istream = process.getInputStream();
				//
				// BufferedReader iReader = new BufferedReader(
				// new InputStreamReader(istream));
				//
				// String input = iReader.readLine();
				//
				// for (int i = 0; input != null; i++, input = iReader
				// .readLine()) {
				// // 若需執行結果可將input傳出
				// System.out.println(input);
				// if (listString != null) {
				// listString = listString + "\n" + input;
				// } else {
				// listString = input;
				// }
				// }
				// if (listString != null) {
				// tx1.setText(listString);
				// } else {
				// tx1.setText("String Null... ");
				// }
				//
				// } catch (IOException e1) {
				// e1.printStackTrace();
				// }
			}
		});

		btn2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 2 Called!");
				tx1.setText(btn2.getText() + " Called!");

				FileOpenDialog fd = new FileOpenDialog(shell);

				// String path = FileOpem(shell);
				String path = fd.FileOpem();
				if (path == null) {
					tx1.setText("None File... ");
					return;
				}
				tx1.setText(tx1.getText() + "\n" + path);

				final String commandString;

				if (getStremulator() != null) {
					commandString = ".\\platform-tools\\adb.exe "
							+ getStremulator() + "install " + path;
				} else {
					commandString = ".\\platform-tools\\adb.exe " + "install "
							+ path;
				}

				System.out.println(commandString);

				try {
					new ProcessExecutor(commandString, tx1, "None File... ");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// try {
				// String listString = null;
				//
				// Process process = Runtime.getRuntime().exec(commandString);
				//
				// InputStream istream = process.getInputStream();
				//
				// BufferedReader iReader = new BufferedReader(
				// new InputStreamReader(istream));
				//
				// String input = iReader.readLine();
				//
				// for (int i = 0; input != null; i++, input = iReader
				// .readLine()) {
				// // 若需執行結果可將input傳出
				// System.out.println(input);
				// if (listString != null) {
				// listString = listString + "\n" + input;
				// } else {
				// listString = input;
				// }
				// }
				// if (listString != null) {
				// tx1.setText(listString);
				// } else {
				// tx1.setText("None File... ");
				// }
				//
				// } catch (IOException e1) {
				// e1.printStackTrace();
				// }

			}
		});

		btn3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 3 Called!");
				// tx1.setText(btn3.getText() + " Called!");

				String strfiler = tx1.getSelectionText();

				System.out.println(strfiler);

				final String commandString;

				if (getStremulator() != null) {
					commandString = ".\\platform-tools\\adb.exe "
							+ getStremulator() + "shell pm list packages "
							+ strfiler;
				} else {
					commandString = ".\\platform-tools\\adb.exe "
							+ "shell pm list packages " + strfiler;
				}

				System.out.println(commandString);

				try {
					new ProcessExecutor(commandString, tx1, "String Null... ");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// try {
				// String listString = null;
				//
				// Process process = Runtime.getRuntime().exec(commandString);
				//
				// InputStream istream = process.getInputStream();
				//
				// BufferedReader iReader = new BufferedReader(
				// new InputStreamReader(istream));
				//
				// String input = iReader.readLine();
				//
				// for (int i = 0; input != null; i++, input = iReader
				// .readLine()) {
				// // 若需執行結果可將input傳出
				// System.out.println(input);
				// if (listString != null) {
				// listString = listString + "\n"
				// + input.replace("package:", "");
				// } else {
				// listString = input.replace("package:", "");
				// }
				// }
				// if (listString != null) {
				// tx1.setText(listString);
				// } else {
				// tx1.setText("String Null... ");
				// }
				//
				// } catch (IOException e1) {
				// e1.printStackTrace();
				// }
			}
		});

		btn4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 4 Called!");
				// tx1.setText(btn4.getText() + " Called!");

				setStremulator(tx1.getSelectionText());

				if (getStremulator() == "") {
					tx1.setText("None Select Device!!");
					setStremulator(null);
					return;
				} else {
					// stremulator = "-s " + stremulator.replace(":", "-");
					setStremulator("-s " + getStremulator() + " ");
				}

				System.out.println(getStremulator());
				tx1.setText(getStremulator() + "is be Select!");

			}
		});

		btn5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 5 Called!");
				// tx1.setText(btn5.getText() + " Called!");

				String apkpath = tx1.getSelectionText();

				if (apkpath == "") {
					tx1.setText("None Select Apk Package!!");
					return;
				}

				// adb shell pm path com.example.someapp
				final String commandString;

				if (getStremulator() != null) {
					commandString = ".\\platform-tools\\adb.exe "
							+ getStremulator() + "shell pm path " + apkpath;
				} else {
					commandString = ".\\platform-tools\\adb.exe "
							+ "shell pm path " + apkpath;
				}

				System.out.println(commandString);

				try {
					new ProcessExecutor(commandString, tx1, "String Null... ");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				final String commandString2;
				String listString = ProcessExecutor.getListString();

				if (getStremulator() != null) {
					commandString2 = ".\\platform-tools\\adb.exe "
							+ getStremulator() + "pull " + listString;
				} else {
					commandString2 = ".\\platform-tools\\adb.exe " + "pull "
							+ listString;
				}

				System.out.println(commandString2);

				try {
					new ProcessExecutor(commandString2, tx1, "String Null... ");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// try {
				// String listString = null;
				//
				// Process process = Runtime.getRuntime().exec(commandString);
				//
				// InputStream istream = process.getInputStream();
				//
				// BufferedReader iReader = new BufferedReader(
				// new InputStreamReader(istream));
				//
				// String input = iReader.readLine();
				//
				// for (int i = 0; input != null; i++, input = iReader
				// .readLine()) {
				// // 若需執行結果可將input傳出
				// System.out.println(input);
				// if (listString != null) {
				// listString = listString + "\n"
				// + input.replace("package:", "");
				// } else {
				// listString = input.replace("package:", "");
				// }
				// }
				// if (listString != null) {
				// tx1.setText(listString);
				//
				// // adb pull /data/app/com.example.someapp-2.apk
				// // path/to/desired/destination
				// final String commandString2;
				//
				// if (stremulator != null) {
				// commandString2 = ".\\platform-tools\\adb.exe "
				// + stremulator + "pull " + listString;
				// } else {
				// commandString2 = ".\\platform-tools\\adb.exe "
				// + "pull " + listString;
				// }
				//
				// listString = null;
				//
				// process = Runtime.getRuntime().exec(commandString2);
				//
				// istream = process.getInputStream();
				//
				// iReader = new BufferedReader(new InputStreamReader(
				// istream));
				//
				// input = iReader.readLine();
				//
				// for (int i = 0; input != null; i++, input = iReader
				// .readLine()) {
				// // 若需執行結果可將input傳出
				// System.out.println(input);
				// if (listString != null) {
				// listString = listString + "\n" + input;
				// } else {
				// listString = input;
				// }
				// }
				// if (listString != null) {
				// tx1.setText(listString);
				// } else {
				// tx1.setText("Please check whether the file directory has been copied apk ...!");
				// }
				//
				// } else {
				// tx1.setText("String Null... ");
				// }
				//
				// } catch (IOException e1) {
				// e1.printStackTrace();
				// }
			}
		});

		btn6.addSelectionListener(new SelectionAdapter() {
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

				if (getStremulator() != null) {
					commandString = ".\\platform-tools\\adb.exe "
							+ getStremulator() + "uninstall " + apkpackage;
				} else {
					commandString = ".\\platform-tools\\adb.exe "
							+ "uninstall " + apkpackage;
				}

				System.out.println(commandString);

				try {
					new ProcessExecutor(commandString, tx1, "String Null... ");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// try {
				// String listString = null;
				//
				// Process process = Runtime.getRuntime().exec(commandString);
				//
				// InputStream istream = process.getInputStream();
				//
				// BufferedReader iReader = new BufferedReader(
				// new InputStreamReader(istream));
				//
				// String input = iReader.readLine();
				//
				// for (int i = 0; input != null; i++, input = iReader
				// .readLine()) {
				// // 若需執行結果可將input傳出
				// System.out.println(input);
				// if (listString != null) {
				// listString = listString + "\n" + input;
				// } else {
				// listString = input;
				// }
				// }
				// if (listString != null) {
				// tx1.setText(listString);
				// } else {
				// tx1.setText("String Null... ");
				// }
				//
				// } catch (IOException e1) {
				// e1.printStackTrace();
				// }
			}
		});

		btn7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 7 Called!");
				tx1.setText(btn4.getText() + " Called!");

				FileOpenDialog fd = new FileOpenDialog(shell);

				FileOpenDialog.setTx1(tx1);
				fd.open();
			}
		});

		btn8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 8 Called!");
				tx1.setText(btn4.getText() + " Called!");

				ConnectDialog cd = new ConnectDialog(shell);

				ConnectDialog.setTx1(tx1);
				cd.open();
			}
		});

		btn9.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Button 9 Called!");
				tx1.setText(btn4.getText() + " Called!");
				
				DisConnectDialog dcd = new DisConnectDialog(shell);

				DisConnectDialog.setTx1(tx1);
				dcd.open();
			}
		});

		Control[] controls = new Control[] { btn1, btn2, btn3, btn4, btn5,
				btn6, btn7, btn8, btn9 };
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

	public static String getStremulator() {
		return stremulator;
	}

	public static void setStremulator(String stremulator) {
		ADB_Main.stremulator = stremulator;
	}
}
