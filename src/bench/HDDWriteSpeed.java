package bench;

import java.io.IOException;

import bench.helper.FileHandler;

public class HDDWriteSpeed implements IBenchmark {
	double result;

	@Override
	public void initialize(Object... params) {
	}

	@Override
	public void warmUp() {

	}

	@Override
	public void run() {
		throw new UnsupportedOperationException(
				"Method not implemented. Use run(Object) instead");
	}

	@Override
	public void run(Object... options) {
		FileHandler handler = new FileHandler();
		// either "fs" - fixed size, or "fb" - fixed buffer
		String option = (String) options[0];
		// true/false whether the written files should be deleted at the end
		Boolean clean = (Boolean) options[1];
		// either "r" - read or "w" - write
		String mode = (String) options[2];

		String prefix = "C:\\Users\\Timotei\\IdeaProjects\\BenchmarkJunk\\write-";//This needs to be adapted

		String suffix = ".dat";
		int startIndex = 0;
		int endIndex = 8;
		long fileSize =256*1024*1024;
		int bufferSize = 4*1024;

		if(mode.equals("r")) {
			try {

				if (option.equals("fs"))
					result = handler.streamFixedSize(prefix, suffix, startIndex,
							endIndex, fileSize, clean, mode);
				else if (option.equals("fb"))
					result = handler.streamFixedBuffer(prefix, suffix, startIndex,
							endIndex, bufferSize, clean, mode);
				else
					throw new IllegalArgumentException("Argument "
							+ options[0].toString() + " is undefined");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			try {

				if (option.equals("fs"))
					result = handler.streamFixedSize(prefix, suffix, startIndex,
							endIndex, fileSize, clean, mode);
				else if (option.equals("fb"))
					result = handler.streamFixedBuffer(prefix, suffix, startIndex,
							endIndex, bufferSize, clean, mode);
				else
					throw new IllegalArgumentException("Argument "
							+ options[0].toString() + " is undefined");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void clean() {

	}

	public void cancel(){

	}


	public double getResult() {
		return result;
	}
}
