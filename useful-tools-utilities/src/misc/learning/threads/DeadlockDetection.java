package misc.learning.threads;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

class DeadLockDetectionThread extends Thread {

	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
		long[] ids = tmx.findDeadlockedThreads();
		if (ids != null) {
			ThreadInfo[] infos = tmx.getThreadInfo(ids, true, true);
			System.out.println("The following threads are deadlocked:");
			for (ThreadInfo ti : infos) {
				System.out.println("ThreadInfo##" + ti);
			}
		}

	}
}

class A {
	synchronized void foo(B b) {
		String name = Thread.currentThread().getName();
		System.out.println(name + " entered X.foo");
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("X Interrupted");
		}
		System.out.println(name + " trying to call Y.last()");
		b.last();
	}

	synchronized void last() {
		System.out.println("Inside X.last");
	}
}

class B {
	synchronized void bar(A a) {
		String name = Thread.currentThread().getName();
		System.out.println(name + " entered Y.bar");
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("Y Interrupted");
		}
		System.out.println(name + " trying to call Y.last()");
		a.last();
	}

	synchronized void last() {
		System.out.println("Inside X.last");
	}
}

class DeadlockDetection implements Runnable {
	A a = new A();
	B b = new B();

	DeadlockDetection() {
		Thread.currentThread().setName("MainThread");
		Thread t = new Thread(this, "RacingThread");
		t.start();
		new DeadLockDetectionThread().start();
		a.foo(b); // get lock on X in this thread.
		System.out.println("Back in main thread");
	}

	public void run() {
		b.bar(a); // get lock on Y in other thread.
		System.out.println("Back in other thread");
	}

	public static void main(String args[]) {
		new DeadlockDetection();
	}

}
