import junit.framework.TestCase;

public class Test extends TestCase {

    @Override
    protected void setUp() throws Exception {
        System.out.println("Set up");
    }

    @org.junit.Test
    public void test(){
        System.out.println("Test");
    }

}
