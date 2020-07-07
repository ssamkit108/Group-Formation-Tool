package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.Validation.PasswordRulesLoader;
import com.dal.catmeclone.Validation.ValidatePassword;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.Validation.ValidationRulesDao;

public interface ValidationAbstractFactoryTest {
	public HistoryConstraintMock createHistoryConstraintMock();
    public PasswordRulesLoader createPasswordRulesLoader();
    public ValidatePassword createValidatePassword();
    public ValidationRulesDao createValidationRulesDao();
    public ValidationPolicy createMaximumLength();
    public ValidationPolicy createMinimumLength();
    public ValidationPolicy createMinimumLower();
    public ValidationPolicy createMinimumUpper();
    public ValidationPolicy createMinimumSpecial();
    public ValidationPolicy createSetNotAllow();
    public ValidationPolicy createHistoryConstraint();
}
