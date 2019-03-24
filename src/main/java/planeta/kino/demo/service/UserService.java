package planeta.kino.demo.service;

import planeta.kino.demo.DTO.UserSimpleDTO;
import planeta.kino.demo.entity.User;

public interface UserService {

    User save(UserSimpleDTO userSimpleDTO) throws Exception;

    User getOne(UserSimpleDTO userSimpleDTO) throws IllegalArgumentException;

    double calculateMonthlyPayment(int loanAmount, int termInYears, double interestRate)
            throws IllegalArgumentException;

}
