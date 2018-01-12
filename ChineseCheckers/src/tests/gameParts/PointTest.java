package gameParts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest
{
    Point point;

    @Before
    public void setUp()
    {
        point = new Point(5, 8);
    }

    @After
    public void tearDown()
    {
        point = null;
    }

    @Test
    public void equals()
    {
        Point testPoint = new Point(5, 8);
        assertEquals(point.equals(testPoint), true);

        Point testPoint2 = new Point(5, 10);
        assertEquals(point.equals(testPoint2), false);
    }

    @Test
    public void getX()
    {
        assertEquals(point.getX(), 5);
    }

    @Test
    public void getY()
    {
        assertEquals(point.getY(), 8);
    }
}