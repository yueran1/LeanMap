package sharingiscaring.sharingiscaring;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;

import sharingiscaring.sharingiscaring.DataStructure.Offer;
import sharingiscaring.sharingiscaring.DataStructure.User;

/**
 * Created by hoye on 2/10/16.
 */
public class testOffer extends ActivityInstrumentationTestCase2 {

    public testOffer(){
        super(Offer.class);
    }

    @UiThreadTest
    public void testBidCons(){
        User user1 = new User();
        User user2 = new User();
        user2.setName("Terry");
        Offer offer1 = new Offer(user1.getName(), null, null, 5.75);
        Offer offer2 = new Offer(user2.getName(), null, null, 3.00);
        assertEquals(5.75, offer1.getRate());
        assertEquals(3.00, offer2.getRate());
        assertNotSame("Testing that the names are different",
                        offer1.getBidder(),
                        offer2.getBidder());
        assertEquals(user1, offer1.getBidder());
        assertEquals(user2, offer2.getBidder());
    }
}
