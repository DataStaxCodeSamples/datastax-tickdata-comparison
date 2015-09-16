package com.datastax.timeseries.utils;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.joda.time.DateTime;

public class TimeSeries {

	private String symbol;
	private long[] dates;
	private double[] values;
	
	public TimeSeries(String symbol, long[] dates, double[] values) {
		super();
		this.symbol = symbol;
		this.dates = dates;
		this.values = values;
	}

	public String getSymbol() {
		return symbol;
	}

	public long[] getDates() {
		return dates;
	}

	public double[] getValues() {
		return values;
	}
	
	public void reverse(){
		ArrayUtils.reverse(dates);
		ArrayUtils.reverse(values);
	}

	@Override
	public String toString() {
		return "TimeSeries [symbol=" + symbol + ", dates=" + Arrays.toString(dates) + ", values="
				+ Arrays.toString(values) + "]";
	}
	public String toFormatterString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("TimeSeries - symbol=" + symbol + "\n");
		
		for (int i=0; i < dates.length; i++){
			buffer.append(new DateTime(dates[i]).toString() + " - " + values[i] + "\n");
		}
			
		return buffer.toString(); 	
	}	
	
}
