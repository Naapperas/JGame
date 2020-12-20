package jGame.logging;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * The main logger for this application. All major logging requests are done to
 * this class, which outputs logs according to this implementation.
 *
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class ProgramLogger {

	// The <code>Logger</code> object that logs all events.
	private static final Logger LOGGER = Logger.getLogger(ProgramLogger.class.getName());

	/**
	 * <code>Handler</code> object that writes logs requested by the {@link #LOGGER}
	 * to a ".log" file.
	 * 
	 * @see Handler
	 * @since 1.0.0
	 */
	private static Handler fileHandler;

	static {
		
		Calendar c = Calendar.getInstance();

		File f = new File("C:\\Users\\nunoa\\OneDrive\\Desktop\\" + c.get(Calendar.YEAR) + "-"
				+
				(c.get(Calendar.MONTH) < 10 ? "0" + (c.get(Calendar.MONTH) + 1) : (c.get(Calendar.MONTH))
								+ 1)
				+ "-" +
				(c.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + c.get(Calendar.DAY_OF_MONTH) : c.get(Calendar.DAY_OF_MONTH))
				+ ".log");
		  
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e1) { // TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  
		  try { 
			  fileHandler = new FileHandler(f.getAbsolutePath(), true); 
		  } catch (SecurityException | IOException e) { // TODO Auto-generated catch block
			  e.printStackTrace(); 
		  }
		  
		fileHandler.setFormatter(new LogFormatter());
		LOGGER.addHandler(fileHandler);
		 

		LOGGER.setUseParentHandlers(false);

		Handler handler = new ConsoleHandler();
		handler.setFormatter(new LogFormatter());
		LOGGER.addHandler(handler);
	}

	/**
	 * Logs the message passed in as the argument at
	 * {@link java.util.logging.Level#INFO} level.
	 *
	 * @param msg the <code>String</code> to be logged
	 * @since 1.0.0
	 */
	public static void writeLog(String msg) {

		LOGGER.info(msg + System.lineSeparator());

	}

	/**
	 * Write a line with the format: <code>key - value</code>, for each key/pair
	 * value contained in this <code>Properties</code> argument.
	 *
	 * @param p the <code>{@link java.util.Properties}</code> object from which
	 *          key/value pairs will be printed
	 * 
	 * @see Properties
	 * @see StringBuilder
	 * @since 1.0.0
	 */
	public static void writeBigLog(Properties p) {

		boolean firstLine = true;

		StringBuilder sb = new StringBuilder();

		for (Iterator<Object> iterator = p.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();

			if (firstLine) {
				sb.append(key + " - " + p.getProperty(key) + System.lineSeparator());
				firstLine = false;
			} else
				sb.append("                                       " + key + " - " + p.getProperty(key)
						+ System.lineSeparator()); // indent whatever is not on the first line

		}

		writeLog(sb.toString());
	}

	/**
	 * Write a line with the format: <code>key - value</code>, for each key/pair
	 * value contained in this <code>Properties</code> argument, after logging a
	 * comment associated with the log.
	 *
	 * @param p       the <code>{@link java.util.Properties}</code> object from
	 *                which key/value pairs will be printed
	 * @param comment a comment to be logged before the key/value pairs
	 * @since 1.0.0
	 */
	public static void writeBigLog(Properties p, String comment) {

		writeLog(comment);

		writeBigLog(p);
	}

	/**
	 * Writes a log line containing the contents of <code>T</code> for each entry in
	 * this array.
	 *
	 * @param collection an array of type {@code T} from which the contents will be
	 *                   printed
	 * @param <T>        the type of elements in this collection
	 * @since 1.0.0
	 */
	public static <T> void writeBigLog(T[] collection) {

		StringBuilder sb = new StringBuilder();
		for (T t : collection)
			sb.append(t);

		writeLog(sb.toString());
	}

	/**
	 * Writes a log line containing the contents of <code>T</code> for each entry in
	 * this array.
	 *
	 * @param collection an array of type {@code T} from which the contents will be
	 *                   printed
	 * @param comment    a comment to be logged before the key/value pairs
	 * @param <T>        the type of elements in this collection
	 * @since 1.0.0
	 */
	public static <T> void writeBigLog(T[] collection, String comment) {

		writeLog(comment);

		writeBigLog(collection);

	}

	/**
	 * Writes a log line containing the contents of <code>T</code> for each entry in
	 * this {@link Collection} object.
	 *
	 * @param collection a {@link Collection} object of type {@code T} from which
	 *                   the contents will be printed
	 * @param <T>        the type of elements in this collection
	 * @see Collection
	 * @since 1.0.0
	 */
	public static <T> void writeBigLog(Collection<T> collection) {

		StringBuilder sb = new StringBuilder();
		for (T t : collection)
			sb.append(t);

		writeLog(sb.toString());

	}

	/**
	 * Writes a log line containing the contents of <code>T</code> for each entry in
	 * this {@link Collection} object.
	 *
	 * @param collection a {@link Collection} object of type {@code T} from which
	 *                   the contents will be printed
	 * @param comment    a comment to be logged before the contents of
	 *                   <code>collection</code>
	 * @param <T>        the type of elements in this collection
	 * @see Collection
	 * @since 1.0.0
	 */
	public static <T> void writeBigLog(Collection<T> collection, String comment) {

		writeLog(comment);

		writeBigLog(collection);
	}

	/**
	 * Logs a message with the specified <code>Level</code>
	 *
	 * @param level the <code>Level</code> at which this message should be logged
	 * @param msg   the message to be logged
	 * @see Level
	 * @since 1.0.0
	 */
	public static void writeLog(Level level, String msg) {

		LOGGER.log(level, msg + System.lineSeparator());

	}

	/**
	 * Writes an error log, consisting of the message provided by <code>e</code>
	 * plus it's stacktrace.
	 *
	 * @param e the <code>Exception</code> object from which to print the stacktrace
	 * @see Exception#printStackTrace()
	 * @since 1.0.0
	 */
	public static void writeErrorLog(Exception e) {

		boolean firstLine = true;

		StringBuilder sb = new StringBuilder();
		StackTraceElement[] trace = e.getStackTrace();
		for (StackTraceElement element : trace)
			if (firstLine) {
				sb.append(element + System.lineSeparator());
				firstLine = false;
			} else
				sb.append("                                       " + element + System.lineSeparator());
		writeLog(java.util.logging.Level.SEVERE, sb.toString());
	}

	/**
	 * Writes an error log, consisting of the message provided by <code>e</code>
	 * plus it's stacktrace.
	 *
	 * @param e       the <code>Exception</code> object from which to print the
	 *                stacktrace
	 * @param comment a comment to be logged before the stacktrace
	 * @see Exception#printStackTrace()
	 * @since 1.0.0
	 */
	public static void writeErrorLog(Exception e, String comment) {
		writeLog(java.util.logging.Level.SEVERE, comment);

		writeErrorLog(e);
	}

	/**
	 * Returns the Handler object responsible for writing logs to a file.
	 *
	 * @return the fileHandler
	 * @since 1.0.0
	 */
	public static Handler getFileHandler() {
		return fileHandler;
	}
}

/**
 * <code>{@link java.util.logging.Formatter Formatter}</code> subclass
 * responsible for formating log messages generated by the application.
 *
 * @author Nuno Pereira
 * @since 1.0.0
 */
class LogFormatter extends Formatter {

	// A Calendar object to print date and time values when a log is requested.
	private Calendar c;

	@Override
	public String format(LogRecord record) {

		c = Calendar.getInstance();

		c.setTimeInMillis(record.getMillis());

		// format the message
		return "[" + c.get(Calendar.YEAR) + "/"
				+ (c.get(Calendar.MONTH) < 10 ? "0" + c.get(Calendar.MONTH) : c.get(Calendar.MONTH)) + "/"
				+ (c.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + c.get(Calendar.DAY_OF_MONTH) : c
						.get(Calendar.DAY_OF_MONTH))
				+ "]" + "{"
				+ (c.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + c.get(Calendar.HOUR_OF_DAY) : c.get(Calendar.HOUR_OF_DAY))
				+ ":" + (c.get(Calendar.MINUTE) < 10 ? "0" + c.get(Calendar.MINUTE) : c.get(Calendar.MINUTE)) + ":"
				+ (c.get(Calendar.SECOND) < 10 ? "0" + c.get(Calendar.SECOND) : c.get(Calendar.SECOND)) + "}" + "("
				+ record.getLoggerName() + ") - " + record.getLevel() + " - " + record.getMessage();
	}
}