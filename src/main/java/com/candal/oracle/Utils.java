package com.candal.oracle;

import java.math.BigDecimal;

public class Utils {



	// don't allow initialization
	private Utils() {
	}

	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * 
	 * 		This function exists because normal jdbcTemplate parameter input
	 *         render the call to the pipelined PL/SQL function unusable due to
	 *         the fact that it cannot process chained tables
	 * 
	 */
	public static String resolveSqlArgs(String sql, Object... args) throws Exception {

    	for (int i = 0; i < args.length; i++) {
        if (args[i] instanceof String) {
          args[i] = "'" + args[i].toString() + "'";
        }
    	}

		String temp = sql.replace("?", "%s");
		temp = String.format(temp, args);

		return temp;
	}

	public static String decimalToString(String value) {

		String temp = value;

		try {

      if (temp == null) {
        temp = "0";
      }
			
			temp = temp.trim();

      if (temp.isEmpty()) {
        temp = "0";
      }
			
			BigDecimal bigDec  = new BigDecimal(temp.trim());
			temp = bigDec.setScale(2 ,BigDecimal.ROUND_DOWN).toString();
		
		} catch (Exception e) {

			temp = "value with error";
		}

		return temp;
	}
	
	public static String trimString(String value) {

    if (value == null) {
      return null;
    } else {
      return value.trim();
    }
	}

}
