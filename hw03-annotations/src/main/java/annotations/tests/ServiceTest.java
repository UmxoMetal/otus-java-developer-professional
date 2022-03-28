package annotations.tests;

import annotations.annotations.After;
import annotations.annotations.Before;
import annotations.annotations.Test;

public class ServiceTest {
    @Before
    public void executeFirstSetUpMethod() {
    }

    @Before
    public void executeSecondSetUpMethod() {
    }

    @Test
    public void executeFirstTest() {
    }

    @Test
    public void executeSecondTest() {
        throw new AssertionError();
    }

    @Test
    public void executeThirdTest() {
    }

    @After
    public void executeTearDownMethod() {
    }
}