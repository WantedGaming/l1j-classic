/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ConsoleLogFormatter extends Formatter {
	private SimpleDateFormat dateFmt = new SimpleDateFormat("HH:mm:ss");

	// ANSI color codes
	private static final String RESET  = "\u001B[0m";
	private static final String GRAY   = "\u001B[90m";
	private static final String WHITE  = "\u001B[37m";
	private static final String GREEN  = "\u001B[32m";
	private static final String YELLOW = "\u001B[33m";
	private static final String RED    = "\u001B[31m";
	private static final String RED_BOLD = "\u001B[1;31m";
	private static final String CYAN   = "\u001B[36m";

	private static boolean useColor = true;

	static {
		// Disable color if NO_COLOR env variable is set
		String noColor = System.getenv("NO_COLOR");
		if (noColor != null) {
			useColor = false;
		}
	}

	public ConsoleLogFormatter() {
	}

	private String colorize(String color, String text) {
		if (!useColor) return text;
		return color + text + RESET;
	}

	private String getLevelTag(Level level) {
		if (level == Level.SEVERE) {
			return colorize(RED_BOLD, "ERROR");
		} else if (level == Level.WARNING) {
			return colorize(YELLOW, " WARN");
		} else if (level == Level.INFO) {
			return colorize(GREEN, " INFO");
		} else if (level == Level.CONFIG) {
			return colorize(CYAN, "  CFG");
		} else {
			return colorize(GRAY, "DEBUG");
		}
	}

	@Override
	public String format(LogRecord record) {
		StringBuffer output = new StringBuffer();
		String[] split = record.getSourceClassName().split("\\.");
		String className = record.getSourceClassName();
		if (split.length > 0)
			className = split[split.length - 1];

		if (record.getLevel().intValue() == Level.CONFIG.intValue()) {
			output.append(record.getMessage());
			output.append("\r\n");
		} else {
			// timestamp
			output.append(colorize(GRAY, dateFmt.format(new Date(record.getMillis()))));
			output.append(" ");
			// level tag
			output.append(getLevelTag(record.getLevel()));
			output.append(colorize(GRAY, " | "));
			// source
			output.append(colorize(CYAN, className));
			output.append(colorize(GRAY, "."));
			output.append(colorize(CYAN, record.getSourceMethodName()));
			output.append(colorize(GRAY, " - "));
			// message (use red for SEVERE, yellow for WARNING)
			if (record.getLevel() == Level.SEVERE) {
				output.append(colorize(RED, record.getMessage()));
			} else if (record.getLevel() == Level.WARNING) {
				output.append(colorize(YELLOW, record.getMessage()));
			} else {
				output.append(colorize(WHITE, record.getMessage()));
			}
			output.append("\r\n");
			if (record.getThrown() != null) {
				try {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					record.getThrown().printStackTrace(pw);
					pw.close();
					output.append(colorize(RED, sw.toString()));
					output.append("\r\n");
				} catch (Exception ex) {
				}
			}
		}
		return output.toString();
	}
}