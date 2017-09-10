package com.ccfdod.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示。
 * 如：ABCABCABC……
 */
class AlternateDemo {
    //标志当前由哪一个线程输出，1代表A，2代表B，3代表C
    private int number = 1;

    private Lock lock = new ReentrantLock();

    //Condition的强大之处在于它可以为多个线程间建立不同的Condition
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    // loopNum:当前循环轮数
    void loopA(int loopNum) {
        //上锁
        lock.lock();
        try {
            while (number != 1) {
                //等待
                condition1.await();
            }

            System.out.println(Thread.currentThread().getName() + ", currentLoopNum is " + loopNum);
            number = 2;
            //唤醒
            condition2.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //确保释放锁
            lock.unlock();
        }
    }

    void loopB(int loopNum) {
        lock.lock();
        try {
            while (number != 2) {
                condition2.await();
            }

            System.out.println(Thread.currentThread().getName() + ", currentLoopNum is " + loopNum);
            number = 3;
            condition3.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void loopC(int loopNum) {
        lock.lock();
        try {
            while (number != 3) {
                condition3.await();
            }

            System.out.println(Thread.currentThread().getName() + ", currentLoopNum is " + loopNum);
            number = 1;
            condition1.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class TestABCAlternate {

    public static void main(String[] args) {
        AlternateDemo ad = new AlternateDemo();

        new Thread(() -> {
            for (int i = 0; i < 10; i++)
                ad.loopA(i);
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++)
                ad.loopB(i);
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++)
                ad.loopC(i);
        }, "C").start();
    }
}