package gameParts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PawnTest
{
    Pawn pawn;

    @Before
    public void setUp()
    {
        pawn = new Pawn(PlayerColor.GREEN);
    }

    @After
    public void tearDown()
    {
        pawn = null;
    }

    @Test
    public void setColor()
    {
        pawn.setColor(PlayerColor.RED);
        assertEquals(pawn.getColor(), PlayerColor.RED);
    }

    @Test
    public void getColor()
    {
        pawn.setColor(PlayerColor.RED);
        assertEquals(pawn.getColor(), PlayerColor.RED);
    }
}