package com.datastax.tickdata.engine;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.NotImplementedException;
import org.joda.time.DateTime;

import cern.colt.list.DoubleArrayList;
import cern.colt.list.LongArrayList;

import com.datastax.timeseries.utils.TimeSeries;

public class TickGenerator implements Iterator<TimeSeries> {
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private int counter = 0;
	private List<String> exchangeSymbols;
	private AtomicLong tickCounter = new AtomicLong(0);

	public TickGenerator(List<String> exchangeSymbols) {
		this.exchangeSymbols = exchangeSymbols;
	}

	@Override
	public boolean hasNext() {
		//return counter < this.exchangeSymbols.size();
		return counter < 25;
	}

	@Override
	public TimeSeries next() {
		String exchangeSymbol = exchangeSymbols.get(counter);
		
		DateTime today = new DateTime().withHourOfDay(8).withMinuteOfHour(0).withSecondOfMinute(0);
		DateTime endTime = new DateTime().withHourOfDay(16).withMinuteOfHour(30).withSecondOfMinute(0);
				
		LongArrayList dates = new LongArrayList();
		DoubleArrayList prices = new DoubleArrayList();
		double startPrice = Math.random()*1000;
		
		while (today.isBefore(endTime.getMillis())){
			
			dates.add(today.getMillis());
			prices.add(startPrice);
			
			startPrice = this.createRandomValue(startPrice);
			
			today = today.plusMillis(new Double(Math.random() * 500).intValue());			
		}
		counter ++;
		
		dates.trimToSize();
		prices.trimToSize();
		
		return new TimeSeries(exchangeSymbol + "-" + formatter.format(today.toDate()), dates.elements(), prices.elements());
	}

	@Override
	public void remove() {
		throw new NotImplementedException();
	}

	private double createRandomValue(double lastValue) {

		double up = Math.random() * 2;
		double percentMove = (Math.random() * 1.0) / 100;

		if (up < 1) {
			lastValue -= percentMove;
		} else {
			lastValue += percentMove;
		}

		tickCounter.incrementAndGet();
		
		return lastValue;
	}

	public long getCount(){
		return this.tickCounter.get();
	}
}
