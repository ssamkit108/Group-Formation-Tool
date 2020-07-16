package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.Validation.*;

public class ValidationAbstractFactoryTestImpl implements ValidationAbstractFactory {


    @Override
    public PasswordRulesLoader createPasswordRulesLoader() {

        return new PasswordRulesLoader();
    }

    @Override
    public ValidatePassword createValidatePassword() {

        return new ValidatePassword();
    }

    @Override
    public ValidationRulesDao createValidationRulesDao() {

        return new ValidationRulesDaoImpl();
    }

    @Override
    public ValidationPolicy createMaximumLength() {

        return new MaximumLength();
    }

    @Override
    public ValidationPolicy createMinimumLength() {

        return new MinimumLength();
    }

    @Override
    public ValidationPolicy createMinimumLower() {

        return new MinimumLower();
    }

    @Override
    public ValidationPolicy createMinimumUpper() {

        return new MinimumUpper();
    }

    @Override
    public ValidationPolicy createMinimumSpecial() {

        return new MinimumSpecial();
    }

    @Override
    public ValidationPolicy createSetNotAllow() {

        return new SetNotAllow();
    }

    @Override
    public ValidationPolicy createHistoryConstraint() {

        return new HistoryConstraint();
    }

    @Override
    public HistoryConstraintDao createHistoryConstraintDao() {

        return new HistoryConstraintDaoImpl();
    }

}
