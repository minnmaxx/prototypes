package org.talz;

import java.io.IOException;

import org.junit.Test;
import org.talz.domain.Order;
import org.talz.domain.product.Pizza;

public class Main {

	@Test
	public void testPizzaWritable() {
		Pizza p = new Pizza(false);
		p.setP1("");
	}
	
	@Test(expected=RuntimeException.class)
	public void testPizzaReadonly() {
		Pizza p = new Pizza(true);
		p.setP1("");	
	}
	
	@Test
	public void testOrderWritable() {
		Order o = new Order();
		o.setAll("","");
	}
	
	@Test(expected=RuntimeException.class)
	public void testOrderReadonly() {
		Order o = new Order();
		o.readOnlyTrue();
		o.setAll("","");	
	}
	
	private void testMultithreaded() throws IOException, InterruptedException{
		final Order o = new Order();
		
		o.setAll("dont","change");
		o.readOnlyTrue();
		
		Runnable run1 = new Runnable() {
			
			@Override
			public void run() {
				try {
					o.controlledSet( "allow" );
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Runnable run2 = new Runnable() {
			
			@Override
			public void run() {
				o.setP1("not allow");
			}
		};
		
		Thread t1 = new Thread( run1, "t1" );
		t1.start();
		
		Thread.sleep(100);

		Thread t2 = new Thread( run2, "t2" );
		t2.start();
		
		t1.join();
		System.out.println( o.getP1() );
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		
		Order o = new Order();
		o.setP1("1");
		o.getP1();

	}
}
