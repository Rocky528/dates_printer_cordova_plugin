package com.giorgiofellipe.datecsprinter;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class DatecsPrinter extends CordovaPlugin {
	private static final DatecsSDKWrapper printer = new DatecsSDKWrapper();
	private enum Option {
		listBluetoothDevices,
		connect,
		disconnect,
		feedPaper,
		printText,
		getStatus,
		getTemperature,
		printBarcode,
		printImage,
		printLogo,
		printSelfTest,
		read;
	}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		printer.setCordova(cordova);

		Option option = null;
		try {
			option = Option.valueOf(action);
		} catch (Exception e) {
			return false;
		}
		switch (option) {
			case listBluetoothDevices:
				printer.getBluetoothPairedDevices(callbackContext);
				break;
			case connect:
				printer.setAddress(args.getString(0));
				printer.connect(callbackContext);
				break;
			case disconnect:
				try {
					printer.closeActiveConnections();
					callbackContext.success("Impressora desconectada");
				} catch (Exception e) {
					callbackContext.error("Erro ao desconectar impressora: " + e.getMessage())
				}
				break;
			case feedPaper:
				printer.feedPaper(args.getInt(0));
				break;
		}
		return true;
	}
}