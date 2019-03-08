package com.basiccode.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

/**
 * 并发编程 - 同步和锁
 *
 * @author : Jarvis
 * @date : Created in 2018/5/29
 */
public class SynchronizationLocks {

    private static final int NUM_INCREMENTS = 10000;
    private static int count = 0;

    /**
     * 线程同步
     * 在不同的线程上共享可变变量，并且变量访问没有同步机制，这会产生竞争条件
     * 当使用同步方法时，每个方法都共享相应对象的相同监视器
     */
    public void synchronization() {

        ExecutorService executor = Executors.newFixedThreadPool(3);
        IntStream.range(0, NUM_INCREMENTS).forEach(i -> executor.submit(this::incrementSync2));

        stopExecutor(executor);
        // 10000
        System.out.println(count);
    }

    /**
     * 线程同步
     * synchronized关键字 线程同步
     */
    synchronized void incrementSync1() {
        count = count + 1;
    }

    /**
     * synchronized关键字也可用于语句块：
     */
    void incrementSync2() {
        synchronized (this) {
            count = count + 1;
        }
    }

    ReentrantLock lock = new ReentrantLock();

    /**
     * 这个方法是线程安全的，就像同步副本那样。
     * 如果另一个线程已经拿到锁了，再次调用lock()会阻塞当前线程，直到锁被释放。
     * 在任意给定的时间内，只有一个线程可以拿到锁。
     */
    void reentrantLockDemo() {
        /**
         lock.lock();
         try {
         count++;
         } finally {
         // unlock()释放
         lock.unlock();
         }
         */

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            lock.lock();
            try {
                // 第一个任务拿到锁的一秒之后
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        executor.submit(() -> {
            System.out.println("Locked: " + lock.isLocked());
            // isHeldByCurrentThread 查询如果此锁是由当前线程持有的。
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            // tryLock()方法是lock()方法的替代，它尝试拿锁而不阻塞当前线程 布尔值结果来检查锁是否已经被获取
            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);
        });

        stopExecutor(executor);
    }

    /**
     * ReadWriteLock接口规定了锁的另一种类型，包含用于读写访问的一对锁。
     * 读写锁的理念是，只要没有任何线程写入变量，并发读取可变变量通常是安全的
     * 所以读锁可以同时被多个线程持有，只要没有线程持有写锁。这样可以提升性能和吞吐量，因为读取比写入更加频繁。
     */
    public void readWriteLockDemo() {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        executor.submit(() -> {
            lock.writeLock().lock();
            try {
                //暂停一秒 等待writeLock完成
                TimeUnit.SECONDS.sleep(3);
                map.put("foo", "bar");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        });

        //启动两个其它的任务，尝试读取map中的元素
        Runnable readTask = () -> {
            lock.readLock().lock();
            try {
                System.out.println(map.get("foo") + " " + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
        };
        //读锁可以安全同步获取 两个读任务会同时执行
        executor.submit(readTask);
        executor.submit(readTask);

        stopExecutor(executor);
    }

    /**
     * StampedLock 读写锁 Java 8 自带了一种新的锁
     * 支持另一种叫做乐观锁（optimistic locking）的模式
     */
    public void stampedLockDemo() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        StampedLock lock = new StampedLock();

        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                TimeUnit.SECONDS.sleep(2);
                map.put("foo", "bar");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //释放锁
                lock.unlockWrite(stamp);
            }
        });

        //启动两个其它的任务，尝试读取map中的元素
        Runnable readTask = () -> {
            long stamp = lock.readLock();
            try {
                System.out.println(map.get("foo") + " 时间戳：" + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockRead(stamp);
            }
        };
        // 当writeLock完成后 会同时执行这两个任务
        executor.submit(readTask);
        executor.submit(readTask);

        stopExecutor(executor);
    }

    /**
     * StampedLock 实现乐观锁
     */
    public void optimisticLockDemo() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();

        executor.submit(() -> {
            //乐观锁tryOptimisticRead() 返回一个标记而不阻塞当前线程，无论锁是否真正可用 如果已经有写锁被拿到，返回的标记等于0
            // 乐观锁不阻止其他线程同时获取写锁
            long stamp = lock.tryOptimisticRead();
            try {
                // lock.validate(stamp)检查标记是否有效
                // 在第一个线程暂停一秒之后，第二个线程拿到写锁而无需等待乐观的读锁被释放
                // 使用乐观锁时，需要每次在访问任何共享可变变量之后都要检查锁，来确保读锁仍然有效。
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock(stamp);
            }
        });

        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                System.out.println("取得 WriteLock");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock(stamp);
                System.out.println("Write 完成");
            }
        });


        executor.submit(() -> {
            long stamp = lock.readLock();
            try {
                if (count == 0) {
                    //将读锁转换为写锁而不用再次解锁和加锁
                    stamp = lock.tryConvertToWriteLock(stamp);
                    System.out.println("tryConvert : " + stamp);
                    if (stamp == 0L) {
                        System.out.println("Could not convert to master lock");
                        stamp = lock.writeLock();
                    }
                    count = 23;
                }
                System.out.println(count);
            } finally {
                lock.unlock(stamp);
            }
        });

        stopExecutor(executor);
    }

    /**
     * 锁通常用于变量或资源的互斥访问，信号量可以维护整体的准入许可
     * 例如你需要限制你程序某个部分的并发访问总数时非常实用
     */
    public void semaphoreDemo() {
        //ThreadPoolExecutor
        //线程池中核心线程数的最大值10,线程池中能拥有最多线程数10，表示空闲线程的存活时间，用于缓存任务的阻塞队列
        ExecutorService exctor = new ThreadPoolExecutor(10, 10, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        ExecutorService executor = Executors.newFixedThreadPool(10);
        //10个线程  大小为5的信号量，所以将并发访问限制为5
        Semaphore semaphore = new Semaphore(5);
        Runnable longRunningTask = () -> {
            boolean premit = false;
            try {
                premit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (premit) {
                    System.out.println("获取 Semaphore ");
                    // 模拟的长时间运行任务的访问
                    TimeUnit.SECONDS.sleep(5);
                } else {
                    System.out.println("不能获取 Semaphore");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (premit) {
                    semaphore.release();
                }
            }
        };

        //使用Executors创建的线程池
        IntStream.range(0, 10).forEach(i -> executor.submit(longRunningTask));
        stopExecutor(executor);

        //使用ThreadPoolExecutor创建的线程池
        IntStream.range(0, 10).forEach(i -> exctor.submit(longRunningTask));
        stopExecutor(exctor);

    }

    /**
     * 停止 Executor 执行器
     *
     * @param executor
     */
    public static void stopExecutor(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("任务中断");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("取消未完成的任务");
            }
            executor.shutdownNow();
            System.out.println("关闭Executor");
        }
    }
}

class OutPut {

    public static void main(String[] args) {

        SynchronizationLocks sync = new SynchronizationLocks();
        //sync.synchronization();

        //sync.reentrantLockDemo();
        //sync.readWriteLockDemo();
        //sync.stampedLockDemo();
        //sync.optimisticLockDemo();
        sync.semaphoreDemo();

    }

}
