package gameParts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameboardCreatorTest
{
    GameboardCreator gbc;

    @Before
    public void setUp()
    {
        gbc = new GameboardCreator(3,3);
    }

    @After
    public void tearDown()
    {
        gbc = null;
    }

    @Test
    public void getBoard()
    {
        assertNotNull(gbc.getBoard());
    }

    @Test
    public void getConstants()
    {
        assertNotNull(gbc.getConstants());
    }
}