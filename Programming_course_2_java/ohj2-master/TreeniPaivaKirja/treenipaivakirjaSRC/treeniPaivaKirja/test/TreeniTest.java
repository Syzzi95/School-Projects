package treeniPaivaKirja.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import treeniPaivaKirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.03.17 20:17:41 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class TreeniTest {



  // Generated by ComTest BEGIN
  /** testLisaaTreeninId115 */
  @Test
  public void testLisaaTreeninId115() {    // Treeni: 115
    Treeni pitsi1 = new Treeni(); 
    assertEquals("From: Treeni line: 117", 0, pitsi1.getTreeninId()); 
    pitsi1.lisaaTreeninId(); 
    Treeni pitsi2 = new Treeni(); 
    pitsi2.lisaaTreeninId(); 
    int n1 = pitsi1.getTreeninId(); 
    int n2 = pitsi2.getTreeninId(); 
    assertEquals("From: Treeni line: 123", n2-1, n1); 
  } // Generated by ComTest END
}