package com.sam.adbtool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.eclipse.swt.widgets.Text;

public class ProcessExecutor {

	private static final int SUCCESS = 0; // 表示程序執行成功
	private static String COMMAND = null;
	private static final String SUCCESS_MESSAGE = "Success！";
	private static final String ERROR_MESSAGE = "Error：";
	private static Text tx1;
	private static String listString = null;
	private static String errmsg = null;

	public ProcessExecutor(String command, Text tx1, String errmsg)
			throws Exception {

		ProcessExecutor.COMMAND = command;
		ProcessExecutor.tx1 = tx1;
		ProcessExecutor.errmsg = errmsg;
		setListString(null);

		// 執行程序
		Process process = Runtime.getRuntime().exec(COMMAND);

		// 打印程序輸出
		readProcessOutput(process);

		// 等待程序執行結束並輸出狀態
		int exitCode = process.waitFor();

		if (exitCode == SUCCESS) {
			System.out.println(SUCCESS_MESSAGE);
		} else {
			System.err.println(ERROR_MESSAGE + exitCode);
		}
	}

	/**
	 * 打印進程輸出
	 * 
	 * @param process
	 *            進程
	 */
	private static void readProcessOutput(final Process process) {
		// 將進程的正常輸出在 System.out 中打印，進程的錯誤輸出在 System.err 中打印
		read(process.getInputStream(), System.out);
		read(process.getErrorStream(), System.err);
	}

	// 讀取輸入流
	private static void read(InputStream inputStream, PrintStream out) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));

			String line;
			while ((line = reader.readLine()) != null) {
				out.println(line);
				if (getListString() != null) {
					setListString(getListString() + "\n"
							+ line.replace("package:", ""));
				} else {
					setListString(line.replace("package:", ""));
				}
			}
			if (getListString() != null) {
				tx1.append(getListString() + "\n");
			} else {
				tx1.append(errmsg + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getListString() {
		return listString;
	}

	public static void setListString(String listString) {
		ProcessExecutor.listString = listString;
	}

}
