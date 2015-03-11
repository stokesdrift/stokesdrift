package org.stokesdrift.examples;

import javax.inject.Named;

@Named("hello_component")
public class HelloComponent {
	public HelloComponent() {
		System.out.println("CREATING HELLOW");
	}

	public String getWorld() {
		return "WOW this worked";
	}
}
