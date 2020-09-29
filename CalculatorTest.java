package calculator;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Unit test for simple App.
 */
public class CalculatorTest 
{

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
	}
	
	@Test
	public void TestSinglePositive(){
		Calculator n = new Calculator("1");
		assertEquals(1, n.calculate(),0);
	}

	@Test
	public void TestSingleNegative(){
		Calculator n = new Calculator("-1");
		assertEquals(-1, n.calculate(),0);
	}

	@Test
	public void TrimParenTest1(){
		Calculator n = new Calculator("(1)");
		assertEquals(1, n.calculate(),0);
	}

	@Test
	public void TrimParenTest2(){
		Calculator n = new Calculator("((1))");
		assertEquals(1, n.calculate(),0);
	}

	@Test
	public void TrimParenTest3(){
		Calculator n = new Calculator("(((1)))");
		assertEquals(1, n.calculate(),0);
	}

	@Test
	public void TrimParenTest4(){
		Calculator n = new Calculator("(1+2)");
		assertEquals(3, n.calculate(),0);
	}

	@Test
	public void TrimParenTest5(){
		Calculator n = new Calculator("-(1+2)");
		assertEquals(-3, n.calculate(),0);
	}

	@Test
	public void TrimParenTest6(){
		Calculator n = new Calculator("-(1-2)");
		assertEquals(1, n.calculate(),0);
	}

	@Test
	public void testAdding(){
		Calculator n = new Calculator("5+5");
		assertEquals(10, n.calculate(),0);
	}

	@Test
	public void testAdding1(){
		Calculator n = new Calculator("5+5+6");
		assertEquals(16, n.calculate(),0);
	}

	@Test
	public void testSubstraction(){
		Calculator n = new Calculator("5-5");
		assertEquals(0, n.calculate(),0);
	}

	@Test
	public void testSubstraction2(){
		Calculator n = new Calculator("5-5-4");
		assertEquals(-4, n.calculate(),0);
	}

	@Test
	public void testSubstraction3(){
		Calculator n = new Calculator("-5-5");
		assertEquals(-10, n.calculate(),0);
	}

	@Test
	public void testMultiplication(){
		Calculator n = new Calculator("5*5");
		assertEquals(25, n.calculate(),0);
	}

	@Test
	public void testMultiplication1(){
		Calculator n = new Calculator("-5*5");
		assertEquals(-25, n.calculate(),0);
	}

	@Test
	public void testMultiplication2(){
		Calculator n = new Calculator("-5*(-5)");
		assertEquals(25, n.calculate(),0);
	}

	@Test
	public void testDivision(){
		Calculator n = new Calculator("5/5");
		assertEquals(1, n.calculate(),0);
	}

	@Test
	public void testDivision1(){
		Calculator n = new Calculator("-5/5");
		assertEquals(-1, n.calculate(),0);
	}

	@Test
	public void testDivision2(){
		Calculator n = new Calculator("-5/(-5)");
		assertEquals(1, n.calculate(),0);
	}

	@Test
	public void testExponent(){
		Calculator n = new Calculator("5^3");
		assertEquals(125, n.calculate(),0);
	}

	@Test
	public void testExponent1(){
		Calculator n = new Calculator("-5^3");
		assertEquals(-125, n.calculate(),0);
	}

	@Test
	public void testExponent2(){
		Calculator n = new Calculator("-5^(-3)");
		assertEquals(-0.008, n.calculate(),0);
	}

	@Test
	public void testComplex1(){
		Calculator n = new Calculator("(1+2)*(4/2)");
		assertEquals(6, n.calculate(),0);
	}

	@Test
	public void testComplex2(){
		Calculator n = new Calculator("1+(2*(4/2))");
		assertEquals(5, n.calculate(),0);
	}

	@Test
	public void testComplex3(){
		Calculator n = new Calculator("1+(((2-4)/2)*3)");
		assertEquals(-2, n.calculate(),0);
	}

	@Test
	public void testComplex4(){
		Calculator n = new Calculator("(5-(4*2)+3)");
		assertEquals(0, n.calculate(),0);
	}

	@Test
	public void testComplex5(){
		Calculator n = new Calculator("(5-4*1/2+3)");
		assertEquals(6, n.calculate(),0);
	}

	@Test
	public void testComplex6(){
		Calculator n = new Calculator("(5-4)-(3*2)^3");
		assertEquals(-215, n.calculate(),0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidExpression(){
		Calculator n = new Calculator("(())");
		n.calculate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidExpression1(){
		Calculator n = new Calculator("(5");
		n.calculate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidExpression2(){
		Calculator n = new Calculator("((5)");
		n.calculate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidExpression3(){
		Calculator n = new Calculator("--5");
		n.calculate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidExpression4(){
		Calculator n = new Calculator("*-5");
		n.calculate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidExpression5(){
		Calculator n = new Calculator("(5-4)(2+3)");
		n.calculate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidExpression6(){
		Calculator n = new Calculator("");
		n.calculate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidExpression7(){
		Calculator n = new Calculator("(4+2+(*6))");
		n.calculate();
	}
}