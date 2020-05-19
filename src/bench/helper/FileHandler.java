package bench.helper;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import logging.TimeUnit;
import timing.Timer;

public class FileHandler {

	private static final int MIN_BUFFER_SIZE = 1024 * 1; // KB
	private static final int MAX_BUFFER_SIZE = 1024 * 1024 * 32; // MB
	private static final int MIN_FILE_SIZE = 1024 * 1024 * 1; // MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 512; // MB
	private Timer timer = new Timer();
	private double benchScore;


	public double streamFixedSize(String filePrefix, String fileSuffix,
			int minIndex, int maxIndex, long fileSize, boolean clean,String mode)
			throws IOException {

		if(mode.equals("r")) {
			System.out.println("Stream read benchmark with fixed file size");
		}
		else{
			System.out.println("Stream write benchmark with fixed file size");
		}
		int currentBufferSize = MIN_BUFFER_SIZE;
		String fileName;
		int counter = 0;
		benchScore = 0;

		while (currentBufferSize <= MAX_BUFFER_SIZE
				&& counter <= maxIndex - minIndex) {
			fileName = filePrefix+counter+fileSuffix;
			if(mode.equals("r")) {
				readWithBufferSize(fileName, currentBufferSize, fileSize, clean);
			}
			else{
				writeWithBufferSize(fileName, currentBufferSize, fileSize, clean);
			}
			currentBufferSize*=2;
			counter++;
		}

		benchScore /= (maxIndex - minIndex + 1);
		String partition = filePrefix.substring(0, filePrefix.indexOf(":\\")); // This can be removed or updated with the with the specific Android partition
		System.out.println("File write score on partition " + partition + ": "
				+ String.format("%.2f", benchScore) + " MB/sec");
		return benchScore;
	}

	public double streamFixedBuffer(String filePrefix, String fileSuffix,
			int minIndex, int maxIndex, int bufferSize, boolean clean,String mode)
			throws IOException {

		System.out.println("Stream write benchmark with fixed buffer size");
		int currentFileSize = MIN_FILE_SIZE;
		String fileName;
		int counter=0;
		
		while (currentFileSize <= MAX_FILE_SIZE
				&& counter <= maxIndex - minIndex) {
			fileName=filePrefix+counter+fileSuffix;
			if(mode.equals("r")) {
				readWithBufferSize(fileName, bufferSize, currentFileSize, clean);
			}
			else{
				writeWithBufferSize(fileName, bufferSize, currentFileSize, clean);
			}
			currentFileSize*=2;
			counter++;
		}

		benchScore /= (maxIndex - minIndex + 1);
		String partition = filePrefix.substring(0, filePrefix.indexOf(":\\"));
		if(mode.equals("r")) {
			System.out.println("File read score on partition " + partition + ": "
					+ String.format("%.2f", benchScore) + " MB/sec");
			return benchScore;
		}else{
			System.out.println("File write score on partition " + partition + ": "
					+ String.format("%.2f", benchScore) + " MB/sec");
			return benchScore;
		}
	}

	private void readWithBufferSize(String fileName,int myBufferSize,long fileSize,boolean clean) throws IOException{
		FileInputStream stream=new FileInputStream(fileName);
		final BufferedInputStream inputStream=new BufferedInputStream(stream,myBufferSize);

		byte[] buffer=new byte[myBufferSize];
		int i=0;
		long toRead=fileSize/myBufferSize;
		Random random=new Random();

		timer.start();
		while(i<toRead){
			inputStream.read(buffer);
			i++;
		}

		printStats(fileName,fileSize,myBufferSize,"r");

		inputStream.close();

	}

	private void writeWithBufferSize(String fileName, int myBufferSize,
			long fileSize, boolean clean) throws IOException {

		File folderPath = new File(fileName.substring(0,
				fileName.lastIndexOf(File.separator)));

		if (!folderPath.isDirectory())
			folderPath.mkdirs();

		final File file = new File(fileName);
		FileOutputStream stream=new FileOutputStream(file);
		final BufferedOutputStream outputStream = new BufferedOutputStream(stream,myBufferSize);

		byte[] buffer = new byte[myBufferSize];
		int i = 0;
		long toWrite = fileSize / myBufferSize;
		Random rand = new Random();

		timer.start();
		while (i < toWrite) {
			rand.nextBytes(buffer);
			outputStream.write(buffer);
			i++;
		}		
		printStats(fileName, fileSize, myBufferSize,"w");

		outputStream.close();
		if(clean) {
			file.deleteOnExit();
		}
	}

	private void printStats(String fileName, long totalBytes, int myBufferSize,String mode) {
		NumberFormat nf = new DecimalFormat("#.00");
		final long time = timer.stop();
		double seconds = TimeUnit.convertTime(TimeUnit.Nano,TimeUnit.Sec,time);
		double megabytes = totalBytes /1000000.0;
		double rate = megabytes/seconds;

		if(mode.equals("r")) {
			System.out.println("Done reading " + totalBytes + " bytes to file: "
					+ fileName + " in " + nf.format(seconds) + " ms ("
					+ nf.format(rate) + "MB/sec)" + " with a buffer size of "
					+ myBufferSize / 1024 + " kB");
		}
		else{
			System.out.println("Done writing " + totalBytes + " bytes to file: "
					+ fileName + " in " + nf.format(seconds) + " ms ("
					+ nf.format(rate) + "MB/sec)" + " with a buffer size of "
					+ myBufferSize / 1024 + " kB");
		}
		benchScore += rate;
	}
}