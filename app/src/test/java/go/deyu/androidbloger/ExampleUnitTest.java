package go.deyu.androidbloger;

import org.junit.Test;

import go.deyu.androidbloger.data.User;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void test_UserToString()throws Exception {
        User u = new User();
        u.setId(1);
        u.setAge(18);
        u.setName("Mary");
        System.out.println(""+u);
    }
}