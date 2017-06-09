package android.serialport;

public interface SerialPortBarcodeInterface {
	abstract String onSerialDecodeFinish(String data);
}
