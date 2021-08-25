package com.hit.algorithm;

import com.company.LRUAlgoCacheImpl;
import com.company.MFUAlgoCacheImpl;
import com.company.RandomReplacementAlgoCacheImpl;
import org.junit.*;

/**
 * test class - run the cache algorithms
 */
public class IAlgoCacheTest {

    private static MFUAlgoCacheImpl mfuObj;
    private static LRUAlgoCacheImpl lruObj;
    private static RandomReplacementAlgoCacheImpl randomObj;

    @BeforeClass
    /**
     * initialize objects for testing
     */
    public static void initVars() {
        mfuObj = new MFUAlgoCacheImpl<Integer, String>(50);
        lruObj = new LRUAlgoCacheImpl<Integer, Double>(15);
        randomObj = new RandomReplacementAlgoCacheImpl<String, Integer>(30);
    }

    @Test
    /**
     *
     */
    public void testMFUAlgoCacheImpl() {

        for (int i = 0; i < 10; i++) {
            mfuObj.putElement(i, "mfu" + i);
        }

        Assert.assertEquals("page is incorrect", "mfu1", mfuObj.getElement(1));
        Assert.assertEquals("page is incorrect", "mfu2", mfuObj.getElement(2));
        mfuObj.getElement(2);
        for (int i = 10; i < 50; i++) {
            mfuObj.putElement(i, "mfu" + i);
        }
        mfuObj.putElement(100, "mfu100");
        Assert.assertNull("Variable may be null.", mfuObj.getElement(2));

        mfuObj.removeElement(5);
        Assert.assertNull("Variable may be null.", mfuObj.getElement(5));
    }

    @Test
    public void testLRUAlgoCacheImpl() {

        for (int i = 0; i < 10; i++) {
            lruObj.putElement(i, "lru" + i);
        }

        Assert.assertEquals("page is incorrect", "lru3", lruObj.getElement(3));
        Assert.assertEquals("page is incorrect", "lru4", lruObj.getElement(4));
        lruObj.removeElement(3);
        Assert.assertNull("Variable may be null.", lruObj.getElement(3));

        lruObj.putElement(5, "lru55555");

        for (int i = 10; i < 20; i++) {
            lruObj.putElement(i, "lru" + i);
        }
        Assert.assertNull("Variable may be null.", lruObj.getElement(6));
        Assert.assertNotNull("Variable may not be null.", lruObj.getElement(4));
    }
}
