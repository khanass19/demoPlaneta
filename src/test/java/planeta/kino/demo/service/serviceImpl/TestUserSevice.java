package planeta.kino.demo.service.serviceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import planeta.kino.demo.DemoApplication;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(DemoApplication.class)
public class TestUserSevice {

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCalculateMonthlyPayment() throws Exception {
        final int loanAmount = 100;
        final int termInYear = 5;
        final double interestRate = 0.17;
        double result = userService.calculateMonthlyPayment(loanAmount, termInYear, interestRate);
        assertThat(2.0, is(result));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchSync() throws Exception {
        final int loanAmount = 0;
        final int termInYear = 5;
        final double interestRate = 0;
        double result = userService.calculateMonthlyPayment(loanAmount, termInYear, interestRate);
        assertThat(2.0, is(result));
    }

}
