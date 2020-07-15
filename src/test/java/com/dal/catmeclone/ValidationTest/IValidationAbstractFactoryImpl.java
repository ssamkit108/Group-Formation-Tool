package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.Validation.*;

public class IValidationAbstractFactoryImpl implements IValidationAbstractFactory {

    @Override
    public HistoryConstraintMock createHistoryConstraintMock() {
        // TODO Auto-generated method stub
        return new HistoryConstraintMock();
    }

    @Override
    public PasswordRulesLoader createPasswordRulesLoader() {
        // TODO Auto-generated method stub
        return new PasswordRulesLoader();
    }

    @Override
    public ValidatePassword createValidatePassword() {
        // TODO Auto-generated method stub
        return new ValidatePassword();
    }

    @Override
    public ValidationRulesDao createValidationRulesDao() {
        // TODO Auto-generated method stub
        return new ValidationRulesDaoImpl();
    }

    @Override
    public ValidationPolicy createMaximumLength() {
        // TODO Auto-generated method stub
        return new MaximumLength();
    }

    @Override
    public ValidationPolicy createMinimumLength() {
        // TODO Auto-generated method stub
        return new MinimumLength();
    }

    @Override
    public ValidationPolicy createMinimumLower() {
        // TODO Auto-generated method stub
        return new MinimumLower();
    }

    @Override
    public ValidationPolicy createMinimumUpper() {
        // TODO Auto-generated method stub
        return new MinimumUpper();
    }

    @Override
    public ValidationPolicy createMinimumSpecial() {
        // TODO Auto-generated method stub
        return new MinimumSpecial();
    }

    @Override
    public ValidationPolicy createSetNotAllow() {
        // TODO Auto-generated method stub
        return new SetNotAllow();
    }

    @Override
    public ValidationPolicy createHistoryConstraint() {
        // TODO Auto-generated method stub
        return new HistoryConstraint();
    }

}
