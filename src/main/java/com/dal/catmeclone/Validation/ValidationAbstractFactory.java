package com.dal.catmeclone.Validation;

public interface ValidationAbstractFactory {
    public PasswordRulesLoader createPasswordRulesLoader();
    public ValidatePassword createValidatePassword();
    public ValidationRulesDao createValidationRulesDao();
    public HistoryConstraintDao createHistoryConstraintDao();
    public ValidationPolicy createMaximumLength();
    public ValidationPolicy createMinimumLength();
    public ValidationPolicy createMinimumLower();
    public ValidationPolicy createMinimumUpper();
    public ValidationPolicy createMinimumSpecial();
    public ValidationPolicy createSetNotAllow();
    public ValidationPolicy createHistoryConstraint();
}
