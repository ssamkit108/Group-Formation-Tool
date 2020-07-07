package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.Validation.HistoryConstraint;
import com.dal.catmeclone.Validation.MaximumLength;
import com.dal.catmeclone.Validation.MinimumLength;
import com.dal.catmeclone.Validation.MinimumLower;
import com.dal.catmeclone.Validation.MinimumSpecial;
import com.dal.catmeclone.Validation.MinimumUpper;
import com.dal.catmeclone.Validation.PasswordRulesLoader;
import com.dal.catmeclone.Validation.SetNotAllow;
import com.dal.catmeclone.Validation.ValidatePassword;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.Validation.ValidationRulesDao;
import com.dal.catmeclone.Validation.ValidationRulesDaoImpl;

public class ValidationAbstractFactoryImpl implements ValidationAbstractFactoryTest {

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
