package gameParts;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest
{
    Field field;
    @Before
    public void setUp()
    {
        field = new Field(3, 4, PlayerColor.GREEN);
    }

    @After
    public void tearDown()
    {
        field = null;
    }

    @Test
    public void getX()
    {
        Assert.assertEquals(field.getX(), 3);
    }

    @Test
    public void getY()
    {
        Assert.assertEquals(field.getY(), 4);
    }

    @Test
    public void getColor()
    {
        Assert.assertEquals(field.getColor(), PlayerColor.GREEN);
    }

    @Test
    public void getPawn()
    {
        field.setPawn(new Pawn(PlayerColor.RED));
        Assert.assertEquals(field.getPawn().getColor(), PlayerColor.RED);
    }

    @Test
    public void setPawn()
    {
        field.setPawn(new Pawn(PlayerColor.BLUE));
        Assert.assertEquals(field.getPawn().getColor(), PlayerColor.BLUE);
    }
}