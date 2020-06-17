package com.dal.catmeclone.Validation;

import java.util.List;

public interface ValidationRulesDao {
	public List<String> getRulesFromConfig();
	public String getRulesValueFromConfig(String ruleName);
}
