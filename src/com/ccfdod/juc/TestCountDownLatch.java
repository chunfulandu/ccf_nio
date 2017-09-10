package com.ccfdod.juc;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch:闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 */
public class TestCountDownLatch {

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(5);
        LatchDemo ld = new LatchDemo(latch);

        long start = System.currentTimeMillis();
        //这里如果i<8(大于5的数，5对应new CountDownLatch(5))，那么在在至少5个线程完成后，执行打印耗费时间语句
        for(int i=0; i<8; i++) {
            new Thread(ld).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {

        }
        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));
    }
}

class LatchDemo implements Runnable {
    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) {
                    System.out.println(i);
                }
            }
        } finally {
            //在finally中保证一定能够执行
            latch.countDown();
        }
    }
}