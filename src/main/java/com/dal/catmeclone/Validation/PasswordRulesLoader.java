package com.dal.catmeclone.Validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.dal.catmeclone.SystemConfig;

@Service
public class PasswordRulesLoader {

	final Logger logger = LoggerFactory.getLogger(PasswordRulesLoader.class);

	// This will container only objects of rules which need to be validate
	private List<ValidationPolicy> ListofRules;

	// This Hashmap will contains the rule object with name
	private Map<String, ValidationPolicy> HashMapofRules = new HashMap<String, ValidationPolicy>();

	ValidationRulesDao ValidationDAO;

	public PasswordRulesLoader() {
		ListofRules = new ArrayList<ValidationPolicy>();
		PrepareListMap();
	}

	private void PrepareListMap() {
		HashMapofRules.put("MaximumLength", new MaximumLength());
		HashMapofRules.put("MinimumLength", new MinimumLength());
		HashMapofRules.put("MinimumLower", new MinimumLower());
		HashMapofRules.put("MinimumUpper", new MinimumUpper());
		HashMapofRules.put("MinimumSpecial", new MinimumSpecial());
		HashMapofRules.put("SetNotAllow", new SetNotAllow());
		HashMapofRules.put("HistoryConstraint", new HistoryConstraint());
	}

	public void CreateActiveRulesList() {
		ValidationDAO = SystemConfig.instance().getValidationRulesDao();
		ListofRules.clear();
		List<String> rules = ValidationDAO.getRulesFromConfig();
		for (String rule : rules) {
			ValidationPolicy ValidationRule = HashMapofRules.get(rule);
			String ruleValue = ValidationDAO.getRulesValueFromConfig(rule);
			ValidationRule.setValue(ruleValue);
			ListofRules.add(ValidationRule);
		}
		logger.info("Activated Rules are:" + ListofRules.toString());
		logger.info("Active Password validation rules fetched from Database.");
	}

	public List<ValidationPolicy> getValidationRulesList() {
		logger.info("Getting Password validation rules.");
		return ListofRules;
	}
}
